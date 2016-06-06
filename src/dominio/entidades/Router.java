package dominio.entidades;

import dominio.entidades.requests.ARP;
import dominio.entidades.requests.ICMP;
import dominio.entidades.requests.MessageType;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import utils.IntClass;

/**
 *
 * @author brunoccst
 */
public class Router extends IMessageManager {

    private String name;
    private int num_ports;
    private Map<IntClass, Port> ports;
    private Map<String, RouterTableRow> routerTable;

    public Router() {
        routerTable = new TreeMap<>();
        ports = new TreeMap<>();
    }

    public Router(String name, int num_ports) {
        this();
        this.name = name;
        this.num_ports = num_ports;
    }

    public String getName() {
        return name;
    }

    public int getNum_ports() {
        return num_ports;
    }

    public Port getPort(int key) {
        return this.ports.get(new IntClass(key));
    }

    public void addPort(Port port) {
        this.ports.put(new IntClass(port.getPortNumber()), port);
    }

    public void AddRouterTableEntry(RouterTableRow newEntry) {
        routerTable.put(newEntry.getNet_dest(), newEntry);
    }

    @Override
    protected void Receive(ARP message, String defaultGateway) {
            RouterTableRow row = routerTable.get(TranslateIP(message.getDestIP()));
            Port portAux = ports.get(new IntClass(row.getPort()));
            message.Reply(this.name, portAux.getMAC());
            portAux.getNetwork().Receive(message, "");
    }

    @Override
    protected void Receive(ICMP message, String defaultGateway) {
        //SEND ARP
        //SEND ICMP
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Collection<Port> getPortsList() {
        return this.ports.values();
    }
    
    public static String TranslateIP(String ip) {
        String[] splitted = ip.split("\\.");

        int firstPart = Integer.parseInt(splitted[0]);
        if (firstPart <= 127) {
            //Classe A
            return splitted[0] + ".0.0.0";
        } else if (firstPart <= 191) {
            
            //Classe B
            return splitted[0] + splitted[1] + ".0.0";
        } else {
            //Classe C
            return splitted[0] + splitted[1] + splitted[2] + ".0";
        }
    }
}
