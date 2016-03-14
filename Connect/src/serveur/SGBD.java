/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package serveur;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yohann
 */

public class SGBD extends Thread {
    private String requeteCreation;
    private String requeteModification;
    private String requeteConsultation;

    // Declaration de variables globales pour executer le SQL
    /* Chargement du driver JDBC pour MySQL */
    private Connection con;
    private Statement st;
    private Statement visibilite;
    private ResultSet rslt;
    private ResultSetMetaData rsmd;
    private String table;
    private ArrayList<String> resultats = new ArrayList<String>();

    // Compteurs utiliser pour parcourir les tableaux et resultats
    private int i, j;

    // Constructeurs : String reponse
    public String getTable() {
            return table;
    }
    public void setTable(String table) {
            // A MODIFIER ou CONFIRMER UTILITE DE LA VARIABLE
            this.table = table;
    }

    // Constructeurs : ArrayList<String> resultats
    public ArrayList<String> getResultats() {
            return resultats;
    }

    // Connexion a la BDD
    public void bdd() {
        try {
System.err.println("#### Construct BDD...");            
            con = DriverManager.getConnection("jdbc:mysql://mysql-stri.alwaysdata.net/stri_connect","stri","STRISTRI");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        try {        
            st = con.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            visibilite = con.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Connexion a la  BDD pour la V2 (messagerie differee)
    public void bdd2() {
        try {
System.err.println("#### Construct BDD_2...");            
            con = DriverManager.getConnection("jdbc:mysql://mysql-stri.alwaysdata.net/stri_connect_msg","stri","STRISTRI");
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = con.createStatement();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            visibilite = con.createStatement();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Connexion a la  BDD pour la V2 (messagerie differee)
    public void bdd3() {
        try {
System.err.println("#### Construct BDD_3...");            
           con = DriverManager.getConnection("jdbc:mysql://mysql-stri.alwaysdata.net/stri_connect_like","stri","STRISTRI");
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = con.createStatement();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            visibilite = con.createStatement();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setResultats(ResultSet rslt, ResultSetMetaData rsmd, String[] visibilite) {
System.err.println("[SGBD] Setter Resultats");
        // Intialisation des compteurs
        i = 0;
        j = 0;
        int cpt = 0;
        String retour, champ;
        resultats.clear();

        // Depuis getVisibleInfos
        if (visibilite != null){
            try {
System.out.println("Condition de visibilité : not null");

                // On parcourt les resultats SQL
                while (rslt.next()!= false){
System.out.println("Resultat suivant...");
                    // Tant qu'il y a des colonnes resultat SQL
                    while (i < rsmd.getColumnCount()){
                        i ++;
                        if(rslt.getString(i) != null){
                            // On teste le nombre de champs a� affecter a�resultats
                            if (j < visibilite.length) {
                                // On verifie la visibilite du champ pour l'affecter
                                if (rsmd.getColumnLabel(i).equalsIgnoreCase(visibilite[j])) {
                                    retour = rslt.getString(i);
                                    champ = rsmd.getColumnLabel(i);
                                    // On ajoute le nom du champ
                                    resultats.add(champ);
                                    // On ajoute la valeur du champ
                                    resultats.add(retour);
                                    // On incremente j
                                    j ++;
                                }
                            }
                        }
                    }
                }
System.out.println("[SGBD] Resultats = " + resultats);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        // Depuis getAllInfos
        else {
            // On parcourt les resultats SQL
            try {
System.out.println("Condition de visibilité : null");
                while (rslt.next() != false){
System.out.println("Resultat suivant...");
                    i = 0;//Permet de recuperer les information du prochain profil
                    // Tant qu'il y a des colonnes resultat SQL					
                    while (i < rsmd.getColumnCount()){
                        i ++;
                        retour = rslt.getString(i);
                        champ = rsmd.getColumnLabel(i);
                        // On ajoute le nom du champ
                        resultats.add(champ);
                        // On ajoute la valeur du champ
                        resultats.add(retour);
                    }
                }
                rslt.last();
                //On recupere le nombre de profil a afficher pour le mettre en tant que premier element de l'arrayListe
                cpt = rslt.getRow();
                resultats.add(0, Integer.toString(cpt));
System.out.println("[SGBD] Resultats = " + resultats);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Requete de consultaiton de la base de données
    public void setRequeteConsultation(String[] chaine) {
System.out.println("[SGBD] Setter CONS");
        // On fabrique le debut la requete
        requeteConsultation = " SELECT * FROM INFORMATIONS WHERE (";
        if (chaine[1].compareTo("MOTSCLES") != 0) {

            // Recherche par champs => on sait ou chercher et on simplifie la fabrication de la requete
            //Si la requete debute par RECH, alors on débute avec i=1
            if("RECH".equals(chaine[0]))
            {
                for (i = 1; i < (chaine.length - 2); i+= 2) {
                    requeteConsultation += chaine[i] + " = '" + chaine[i+1] + "' OR ";
                }
            }
            else //Sinon on débute avec i=0 (utiliser pour recuperer les informations utilisateur lors de leur connexion
            {
                for (i = 0; i < (chaine.length - 2); i+= 2) {
                        requeteConsultation += chaine[i] + " = '" + chaine[i+1] + "' OR ";
                }
            }
            // On finit la requete avec l'ajout du dernier champ
            requeteConsultation += chaine[i] + " = '" + chaine[i+1] + "');";
System.out.println("Consultation par mots clés...");            
        } 
        else {
            // On stocke les mots clees dans un tableau
            // Le separateur est un espace (logique de saisie)
            String[] mots = chaine[2].split(" ");

            // On regarde le nombre de mots clees et on boucle
            // Pour MAIL
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "mail LIKE '%" + mots[i] + "%' OR ";
            }
            // Pour NOM
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "nom LIKE '%" + mots[i] + "%' OR ";
            }
            // Pour PRENOM
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "prenom LIKE '%" + mots[i] + "%' OR ";
            }
            // Pour TELEPHONE
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "telephone LIKE '%" + mots[i] + "%' OR ";
            }
            // Pour DIPLOMES
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "diplomes LIKE '%" + mots[i] + "%' OR ";
            }
            // Pour ANNEE_DIPLOMATION
            for (i = 0; i <= (mots.length - 1); i++) {
                    requeteConsultation += "annee_diplomation LIKE '%" + mots[i] + "%' OR ";
            }
            // On ajoute la fin (On recherche tous les mots clés sauf le dernier)
            for (i = 0; i <= (mots.length - 2); i++) {
                    requeteConsultation += "competences LIKE '%" + mots[i] + "%' OR ";
            }
            // On finit par rechercher le dernier mot clee en cloturant la requete
            requeteConsultation += "competences LIKE '%" + mots[i] + "%');";
System.out.println("Consultation par champs...");
         }
System.out.println("[SGBD] REQUETE = " + requeteConsultation);
    }

    // Requetes de creation dans la base de donnees
    public void setRequeteCreationUtil (String mail, String mdp) {
System.err.println("[SGBD] Setter CREA Util");        
            this.requeteCreation  = "INSERT INTO UTILISATEURS (mail, mdp) VALUES ('" + mail + "','" + mdp + "'); ";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }

    public void setRequeteCreationInfos (String nom, String prenom, String mail) {
System.err.println("[SGBD] Setter CREA Infos");        
            this.requeteCreation = "INSERT INTO INFORMATIONS (mail, nom, prenom) VALUES ('"+ mail +"','" + nom + "','" + prenom + "'); ";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }

    public void setRequeteCreationVisible (String mail) {
System.err.println("[SGBD] Setter CREA Visibilite");        
            this.requeteCreation = "INSERT INTO VISIBILITE VALUES ('"+ mail +"','mail,nom,prenom','mail,nom,prenom,telephone,diplomes,annee_diplomation,competences');";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }

    public void setRequeteCreationMessage (String mail, String mailDest, String msg) {
System.err.println("[SGBD] Setter CREA Messsage");                
            this.requeteCreation = "INSERT INTO DEPOT_MSG (mail_exp, mail_des, message, lu) VALUES ('" + mail + "','" + mailDest + "','" + msg + "','0'); ";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }
    
    public void setRequeteCreationInitialLike (String mail) {
System.err.println("[SGBD] Setter CREA CPT_LIKE");        
            this.requeteCreation = "INSERT INTO CPT_LIKE VALUES ('"+ mail +"','0');";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }
    
    //Requete recuperation de message client
    public void setRequeteRecupMessage(String mail) {
System.err.println("[SGBD] Setter MSSG Recup");        
         this.requeteConsultation = "SELECT * FROM DEPOT_MSG WHERE mail_des =  '" + mail + "';";
System.out.println("[SGBD] REQUETE = " + requeteConsultation);            
    }
    
    //Requete de mise a jour de l'etat de lecture
    public void setLu(String id_mail){
System.err.println("[SGBD] Setter MSSG Recup");        
        this.requeteModification = "UPDATE DEPOT_MSG SET lu = '1' WHERE id_mail =  '" + id_mail + "';";
System.out.println("[SGBD] REQUETE = " + requeteModification);
    }
    
    //Requete d'ajout de la personne qui like un profil
    public void setRequeteCreationLike (String cible, String auteur) {
System.err.println("[SGBD] Setter CREA AJOUT LIKE");        
            this.requeteCreation = "INSERT INTO LIKES VALUES ('"+ cible +"','" + auteur + "');";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }
    
    //Requete de suppression d'un like d'un profil
    public void setRequeteDeleteLike (String cible, String auteur) {
System.err.println("[SGBD] Setter CREA AJOUT LIKE");        
            this.requeteModification = "DELETE FROM LIKES WHERE cible = '"+ cible +"' AND auteur = '" + auteur + "';";
System.out.println("[SGBD] REQUETE = " + requeteCreation);            
    }
    
    //Requete de récuperation de like d'un profil
    public void setRequeteRecupCptLike (String cible) {
System.err.println("[SGBD] Setter CREA CPT_LIKE");        
            this.requeteConsultation = "SELECT nb_like FROM CPT_LIKE WHERE cible = '"+ cible +"';";
System.out.println("[SGBD] REQUETE = " + requeteConsultation);            
    }
  
     //Requete de récuperation si l'utilisateur like un profil
    public void setRequeteRecupIfClientLike (String cible, String auteur) {
System.err.println("[SGBD] Setter CREA LIKES");        
            this.requeteConsultation = "SELECT * FROM LIKES where cible = '"+ cible +"' AND auteur = '"+ auteur +"';";
System.out.println("[SGBD] REQUETE = " + requeteConsultation);            
    }
    
    //Requete de modification du compteur de like d'un profil
    public void setRequeteModificationCptLike (String cible, String nb_like) {
System.err.println("[SGBD] Setter CREA CPT_LIKE");        
            this.requeteModification = "UPDATE CPT_LIKE SET nb_like = '"+ nb_like +"' WHERE cible = '" + cible + "';";
System.out.println("[SGBD] REQUETE = " + requeteModification);            
    }
    
    // Requete de modification dans la base de donnees
    public void setRequeteModification(String[] chaine, String adresseMail) {
        // initialisation des variables
        i = 0;
        j = 0;
System.err.println("[SGBD] Setter MODI");
        // Recuperation de la table
        while(i < chaine.length){ // On parcourt la chaine
            j = i % 2;
            if (j == 1) {                     
                if (null != chaine[i])	
                        // On teste le nom des champs
                switch (chaine[i]) {
                // Le champ mdp n'appartient qu'a la table utilisateurs
                    case "mdp":
                        setTable("UTILISATEURS");
                        break;
                    case "infos_visibles_anonymes":
                        setTable("VISIBILITE"); // Les champs infos_visibiles > table visibilite
                        break;
                    case "infos_visibles_utilisateurs":
                        setTable("VISIBILITE"); // Les champs infos_visibiles > table visibilite
                        break;
                    default:
                        setTable("INFORMATIONS"); // Les autres champs appartiennent a la table informations
                        break;
                } // Les autres champs appartiennent a la table informations
            }
            i ++;
        }
System.out.println("Recuperation table... " + table);
        // Debut de la requete
        requeteModification = "UPDATE " + table + " SET ";
System.out.println("Construction requete...");
        // Assemblage des N-1 termes suivants
        for(i = 1; i < (chaine.length - 2); i += 2){
            if (chaine[i+1] != null)
                requeteModification += chaine[i] + " = '" + chaine[i+1] + "', ";
        }
        // Ajout du dernier terme a la requête
        requeteModification += chaine[i] + " =  '" + chaine[i+1] + "'";

        // Ajout de la condition WHERE
        requeteModification += " WHERE mail = '" + adresseMail + "';";
System.out.println("[SGBD] REQUETE = " + requeteModification);
    }

    // Methode de recuperation du mail
    public boolean recupererMail(String adresseMail) {
System.err.println("[SGBD] Getter MAIL");
        bdd();    	
        try {
            rslt = st.executeQuery("SELECT mail FROM UTILISATEURS WHERE mail = '" + adresseMail + "'");
            if (rslt.next()) {
            con.close();
System.out.println("Execution requete OK...");
            return true;
        }
        else{
            con.close();
System.err.println("<!> Echec mise à jour...");            
            return false;
        }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }     	
    }

    // Methode de verification du mot de passe (politique de securitee des mots de passe)
    // MINIMUM 6 CARACTERES
// RAJOUTER LA CONDITION SUIVANTE DANS LE IF : && motDePasse.contains("[[a-z]{1,}][[A-Z]{1,}][[0-9]{1,}]")
    // = AU MOINS une lettre minuscule + une lettre majuscule + un chiffre : Exemple = 'Stri2014'
    public boolean verifierMotDePasse(String motDePasse){
System.err.println("[SGBD] Exec MDP");
        int taille = motDePasse.length();
        if (taille >= 6) {
System.out.println("Verification OK...");            
                return true;
        } else {
System.err.println("<!> Echec sécurité...");            
                return false;
        }
    }

    // Methode de recuperation du mot de passe
    public boolean recupererMotDePasse(String motDePasse, String mail) {
System.err.println("[SGBD] Getter MDP");
        bdd();
        try {
            rslt = st.executeQuery("SELECT mdp FROM UTILISATEURS WHERE mdp = '" + motDePasse + "' AND mail = '"+ mail +"';");
            if (rslt.next()){
                con.close();
System.out.println("Execution requete OK...");
                return true;
            }
            else {
                con.close();
System.err.println("<!> Echec récupération...");            
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Requete de verification des droits d'acces aux informations des utilisateurs
    public boolean isAdmin(String adresseMail) {
System.err.println("[SGBD] Exec ADMIN");
        bdd();
        try {
            rslt = st.executeQuery("SELECT mail FROM ADMINISTRATEURS WHERE mail = '" + adresseMail +"';");
            if (rslt.next()){
                con.close();
System.out.println("Utilisateur admin...");
                return true;
            } else {
                con.close();
System.out.println("Utilisateur non admin...");            
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Recuperation des informations d'un profil utilisateur (admin)
    public ArrayList<String> getAllInfos(String adresseMail) {
System.err.println("[SGBD] Getter INFOS");            
        bdd();
        // On fabrique les informations a transmettre
        String[] req = {"MAIL", adresseMail};
System.out.println("Construction requete...");
        setRequeteConsultation(req);

        // On l'execute sur la BDD et on recupere les informations sur ces resultats
        try {
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete OK...");
            // On standardise les resultats
            setResultats(rslt,rsmd,null);
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return resultats;
    }

    // Recuperation des informations d'un profil utilisateur (selon visibilitee)
    public ArrayList<String> getVisibleInfos(String adresseMail, String mailConnecte) {
System.err.println("[SGBD] Getter VISIBILITE");            
        bdd();
        // On a des variables de gestion de la visibilitee
        String temp;
        String[] split;
        ResultSet visible;
        
System.out.println("Construction requete...");
        // On fabrique les informations a transmettre
        String[] req = {"MAIL", adresseMail};
        setRequeteConsultation(req);

        // On l'execute sur la BDD et on recupere les informations sur ces resultats
        try {
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete CONS OK...");
            // Gestion de la visibilite
            if (mailConnecte == null) { // Affiche les infos visibles pour les anonymes
                visible = visibilite.executeQuery("SELECT infos_visibles_anonymes FROM VISIBILITE WHERE mail = '" + adresseMail + "';");
                visible.next();
System.out.println("Execution requete VISIB OK...");
                temp = visible.getString("infos_visibles_anonymes");
                split = temp.split(",");
System.out.println("Execution OK...");
                // On standardise les resultats
                setResultats(rslt,rsmd,split);
                con.close();
            } else {    // Affiche les infos visibles pour les utilisateurs identifiés
                visible = visibilite.executeQuery("SELECT infos_visibles_utilisateurs FROM VISIBILITE WHERE mail = '" + adresseMail + "';");
                visible.next();
System.out.println("Execution requete VISIB OK...");
                temp = visible.getString("infos_visibles_utilisateurs");
                split = temp.split(",");
System.out.println("Execution OK...");
                // On standardise les resultats
                setResultats(rslt,rsmd,split);
                con.close();            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return resultats;
    }

    // Requete de recherche d'utilisateurs selon des mots clees
    public ArrayList<String> getUtilisateurs(String[] motsCles) {
System.err.println("[SGBD] Getter UTIL");            
        bdd();
System.out.println("Construction requete...");
        // On fabrique la requete
        setRequeteConsultation(motsCles);
        // On l'execute sur la BDD et on recupere les informations sur ces resultats
        try {
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete CONS OK...");
            // On standardise les resultats
            setResultats(rslt,rsmd,null);
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return resultats;
    }

    // Requete de recherche d'utilisateurs selon des mots cles
    public String getMessage(String mail) {
        System.err.println("[SGBD] Getter MSSG");            
        bdd2();
        String message = "";
System.out.println("Construction requete...");
        // On fabrique la requete
        setRequeteRecupMessage(mail);
        // On l'execute sur la BDD et on recupere les informations sur ces resultats
        try {
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete CONS OK...");
            // On standardise les resultats
            setResultats(rslt,rsmd,null);
            con.close();
            //Creation d'un string a retourner aux fonctions
            for(i = 0; i < (resultats.size() -2); i += 2)
                {
                    message += resultats.get(i) + "|" + resultats.get(i+1) + "|";
                }
            //Ajout du dernier champs
            message += resultats.get(i);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return message;
    }
    
    public synchronized String getLu(String id_mail){
        
        String message = "Message non lu";
        try {
System.err.println("[SGBD] Getter Lecture MSSG");
            bdd2();
System.out.println("Construction requete...");
            // On fabrique la requete
            setLu(id_mail);
            // On l'execute sur la BDD et on recupere les informations sur ces resultats
            i = st.executeUpdate(requeteModification);
            message = "Message lu";
        } catch (SQLException ex) {
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
    
    public void modifierCptLike(String cible, String auteur, String test){
        System.err.println("[SGBD] Ajouter d'un auteur like et modification compteur");            
        String valeur;
        int cpt_like = 0;
        
        if(test.compareTo("1") == 0)
        {
            //Ajout de l'auteur du like
            setRequeteCreationLike(cible, auteur);
            executeLike(); 

    System.out.println("Construction requete...");
            // On fabrique la requete
            setRequeteRecupCptLike(cible);
            // On l'execute sur la BDD et on recupere les informations sur ces resultats
            try {
                bdd3();
                rslt = st.executeQuery(requeteConsultation);
                rsmd = rslt.getMetaData();
    System.out.println("Execution requete RECUP CPT LIKE ok...");
                rslt.next();
                valeur = rslt.getString(1);
        System.out.println("Champs : " + valeur);

                //Mise a jours du compteur de like
                cpt_like = Integer.parseInt(valeur) + 1 ;
                System.out.println("CPT : " + cpt_like);
                valeur = Integer.toString(cpt_like);
                //Faire l'envoit du new cpt
                setRequeteModificationCptLike(cible, valeur);
                updateLike();
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            //Ajout de l'auteur du like
            setRequeteDeleteLike(cible, auteur);
            updateLike(); 

    System.out.println("Construction requete...");
            // On fabrique la requete
            setRequeteRecupCptLike(cible);
            // On l'execute sur la BDD et on recupere les informations sur ces resultats
            try {
                bdd3();
                rslt = st.executeQuery(requeteConsultation);
                rsmd = rslt.getMetaData();
    System.out.println("Execution requete RECUP CPT LIKE ok...");
                rslt.next();
                valeur = rslt.getString(1);
        System.out.println("Champs : " + valeur);

                //Mise a jours du compteur de like
                cpt_like = Integer.parseInt(valeur) - 1 ;
                System.out.println("CPT : " + cpt_like);
                valeur = Integer.toString(cpt_like);
                //Faire l'envoit du new cpt
                setRequeteModificationCptLike(cible, valeur);
                updateLike();
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public String recupLike(String cible, String auteur){
        System.err.println("[SGBD] Recuperer les informations des likes");            
        String message = "";
System.out.println("Construction requete...");
        try {
            bdd3();
            // On fabrique la requete
            setRequeteRecupCptLike(cible);
            // On l'execute sur la BDD et on recupere les informations sur ces resultats
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete RECUP CPT LIKE ok...");
            rslt.next();
            message = "LIKE|CPTLIKE|" + rslt.getString(1);
System.out.println("Message : " + message);
System.out.println("Nb de like du profil : " + rslt.getString(1));
            setRequeteRecupIfClientLike(cible, auteur);
            rslt = st.executeQuery(requeteConsultation);
            rsmd = rslt.getMetaData();
System.out.println("Execution requete RECUP CPT LIKE ok...");
            //Si la requete SQL renvoit une ligne, alors le client like deja le profil
            if(rslt.next()!= false)
            {               
                message += "|LIKEORUNLIKE|1";
            }
            //Sinon il ne like pas le profil
            else
            {
                message += "|LIKEORUNLIKE|0";
            }
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return message;
    }
    
    // Mise à jour de la BDD = Action d'ecriture donc besoin de gestion des acces concurrents
    // Creation ou Modification (Synchronized)
    public synchronized int executeUpdate(String type) {
System.err.println("[SGBD] Exec UPDATE");            
        bdd();
        if ("CREA".equals(type)) {
            try {
System.out.println("Update Creation...");                
                i = st.executeUpdate(requeteCreation);
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            }
            return i;
        } else {
            try {
System.out.println("Update Modification...");                
                i = st.executeUpdate(requeteModification);
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
            }
            return i;
        }
    }
    
    public synchronized int executeUpdateMessage() {
System.err.println("[SGBD] Exec UPDATE MSSG");            
        bdd2();
        try {
System.out.println("Update Message...");                
            i = st.executeUpdate(requeteCreation);
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }  
    
    //Connexion pour la creation d'un like
    public synchronized int executeLike() {
System.err.println("[SGBD] Exec UPDATE MSSG");            
        bdd3();
        try {
System.out.println("Update Crea Like ...");                
            i = st.executeUpdate(requeteCreation);
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }
    
    //Connexion pour la modification d'un like
    public synchronized int updateLike() {
System.err.println("[SGBD] Exec UPDATE MSSG");            
        bdd3();
        try {
System.out.println("Update CPT_Like nb_like ...");                
            i = st.executeUpdate(requeteModification);
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(SGBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }  
}