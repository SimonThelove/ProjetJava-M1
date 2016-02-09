package serveur;
import java.sql.SQLException;

import informations.SGBD;

public class Serveur {
	
	public Serveur() {
		super();
	}

	private SGBD sgbd = new SGBD();
	
	private String reponse;
	private String[] resultats;
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

	// Constructeurs : String[] resultats
	public String[] getResultats(String[] resultats) {
		return resultats;
	}
	public void setResultats(String[] resultats) {
		this.resultats = resultats;
	}
	

	// Méthode de création d'un compte sur le serveur d'annuaire
	public String creerCompte(String nom, String prenom, String adresseMail, String motDePasse) {
		System.out.println("creation compte ...");
		System.out.println("test avant : " + test);
		setTest(sgbd.recupererMail(adresseMail));
		System.out.println("test apres : " + test);
		if (isTest()){
			// Adresse mail deja existante = Echec creation
			return "Mail déjà existant.";
		}
		else {
			// VerificationMotDePasse
			setTest(sgbd.verifierMotDePasse(motDePasse));
			if (!isTest()) {
				return "Votre mot de passe n'est pas sécurisé.";
			}
			else {
				// Traitement de la requête par le SGBD
				sgbd.setRequeteCreation(nom, prenom, adresseMail, motDePasse);
				setValide(sgbd.executeUpdate("CREA"));
				if (valide != 0)
					return "Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
				else	return "Erreur création : votre compte n'a pas été créé.";
			}
		}
	}
	
	// Méthode de connexion au serveur d'annuaire
	public String seConnecter(String adresseMail, String motDePasse) throws SQLException{
		
		// ControleMail
				setTest(sgbd.recupererMail(adresseMail));
				if (!isTest()){
					// Adresse mail non existante = Echec connexion
					return "Utilisateur inconnu.";
				}
				else {
					// ControleMotDePasse
					setTest(sgbd.recupererMotDePasse(motDePasse));
					if (!isTest()) {
						return "Votre mot de passe est incorrect.";
					}
					else {
//creationThread et ID client de manière unique
						return "Vous êtes bien connectés.";
					}
				}
	}
	
	public String seDeconnecter() {
//fermetureThread et déconnexion du client
		return "Vous vous êtes bien déconnecté.";
	}

	// Méthode de modification des informations sur le compte connecté
	public String modifierInformations(String[] chaine, String adresseMail) throws SQLException{
		
		// Modification des informations
		sgbd.setRequeteModification(chaine, adresseMail);
		setValide(sgbd.executeUpdate("MODI"));
		if (valide != 0)
			return "Vos modifications ont été prises en compte.";
		else	return "Erreur modification : vos modifications n'ont pas été prises en compte.";
	}
	
	// Méthode de consultation d'un profil utilisateur
	public String[] consulter(String adresseMail) throws SQLException {
		
		// ControleDroits
		if(sgbd.isAdmin(adresseMail)){
			// Récupération de toutes les informations du profil
			resultats = (sgbd.getAllInfos(adresseMail));
		}
		else {
			// Récupération des informations visibles du profil
			resultats = (sgbd.getVisibleInfos(adresseMail));
		}

		return resultats;
	}
	
	// Méthode de recherche d'utilisateurs
	public String[] rechercher(String[] chaine) throws SQLException{
				
		// Recherche
		resultats = (sgbd.getUtilisateurs(chaine));
		
		return resultats;
	}
}
