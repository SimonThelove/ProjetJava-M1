/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package serveur;

/**
 *
 * @author Yohann
 */
import static javafx.application.Application.launch;
import socketsTCP.SocketServeur;

public class LanceurServeur {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SocketServeur soc = new SocketServeur();
		soc.socket();
	}
}
