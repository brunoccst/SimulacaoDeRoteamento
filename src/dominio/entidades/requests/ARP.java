package dominio.entidades.requests;

/**
 *
 * @author 14104872
 */
public class ARP extends Message {
    
    private final String ArpRequest = "{0} box {0} : ARP - Who has {1}? Tell {2};";
    private final String ArpReply = "{0} => {1} : ARP - {2} is at {3};";
    
    private String sourceMAC;

    public ARP(String sourceName, String sourceIP, String destIP, MessageType msgType) {
        super(sourceName,sourceIP,destIP,msgType);
    }

    public String getSourceMAC() {
        return sourceMAC;
    }
    
    public void Reply(String sourceName,String sourceMAC){
        String aux = sourceIP;
        this.sourceIP = destIP;
        this.sourceMAC = sourceMAC;
        this.sourceName = sourceName;
        this.destName = this.sourceName;
        this.destIP = aux;
        this.msgType = MessageType.Reply;
    }
    
    @Override
    public String toString()
    {
        if (msgType == MessageType.Reply)
        {
            return String.format(ArpReply, sourceName, destName, sourceIP, sourceMAC);
        }
        else
        {
            return String.format(ArpRequest, sourceName, destIP, sourceIP);
        }
    }
}
