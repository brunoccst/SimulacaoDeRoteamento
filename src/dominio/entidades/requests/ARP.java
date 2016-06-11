package dominio.entidades.requests;

/**
 *
 * @author 14104872
 */
public class ARP extends Message {
    
    private final String ArpRequest = "%1$s box %1$s : ARP - Who has %2$s? Tell %3$s;";
    private final String ArpReply = "%1$s => %2$s : ARP - %3$s is at %4$s;";
    
    private String sourceMAC;
    private Boolean answered = false;
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
        this.destName = this.sourceName;
        this.sourceName = sourceName;
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
