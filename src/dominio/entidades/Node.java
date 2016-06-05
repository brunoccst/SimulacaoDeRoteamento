package dominio.entidades;

import dominio.entidades.requests.*;

/**
 *
 * @author brunoccst
 */
public class Node extends IMessageManager {

    private final static int DEFAULT_TTL = 8;
    private String name;
    private String MAC;
    private String IP;
    private int MTU;
    private String gateway;
    private Network network;

    private String lastMessage;
    private ARP lastArp;
    private int errorCode;

    public Node() {

    }

    public Node(String nome, String MAC, String IP, int MTU, String gateway) {
        this.name = nome;
        this.MAC = MAC;
        this.IP = IP;
        this.gateway = gateway;
        this.MTU = MTU;
    }

    public int getMTU() {
        return MTU;
    }

    public void setMTU(int MTU) {
        this.MTU = MTU;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void SendMessageToNetwork(String message, String dest) {
        //SEND ARPREQUEST TO NETWORK
        lastMessage = message;
        Send(dest);
    }

    public void Send(String dest) {
        network.Receive(new ARP(this.name, this.IP, dest, MessageType.Request));

    }
    public void ReturnError(int code){
        this.errorCode = code;
    }
    private void ResetErrorCode(){
        this.errorCode = 0;
    }
    private void Send(int TTL) {
        int mtu = network.getMTU();
        ICMP originalICMP = new ICMP(this.name, this.IP, lastArp.getDestName(), lastArp.getSourceIP(), TTL, lastMessage, MessageType.Request),
                nextMessage = originalICMP,
                messageToSend;
        
        //SEND ICMP IN PACKAGES
        while (nextMessage != null) {
            messageToSend = (ICMP) nextMessage.Clone();
            if (nextMessage.getData().length() > MTU) {
                messageToSend.setData(nextMessage.getData().substring(0, mtu));
                nextMessage.setData(nextMessage.getData().substring(mtu));
            } 
            
            else 
                nextMessage = null;
            network.Receive(messageToSend);
            if (errorCode == -1)
                break;
        }
        if (errorCode == -1) {
            ResetErrorCode();
            Send(TTL * 2);
        }
    }

    @Override
    protected void Receive(ARP message) {
        if (message.getMsgType() == MessageType.Reply){
            lastArp = message;
            Send(DEFAULT_TTL);
        }
        else {
            ARP reply = message;
            if(this.IP.equals(reply.getDestIP()))
                reply.Reply(this.name, this.MAC);
            else
                reply = null;
            network.Receive(reply);
        }
    }

    @Override
    protected void Receive(ICMP message) {
    }
}
