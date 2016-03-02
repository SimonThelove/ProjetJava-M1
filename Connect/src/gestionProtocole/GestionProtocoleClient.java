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
import socketsTCP.SocketEcouteMsgr;

/**
 *
 * @author Simon
 */
public class GestionProtocoleClient {
    
    private final ObservableList<Client> clients;
    private final ObservableList<String> clients_co;
    private final ObservableList<String> messagerie;
    
    private SocketClient soc; //recuperation du socket d'echange
    private SocketEcouteMsgr socMsgr; //recuperation du socket P2P
    private String message = null;//Variable ou est stockee la requete
    private int nbPersonne = 0;//Nombre de profil que renvoit les requetes de recherche
    private String req[] = null;//Requete de retour du serveur 
    
    private String expediteur; //Expediteur du message
    private String mailRecu;   //Contenu du message reçu

    public GestionProtocoleClient(SocketClient socket) {
System.err.println("#### Construct GPC vers Serveur...");         
        this.soc = socket;
        this.clients = FXCollections.observableArrayList();
        this.clients_co = FXCollections.observableArrayList();
        this.messagerie = FXCollections.observableArrayList();
    }
    
    public GestionProtocoleClient(SocketEcouteMsgr socket) {
System.err.println("#### Construct GPC pour P2P...");         
        this.socMsgr = socket;
        this.clients = FXCollections.observableArrayList();
        this.clients_co = FXCollections.observableArrayList();
        this.messagerie = FXCollections.observableArrayList();
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
System.err.println("@GPC setMessage...");         
        this.message = client.getMailCo();    
        this.message += "|NOM|" + client.getNom();
        this.message += "|PRENOM|" + client.getPrenom();
        this.message += "|TELEPHONE|" + client.getTel();
        this.message += "|DIPLOMES|" + client.getDiplome();
        this.message += "|ANNEE_DIPLOMATION|" + client.getAnnee();
        this.message += "|COMPETENCES|" + client.getCompetences();
        this.message += " |";
System.err.println("@setMessage FIN ----------");         
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(Client client){
System.err.println("@GPC requeteCrea...");                 
	//Creation de la requete
	message = "CREA|NOM|" + client.getNom() + "|PRENOM|" + client.getPrenom()  + "|MAIL|" + client.getMail() + "|MOTDEPASSE|" + client.getMdp();
System.out.println("# Recuperation infos OK... " + message);         	
        //Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteCrea FIN ----------");         
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse, Client client){
System.err.println("@GPC requeteConx");         

	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
System.out.println("# Recuperation infos OK... " + message);
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteConx FIN ----------");                 
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles, Client client){
System.err.println("@GPC requeteRechMotsCles");                 
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
System.out.println("# Recuperation infos OK... " + message);           
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteRechMotsCles FIN ----------");                 
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(Client client){
System.err.println("@GPC requeteRechNom");         
        
	//Creation de la requete
	message = "RECH|NOM|" + client.getNom() +
                "|PRENOM|" + client.getPrenom() +
                "|MAIL|" + client.getMail() +
                "|DIPLOMES|" + client.getDiplome() +
                "|ANNEE_DIPLOMATION|" + client.getAnnee() +
                "|COMPETENCES|" + client.getCompetences();
System.out.println("# Recuperation infos OK... " + message);         
        
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteRechNom FIN ----------");         

    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(int index, Client client){
System.err.println("@GPC requeteCons");         

	//Creation de la requete
	message = "CONS|MAIL|" + clients.get(index).getMail() + "|0|";
System.out.println("# Recuperation infos OK... " + message);

	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteConx FIN ----------");         

    }
	
    //Methode de concatenation de la requete pour recuperer les information du client qui se connecte
    public void requeteNomConnecte(Client client){
System.err.println("@GPC requeteNomConnecte");         

	//Creation de la requete
	message = "CONS|MAIL|" + client.getMail() + "|1|";
System.out.println("# Recuperation infos OK... " + message);         

	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteNomConnecte FIN ----------");         

    }
    
    //Methode de concatenation de la requete modification
    public void requeteModi(Client client){
System.err.println("@GPC requeteModi");         

        String requete;
	//Creation de la requete
	requete = "MODI|MAIL|" + message;
System.out.println("# Recuperation infos OK... " + message);         
        
	//Envoi du message a SocketClient
	requete = soc.echangeServeur(requete);
	//Appelle a la methode pour creer un affichage au client
	decoupage(requete, client);        
System.err.println("@requeteModi FIN ----------"); 
	}
    
    //Methode de concatenation de la requete deconnexion
    public void requeteEnvoiMsg(String mailDes, String msg, Client client){
System.err.println("@GPC requeteEnvoiMsg");         
       
	//Creation de la requete
	message = "MSSG|ENVOI|MAIL_EXP|" + client.getMail() + "|MAIL_DES|" + mailDes + "|MESSAGE|" + msg;        
System.out.println("# Recuperation infos OK... " + message);

	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteEnvoiMsg FIN ----------"); 
    }
    
    //Methode de concatenation de la requete deconnexion
    public void requeteRecupMsg(Client client){
System.err.println("@GPC requeteRecupMsg");         
        
	//Creation de la requete
	message = "MSSG|RECUP|MAIL_DES|" + client.getMail();        
System.out.println("# Recuperation infos OK... " + message);
        
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteRecupMsg FIN ----------");         
    }
    
    //Méthode de récupération de la liste des clients connectés
    public void requeteP2P (Client client){
System.err.println("@GPC requeteP2P");         
    
        //Creation de la requete
        message = "P2PH|";       
System.out.println("# Recuperation infos OK... " + message);
       
        //Envoi du message a SocketClient
        message = soc.echangeServeur(message);
        //appel a la methode pour creer un affchage client
        decoupage(message, client);       
System.err.println("@requeteP2P FIN ----------");         
    }
    
    //Methode de demande de connexion PeerToPeer
    public void echangerP2P (String msg, Client client) {
System.err.println("@GPC echangerP2P");         
        
        //Creation de la requete
        message = "P2PM|" + msg;
System.out.println("# Recuperation infos OK... " + message);

        //Envoi du message a SocketClient
        message = socMsgr.echangeP2P(message);
        //appel a la methode pour creer un affchage client
        decoupage(message, client);
System.err.println("@echangerP2P FIN ----------"); 
    }
    
    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(Client client){
System.err.println("@GPC requeteDeco");         
        
	//Creation de la requete
	message = "DECO|";
System.out.println("# Recuperation infos OK... " + message);

        //Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.err.println("@requeteDeco FIN ----------");         
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public void decoupage(String reponse, Client client){
        
System.err.println("@GPC decoupage");         
System.out.println("## ENTREE = " + reponse);

        client.setChaine("Aucune information disponible");       
        req = reponse.split("[|]");
        
        switch(req[0]){
        //Recupere le message a afficher au client
        case "MSG":
            try {
System.err.println("---- CASE MSG ----");                
                client.setChaine(req[1]);
System.out.println("# MSG - Retour client = " + client.getChaine());
                //Si le message est le suivant, alors on met a jours les variables de connexion du client
                // (le message de bienvenu sera a jours directement sans passer par une reconnexion)
                if("Vos modifications ont ete prises en compte.".equals(req[1]))
                {
System.out.println("# Modifications Nom/Prenom client...");                    
                    client.setNomCo(client.getNom());
                    client.setPrenomCo(client.getPrenom());
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Case MSG - Erreur Format");
            }
            break;
        //Affiche la liste simplifier des profils
        case "LIST":
           try {
System.err.println("---- CASE LIST ----");               
                if (!Integer.toString(0).matches(req[1])) { // Ne s'exécute qe s'il y a des résultats à la recherche
System.out.println("# LIST - Nombre de resultats = " + req[1]);
                    int j = 1;
                    for(int i = 0; !Integer.toString(i).matches(req[1]); i ++){
System.out.println("# Recuperation CLIENT_" + (i+1) + "...");
                        clients.add(new Client (req[j+2],req[j+4],req[j+6],req[j+10],req[j+12],req[j+14],Integer.toString(i)));
System.out.println("# Ajout du client dans l'Arraylist CLIENTS...");
                        j += 14; // permet de récupérer les bonnes valeurs
                    }
            	}
            }
            catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                client.setChaine("Case LIST - Erreur format");
            }
            break;
        //Affiche un profil
        case "PROF":
            try {
System.err.println("---- CASE PROF ----");

            	for(int i = 1; i < (req.length) && !client.getChaine().equals("Erreur GPC"); i += 2)
            	{
System.out.println("# PROF - "+ req[i] + " : " + req[i+1]);
            		switch (req[i])
            		{
                            //Si dans la req[1] il y a "1", alors la requete est speciale (requete de recuperation d'info du client lors de sa connexion)
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
                client.setChaine("Case PROF - Erreur format");
            }
            break;
        case "MSSG":
System.err.println("---- CASE MSSG ----");            
            if(Integer.toString(0).matches(req[1]))
            {
System.out.println("# MSSG - Aucun message");                
                //Mettre un message pas de resultat
            }
            else
            {
System.out.println("# MSSG - Nombre de messages : " + req[1]);                
                for(int i = 2; i < (req.length-2); i += 8)
                {
                    expediteur = req[i+3];
                    mailRecu = req[i+7];
                    messagerie.add(expediteur + "|" + mailRecu);
System.out.println("# MSSG - Expéditeur : " + req[i+3] + " - message : " + req[i+7]);
                }
            }
            break;
        case "P2PH" :
System.err.println("---- CASE P2PH ----");            
            // Retour de la hashtable depuis le serveur
System.out.println("# P2PH - Clients connectés : " + req[1]);
            for (int i = 1; i < req.length; i++){
                clients_co.add(req[i]);
            }
System.out.println("# Clients ajoutés à la liste...");            
            break;
        case "P2PC" :
System.err.println("---- CASE P2PC ----");            
            // Reception demande de connexion (req[1]= portEcoute // req[2]= nomDest)
System.out.println("# P2PC - Destinataire : " + req[2]);
            break;
        case "P2PM" :
System.err.println("---- CASE P2PM ----");            
            // Reception message P2P
System.out.println("# P2PM - Message : " + req[1]);
            client.setChaine(req[1]);
            break;
        case "DECO" :
System.err.println("---- CASE DECO ----");
System.out.println("# Vidage des infos du client connecté...");
            client.setMailCo(null);
            client.setNomCo(null);
            client.setPrenomCo(null);
            client.setChaine("Vous êtes bien déconnectés.");
System.out.println("# Fin de la connexion identifiée...");            
            break;
        default :
System.err.println("---- CASE DEFAULT ----");
System.out.println("# DEFAULT - req[1] : " + req[1]);
            client.setChaine("Case Default - Erreur dans votre choix");
            break;
        }
    }
}