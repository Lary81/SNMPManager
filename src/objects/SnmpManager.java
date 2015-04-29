package objects;
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
    
    private ArrayList<CommunityTarget> listOfTargets;
    private ArrayList<ResponseEvent> listOfAgents;
    private String chosenIp = "";
    
    public SnmpManager() {
        listOfTargets = new ArrayList<>();
        listOfAgents = new ArrayList<ResponseEvent>();
    }
    
    public ArrayList<CommunityTarget> initializeTargetList(String ipRange1, String ipRange2) {
        ArrayList<CommunityTarget> list = new ArrayList<CommunityTarget>();
        CommunityTarget target = new CommunityTarget();
        String rangeIp1[] = ipRange1.split("."); 
        String rangeIp2[] = ipRange2.split(".");
        int range1 = Integer.parseInt(rangeIp1[3]); //range1[3] = menor numero do range1 de máquinas
        int range2 = Integer.parseInt(rangeIp2[3]); //range2[3] = maior numero do range2 de máquinas
        while(range1 < range2){
            Address targetAddress = new UdpAddress("192.168.1."+ range1 + "/161");
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(3);
            target.setTimeout(500);
            target.setVersion(SnmpConstants.version2c);
            list.add(target);
            range1++;
        }
        return list;
    }
    
    public void runNetworkDiscovery() {
        PDU request = new PDU();
        request.setType(PDU.GET);
        OID oid= new OID("1.3.6.1.2.1.1.1.0");
        request.add(new VariableBinding(oid));
        oid= new OID("1.3.6.1.2.1.1.2.0");
        request.add(new VariableBinding(oid));
        oid= new OID("1.3.6.1.2.1.1.3.0");
        request.add(new VariableBinding(oid));
        oid= new OID("1.3.6.1.2.1.1.4.0");
        request.add(new VariableBinding(oid));
        oid= new OID("1.3.6.1.2.1.1.5.0");
        request.add(new VariableBinding(oid));
        try {
            Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();
            for(CommunityTarget target : listOfTargets) {
                ResponseEvent response = snmp.send(request, target);
                if (response.getResponse() != null) {
                    listOfAgents.add(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String listOfAgentsToString() {
        String str = "";
        for(int i=0; i<listOfAgents.size(); i++) {
            ResponseEvent r = listOfAgents.get(i);
            str += "Agent #" + i + ": \n"
                 + "[ipAddress: " + r.getPeerAddress() + "]\n" 
                 + "[sysDescr: " + r.getResponse().get(0).toValueString() + "]\n"
                 + "[sysObjectID: " + r.getResponse().get(1).toValueString() + "]\n"
                 + "[sysUpTime: " + r.getResponse().get(2).toValueString() + "]\n"
                 + "[sysContact: " + r.getResponse().get(3).toValueString() + "]\n"
                 + "[sysName: " + r.getResponse().get(4).toValueString() + "]\n";
            if(i != listOfAgents.size() - 1)
                str+= "\n";
        }
        return str;
    }
    
    public ArrayList<CommunityTarget> getListOfTargets() {
        return listOfTargets;
    }
    
    public ArrayList<ResponseEvent> getListOfAgents() {
        return listOfAgents;
    }
    
        public void setChosenIp(String chosenIp) {
        this.chosenIp = chosenIp;
    } 
}