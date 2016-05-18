package dominio.entidades;

/**
 *
 * @author brunoccst
 */
public class Node {
    
    private String name;
    private String MAC;
    private String IP;
    private String MTU;
    private String gateway;

    public Node() {
        
    }

    public Node(String nome, String MAC, String IP, String MTU, String gateway) {
        this.name = nome;
        this.MAC = MAC;
        this.IP = IP;
        this.MTU = MTU;
        this.gateway = gateway;
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

    public String getMTU() {
        return MTU;
    }

    public void setMTU(String MTU) {
        this.MTU = MTU;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
}
