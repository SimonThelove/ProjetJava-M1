package testClient;
import client.Client;
import gestionProtocole.GestionProtocoleClient;

public class TestYohann {
	
	/*Main permettant de cr�er un nouveau client et afficher un menu
	afin qu'il puisse executer une action
	*/
	public static void main(String[] args)
    {
		Client test= new Client();
		System.out.println("Bienvenue dans Connect !");
		do {
			//Appelle du menu principal en mode anonyme
			test.menuAnonyme();
			test.getSc().nextLine();//Vider l'entr�e clavier
			
			switch (test.getChoix()) {
				case 1:
					//Cr�ation d'un compte client
					System.out.println(test.creerCompte());
					break;
				case 2:
					//Connexion � l'application
					System.out.println(test.seConnecter());
					
					//-------
					//Menu client mode connect�
					
					
					//-------
					break;
				case 3:
					do {
						//Appelle du menu rechercher en mode anonyme
						test.menuRechercher();
						test.getSc().nextLine();//Vider l'entr�e clavier
						switch (test.getChoix()) {
							case 1:
								//Faire une recherche par mots Cl�s
								System.out.println(test.rechercherMotsCles());
								break;
							case 2:
								//Faire une recherche avec diff�rents champs � remplir
								System.out.println(test.rechercherAvancee());
								break;
							case 3:
								//Retour au menu principal
								System.out.println("Retour au menu principal\n");
								break;
							default:
								System.out.println("Veuillez choisir un type de recherche ou retournez au menu principal");
						}
					 } while (test.getChoix() != 3);
					break;
				case 4:
					//Fermer l'application
					test.quitter();
				default:
					System.out.println("Veuillez choisir une action existante.");
			}
	    } while (test.getChoix() != 4);
    }
}
