/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.io.Serializable;
import org.springframework.web.util.HtmlUtils;

public class Client implements Serializable {
    private String id;
    private String name;
    private Status status;
    private Color color;
    
    public Client() {
        id = null;
        name = null;
        status = null;
        color = null;
    }

    public String getId() {
        return HtmlUtils.htmlEscape(id);
    }

    public void setId(String id) {
        id = id.replaceAll("[':; ]", "");
        if (id.length() > 12) {
            id = id.substring(0, 12);
        }
        id = HtmlUtils.htmlEscape(id);
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = HtmlUtils.htmlEscape(name);
        this.name = name;
    }
    
    public String getNameForTextField() {
        return HtmlUtils.htmlUnescape(name);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getColor() {
        String colorString = Integer.toHexString(0xFFFFFF & color.getRGB());
        if (colorString.length() < 6) {
            for (int i = colorString.length(); i < 6; i++) {
                colorString = "0" + colorString;
            }
        }
        return colorString;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
