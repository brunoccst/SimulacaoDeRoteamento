import dominio.entidades.Network;
import dominio.entidades.Node;
import dominio.entidades.Port;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brunoccst
 */
public class NetworkTest {
    
    private Network network;
    
    public NetworkTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        network = new Network("192.168.0.1");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void TestAddNode() {
        Node node = new Node("n1","00:00:00:00:00:01","192.168.0.2","5","192.168.0.1");
        network.AddNode(node);
        Node searchedNode = network.GetNode(node.getName());
        
        assertEquals(node, searchedNode);
    }
    
    @Test
    public void TestAddPort() {
        Port port = new Port("00:00:00:00:00:05","192.168.0.1", 5, 1);
        network.AddPort(port);
        Port searchedPort = network.GetPort(port.getIP());
        
        assertEquals(port, searchedPort);
    }
}
