/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio.entidades.requests;

/**
 *
 * @author 14104872
 */
public class ICMP extends Message{

    private final String ICMPEchoRequestReply = "%1$s => %2$s : ICMP - Echo (ping) %7$s (src=%3$s dst=%4$s ttl=%5$s data=%6$s);";
    private final String ICMPEchoProcess = "%1$s rbox %1$s : Received %2$s;";

    private int TTL;
    private String data;
    private boolean isFinal;

    public ICMP(String sourceName, String sourceIP, String destName, String destIP, int TTL, String data, MessageType msgType) {
        super(sourceName,sourceIP,destName,destIP,msgType);
        this.TTL = TTL;
        this.data = data;
        this.msgType = msgType;
        this.isFinal = false;
    }
    
    public boolean isFinal(){
        return isFinal;
    }
    
    public void setFinal(){
        isFinal = true;
    }
    
    public void setNotFinal(){
        isFinal = false;
    }
            
    public int getTTL() {
        return TTL;
    }
    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public void invertSourceDest() {
        String aux = sourceName;
        this.sourceName = destName;
        this.destName = aux;
        aux = sourceIP;
        this.sourceIP = destIP;
        this.destIP = aux;
        msgType = msgType == MessageType.Reply ? MessageType.Request : MessageType.Reply;
    }

    public String processOnNode(String node) {
        return String.format(ICMPEchoProcess, node,data);
    }

    @Override
    public String toString() {
        String type = msgType == MessageType.Reply ? "reply" : "request";
        return String.format(ICMPEchoRequestReply, sourceName, destName, sourceIP, destIP, TTL, data, type);
    }
}
