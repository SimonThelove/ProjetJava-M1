package serveur;
import informations.SGBD;

public class Serveur {
	
	private SGBD sgbd;
	
	private String requete;
	private String[] resultats;
	private boolean test = false;
	
	// Constructeurs : boolean test
	public boolean isTest() {
		return test;
	}
	public void setTest(boolean test) {
		this.test = test;
	}
	
	// Constructeurs :  String requete
	public String getRequete() {
		return requete;
	}
	public void setRequete(String requete) {
		this.requete = requete;
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
		
		// ControleMail
		setTest(sgbd.recupererMail(adresseMail));
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
				
				return "Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
			}
		}
	}
	
	// Méthode de connexion au serveur d'annuaire
	public String seConnecter(String adresseMail, String motDePasse){
		
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
	public String modifierInformations(String[] chaine){
		
		// Modification des informations
		sgbd.setRequeteModification(chaine);
		
		return "Vos modifications ont été prises en compte.";
	}
	
	// Méthode de consultation d'un profil utilisateur
	public void consulter(String adresseMail) {
		
		// ControleDroits
		if(sgbd.isAdmin()){
			// Récupération de toutes les informations du profil
			setResultats(sgbd.getAllInfos());
		}
		else {
			// Récupération des informations visibles du profil
			setResultats(sgbd.getVisibleInfos());
		}
	}
	
	// Méthode de recherche d'utilisateurs
	public void rechercher(String[] chaine){
		
		// Recherche
		setResultats(sgbd.getUtilisateurs(chaine));
	}
}
