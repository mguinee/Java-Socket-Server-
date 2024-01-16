package msg;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import databufferizer.DataBufferizer;

public class Send {
	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals("-h")) {
			System.out.println("usage : java SendMessage <adresse> <port> <loginSource> <loginDest>");
			return;
		}
		
		if (args.length < 4) {
			System.out.println("Not enough arguments provided"
					+ "\n" + "usage : java SendMessage <adresse> <port> <loginSource> <loginDest>");
			return;
		}
		
		try {
			InetAddress adress= InetAddress.getByName(args[0]);
			int port = Integer.parseInt(args[1]);
			String login_src = args[2];
			String login_dest = args[3];
			
			DatagramSocket socket = new DatagramSocket();
			DataBufferizer data = new DataBufferizer();
			byte[] buffer = new byte[1024];
			
			Scanner entry = new Scanner(System.in);
			StringBuilder mot = new StringBuilder();
			String line;

			while (!(line = entry.nextLine()).equals("-1")) {
				mot.append(line).append("\n");
			}

			String message = mot.toString();


			
			data.writeByte((byte)4,buffer,0);
			data.writeByte((byte)login_src.length(), buffer, 1);
			data.writeByteArray(login_src.getBytes(), buffer, 2, login_src.length());
			data.writeByte((byte)login_dest.length(), buffer, 18);
			data.writeByteArray(login_dest.getBytes(), buffer, 19, login_dest.length());
			data.writeShort((short)message.length(), buffer, 35);
			data.writeByteArray(message.getBytes(), buffer, 36, mot.length());
			
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length,adress,port);
			socket.send(packet);
			System.out.println("Datagram sent to "+args[0]+":"+port);
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
