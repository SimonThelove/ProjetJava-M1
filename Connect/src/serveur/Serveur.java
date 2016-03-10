/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */

package serveur;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Yohann
 */

public class Serveur {
    
    public Serveur() {
            super();
System.err.println("#### Construct Serveur...");            
    }

    private SGBD sgbd = new SGBD();
    private String reponse;
    private ArrayList<String> resultats =  new ArrayList<String>();
    private boolean test = false;
    private int valide = 0;

    // Constructeurs :  int valide
    public int getValide() {
            return valide;
    }
    public void setValide(int valide) {
            this.valide = valide;
    }
    // Constructeurs : boolean test
    public boolean isTest() {
            return test;
    }
    public void setTest(boolean test) {
            this.test = test;
    }

    // Constructeurs :  String reponse
    public String getReponse() {
            return reponse;
    }
    public void setReponse(String reponse) {
            this.reponse = reponse;
    }

    // Methode de creation d'un compte sur le serveur d'annuaire
    public String creerCompte(String nom, String prenom, String adresseMail, String motDePasse) {
System.err.println("[SRV] creerCompte");
System.out.println(">> Recuperation mail ...");
        setTest(sgbd.recupererMail(adresseMail));
        if (isTest()){
            // Adresse mail deja existante = Echec creation
            return "Mail deja existant.";
        }
        else {
            // VerificationMotDePasse
            setTest(sgbd.verifierMotDePasse(motDePasse));
            if (!isTest()) {
                return "Votre mot de passe n'est pas securise.";
            }
            else {
                // Traitement de la requete par le SGBD
System.out.println(">> Creation utilisateur ...");
                sgbd.setRequeteCreationUtil(adresseMail, motDePasse);
                setValide(sgbd.executeUpdate("CREA"));
System.out.println(">> Creation informations ...");
                sgbd.setRequeteCreationInfos(nom, prenom, adresseMail);
                setValide(sgbd.executeUpdate("CREA"));
System.out.println(">> Creation visibilite ...");
                sgbd.setRequeteCreationVisible(adresseMail);
                setValide(sgbd.executeUpdate("CREA"));
System.out.println(">> Creation CPT_LIKE ...");
                sgbd.setRequeteCreationLike(adresseMail);
                setValide(sgbd.executeLike());
                if (valide != 0) {
System.out.println("[SRV] creerCompte FIN -----");
                    return "Votre compte a bien ete creer, vous pouvez maintenant vous connecter.";
                } else {
System.out.println("[SRV] creerCompte FIN -----");
                    return "Erreur creation : votre compte n'a pas ete cree.";
                }
            }
        }
    }

    // Methode de connexion au serveur d'annuaire
    public String seConnecter(String adresseMail, String motDePasse) throws SQLException{
System.err.println("[SRV] seConnecter");
        // Controle du mail
System.out.println(">> Controle mail ...");
        setTest(sgbd.recupererMail(adresseMail));

        if (!isTest()){
            // Adresse mail non existante = Echec connexion
            return "Utilisateur inconnu.";
        }
        else {
            // Controle du mot de passe
System.out.println(">> Controle mot de passe ...");
            setTest(sgbd.recupererMotDePasse(motDePasse, adresseMail));
            if (!isTest()) {
System.out.println("[SRV] seConnecter FIN -----");
                return "Votre mot de passe est incorrect.";
            }
            else {
System.out.println("[SRV] seConnecter FIN -----");
                return "Vous etes bien connecte.";
            }
        }
    }

    public String seDeconnecter() {
System.err.println("[SRV] seDeconnecter");
System.out.println(">> Deconnexion client ...");
System.out.println("[SRV] seDeconnecter FIN -----");
        return "Vous vous etes bien deconnecte.";
    }

    // Methode de modification des informations sur le compte connecter
    public String modifierInformations(String[] chaine, String adresseMail) throws SQLException{
System.err.println("[SRV] modifierInformations");
        // Modification des informations
System.out.println(">> Modifications compte ...");
        sgbd.setRequeteModification(chaine, adresseMail);
        setValide(sgbd.executeUpdate("MODI"));
        if (valide != 0) {
System.out.println("[SRV] seDeconnecter FIN -----");
            return "Vos modifications ont ete prises en compte.";
        } else {	
System.out.println("[SRV] seDeconnecter FIN -----");
            return "Erreur modification : vos modifications n'ont pas ete prises en compte.";
        }
    }

    // Methode de consultation d'un profil utilisateur
    public ArrayList<String> consulter(String adresseMail, String droitAdmin, String mailConnecte) throws SQLException {
System.err.println("[SRV] consulter");

        // ControleDroits (si la personne est admin ou si la personne se ocnnecte et recupere ses informations)
        if(sgbd.isAdmin(adresseMail) || ("1".equals(droitAdmin))){
            // Recuperation de toutes les informations du profil
            resultats = (sgbd.getAllInfos(adresseMail));
        }
        //Sinon on applique la visibilite
        else {
            // Recuperation des informations visibles du profil
            resultats = (sgbd.getVisibleInfos(adresseMail, mailConnecte));
        }
System.out.println("[SRV] consulter FIN -----");
        return resultats;
    }

    // Methode de recherche d'utilisateurs
    public ArrayList<String> rechercher(String[] chaine) {
System.err.println("[SRV] rechercher");
        // Recherche
        resultats = (sgbd.getUtilisateurs(chaine));
System.out.println(">> RESULTATS = " + resultats);
System.out.println("[SRV] rechercher FIN -----");
        return resultats;
    }

    // Methode d'envoi de message
    public String envoiMessage(String mail, String mailDest, String msg) {		
System.err.println("[SRV] envoiMessage");
System.out.println(">> Controle mail ...");
        setTest(sgbd.recupererMail(mailDest));
        if (!isTest()){
            // Adresse mail non existante = Echec connexion
System.out.println("[SRV] envoiMessage FIN -----");
            return "Utilisateur inconnu.";
        }
        else
        {
            //Creation et execution de la requete pour envoyer un message a un client
System.out.println(">> Creation message ...");
            sgbd.setRequeteCreationMessage(mail, mailDest, msg);
            setValide(sgbd.executeUpdateMessage());
            if (valide != 0) {
System.out.println("[SRV] envoiMessage FIN -----");
                    return "Votre message a bien ete envoye."; }
            else {
System.out.println("[SRV] envoiMessage FIN -----");
                    return "Erreur d'envoi : votre message n'a pas ete envoye."; }
        }
    }
     public String recupererMessage(String mail) {
System.err.println("[SRV] recupererMessage");
        //Execution de la requete
        reponse = sgbd.getMessage(mail);
System.out.println(">> REPONSE = " + reponse);
System.out.println("[SRV] recupererMessage FIN -----");
        return reponse;         
     }
}