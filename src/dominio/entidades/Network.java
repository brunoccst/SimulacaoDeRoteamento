package dominio.entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author brunoccst
 */
public class Network {

    public Network(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt"))) {
            String sCurrentLine;
            String currentLinesCommand = null;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) == '#') {
                    currentLinesCommand = sCurrentLine;
                    continue;
                }
                switch (currentLinesCommand) {
                    case "#NODE":
                        CreateNode(sCurrentLine);
                        break;
                    case "#ROUTER":
                        CreateRouter(sCurrentLine);
                        break;
                    case "#ROUTERTABLE":
                        CreateRouterTableEntry(sCurrentLine);
                        break;
                }
            }
        }
    }

    public void SendMessage(String src, String dst, String msg) {

    }

    private void CreateNode(String node) {

    }

    private void CreateRouter(String router) {

    }

    private void CreateRouterTableEntry(String tableEntry) {

    }
}
