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
            
    private String message = null;//Variable ou est stockee la requete
    private int nbPersonne = 0;//Nombre de profil que renvoit les requetes de recherche
    private String req[] = null;//Requete de retour du serveur 

    public GestionProtocoleClient() {
        this.clients = FXCollections.observableArrayList();
    }
    
    public ObservableList<Client> getClients() {
        return clients;
    }
        
    public String getMessage() {
        return message;
    }

    // Methode de conception de requete (utilise depuis Modification.java)
    public void setMessage(Client client) {
            this.message = "NOM|" + client.getNom();
            this.message += "|PRENOM|" + client.getPrenom();
            this.message += "|MAIL|" + client.getMail();
            this.message += "|DIPLOMES|" + client.getDiplome();
            this.message += "|ANNEE_DIPLOMATION|" + client.getAnnee();
            this.message += "|COMPETENCES|" + client.getCompetences();
            this.message += " |";

System.out.println(message);
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(Client client){
	//Creation de la requete
	message = "CREA|NOM|" + client.getNom() + "|PRENOM|" + client.getPrenom()  + "|MAIL|" + client.getMail() + "|MOTDEPASSE|" + client.getMdp();
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse, Client client){
	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles, Client client){
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(Client client){
	//Creation de la requete
	message = "RECH|NOM|" + client.getNom() + "|PRENOM|" + client.getPrenom() + "|MAIL|" + client.getMail() + "|DIPLOME|" + client.getDiplome() + "|ANNEE|" + client.getAnnee() + "|COMPETENCES|" + client.getCompetences();
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(int index, Client client){
	//Creation de la requete
	message = "CONS|" + clients.get(index).getMail();
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode de concatenation de la requete modification
    public void requeteModi(Client client){
        String requete;
	//Creation de la requete
	requete = "MODI|MAIL|" + "yohann@gm.fr" + "|" + message;
	//Envoit du message a SocketClient
	requete = soc.socket(requete);
	//Appelle a la methode pour creer un affichage au client
	decoupage(requete, client);
	}

    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(Client client){
	//Creation de la requete
	message = "DECO|";
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public void decoupage(String reponse, Client client){
        
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