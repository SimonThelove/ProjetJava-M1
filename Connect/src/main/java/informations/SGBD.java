package informations;

import java.sql.*;

public class SGBD extends Thread {
	
	private String requeteCreation;
	private String requeteModification;
	private String requeteConsultation;
	
	// Déclaration de variables globales pour executer le SQL
	/* Chargement du driver JDBC pour MySQL */
	private Connection con;
	private Statement st;
	private ResultSet rslt;
	private ResultSetMetaData rsmd;

	private String table;
	private String[] resultats = null;
	
	// Compteurs utilisés pour parcourir les tableaux et résultats
	private int i, j;
	
	// Connexion à la  BDD
	public void bdd() throws SQLException {
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    // Gérer les éventuelles erreurs ici.
		}
		con = DriverManager.getConnection("jdbc:mysql-stri.alwaysdata.net","stri","STRISTRI");
		st = con.createStatement();
	}
	
	// Constructeurs : String reponse
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		// A MODIFIER ou CONFIRMER UTILITE DE LA VARIABLE
		this.table = table;
	}

	// Constructeurs : String[] resultats
	public String[] getResultats() {
		return resultats;
	}
	public void setResultats(ResultSet rslt, ResultSetMetaData rsmd, String[] visibilite) throws SQLException {
		// Initialisation du compteur et du nombre de résultats SQL
		i = 0;
		j = 0;
		resultats[0] = "0";
		
		// Avec contrôle de la visibilité (getVisibleInfos)
		if (visibilite != null) {
			// On parcourt les résultats SQL
			while (rslt.next()) {
				// On parcourt les champs de chaque résultat SQL
				// tant qu'il y a des colonnes dans le ResultSet (start 1)
				while (i < rsmd.getColumnCount()) {
					// On avance dans resultats et sur la ligne du ResultSet
					i ++;
					// On teste le contenu de chaque colonne
					if (rslt.getString(i) != null) {
						// On teste le nombre de champs à affecter à resultats
						if (j < visibilite.length) {
							// On verifie la visibilite du champ pour l'affecter
							if (rsmd.getColumnLabel(i) == visibilite[j]) {
								// On remplit le tableau resultats
								resultats[i] = rsmd.getColumnLabel(i);
								resultats[i+1] = rslt.getString(i);
								// On incrémente j
								j ++;
							}
						}
						// On implémente le nombre résultats SQL dans resultats[0]
						// en utilisant le numéro de ligne de ResultSet comme référence (start 1)
						resultats[0] = "" + rslt.getRow() + "";
					}
				}
			}
		}
		// Sans contrôle de la visibilité (getAllInfos)
		else {
			// On parcourt les résultats SQL
			while (rslt.next()) {
				// On parcourt les champs de chaque résultat SQL
				// tant qu'il y a des colonnes dans le ResultSet (start 1)
				while (i < rsmd.getColumnCount()) {
					// On avance dans resultats et sur la ligne du ResultSet
					i ++;
					// On teste le contenu de chaque colonne
					if (rslt.getString(i) != null) {
						// On remplit le tableau resultats
						resultats[i] = rsmd.getColumnLabel(i);
						resultats[i+1] = rslt.getString(i);
						// On implémente le nombre résultats SQL dans resultats[0]
						// en utilisant le numéro de ligne de ResultSet comme référence (start 1)
						resultats[0] = "" + rslt.getRow() + "";
					}
				}
			}
		}
	}

	// Requête de consultaiton de la base de données
	public void setRequeteConsultation(String[] chaine) {

		// On fabrique le début la requête
		requeteConsultation = " SELECT * FROM informations WHERE (";

		if (chaine[1] == "MOTSCLES") {
 			// On stocke les mots clés dans un tableau
 			// Le séparateur est un espace (logique de saisie)
			String[] mots = chaine[2].split(" ");
 	
 			// On regarde le nombre de mots clés et on bricole
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
 
 			// On y colle la fin (On recherche tous les mots clés sauf le dernier)
 			for (i = 0; i <= (mots.length - 2); i++) {
 				requeteConsultation += "competences LIKE '%" + mots[i] + "%' OR ";
 			}
 			// On finit par rechercher le dernier mot clé en clôturant la requête
 			requeteConsultation += "competences LIKE '%" + mots[i] + "%');";
 	
 
		} else 	{
   			// Recherche par champs => on sait où chercher et on simplifie la fabrication de la requête
   			for (i = 1; i < (chaine.length - 2); i+= 2) {
    				requeteConsultation += chaine[i] + " LIKE '%" + chaine[i+1] + "%' AND ";
		   	}
    			// On finit la requête avec l'ajout du dernier champ
		 	requeteConsultation += chaine[i] + " LIKE '%" + chaine[i+1] + "%');";
		 }
	}
	
	// Requête de création dans la base de données
	public void setRequeteCreation(String nom, String prenom, String adresseMail, String motDePasse) {
		this.requeteCreation  = "INSERT INTO UTILISATEURS (mail, mdp) VALUES ('" + adresseMail+ "','" + motDePasse + "'); ";
		this.requeteCreation += "INSERT INTO INFORMATIONS (nom, prenom) VALUES ('" + nom + "','" + prenom + "'); ";
		this.requeteCreation += "INSERT INTO VISIBILITE VALUES ('',''," + adresseMail + ");";
	}

	// Requête de modification dans la base de données
	public void setRequeteModification(String[] chaine, String adresseMail) {
		
		// initialisation des variables
		i = 0;
		j = 0;

		// Recuperation de la table
		while(i < chaine.length){				// On parcourt la chaine
			j = i % 2;
			if (j == 1) {						// On teste le nom des champs
				if (chaine[i] == "mdp")			// Le champ mdp n'appartient qu'à la table utilisateurs
					setTable("utilisateurs");
				else if (chaine[i] == "infos_visibles_anonymes" || chaine[i] == "infos_visibles_utilisateurs")
					setTable("visibilite");		// Les champs infos_visibiles > table visibilite
				else
					setTable("informations");	// Les autres champs appartiennent à la table informations
			}
			i ++;
		}
		
		// Début de la requête
		requeteModification = "UPDATE " + table + " SET ";
		
		// Assemblage des N-1 termes suivants
		for(i = 1; i < (chaine.length - 2); i += 2){
			if (chaine[i+1] != null)
				requeteModification += chaine[i] + " = " + chaine[i+1] + ", ";
		}
		// Ajout du dernier terme à la requête
		requeteModification += chaine[i] + " =  " + chaine[i+1];
		
		// Ajout de la condition WHERE
		requeteModification += " WHERE mail = " + adresseMail + ";";
	}
	
	// Méthode de récupération du mail
	public boolean recupererMail(String adresseMail) throws SQLException{
	
            	rslt = st.executeQuery("SELECT mail FROM UTILISATEURS WHERE mail = '" + adresseMail + "'");
            	if (rslt != null)
            		return true;
            	else
            		return false;
	}
	
	// Méthode de vérification du mot de passe (politique de sécurité des mots de passe)
	public boolean verifierMotDePasse(String motDePasse){
			int taille = motDePasse.length();
			
    		if (taille >= 6)
    			return true;
    		else
    			return false;
	}
	
	// Méthode de récupération du mot de passe
	public boolean recupererMotDePasse(String motDePasse) throws SQLException{
		
            	rslt = st.executeQuery("SELECT mdp FROM UTILISATEURS WHERE mdp = '" + motDePasse + "'");
            	if (rslt != null)
            		return true;
            	else
            		return false;
	}
	
	// Requête de vérification des droits d'accès aux informations des utilisateurs
	public boolean isAdmin(String adresseMail) throws SQLException{
		
		rslt = st.executeQuery("SELECT mail FROM ADMINISTRATEURS WHERE mail = '" + adresseMail +"';");
		if (rslt.next()){
			return true;
		} else {
			return false;
		}
	}
	
	// Récupération des informations d'un profil utilisateur (admin)
	public String[] getAllInfos(String adresseMail) throws SQLException{
		
		// On fabrique les informations à transmettre
		String[] req = ("CONS|MAIL|" + adresseMail + "").split("|");
		setRequeteConsultation(req);
		
		// On l'exécute sur la BDD et on récupère les informations sur ces résultats
		rslt = st.executeQuery(requeteConsultation);
		rsmd = rslt.getMetaData();

		// On standardise les résultats
		setResultats(rslt,rsmd,null);
		
		return resultats;
	}
	
	// Récupération des informations d'un profil utilisateur (selon visibilité)
	public String[] getVisibleInfos(String adresseMail) throws SQLException{
		
		// On des variables de gestion de la visibilité
		String temp;
		String[] split;
		
		// On fabrique les informations à transmettre
		String[] req = ("CONS|MAIL|" + adresseMail + "").split("|");
		setRequeteConsultation(req);
		
		// On l'exécute sur la BDD et on récupère les informations sur ces résultats
		rslt = st.executeQuery(requeteConsultation);
		rsmd = rslt.getMetaData();
		
		// Gestion de la visibilité
		ResultSet visible = st.executeQuery("SELECT infos_visibles_anonymes FROM visibilite WHERE mail = '" + adresseMail + "';");
		temp = visible.getString("infos_visibles_anonymes");
		split = temp.split(",");
		
		// On standardise les résultats
		setResultats(rslt,rsmd,split);
		
		return resultats;
	}
	
	// Requête de recherche d'utilisateurs selon des mots clés
	public String[] getUtilisateurs(String[] motsCles) throws SQLException {
		
		// On fabrique la requête
		setRequeteConsultation(motsCles);
	
		// On l'exécute sur la BDD et on récupère les informations sur ces résultats
		rslt = st.executeQuery(requeteConsultation);
		rsmd = rslt.getMetaData();

		// On standardise les résultats
		setResultats(rslt,rsmd,null);

		return resultats;
	}
	
	// Mise à jour de la BDD = Action d'écriture donc besoin de gestion des accès concurrents
	// Creation ou Modification (Synchronized)
	public synchronized int executeUpdate(String type) throws SQLException {
		if (type == "CREA") {
			i = st.executeUpdate(requeteCreation);
			return i;
		} else {
			i = st.executeUpdate(requeteModification);
			return i;
		}
	}
}
