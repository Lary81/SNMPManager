/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import snmp.ISnmpManager;
import snmp.SnmpManager;

/**
 *
 * @author Mateus Athaydes
 */
public class SnmpController {

    ISnmpManager snmpManager;

    public SnmpController() {
        snmpManager = new SnmpManager();
    }

    public String networkDiscoveryToString(String community) {
        String str;
        snmpManager.runNetworkDiscovery(community);
        if (snmpManager.getListOfAgents().isEmpty()) {
            str = "No agents have been found";
        } else {
            str = "List of agents found:\n\n";
            ArrayList<String> listOfAgents = snmpManager.getListOfAgents();
            for (int i = 0; i < listOfAgents.size(); i++) {
                String address = listOfAgents.get(i);
                String formatedAddress = address.split("/")[0]; //
                str += "[" + (i) + "] " + formatedAddress + "\n";
                if (i != snmpManager.getListOfAgents().size() - 1) {
                    str += "\n";
                }
            }
        }
        return str;
    }

    public boolean isAgent(String community, String ip) { // -/161
        return snmpManager.isAgent(community, (ip + "/161"));
    }

    public String snmpFunctionsToString(String community, String function, String object, String ipTarget) {
        String str;
        switch (function) {
            case "GET":
                str = snmpManager.getObjectAsString(community, object, (ipTarget + "/161"));
                break;
            case "GETNEXT":
                str = snmpManager.getNextObjectAsString(community, object, (ipTarget + "/161"));
                break;
            case "SET":
                str = "";
                break;
            case "GETBULK":
                str = "";
                break;
            case "WALK":
                str = "";
                break;
            case "GETTABLE":
                str = "";
                break;
            case "GETDELTA":
                str = "";
                break;
            default:
                str = "";
        }
        return str;
    }
    
    public String translateObjectToOID(String object) {
        String oid;
        switch (object.toLowerCase()) {
            case "sysdescr":
                oid = "1.3.6.1.2.1.1.1.0";
                break;
            case "sysuptime":
                oid = "1.3.6.1.2.1.1.3.0";
                break;
            case "syscontact":
                oid = "1.3.6.1.2.1.1.4.0";
                break;
            case "sysname":
                oid = "1.3.6.1.2.1.1.5.0";
                break;
            case "syslocation":
                oid = "1.3.6.1.2.1.1.6.0";
                break;
            case "ifnumber":
                oid = "1.3.6.1.2.1.2.1.0";
                break;
            case "iftable":
                oid = "1.3.6.1.2.1.2.2.0";
                break;
            case "ipinreceives":
                oid = "1.3.6.1.2.1.4.3.0";
                break;
            case "ipoutrequests":
                oid = "1.3.6.1.2.1.4.10.0";
                break;
            case "iproutetable":
                oid = "1.3.6.1.2.1.4.21.0";
                break;
            case "tcpinsegs":
                oid = "1.3.6.1.2.1.6.10.0";
                break;
            case "tcpoutsegs":
                oid = "1.3.6.1.2.1.6.11.0";
                break;
            case "tcpconntable":
                oid = "1.3.6.1.2.1.6.13.0";
                break;
            case "udpindatagrams":
                oid = "1.3.6.1.2.1.7.1.0";
                break;
            case "udpoutdatagrams":
                oid = "1.3.6.1.2.1.7.4.0";
                break;
            case "udptable":
                oid = "1.3.6.1.2.1.7.5.0";
                break;
            case "icmpinmsgs":
                oid = "1.3.6.1.2.1.5.1.0";
                break;
            case "icmpinechos":
                oid = "1.3.6.1.2.1.5.8.0";
                break;
            case "icmpinechoreps":
                oid = "1.3.6.1.2.1.5.9.0";
                break;
            case "icmpoutmsgs":
                oid = "1.3.6.1.2.1.5.14.0";
                break;
            case "snmpinpkts":
                oid = "1.3.6.1.2.1.11.1.0";
                break;
            case "snmpoutpkts":
                oid = "1.3.6.1.2.1.11.2.0";
                break;
            case "snmpinbadcommunityname":
                oid = "1.3.6.1.2.1.11.4.0";
                break;
            case "snmpingetrequests":
                oid = "1.3.6.1.2.1.11.15.0";
                break;
            case "snmpingetnexts":
                oid = "1.3.6.1.2.1.11.16.0";
                break;
            case "snmpinsetrequests":
                oid = "1.3.6.1.2.1.11.17.0";
                break;
            case "snmpingetresponses":
                oid = "1.3.6.1.2.1.11.18.0";
                break;
            case "snmpoutgetrequests":
                oid = "1.3.6.1.2.1.11.25.0";
                break;
            case "snmpoutgetnexts":
                oid = "1.3.6.1.2.1.11.26.0";
                break;
            case "snmpoutsetrequests":
                oid = "1.3.6.1.2.1.11.27.0";
                break;
            case "snmpoutgetresponses":
                oid = "1.3.6.1.2.1.11.28.0";
                break;
            default:
                oid = "";
        }
        return oid;
    }
}
