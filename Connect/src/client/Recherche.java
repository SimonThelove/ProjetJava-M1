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
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import socketsTCP.SocketClient;
import socketsTCP.SocketMessenger;

/**
 *
 * @author lamoure
 */
public class Recherche extends GridPane {
    
    // La classe recherche, accessible pour tous les utilisateurs,
    // permet la recherche d'un utilisateur dans la base de données
    // cette dernière étant accessible depuis le serveur
        
    private GestionProtocoleClient gp;
    private Client clientRecherche;
    
    private String motsCherches;
    private Text titre;
    private Hyperlink rechercheAvancee;
    private Label nom, prenom, mail, tel, diplome, annee, competences, motsCles;
    private TextField saisie_nom, saisie_prenom, saisie_mail, saisie_tel, saisie_diplome, saisie_annee, saisie_competences, saisie_motsCles;
    private Button rechercher, retour, rechercherPlus;

    public Recherche() {
        super();
        this.clientRecherche = new Client();
    }

    public void rechercher(Stage fenetre_menu, Scene rootScene, Client clientConnecte, SocketClient socket, SocketMessenger socMsgr) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement de Recherche.java"); 
        
        this.gp = new GestionProtocoleClient(socket);
        
        titre = new Text("Recherche");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labels et textField
        motsCles = new Label("Mots clés :");
        this.add(motsCles, 0, 1);

        saisie_motsCles = new TextField();
        this.add(saisie_motsCles, 1, 1);
        
        //Creation d'un lien vers la recherche avancee
        rechercheAvancee = new Hyperlink("Recherche Avancee");
        this.add(rechercheAvancee, 0, 2);
        
        //Action lors de l'appui sur le lien rechercheAvancee
        rechercheAvancee.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println("Recherche avancée..."); 
                    
                    Recherche recherche = new Recherche();
                    Scene scene_recherche = new Scene(recherche);
                    recherche.rechercheAvancee(fenetre_menu, scene_recherche, clientConnecte, socket, socMsgr);
                    fenetre_menu.setScene(scene_recherche);
                }
            }
        );
        
        //Creation du bouton rechercher
        rechercher = new Button("Rechercher");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(rechercher);
        this.add(hbBtn, 1, 2);
        
        //Action lors de l'appui sur le bouton rechercher
        rechercher.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println("Vérification saisie..."); 

                   //Verification qu'aucun champs soit vide
                    if(saisie_motsCles.getText().length() == 0)
                    {
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Mot(s) Cle(s) manquant !");
                        alert.setContentText("Veuillez saisir au moins un mot cle.");
                        alert.showAndWait();
                        motsCles.setTextFill(Color.RED);
                    }
                    //Sinon on execute la requete de recheche
                    else
                    {
System.out.println("Récupération saisie..."); 
                        
                        //On recupere le(s) mot(s) cle(s)
                        motsCherches = saisie_motsCles.getText();
                        
System.out.println("Envoi requête..."); 
                        
                        //Appel a la fonction de rechercheMotsCles
                        gp.requeteRechMotsCles(motsCherches, clientConnecte);

System.out.println(">> Fermeture Recherche.java (Exec OK)"); 
                        
                        //On affiche le(s) resultat(s) de la recherche
                        AffichageResultats affichage = new AffichageResultats();
                        Scene scene_affichage = new Scene(affichage);
                        affichage.afficherResultats(fenetre_menu, scene_affichage, clientConnecte, gp, socket, socMsgr);
                        fenetre_menu.setScene(scene_affichage);
                    }
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 4);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture Recherche.java (Retour)"); 
                    
                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(clientConnecte.getMail()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, clientConnecte, gp, socket, socMsgr);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, clientConnecte, socket, socMsgr);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
    
    public void rechercheAvancee (Stage fenetre_menu, Scene rootScene, Client clientConnecte, SocketClient socket, SocketMessenger socMsgr) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        this.gp = new GestionProtocoleClient(socket);
        
System.out.println("Affichage champs de recherche..."); 
        
        titre = new Text("Recherche Avancée");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labales et TextField
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
        
        tel = new Label("Téléphone :");
        this.add(tel, 0, 4);

        saisie_tel = new TextField();
        this.add(saisie_tel, 1, 4);
        
        diplome = new Label("Diplôme :");
        this.add(diplome, 0, 5);

        saisie_diplome = new TextField();
        this.add(saisie_diplome, 1, 5);
        
        annee = new Label("Année :");
        this.add(annee, 0, 6);

        saisie_annee = new TextField();
        this.add(saisie_annee, 1, 6);
        
        competences = new Label("Compétences :");
        this.add(competences, 0, 7);

        saisie_competences = new TextField();
        this.add(saisie_competences, 1, 7);
        
        //Creation du bouton rechercher
        rechercherPlus = new Button("Rechercher");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(rechercherPlus);
        this.add(hbBtn, 1, 8);
        
        //Action lors de l'appui sur le bouton rechercher
        rechercherPlus.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
System.out.println("Verification saisie..."); 
                    
                    //Verification qu'aucun champs soit vide
                    if((saisie_nom.getText().length() == 0) && (saisie_prenom.getText().length() == 0) && (saisie_mail.getText().length() == 0)
                        && (saisie_diplome.getText().length() == 0) && (saisie_annee.getText().length() == 0) && (saisie_competences.getText().length() == 0))
                    {
                        //Affichage POPUP car il n'y as pas au moins un champs renseigne
                        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Connect - information");
                        alert.setHeaderText("Information(s) manquante(s) !");
                        alert.setContentText("Veuillez saisir au moins un champs.");
                        alert.showAndWait();
                        nom.setTextFill(Color.RED);
                        prenom.setTextFill(Color.RED);
                        mail.setTextFill(Color.RED);
                        diplome.setTextFill(Color.RED);
                        annee.setTextFill(Color.RED);
                        competences.setTextFill(Color.RED);
                    }
                    //S'il y a au moins un champs renseigne, on execute le requete de recherche avancee
                    else
                    {
System.out.println("Recuperation saisie..."); 
                        
                        //On recupere les informations
                        clientRecherche.setNom(saisie_nom.getText());
                        clientRecherche.setPrenom(saisie_prenom.getText());
                        clientRecherche.setMail(saisie_mail.getText());
                        clientRecherche.setTel(motsCherches);
                        clientRecherche.setDiplome(saisie_diplome.getText());
                        clientRecherche.setAnnee(saisie_annee.getText());
                        clientRecherche.setCompetences(saisie_competences.getText());

System.out.println("Envoi requête..."); 
                        
                        //Execution de la requete rechNom
                        gp.requeteRechNom(clientRecherche);
                        
System.out.println(">> Fermeture Recherche.java (Exec OK)"); 

                        //Affichage des resultats de la requete
                        AffichageResultats affichage = new AffichageResultats();
                        Scene scene_affichage = new Scene(affichage);
                        affichage.afficherResultats(fenetre_menu, scene_affichage, clientConnecte, gp, socket, socMsgr);
                        fenetre_menu.setScene(scene_affichage);
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
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture Recherche.java (Retour)"); 
                   
                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(clientConnecte.getMail()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, clientConnecte, gp, socket, socMsgr);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, clientConnecte, socket, socMsgr);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
}
