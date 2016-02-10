package client;
import java.util.Scanner;
import gestionProtocole.GestionProtocoleClient;

public class Client {
	private Scanner sc = new Scanner(System.in);
	private int choix;
	private GestionProtocoleClient gp = new GestionProtocoleClient(null);
	private String mail = null;
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public GestionProtocoleClient getGp() {
		return gp;
	}

	public void setGp(GestionProtocoleClient gp) {
		this.gp = gp;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}

	//Méthode du menu anonyme
	public void menuAnonyme(){
	    System.out.println("\n\n1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    System.out.print("\nChoix : ");
	    try{
	    	choix = sc.nextInt();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Votre choix doit être un nombre.");
	    }
    }
	
	//Méthode de la création de compte
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
	    
	    //Appelle à la concaténation de la requète creerCompte
	    gp.requeteCrea(nom, prenom, mail, motDePasse);
	    return gp.getMessage();
	  }
	
	//Méthode de la connexion
	public String seConnecter(){
	    System.out.println("\n\n--> Identification\n");
	    System.out.print("Nom d'utilisateur (votre mail) : ");
	    mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    //Appelle à la concaténation de la requète connexion
	    gp.requeteConx(mail, motDePasse);
	    return gp.getMessage();
	  }
	
	//Méthode du menu connecté
		public void menuConnecter(){
		    System.out.println("\n\n1. Rechercher");
		    System.out.println("2. Modifier mes informations");
		    System.out.println("3. Se déconnecter");
		    System.out.print("\nChoix : ");
		    try{
		    	choix = sc.nextInt();
		    }
		    catch (Exception e)
		    {
		    	System.out.println("Votre choix doit être un nombre.");
		    }
	    }
	  
	//Méthode du menu Rechercher
	public void menuRechercher(){   
	    System.out.println("\n\n--> Recherche\n");
	    System.out.println("1. Rechercher par mots clés");
	    System.out.println("2. Recherche avancée");
	    System.out.println("3. Revenir au menu principal");
	    System.out.print("\nChoix : ");
	    try{
	    	choix = sc.nextInt();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Votre choix doit être un nombre.");
	    	choix = 0; //Permet de rester dans le menu Rechercher
	    }
	  }
	
	//Méthode de la recherche par mots clés
	public String rechercherMotsCles(){   
	    System.out.println("\n\n--> Recherche par mot clés\n");
	    System.out.print("Saisissez votre recherche : ");
	    String motsCles = sc.nextLine();
	    
	    //Appelle à la concaténation de la requète rechercherMotsCles
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
	
	//Méthode de la recherche avancée
	public String rechercherAvancee(){   
		System.out.println("\n\n--> Recherche avancée	\n");
		System.out.print("Nom : ");
	    String nom = sc.nextLine();
	    System.out.print("Prenom : ");
	    String prenom = sc.nextLine();	
	    System.out.print("Mail : ");
	    String mailRech = sc.nextLine();
	    System.out.print("Intitulé du diplome : ");
	    String nomDiplome = sc.nextLine();
	    System.out.print("Année de diplomation : ");
	    String anneeDiplome = sc.nextLine();
	    System.out.print("Compétence : ");
	    String competence = sc.nextLine();
	    //Appelle à la concaténation de la requète rechercherAvancee
	    gp.requeteRechNom(nom, prenom, mailRech, nomDiplome, anneeDiplome, competence);
	    return gp.getMessage();
	  }
	
	//Méthode de la modification d'information
		public String modifierInfo(){   
			char info;
			String requete = new String();
			System.out.println("\n\n--> Modifier ses informations");
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
			
		    do{
			    System.out.print("\nSouhaitez-vous modifier votre Intitulé du diplome ? (o/n) : ");
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
			
		    do{
			    System.out.print("\nSouhaitez-vous modifier votre Année de diplomation ? (o/n) : ");
				info = sc.nextLine().charAt(0);
			}while ((info != 'o') && (info != 'n'));
		    if(info == 'o')
			{
				requete +="ANNEE|";
			}
			if(info == 'o')
			{
				System.out.print("\nNouvelle année ? : ");
				requete += sc.nextLine() + "|";
			}
		    
		    do{
			    System.out.print("\nSouhaitez-vous modifier vos Compétences ? (o/n) : ");
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
			
			
		    //Appelle à la concaténation de la requète rechercherModification
		    gp.requeteModi(requete);
		    return gp.getMessage();
		  }
	
		//Méthode pour se déconnecter
		public String seDeconnecter() {
			gp.requeteDeco();
			mail = null;
			return gp.getMessage();
		}
	
	//Méthode pour quitter l'application
	public void quitter() {
		System.exit(0);
	}
}