/*
 * IFPB - Campus João Pessoa
 * CST - Sistemas para Internet
 * Felipe Medeiros Alves
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author felipe
 */
public class Cliente {
    
    public static void main(String[] args) {
        Socket socket = null;
        DataOutputStream out;
        DataInputStream in;
        Scanner sc;
        String uri,ip,porta,ipport,file = null;
        String[] parts;
        Boolean conexao = false;
        try {
            while(true){
                do{
                    Pattern patternIPPORT;
                    do{
                        do{
                            sc = new Scanner(System.in);
                            uri = sc.nextLine();
                        }while(!uri.contains("/"));
                        parts = uri.split("/");
                        ipport = parts[0];

                        String IP_PORT_PATTERN = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})";
                        patternIPPORT = Pattern.compile(IP_PORT_PATTERN);
                    }while(!patternIPPORT.matcher(ipport).matches());
                    
                    file = parts[1];
                    parts = parts[0].split(":");
                    ip = parts[0];
                    porta = parts[1];
                    
                    try{
                        socket = new Socket(ip,Integer.parseInt(porta));
                        conexao = true;
                    }catch (ConnectException ex){
                        System.out.println("Não foi possível conectar-se com o servidor.");
                    }
                }while(!conexao);

                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                out.writeUTF(file);
                int status = in.readInt();
                if(status == 200){
                    System.out.println(in.readUTF());
                }else{
                    System.out.println("Arquivo não encontrado.");
                }
                
                conexao = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
