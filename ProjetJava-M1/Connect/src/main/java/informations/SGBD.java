package informations;

public class SGBD {
	
	private boolean testRecuperation;
	private boolean testVerification;
	
	private String requeteCreation;
	private String requeteModification;
	private String requete;
	
	private String reponse;
	private String[] resultats;
	
	// Méthode de récupération du mail
	public boolean recupererMail(String adresseMail){
		
		//testRecuperation = Requete SQL getMail
		if (testRecuperation){
			return true;
		} else
			return false;
	}
	
	// Méthode de vérification du mot de passe (politique de sécurité des mots de passe)
	public boolean verifierMotDePasse(String motDePasse){
		
		//testVerification = politique de sécurité des mots de passe
		if (testVerification){
			return true;
		} else {
			return false;
		}
	}
	
	// Méthode de récupération du mot de passe
	public boolean recupererMotDePasse(String motDePasse){
		
		//testRecuperation = requete SQL getMotDePasse
		if (testRecuperation){
			return true;
		}
		else {
			return false;
		}
	}
	// Requête de création dans la base de données
	public void setRequeteCreation(String requeteCreation) {
		this.requeteCreation = requeteCreation;
	}

	// Requête de modification dans la base de données
	public void setRequeteModification(String requeteModification) {
		this.requeteModification = requeteModification;
	}
	
	// Requête de vérification des droits d'accès aux informations des utilisateurs
	public boolean isAdmin(){
		
		//testVerification = requête SQL getTypeCompte
		if (testVerification){
			return true;
		} else {
			return false;
		}
	}
	
	// Récupération des informations d'un profil utilisateur (admin)
	public String[] getAllInfos(){
		
		return resultats;
	}
	
	// Récupération des informations d'un profil utilisateur (selon visibilité)
	public String[] getVisibleInfos(){
		
		return resultats;
	}
	
	// Requête de recherche d'utilisateurs selon des mots clés
	public String[] getUtilisateurs(String[] motsCles) {
		
		return resultats;
	}
	
}
