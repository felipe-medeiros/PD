/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author estagio.felipea
 */
public class Sala {
    private Map<String,Session> usuarios = 
            Collections.synchronizedMap(new HashMap<String,Session>());

    public Map<String, Session> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Session> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Override
    public String toString() {
        String nomes = "";
        for (Map.Entry<String, Session> entry : usuarios.entrySet()){
            nomes += entry.getKey() + ", ";
        }
        return nomes;
    }    
}
