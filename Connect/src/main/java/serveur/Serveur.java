package serveur;
import java.sql.SQLException;
import java.util.ArrayList;

import informations.SGBD;

public class Serveur {
	public Serveur() {
		super();
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
		System.out.println("creation compte ...");
		setTest(sgbd.recupererMail(adresseMail));
		if (isTest()){
			// Adresse mail deja existante = Echec creation
			return "Mail deja†existant.";
		}
		else {
			// VerificationMotDePasse
			setTest(sgbd.verifierMotDePasse(motDePasse));
			if (!isTest()) {
				return "Votre mot de passe n'est pas securiser.";
			}
			else {
				// Traitement de la requete par le SGBD
				System.out.println("creation utilisateur ...");
				sgbd.setRequeteCreationUtil(adresseMail, motDePasse);
				setValide(sgbd.executeUpdate("CREA"));
				System.out.println("creation informations ...");
				sgbd.setRequeteCreationInfos(nom, prenom, adresseMail);
				setValide(sgbd.executeUpdate("CREA"));
				System.out.println("creation visibilite ...");
				sgbd.setRequeteCreationVisible(adresseMail);
				setValide(sgbd.executeUpdate("CREA"));
				if (valide != 0)
					return "Votre compte a bien ete creer, vous pouvez maintenant vous connecter.";
				else	return "Erreur creation : votre compte n'a pas ete creer.";
			}
		}
	}
	
	// Methode de connexion au serveur d'annuaire
	public String seConnecter(String adresseMail, String motDePasse) throws SQLException{
		
		// ControleMail
			System.out.println("controle mail ...");
				setTest(sgbd.recupererMail(adresseMail));
				if (!isTest()){
					// Adresse mail non existante = Echec connexion
					return "Utilisateur inconnu.";
				}
				else {
					// ControleMotDePasse
					System.out.println("controle mot de passe ...");
					setTest(sgbd.recupererMotDePasse(motDePasse, adresseMail));
					if (!isTest()) {
						return "Votre mot de passe est incorrect.";
					}
					else {
						//creationThread et ID client de mani√®re unique
						return "Vous etes bien connectes.";
					}
				}
	}
	
	public String seDeconnecter() {
		//fermetureThread et deconnexion du client
		System.out.println("deconnexion client ...");
		return "Vous vous etes bien deconnecter.";
	}

	// Methode de modification des informations sur le compte connecter
	public String modifierInformations(String[] chaine, String adresseMail) throws SQLException{
		
		// Modification des informations
		System.out.println("modifications compte ...");
		sgbd.setRequeteModification(chaine, adresseMail);
		setValide(sgbd.executeUpdate("MODI"));
		if (valide != 0)
			return "Vos modifications ont ete prises en compte.";
		else	return "Erreur modification : vos modifications n'ont pas ete prises en compte.";
	}
	
	// Methode de consultation d'un profil utilisateur
	public ArrayList<String> consulter(String adresseMail) throws SQLException {
		
		// ControleDroits
		if(sgbd.isAdmin(adresseMail)){
			// Recuperation de toutes les informations du profil
			resultats = (sgbd.getAllInfos(adresseMail));
		}
		else {
			// Recuperation des informations visibles du profil
			resultats = (sgbd.getVisibleInfos(adresseMail));
		}
		return resultats;
	}
	
	// Methode de recherche d'utilisateurs
	public ArrayList<String> rechercher(String[] chaine) {
				
		// Recherche
		resultats = (sgbd.getUtilisateurs(chaine));
		return resultats;
	}
}
