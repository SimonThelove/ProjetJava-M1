package gestionProtocole;
import java.sql.SQLException;
import java.util.ArrayList;

import serveur.Serveur;
 
public class GestionProtocoleServeur {
 
    private Serveur serveur;
    private String reponse, resultats;
    private ArrayList<String> temp = new ArrayList<String>();

	public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
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
				temp = (serveur.consulter(req[2]));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	setResultats(temp);
            	setReponse("PROF|" + resultats);
            	break;
		    case "RECH":
			temp = (serveur.rechercher(req));
	            if (temp.get(0).compareTo("1") == 0)
	            	requete("CONS|" + temp.get(1) + "|" + temp.get(2));
	            else {
	            	setResultats(temp);
	            	setReponse("LIST|" + resultats);
	            }
	            break;
		    case "DECO":
	            setReponse("MSG|" + serveur.seDeconnecter());
	            break;
        }
        return reponse;
    }

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public void setResultats(ArrayList<String> array) {
		this.resultats += array.get(0);
	}
}