
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
import socketsTCP.SocketClient;

/**
 *
 * @author lamoure
 */
public class AffichageResultats extends GridPane {
    
    // Cette classe permet l'affichage des résultats d'une recherche (afficherResultats)
    // Elle permet egalement l'affichage d'un profil utilisateur (afficherProfil)

    private Button retour, consulter, like;
    private ListView resultats;
    private Text titre;
    private Label nom, mail, tel, diplome, competences;
    private String likeOrUnlike;
    
    //Creation du menu Resultats
    public void afficherResultats(Stage fenetre_menu, Scene rootScene, Client clientConnecte, GestionProtocoleClient gp, SocketClient socket) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement AffichageResultats.java");
        titre = new Text("Resultats");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        ObservableList clients = FXCollections.observableArrayList();

        resultats = new ListView(clients);

        for (int i = 0; i < gp.getClients().size(); i++){
            clients.add(i, gp.getClients().get(i).getPrenom() + " " + gp.getClients().get(i).getNom());
        }
        resultats.setItems(clients);
        this.add(resultats, 0, 2);
System.out.println("liste résultats OK..."); 

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
                    
System.out.println("Selection d'un profil...");                    
                    gp.requeteCons(resultats.getSelectionModel().getSelectedIndex(), clientConnecte);
                    AffichageResultats affichage = new AffichageResultats();
                    Scene scene_affichage = new Scene(affichage);
                    affichage.afficherProfil(fenetre_menu, scene_affichage,clientConnecte, gp, socket);
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
System.out.println(">> Fermeture AffichageResultats.java (Retour depuis résultats)");

                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(clientConnecte.getMail()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, clientConnecte, gp, socket);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, clientConnecte, socket);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
    
    //AFFICHAGE EN MODE PROFIL (1 seul résultat ou consultation)
    public void afficherProfil(Stage fenetre_menu, Scene rootScene, Client clientConnecte, GestionProtocoleClient gp, SocketClient socket)
    {
  System.out.println("Affichage d'un profil...");      
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        Client client = gp.getClientRecherche();
        
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
        
        //Creation du bouton like ou unlike uniquement si le client est connecte
        if(clientConnecte.getMail()!=null)
        {
            // Si l'utilisateur n'aime pas le profil, alors creation du bouton like
            liker(gp);

            //Action lors de l'appui sur le bouton like ou unlike
            like.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle (ActionEvent e)
                    {
System.out.println(">> Debut like ou unlike profil");                
System.out.println("Envoi requête..."); 
                        //Appel a la fonction de rechercheMotsCles
                        gp.requeteLike(likeOrUnlike, client, clientConnecte);
                        
                        // Acutalisation du bouton like
                        liker(gp);
System.out.println(">> Fin like ou unlike");
                    }
                }
            );
        }
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(retour);
        this.add(hbBtn, 5, 7);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
                {

                @Override
                public void handle (ActionEvent e)
                {
System.out.println(">> Fermeture d'AffichageResultats.java (Retour depuis profil)");
                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(clientConnecte.getMail()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, clientConnecte, gp, socket);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, clientConnecte, socket);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
    
    // Méthode de gestion du texte sur le bouton like
    public void liker (GestionProtocoleClient gp) {
        
        if(gp.getLike().compareTo("0") == 0)
            {
                //Creation du bouton like
                like = new Button("Liker");
                HBox hbLike = new HBox(10);
                hbLike.setAlignment(Pos.BOTTOM_RIGHT);
                hbLike.getChildren().add(like);
                this.add(hbLike, 0, 6);
                likeOrUnlike = "1";
            }
            //Sinon creation du bouton unlike
            else if (gp.getLike().compareTo("1") == 0)
            {
                //Creation du bouton unlike
                like = new Button("UnLike");
                HBox hbLike = new HBox(10);
                hbLike.setAlignment(Pos.BOTTOM_RIGHT);
                hbLike.getChildren().add(like);
                this.add(hbLike, 0, 6);
                likeOrUnlike = "0";
            }
    }
}