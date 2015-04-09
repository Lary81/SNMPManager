package snmpmanager;
import java.io.IOException;
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

public class SnmpManager {

    public static void main(String[] args) throws IOException {

        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(new int[]{1, 3, 6, 1, 2, 1, 1, 1})));
        pdu.add(new VariableBinding(new OID(new int[]{1, 3, 6, 1, 2, 1, 1, 2})));
        pdu.setType(PDU.GETNEXT);
        
        Address targetAddress = GenericAddress.parse("udp:255.255.255.255/161");
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(1);
        target.setTimeout(5000);
        target.setVersion(SnmpConstants.version1);
        
        try {
            Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();
            ResponseEvent response = snmp.send(pdu, target);
            if (response.getResponse() != null) {
                System.out.println(response.getPeerAddress());
                System.out.println(response.getResponse());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(99);
        }
    }
}
