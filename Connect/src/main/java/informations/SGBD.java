package informations;

public class SGBD {
	
	private boolean testRecuperation;
	private boolean testVerification;
	
	private String requeteCreation;
	private String requeteModification;
	private String requeteConsultation;

	private String reponse;
	private String[] resultats;
	
	// Constructeurs : String reponse
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	// Constructeurs : String[] resultats
	public String[] getResultats() {
		return resultats;
	}
	public void setResultats(String[] resultats) {
		this.resultats = resultats;
	}

	// Requête de consultaiton de la base de données
	public void setRequeteConsultation(String requeteConsultation) {
		this.requeteConsultation = requeteConsultation;
	}
	
	// Requête de création dans la base de données
	public void setRequeteCreation(String requeteCreation) {
		this.requeteCreation = requeteCreation;
	}

	// Requête de modification dans la base de données
	public void setRequeteModification(String[] chaine) {
		
		// Assemblage de la requête SQL
				// Assemblage des N-1 premiers termes
				for(int n = 1; n <= 11; n += 2){
					requeteModification += chaine[n] + " = " + chaine[n+1] + " AND ";
				}
				// Ajout du dernier terme à la requête
				requeteModification += chaine[11] + " =  " + chaine[12] + ";";
	}
	
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
