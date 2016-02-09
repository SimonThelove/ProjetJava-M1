package testClient;
import client.Client;

public class TestYohann {
	
	/*Main permettant de créer un nouveau client et afficher un menu
	afin qu'il puisse executer une action
	*/
	public static void main(String[] args)
    {
		Client test= new Client();
		System.out.println("Bienvenue dans Connect !");
		do {
			//Appelle du menu principal en mode anonyme
			test.menuAnonyme();
			test.getSc().nextLine();//Vider l'entrée clavier
			
			switch (test.getChoix()) {
				case 1:
					//Création d'un compte client
					System.out.println(test.creerCompte());
					break;
				case 2:
					//Connexion à l'application
					String[] verif = null;
					String message = null;
					do
					{
						message = test.seConnecter();
						System.out.println(message);
						verif = message.split(" ");
					}while(test.getMail() != "");
					
					do {
						//Appelle du menu connecté
						test.menuConnecter();
						test.getSc().nextLine();//Vider l'entrée clavier
						switch (test.getChoix()) {
							case 1:
								do {
									//Appelle du menu rechercher en mode connecté
									test.menuRechercher();
									test.getSc().nextLine();//Vider l'entrée clavier
									switch (test.getChoix()) {
										case 1:
											//Faire une recherche par mots Clés
											System.out.println(test.rechercherMotsCles());
											break;
										case 2:
											//Faire une recherche avec différents champs à remplir
											System.out.println(test.rechercherAvancee());
											break;
										case 3:
											//Retour au menu connecté
											System.out.println("Retour au menu connecté\n");
											break;
										default:
											System.out.println("Veuillez choisir un type de recherche ou retournez au menu connecté");
									}
								 } while (test.getChoix() != 3);
								break;
							case 2 :
								//Modifier ses informations
								System.out.println(test.modifierInfo());
								break;								
							case 3 :
								//Se déconnecter pour revenir au menu principal
								System.out.println(test.seDeconnecter());
								break;
							default:
								System.out.println("Veuillez choisir une action existante");
						}
					}while (test.getChoix() != 3);
					break;					
				case 3:
					do {
						//Appelle du menu rechercher en mode anonyme
						test.menuRechercher();
						test.getSc().nextLine();//Vider l'entrée clavier
						switch (test.getChoix()) {
							case 1:
								//Faire une recherche par mots Clés
								System.out.println(test.rechercherMotsCles());
								break;
							case 2:
								//Faire une recherche avec différents champs à remplir
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
