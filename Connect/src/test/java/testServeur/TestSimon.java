package testServeur;

import socketsTCP.SocketServeur;

public class TestSimon {
	
	private static SocketServeur soc;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SocketServeur soc = new SocketServeur();
		
		soc.socket();
	}

}
