package dominio.entidades;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author brunoccst
 */
public class Router {
    
    private String name;
    private int num_ports;
    private List<Node> node;
    private Map<String, RouterTableRow> routerTable;

    public Router() {
        routerTable = new TreeMap<>();
    }

    public Router(String name, int num_ports, List<Node> node) {
        this.name = name;
        this.num_ports = num_ports;
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_ports() {
        return num_ports;
    }

    public void setNum_ports(int num_ports) {
        this.num_ports = num_ports;
    }

    public List<Node> getNode() {
        return node;
    }

    public void setNode(List<Node> node) {
        this.node = node;
    }

    public Map<String, RouterTableRow> getRouterTable() {
        return routerTable;
    }
    
    public void AddRouterTableEntry(RouterTableRow newEntry)
    {
        routerTable.put(newEntry.getNet_dest(), newEntry);
    }
    
}
