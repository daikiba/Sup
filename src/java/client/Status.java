/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kone
 */
public enum Status {
    Unclear, Home, School, Gaming, Party, Sleep;
    public List<Status> getValues() {
        List<Status> statuses = new ArrayList<Status>();
        Status[] statusesTable = Status.values();
        for (int i = 0; i < statusesTable.length; i++) {
            statuses.add(statusesTable[i]);
        }
        return statuses;
    }
}
