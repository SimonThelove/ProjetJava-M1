/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.scene.control.Alert;

import socketsTCP.SocketClient;

/**
 *
 * @author lamoure
 */

public class Connexion extends GridPane {
    
    private GestionProtocoleClient gp;
    private SocketClient soc;
    private Text titre;
    private Label identifiant, mdp;
    private TextField saisie_id;
    private PasswordField saisie_mdp;
    private Button seConnecter, retour;
       
    //Creation de la fenetre connexion au client
    public void seConnecter (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket){
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement de Connexion.java");      

        this.gp = new GestionProtocoleClient(socket);
        this.soc = socket;
        
        titre = new Text("Connexion");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 3, 1);
        
        //Creation des differents labels
        identifiant = new Label("Mail utilisateur : ");
        this.add(identifiant, 0, 1);

        saisie_id = new TextField();
        this.add(saisie_id, 1, 1);

        mdp = new Label("Mot de passe :");
        this.add(mdp, 0, 2);

        saisie_mdp = new PasswordField();
        this.add(saisie_mdp, 1, 2);
        
        //Creation du bouton seConnecter
        seConnecter = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(seConnecter);
        this.add(hbBtn, 1, 4);
        
        //Action lors de l'appui sur le bouton seConnecter
        seConnecter.setOnAction(new EventHandler<ActionEvent>()
                {
                @Override
                public void handle (ActionEvent e) {
                    
System.out.println("Vérification saisie...");         

                    //Verification qu'aucun champ soit vide
                    if(saisie_id.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Mail manquant !");
                        alert.setContentText("Veuillez saisir votre mail.");
                        alert.showAndWait();
                        identifiant.setTextFill(Color.RED);
                        mdp.setTextFill(Color.BLACK);
                    }
                    else if(saisie_mdp.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Mot de passe manquant !");
                        alert.setContentText("Veuillez saisir votre mot de passe.");
                        alert.showAndWait();
                        mdp.setTextFill(Color.RED);
                        identifiant.setTextFill(Color.BLACK);
                    }
                    //Sinon on execute la requete de connexion
                    else
                    {
System.out.println("Recuperation valeurs...");     

                        //Aucun champ n'est vide, alors on recupere les valeurs des deux champs
                        client.setMail(saisie_id.getText());
                        client.setMdp(saisie_mdp.getText());

                        //Appel a la fonction de connexion
                        gp.requeteConx(client.getMail(), client.getMdp(), client);
                        
                        //POP-UP DU MESSAGE DE RESULTAT
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(fenetre_menu);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Message");
                        alert.setContentText(client.getChaine());
                        alert.showAndWait();

                        //Si les identifiants sont bon et que la connexion c'est bien deroulee
                        if (client.getChaine().startsWith("Vous etes bien connecte"))
                        {
System.out.println("Recuperation informations de l'utilisateur identifié...");  

                            //Appel a la fonction pour recuperer les informations du client
                            gp.requeteNomConnecte(client);
System.out.println(">> Fermeture de Connexion.java (Connexion OK)");

                            //Creation du menu connecter
                            MenuConnecte menuC = new MenuConnecte();
                            Scene scene_menuC = new Scene(menuC);
                            menuC.menuConnecte(fenetre_menu, scene_menuC, gp.getClients().get(0), gp, soc);
                            fenetre_menu.setScene(scene_menuC);
                        }
                        //Sinon on le renvoit au menu principal anonyme
                        else
                        {
System.out.println(">> Fermeture de Connexion.java (Echec Connexion)");
                            
                            MenuAnonyme menuA = new MenuAnonyme();
                            Scene scene_menuA = new Scene(menuA);
                            menuA.menuAnonyme(fenetre_menu, scene_menuA, client, soc);
                            fenetre_menu.setScene(scene_menuA);
                        }
                    }
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 5);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {            
                @Override
                //On renvoit le client au menuAnonyme
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de Connexion.java (Retour)");
                    
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menu, scene_menuA, client, socket);
                    fenetre_menu.setScene(scene_menuA);
                }
            }
        );
    }
}