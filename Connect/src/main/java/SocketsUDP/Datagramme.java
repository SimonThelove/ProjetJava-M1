package socketsUDP;

import java.io.*;
import java.net.*;

public class Datagramme {

	byte[] tamponR = new byte[1000];
	byte[] tamponE;
	String texte;
	DatagramSocket socket;
	DatagramPacket reception, envoi;
	InetAddress adresse = null;

	// méthode de reception des données utilisant les sockets UDP
	public void Recevoir()
		throws SocketException, IOException
		{
			// crée un socket UDP qui attends des datagrammes sur le port 50000 DatagramSocket
			socket = new DatagramSocket(50000);
			
			// crée un objet pour stocker les données du datagramme attendu DatagramPacket
			reception = new DatagramPacket(tamponR, tamponR.length);
			
			// attends puis récupère les données du datagramme
			socket.receive(reception);			// récupère la chaîne de caractère reçue
			
			// Note: reception.getLength() contient le nombre d’octets reçus
			texte = new String(tamponR, 0, reception.getLength()); System.out.println("Reception de la machine "+
							  reception.getAddress().getHostName()+ " sur le port " +reception.getPort()+" :\n"+
							  texte );
		}
	
	// méthode d'envoi des données utilisant les sockets UDP
	public void Envoyer(String message)
			throws SocketException, IOException, UnknownHostException
		{
			tamponE = message.getBytes();

			// recupère l’adresse IP de la machine qui envoie le datagramme
			adresse = InetAddress.getByName("ADRESSE_SERVEUR");
			
			// crée l’objet qui stockera les données du datagramme à envoyer
			envoi = new DatagramPacket(tamponE,tamponE.length,adresse,50000);
			
			// crée un socket UDP (le port est choisi par le système)
			socket = new DatagramSocket();
			
			// envoie le datagramme UDP
			socket.send(envoi);
		}
}
