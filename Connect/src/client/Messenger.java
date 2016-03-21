package client;


import gestionProtocole.GestionProtocoleClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import socketsTCP.SocketClient;
import socketsTCP.SocketMessenger;

/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */

/**
 *
 * @author Simon
 */
public class Messenger extends GridPane {
    
    // La classe Messenger correspond à l'interface graphique de conversation Peer-to-Peer (P2P)
    // Elle est accessible pour tous les utilisateurs, identifiés ou anonymes
    // Elle permet de récupérer la liste des connectés
    // Elle permet d'établir une connexion avec un client sélectionné
    // Elle permet d'échanger avec ce client sans passer par le serveur
    // Elle permet de fermer une conversation avec un client sans passer par le serveur
    
    // Gestion des protocoles d'échange
    private GestionProtocoleClient gpClient;
    
    // Sockets de communications
    private SocketClient socketClient;
    private SocketMessenger socketMsgr;
    
    // Variable de stockage des infos du client local
    private Client clientLocal;
    
    // Liste observable des clients connectés
    private ObservableList<String> clients_co;
    
    // Variable locale pour la gestion de l'index du client sélectionné
    private String clientSelectionne;
    
    // Objets d'affichage
    private Stage fenetre;
    private Button actualiser, contacter, envoyer, retour;
    private TextArea conversation;
    private TextField saisie_msg;
    private ListView<String> liste_clients;
    
    // Constructeur de la classe Messenger
    // Obligatoire pour initialiser une ObservableList
    // + Construction d'un socket dédié au messenger P2P

    public Messenger() {
        this.clients_co = FXCollections.observableArrayList();
        
    }
    
    // Méthode appelée au lancement de la classe Messenger (depuis un menuAnonyme/Connecte)
    // Elle prend en paramètres les objets nécessaires à la création de l'affichage
    // Elle prend également les informations du client local et les informations des sockets
    // Le SocketClient pour échanger avec le serveur
    // Le SocketMessenger pour échanger en P2P
    
    public void dialoguer(Stage fenetre_menu, Scene rootScene, Client clientConnecte, SocketClient socClient, SocketMessenger socMsgr) {
        
        // On commence par initialiser la variable fenetre
        // pour pouvoir afficher des alertes basées sur la fenêtre parente
        this.fenetre = fenetre_menu;
        
        // On initialise ensuite la variable clientLocal
        // pour la différencier d'un autre objet Client potentiel
        this.clientLocal = clientConnecte;
        
        // On initialise également les socket locaux
        // pour pouvoir les utiliser dans toutes les méthodes de la classe
        this.socketClient = socClient;
        this.socketMsgr = socMsgr;
        
        // On instancie les variables non initialisées
        this.gpClient = new GestionProtocoleClient(socClient);
        
        // On initialise ensuite les attributs nécessaires à l'affichage
        this.setPrefSize(600, 600);                     // Taille de 600 x 600
        this.setHgap(10);                               // Espacements horizontaux de 10
        this.setVgap(10);                               // Espacements verticaux de 10
        this.setPadding(new Insets(25, 25, 25, 25));    // Marges de la zone de travail de 25
        
// On fait un affichage console pour simplifier la gestion des erreurs
// (tous les affichages console servent au debug et aucun n'est indenté)
System.out.println(">> Lancement de Messenger.java");
        
        // On génère les zones d'affichage
        conversation = new TextArea();                  // Construction d'une zone de texte
        conversation.setPrefSize(400, 500);             // Taille de 400 x 500
        conversation.setEditable(false);                // Zone non modifiable
        this.add(conversation, 0, 0, 2, 1);             // Ajout au GridPane en position (0,0) sur 2 col, 1 ligne
        
        saisie_msg = new TextField();                   // Construction d'une ligne de texte
        saisie_msg.setPrefWidth(250);                   // Largeur de 250
        this.add(saisie_msg, 0, 1);                     // Ajout au GridPane en position (0,1)
        
        liste_clients = new ListView<>(clients_co);     // Construction d'une vue de la ObservableList clients_co
        gpClient.requeteP2PH(clientLocal);              // Récupération des clients connectés auprès du serveur
        this.clients_co = gpClient.getClients_co();     // Initialisation de la ObservableList clients_co locale
        liste_clients.setItems(clients_co);             // Initialisation des objets dans la vue
        liste_clients.setPrefSize(200, 550);            // Taille de la liste 200 x 550
        this.add(liste_clients, 2, 0, 2, 1);            // Ajout au GridPane en position (2,0) sur 2 col, 1 ligne
        
// Affichage debug
System.out.println("Création affichage OK...");

        // On génère les boutons
        envoyer = new Button("Envoyer");                // Construction d'un bouton envoyer
        HBox hbEnvoi = new HBox(10);                    // Construction d'une boîte pour affichage
        hbEnvoi.setAlignment(Pos.BOTTOM_RIGHT);         // Positionnement dans la boite en bas à droite
        hbEnvoi.getChildren().add(envoyer);             // Ajout du bouton dans la boîte
        this.add(hbEnvoi, 1, 1);                        // Ajout de la boîte au GridPane en position (1,1)
        
        actualiser = new Button("Actualiser");          // Construction d'un bouton envoyer
        HBox hbActualise = new HBox(10);                // Construction d'une boîte pour affichage
        hbActualise.setAlignment(Pos.BOTTOM_RIGHT);     // Positionnement dans la boite en bas à droite
        hbActualise.getChildren().add(actualiser);      // Ajout du bouton dans la boîte
        this.add(hbActualise, 2, 1);                    // Ajout de la boîte au GridPane en position (2,1)
        
        retour = new Button("Retour");                  // Construction d'un bouton envoyer
        HBox hbRetour = new HBox(10);                   // Construction d'une boîte pour affichage
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);        // Positionnement dans la boite en bas à droite
        hbRetour.getChildren().add(retour);             // Ajout du bouton dans la boîte
        this.add(hbRetour, 3, 1);                       // Ajout de la boîte au GridPane en position (3,1)
        
// Affichage debug
System.out.println("Création boutons OK...");
        
