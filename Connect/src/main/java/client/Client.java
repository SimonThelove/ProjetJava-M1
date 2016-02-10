package client;
import java.util.Scanner;
import gestionProtocole.GestionProtocoleClient;

public class Client {
	private Scanner sc = new Scanner(System.in);//Pour la lecture de saisie utilisateur
	private int choix;//Le choix saisie par l'utilisateur
	private GestionProtocoleClient gp = new GestionProtocoleClient(null);
	private String mail = null;//Recuperation du mail de l'utilisateur connecter
	
	public String getMail() {
		return mail;
	}

	public GestionProtocoleClient getGp() {
		return gp;
	}

	public Scanner getSc() {
		return sc;
	}

	public int getChoix() {
		return choix;
	}

	//Methode du menu anonyme
	public void menuAnonyme(){
	    System.out.println("\n\n1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    System.out.print("\nChoix : ");
	    try{
	    	//Recuperation de la saisie utlisateur
	    	choix = sc.nextInt();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Votre choix doit être un nombre.");
	    }
    }
	
	//Methode de la creation de compte
	public String creerCompte(){
	    System.out.println("\n\n--> INSCRIPTION\n");
	    System.out.print("Nom : ");
	    String nom = sc.nextLine();
	    System.out.print("Prenom : ");
	    String prenom = sc.nextLine();	
	    System.out.print("Mail : ");
	    String mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    
	    //Appelle a la concatenation de la requete creerCompte
	    gp.requeteCrea(nom, prenom, mail, motDePasse);
	    //Renvoit le resultat de la requete au client
	    return gp.getMessage();
	  }
	
	//Methode de la connexion
	public String seConnecter(){
	    System.out.println("\n\n--> Identification\n");
	    System.out.print("Nom d'utilisateur (votre mail) : ");
	    mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    
	    //Appelle a la concatenation de la requete connexion
	    gp.requeteConx(mail, motDePasse);
	    //Renvoit le resultat de la requete au client
	    return gp.getMessage();
	  }
	
	//Methode du menu connecter
		public void menuConnecter(){
		    System.out.println("\n\n1. Rechercher");
		    System.out.println("2. Modifier mes informations");
		    System.out.println("3. Se deconnecter");
		    System.out.print("\nChoix : ");
		    try{
		    	//Recuperation de la saisie utlisateur
		    	choix = sc.nextInt();
		    }
		    catch (Exception e)
		    {
		    	System.out.println("Votre choix doit etre un nombre.");
		    }
	    }
	  
	//Methode du menu Rechercher
	public void menuRechercher(){   
	    System.out.println("\n\n--> Recherche\n");
	    System.out.println("1. Rechercher par mots cles");
	    System.out.println("2. Recherche avancee");
	    System.out.println("3. Revenir au menu principal");
	    System.out.print("\nChoix : ");
	    try{
	    	//Recuperation de la saisie utlisateur
	    	choix = sc.nextInt();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Votre choix doit être un nombre.");
	    	choix = 0; //Permet de rester dans le menu Rechercher
	    }
	  }
	
	//Methode de la recherche par mots cles
	public String rechercherMotsCles(){   
	    System.out.println("\n\n--> Recherche par mot cles\n");
	    System.out.print("Saisissez votre recherche : ");
	    String motsCles = sc.nextLine();
	    
	    //Appelle a la concatenation de la requete rechercherMotsCles
	    gp.requeteRechMotsCles(motsCles);
	    if(gp.getNbPersonne() == 1)
	    {
	    	return gp.getMessage();
	    }
	    else
	    {
		    	do{
		    		System.out.println(gp.getMessage());
		    	}while((gp.getChoixProfil() < 1) && (gp.getChoixProfil() > gp.getNbPersonne()));

		    gp.requeteCons();
		    return gp.getMessage();
	    }
	  }
	
	//Methode de la recherche avancee
	public String rechercherAvancee(){   
		System.out.println("\n\n--> Recherche avancee	\n");
		System.out.print("Nom : ");
	    String nom = sc.nextLine();
	    System.out.print("Prenom : ");
	    String prenom = sc.nextLine();	
	    System.out.print("Mail : ");
	    String mailRech = sc.nextLine();
	    System.out.print("Intituler du diplome : ");
	    String nomDiplome = sc.nextLine();
	    System.out.print("Annee de diplomation : ");
	    String anneeDiplome = sc.nextLine();
	    System.out.print("Competence : ");
	    String competence = sc.nextLine();
	    
	    //Appelle à la concatenation de la requete rechercherAvancee
	    gp.requeteRechNom(nom, prenom, mailRech, nomDiplome, anneeDiplome, competence);
	    //Renvoit le resultat de la requete au client
	    return gp.getMessage();
	  }
	
	//Methode de la modification d'information
	public String modifierInfo(){   
		char info;//Variable permettant de connaitre si le client souhaite modifier un champs
		String requete = new String();
		
		//Demande pour chaque champs si le client souhaite modifier l'information
		//Si oui, alors il devra donner la nouvelle valeur du champ
		System.out.println("\n\n--> Modifier ses informations");
		
		//Pour le nom
		do{
			System.out.print("\nSouhaitez-vous modifier votre Nom ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
		if(info == 'o')
		{
			requete ="NOM|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouveau nom ? : ");
			requete += sc.nextLine() + "|";
		}

		//Pour le prenom
		do{
	    	System.out.print("\nSouhaitez-vous modifier votre Prenom ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
		if(info == 'o')
		{
			requete +="PRENOM|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouveau prenom ? : ");
			requete += sc.nextLine() + "|";
		}
		
		//Pour le mdp
	    do{
			System.out.print("\nSouhaitez-vous modifier votre Mot de passe ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
	    if(info == 'o')
		{
			requete +="MOTDEPASSE|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouveau mot de passe ? : ");
			requete += sc.nextLine() + "|";
		}
		
		//Pour le diplome
	    do{
		    System.out.print("\nSouhaitez-vous modifier votre Intituler du diplome ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
	    if(info == 'o')
		{
			requete +="DIPLOME|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouveau diplome ? : ");
			requete += sc.nextLine() + "|";
		}
		
		//Pour l'annee de diplomation
	    do{
		    System.out.print("\nSouhaitez-vous modifier votre Annee de diplomation ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
	    if(info == 'o')
		{
			requete +="ANNEE|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouvelle annee ? : ");
			requete += sc.nextLine() + "|";
		}
	    
		//Pour les competences
	    do{
		    System.out.print("\nSouhaitez-vous modifier vos Competences ? (o/n) : ");
			info = sc.nextLine().charAt(0);
		}while ((info != 'o') && (info != 'n'));
	    if(info == 'o')
		{
			requete +="COMPETENCES|";
		}
		if(info == 'o')
		{
			System.out.print("\nNouveau nom ? : ");
			requete += sc.nextLine();
		}
		System.out.print(requete);
		
		
	    //Appelle a la concatenation de la requete rechercherModification
	    gp.requeteModi(requete);
	    //Renvoit le resultat de la requete au client
	    return gp.getMessage();
	  }

	//Methode pour se deconnecter
	public String seDeconnecter() {
		//Appelle a la concatenation de la requete seDeconnecter
		gp.requeteDeco();
		//On retire le mail stocker de l'utilisateur recuperer pendant sa connexion
		mail = null;
	    //Renvoit le resultat de la requete au client
		return gp.getMessage();
	}

	//Methode pour quitter l'application
	public void quitter() {
		System.exit(0);
	}
}