/*
 * IFPB - Campus João Pessoa
 * CST - Sistemas para Internet
 * Felipe Medeiros Alves
 */
package servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class LerURI extends Thread{
    private Socket socket;
    
    public LerURI(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream in;
        DataOutputStream out; 
        String msg = null;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            msg = in.readUTF();
            File file = new File(System.getProperty("user.dir")+"\\"+msg);
                        
            if (!file.exists()){
                out.writeInt(400);           
            }else{
                out.writeInt(200);
                BufferedReader reader = new BufferedReader( new FileReader(file) );
                String s = "", line = null;
                while ((line = reader.readLine()) != null) {
                    s += line;
                }                
                out.writeUTF(s);                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LerURI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
