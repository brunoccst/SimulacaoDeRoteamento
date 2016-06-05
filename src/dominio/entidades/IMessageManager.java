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
    
    public void Receive(Message message){
        if(message instanceof ARP)
            Receive((ARP)message);
        else
            Receive((ICMP)message);
    }
    protected abstract void Receive(ARP message);
    protected abstract void Receive(ICMP message);
}
