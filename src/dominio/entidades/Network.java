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
    private Node nodeCache;
    private Port portCache;
    private String lastSender;

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
        nodes.put(node.getIP(), node);
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
    protected void Receive(ARP message, String gatewayDefault, String sender) {
        if (message.getMsgType() == MessageType.Reply) {
            Port port;
            Node node = nodes.get(message.getDestIP());
            if (node == null) {
                port = ports.get(message.getDestIP());
                port.getRouter().Receive(message, "", "");
            } else {
                node.Receive(message, "", "");
            }
        } else {
            try{
            Node node = nodes.get(message.getDestIP());
            if (node == null) {
                Port portAux;
                //RECEBENDO ARP DE UM NODO
                if (!gatewayDefault.equals("")) {
                    portAux = ports.values().stream().filter(port -> port.getIP().equals(gatewayDefault)).findFirst().get();
                    portAux.getRouter().Receive(message, "", portAux.getPortNumber() + "");
                } 
                else {
                    //RECEBENDO ARP DE UM ROUTER
                    ports.values().forEach((port)->{
                        if(port.getIP().equals(message.getSourceIP()) && message.getMsgType() == MessageType.Request)
                            port.getRouter().Receive(message, "", port.getPortNumber() + "");
                    });
                }
                
            } else {
                node.Receive(message, "", "");
            }
            }catch(Exception e){
                lastSender = sender;
                ReturnError(NOT_FOUND_HOST);
            }
        }
    }

    protected void loadCacheWithMac(String MAC) {
        if (nodeCache == null && portCache == null) {
            Optional<Node> optional = nodes.values().stream().filter(node -> node.getMAC().equals(MAC)).findFirst();
            if (optional.isPresent()) {
                nodeCache = optional.get();
            } else {
                portCache = ports.values().stream().filter(port -> port.getMAC().equals(MAC) ).findFirst().get();
            }
        }

    }

    protected void clearCache() {
        nodeCache = null;
        portCache = null;
    }
    @Override
    public void ReturnError(int code) {
        clearCache();
        loadCacheWithMac(lastSender);
        if(nodeCache == null)
            portCache.getRouter().ReturnError(code);
        else
            nodeCache.ReturnError(code);

    }

    @Override
    protected void Receive(ICMP message, String gatewayDefault, String sender) {
        loadCacheWithMac(gatewayDefault);
        Node nodeAux = nodeCache;
        Port portAux = portCache;
        if ( message.getMsgType() == MessageType.Request) {
            if (message.isFinal()) {
                clearCache();
                loadCacheWithMac(sender);
            }
            if (nodeAux != null) {
                nodeAux.Receive(message, "", sender);
            } else {
                portAux.getRouter().Receive(message, gatewayDefault, portAux.getPortNumber() + "");
            }

        } else {
            lastSender = sender;
            if (nodeAux != null) {
                nodeAux.Receive(message, "", "");
            } else {
                portAux.getRouter().Receive(message, "", "");
            }
        }
    }
}
