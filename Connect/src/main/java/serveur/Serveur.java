package serveur;
import informations.SGBD;

public class Serveur {
	
	SGBD sgbd;
	
	// varibles de test
	boolean test = false;
	
	// Méthode de création d'un compte sur le serveur d'annuaire
	public String creerCompte(String nom, String prenom, String adresseMail, String motDePasse) {
		
		// ControleMail
		test = sgbd.recupererMail(adresseMail);
		if (test){
			// Adresse mail deja existante = Echec creation
			return "Mail déjà existant.";
		}
		else {
			// VerificationMotDePasse
			// test = verifierMotDePasse(motDePasse);
			if (!test) {
			return "Votre mot de passe n'est pas sécurisé.";
			}
			else {
				
				return "Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
			}
		}
	}
	
	// Méthode de connexion au serveur d'annuaire
	public String seConnecter(String adresseMail, String motDePasse){
		
		// ControleMail
				test = sgbd.recupererMail(adresseMail);
				if (!test){
					// Adresse mail non existante = Echec connexion
					return "Mail déjà existant.";
				}
				else {
					// VerificationMotDePasse
					// test = verifierMotDePasse(motDePasse);
					if (!test) {
					return "Votre mot de passe n'est pas sécurisé.";
					}
					else {
						
						return "Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
					}
				}
	}
	
	
/*    class CompteEnBanque {
        private double solde;
        private Date derniereOperation;
 
        public CompteEnBanque(double solde) {
            this.solde = solde;
            derniereOperation = new Date(); // recupere la date courante
        }
 
        public double getSolde() {
            return solde;
        }
 
        public Date getDerniereOperation() {
            return derniereOperation;
        }
 
        public void ajouter(double somme) {
            solde += somme;
            derniereOperation = new Date(); // recupere la date courante
        }
 
        public void retirer(double somme) {
            solde -= somme;
            derniereOperation = new Date(); // recupere la date courante
        }
    }
 
    HashMap<String, CompteEnBanque> comptes;
 
    public Serveur() {
        comptes = new HashMap<String, CompteEnBanque>();
    }
 
    public void creerCompte(String id, double somme) {
        comptes.put(id, new CompteEnBanque(somme));
    }
 
    public void ajouter(String id, double somme) {
        CompteEnBanque cpt = comptes.get(id);
        cpt.ajouter(somme);
    }
 
    public void retirer(String id, double somme) {
        CompteEnBanque cpt = comptes.get(id);
        cpt.retirer(somme);
    }
 
    public double getSolde(String id) {
        CompteEnBanque cpt = comptes.get(id);
        return cpt.getSolde();
    }
 
    public Date getDerniereOperation(String id) {
        CompteEnBanque cpt = comptes.get(id);
        return cpt.getDerniereOperation();
    }
 
    public static void main(String[] args) {
        Serveur s = new Serveur();
        s.creerCompte("ABC1234", 1000);
        s.ajouter("ABC1234", 100);
        s.retirer("ABC1234", 30);
        double solde = s.getSolde("ABC1234");
        Date date = s.getDerniereOperation("ABC1234");
        System.out.println("ABC1234 -> " + solde + " " + date);
    }
*/
}