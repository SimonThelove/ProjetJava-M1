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
import javafx.scene.control.TextArea;
import socketsTCP.SocketMessenger;

/**
 *
 * @author Simon
 */
public class GestionProtocoleClient {
    
    private final ObservableList<Client> clients;
    private final ObservableList<String> clients_co;
    private final ObservableList<String> messagerie, messages;
    
    private String like, nbLike;
    private SocketClient soc; //recuperation du socket d'echange
    private SocketMessenger socMsgr; //recuperation du socket P2P
    private String message = null;//Variable ou est stockee la requete
    private int nbPersonne = 0;//Nombre de profil que renvoit les requetes de recherche
    private String req[] = null;//Requete de retour du serveur
    private Client clientRecherche;
    private Client clientContacte;
    private TextArea conversation;

    private String expediteur;  //Expediteur du message
    private String mailRecu;    //Contenu du message reçu
    private String id_mail;     //Identifiant du message reçu
    private int messagesNonLus; //Nombre de messages non lus
    private int nbMessages;     //Nombre de messages au total
    
    public GestionProtocoleClient(SocketClient socket) {
System.err.println("#### Construct GPC vers Serveur");         
        this.soc = socket;
        this.clients = FXCollections.observableArrayList();
        this.clients_co = FXCollections.observableArrayList();
        this.messagerie = FXCollections.observableArrayList();
        this.messages = FXCollections.observableArrayList();
        this.clientRecherche = new Client();
    }
    
    public GestionProtocoleClient(SocketMessenger socket) {
System.err.println("#### Construct GPC pour P2P");         
        this.socMsgr = socket;
        this.clients = FXCollections.observableArrayList();
        this.clients_co = FXCollections.observableArrayList();
        this.messagerie = FXCollections.observableArrayList();
        this.messages = FXCollections.observableArrayList();
        this.clientContacte = new Client();
    }

    public ObservableList<String> getClients_co() {
        return clients_co;
    }
    
    public ObservableList<Client> getClients() {
        return clients;
    }
    
    public ObservableList<String> getMessagerie(){
        return messagerie;
    }
        
    public ObservableList<String> getMessages(){
        return messages;
    }
    
    public TextArea getConversation() {
        return conversation;
    }
        
    public String getMessage() {
        return message;
    }
    
    public int getMessagesNonLus(){
        return messagesNonLus;
    }
    
    public int getNbMessages(){
        return nbMessages;
    }
    
    public String getIdMail() {
        return id_mail;
    }
    
     public Client getClientRecherche() {
        return clientRecherche;
    }

    public void setClientRecherche(Client clientRecherche) {
        this.clientRecherche = clientRecherche;
    }
    
    public String getLike() {
        return like;
    }
    
    public String getNbLike() {
        return nbLike;
    }
    
