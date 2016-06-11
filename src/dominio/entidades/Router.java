package dominio.entidades;

import dominio.entidades.requests.ARP;
import dominio.entidades.requests.ICMP;
import dominio.entidades.requests.MessageType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author brunoccst
 */
public class Router extends IMessageManager {

    private String name;
    private int num_ports;
    private Map<String, Port> ports;
    private Map<String, RouterTableRow> routerTable;
    private LinkedList<ICMP> buffer;
    private Port portCache;
    private String lastSender;

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
        return this.ports.get(""+key);
    }

    public void addPort(Port port) {
        this.ports.put(""+port.getPortNumber(), port);
    }

    protected void Send() {
        ICMP nextMessage,
                messageAux;
        //SE FOR REPLY OLHA A CACHE
        Network network = buffer.peek().getMsgType() == MessageType.Reply ? 
                                portCache.getNetwork() : 
                                GetPortToSend(buffer.peek().getDestIP()).getNetwork();
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
            if (aux.isEmpty()) {
                nextMessage.setFinal();
            } else {
                nextMessage.setNotFinal();
            }
            nextMessage.setTTL(newTTL);
            nextMessage.setSourceName(name);
            network.Receive(nextMessage, lastSender, GetPortToSend(nextMessage.getDestIP()).getMAC());

            if (errorCode == -1) {
                break;
            }
        }
        if (errorCode == -1) {
            errorCode = 0;
            buffer.clear();
            ports.get(lastSender).getNetwork().ReturnError(TIME_EXCEEDED_CODE);
        }
    }

    public void AddRouterTableEntry(RouterTableRow newEntry) {
        routerTable.put(newEntry.getNet_dest(), newEntry);
    }

    @Override
    protected void Receive(ARP message, String defaultGateway, String sender) {
        if (message.getMsgType() == MessageType.Request) {
            Port portAux = GetPortToSend(message.getDestIP());
            if(portAux != null)
                portAux = ports.get(sender);
            message.Reply(this.name, portAux.getMAC());
            System.out.println(message.toString());
            portAux.getNetwork().Receive(message, "", "");
        } else {
            lastSender = message.getSourceMAC();
            Send();
        }
    }

    @Override
    protected void Receive(ICMP message, String defaultGateway, String sender) {
        message.setDestName(name);
        System.out.println(message.toString());
        
        if (message.getTTL() - 1 == 0) {
            ports.get(sender).getNetwork().ReturnError(TIME_EXCEEDED_CODE);
        }
        //SE FOR NOVA MENSAGEM LIMPA BUFFER
        if (!buffer.isEmpty() && message.getMsgType() != buffer.peek().getMsgType()) {
            buffer.clear();
        }
        buffer.add(message);
        
        //SE FOR FINAL COMEÇA A ENVIAR ICMP
        if (message.isFinal()) {
            if (message.getMsgType() == MessageType.Reply) {
                lastSender = sender;
                Send();
                return;
            }
            portCache = ports.get(sender);
            Port portAux = GetPortToSend(message.getDestIP());
            if (portAux == null) {
                ReturnError(NOT_FOUND_NEXT_HOP);
            } else {
                ARP arpRequest = new ARP(this.name, portAux.getIP(), message.getDestIP(), MessageType.Request);
                System.out.println(arpRequest.toString());
                portAux.getNetwork().Receive(arpRequest, "", portAux.getMAC());
            }
        }
    }

    protected Port GetPortToSend(String ip) {
        RouterTableRow row = routerTable.get(TranslateIP(ip));
        if (row == null) {
            return null;
        }
        return ports.get(""+row.getPort());

    }

    Collection<Port> getPortsList() {
        return this.ports.values();
    }

    public static String TranslateIP(String ip) {
        String[] splitted = ip.split("\\.");

        //Classe A
        int firstPart = Integer.parseInt(splitted[0]);
        String secondPart = "0",thirdPart="0",fourthPart = "0";
        if (firstPart > 127) {
            //Classe B
            secondPart = splitted[1];
        } if (firstPart > 191) {
            //Classe C
            thirdPart = splitted[2];
        } 
        return String.format("%d.%s.%s.%s",firstPart,secondPart,thirdPart,fourthPart);
    }
}
