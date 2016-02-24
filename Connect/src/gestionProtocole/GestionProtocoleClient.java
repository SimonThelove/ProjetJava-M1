/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package gestionProtocole;

import socketsTCP.SocketClient;
import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Simon
 */
public class GestionProtocoleClient {
    
    private final SocketClient soc = new SocketClient();
    private final ObservableList<Client> clients;
    
    private Client client = new Client();
    
    private String message = null;//Variable ou est stockee la requete
    private int nbPersonne = 0;//Nombre de profil que renvoit les requetes de recherche
    private String req[] = null;//Requete de retour du serveur 

    public GestionProtocoleClient() {
        this.clients = FXCollections.observableArrayList();
    }
    
    public Client getClient() {
        return client;
    }
    
    public ObservableList<Client> getClients() {
        return clients;
    }
        
    public String getMessage() {
        return message;
    }

    // Methode de conception de requete (utilise depuis Modification.java)
    public void setMessage() {
        this.message = null;
        if (client.getNom() != null)
            this.message += "NOM|" + client.getNom();
        if (client.getPrenom() != null)
            this.message += "PRENOM|" + client.getPrenom();
        if (client.getMail() != null)
            this.message += "MAIL|" + client.getMail();
        if (client.getDiplome() != null)
            this.message += "DIPLOME|" + client.getDiplome();
        if (client.getAnnee() != null)
            this.message += "ANNEE|" + client.getAnnee();
        if (client.getCompetences() != null)
            this.message += "COMPETENCES|" + client.getCompetences();
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(String nom, String prenom, String mail, String motDePasse){
	//Creation de la requete
	message = "CREA|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse){
	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles){
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(String nom, String prenom, String mail, String diplome, String annee, String competences){
	//Creation de la requete
	message = "RECH|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|DIPLOME|" + diplome + "|ANNEE|" + annee + "|COMPETENCES|" + competences;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(int index){
	//Creation de la requete
	message = "CONS|" + clients.get(index).getMail();
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }
	
    //Methode de concatenation de la requete modification
    public void requeteModi(String requete){
	//Creation de la requete
	message = "MODI|MAIL|" + client.getMail() + "|" + requete;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
	}

    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(){
	//Creation de la requete
	message = "DECO|";
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message);
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public void decoupage(String reponse){
System.out.println("reponse : " + reponse);
	nbPersonne = 1;
        client.setChaine("Aucune information disponible");
System.out.println("GPC - chaine 1 : " + client.getChaine());        
        req = reponse.split("[|]");
        
        switch(req[0]){
        //Recupere le message a afficher au client
        case "MSG":
            try {
System.out.println("MSG - req 1 : " + req[1]);
                client.setChaine(req[1]);
System.out.println("MSG - chaine : " + client.getChaine());
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Erreur format");
            }
            break;
        //Affiche la liste simplifier des profils
        case "LIST":
            try {
            	//message = "Resultats :\n\n";
System.out.println("LIST - req 1 : " + req[1]);
            	for(int i = 1; i <= req.length; i++){
System.out.println("message " + i + " :" + req[i]);
                        client = new Client();
            		client.setMail(req[i+1]);
                        client.setNom(req[i+3]);
                        client.setPrenom(req[i+5]);
                        nbPersonne ++;
                        client.setChaine(Integer.toString(nbPersonne));
                        clients.add(client);
            	}
            }
            catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Erreur format");

            }
            break;
        //Affiche un profil
        case "PROF":
            try {
System.out.println("PROF - req 1 : " + req[1]);
            	for(int i = 1; i < (reponse.length()) && !client.getChaine().equals("Erreur GPC"); i += 2)
            	{
            		switch (req[i])
            		{
                            case "MAIL" : 
                                client.setMail(req[i+1]);
                            case "NOM" :
            			client.setNom(req[i+1]);
            			break;
            		    case "PRENOM" :
            		    	client.setPrenom(req[i+1]);
                		break;
                            case "DIPLOME" :
                                client.setDiplome(req[i+1]);
                                break;
                            case "ANNEE" :
                                client.setAnnee(req[i+1]);
                		break;
                            case "COMPETENCES" :
                		client.setCompetences(req[i+1]);
                                break;        
                            default:
            		        client.setChaine("Erreur GPC");
                                break;
            		}
            	}
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Erreur format");
            }
            break;
        case "DECO" :
System.out.println("DECO - req 1 : " + req[1]);
            client.setChaine("Vous êtes bien déconnectés.");
        default :
System.out.println("DEF - req 1 : " + req[1]);
            client.setChaine("Erreur dans votre choix");
        }
    }
}