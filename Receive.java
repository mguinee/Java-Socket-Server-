package msg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.util.Date;
import databufferizer.DataBufferizer;


public class Receive {
	 public static void main(String args[]) throws IOException {
		 if (args.length !=1 || args[0].equals("-h")) {
				System.out.println("usage : java RecvMessage.java <port>");
				return;
		 }
		 	
		 int port = Integer.parseInt(args[0]);
	     DatagramSocket socket = new DatagramSocket(port);
	     byte[] buffer = new byte[1024];
	     DataBufferizer data = new DataBufferizer();
	     
	     //récupération du datagramme sur le socket
	     DatagramPacket packet = new DatagramPacket(buffer, 52);
	     socket.receive(packet);
	     socket.close();
	     
	     //lecture des informations sur le datagramme
	     byte[] log_src_byte = data.readByteArray(buffer, 2, data.readByte(buffer,1));
         String log_src = new String(log_src_byte);
         byte[] log_dest_byte = data.readByteArray(buffer,19, data.readByte(buffer,18));
         String log_dest = new String(log_dest_byte);      
         
         byte[] message_byte = data.readByteArray(buffer,36, data.readShort(buffer,35));
         String message = new String(message_byte,Charset.forName("UTF-8"));  
         
         //affichage des informations
         System.out.println("From : " + log_src);
         System.out.println("To : " + log_dest);
         System.out.println("Date : " + new Date(System.currentTimeMillis()));
         System.out.println("\n" + message);

         
	 }
}
