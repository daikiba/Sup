/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Kone
 */

@Controller
@Scope("session")
@SessionAttributes("client")
public class PageController implements Serializable {
    
    private Client client;
    
    @Autowired
    private AllClients clients;
    
    @RequestMapping("/index.htm")
    public String frontpage(Model model) {
        if (client != null) {
            model.addAttribute("myName", client.getName());
            model.addAttribute("myColor", client.getColor());
            model.addAttribute("myStatus", client.getStatus());
        }
        else {
            model.addAttribute("myName", "Username");
            model.addAttribute("myColor", "99FFAA");
            model.addAttribute("myStatus", "Home");
        }
        return "index";
    }
    
    @RequestMapping("/userxml.htm")
    public void xmlOut(HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<clients>");
        for(Client c : clients.getClients()) {
            sb.append("<client>");
            sb.append("<id>");
            sb.append(c.getName());
            sb.append("</id>");
            sb.append("<status>");
            sb.append(c.getStatus());
            sb.append("</status>");
            sb.append("<color>");
            sb.append(c.getColor());
            sb.append("</color>");
            sb.append("</client>");
        }
        sb.append("</clients>");
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(sb.toString());
    }
    
    @RequestMapping("/userlist.htm")
    public String showUserList(Model model) {
        model.addAttribute("clients", clients);
        return "userlist";
    }
    
    @RequestMapping("/savemyinfo.htm")
    public String usersave(Model model,
    @RequestParam("id") String name,
    @RequestParam(value = "status", required = false) String status,
    @RequestParam(value = "color", required = false) String color){
        boolean nameChanged = true;
        if (client == null) {
             client = new Client();
        }
        else {
            if (!client.getName().equals(name)) {
                nameChanged = clients.renameClient(client, name);
            }
        }
        if (nameChanged) {
            client.setName(name);
        }
        
        Status newStatus = Status.Unclear;
        try {
            newStatus = Status.valueOf(status);
        }
        catch(Exception ex) { }
        client.setStatus(newStatus);

        if (color != null) {
            int colorCode = 0xF00F00;
            color = color.replaceAll("#", "");
            if (color.length() == 3) {
                colorCode = getValueForHex3(color);
            }
            else if (color.length() == 6) {
                colorCode = getValueForHex6(color);
            }
            Color clColor = new Color(colorCode);
            client.setColor(clColor);
        }
        else {
            client.setColor(Color.yellow);
        }
             
        clients.updateClient(client);
        return "save";
    }
    
    public int getValueForHex3(String hex) {
        String longerHex = String.valueOf(hex.charAt(0)) + hex.charAt(0) +
                String.valueOf(hex.charAt(1)) + hex.charAt(1) +
                String.valueOf(hex.charAt(2)) + hex.charAt(2);
        return getValueForHex6(longerHex);
    }
    
    public int getValueForHex6(String hex) {
        int r = getValueForHex2(hex.substring(0, 2));
        int g = getValueForHex2(hex.substring(2, 4));
        int b = getValueForHex2(hex.substring(4, 6));
        return ((r << 16) + (g << 8) + b);
    }
    
    public int getValueForHex2(String hex) {
        int val = 0;
        hex = hex.toUpperCase();
        char firstPart = hex.charAt(0);
        char secondPart = hex.charAt(1);
        val = 16 * getValueForChar(firstPart);
        val += getValueForChar(secondPart);
        return (0xFF & val);
    }
    
    public int getValueForChar(char hexChar) {
        // 0 = 48
        // 9 = 57
        // A = 65
        // F = 70
        int retVal = 0;
        if (hexChar > '9') {
            retVal = Math.min(10 + (int)hexChar - 65, 15);
        }
        else {
            retVal = Math.max(0, (int)hexChar - 48);
        }
        return retVal;
    }
}