        // Actions effectuées par le bouton ENVOYER
        envoyer.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
// Affichage debug
System.out.println("Envoi du message saisi...");

                // Appel d'une méthode de la classe
                envoyer(saisie_msg.getText());
                
            } 
        });
        
        // Actions effectuées par le bouton ACTUALISER
        actualiser.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
// Affichage debug
System.out.println("Actualisation de clients_co...");

                // Appel d'une méthode de la classe
                actualiser();
            } 
        });
        
        // Actions effectuées par le bouton RETOUR
        retour.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
// Affichage debug                
System.out.println(">> Fermeture Messenger.java (Retour)");

                // Appel d'une méthode de la classe
                retour(fenetre_menu);
            } 
        });
        
    }
    
    // Méthode appelée depuis le bouton ENVOYER
    // Permet l'envoi d'un message saisi vers le client P2P sélectionné
    public void envoyer (String messageSaisi) {
        
        socketMsgr.envoyer(messageSaisi);                     // On transmet un message P2P
        
        conversation.setText(conversation.getText()           // On récupère la conversation affichée pour la mettre à jour
                + clientLocal.getPrenom()                     // On insère le nom du client local
                + " : " + saisie_msg.getText()                // On ajoute un séparateur suivi du message envoyé
                + System.lineSeparator());                    // On fait un retour à la ligne
        
        String retour = socketMsgr.recevoir();                // On attend une réponse ou une notification
        
        conversation.setText(conversation.getText()           // On récupère la conversation affichée pour la mettre à jour
        + retour                                              // On ajoute le message reçu
        + System.lineSeparator());                            // On fait un retour à la ligne
        
    }
    
    // Méthode appelée depuis le bouton ACTUALISER
    public void actualiser () {
        
        // On vide la liste des clients connectés affichée
        this.clients_co.clear();
        
        // On ré-exécute la demande de la liste des clients connectés au serveur
        gpClient.requeteP2PH(clientLocal);              // Récupération des clients connectés auprès du serveur
        this.clients_co = gpClient.getClients_co();     // Initialisation de la ObservableList clients_co locale
        liste_clients.setItems(clients_co);             // Initialisation des objets dans la vue
        
        
    }
    
    // Méthode appelée depuis le bouton RETOUR
    public void retour (Stage fenetre_menu) {
        
        // ACTION POUR LE CLIENT DISTANT :
        socketMsgr.envoyer("P2PN|close|" + clientSelectionne);  // On transmet une notification de fermeture de la conversation P2P
        
        // ACTION POUR LE CLIENT LOCAL :
        // S'il est connecté, il retourne au menuConnecte
        if(clientLocal.getMail()!=null)
        {
            MenuConnecte menuC = new MenuConnecte();
            Scene scene_menuC = new Scene(menuC);
            menuC.menuConnecte(fenetre_menu, scene_menuC, clientLocal, gpClient, socketClient, socketMsgr);
            fenetre_menu.setScene(scene_menuC);
        }
        // Sinon il retourne au menuAnonyme
        else
        {
            MenuAnonyme menuA = new MenuAnonyme();
            Scene scene_menuA = new Scene(menuA);
            menuA.menuAnonyme(fenetre_menu, scene_menuA, clientLocal, socketClient, socketMsgr);
            fenetre_menu.setScene(scene_menuA);
        }
    } 
}
