import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class TheNews {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String user = JOptionPane.showInputDialog("Username :");

		String[] options = { "Esportes", "Entretenimento"};
	    int topic = JOptionPane.showOptionDialog(null, "Qual chat você deseja Entrar?", "Select one:", 
	                                                      0, 2, null, options, options[0]);
	    if (topic == 0) {
	      JOptionPane.showMessageDialog(null, "você entrou em Esportes!");
	    }
	    if (topic == 1) { 
	      JOptionPane.showMessageDialog(null, "voce entrou em Entretenimento!");
	    }
	    
		User u1 = new User(user, topic);
		
		Receber r = new Receber(u1);
		new Thread(r).start();

		InetAddress grupo = InetAddress.getByName("230.0.0.0");;
		MulticastSocket socket = new MulticastSocket();
		
		//armazenar conteudo e flag
		byte[] sendP = new byte[1024]; 
		byte[] sendF = new byte[1024]; 
	  
		int port;
		String mensagem = " ";
		if(u1.getTopico() == 0) {
		   port = 4321;
		}
		else{
		   port = 4322;
		}
	   
		while(true){
			String sign = "0";
			String formato = "";
			mensagem = JOptionPane.showInputDialog(u1.getNome() + " Digite a Mensagem :");
			
			if(mensagem.equals("SAIR")) {
			   	formato=  "[" + dtf.format(LocalDateTime.now()) + "] " + u1.getNome() + " :" +  "saiu do grupo";
				sendP = formato.getBytes();	   
				   
				DatagramPacket packet = new DatagramPacket(sendP, sendP.length, grupo, port);
				socket.send(packet);
				sign = "1";
				sendF = sign.getBytes();
				DatagramPacket flag = new DatagramPacket(sendF, sendF.length, grupo, port);
				socket.send(flag);

		   		break;
			}

			formato = "[" + dtf.format(LocalDateTime.now()) + "] " + u1.getNome() + " :" + mensagem;
			sendP = formato.getBytes();	   
		   
			DatagramPacket pacote = new DatagramPacket(sendP, sendP.length, grupo, port);
			socket.send(pacote);
			sendF = sign.getBytes();
			DatagramPacket flag = new DatagramPacket(sendF, sendF.length, grupo, port);
			socket.send(flag);
				  
		}
		socket.close();
	}

}
