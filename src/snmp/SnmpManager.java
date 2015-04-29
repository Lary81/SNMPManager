package snmp;

import java.io.IOException;
import java.util.ArrayList;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author Carolina Nery
 * @author Mateus Athaydes
 * @author Pedro Fontoura
 */
public class SnmpManager implements ISnmpManager {

    private ArrayList<String> listOfAgents;

    public SnmpManager() {
        listOfAgents = new ArrayList<>();
    }

    @Override
    public ArrayList<String> getListOfAgents() {
        return listOfAgents;
    }
    
    private void setListOfAgents(ArrayList<String> list) {
        listOfAgents = list;
    }

    @Override
    public boolean isAgent(String community, String ip) { // +/161
        try {
            CommunityTarget target = buildTarget(community, ip);
            PDU request = new PDU();
            request.setType(PDU.GET);
            OID oid = new OID("1.3.6.1.2.1.1.1.0"); // sysDescr 
            request.add(new VariableBinding(oid));
            try {
                Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                snmp.listen();
                ResponseEvent response = snmp.send(request, target);
                if (response.getResponse() != null) {
                    return true;
                } else {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    @Override
    public void runNetworkDiscovery(String community) {
        ArrayList<String> targetIpAddressList = this.buildTargetIPList();
        ArrayList<String> newListOfAgents = new ArrayList<>();
        for (String ipAddress : targetIpAddressList) {
            if (this.isAgent(community, ipAddress)) {
                newListOfAgents.add(ipAddress);
            }
        }
        this.setListOfAgents(newListOfAgents);
    }

    @Override
    public String getObjectAsString(String community, String oid, String ip) { // +161
        try {
            CommunityTarget target = this.buildTarget(community, ip);
            String responseString = "";
            PDU request = new PDU();
            request.setType(PDU.GET);
            OID oidObj = new OID(oid);
            request.add(new VariableBinding(oidObj));
            try {
                Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                snmp.listen();
                ResponseEvent responseEvent = snmp.send(request, target);
                if (responseEvent.getResponse() != null) {
                    responseString = responseEvent.getResponse().getVariableBindings().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        } catch (NumberFormatException nfe) {
            return "Invalid OID";
        }
    }
    
    @Override
    public String getNextObjectAsString(String community, String oid, String ip) { // +161
        try {
            CommunityTarget target = this.buildTarget(community, ip);
            String responseString = "";
            PDU request = new PDU();
            request.setType(PDU.GETNEXT);
            OID oidObj = new OID(oid);
            request.add(new VariableBinding(oidObj));
            try {
                Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                snmp.listen();
                ResponseEvent responseEvent = snmp.send(request, target);
                if (responseEvent.getResponse() != null) {
                    responseString = responseEvent.getResponse().getVariableBindings().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        } catch (NumberFormatException nfe) {
            return "Invalid OID";
        }
    }

    private CommunityTarget buildTarget(String community, String ip) { // +/161
        CommunityTarget target = new CommunityTarget();
        Address targetAddress = new UdpAddress(ip);
        target.setCommunity(new OctetString(community));
        target.setAddress(targetAddress);
        target.setRetries(1);
        target.setTimeout(100);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    private ArrayList<String> buildTargetIPList() {
        String ip;
        ArrayList<String> list = new ArrayList<>();
        int lastOctet = 64; // 150
        while (lastOctet <= 64) { // 239
            // 10.32.143.
            ip = "10.32.148." + Integer.toString(lastOctet) + "/161";
            list.add(ip);
            lastOctet++;
        }
        return list;
    }

}
