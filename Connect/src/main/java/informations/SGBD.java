package informations;

import java.sql.*;

public class SGBD {
	
	private String requeteCreation;
	private String requeteModification;
	private String requeteConsultation;
	
	// Déclaration de variables globales pour executer le SQL
	private Connection con = DriverManager.getConnection("jdbc:mysql-stri.alwaysdata.net","stri","STRISTRI");
	private Statement st = con.createStatement();
	private ResultSet rslt;
	private ResultSetMetaData rsmd;

	private String reponse;
	private String[] resultats;
	
	// Compteur pour les boucles de recherche
	private int i;
	
	// Constructeurs : String reponse
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		// A MODIFIER ou CONFIRMER UTILITE DE LA VARIABLE
		this.reponse = reponse;
	}

	// Constructeurs : String[] resultats
	public String[] getResultats() {
		return resultats;
	}
	public void setResultats(String[] resultats) {
		// Initialisation du compteur et du nombre de résultats SQL
		i = 0;
		resultats[0] = 0;

		// On parcourt les résultats SQL
		while (rslt.next()) {
			// On parcourt les champs de chaque résultat SQL
			// tant qu'il y a des colonnes dans lle ResultSet (start 1)
			while ((i-1) <= rsmd.getColumnCount()) {
				// On teste le contenu de chaque colonne
				if (rslt.getString(i+1) != null) {
					// On remplit le tableau resultats
					resultats[i+1] = rsmd.getColumnLabel(i+1);
					resultats[i+2] = rslt.getString(i+1);
					// On avance dans resultats et sur la ligne du ResultSet
					i += 2;
					// On implémente le nombre résultats SQL dans resultats[0]
					// en utilisant le numéro de ligne de ResultSet comme référence (start 1)
					resultats[0] = (String)rslt.getRow();
				}
			}
		}
	}

	// Requête de consultaiton de la base de données
	public void setRequeteConsultation(String requeteConsultation) {
		// Création de la requête

		// On fabrique le début la requête
		req = " SELECT * FROM informations WHERE (";

		if (chaine[1] == "MOTSCLES") {
 			// On stocke les mots clés dans un tableau
 			// Le séparateur est un espace (logique de saisie)
			String[] mots = chaine[2].split(" ");
 	
 			// On regarde le nombre de mots clés et on bricole
        		// Pour MAIL
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "mail LIKE '%" + mots[i] + "%' OR ";
 			}
 			// Pour NOM
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "nom LIKE '%" + mots[i] + "%' OR ";
 			}
 			// Pour PRENOM
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "prenom LIKE '%" + mots[i] + "%' OR ";
 			}
 			// Pour TELEPHONE
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "telephone LIKE '%" + mots[i] + "%' OR ";
 			}
 			// Pour DIPLOMES
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "diplomes LIKE '%" + mots[i] + "%' OR ";
 			}
 			// Pour ANNEE_DIPLOMATION
 			for (i = 0; i <= (mots.length - 1); i++) {
 				req += "annee_diplomation LIKE '%" + mots[i] + "%' OR ";
 			}
 
 			// On y colle la fin (On recherche tous les mots clés sauf le dernier)
 			for (i = 0; i <= (mots.length - 2); i++) {
 				req += "competences LIKE '%" + mots[i] + "%' OR ";
 			}
 			// On finit par rechercher le dernier mot clé en clôturant la requête
 			req += "competences LIKE '%" + mots[i] + "%');";
 	
 
		} else 	{
   			// Recherche par champs => on sait où chercher et on simplifie la fabrication de la requête
   			for (i = 1; i < (chaine.length - 2); i+= 2) {
    				req += chaine[i] + " LIKE '%" + chaine[i+1] + "%' AND ";
		   	}
    			// On finit la requête avec l'ajout du dernier champ
		 	req+= chaine[i] + " LIKE '%" + chaine[i+1] + "%');";
		 }
	}
	
	// Requête de création dans la base de données
	public void setRequeteCreation(String nom, String prenom, String adresseMail, String motDePasse) {
		this.requeteCreation  = "INSERT INTO UTILISATEURS (mail, mdp) VALUES ('" + adresseMail+ "','" + motDePasse + "'); ";
		this.requeteCreation += "INSERT INTO INFORMATIONS (nom, prenom) VALUES ('" + nom + "','" + prenom + "'); ";
		this.requeteCreation += "INSERT INTO VISIBILITE VALUES ('',''," + adresseMail + ");";
	}

	// Requête de modification dans la base de données
	public void setRequeteModification(String[] chaine) {
		// A MODIFIER CAR CA NE VA PAS DU TOUT MARCHER COMME CA
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
	
            	rslt = st.executeQuery("SELECT mail FROM UTILISATEURS WHERE mail = '" + adresseMail + "'");
            	if (rslt != null)
            		return true;
            	else
            		return false;
	}
	
	// Méthode de vérification du mot de passe (politique de sécurité des mots de passe)
	public boolean verifierMotDePasse(String motDePasse){
		
    		if (int taille = motDePasse.length() >= 6)
    			return true;
    		else
    			return false;
	}
	
	// Méthode de récupération du mot de passe
	public boolean recupererMotDePasse(String motDePasse){
		
            	rslt = st.executeQuery("SELECT mdp FROM UTILISATEURS WHERE mdp = '" + motDePasse + "'");
            	if (rslt != null)
            		return true;
            	else
            		return false;
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
		
		// On fabrique la requête
		setRequeteConsultation(motsCles);
	
		// On l'exécute sur la BDD et on récupère les informations sur ces résultats
		rslt = st.executeQuery(requeteConsultation);
		rsmd = rslt.getMetaData();

		// On standardise les résultats
		setResultats();

		return resultats;
	}
	
	// Mise à jour de la BDD
	// Creation ou Modification
	public int executeUpdate(String type) {
		if (type == "CREA") {
			i = st.executeUpdate(requeteCreation);
			return i;
		} else if (type == "MODI") {
			i = st.executeUpdate(requeteModificaiotn);
			return i;
		}
	}
}
