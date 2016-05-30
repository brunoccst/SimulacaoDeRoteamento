/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio.entidades;

import java.util.Map;
import java.util.TreeMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        //TODO: Implement this method
        throw new NotImplementedException();
    }
    
}