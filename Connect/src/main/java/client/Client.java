package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	private static Scanner sc;


	public static void main(String[] args)
    {
		int choix;
		System.out.println("Bienvenue dans Connect !");
		do {
			choix = menuAnonyme();
			switch (choix) {
				case 1:
					//menuInscription();
					break;
				case 2:
					//menuIdentification();
					break;
				case 3:
					/*do {
						menuRechercher();
					 } while ((choix != 1) && (choix != 2) && (choix != 3));*/
					break;
				case 4:
					quitter();
				default:
					System.out.println("Veuillez choisir une action existante.");
					sc.nextLine();
					choix = menuAnonyme();
			}
	    } while ((choix != 1) && (choix != 2) && (choix != 3) && (choix != 4));
    }
	
	static int menuAnonyme(){
	    int choix = 0;
	    sc = new Scanner(System.in);
	   
	    System.out.println("\n\n1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    
    	choix = sc.nextInt();
	 
	    return choix;
	  }
/*
	static menuInscription(){
	   
	    System.out.println("\n\n--> INSCRIPTION\n");
	    System.out.println("Nom : ");
	    System.out.println("Prenom : ");
	    System.out.println("Mail : ");
	    System.out.println("Mot de passe : ");
	 
	    return ;
	  }
	
	static  menuIdentification(){
	   
	    System.out.println("\n\n--> Identification\n");
	    System.out.println("Nom d'utilisateur (votre mail) : ");
	    System.out.println("Mot de passe : ");
	 
	    return ;
	  }
	  
	  static  menuRechercher(){
	    int choix = 0;
	    sc = new Scanner(System.in);
	   
	    System.out.println("\n\n--> Recherche\n");
	    System.out.println("1. Rechercher par mots clés");
	    System.out.println("2. Recherche avancée");
	    System.out.println("3. Revenir au menu principal");
	 	
	 	choix = sc.nextInt();
	 	//test
	    return ;
	  }
	*/
	private static void quitter() {
		System.exit(0);
	}
}