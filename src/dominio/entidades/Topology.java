package dominio.entidades;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author brunoccst
 */
public class Topology {
    
    private Map<String, Node> nodes;
    private Map<String, Router> routers;
    private Map<String, Network> networks;

    public Topology() {
        nodes = new TreeMap<>();
        routers = new TreeMap<>();
        networks = new TreeMap<>();
    }
    
    public void AddNode(Node node){
        nodes.put(node.getName(), node);
    }
    public void AddRouter(Router router){
        routers.put(router.getName(), router);
    }
    public void AddNetwork(Network network){
        networks.put(network.getIP(), network);
    }
    
    public Node GetNode(String node){
        return nodes.get(node);
    }
    public Router GetRouter(String router){
        return routers.get(router);
    }
    public Network GetNetwork(String network){
        return networks.get(network);
    }   
    
    public void SendMessage(String src, String dst, String msg) {
        
        Node srcNode = nodes.get(src);
        srcNode.SendMessageToNetwork(msg, nodes.get(dst).getIP());
        
        //TODO: Implement this method
        //throw new NotImplementedException();
    }
    
}
