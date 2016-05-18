********************************************************
********************************************************
**                                                    **
** TRABALHO FINAL DE REDES DE COMPUTADORES I - 2016/1 **
**                                                    **
********************************************************
********************************************************



	%%%%%%%%%%%%%%%%%%%%%
	%%%%% DESCRI��O %%%%%
	%%%%%%%%%%%%%%%%%%%%%

O trabalho consiste em desenvolver um simulador de rede. O simulador deve receber como par�metros de execu��o o nome de um arquivo de descri��o de topologia (conforme formato especificado), um n� origem, um n� destino e uma mensagem. O simulador deve apresentar na sa�da as mensagens enviadas pelos n�s e roteadores da topologia conforme o formato estabelecido, considerando o envio de um ping (ICMP Echo Request) do n� origem at� o n� destino contendo a mensagem indicada por par�metro. O simulador dever� realizar a transmiss�o da mensagem atrav�s do ping respeitando a topologia da rede e necessidade de fragmenta��o da mensagem conforme o MTU das interfaces de rede. O simulador considera o MTU somente para fragmentar o campo de dados do datagrama IP (cabe�alhos n�o s�o considerados no valor do MTU).


-------------------------------------------------


	%%%%%%%%%%%%%%%%%%%%%
	%%%%% FORMATA��O %%%%
	%%%%%%%%%%%%%%%%%%%%%

@@ Arquivo de descri��o de topologia @@

#NODE
<node_name>,<MAC>,<IP>,<MTU>,<gateway>

#ROUTER
<router_name>,<num_ports>,<MAC0>,<IP0>,<MTU0>,<MAC1>,<IP1>,<MTU1>,<MAC2>,<IP2>,<MTU2> �

#ROUTERTABLE
<router_name>,<net_dest>,<nexthop>,<port>


--------------------------


@@ Formato de sa�da @@

Pacotes ARP Request: <src_name> box <src_name> : ARP - Who has <dst_IP>? Tell <src_IP>;
Pacotes ARP Reply: <src_name> => <dst_name> : ARP - <src_IP> is at <src_MAC>;
Pacotes ICMP Echo Request: <src_name> => <dst_name> : ICMP - Echo (ping) request (src=<src_IP> dst=<dst_IP> ttl=<TTL> data=<msg>);
Pacotes ICMP Echo Reply: <src_name> => <dst_name> : ICMP - Echo (ping) reply (src=<src_IP> dst=<dst_IP> ttl=<TTL> data=<msg>);
Processamento final do ICMP Echo Request/Reply no n�: <dst_name> rbox <dst_name> : Received <msg>;


--------------------------


@@ Modo de execu��o do simulador @@

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

Exemplos de execu��o:
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
	%%%% DETALHES PARA A CONSTRU��O DO SIMULADOR %%%%%
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


- TTL inicial dos pacotes IP deve ser igual a 8

- A topologia somente utilizar� redes usando o modelo de classes (A, B ou C), isto �, n�o ser�o utilizadas subredes

- A topologia n�o apresentar� erros de configura��o (loops, endere�os errados)

- O simulador deve ser executado a partir de um terminal por linha de comando de acordo com o exemplo apresentado - n�o deve ser necess�rio utilizar uma IDE para executar o simulador!!!

- O simulador pode ser implementado em qualquer linguagem

- A entrada e sa�da devem respeitar EXATAMENTE os formatos apresentados

- O formato de sa�da � baseado na linguagem MsGenny. Sugere-se verificar se a sa�da est� correta atrav�s do site https://sverweij.github.io/mscgen_js. Usar o cabe�alho �wordwraparcs=true,hscale=2.0;� para facilitar a visualiza��o.