    // Methode de conception de requete (utilise depuis Modification.java)
    public void setMessage(Client client) {
System.err.println("[GPC] setMessage");         
        this.message =  "MAIL|" + client.getMail();    
        this.message += "|NOM|" + client.getNom();
        this.message += "|PRENOM|" + client.getPrenom();
        this.message += "|TELEPHONE|" + client.getTel();
        this.message += "|DIPLOMES|" + client.getDiplome();
        this.message += "|ANNEE_DIPLOMATION|" + client.getAnnee();
        this.message += "|COMPETENCES|" + client.getCompetences();
        this.message += " |";
System.out.println("[GPC] setMessage FIN -----");         
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(Client client){
System.err.println("[GPC] requeteCrea");                 
	//Creation de la requete
	message = "CREA|NOM|" + client.getNom() + "|PRENOM|" + client.getPrenom()  + "|MAIL|" + client.getMail() + "|MOTDEPASSE|" + client.getMdp();
        //Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteCrea FIN -----");         
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse, Client client){
System.err.println("[GPC] requeteConx");         

	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteConx FIN -----");                 
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles, Client client){
System.err.println("[GPC] requeteRechMotsCles");                 
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteRechMotsCles FIN -----");                 
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(Client client){
System.err.println("[GPC] requeteRechNom");         
        
	//Creation de la requete
	message = "RECH|NOM|" + client.getNom() +
                "|PRENOM|" + client.getPrenom() +
                "|MAIL|" + client.getMail() +
                "|DIPLOMES|" + client.getDiplome() +
                "|ANNEE_DIPLOMATION|" + client.getAnnee() +
                "|COMPETENCES|" + client.getCompetences() + " |";       
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteRechNom FIN -----");         
    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(int index, Client client){
System.err.println("[GPC] requeteCons");         
        // Creation de la requete
        message = "CONS|MAIL|" + clients.get(index).getMail() + "|0|";
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteConx FIN -----");
System.err.println("[GPC] requeteNbLike");         
        // Creation de la requete
        message = "LIKE|CIBLE|" + clients.get(index).getMail() + "|AUTEUR|" + client.getMail();
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteNbLike FIN -----");  

    }
	
    //Methode de concatenation de la requete pour recuperer les information du client qui se connecte
    public void requeteNomConnecte(Client client){
System.err.println("[GPC] requeteNomConnecte");         

	//Creation de la requete
	message = "CONS|MAIL|" + client.getMail() + "|1|";
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);        
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteNomConnecte FIN -----");         

    }
    
    //Methode de concatenation de la requete modification
    public void requeteModi(Client client){
System.err.println("[GPC] requeteModi");         

        String requete;
	//Creation de la requete
	requete = "MODI|" + message;        
	//Envoi du message a SocketClient
	requete = soc.echangeServeur(requete);
	//Appelle a la methode pour creer un affichage au client
	decoupage(requete, client);        
System.out.println("[GPC] requeteModi FIN -----"); 
	}
    
    //Methode de concatenation de la requete deconnexion
    public void requeteEnvoiMsg(String mailDes, String msg, Client client){
System.err.println("[GPC] requeteEnvoiMsg");         
       
	//Creation de la requete
	message = "MSSG|ENVOI|MAIL_EXP|" + client.getMail() + "|MAIL_DES|" + mailDes + "|MESSAGE|" + msg;        
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appel a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteEnvoiMsg FIN -----"); 
    }
    
    //Methode de concatenation de la requete de recuperation d'un message
    public void requeteRecupMsg(Client client){
System.err.println("[GPC] requeteRecupMsg");         
        
	//Creation de la requete
	message = "MSSG|RECUP|MAIL_DES|" + client.getMail();                
	//Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appel a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteRecupMsg FIN -----");         
    }
    
    //Méthode d'annonce de lecture d'un message
    public void lectureMessage(String index, Client client){
System.err.println("[GPC] requeteRecupMsg"); 
        //Creation de la requete
        message = "MSSG|LECT|ID_MAIL|" + index;
        //Envoi du message a SocketClient
        message = soc.echangeServeur(message);
        //Decoupage de la reponse pour un affichage client
        decoupage(message, client);
System.out.println("[GPC] requeteRecupMsg FIN -----");         
    }
    
    //Méthode de récupération de la liste des clients connectés
    public void requeteP2PH (Client client){
System.err.println("[GPC] requeteP2P");         
    
        //Creation de la requete
        if (client.getMail() != null)
            message = "P2PH|" + client.getPrenom() + " " + client.getNom();
        else
            message = "P2PH|null";
        //Envoi du message a SocketClient
        message = soc.echangeServeur(message);
        //appel a la methode pour creer un affchage client
        decoupage(message, client);       
System.out.println("[GPC] requeteP2P FIN -----");         
    }
    
    //Methode ce concatenation de la requete Like
    public void requeteLike(String likeOrUnlike, Client client, Client clientConnecte)
    {
System.err.println("[GPC] requeteLike"); 
        //Creation de la requete
        message = "LIKE|TYPE|" + likeOrUnlike + "|CIBLE|" + client.getMail() + "|AUTEUR|" + clientConnecte.getMail();
        System.err.println(message);
        //Envoi du message a SocketClient
        message = soc.echangeServeur(message);
        //Decoupage de la reponse pour un affichage client
        decoupage(message, client);
System.out.println("[GPC] requeteLike FIN -----");       
    }
    
    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(Client client){
System.err.println("[GPC] requeteDeco");         
        
	//Creation de la requete
	message = "DECO|";
        //Envoi du message a SocketClient
	message = soc.echangeServeur(message);
	//Appelle a la methode pour creer un affichage au client
	decoupage(message, client);
System.out.println("[GPC] requeteDeco FIN -----");         
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public void decoupage(String reponse, Client clientConnecte){
        
System.err.println("[GPC] decoupage");         
System.out.println("# ENTREE = " + reponse);

        clientConnecte.setChaine("Aucune information disponible");       
        req = reponse.split("[|]");
        
        switch(req[0]){
        //Recupere le message a afficher au client
        case "MSG":
            try {
System.err.println("---- CASE MSG ----");                
                clientConnecte.setChaine(req[1]);
System.out.println("# MSG - Retour client = " + clientConnecte.getChaine());
                //Si le message est le suivant, alors on met a jours les variables de connexion du client
                // (le message de bienvenu sera a jours directement sans passer par une reconnexion)
                if("Vos modifications ont ete prises en compte.".equals(req[1]))
                {
System.out.println("#! Modifications Nom/Prenom client");                    
                    //client.setNom(client.getNom());
                    //client.setPrenom(client.getPrenom());
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                clientConnecte.setChaine("Case MSG - Erreur Format");
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
System.out.println("# Recuperation CLIENT_" + (i+1));
                        clients.add(new Client (req[j+2],req[j+4],req[j+6],req[j+10],req[j+12],req[j+14],Integer.toString(i)));
System.out.println("# Ajout du client dans l'Arraylist CLIENTS");
                        j += 14; // permet de récupérer les bonnes valeurs
                    }
            	}
            }
            catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                clientConnecte.setChaine("Case LIST - Erreur format");
            }
            break;
            
        //Affiche un profil
        case "PROF":
            try {
System.err.println("---- CASE PROF ----");

            	for(int i = 1; i < (req.length) && !clientConnecte.getChaine().equals("Erreur GPC"); i += 2)
            	{
System.out.println("#! Recuperation champ " + i);
            		switch (req[i])
            		{
                            //Si dans la req[1] il y a "1", alors la requete est speciale (requete de recuperation d'info du client lors de sa connexion)
                            //On met des variables propres au client qui permettra la gestion des boutons retour menu au menuConnecte
                            case "1" :
                                i=0;
                                clientRecherche.setMail(req[3]);
                                clientRecherche.setNom(req[5]);
                                clientRecherche.setPrenom(req[7]);
                                break;
                            case "mail" : 
                                clientRecherche.setMail(req[i+1]);
                                break;
                            case "nom" :
            			clientRecherche.setNom(req[i+1]);
            			break;
            		    case "prenom" :
            		    	clientRecherche.setPrenom(req[i+1]);
                		break;
                            case "telephone" :
                                clientRecherche.setTel(req[i+1]);
                                break;
                            case "diplomes" :
                                clientRecherche.setDiplome(req[i+1]);
                                break;
                            case "annee_diplomation" :
                                clientRecherche.setAnnee(req[i+1]);
                		break;
                            case "competences" :
                		clientRecherche.setCompetences(req[i+1]);
                                break;        
                            default:
            		        clientRecherche.setChaine("Case default - Erreur req[" + i + "]");
                                break;
            		}
            	}
                clients.add(clientRecherche);
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                clientConnecte.setChaine("Case PROF - Erreur format");
            }
            break;
            
        case "MSSG":
System.err.println("---- CASE MSSG ----");            
            if(Integer.toString(0).matches(req[1]))
            {
System.out.println("#! MSSG - Aucun message");                
            }
            else
            {
System.out.println("# MSSG - Nombre de messages = " + req[1]);
                messagerie.clear();     // On vide la liste avant de la remplir
                nbMessages = 0;         // On initialise a zero le nombre de messages
                messagesNonLus = 0;     // On initialise a zero les messages non lus
                for(int i = 2; i < (req.length-2); i += 10)
                {
                    nbMessages ++;
                    id_mail = req[i+1];
                    expediteur = req[i+3];
                    mailRecu = req[i+7];
                    if(req[i+9].equalsIgnoreCase("0"))
                        messagesNonLus ++;
                    messagerie.add(expediteur + " - mail n° " + id_mail);
                    messages.add(expediteur + "|" + mailRecu);
System.out.println("# MSSG - Expéditeur : " + req[i+3] + " - message n° " + nbMessages + " : " + req[i+7]);
                }
            }
            clientConnecte.setChaine(Integer.toString(nbMessages));
            break;
            
        case "P2PH" :
System.err.println("---- CASE P2PH ----");            
            // Retour de la hashtable depuis le serveur
System.out.println("# P2PH - Clients connectés = " + req[1]);
System.out.println("# P2PH - Vidage de la table clients_co");            
            clients_co.clear();
            for (int i = 1; i < req.length; i++){
                clients_co.add(req[i]);
            }
System.out.println("# Clients ajoutés à la liste");            
            break;
        /*
        case "P2PM" :
System.err.println("---- CASE P2PM ----");            
            // Reception message P2P
System.out.println("# P2PM - Message reçu = " + req[1]);
            clientConnecte.setChaine(req[1]);
            conversation.setText(conversation.getText() + clientConnecte.getChaine() + System.lineSeparator());
            break;
        case "P2PN" :
System.err.println("---- CASE P2PN ----");
            // Notification de réception
            switch (req[1]){
                            case "ok":
                                // Retour du serveur pour demande de conversation P2P
                                clientConnecte.setChaine("Demande de connexion envoyée.");
            System.out.println("# P2PN - Confirmation de lancement de la conversation");
                                break;
                            case "hello":
                                // Reception d'une demande pour lancer une conversation P2P
                                clientConnecte.setChaine("Demande de connexion reçue.");
            System.out.println("# P2PN - Lancement de la conversation");
                                break;
                            case "close":
                                // Fermer la conversation P2P avec le client distant
                                clientConnecte.setChaine("Le client distant a quitté la conversation.");
            System.out.println("# P2PN - Fermeture de la conversation");
                                break;
                            default:
                                // Reception d'une notification inconnue
                                clientConnecte.setChaine("Erreur notification P2P.");
            System.out.println("# P2PN - Erreur notification P2P");
                        }
System.out.println("# P2PN - Message reçu");
            break;
            */
        case "LIKE" :
System.err.println("---- CASE LIKE ----");
            // Recuperation du nombre de like
            nbLike = req[2];
            // Recuperation de l'information si le client like ou non le profil
            like = req[4];
System.out.println("Nb de like : " + req[2] + " like=1|unlike=0 : " + req[4]);
System.out.println("# P2PN - Message reçu");
            break;
        case "DECO" :
System.err.println("---- CASE DECO ----");
System.out.println("# DECO - Fermeture connexion");
            // Reset des informations du client connecté
            clientConnecte.setMail(null);
            clientConnecte.setNom(null);
            clientConnecte.setPrenom(null);
            clientConnecte.setAnnee(null);
            clientConnecte.setCompetences(null);
            clientConnecte.setDiplome(null);
            clientConnecte.setMdp(null);
            clientConnecte.setTel(null);
            clientConnecte.setChaine("Vous êtes bien déconnectés.");
            break;
            
        default :
System.err.println("---- CASE DEFAULT ----");
System.out.println("# DEFAULT - req[1] : " + req[1]);
            clientConnecte.setChaine("Case Default - Erreur dans votre choix");
            break;
        }
System.out.println("[GPC] decoupage FIN -----");
    }
}