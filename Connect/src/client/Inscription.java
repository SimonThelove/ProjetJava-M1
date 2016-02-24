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

/**
 *
 * @author lamoure
 */
public class Inscription extends GridPane {
    
    private Client client = new Client();
    
    private final GestionProtocoleClient gp = new GestionProtocoleClient();
    
    private Text titre;
    private Label nom, prenom, mail, mdp;
    private TextField saisie_nom, saisie_prenom, saisie_mail;
    private PasswordField saisie_mdp;
    private Button sInscrire;
    
    public void sInscrire (Stage fenetre_menu, Scene rootScene) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        titre = new Text("Inscription");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

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
        
        sInscrire = new Button("S'inscrire");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(sInscrire);
        this.add(hbBtn, 1, 6);
        
        
        
        sInscrire.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                client.setNom(saisie_nom.getText());
                client.setPrenom(saisie_prenom.getText());
                client.setMail(saisie_mail.getText());
                client.setMdp(saisie_mdp.getText());
                gp.requeteCrea(client.getNom(), client.getPrenom(), client.getMail(), client.getMdp());
                client = gp.getClient();
                // POP-UP DU MESSAGE DE RESULTAT
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(fenetre_menu);
                alert.setTitle("Connect - information");
                alert.setHeaderText("Message");
                alert.setContentText(client.getChaine());
                alert.showAndWait();
                // RETOUR MENU PRINCIPAL
                
                MenuAnonyme menuA = new MenuAnonyme();
                Scene scene_menuA = new Scene(menuA);
                menuA.menuAnonyme(fenetre_menu, scene_menuA);
                fenetre_menu.setScene(scene_menuA);
            }
        });

        
    }
}
