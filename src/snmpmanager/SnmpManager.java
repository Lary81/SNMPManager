package snmpmanager;
import java.io.IOException;
import java.util.ArrayList;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
 
/**
 *
 * @author Carolina Nery
 * @author Mateus Athaydes
 * @author Pedro Fontoura
 */
public class SnmpManager {
 
    public static void main(String[] args) throws IOException {
 
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(new int[]{1, 3, 6, 1, 2, 1, 1, 1})));
        pdu.add(new VariableBinding(new OID(new int[]{1, 3, 6, 1, 2, 1, 1, 2})));
        pdu.setType(PDU.GETNEXT);
       
        ArrayList<CommunityTarget> AgentsPool = new ArrayList<CommunityTarget>();
        String baseAddress = "udp:10.0.0.";
        for(int i = 0; i < 255; i++) {
            CommunityTarget target = new CommunityTarget();
            Address targetAddress = GenericAddress.parse(baseAddress + Integer.toString(i) + "/161");
            //Address targetAddress = GenericAddress.parse("192.168.25.47/161");
            target.setCommunity(new OctetString("public"));
            target.setAddress(targetAddress);
            target.setRetries(1);
            target.setTimeout(1000);
            target.setVersion(SnmpConstants.version1);
            AgentsPool.add(target);
        }
       
        try {
            Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();
            for(CommunityTarget target : AgentsPool) {
                System.out.println("Trying " + target.getAddress() + "...");
                ResponseEvent response = snmp.send(pdu, target);
                if (response.getResponse() != null) {
                    response.getResponse().toString();
                    System.out.println(response.getPeerAddress());
                    System.out.println(response.getResponse());
                } else {
                    System.out.println(target.getAddress() + " has timeouted.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(99);
        }
    }
}