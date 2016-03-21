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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import socketsTCP.SocketClient;
import socketsTCP.SocketMessenger;

/**
 *
 * @author lamoure
 */
public class Inscription extends GridPane {
    
    // Cette classe permet l'inscription d'un nouvel utilisateur
    // Elle est accessible depuis le menuAnonyme et ne permet pas l'accès au menuConnecte
    // Pour remplir l'intégralité des informations de son profil l'utilisateur devra se connecter
    // Les détails d'un profil ne sont gérables que depuis la classe Modifier
    
    private GestionProtocoleClient gp;
    private Text titre;
    private Label nom, prenom, mail, mdp;
    private TextField saisie_nom, saisie_prenom, saisie_mail;
    private PasswordField saisie_mdp;
    private Button sInscrire, retour;
    
    public void sInscrire (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket, SocketMessenger socMsgr) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement d'Inscription.java");
        
        this.gp = new GestionProtocoleClient(socket);

        titre = new Text("Inscription");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labels et TextFields
        nom = new Label("Nom :");
        this.add(nom, 0, 1);

        saisie_nom = new TextField();
        this.add(saisie_nom, 1, 1);

        prenom = new Label("Prenom :");
        this.add(prenom, 0, 2);

        saisie_prenom = new TextField();
        this.add(saisie_prenom, 1, 2);
        
        mail = new Label("Mail :");
        this.add(mail, 0, 3);

        saisie_mail = new TextField();
        this.add(saisie_mail, 1, 3);
        
        mdp = new Label("Mot de passe :");
        this.add(mdp, 0, 4);

        saisie_mdp = new PasswordField();
        this.add(saisie_mdp, 1, 4);
        
        //Creation du bouton s'inscrire
        sInscrire = new Button("S'inscrire");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(sInscrire);
        this.add(hbBtn, 1, 6);
        
        //Action lors de l'appui sur le bouton S'inscrire
        sInscrire.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println("Verification champs...");
                    
                    //Verification qu'aucun champs soit vide
                     if(saisie_nom.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Nom manquant !");
                        alert.setContentText("Veuillez saisir votre nom.");
                        alert.showAndWait();
                        nom.setTextFill(Color.RED);
                        prenom.setTextFill(Color.BLACK);
                        mail.setTextFill(Color.BLACK);
                        mdp.setTextFill(Color.BLACK);
                    }
                    else if(saisie_prenom.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Prenom manquant !");
                        alert.setContentText("Veuillez saisir votre prenom.");
                        alert.showAndWait();
                        prenom.setTextFill(Color.RED);
                        nom.setTextFill(Color.BLACK);
                        mail.setTextFill(Color.BLACK);
                        mdp.setTextFill(Color.BLACK);
                    }
                    else if(saisie_mail.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Mail manquant !");
                        alert.setContentText("Veuillez saisir votre mail.");
                        alert.showAndWait();
                        mail.setTextFill(Color.RED);
                        nom.setTextFill(Color.BLACK);
                        prenom.setTextFill(Color.BLACK);
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
                        nom.setTextFill(Color.BLACK);
                        prenom.setTextFill(Color.BLACK);
                        mail.setTextFill(Color.BLACK);
                    }
                    //Si aucun champs est vide, alors creation de la requete et execution de la requete
                    else
                    {
System.out.println("Envoi informations...");
                        
                        //Recuperation des informations renseignees
                        client.setNom(saisie_nom.getText());
                        client.setPrenom(saisie_prenom.getText());
                        client.setMail(saisie_mail.getText());
                        client.setMdp(saisie_mdp.getText());
                        
                        //Execution de la requete creationCompte
                        gp.requeteCrea(client);

                        // POP-UP du message de resultat
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(fenetre_menu);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Message");
                        alert.setContentText(client.getChaine());
                        alert.showAndWait();
System.out.println(">> Fermeture Inscription.java (Exec OK)");
                        
                        //Retour au menu anonyme
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, client, socket, socMsgr);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 7);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
                {
                @Override
                //Retour au menu anonyme
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture Inscription.java (Retour)");
                    
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menu, scene_menuA, client, socket, socMsgr);
                    fenetre_menu.setScene(scene_menuA);
                }
            }
        );
    }
}
