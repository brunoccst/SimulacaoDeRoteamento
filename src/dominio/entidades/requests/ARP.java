package dominio.entidades.requests;

/**
 *
 * @author 14104872
 */
public class ARP {
    
    private final String ArpRequest = "{0} box {0} : ARP - Who has {1}? Tell {2};";
    private final String ArpReply = "{0} => {1} : ARP - {2} is at {3};";
    
    private String sourceName;
    private String sourceIP;
    private String sourceMAC;
    private String destName;
    private String destIP;
    private MessageType msgType;

    public ARP(String sourceName, String sourceIP, String sourceMAC, String destName, String destIP, MessageType msgType) {
        this.sourceName = sourceName;
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.msgType = msgType;
        this.destName = destName;
        this.sourceMAC = sourceMAC;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestIP() {
        return destIP;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public String getDestName() {
        return destName;
    }

    public String getSourceMAC() {
        return sourceMAC;
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
