package testClient;
import client.Client;
import gestionProtocole.GestionProtocoleClient;

public class TestYohann {
	
	public static void main(String[] args)
    {
		Client test= new Client();
		System.out.println("Bienvenue dans Connect !");
		do {
			test.menuAnonyme();
			test.getSc().nextLine();//Vider l'entrée clavier
			switch (test.getChoix()) {
				case 1:
					System.out.println(test.creerCompte());
					break;
				case 2:
					System.out.println(test.seConnecter());
					break;
				case 3:
					do {
						test.menuRechercher();
						test.getSc().nextLine();//Vider l'entrée clavier
						switch (test.getChoix()) {
							case 1:
								System.out.println(test.rechercherMotCles());
								break;
							case 2:
								System.out.println(test.rechercherAvancee());
								break;
							case 3:
								System.out.println("Retour au menu principal\n");
								break;
							default:
								System.out.println("Veuillez choisir un type de recherche ou retournez au menu principal");
						}
					 } while (test.getChoix() != 3);
					break;
				case 4:
					test.quitter();
				default:
					System.out.println("Veuillez choisir une action existante.");
			}
	    } while (test.getChoix() != 4);
    }
}
