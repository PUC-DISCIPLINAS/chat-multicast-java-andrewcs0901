package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import server.groups.GroupDAO;

public class MulticastServer {
		// args  provê o conteúdo da mensagem e o endereço  do grupo multicast (p. ex. "228.5.6.7")
		
	static MulticastSocket M_SOCKET;
	static int SOCKET = 6789;
	static int BUFFER_LENGTH = 1000;
	static GroupDAO groups = new GroupDAO(); 

	MulticastServer() {
		try {
			System.out.println("Servidor: ouvindo porta TCP/6789.");
			M_SOCKET = new MulticastSocket(SOCKET);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		M_SOCKET.close();
	}

	public void sendMessage(String message, String group) throws UnknownHostException {
		byte[] messageByte = message.getBytes();
		InetAddress groupIp = InetAddress.getByName(group);
		DatagramPacket messageOut = new DatagramPacket(messageByte, messageByte.length, groupIp, SOCKET);
		try {
			M_SOCKET.send(messageOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
