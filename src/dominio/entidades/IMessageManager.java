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
    
    public void Receive(Message message, String gatewayDefault){
        if(message instanceof ARP)
            Receive((ARP)message,gatewayDefault);
        else
            Receive((ICMP)message,gatewayDefault);
    }
    protected abstract void Receive(ARP message, String gatewayDefault);
    protected abstract void Receive(ICMP message, String gatewayDefault);
}
