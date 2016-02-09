package testClient;
import client.Client;

public class TestYohann {
	
	/*Main permettant de cr�er un nouveau client et afficher un menu
	afin qu'il puisse executer une action
	*/
	public static void main(String[] args)
    {
		Client test= new Client();
		
		int valide;
		String retour;
		
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
					do
					{
						retour = test.seConnecter();
						valide = retour.compareTo("Utilisateur inconnu.");
						if (valide != 0)
							valide = retour.compareTo("Votre mot de passe est incorrect.");
						System.out.println(retour);
					}while(valide == 0);
					
					do 
					{
						//Appelle du menu connect�
						test.menuConnecter();
						test.getSc().nextLine();//Vider l'entr�e clavier
						switch (test.getChoix()) {
							case 1:
								do {
									//Appelle du menu rechercher en mode connect�
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
											//Retour au menu connect�
											System.out.println("Retour au menu connect�\n");
											break;
										default:
											System.out.println("Veuillez choisir un type de recherche ou retournez au menu connect�");
									}
								 } while (test.getChoix() != 3);
								break;
							case 2 :
								//Modifier ses informations
								System.out.println(test.modifierInfo());
								break;								
							case 3 :
								//Se d�connecter pour revenir au menu principal
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
