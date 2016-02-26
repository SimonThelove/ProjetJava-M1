/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package serveur;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Yohann
 */

public class SGBD extends Thread {
	private String requeteCreation;
	private String requeteModification;
	private String requeteConsultation;
	
	// Declaration de variables globales pour executer le SQL
	/* Chargement du driver JDBC pour MySQL */
	private Connection con;
	private Statement st;
	private Statement visibilite;
	private ResultSet rslt;
	private ResultSetMetaData rsmd;
	private String table;
	private ArrayList<String> resultats = new ArrayList<String>();
	
	// Compteurs utiliser pour parcourir les tableaux et resultats
	private int i, j;
	
	// Constructeurs : String reponse
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		// A MODIFIER ou CONFIRMER UTILITE DE LA VARIABLE
		this.table = table;
	}

	// Constructeurs : ArrayList<String> resultats
	public ArrayList<String> getResultats() {
		return resultats;
	}
	
	// Connexion e� la  BDD
	public void bdd() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://mysql-stri.alwaysdata.net/stri_connect","stri","STRISTRI");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			visibilite = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	

	public void setResultats(ResultSet rslt, ResultSetMetaData rsmd, String[] visibilite) {
		// Intialisation des compteurs
		i = 0;
		j = 0;
		int cpt = 0;
		String retour, champ;
		resultats.clear();
		
		// Depuis getVisibleInfos
		if (visibilite != null){
			// On parcourt les resultats SQL
			try {
				while (rslt.next()!= false){
					// Tant qu'il y a des colonnes resultat SQL
					while (i < rsmd.getColumnCount()){
						i ++;
						if(rslt.getString(i) != null){
							// On teste le nombre de champs a� affecter a�resultats
							if (j < visibilite.length) {
								// On verifie la visibilite du champ pour l'affecter
								if (rsmd.getColumnLabel(i).equalsIgnoreCase(visibilite[j])) {
									retour = rslt.getString(i);
									champ = rsmd.getColumnLabel(i);
									// On ajoute le nom du champ
									resultats.add(champ);
									// On ajoute la valeur du champ
									resultats.add(retour);
									// On incremente j
									j ++;
				System.out.println("niveau SGBD getVisible info" +resultats);
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Depuis getAllInfos
		else {
			// On parcourt les resultats SQL
			try {
				while (rslt.next() != false){
					i = 0;//Permet de recuperer les information du prochain profil
					// Tant qu'il y a des colonnes resultat SQL					
					while (i < rsmd.getColumnCount()){
						System.out.println("niveau SGBD getAllInfo" +resultats);
						i ++;
						retour = rslt.getString(i);
						champ = rsmd.getColumnLabel(i);
						// On ajoute le nom du champ
						resultats.add(champ);
						// On ajoute la valeur du champ
						resultats.add(retour);
					}
				}
				rslt.last();
				//On recupere le nombre de profil a afficher pour le mettre en tant que premier element de l'arrayListe
				cpt = rslt.getRow();
				resultats.add(0, Integer.toString(cpt));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Requete de consultaiton de la base de données
	public void setRequeteConsultation(String[] chaine) {

		// On fabrique le debut la requete
		requeteConsultation = " SELECT * FROM INFORMATIONS WHERE (";
		if (chaine[1].compareTo("MOTSCLES") != 0) {
                        System.out.println("SGBD chain 0 - " + chaine[0] + chaine[1]);
   			// Recherche par champs => on sait ou chercher et on simplifie la fabrication de la requete
   			for (i = 0; i < (chaine.length - 2); i+= 2) {
   				System.out.println("SGBD chain 1 - " + chaine[i]);
                                System.out.println("SGBD chain 2 - " + chaine[i+1]);
    				requeteConsultation += chaine[i] + " = '" + chaine[i+1] + "' AND ";
		   	}
    			// On finit la requete avec l'ajout du dernier champ
                        System.out.println("SGBD requeteConsultation - " + requeteConsultation);
		 	requeteConsultation += chaine[i] + " = '" + chaine[i+1] + "');";
		 	
		} else 	{
			// On stocke les mots clees dans un tableau
 			// Le separateur est un espace (logique de saisie)
			String[] mots = chaine[2].split(" ");
 	
 			// On regarde le nombre de mots clees et on boucle
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
 
 			// On ajoute la fin (On recherche tous les mots clés sauf le dernier)
 			for (i = 0; i <= (mots.length - 2); i++) {
 				requeteConsultation += "competences LIKE '%" + mots[i] + "%' OR ";
 			}
 			// On finit par rechercher le dernier mot clee en cloturant la requete
 			requeteConsultation += "competences LIKE '%" + mots[i] + "%');";
 			
		 }
		System.out.println("SGBD requeteConsultation - " + requeteConsultation);
	}
	
	// Requetes de creation dans la base de donnees
	public void setRequeteCreationUtil (String mail, String mdp) {
		this.requeteCreation  = "INSERT INTO UTILISATEURS (mail, mdp) VALUES ('" + mail + "','" + mdp + "'); ";
	}
	
	public void setRequeteCreationInfos (String nom, String prenom, String mail) {
		this.requeteCreation = "INSERT INTO INFORMATIONS (mail, nom, prenom) VALUES ('"+ mail +"','" + nom + "','" + prenom + "'); ";
	}
	
	public void setRequeteCreationVisible (String mail) {
		this.requeteCreation = "INSERT INTO VISIBILITE VALUES ('"+ mail +"','mail,nom,prenom','mail,nom,prenom');";
	}

	// Requete de modification dans la base de donnees
	public void setRequeteModification(String[] chaine, String adresseMail) {
		// initialisation des variables
		i = 0;
		j = 0;

		// Recuperation de la table
		while(i < chaine.length){				// On parcourt la chaine
			j = i % 2;
			if (j == 1) {						// On teste le nom des champs
				if (chaine[i] == "mdp")			// Le champ mdp n'appartient qu'a la table utilisateurs
					setTable("UTILISATEURS");
				else if (chaine[i] == "infos_visibles_anonymes" || chaine[i] == "infos_visibles_utilisateurs")
					setTable("VISIBILITE");		// Les champs infos_visibiles > table visibilite
				else
					setTable("INFORMATIONS");	// Les autres champs appartiennent a la table informations
			}
			i ++;
		}
		
		// Debut de la requete
		requeteModification = "UPDATE " + table + " SET ";
                
		// Assemblage des N-1 termes suivants
		for(i = 1; i < (chaine.length - 2); i += 2){
			if (chaine[i+1] != null)
				requeteModification += chaine[i] + " = '" + chaine[i+1] + "', ";
		}
		// Ajout du dernier terme a la requête
		requeteModification += chaine[i] + " =  '" + chaine[i+1] + "'";
		
		// Ajout de la condition WHERE
		requeteModification += " WHERE mail = '" + adresseMail + "';";
        System.out.println("SGBD setRequeteModification requeteModification : " + requeteModification);
	}
	
	// Methode de recuperation du mail
	public boolean recupererMail(String adresseMail) {
		bdd();    	
		try {
			rslt = st.executeQuery("SELECT mail FROM UTILISATEURS WHERE mail = '" + adresseMail + "'");
			if (rslt.next()) {
        		con.close();
        		return true;
        	}
        	else{
        		con.close();
        		return false;
        		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}     	
	}
	
	// Methode de verification du mot de passe (politique de securitee des mots de passe)
	public boolean verifierMotDePasse(String motDePasse){
			int taille = motDePasse.length();
			
    		if (taille >= 6)
    			return true;
    		else
    			return false;
	}
	
	// Methode de recuperation du mot de passe
	public boolean recupererMotDePasse(String motDePasse, String mail) {
		bdd();
            	try {
					rslt = st.executeQuery("SELECT mdp FROM UTILISATEURS WHERE mdp = '" + motDePasse + "' AND mail = '"+ mail +"';");
					if (rslt.next()){
	            		con.close();
	            		return true;
	            		}
	            	else {
	            		con.close();
	            		return false;
	            	}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
            	
	}
	
	// Requete de verification des droits d'acces aux informations des utilisateurs
	public boolean isAdmin(String adresseMail) {
		bdd();
		try {
			rslt = st.executeQuery("SELECT mail FROM ADMINISTRATEURS WHERE mail = '" + adresseMail +"';");
			if (rslt.next()){
	    		con.close();
				return true;
			} else {
	    		con.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	// Recuperation des informations d'un profil utilisateur (admin)
	public ArrayList<String> getAllInfos(String adresseMail) {
		bdd();
		// On fabrique les informations a transmettre
		String[] req = {"MAIL", adresseMail};
System.out.println("SGBD getAllInfo - req :" + req[1]);
		setRequeteConsultation(req);
		
		// On l'execute sur la BDD et on recupere les informations sur ces resultats
		try {
			rslt = st.executeQuery(requeteConsultation);
			rsmd = rslt.getMetaData();

			// On standardise les resultats
			setResultats(rslt,rsmd,null);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultats;
	}
	
	// Recuperation des informations d'un profil utilisateur (selon visibilitee)
	public ArrayList<String> getVisibleInfos(String adresseMail) {
		bdd();
		// On des variables de gestion de la visibilitee
		String temp;
		String[] split;
		
		// On fabrique les informations a transmettre
		String[] req = {"MAIL", adresseMail};
System.out.println("SGBD getVisibleInfo - req :" + req[1]);
		setRequeteConsultation(req);
		
		// On l'execute sur la BDD et on recupere les informations sur ces resultats
		try {
			rslt = st.executeQuery(requeteConsultation);
			rsmd = rslt.getMetaData();

			// Gestion de la visibilitee
			ResultSet visible = visibilite.executeQuery("SELECT infos_visibles_anonymes FROM VISIBILITE WHERE mail = '" + adresseMail + "';");
			visible.next();
			temp = visible.getString("infos_visibles_anonymes");
			System.out.println("SGBD temp :" + temp);
			split = temp.split(",");
			// On standardise les resultats
			setResultats(rslt,rsmd,split);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultats;
	}
	
	// Requete de recherche d'utilisateurs selon des mots clees
	public ArrayList<String> getUtilisateurs(String[] motsCles) {
		bdd();
		// On fabrique la requete
		setRequeteConsultation(motsCles);
		// On l'execute sur la BDD et on recupere les informations sur ces resultats
		try {
			rslt = st.executeQuery(requeteConsultation);
			rsmd = rslt.getMetaData();
			// On standardise les resultats
			setResultats(rslt,rsmd,null);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultats;
	}
	
	// Mise à jour de la BDD = Action d'ecriture donc besoin de gestion des acces concurrents
	// Creation ou Modification (Synchronized)
	public synchronized int executeUpdate(String type) {
		bdd();
		if (type == "CREA") {
			try {
				i = st.executeUpdate(requeteCreation);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i;
		} else {
			try {
				i = st.executeUpdate(requeteModification);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i;
		}
	}
}

