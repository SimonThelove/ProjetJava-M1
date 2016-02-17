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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author lamoure
 */
public class AffichageResultats extends GridPane {
    
    private Button retour;
    private TableView resultats;
    private Text titre;
    private Label nom, prenom, mail, tel, diplome, annee, competences;
    
    public void afficherResultats(Stage fenetre_menu, Scene rootScene) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        /**
         * 
         * AFFICHAGE EN MODE RESULTATS (0 ou Plusieurs)
         * 
         * > Aucun résultat = "Aucun résultat"
         * > Plusieurs résultats = Tableau sélectionnable (Nom, Prenom, Mail)
         *          >> Sélection du tableau = Recherche sur un seul profil (CONSULTATION)
         * 
         */
        titre = new Text("Resultats");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        resultats = new TableView();
        
        TableColumn nom = new TableColumn("Nom");
        TableColumn prenom = new TableColumn("Prénom");
        TableColumn mail = new TableColumn("E-mail");
        resultats.getColumns().addAll(nom, prenom, mail);
        this.add(resultats, 0, 2);
        
        retour = new Button("Retour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(retour);
        this.add(hbBtn, 0, 4);
        
        retour.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle (ActionEvent e) {
                //utilisateur.setID();
                //utilisateur.setMDP();
                
                // TEST AFFICHER PROFIL
                AffichageResultats affichage = new AffichageResultats();
                Scene scene_affichage = new Scene(affichage);
                affichage.afficherProfil(fenetre_menu, scene_affichage);
                fenetre_menu.setScene(scene_affichage);
                
                // IF CONNECTE
                /* 
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC);
                    fenetre_menu.setScene(scene_menuC);
                */
                // ELSE (NON CONNECTE)
                /* 
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menu, scene_menuA);
                    fenetre_menu.setScene(scene_menuA);
                */
            }
        });
    }
    
    public void afficherProfil(Stage fenetre_menu, Scene rootScene){
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("Profil");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        nom = new Label("NOM");
        nom.setText("Nom : ");
        this.add(nom, 0, 2);
        
        prenom = new Label();
        prenom.setText("Prénom : ");
        this.add(prenom, 2, 2);
        
        mail = new Label();
        mail.setText("E-mail : ");
        this.add(mail, 0, 3);
        
        tel = new Label();
        tel.setText("Téléphone : ");
        this.add(tel, 0, 4);
        
        diplome = new Label();
        diplome.setText("Diplôme : ");
        this.add(diplome, 4, 3);
        
        annee = new Label();
        annee.setText("Année d'obtention : ");
        this.add(annee, 4, 4);
        
        competences = new Label();
        competences.setText("Compétences : ");
        this.add(competences, 2, 5);
        
        retour = new Button("Retour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(retour);
        this.add(hbBtn, 5, 7);
        
        /**
         * 
         * AFFICHAGE EN MODE PROFIL (1 seul résultat ou consultation)
         * 
         */
        
        retour.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle (ActionEvent e) {
                //utilisateur.setID();
                //utilisateur.setMDP();
                
                // IF CONNECTE
                /* 
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC);
                    fenetre_menu.setScene(scene_menuC);
                */
                // ELSE (NON CONNECTE)
                 
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menu, scene_menuA);
                    fenetre_menu.setScene(scene_menuA);
                
            }
        });
    }
}
