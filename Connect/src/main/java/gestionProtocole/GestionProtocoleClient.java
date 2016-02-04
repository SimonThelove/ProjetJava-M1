package gestionProtocole;
import java.io.*;
import java.net.*;
import client.Client;
import socketsTCP.SocketClient;

public class GestionProtocoleClient {
	private Client client;
	private String message;
	private SocketClient soc;
	private String nbElement;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GestionProtocoleClient(Client client) {
		super();
		this.client = client;
	}

	public void setRequeteCrea(String nom, String prenom, String mail, String motDePasse){
		message = "CREA|" + nom + "|" + prenom + "|" + mail + "|" + motDePasse + "||";
		message = soc.socket(message);
		message = decoupage(message);
	}
	
	public void setRequeteConx(String mail, String motDePasse){
		message = "CONX|" + mail + "|" + motDePasse + "||";
		message = soc.socket(message);
	}

	public void setRequeteRechMotsCles(String motCles){
		message = "RECH|MOTSCLES|" + motCles + "||";
		message = soc.socket(message);
		//Test pour savoir les variable renseigné
		
		
	}

	public void setRequeteRechNom(String nom, String prenom, String mail, String diplome, String annee, String competences){
		message = "RECH|NOM|";
		message = soc.socket(message);

		//Test pour savoir les variable renseigné
	}

	public void setRequeteCons(String mail){
		message = "CONS|" + mail +"||";
		message = soc.socket(message);

		//Test pour savoir les variable renseigné
	}
	
	public void setRequeteModi(String nom, String prenom, String motDePasse, String diplome, String annee, String competences){
		message = "MODI|";
		message = soc.socket(message);

		//Test pour savoir les variable renseigné
	}

	public void setRequeteDeco(String mail, String motDePasse){
		message = "DECO";
		message = soc.socket(message);

	}
	
	
	public String decoupage(String reponse){
        String[] req = reponse.split(" ");
        switch(req[0]){
        case "MSG":
            try {
                message = req[1];
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        case "INFO":
        	try {
                nbElement = req[1];
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        case "LIST":
            try {
                serveur.ajouter(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
            break;
        case "PROF":
            try {
            	message = "---------------------------------\n";
            	message = "";
            	message = "---------------------------------\n";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        default :
            message = "Erreur dans votre choix";
        }
        for (int i=0; i<nbElement; i++)
    }	
}
