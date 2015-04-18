/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *
 * @author PEDRO
 */
public class Main {
    
    public static void main(String[] args) {
        SnmpManager manager = new SnmpManager();
        manager.runNetworkDiscovery();
        for(int i=0; i<manager.getListOfAgents().size(); i++) {
            System.out.println(manager.listOfAgentsToString());
        }
    }
        
}
