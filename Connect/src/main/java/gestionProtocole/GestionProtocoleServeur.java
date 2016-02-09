package gestionProtocole;
import java.sql.SQLException;

import serveur.Serveur;
 
public class GestionProtocoleServeur {
 
    private Serveur serveur;
    private String reponse, resultats;
    private String[] temp;

	public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
    }
    
    public String requete(String entreeSocket) {
        String[] req = entreeSocket.split("[|]");

        switch(req[0])
        {
	        case "CREA":
				System.out.println("NOM : "+ req[2] +" PRENOM : "+ req[4] +" MAIL : "+ req[6] +" MDP : "+ req[8]);
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
			try {
				temp = (serveur.rechercher(req));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            if (temp[0] == "1")
	            	requete("CONS|" + temp[1] + "|" + temp[2]);
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

	public void setResultats(String[] resultats) {
		// Compteur d'implémentation de reponse
		int i;
		
		// Initialisation puis construction de reponse
		this.resultats = null;
		for(i = 0; i < (resultats.length - 1); i++){
			this.resultats += resultats[i] + "|";
		}
			this.resultats += resultats[i];
	}
}