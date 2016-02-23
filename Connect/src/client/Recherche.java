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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import gestionProtocole.GestionProtocoleClient;

/**
 *
 * @author lamoure
 */
public class Recherche extends GridPane {
    
    private final Client client = new Client();
    private final GestionProtocoleClient gp = new GestionProtocoleClient();
    
    private String motsCherches;
    
    private Text titre;
    private Label motsCles;
    private TextField saisie_motsCles;
    private Hyperlink rechercheAvancee;
    private Button rechercher;
    
    private Label nom, prenom, mail, diplome, annee, competences;
    private TextField saisie_nom, saisie_prenom, saisie_mail, saisie_diplome, saisie_annee, saisie_competences;
    private Button rechercherPlus;

    public void rechercher(Stage fenetre_menu, Scene rootScene) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("Recherche");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        motsCles = new Label("Mots clés :");
        this.add(motsCles, 0, 1);

        saisie_motsCles = new TextField();
        this.add(saisie_motsCles, 1, 1);
        
        rechercheAvancee = new Hyperlink("Recherche Avancée");
        this.add(rechercheAvancee, 0, 2);
        
        rechercheAvancee.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e) {
                Recherche recherche = new Recherche();
                Scene scene_recherche = new Scene(recherche);
                recherche.rechercheAvancee(fenetre_menu, scene_recherche);
                fenetre_menu.setScene(scene_recherche);
            }
    });
        
        rechercher = new Button("Rechercher");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(rechercher);
        this.add(hbBtn, 1, 2);
        
        rechercher.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
               motsCherches = saisie_motsCles.getText();
               
               gp.requeteRechMotsCles(motsCherches);
               
               AffichageResultats affichage = new AffichageResultats();
               Scene scene_affichage = new Scene(affichage);
               affichage.afficherResultats(fenetre_menu, scene_affichage);
               fenetre_menu.setScene(scene_affichage);
            }
        });
    }
    
    public void rechercheAvancee (Stage fenetre_menu, Scene rootScene) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("Recherche Avancée");
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
        
        mail = new Label("E-mail :");
        this.add(mail, 0, 3);

        saisie_mail = new TextField();
        this.add(saisie_mail, 1, 3);
        
        diplome = new Label("Diplôme :");
        this.add(diplome, 0, 4);

        saisie_diplome = new TextField();
        this.add(saisie_diplome, 1, 4);
        
        annee = new Label("Année :");
        this.add(annee, 0, 5);

        saisie_annee = new TextField();
        this.add(saisie_annee, 1, 5);
        
        competences = new Label("Compétences :");
        this.add(competences, 0, 6);

        saisie_competences = new TextField();
        this.add(saisie_competences, 1, 6);
        
        rechercherPlus = new Button("Rechercher");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(rechercherPlus);
        this.add(hbBtn, 1, 8);
                
        
        rechercherPlus.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
               client.setNom(saisie_nom.getText());
               client.setPrenom(saisie_prenom.getText());
               client.setTel(saisie_mail.getText());
               client.setDiplome(saisie_diplome.getText());
               client.setAnnee(saisie_annee.getText());
               client.setCompetences(saisie_competences.getText());
               
               //gp.requeteRechNom(client.getNom(), client.getPrenom(), client.getTel(), client.getDiplome(), client.getAnnee(), client.getCompetences());
                
               AffichageResultats affichage = new AffichageResultats();
               Scene scene_affichage = new Scene(affichage);
               affichage.afficherResultats(fenetre_menu, scene_affichage);
               fenetre_menu.setScene(scene_affichage);
            }
        });
    }
}
