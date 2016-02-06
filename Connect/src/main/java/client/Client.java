package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import gestionProtocole.GestionProtocoleClient;

public class Client {
	private Scanner sc = new Scanner(System.in);
	private int choix;
	private GestionProtocoleClient gp = new GestionProtocoleClient(null);
	
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
	    System.out.println("\n\n--> INSCRIPTION");
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
	    String mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    
	    //Appelle à la concaténation de la requète connexion
	    gp.requeteConx(mail, motDePasse);
	    return gp.getMessage();
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
	    return gp.getMessage();
	  }
	
	//Méthode de la recherche avancée
	public String rechercherAvancee(){   
		System.out.println("\n\n--> Recherche avancée	\n");
		System.out.print("Nom : ");
	    String nom = sc.nextLine();
	    System.out.print("Prenom : ");
	    String prenom = sc.nextLine();	
	    System.out.print("Mail : ");
	    String mail = sc.nextLine();
	    System.out.print("Intitulé du diplome : ");
	    String nomDiplome = sc.nextLine();
	    System.out.print("Année de diplomation : ");
	    String anneeDiplome = sc.nextLine();
	    System.out.print("Compétence : ");
	    String competence = sc.nextLine();//Vider l'entrée clavier
	    //Appelle à la concaténation de la requète rechercherAvancee
	    gp.requeteRechNom(nom, prenom, mail, nomDiplome, anneeDiplome, competence);
	    return gp.getMessage();
	  }
	
	//Méthode pour quitter l'application
	public void quitter() {
		System.exit(0);
	}
}