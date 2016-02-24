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

public class Connexion extends GridPane {
    
    private final GestionProtocoleClient gp = new GestionProtocoleClient();
    
    private Client client = new Client();

    private Text titre;
    private Label identifiant, mdp;
    private TextField saisie_id;
    private PasswordField saisie_mdp;
    private Button seConnecter, retour;
        
    public void seConnecter (Stage fenetre_menu, Scene rootScene){
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("Connexion");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        identifiant = new Label("Mail utilisateur : ");
        this.add(identifiant, 0, 1);

        saisie_id = new TextField();
        this.add(saisie_id, 1, 1);

        mdp = new Label("Mot de passe :");
        this.add(mdp, 0, 2);

        saisie_mdp = new PasswordField();
        this.add(saisie_mdp, 1, 2);
        
        seConnecter = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(seConnecter);
        this.add(hbBtn, 1, 4);
        
        seConnecter.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle (ActionEvent e) {
                client.setMail(saisie_id.getText());
                client.setMdp(saisie_mdp.getText());
                
                gp.requeteConx(client.getMail(), client.getMdp());
                client = gp.getClient();
                // POP-UP DU MESSAGE DE RESULTAT
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(fenetre_menu);
                alert.setTitle("Connect - information");
                alert.setHeaderText("Message");
                alert.setContentText(client.getChaine());
                alert.showAndWait();
                
                if (client.getChaine().equals("Vous etes bien connectes."))
                {
System.out.println("CONX - infos : " + client.getNom() + " " + client.getPrenom());
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client.getNom(), client.getPrenom());
                    fenetre_menu.setScene(scene_menuC);
                }
                else
                {
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menu, scene_menuA);
                    fenetre_menu.setScene(scene_menuA);
                }
            }
        });
        
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 5);
        
        retour.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle (ActionEvent e) {
                MenuAnonyme menuA = new MenuAnonyme();
                Scene scene_menuA = new Scene(menuA);
                menuA.menuAnonyme(fenetre_menu, scene_menuA);
                fenetre_menu.setScene(scene_menuA);

            }
        });
        
    }
}
