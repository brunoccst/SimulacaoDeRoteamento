package apresentacao;

import dominio.entidades.Network;
import dominio.entidades.Topology;
import dominio.entidades.TopologyFactory;
import java.io.IOException;

/**
 * @author brunoccst
 * @author matheuswanted
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException,Exception {
        
        //TODO: Add try catch
            String filePath = args[0];
            TopologyFactory topFactory = new TopologyFactory();
            Topology topology = topFactory.CreateTopology(filePath);
            
            String origin = args[1];
            String destiny = args[2];
            String message = args[3];
            
            topology.SendMessage(origin, destiny, message);
            
            System.out.println("Processo finalizado.\n");
    }
}
