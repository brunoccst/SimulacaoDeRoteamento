package dominio.entidades;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author brunoccst
 */
public class Network {
    
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
    
    public Node GetNode(String nodeName)
    {
        return nodes.get(nodeName);
    }
    
    public void AddPort(Port port)
    {
        ports.put(port.getIP(), port);
    }
    
    public Port GetPort(String portIp)
    {
        return ports.get(portIp);
    }

    public void SendMessage(String message)
    {
        
    }
}
