package dominio.entidades;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author brunoccst
 */
public class Network {
    
    private Map<String, Node> nodes;
    private Map<String, Port> ports;
    private String IP;
    
    public Network(String ip) {
        this.IP = ip;
        this.nodes = new TreeMap<>();
        this.ports = new TreeMap<>();
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
    
    public void AddNode(Node node) {
        nodes.put(node.getName(), node);
    }
    
    public void AddPort(Port port)
    {
        ports.put(port.getIP(), port);
    }

}
