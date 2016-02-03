package serveur;
import informations.SGBD;

public class Serveur {
	
	private SGBD sgbd;
	
	private String requete;
	private String[] resultats;
	
	// Constructeurs
	public String getRequete() {
		return requete;
	}

	public void setRequete(String requete) {
		this.requete = requete;
	}

	public String[] getResultats() {
		return resultats;
	}

	public void setResultats(String[] resultats) {
		this.resultats = resultats;
	}
	
	// varibles de test
	private boolean test = false;

	// Méthode de création d'un compte sur le serveur d'annuaire
	public String creerCompte(String nom, String prenom, String adresseMail, String motDePasse) {
		
		// ControleMail
		test = sgbd.recupererMail(adresseMail);
		if (test){
			// Adresse mail deja existante = Echec creation
			return "Mail déjà existant.";
		}
		else {
			// VerificationMotDePasse
			test = sgbd.verifierMotDePasse(motDePasse);
			if (!test) {
				return "Votre mot de passe n'est pas sécurisé.";
			}
			else {
				// Assemblage des données pour requête SQL
				requete = "NOM = "+ nom +" AND PRENOM = "+ prenom;
				requete += " AND MAIL = "+ adresseMail +" AND MOTDEPASSE = "+ motDePasse +";";
				
				// Traitement de la requête par le SGBD
				sgbd.setRequeteCreation(requete);
				
				return "Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
			}
		}
	}
	
	// Méthode de connexion au serveur d'annuaire
	public String seConnecter(String adresseMail, String motDePasse){
		
		// ControleMail
				test = sgbd.recupererMail(adresseMail);
				if (!test){
					// Adresse mail non existante = Echec connexion
					return "Utilisateur inconnu.";
				}
				else {
					// ControleMotDePasse
					test = sgbd.recupererMotDePasse(motDePasse);
					if (!test) {
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
		
		// Assemblage de la requête SQL
		// Assemblage des N-1 premiers termes
		for(int n = 1; n <= 11; n += 2){
			requete += chaine[n] + " = " + chaine[n+1] + " AND ";
		}
		// Ajout du dernier terme à la requête
		requete += chaine[11] + " =  " + chaine[12] + ";";
		
		// Traitement de la requête par le SGBD
		sgbd.setRequeteModification(requete);
		
		return "Vos modifications ont été prises en compte.";
	}
	
	// Méthode de consultation d'un profil utilisateur
	public void consulter(String adresseMail) {
		
		// ControleDroits
		if(sgbd.isAdmin()){
			// Récupération de toutes les informations du profil
			resultats = sgbd.getAllInfos();
		}
		else {
			// Récupération des informations visibles du profil
			resultats = sgbd.getVisibleInfos();
		}
	}
	
	// Méthode de recherche d'utilisateurs
	public void rechercher(String[] chaine){
		
		// Recherche
		resultats = sgbd.getUtilisateurs(chaine);
	}
}