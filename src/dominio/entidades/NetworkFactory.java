package dominio.entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 14104872
 */
public class NetworkFactory {
    
    public NetworkFactory()
    {
        
    }
    
    public Network CreateNetwork(String filePath) throws IOException, Exception
    {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Router> routers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            String currentLinesCommand = null;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) == '#') {
                    currentLinesCommand = sCurrentLine;
                    continue;
                }
                switch (currentLinesCommand) {
                    case "#NODE":
                        Node newNode = CreateNode(sCurrentLine);
                        nodes.add(newNode);
                        break;
                    case "#ROUTER":
                        Router newRouter = CreateRouter(sCurrentLine);
                        routers.add(newRouter);
                        break;
                    case "#ROUTERTABLE":
                        CreateRouterTableEntry(sCurrentLine);
                        break;
                }
            }
        }
        
        return new Network();
    }
    
    private Node CreateNode(String nodeString) {
        String[] split = nodeString.split(",");
        Node node = new Node(split[0], split[1], split[2], split[4], split[5]);
        return node;
    }

    private Router CreateRouter(String routerString) throws Exception {
        String[] split = routerString.split(",");
        
        String name = split[0];
        int num_ports = Integer.parseInt(split[1]);
        
        //Check if the number of ports is different from the passed ones
        if ((num_ports * 3) != (split.length - 2)) throw new Exception("Number of ports is different from the passed ones.");
        
        ArrayList<Port> portList = new ArrayList<>();
        
        int portNumber = 1;
        
        for (int i = 2; i < split.length; i += 3)
        {
            String mac = split[i];
            String ip = split[i + 1];
            int mtu = Integer.parseInt(split[i + 2]);
            
            Port newPort = new Port(mac, ip, mtu, portNumber);
            portList.add(newPort);
            
            portNumber++;
        }
        
        Router router = new Router(name, num_ports, portList);
        return router;
    }

    private RouterTableRow CreateRouterTableEntry(String tableEntryString) {
        String[] split = tableEntryString.split(",");
        
        String routerName = split[0];
        String net_dest = split[1];
        String nexthop = split[2];
        int port = Integer.parseInt(split[3]);
        
        RouterTableRow rtr = new RouterTableRow(routerName, net_dest, nexthop, port);
        
        return rtr;
    }
}
