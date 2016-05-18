package dominio.entidades;

/**
 *
 * @author brunoccst
 */
public class RouterTableRow {

    private String name;
    private String net_dest;
    private String next_hop;
    private int port;

    public RouterTableRow() {
    }

    public RouterTableRow(String name, String net_dest, String next_hop, int port) {
        this.name = name;
        this.net_dest = net_dest;
        this.next_hop = next_hop;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNet_dest() {
        return net_dest;
    }

    public void setNet_dest(String net_dest) {
        this.net_dest = net_dest;
    }

    public String getNext_hop() {
        return next_hop;
    }

    public void setNext_hop(String next_hop) {
        this.next_hop = next_hop;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
}
