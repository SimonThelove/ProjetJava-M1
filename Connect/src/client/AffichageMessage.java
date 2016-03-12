/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import gestionProtocole.GestionProtocoleClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socketsTCP.SocketClient;

/**
 *
 * @author lamoure
 */
public class AffichageMessage extends GridPane {
    
    // On reprend le code de la classe envoyer message
    // on pré-remplit les affichages et on ne permet pas leur édition
    // Seules quelques modifications ont été apportées, notamment au niveau des boutons
    
    private GestionProtocoleClient gp;
    private Text titre;
    private Label mail, message;
    private TextArea affichage_message;
    private TextField affichage_mail;
    private String temp, mailExp, messageRecu;
    private int index;
    private Button repondre, retour;
    
    public void afficherMessage (Stage fenetre_menu, Scene rootScene, GestionProtocoleClient gp, Client client, SocketClient socket, int messageSelectionne) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

System.out.println(">>>> Lancement AfficherMessage.java");
        
        this.gp = gp;
        
        //Recuperation du message selectionne
        temp = gp.getMessages().get(messageSelectionne - 1); // messageSelectionee - 1 correspond a l'index du message selectionne
        index = temp.indexOf("|");
        mailExp = temp.substring(0,index);
        messageRecu = temp.substring(index + 1);
        
        //Recuperation de l'id_mail du message selectionne
        temp = gp.getMessagerie().get(messageSelectionne - 1); // messageSelectionee - 1 correspond a l'index du message selectionne
        index = temp.indexOf("°");
        temp = temp.substring(index + 2);   // index + 2 nous positionne sur l'id_mail dans la lste messagerie
        
        //Annonce au serveur de la lecture du message
        gp.lectureMessage(temp, client);
        
        titre = new Text("Message numéro " + messageSelectionne);
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        //Creation des differents labels et TextField
        mail = new Label("Adresse mail expéditeur :");
        this.add(mail, 0, 1);
        
        message = new Label("Message reçu :");
        this.add(message, 0, 2);

        // Affichage de l'expéditeur
        affichage_mail = new TextField();
        affichage_mail.setText(mailExp);
        affichage_mail.setEditable(false);
        this.add(affichage_mail, 1, 1);

        // Affichage du message reçu
        affichage_message = new TextArea();
        affichage_message.setText(messageRecu);
        affichage_message.setEditable(false);
        this.add(affichage_message, 1, 2);
        
        //Creation du bouton Valider
        repondre = new Button("Répondre");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(repondre);
        this.add(hbBtn, 1, 5);
                
        //Action lors de l'appui sur le bouton Valider
        repondre.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println(">> Fermeture AffichageMessage.java (Répondre)");
                    // Affichage de l'interface envoiMessage
                    EnvoiMessage envoiMessage = new EnvoiMessage();
                    Scene scene_envoiMessage = new Scene(envoiMessage);
                    envoiMessage.envoyerMessage(fenetre_menu, scene_envoiMessage, client, socket, mailExp);
                    fenetre_menu.setScene(scene_envoiMessage);
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
                //Renvoie le client au menu précédent
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture AffichageMessage.java (Retour)");
                    
                    RecuperationMessage recuperationMessage = new RecuperationMessage();
                    Scene scene_recuperationMessagee = new Scene(recuperationMessage);
                    recuperationMessage.recupererMessage(fenetre_menu, scene_recuperationMessagee, gp, client, socket);
                    fenetre_menu.setScene(scene_recuperationMessagee);
                }
            }
        ); 
    }
}
