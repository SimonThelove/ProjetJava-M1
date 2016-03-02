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
    
    private final ObservableList<Client> clients;
    private final ObservableList<String> clients_co;
    //private final ObservableList<String> messagerie;
    
    private SocketClient soc; //recuperation du socket d'echange
    private String message = null;//Variable ou est stockee la requete
    private int nbPersonne = 0;//Nombre de profil que renvoit les requetes de recherche
    private String req[] = null;//Requete de retour du serveur 
    
    private String expediteur; //Expediteur du message
    private String mailRecu;   //Contenu du message reçu

    public GestionProtocoleClient(SocketClient socket) {
        this.soc = socket;
        this.clients = FXCollections.observableArrayList();
        this.clients_co = FXCollections.observableArrayList();
        //this.messagerie = FXCollections.observableArrayList();
    }

    public ObservableList<String> getClients_co() {
        return clients_co;
    }
    
    public ObservableList<Client> getClients() {
        return clients;
    }
        
    public String getMessage() {
        return message;
    }

    // Methode de conception de requete (utilise depuis Modification.java)
    public void setMessage(Client client) {
        this.message = client.getMailCo();    
        this.message += "|NOM|" + client.getNom();
        this.message += "|PRENOM|" + client.getPrenom();
        this.message += "|TELEPHONE|" + client.getTel();
        this.message += "|DIPLOMES|" + client.getDiplome();
        this.message += "|ANNEE_DIPLOMATION|" + client.getAnnee();
        this.message += "|COMPETENCES|" + client.getCompetences();
        this.message += " |";
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(Client client){
	//Creation de la requete
	message = "CREA|NOM|" + client.getNom() + "|PRENOM|" + client.getPrenom()  + "|MAIL|" + client.getMail() + "|MOTDEPASSE|" + client.getMdp();
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse, Client client){
	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
System.out.println("CONX : " + message);
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles, Client client){
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(Client client){
	//Creation de la requete
	message = "RECH|NOM|" + client.getNom() +
                "|PRENOM|" + client.getPrenom() +
                "|MAIL|" + client.getMail() +
                "|DIPLOMES|" + client.getDiplome() +
                "|ANNEE_DIPLOMATION|" + client.getAnnee() +
                "|COMPETENCES|" + client.getCompetences();
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(int index, Client client){
	//Creation de la requete
	message = "CONS|MAIL|" + clients.get(index).getMail() + "|0|";
System.out.println("GPC CONS : " + message);
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode de concatenation de la requete pour recuperer les information du client qui se connecte
    public void requeteNomConnecte(Client client){
	//Creation de la requete
	message = "CONS|MAIL|" + client.getMail() + "|1|";
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
    
    //Methode de concatenation de la requete modification
    public void requeteModi(Client client){
        String requete;
	//Creation de la requete
	requete = "MODI|MAIL|" + message;
	//Envoit du message a SocketClient
	requete = soc.echangeServeur(requete);
	//Appelle a la methode pour creer un affichage au client
	decoupage(requete, client);
	}
    
    //Methode de concatenation de la requete deconnexion
    public void requeteEnvoiMsg(String mailDes, String msg, Client client){
	//Creation de la requete
	message = "MSSG|ENVOI|MAIL_EXP|" + client.getMail() + "|MAIL_DES|" + mailDes + "|MESSAGE|" + msg;
 System.out.println(msg);
	//Envoit du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
    
    //Methode de concatenation de la requete deconnexion
    public void requeteRecupMsg(Client client){
	//Creation de la requete
	message = "MSSG|RECUP|MAIL_DES|" + client.getMail();
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
    
    //Méthode de récupération de la liste des clients connectés
    public void requeteP2P (Client client){
        //Creation de la requete
        message = "P2PH|";
        //Envoi du message a SocketClient
        message = soc.echangeServeur(message);
        //appel a la methode pour creer un affchage client
        decoupage(message, client);
    }
    
    //Methode de demande de connexion PeerToPeer
    // EN COURS DE DEVELOPPEMENT...
    
    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(Client client){
	//Creation de la requete
	message = "DECO|";
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public void decoupage(String reponse, Client client){
        
System.out.println("reponse : " + reponse);
        client.setChaine("Aucune information disponible");       
        req = reponse.split("[|]");
        
        switch(req[0]){
        //Recupere le message a afficher au client
        case "MSG":
            try {
System.out.println("MSG - req 1 : " + req[1]);
                client.setChaine(req[1]);
System.out.println("MSG - chaine : " + client.getChaine());
                //Si le message est le suivant, alors on met a jours les variables de connexion du client
                // (le message de bienvenu sera a jours directement sans passer par une reconnexion)
                if("Vos modifications ont ete prises en compte.".equals(req[1]))
                {
                    client.setNomCo(client.getNom());
                    client.setPrenomCo(client.getPrenom());
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Erreur format");
            }
            break;
        //Affiche la liste simplifier des profils
        case "LIST":
           try {
            	//message = "Resultats :\n\n";
                if (!Integer.toString(0).matches(req[1])) { // Ne s'exécute qe s'il y a des résultats à la recherche
System.out.println("LIST - nb resultats : " + req[1]);
                    int j = 1;
                    for(int i = 0; !Integer.toString(i).matches(req[1]); i ++){
System.out.println("client " + (i+1) + " :" + req[j+4] + " " + req[j+6]);
                        clients.add(new Client (req[j+2],req[j+4],req[j+6],req[j+10],req[j+12],req[j+14],Integer.toString(i)));
System.out.println("liste clients : " + clients.get(i).getMail());
                        j += 14; // permet de récupérer les bonnes valeurs
                    }
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
System.out.println("PROF - req 1 : " + req[6]);

            	for(int i = 1; i < (req.length) && !client.getChaine().equals("Erreur GPC"); i += 2)
            	{
        System.out.println("PROF - req avant switch : " + req[i]);
            		switch (req[i])
            		{
                            //Si dans la req[1] il y a "1", alors la requete est special (requete de recuperation d'info du client lors de sa connexion
                            //On met des variables propres au client qui permettra la gestion des boutons retour menu au menuConnecte
                            case "1" :
                                i=0;
                                client.setMailCo(req[3]);
                                client.setNomCo(req[5]);
                                client.setPrenomCo(req[7]);
                                break;
                            case "mail" : 
                                client.setMail(req[i+1]);
                                break;
                            case "nom" :
            			client.setNom(req[i+1]);
            			break;
            		    case "prenom" :
            		    	client.setPrenom(req[i+1]);
                		break;
                            case "telephone" :
                                client.setTel(req[i+1]);
                                break;
                            case "diplomes" :
                                client.setDiplome(req[i+1]);
                                break;
                            case "annee_diplomation" :
                                client.setAnnee(req[i+1]);
                		break;
                            case "competences" :
                		client.setCompetences(req[i+1]);
                                break;        
                            default:
            		        client.setChaine("Erreur GPC");
                                break;
            		}
            	}
                clients.add(client);
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Erreur format");
            }
            break;
        case "MSSG":
            if(Integer.toString(0).matches(req[1]))
            {
                //Mettre un message pas de resultat
            }
            else
            {
                for(int i = 2; i < (req.length-2); i += 8)
                {
                    //expediteur = req[i+3];
                    //mailRecu = req[i+7];
                    //messagerie.add(expediteur);
System.out.println("message envoyé par " + req[i+3] + " - message : " + req[i+7]);
                }
            }
            break;
        case "P2PH" :
            // Retour de la hashtable depuis le serveur
System.out.println("P2PH : " + req[1]);
            for (int i = 1; i < req.length; i++){
                clients_co.add(req[i]);
            }
            break;
        case "DECO" :
            client.setMailCo(null);
            client.setNomCo(null);
            client.setPrenomCo(null);
            client.setChaine("Vous êtes bien déconnectés.");
        default :
            client.setChaine("Erreur dans votre choix");
        }
    }
}