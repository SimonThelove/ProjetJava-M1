package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import gestionProtocole.GestionProtocoleClient;

public class Client {
	private Scanner sc = new Scanner(System.in);
	private int choix;
	private GestionProtocoleClient gp;
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

	public void menuAnonyme(){
	    System.out.println("\n\n1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    System.out.print("\nChoix : ");
    	choix = sc.nextInt();
    	
    }

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
	    gp.setRequeteCrea(nom, prenom, mail, motDePasse);
	    return gp.getMessage();
	  }
	
	public String seConnecter(){
	    System.out.println("\n\n--> Identification\n");
	    System.out.print("Nom d'utilisateur (votre mail) : ");
	    String mail = sc.nextLine();
	    System.out.print("Mot de passe : ");
	    String motDePasse = sc.nextLine();
	    gp.setRequeteConx(mail, motDePasse);
	    return gp.getMessage();
	  }
	  
	public int menuRechercher(){   
	    System.out.println("\n\n--> Recherche\n");
	    System.out.println("1. Rechercher par mots clés");
	    System.out.println("2. Recherche avancée");
	    System.out.println("3. Revenir au menu principal");
	    System.out.print("\nChoix : ");
	 	choix = sc.nextInt();
	 	return choix;
	  }
	
	public String rechercherMotCles(){   
	    System.out.println("\n\n--> Recherche par mot clés\n");
	    System.out.print("Saisissez votre recherche : ");
	    String motsCles = sc.nextLine();
	    gp.setRequeteRechMotsCles(motsCles);
	    return gp.getMessage();
	  }
	
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
	    gp.setRequeteRechNom(nom, prenom, mail, nomDiplome, anneeDiplome, competence);
	    return gp.getMessage();
	  }
	
	public void quitter() {
		System.exit(0);
	}
}