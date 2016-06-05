/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio.entidades.requests;

/**
 *
 * @author Matheus
 */
public class Message implements Cloneable {

    protected String sourceName;
    protected String sourceIP;
    protected String destName;
    protected String destIP;
    protected MessageType msgType;

    protected Message(String sourceName, String sourceIP, String destName, String destIP, MessageType msgType) {
        this.sourceName = sourceName;
        this.sourceIP = sourceIP;
        this.destName = destName;
        this.destIP = destIP;
        this.msgType = msgType;
    }

    protected Message(String sourceName, String sourceIP, String destIP, MessageType msgType) {
        this.sourceName = sourceName;
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.msgType = msgType;
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

    public Message Clone() {
        try {
            return (Message) (this instanceof ICMP ? ((ICMP) this).clone() : ((ARP) this).clone());
        } catch (CloneNotSupportedException c) {
            return null;
        }
    }
}
