/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmp;

import java.util.ArrayList;

/**
 *
 * @author Mateus Athaydes
 */
public interface ISnmpManager {

    void runNetworkDiscovery(String community);

    ArrayList<String> getListOfAgents();

    String getObjectAsString(String community, String objectName, String ipTarget);
    
    String getNextObjectAsString(String community, String oid, String ip);

    boolean isAgent(String community, String ipAddress);
    //void setChosenIp(String ip);
}
