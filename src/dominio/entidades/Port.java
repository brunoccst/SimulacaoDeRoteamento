package dominio.entidades;

/**
 *
 * @author brunoccst
 */
public class Port {
    
    private String MAC;
    private String IP;
    private int MTU;
    private int portNumber;

    public Port()
    {
        
    }
    
    public Port(String MAC, String IP, int MTU, int portNumber) {
        this.MAC = MAC;
        this.IP = IP;
        this.MTU = MTU;
        this.portNumber = portNumber;
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

    public int getMTU() {
        return MTU;
    }

    public void setMTU(int MTU) {
        this.MTU = MTU;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
