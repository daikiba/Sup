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
        c.setName(c.getName().replaceAll("[':; ]", ""));
        if (!listContains(clients, c.getName())) {
            clients.add(c);
            System.out.println("++Added new client: " + c.getName());
        }
        else {
            Client oldData = getClientFromList(clients, c.getName());
            System.out.println("$$Loaded old client: " + c.getName());
            if (oldData != null) {
                oldData.setStatus(c.getStatus());
            }
            else {
                System.out.println("Something's wrong with the client list..");
            }
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
    
    public Client getClientFromList(List<Client> list, String name) {
        for(Client c : list) {
            if (name.equals(c.getName())){
                return c;
            }
        }
        return null;
    }
    
    public boolean renameClient(Client c, String newName) {
        newName = newName.replaceAll("['\\(\\)\\}\\{:; ]", "");
        Client oldClient = getClientFromList(clients, newName);
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
