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
    private List<Port> ports;
    private Map<String, RouterTableRow> routerTable;

    public Router() {
        routerTable = new TreeMap<>();
    }

    public Router(String name, int num_ports, List<Port> ports) {
        this();
        this.name = name;
        this.num_ports = num_ports;
        this.ports = ports;
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

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
    
    public Map<String, RouterTableRow> getRouterTable() {
        return routerTable;
    }
    
    public void AddRouterTableEntry(RouterTableRow newEntry)
    {
        routerTable.put(newEntry.getNet_dest(), newEntry);
    }
    
}
