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

	//M�thode du menu anonyme
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
	    	System.out.println("Votre choix doit �tre un nombre.");
	    }
    }
	
	//M�thode de la cr�ation de compte
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
	    
	    //Appelle � la concat�nation de la requ�te creerCompte
	    gp.requeteCrea(nom, prenom, mail, motDePasse);
	    return gp.getMessage();
	  }
	
	//M�thode de la connexion
	public String seConnecter(){
	    System.out.println("\n\n--> Identification\n");
	    System.out.print("Nom d'utilisateur (votre mail) : ");
	    String mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    
	    //Appelle � la concat�nation de la requ�te connexion
	    gp.requeteConx(mail, motDePasse);
	    return gp.getMessage();
	  }
	  
	//M�thode du menu Rechercher
	public void menuRechercher(){   
	    System.out.println("\n\n--> Recherche\n");
	    System.out.println("1. Rechercher par mots cl�s");
	    System.out.println("2. Recherche avanc�e");
	    System.out.println("3. Revenir au menu principal");
	    System.out.print("\nChoix : ");
	    try{
	    	choix = sc.nextInt();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Votre choix doit �tre un nombre.");
	    	choix = 0; //Permet de rester dans le menu Rechercher
	    }
	  }
	
	//M�thode de la recherche par mots cl�s
	public String rechercherMotsCles(){   
	    System.out.println("\n\n--> Recherche par mot cl�s\n");
	    System.out.print("Saisissez votre recherche : ");
	    String motsCles = sc.nextLine();
	    
	    //Appelle � la concat�nation de la requ�te rechercherMotsCles
	    gp.requeteRechMotsCles(motsCles);
	    return gp.getMessage();
	  }
	
	//M�thode de la recherche avanc�e
	public String rechercherAvancee(){   
		System.out.println("\n\n--> Recherche avanc�e	\n");
		System.out.print("Nom : ");
	    String nom = sc.nextLine();
	    System.out.print("Prenom : ");
	    String prenom = sc.nextLine();	
	    System.out.print("Mail : ");
	    String mail = sc.nextLine();
	    System.out.print("Intitul� du diplome : ");
	    String nomDiplome = sc.nextLine();
	    System.out.print("Ann�e de diplomation : ");
	    String anneeDiplome = sc.nextLine();
	    System.out.print("Comp�tence : ");
	    String competence = sc.nextLine();//Vider l'entr�e clavier
	    //Appelle � la concat�nation de la requ�te rechercherAvancee
	    gp.requeteRechNom(nom, prenom, mail, nomDiplome, anneeDiplome, competence);
	    return gp.getMessage();
	  }
	
	//M�thode pour quitter l'application
	public void quitter() {
		System.exit(0);
	}
}