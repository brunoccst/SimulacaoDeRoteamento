package dominio.entidades;

import java.util.ArrayList;

/**
 *
 * @author brunoccst
 */
public class Node {
    
    private String name;
    private String MAC;
    private String IP;
    private String gateway;
    private Network network;

    public Node() {
        
    }

    public Node(String nome, String MAC, String IP, String gateway, Network network) {
        this.name = nome;
        this.MAC = MAC;
        this.IP = IP;
        this.gateway = gateway;
        this.network = network;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
    
    public void SendMessageToNetwork(String message)
    {
        int mtu = network.getMTU();
        
        ArrayList<String> packages = new ArrayList<>();
        for (String pkg : packages)
        {
            network.SendMessage(message);
        }
    }
}
