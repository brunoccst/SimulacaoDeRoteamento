package dominio.entidades;

/**
 *
 * @author brunoccst
 */
public class Request {
    private final String ArpRequest = "{0} box {0} : ARP - Who has {1}? Tell {2};";
    private final String ArpReply = "{0} => {1} : ARP - {2} is at {3};";
    private final String ICMPEchoRequest = "{0} => {1} : ICMP - Echo (ping) request (src={2} dst={3} ttl={4} data={5});";
    private final String ICMPEchoReply = "{0} => {1} : ICMP - Echo (ping) reply (src={2} dst={3} ttl={4} data={5});";
    private final String ICMPEchoProcess = "{0} rbox {0} : Received {1};";
}
