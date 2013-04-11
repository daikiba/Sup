/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Kone
 */
public class AllClients implements Serializable {
    private static List<Client> clients;
    
    public AllClients() {
         clients = new ArrayList<Client>();
    }
    
    public void updateClient(Client c) {
        if (!listContains(clients, c.getId())) {
            clients.add(c);
            System.out.println("++Added new client: " + c.getId());
        }
    }
    
    public void setClients() { }
    
    public List<Client> getClients() {
        return clients;
    }
    
    public boolean listContains(List<Client> list, String name) {
        boolean containsClient = false;
        
        for(Client c : list) {
            if (name.equals(c.getName())){
                containsClient = true;
                break;
            }
        }
        
        return containsClient;
    }
    
    public boolean containsID(Client c) {
        for (Client cl : clients) {
            if (c.getId().equalsIgnoreCase(cl.getId())) return true;
        }
        return false;
    }
    
    public Client getClientFromList(List<Client> list, String name) {
        for(Client c : list) {
            if (name.equals(c.getId())){
                return c;
            }
        }
        return null;
    }
    
    public boolean renameClient(Client c, String newName) {
        Client oldClient = getClientFromList(clients, c.getId());
        if (oldClient != null && !listContains(clients, newName)) {
            oldClient.setName(newName);
            return true;
        }
        return false;
    }
    
    public int getSize() {
        return clients.size();
    }
}
