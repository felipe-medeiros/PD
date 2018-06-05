/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author estagio.felipea
 */
@ServerEndpoint("/chat/{sala}/{usuario}")
public class NewWSEndpoint {
    private static Map<String,Sala> salas = 
            Collections.synchronizedMap(new HashMap<>());
    
    @OnOpen
    public void conectar(Session ses,@PathParam("sala") String sala,@PathParam("usuario") String usuario){
        Sala sala_obj;
        
        if(salas.containsKey(sala)){
            salas.get(sala).getUsuarios().put(usuario, ses);
        }else{
            sala_obj = new Sala();
            sala_obj.getUsuarios().put(usuario, ses);
            salas.put(sala, sala_obj);
        }
        sala_obj = salas.get(sala);
        for (Map.Entry<String, Session> entry : sala_obj.getUsuarios().entrySet()){
            try {
                entry.getValue().getBasicRemote().sendText("Usuários conectados: "+sala_obj.toString());
            } catch (IOException ex) {
                Logger.getLogger(NewWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnMessage
    public void onMessage(String message,Session ses) {
        try {
            Map<String,String> params = ses.getPathParameters();
            Sala sala_obj = salas.get(params.get("sala"));
            Map<String,Session> users = sala_obj.getUsuarios();
            String usuario = null;

            for (Map.Entry<String, Session> entry : users.entrySet()){
                if(entry.getValue() == ses)
                    usuario = (String) entry.getKey();
            }

            long yourmilliseconds = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");    
            Date resultdate = new Date(yourmilliseconds);

            if(message.contains("rename")){
                String[] msg_parts = message.split(" ");
                String nome = msg_parts[1];

                if(users.containsKey(nome)){
                    ses.getBasicRemote().sendText("Nome de usuário já em uso");
                }else{
                    
                    users.put(nome, ses);
                    users.remove(usuario);
                    ses.getBasicRemote().sendText("Renomeado com sucesso");
                    for (Map.Entry<String, Session> entry : users.entrySet()){
                        entry.getValue().getBasicRemote().sendText(usuario+" mudou o nick para "+nome);
                    }
                    
                }                
            }else if(message.contains("send -u")){
                String[] msg_parts = message.split(" ", 4);
                String msg = msg_parts[3];
                String destino = msg_parts[2];
                
                users.get(destino).getBasicRemote().sendText(
                            "["+usuario+"]"+"["+sdf.format(resultdate)+"] reservadamente: "+msg);
            }else{
                for(Map.Entry<String,Session> entry: users.entrySet()){
                    entry.getValue().getBasicRemote().sendText("["+usuario+"]"+"["+sdf.format(resultdate)+"]: "+message);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NewWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnClose
    public void desconectar(Session ses){
        Map<String,String> params = ses.getPathParameters();
        Sala sala_obj = salas.get(params.get("sala"));
        Map<String,Session> users = sala_obj.getUsuarios();
        
        for (Map.Entry<String, Session> entry : users.entrySet()){
            if(entry.getValue() == ses){
                users.remove(entry.getKey());
            }
        }
        for (Map.Entry<String, Session> entry : users.entrySet()){
            try {
                entry.getValue().getBasicRemote().sendText("Usuários conectados: "+sala_obj.toString());
            } catch (IOException ex) {
                Logger.getLogger(NewWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
