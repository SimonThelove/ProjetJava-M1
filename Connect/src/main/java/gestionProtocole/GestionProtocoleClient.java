package gestionProtocole;
import java.util.Scanner;

import client.Client;
import socketsTCP.SocketClient;

public class GestionProtocoleClient {
	private Client client;
	private Scanner sc = new Scanner(System.in);
	private String message;
	private SocketClient soc = new SocketClient();
	private int nbPersonne;
	private int choixProfil;
	private String mailRechercher[];
	private String req[];
	
	public int getChoixProfil() {
		return choixProfil;
	}

	public void setChoixProfil(int choixProfil) {
		this.choixProfil = choixProfil;
	}

	public int getNbPersonne() {
		return nbPersonne;
	}

	public void setNbPersonne(int nbPersonne) {
		this.nbPersonne = nbPersonne;
	}

	public String[] getMailRechercher() {
		return mailRechercher;
	}

	public void setMailRechercher(String[] mailRechercher) {
		this.mailRechercher = mailRechercher;
	}
	
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

	//Mï¿½thode de concatï¿½nation de la requï¿½te creerCompte
	public void requeteCrea(String nom, String prenom, String mail, String motDePasse){
		message = "CREA|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;

		System.out.println(message);
		//Envoit du message à SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}
	
	//Mï¿½thode de concatï¿½nation de la requï¿½te connexion
	public void requeteConx(String mail, String motDePasse){
		message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}

	//Mï¿½thode de concatï¿½nation de la requï¿½te rechercherMotsCles
	public void requeteRechMotsCles(String motCles){
		message = "RECH|MOTSCLES|" + motCles;
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}

	//Mï¿½thode de concatï¿½nation de la requï¿½te RechercherAvancee
	public void requeteRechNom(String nom, String prenom, String mail, String diplome, String annee, String competences){
		message = "RECH|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|DIPLOME|" + diplome + "|ANNEE|" + annee + "|COMPETENCES|" + competences;
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}

	//Mï¿½thode de concatï¿½nation de la requï¿½te consultation
	public void requeteCons(){
		message = "CONS|" + mailRechercher[choixProfil];
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}
	
	//Mï¿½thode de concatï¿½nation de la requï¿½te modification
	public void requeteModi(String mail, String nom, String prenom, String motDePasse, String diplome, String annee, String competences){
		message = "MODI|MAIL|" + mail + "|NOM|" + nom + "|PRENOM|" + prenom + "|MOTDEPASSE|" + motDePasse + "|DIPLOME|"  + diplome + "|ANNEE|" + annee + "|COMPETENCES|" + competences;
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}

	//Mï¿½thode de concatï¿½nation de la requï¿½te deconnexion
	public void requeteDeco(){
		message = "DECO";
		//Envoit du message ï¿½ SocketClient
		message = soc.socket(message);
		//Appelle ï¿½ la mï¿½thode pour crï¿½er un affichage ordonnï¿½ au client
		message = decoupage(message);
	}
	
	//Mï¿½thode pour crï¿½er la rï¿½ponse ï¿½ afficher d'une requï¿½te
	public String decoupage(String reponse){
		nbPersonne = 1;
        req = reponse.split("[|]");
        switch(req[0]){
        //Recupï¿½re le message ï¿½ afficher au client
        case "MSG":
            try {
                message = req[1];
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        //Affiche la liste simplifiï¿½e des profils
        case "LIST":
            try {
            	int i, j=3;
        		nbPersonne = Integer.parseInt(req[1]);
        		message = "Resultats :\n\n";
                    	for(i = 1; i < (nbPersonne) ; i = i+2, j = j+6)
        		{
        			message += req[j] + " - " + req[j+2] + " - " + req[j+4] + "\n";
        			mailRechercher[i] = req[j+4];
        		}
                    		
        		message += "\nQuel profil souhaitez-vous consulter ? :";
            	choixProfil = sc.nextInt();
            	}
              catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        //Affiche un profil
        case "PROF":
            try {
            	int i;
            	message = "\n\n----------------------------------------\n";
            	for(i = 1; i < (reponse.length()) ; i = i+2)
            	{
            		switch (req[i])
            		{
            			case "NOM" :
            				message += req[i+1] + " - ";
            				break;
            		    case "PRENOM" :
            		    	message += req[i+1] + " - ";
                		    break;
                		case "MAIL" :
                			message += req[i+1];
                    		break;
                		case "DIPLOME" :
                			message += "\nDiplome obtenu : " + req[i+1] + " (en";
                			break;
                		case "ANNEE" :
                			message += " (en" + req[i+1] + ")";
                			break;
                		case "COMPETENCES" :
                			message += "\nCompï¿½tences ; " + req[i+1];
                            break;        
            		  default:
            		    /*Action*/;             
            		}
	            	message = req[2] + " - " + req[4] + " - " + req[6] + "\nDiplomï¿½(e) en " + req[8] + " (" + req[10] + ")\nCompï¿½tence(s) : " + req[12];
            	}
            	message += "\n----------------------------------------\n";
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
