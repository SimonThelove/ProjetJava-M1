package serveur;
import informations.SGBD;

public class Serveur {
	
	SGBD sgbd;
	
	// varibles de test
	boolean test = false;
	String chaineTestee;
	String tableauTeste[];

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
//test = verifierMotDePasse(motDePasse);
			if (!test) {
				return "Votre mot de passe n'est pas sécurisé.";
			}
			else {
//sgbd.setRequeteCreation(adresseMail,motDePasse, nom, prenom);
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
//test = recupererMotDePasse(motDePasse);
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

	public String modifierInformations(String chaineTestee){
		
		// ControleChamps
		
		
		
		return "";
	}
}