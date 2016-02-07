package gestionProtocole;
import java.io.*;
import java.net.*;
import client.Client;
import socketsTCP.SocketClient;

public class GestionProtocoleClient {
	private Client client;
	private String message;
	private SocketClient soc = new SocketClient();
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

	//M�thode de concat�nation de la requ�te creerCompte
	public void requeteCrea(String nom, String prenom, String mail, String motDePasse){
		message = "CREA|" + nom + "|" + prenom + "|" + mail + "|" + motDePasse + "||";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}
	
	//M�thode de concat�nation de la requ�te connexion
	public void requeteConx(String mail, String motDePasse){
		message = "CONX|" + mail + "|" + motDePasse + "||";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}

	//M�thode de concat�nation de la requ�te rechercherMotsCles
	public void requeteRechMotsCles(String motCles){
		message = "RECH|MOTSCLES|" + motCles + "||";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}

	//M�thode de concat�nation de la requ�te RechercherAvancee
	public void requeteRechNom(String nom, String prenom, String mail, String diplome, String annee, String competences){
		message = "RECH|NOM|";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}

	//M�thode de concat�nation de la requ�te consultation
	public void requeteCons(String mail){
		message = "CONS|" + mail +"||";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}
	
	//M�thode de concat�nation de la requ�te modification
	public void requeteModi(String nom, String prenom, String motDePasse, String diplome, String annee, String competences){
		message = "MODI|";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}

	//M�thode de concat�nation de la requ�te deconnexion
	public void requeteDeco(){
		message = "DECO";
		//Envoit du message � SocketClient
		message = soc.socket(message);
		//Appelle � la m�thode pour cr�er un affichage ordonn� au client
		message = decoupage(message);
	}
	
	//M�thode pour cr�er la r�ponse � afficher d'une requ�te
	public String decoupage(String reponse){
        String[] req = reponse.split(" ");
        switch(req[0]){
        //Recup�re le message � afficher au client
        case "MSG":
            try {
                message = req[1];
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        //Affiche la liste simplifi�e des profils
        case "LIST":
            try {
            	/*
            	nbElement = req[1];
            	int k = 2;
            	message = "----------------------------------------\n";
            	for(int i=0; i>nbElement; i++)
            	{
            		
            	}*/
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                
            }
            break;
        //Affiche un profil
        case "PROF":
            try {
        		message = "----------------------------------------\n";
            	message = "";
        		message = "----------------------------------------\n";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        default :
            message = "Erreur dans votre choix";
        }
        return message;
    }	
}
