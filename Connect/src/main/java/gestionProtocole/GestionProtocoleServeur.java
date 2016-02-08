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
    
    public String requete(String entreeSocket) throws SQLException{
        String[] req = entreeSocket.split("|");
        switch(req[0])
        {
	        case "CREA":
	            setReponse("MSG|" + serveur.creerCompte(req[2], req[4], req[6], req[8]));
	      
		    case "CONX":
	            setReponse("MSG|" + serveur.seConnecter(req[2], req[4]));
		            
		    case "MODI": // AJOUTER LE MAIL DU CLIENT CONNECTE (condition WHERE du SQL UPDATE)
	            setReponse("MSG|" + serveur.modifierInformations(req,req[2]));

		    case "CONS":
		    	temp = (serveur.consulter(req[2]));
		    	setResultats(temp);
            	setReponse("PROF|" + resultats);

		    case "RECH":
	            temp = (serveur.rechercher(req));
	            if (temp[0] == "1")
	            	requete("CONS|" + temp[1] + "|" + temp[2]);
	            else {
	            	setResultats(temp);
	            	setReponse("LIST|" + resultats);
	            }

		    case "DECO":
	            setReponse("MSG|" + serveur.seDeconnecter());

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