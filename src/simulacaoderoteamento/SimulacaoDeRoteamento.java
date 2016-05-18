package simulacaoderoteamento;

/**
 * @author brunoccst
 * @author matheuswanted
 */
public class SimulacaoDeRoteamento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            Rede rede = new Rede(args[0]);
            rede.SendMessage(args[1], args[2], args[3]);
        }
        catch (Exception e)
        {
            System.err.println("Ocorreu um erro ao executar o aplicativo.\n");
            return;
        }
    }
}
