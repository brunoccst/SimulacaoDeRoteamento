package dominio.entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author 14104872
 */
public class TopologyFactory {

    public TopologyFactory() {

    }

    public Topology CreateTopology(String filePath) throws IOException, Exception {
        Topology top = new Topology();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            String currentLinesCommand = "";

            Node newNode;
            Router newRouter;
            RouterTableRow newRow;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) == '#') {
                    currentLinesCommand = sCurrentLine;
                    continue;
                }
                switch (currentLinesCommand) {
                    case "#NODE":
                        newNode = CreateNode(sCurrentLine);
                        top.AddNode(newNode);
                        AddNodeToNetwork(newNode, top);
                        break;
                    case "#ROUTER":
                        newRouter = CreateRouter(sCurrentLine);
                        top.AddRouter(newRouter);
                        AddRouterToNetwork(newRouter, top);
                        break;
                    case "#ROUTERTABLE":
                        newRow = CreateRouterTableEntry(sCurrentLine);
                        top.GetRouter(newRow.getRouterName()).AddRouterTableEntry(newRow);
                        break;
                }
            }
        }

        return top;
    }

    private void AddNodeToNetwork(Node node, Topology top) {
        String networkIP = TranslateIP(node.getIP());
        Network network = top.GetNetwork(networkIP);

        //If network doesn't exists, create it
        if (network == null) {

            network = new Network(networkIP, node.getMTU());
            top.AddNetwork(network);
        }

        network.AddNode(node);
    }

    private void AddRouterToNetwork(Router router, Topology top) {
        
        router.getPortsList().forEach((port)->
        {
            String networkIP;
            //Get the network by the translated port IP
            networkIP = TranslateIP(port.getIP());
            Network network = top.GetNetwork(networkIP);

            //If network doesn't exists, create it
            if (network == null) {

                network = new Network(networkIP, port.getMTU());
                top.AddNetwork(network);
            }

            //Add cross-reference of networks and ports
            network.AddPort(port);
            port.setNetwork(network);
        });

    }

    private Node CreateNode(String nodeString) {
        String[] split = nodeString.split(",");
        Node node = new Node(split[0], split[1], split[2], Integer.parseInt(split[3]), split[4]);
        return node;
    }

    private Router CreateRouter(String routerString) throws Exception {
        String[] split = routerString.split(",");

        String name = split[0];
        int num_ports = Integer.parseInt(split[1]);

        //Check if the number of ports is different from the passed ones
        if ((num_ports * 3) != (split.length - 2)) {
            throw new Exception("Number of ports is different from the passed ones.");
        }
        Router router = new Router(name, num_ports);

        int portNumber = 0,mtu;
        String mac, ip;
        for (int i = 2; i < split.length; i += 3) {
            mac = split[i];
            ip = split[i + 1];
            mtu = Integer.parseInt(split[i + 2]);

            router.addPort(new Port(mac, ip, mtu, portNumber));
        }

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

    private String TranslateIP(String ip) {
        //TODO: Check why the split isn't working
        String[] splitted = ip.split(".");

        int firstPart = Integer.parseInt(splitted[0]);
        if (firstPart <= 127) {
            //Classe A
            return splitted[0] + ".0.0.0";
        } else if (firstPart <= 191) {
            //Classe B
            return splitted[0] + splitted[1] + ".0.0";
        } else {
            //Classe C
            return splitted[0] + splitted[1] + splitted[2] + ".0";
        }
    }
}
