/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import org.snmp4j.CommunityTarget;
import org.snmp4j.event.ResponseEvent;

/**
 *
 * @author Mateus Athaydes
 */
public interface ISnmpManager {

    ArrayList<CommunityTarget> initializeTargetList(String ipRange1, String ipRange2);

    void runNetworkDiscovery();

    String listOfAgentsToString();

    ArrayList<CommunityTarget> getListOfTargets();

    ArrayList<ResponseEvent> getListOfAgents();

    void setChosenIp(String ip);
}
