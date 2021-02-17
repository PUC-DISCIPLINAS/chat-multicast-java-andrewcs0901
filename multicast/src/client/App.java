package client;

import java.util.Scanner;

import util.EAcceptedOptions;

public class App {
	static Client client = null;
	static Scanner scanner = new Scanner(System.in);
	static String res = "";

	public static void main(String[] args) {
		String userName = "";
		while(userName.length() == 0) {
			System.out.println("Insira seu nome: ");
			userName = scanner.nextLine();
		}
		client = new Client(userName);
		try{
			do {
				res = "";
				System.out.println("1 - listar salas");
				System.out.println("2 - ingressar no chat de comandos e mensagens");
				System.out.println("0 - sair");
				res = scanner.nextLine();
				switch (res) {
				case "1":
					client.handleSendMessage(EAcceptedOptions.LIST_GROUPS);
					break;
				case "2":
					chatInterface();
					break;
				default:
					break;
				}
			} while (!res.equalsIgnoreCase("0"));
		} finally {
			scanner.close();
			if (client != null) {
				client.closeSocket();
			}
		}
	}

	private static void chatInterface() {
		res = "";
		System.out.println("Digite /?, para conhecer os comandos disponíveis ou apenas comece o bate-papo");
		while (true) {
			res = scanner.nextLine();
			if (!res.equalsIgnoreCase("/exit"))
				client.handleSendMessage(res);
			else
				return;
		}
	}
}
