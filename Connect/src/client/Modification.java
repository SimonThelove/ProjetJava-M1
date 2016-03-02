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
import javafx.scene.paint.Color;
import socketsTCP.SocketClient;

/**
 *
 * @author lamoure
 */
public class Modification extends GridPane {
        
    private GestionProtocoleClient gp;
    private Text titre;
    private Label nom, prenom, telephone, diplome, annee, competences;
    private TextField saisie_nom, saisie_prenom, saisie_telephone, saisie_diplome, saisie_annee, saisie_competences;
    private Button modifier, retour;
    
    public void modifier (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement de Modification.java"); 

        this.gp = new GestionProtocoleClient(socket);
        
        titre = new Text("Modification");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labels et TextField(pré-remplissage des TextField avec les info actuelles du client)
        nom = new Label("Nom :");
        this.add(nom, 0, 1);

        saisie_nom = new TextField();
        this.add(saisie_nom, 1, 1);
        saisie_nom.setText(client.getNom());

        prenom = new Label("Prenom :");
        this.add(prenom, 0, 2);

        saisie_prenom = new TextField();
        this.add(saisie_prenom, 1, 2);
        saisie_prenom.setText(client.getPrenom());
        
        telephone = new Label("Telephone :");
        this.add(telephone, 0, 3);

        saisie_telephone = new TextField();
        this.add(saisie_telephone, 1, 3);
        saisie_telephone.setText(client.getTel());
              
        diplome = new Label("Diplome :");
        this.add(diplome, 0, 4);

        saisie_diplome = new TextField();
        this.add(saisie_diplome, 1, 4);
        saisie_diplome.setText(client.getDiplome());
        
        annee = new Label("Année :");
        this.add(annee, 0, 5);

        saisie_annee = new TextField();
        this.add(saisie_annee, 1, 5);
        saisie_annee.setText(client.getAnnee());
        
        competences = new Label("Compétences :");
        this.add(competences, 0, 6);

        saisie_competences = new TextField();
        this.add(saisie_competences, 1, 6);
        saisie_competences.setText(client.getCompetences());
        
        //Creation du bouton Valider
        modifier = new Button("Valider");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(modifier);
        this.add(hbBtn, 1, 8);
        
        //Action lors de l'appui sur le bouton Valider
        modifier.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println("Verification champs..."); 
                    
                    //Affichage POPUP car il n'y as pas le nom de renseigne
                    if(saisie_nom.getText().length() == 0)
                    {
                        //Affichage POPUP car il n'y as pas au moins un champs renseigne
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Information manquante !");
                        alert.setContentText("Veuillez saisir votre nom.");
                        alert.showAndWait();
                        nom.setTextFill(Color.RED);
                        prenom.setTextFill(Color.BLACK);
                    }
                    //Affichage POPUP car il n'y as pas le prenom de renseigne
                    else if(saisie_prenom.getText().length() == 0)
                    {
                        //Affichage POPUP car il n'y as pas au moins un champs renseigne
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Information(s) manquante(s) !");
                        alert.setContentText("Veuillez saisir votre prenom.");
                        alert.showAndWait();
                        nom.setTextFill(Color.BLACK);
                        prenom.setTextFill(Color.RED);
                    }
                    //Sinon on execute le requete de modification
                    else
                    {
System.out.println("Recuperation des informations..."); 
                        
                        //Recuperation des informations des champs
                        client.setNom(saisie_nom.getText());
                        client.setPrenom(saisie_prenom.getText());
                        client.setTel(saisie_telephone.getText());
                        client.setDiplome(saisie_diplome.getText());
                        client.setAnnee(saisie_annee.getText());
                        client.setCompetences(saisie_competences.getText());
                        
System.out.println("Envoi de la requête..."); 

                        //Creation de la requete a envoyer puis execution de la requete Modification
                        gp.setMessage(client);
                        gp.requeteModi(client);

                        // POP-UP de message de resultat
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(fenetre_menu);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Message");
                        alert.setContentText(client.getChaine());
                        alert.showAndWait();

System.out.println(">> Fermeture de Modification.java (Exec OK)"); 
                        
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
        this.add(hbRetour, 1, 9);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Renvoit le client au menu connecte
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de Modification.java (Retour)"); 
                    
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        ); 
    }
}
