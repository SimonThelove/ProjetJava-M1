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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import socketsTCP.SocketClient;

/**
 *
 * @author Yohann
 */
public class EnvoiMessage extends GridPane {
    
    // Cette classe permet l'envoi de messages de type mail à un autre client
    // Ces messages sont stockés dans une base de données accessible par le serveur
        
    private GestionProtocoleClient gp;
    private Text titre;
    private Label mail, message;
    private TextArea saisie_message;
    private TextField saisie_mail;
    private String mailDest, messageEnvoyer;
    private Button modifier, retour;
    
    public void envoyerMessage (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket, String mailDestinataire) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

System.out.println(">>>> Lancement EnvoiMessage.java");
        
        this.gp = new GestionProtocoleClient(socket);
        
        titre = new Text("Envoyer un message");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labels et TextField
        mail = new Label("Adresse mail destinataire :");
        this.add(mail, 0, 1);

        saisie_mail = new TextField();
        
        //Dans le cas d'une réponse, on pré-remplit le mail
        if(!mailDestinataire.isEmpty() || mailDestinataire == null)
            saisie_mail.setText(mailDestinataire);
        this.add(saisie_mail, 1, 1);

        message = new Label("Message a envoyer :");
        this.add(message, 0, 2);

        saisie_message = new TextArea();
        this.add(saisie_message, 1, 2);
        
        //Creation du bouton Valider
        modifier = new Button("Valider");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(modifier);
        this.add(hbBtn, 1, 5);
                
        //Action lors de l'appui sur le bouton Valider
        modifier.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
                    
System.out.println("Verification message...");
                    
                    if(saisie_mail.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Mail manquant !");
                        alert.setContentText("Veuillez saisir le mail destinataire.");
                        alert.showAndWait();
                        mail.setTextFill(Color.RED);
                        message.setTextFill(Color.BLACK);
                    }
                    else if(saisie_message.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Message manquant !");
                        alert.setContentText("Veuillez saisir votre message.");
                        alert.showAndWait();
                        message.setTextFill(Color.RED);
                        mail.setTextFill(Color.BLACK);
                    }
                    else
                    {
System.out.println("Envoi message...");
                        
                        //On recupere le mail destinataire et le message
                        mailDest = saisie_mail.getText();
                        messageEnvoyer = saisie_message.getText();

                        //Creation de la requete a envoyer puis execution de la requete Modification
                        gp.requeteEnvoiMsg(mailDest, messageEnvoyer, client);

                        // POP-UP de message de resultat
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(fenetre_menu);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Message");
                        alert.setContentText(client.getChaine());
                        alert.showAndWait();
System.out.println(">> Fermeture de EnvoiMessage.java (Exec OK)");

                        // Retour au menu connecte
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket);
                        fenetre_menu.setScene(scene_menuC);
                    }
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 6);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Renvoit le client au menu connecte
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de EnvoiMessage.java (Retour)");
                    
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        ); 
    }
}