/*
 * IFPB - Campus João Pessoa
 * CST - Sistemas para Internet
 * Felipe Medeiros Alves
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class Servidor {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        try {
                        
             serverSocket = new ServerSocket(4200);
             while(true){
                socket = serverSocket.accept();
                System.out.println("Conectou com:"+socket.getInetAddress()+":"+socket.getPort());

                LerURI ler = new LerURI(socket);
                ler.start();
             }             
             
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
