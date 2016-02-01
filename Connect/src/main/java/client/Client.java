package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	private static Scanner sc;


	public static void main(String[] args)
    {
		int choix;   
		do {
			choix = menu();
			switch (choix) {
				case 1:
					//creerCompte();
					break;
				case 2:

					break;
				case 3:

					break;
				case 4:
					quitter();
				default:
					clearConsole();
					System.out.println("Veuillez choisir une action existante.");
					sc.nextLine();
					System.out.println();
			}
	    } while (choix < 4);
    }
	
	
	private static void quitter() {
		System.out.println("Programme terminé");
		
	}
	static int menu(){
	    int choix = 0;
	    sc = new Scanner(System.in); 
	    System.out.println();
	    System.out.println();
	    System.out.println("1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    while ((choix != 1) && (choix != 2) && (choix != 3) && (choix != 4)) {
	    	choix = sc.nextInt();
	       

			System.out.println("Vous avez saisi : " + sc); 
			sc.nextInt();
	    }
	    // se debarasser du \n 
	    sc.nextLine();
	    return choix;
	  }
	
	
	private static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
}