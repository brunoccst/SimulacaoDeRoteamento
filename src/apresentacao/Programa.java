package apresentacao;

import dominio.entidades.Network;
import dominio.entidades.NetworkFactory;
import java.io.IOException;

/**
 * @author brunoccst
 * @author matheuswanted
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            String filePath = args[0];
            
            NetworkFactory netFactory = new NetworkFactory();
            Network network = netFactory.CreateNetwork(filePath);
            
            String origin = args[1];
            String destiny = args[2];
            String message = args[3];
            
            network.SendMessage(origin, destiny, message);
            
            System.out.println("Processo finalizado.\n");
        }
        catch (IOException e)
        {
            System.err.println("Ocorreu um erro ao importar os dados do arquivo.\n");
            return;
        }
        catch (Exception e)
        {
            System.err.println("Ocorreu um erro ao executar o aplicativo.\n");
            return;
        }
    }
}
