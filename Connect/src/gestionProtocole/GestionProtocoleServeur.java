/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package gestionProtocole;

import java.sql.SQLException;
import java.util.ArrayList;
import serveur.Serveur;

/**
 *
 * @author Yohann
 */
 
public class GestionProtocoleServeur {
    private Serveur serveur;
    private String reponse;
    private ArrayList<String> temp = new ArrayList<String>();

	public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
    }
    
	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	
    public String requete(String entreeSocket) {
        String[] req = entreeSocket.split("[|]");

        switch(req[0])
        {
	        case "CREA":
				reponse = ("MSG|" + serveur.creerCompte(req[2], req[4], req[6], req[8]));
				break;
		    case "CONX":
			try {
				setReponse("MSG|" + serveur.seConnecter(req[2], req[4]));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		    case "MODI": // AJOUTER LE MAIL DU CLIENT CONNECTE (condition WHERE du SQL UPDATE)
			try {
				setReponse("MSG|" + serveur.modifierInformations(req,req[2]));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		    case "CONS":
			try {
				System.out.println("GPS CONS fonction 1 - req :" + req[2]);
				temp = (serveur.consulter(req[2]));
				System.out.println("GPS CONS fonction 2 - temp :" + temp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	String resultat = String.join("|",temp);
                        setReponse("PROF|" + resultat);
            	break;
		    case "RECH":
			temp = (serveur.rechercher(req));
			System.out.println("Niveau GPS temp - " + temp);
	            if (temp.get(0).compareTo("1") == 0)
	            {
	            	System.out.println("Niveau GPS Rech :"+ temp.get(1) + "|" + temp.get(2));
	            	requete("CONS|" + temp.get(1) + "|" + temp.get(2));
	            }
	            else {
	            	System.out.println("Niveau GPS LIST||");
	            	//Transformation de l'arrayList en String
	            	String result = String.join("|",temp);
	            	//Concatenation de la requete
	            	setReponse("LIST|" + result);
	            }
	            break;
		    case "DECO":
	            setReponse("MSG|" + serveur.seDeconnecter());
	            break;
        }
    
        System.out.println("Niveau GPS resultat2 - " + reponse);
        return reponse;
    }	
}