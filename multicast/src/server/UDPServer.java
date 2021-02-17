package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import server.groups.GroupDAO;
import util.DataCollection;
import util.EAcceptedOptions;
import util.Message;
import util.MultiCastIpGenerator;

class UDPServer {

	ServerSocket listenSocket = null;
	DatagramSocket aSocket = null;
	GroupDAO groups = new GroupDAO();
	EAcceptedOptions eAcceptedOptions;

	public UDPServer() {
		try {

			int serverPort = 6790;

			aSocket = new DatagramSocket(serverPort);

			System.out.println("Servidor: ouvindo porta TCP/6790.");
			byte[] buffer;

			while (true) {
				buffer = new byte[1000];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				request(request);
			}

		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		} finally {
			if (listenSocket != null)
				try {
					listenSocket.close();
					System.out.println("Servidor: liberando porta TCP/6790.");
				} catch (IOException e) {
				}
		}

	}

	public void request(DatagramPacket request) {
		new Thread(() -> {
			try (ByteArrayInputStream bInputStream = new ByteArrayInputStream(request.getData());
					ObjectInput objInput = new ObjectInputStream(bInputStream)) {
				Message message = (Message) objInput.readObject();
				System.out.println("Servidor: recebido \'" + message + "\'.");
				try {
					responseHandler(request, message.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("UDPServer.request " + e1.getMessage());
			} catch (ClassNotFoundException e1) {
				System.out.println("UDPServer.request " + e1.getMessage());
			}
		}).start();
	}
	
	public void responseHandler(DatagramPacket request, String message) throws Exception {
		String[] commandSplit = message.split(" ", 3);
		System.out.println("Test");
		switch(commandSplit[0]) {
		case EAcceptedOptions.LIST_GROUPS:
			response(request, groups.listAll(), EAcceptedOptions.LIST_GROUPS);
			break;
		case EAcceptedOptions.LIST_USERS: 
			response(request, groups.getUsers(commandSplit[1]), EAcceptedOptions.LIST_USERS);
			break;
		case EAcceptedOptions.CREATE:
			String ip = MultiCastIpGenerator.nextIp();
			response(request, groups.addGroup(ip), EAcceptedOptions.CREATE + " ip -> " + ip);
			break;
		case EAcceptedOptions.IN:
			response(request, groups.addUser(commandSplit[1],commandSplit[2]), EAcceptedOptions.IN);
			break;
		case EAcceptedOptions.LEAVE:
			groups.deleteUser(commandSplit[1],commandSplit[2]);
			response(request, new ArrayList<String>(), EAcceptedOptions.LEAVE);
			break;
		default:
			List<String> validValues = Arrays.asList(EAcceptedOptions.values());
			response(request, new ArrayList<String>(validValues), EAcceptedOptions.INVALID);
		}
	}

	public void response(DatagramPacket request, List<String> dataList, String type) {
		new Thread(() -> {
			try (ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
					ObjectOutputStream objOutput = new ObjectOutputStream(bOutputStream)) {
				DataCollection data = new DataCollection(dataList, type);
				objOutput.writeObject(data);
				objOutput.flush();
				byte[] response = bOutputStream.toByteArray();
				DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(),
						request.getPort());
				try {
					aSocket.send(reply);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}).start();
	}
}

/*
 * class Connection extends Thread{
 * 
 * 
 * 
 * DataInputStream in; DataOutputStream out; Socket clientSocket;
 * 
 * public Connection(Socket aClientSocket) { try { clientSocket = aClientSocket;
 * in = new DataInputStream(clientSocket.getInputStream()); out = new
 * DataOutputStream(clientSocket.getOutputStream()); this.start(); } catch
 * (IOException e) { System.out.println("Conexão:" + e.getMessage()); } }
 * 
 * public void run() { try { String data = in.readUTF();
 * System.out.println("Recebido: " + data); out.writeUTF(data); } catch
 * (EOFException e) { System.out.println("EOF:" + e.getMessage()); } catch
 * (IOException e) { System.out.println("readline:" + e.getMessage()); } finally
 * { try { clientSocket.close();
 * System.out.println("Servidor: fechando conexão com cliente."); } catch
 * (IOException e) {} }
 * 
 * } }
 */