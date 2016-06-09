/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio.entidades;

import dominio.entidades.requests.*;

/**
 *
 * @author Matheus
 */
public abstract class IMessageManager {
    public int errorCode;
    protected static final int TIME_EXCEEDED_CODE = -1;
    protected static final int NOT_FOUND_HOST = -2;
    protected static final int NOT_FOUND_NEXT_HOP = -3;
    public void Receive(Message message, String gatewayDefault, String sender){
        if(message instanceof ARP)
            Receive((ARP)message,gatewayDefault,sender);
        else
            Receive((ICMP)message,gatewayDefault,sender);
    }
    
    public void ReturnError(int code){
        this.errorCode = code;
    }
    protected void ResetErrorCode(){
        this.errorCode = 0;
    }
    protected abstract void Receive(ARP message, String gatewayDefault, String sender);
    protected abstract void Receive(ICMP message, String gatewayDefault, String sender);
}
