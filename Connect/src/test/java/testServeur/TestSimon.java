package testServeur;

import socketsTCP.SocketServeur;

public class TestSimon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SocketServeur soc = new SocketServeur();
		System.out.println("Lancement serveur...");
		soc.socket();
	}

}
