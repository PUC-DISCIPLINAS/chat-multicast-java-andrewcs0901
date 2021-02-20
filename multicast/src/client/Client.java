package client;

import java.net.MulticastSocket;
import java.net.UnknownHostException;

import util.DataCollection;
import util.EAcceptedOptions;
import util.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	private static MulticastSocket mSocket;
	private static DatagramSocket dSocket;
	private static InetAddress groupIp;
	private static String ip;
	static String userName;

	private static final int MULTICAST_SOCKET = 6789;
	private static final int UDP_SOCKET = 6790;
	private static final int BUFFER_LENGTH = 1000;

	Client(String userName) {
		try {
			mSocket = new MulticastSocket(MULTICAST_SOCKET);
			dSocket = new DatagramSocket();
			Client.userName = userName;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		if(groupIp != null) {
			leaveGroup();
		}
		mSocket.close();
	}

	public void handleSendMessage(String message) {

		try {
			if (message.length() > 0) {
				String[] commandSplit = message.split(" ", 3);
				String formatedMessage = commandSplit[0];
				Message messageOut;
				if (message.charAt(0) == '/') {
					switch (commandSplit[0]) {
					case EAcceptedOptions.LIST_USERS:
						formatedMessage += " " + ip;
						break;
					case EAcceptedOptions.IN:
						System.out.println(commandSplit.length);
						if (commandSplit.length != 2) {
							System.out.println("Código de grupo vazio");
							return;
						}
						formatedMessage += " " + userName + " " + commandSplit[1];
						ip = commandSplit[1];
						break;
					case EAcceptedOptions.LEAVE:
						formatedMessage += " " + userName + " " + ip;
						break;
					}
					messageOut = new Message(userName, formatedMessage);
					sendMessage(new Packet(dSocket, messageOut, InetAddress.getByName("localhost"), UDP_SOCKET));
					receiveMessages(dSocket);
				} else {
					messageOut = new Message(userName, message);
					sendMessage(new Packet(mSocket, messageOut, InetAddress.getByName(ip), MULTICAST_SOCKET));
				}
			}

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}

	}

	public void handleReceivedMessage(DatagramPacket messageIn) {

		try (ByteArrayInputStream bInputStream = new ByteArrayInputStream(messageIn.getData());
				ObjectInput objInput = new ObjectInputStream(bInputStream)) {
			switch (messageIn.getPort()) {
			case UDP_SOCKET:
				DataCollection data = (DataCollection) objInput.readObject();
				String type = data.getType();
				System.out.println("Response:" + type);

				if (type.equals(EAcceptedOptions.LIST_GROUPS) || type.equals(EAcceptedOptions.LIST_USERS)
						|| type.equals(EAcceptedOptions.INVALID)) {
					if (data.getData() != null && data.getData().size() > 0)
						for (String i : data.getData())
							System.out.println(i);
					else
						System.out.println("{ }");
					return;
				}
				if (type.equals(EAcceptedOptions.IN)) {
					System.out.println(data.getData());
					joinGroup(ip);
					return;
				}
				if (type.equals(EAcceptedOptions.LEAVE)) {
					leaveGroup();
					return;
				}
				break;
			case MULTICAST_SOCKET:
				Message msg = (Message) objInput.readObject();
				System.out.println(msg);
				break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void joinGroup(String ip) throws UnknownHostException {
		Client.ip = ip;
		groupIp = InetAddress.getByName(ip);
		System.out.println(groupIp.getHostName());
		try {
			mSocket.joinGroup(groupIp);
			receiveMessages(mSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void leaveGroup() {
		try {
			handleSendMessage(EAcceptedOptions.LEAVE);
			mSocket.leaveGroup(groupIp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(Packet p) {
		try (ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objOutput = new ObjectOutputStream(bOutputStream)) {
			objOutput.writeObject(p.message);
			objOutput.flush();
			byte[] messageByte = bOutputStream.toByteArray();
			DatagramPacket messageOut = new DatagramPacket(messageByte, messageByte.length, p.destination, p.port);
			try {
				p.socket.send(messageOut);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void receiveMessages(DatagramSocket socket) {
		new Thread(() -> {
			byte[] buffer;
			try {
				while (true) {
					buffer = new byte[BUFFER_LENGTH];
					DatagramPacket messageIn = new DatagramPacket(buffer, BUFFER_LENGTH);
					socket.receive(messageIn);
					handleReceivedMessage(messageIn);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}).start();
	}

	private class Packet {
		DatagramSocket socket;
		Message message;
		InetAddress destination;
		int port;

		public Packet(DatagramSocket socket, Message message, InetAddress destination, int port) {
			this.socket = socket;
			this.message = message;
			this.destination = destination;
			this.port = port;
		}
	}

}
