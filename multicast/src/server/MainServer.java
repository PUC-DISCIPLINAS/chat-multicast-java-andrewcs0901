package server;

import server.groups.GroupDAO;

class MainServer {
	
	public static void main(String args[]) {
		new Thread( () -> new GroupDAO()).start();
		new Thread( () -> new UDPServer()).start();
	}
	
}
