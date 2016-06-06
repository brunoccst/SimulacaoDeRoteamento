package dominio.entidades;

import dominio.entidades.requests.ARP;
import dominio.entidades.requests.ICMP;
import dominio.entidades.requests.MessageType;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 *
 * @author brunoccst
 */
public class Network extends IMessageManager {

    private Map<String, Node> nodes;
    private Map<String, Port> ports;
    private int MTU;
    private String IP;

    public Network(String ip, int mtu) {
        this.IP = ip;
        this.MTU = mtu;
        this.nodes = new TreeMap<>();
        this.ports = new TreeMap<>();
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getMTU() {
        return MTU;
    }

    public void setMTU(int MTU) {
        this.MTU = MTU;
    }

    public void AddNode(Node node) {
        nodes.put(node.getName(), node);
    }

    public Node GetNode(String nodeName) {
        return nodes.get(nodeName);
    }

    public void AddPort(Port port) {
        ports.put(port.getIP(), port);
    }

    public Port GetPort(String portIp) {
        return ports.get(portIp);
    }

    public void SendMessage(String message) {

    }

    @Override
    protected void Receive(ARP message, String gatewayDefault) {
        if (message.getMsgType() == MessageType.Reply) {
            Port port;
            Node node = nodes.get(message.getDestIP());
            if (node == null) {
                port = ports.get(message.getDestIP());
                port.getRouter().Receive(message,"");
            } else {
                node.Receive(message,"");
            }
        } else {
            Optional<Node> nodeAux = nodes.values().stream().filter(node -> node.getIP().equals(message.getDestIP())).findFirst();
            if (nodeAux.isPresent()) {
                nodeAux.get().Receive(message,"");
            } else {
                Port portAux;
                if(!gatewayDefault.equals(""))
                    portAux = ports.values().stream().filter(port -> port.getIP().equals(gatewayDefault)).findFirst().get();
                else
                    portAux = ports.values().stream().filter(port -> !port.getIP().equals(message.getDestIP())).findFirst().get();
                portAux.getRouter().Receive(message,"");
            }
        }
    }

    @Override
    protected void Receive(ICMP message, String gatewayDefault) {
    }
}
