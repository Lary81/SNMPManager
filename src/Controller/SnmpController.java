/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import objects.ISnmpManager;
import objects.SnmpManager;
import org.snmp4j.CommunityTarget;

/**
 *
 * @author Mateus Athaydes
 */
public class SnmpController {
    
    ISnmpManager snmpManager;
    public SnmpController(){
        snmpManager = new SnmpManager();
    }
    
    public void GoAction(String ip){
        snmpManager.setChosenIp(ip);
        
        //preparar para a próxima tela
    }
    
    public String TargetListDiscoveryAction(String ipRange1, String ipRange2){
        String str = "";
        ArrayList<CommunityTarget> list = snmpManager.initializeTargetList(ipRange1, ipRange2);
        for(CommunityTarget target : list){
            str += "IP: " + target.getAddress() + "\n";
        }
        return str;
    }
}
