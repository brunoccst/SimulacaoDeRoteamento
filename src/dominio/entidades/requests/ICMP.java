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

    private final String ICMPEchoRequestReply = "{0} => {1} : ICMP - Echo (ping) {6} (src={2} dst={3} ttl={4} data={5});";
    private final String ICMPEchoProcess = "{0} rbox {0} : Received {1};";

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
        return String.format(ICMPEchoProcess, node);
    }

    @Override
    public String toString() {
        String type = msgType == MessageType.Reply ? "reply" : "request";
        return String.format(ICMPEchoRequestReply, sourceName, destName, sourceIP, destIP, TTL, data, type);
    }
}
