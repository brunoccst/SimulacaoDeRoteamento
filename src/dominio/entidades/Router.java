package dominio.entidades;

import dominio.entidades.requests.ARP;
import dominio.entidades.requests.ICMP;
import dominio.entidades.requests.Message;
import dominio.entidades.requests.MessageType;
import java.util.Collection;
import java.util.LinkedList;
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
    private LinkedList<ICMP> buffer;

    public Router() {
        routerTable = new TreeMap<>();
        ports = new TreeMap<>();
        buffer = new LinkedList<>();
        errorCode = 0;
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
    protected void Receive(ARP message, String defaultGateway, String sender) {
        if (message.getMsgType() == MessageType.Request) {
            Port portAux = GetPortToSend(message.getDestIP());
            message.Reply(this.name, portAux.getMAC());
            portAux.getNetwork().Receive(message, "","");
        } else {
            ICMP nextMessage,
                    messageAux;
            Network network = GetPortToSend(message.getDestIP()).getNetwork();
            int MTU = network.getMTU();
            int newTTL = buffer.peek().getTTL() - 1;
            LinkedList<ICMP> aux = new LinkedList<>();
            buffer.forEach(e -> aux.add((ICMP) e.Clone()));

            while (!aux.isEmpty()) {
                nextMessage = aux.poll();
                if (nextMessage.getData().length() > MTU) {
                    messageAux = (ICMP) nextMessage.Clone();
                    messageAux.setData(messageAux.getData().substring(MTU));
                    aux.addFirst(messageAux);
                    
                    nextMessage.setData(messageAux.getData().substring(0, MTU));
                }
                if (aux.isEmpty()) 
                    nextMessage.setFinal();
                else 
                    nextMessage.setNotFinal();
                nextMessage.setTTL(newTTL);
                network.Receive(nextMessage, "",GetPortToSend(message.getDestIP()).getMAC());

                if (errorCode == -1) {
                    break;
                }
            }
            if (errorCode == -1) {
                errorCode = 0;
                buffer.clear();
                ports.get(Integer.parseInt(sender)).getNetwork().ReturnError(TIME_EXCEEDED_CODE);
            }
        }
    }

    @Override
    protected void Receive(ICMP message, String defaultGateway, String sender) {
        if(message.getTTL()-1 == 0){
            ports.get(Integer.parseInt(sender)).getNetwork().ReturnError(TIME_EXCEEDED_CODE);
        }
        buffer.add(message);
        if (message.isFinal()) {
            Port portAux = GetPortToSend(message.getDestIP());
            if (portAux == null) {
                ReturnError(NOT_FOUND_NEXT_HOP);
            } else {
                portAux.getNetwork().Receive(new ARP(this.name, portAux.getIP(), message.getDestIP(), MessageType.Request), "",portAux.getMAC());
            }
        }
    }

    protected Port GetPortToSend(String ip) {
        RouterTableRow row = routerTable.get(TranslateIP(ip));
        if (row == null) {
            return null;
        }
        return ports.get(new IntClass(row.getPort()));

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
