import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args)
    {
 
    
		do {
			choix = menu();
			
			switch (choix) {
				case 1:
					creerCompte();
					break;
				case 2:
					approvisionnement(medicaments);
					break;
				case 3:
					affichage(clients, medicaments);
					break;
				case 4:
					quitter();
				default:
					clearConsole();
					System.out.println("Veuillez choisir une opération existante.");
					scanner.nextLine();
					System.out.println();
			}
	    } while (1);
	
	static int menu() {
	    int choix = 0;
	    System.out.println();
	    System.out.println();
	    System.out.println("1. S'inscrire");
	    System.out.println("2. S'identifier");
	    System.out.println("3. Rechercher (en tant qu'anonyme)");
	    System.out.println("4. Quitter");
	    while ((choix != 1) && (choix != 2) && (choix != 3) && (choix != 4)) {
	      choix = scanner.nextInt();
	    }
	    // se debarasser du \n 
	    scanner.nextLine();
	    return choix;
	  }
	
	
	public final static void clearConsole()
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
	
	static void quitter() {
	    System.out.println("Programme terminée!");
	  }
    }
	
}
