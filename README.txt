********************************************************
********************************************************
**                                                    **
** TRABALHO FINAL DE REDES DE COMPUTADORES I - 2016/1 **
**                                                    **
********************************************************
********************************************************



	%%%%%%%%%%%%%%%%%%%%%
	%%%%% DESCRIÇÃO %%%%%
	%%%%%%%%%%%%%%%%%%%%%

O trabalho consiste em desenvolver um simulador de rede. O simulador deve receber como parâmetros de execução o nome de um arquivo de descrição de topologia (conforme formato especificado), um nó origem, um nó destino e uma mensagem. O simulador deve apresentar na saída as mensagens enviadas pelos nós e roteadores da topologia conforme o formato estabelecido, considerando o envio de um ping (ICMP Echo Request) do nó origem até o nó destino contendo a mensagem indicada por parâmetro. O simulador deverá realizar a transmissão da mensagem através do ping respeitando a topologia da rede e necessidade de fragmentação da mensagem conforme o MTU das interfaces de rede. O simulador considera o MTU somente para fragmentar o campo de dados do datagrama IP (cabeçalhos não são considerados no valor do MTU).


-------------------------------------------------


	%%%%%%%%%%%%%%%%%%%%%
	%%%%% FORMATAÇÃO %%%%
	%%%%%%%%%%%%%%%%%%%%%

@@ Arquivo de descrição de topologia @@

#NODE
<node_name>,<MAC>,<IP>,<MTU>,<gateway>

#ROUTER
<router_name>,<num_ports>,<MAC0>,<IP0>,<MTU0>,<MAC1>,<IP1>,<MTU1>,<MAC2>,<IP2>,<MTU2> …

#ROUTERTABLE
<router_name>,<net_dest>,<nexthop>,<port>


--------------------------


@@ Formato de saída @@

Pacotes ARP Request: <src_name> box <src_name> : ARP - Who has <dst_IP>? Tell <src_IP>;
Pacotes ARP Reply: <src_name> => <dst_name> : ARP - <src_IP> is at <src_MAC>;
Pacotes ICMP Echo Request: <src_name> => <dst_name> : ICMP - Echo (ping) request (src=<src_IP> dst=<dst_IP> ttl=<TTL> data=<msg>);
Pacotes ICMP Echo Reply: <src_name> => <dst_name> : ICMP - Echo (ping) reply (src=<src_IP> dst=<dst_IP> ttl=<TTL> data=<msg>);
Processamento final do ICMP Echo Request/Reply no nó: <dst_name> rbox <dst_name> : Received <msg>;


--------------------------


@@ Modo de execução do simulador @@

$ simulador <topologia> <origem> <destino> <mensagem>


--------------------------


@@ EXEMPLO @@


Arquivo topologia.txt

#NODE
n1,00:00:00:00:00:01,192.168.0.2,5,192.168.0.1
n2,00:00:00:00:00:02,192.168.0.3,5,192.168.0.1
n3,00:00:00:00:00:03,192.168.1.2,5,192.168.1.1
n4,00:00:00:00:00:04,192.168.1.3,5,192.168.1.1
#ROUTER
r1,2,00:00:00:00:00:05,192.168.0.1,5,00:00:00:00:00:06,192.168.1.1,5
#ROUTERTABLE
r1,192.168.0.0,0.0.0.0,0
r1,192.168.1.0,0.0.0.0,1

Exemplos de execução:
$ simulador topologia.txt n1 n2 hello
n1 box n1 : ARP - Who has 192.168.0.3? Tell 192.168.0.2;
n2 => n1 : ARP - 192.168.0.3 is at 00:00:00:00:00:02;
n1 => n2 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.0.3 ttl=8 data=hello);
n2 rbox n2 : Received hello;
n2 => n1 : ICMP - Echo (ping) reply (src=192.168.0.3 dst=192.168.0.2 ttl=8 data=hello);
n1 rbox n1 : Received hello;

$ simulador topologia.txt n1 n2 helloworld
n1 box n1 : ARP - Who has 192.168.0.3? Tell 192.168.0.2;
n2 => n1 : ARP - 192.168.0.3 is at 00:00:00:00:00:02;
n1 => n2 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.0.3 ttl=8 data=hello);
n1 => n2 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.0.3 ttl=8 data=world);
n2 rbox n2 : Received helloworld;
n2 => n1 : ICMP - Echo (ping) reply (src=192.168.0.3 dst=192.168.0.2 ttl=8 data=hello);
n2 => n1 : ICMP - Echo (ping) reply (src=192.168.0.3 dst=192.168.0.2 ttl=8 data=world);
n1 rbox n1 : Received helloworld;

$ simulador topologia.txt n1 n3 hello
n1 box n1 : ARP - Who has 192.168.0.1? Tell 192.168.0.2;
r1 => n1 : ARP - 192.168.0.1 is at 00:00:00:00:00:05;
n1 => r1 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=8 data=hello);
r1 box r1 : ARP - Who has 192.168.1.2? Tell 192.168.1.1;
n3 => r1 : ARP - 192.168.1.2 is at 00:00:00:00:00:03;
r1 => n3 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=7 data=hello);
n3 rbox n3 : Received hello;
n3 => r1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=8 data=hello);
r1 => n1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=7 data=hello);
n1 rbox n1 : Received hello;

$ simulador topologia.txt n1 n3 helloworld
n1 box n1 : ARP - Who has 192.168.0.1? Tell 192.168.0.2;
r1 => n1 : ARP - 192.168.0.1 is at 00:00:00:00:00:05;
n1 => r1 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=8 data=hello);
n1 => r1 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=8 data=world);
r1 box r1 : ARP - Who has 192.168.1.2? Tell 192.168.1.1;
n3 => r1 : ARP - 192.168.1.2 is at 00:00:00:00:00:03;
r1 => n3 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=7 data=hello);
r1 => n3 : ICMP - Echo (ping) request (src=192.168.0.2 dst=192.168.1.2 ttl=7 data=world);
n3 rbox n3 : Received helloworld;
n3 => r1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=8 data=hello);
n3 => r1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=8 data=world);
r1 => n1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=7 data=hello);
r1 => n1 : ICMP - Echo (ping) reply (src=192.168.1.2 dst=192.168.0.2 ttl=7 data=world);
n1 rbox n1 : Received helloworld;


-------------------------------------------------


	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	%%%% DETALHES PARA A CONSTRUÇÃO DO SIMULADOR %%%%%
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


- TTL inicial dos pacotes IP deve ser igual a 8

- A topologia somente utilizará redes usando o modelo de classes (A, B ou C), isto é, não serão utilizadas subredes

- A topologia não apresentará erros de configuração (loops, endereços errados)

- O simulador deve ser executado a partir de um terminal por linha de comando de acordo com o exemplo apresentado - não deve ser necessário utilizar uma IDE para executar o simulador!!!

- O simulador pode ser implementado em qualquer linguagem

- A entrada e saída devem respeitar EXATAMENTE os formatos apresentados

- O formato de saída é baseado na linguagem MsGenny. Sugere-se verificar se a saída está correta através do site https://sverweij.github.io/mscgen_js. Usar o cabeçalho “wordwraparcs=true,hscale=2.0;” para facilitar a visualização.