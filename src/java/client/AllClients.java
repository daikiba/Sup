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
    private static int latestXml;
    
    public AllClients() {
         clients = new ArrayList<Client>();
         latestXml = 1;
    }
    
    public void updateClient(Client c) {
        if (!listContainsID(c.getId())) {
            clients.add(c);
            System.out.println("++Added new client: " + c.getId());
        }
        else {
            System.out.println("Updating..");
        }
        latestXml++;
        System.out.println("Server clientxml version: " + latestXml);
    }
    
    public static int getLatestXmlId() {
        return latestXml;
    }
    
    public void setClients() { }
    
    public List<Client> getClients() {
        return clients;
    }
    
    public boolean listContainsName(String name) {
        for(Client c : clients) {
            if (name.equalsIgnoreCase(c.getName())) return true;
        }
        
        return false;
    }
    
    public boolean listContainsID(String cS) {
        for (Client cl : clients) {
            if (cS.equals(cl.getId())) return true;
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
        if (oldClient != null && !listContainsName(newName)) {
            oldClient.setName(newName);
            return true;
        }
        return false;
    }
    
    public int getSize() {
        return clients.size();
    }
}
