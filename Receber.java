import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receber implements Runnable{
	private User user;
	
	public Receber(User user) {
		this.user = user;
	}

	public void run(){
		try{
			for(;;){
				recebe(user);
			}
		}catch(Exception e){
			System.out.print("Erro");
			return;
		}
	}

	public void recebe(User user) throws IOException{

		user.setBody("");
		InetAddress ia = InetAddress.getByName("230.0.0.0");;
		int port;
		
		if(user.getTopico() == 0) {
		   port = 4321;
		} else {
		   port = 4322;
		}
     
		MulticastSocket socket = new MulticastSocket(port);
		InetSocketAddress group = new InetSocketAddress(ia , port);
		NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
		  
		socket.joinGroup(group,ni);
		  
		while(true){
			byte[] buffer = new byte[1024];
		    //datagrama com o conteudo 
		    DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
		    socket.receive(packet);
		    
		    // datagrama com a flag 
		    DatagramPacket flag = new DatagramPacket(buffer,buffer.length);
		    socket.receive(flag);
		    
		    //transformando conteudo em string
		    user.setBody(new String(packet.getData()));
		    user.setFlag(new String(flag.getData()));
		     
		    //flag utilizada para indicar que a pessoa quer sair do grupo
		    System.out.println(user.getBody());
		    if (user.getFlag().equals("1")) {
		    	break;
		    }
		 }
		 socket.leaveGroup(group, ni);
		 socket.close();
	}
}
