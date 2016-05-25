package dominio.entidades;

/**
 *
 * @author brunoccst
 */
public class RouterTableRow {

    private String routerName;
    private String net_dest;
    private String next_hop;
    private int port;

    public RouterTableRow() {
    }

    public RouterTableRow(String name, String net_dest, String next_hop, int port) {
        this.routerName = name;
        this.net_dest = net_dest;
        this.next_hop = next_hop;
        this.port = port;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String name) {
        this.routerName = name;
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
