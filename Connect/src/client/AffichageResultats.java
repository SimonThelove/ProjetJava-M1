
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author lamoure
 */
public class AffichageResultats extends GridPane {

    private Button retour, consulter;
    private ListView resultats;
    private Text titre;
    private Label nom, mail, tel, diplome, competences;
    
    //Creation du menu Resultats
    public void afficherResultats(Stage fenetre_menu, Scene rootScene, Client client, GestionProtocoleClient gp) {
        
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
        
        ObservableList clients = FXCollections.observableArrayList();
        
        resultats = new ListView(clients);      
        for (int i = 0; i < gp.getClients().size(); i++){
System.out.println("Affchage : " + gp.getClients().get(i).getPrenom() + " " + gp.getClients().get(i).getNom());
            clients.add(i, gp.getClients().get(i).getPrenom() + " " + gp.getClients().get(i).getNom());
        }
        resultats.setItems(clients);
        this.add(resultats, 0, 2);
        
        //Creation du bouton consulter
        consulter = new Button("Consulter");
        HBox hbConsult = new HBox(10);
        hbConsult.setAlignment(Pos.BOTTOM_RIGHT);
        hbConsult.getChildren().add(consulter);
        this.add(hbConsult, 0, 3);
        
        //Action lors de l'appui du bouton consulter
        consulter.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) {
                    
                    gp.requeteCons(resultats.getSelectionModel().getSelectedIndex(), client);
System.out.println("Consultation profil - index " + resultats.getSelectionModel().getSelectedIndex());
                    AffichageResultats affichage = new AffichageResultats();
                    Scene scene_affichage = new Scene(affichage);
                    affichage.afficherProfil(fenetre_menu, scene_affichage, client);
                    fenetre_menu.setScene(scene_affichage);
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(retour);
        this.add(hbBtn, 0, 4);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle (ActionEvent e) {
                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(client.getMailCo()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, client);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, client);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
    
    public void afficherProfil(Stage fenetre_menu, Scene rootScene, Client client)
    {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("PROFIL de ");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0);
        
        //Creation des differents labels
        nom = new Label();
        nom.setText(client.getNom() + " " + client.getPrenom());
        nom.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(nom, 1, 0);
        
        mail = new Label();
        mail.setText("E-mail : " + client.getMail());
        this.add(mail, 0, 1, 2, 1);
        
        tel = new Label();
        tel.setText("Téléphone : " + client.getTel());
        this.add(tel, 0, 2);
        
        diplome = new Label();
        diplome.setText("Diplôme : " + client.getDiplome() + " (" + client.getAnnee() + ")");
        this.add(diplome, 0, 3);
        
        competences = new Label();
        competences.setText("Compétences : " + client.getCompetences());
        this.add(competences, 0, 4, 3, 1);
        
        //Creation du bouton retour
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
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
                {

                @Override
                public void handle (ActionEvent e)
                {

                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(client.getMailCo()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, client);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, client);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
}