package gestionProtocole;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

import serveur.Serveur;
 

import java.io.BufferedReader;
 
public class GestionProtocoleServeur {
 
    private Serveur serveur;
    private String reponse;
     
    public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
    }
    
    public String requete(String entreeSocket) throws SQLException{
        String[] req = entreeSocket.split("|");
        switch(req[0])
        {
	        case "CREA":
	            setReponse("MSG|" + serveur.creerCompte(req[2], req[4], req[6], req[8]));
	      
		    case "CONX":
	            setReponse("MSG|" + serveur.seConnecter(req[2], req[4]));
		            
		    case "MODI": // AJOUTER LE MAIL DU CLIENT CONNECTE (condition WHERE du SQL UPDATE)
	            setReponse("MSG|" + serveur.modifierInformations(req,"adresseMail"));

		    case "CONS":
	            setReponse("MSG|" + serveur.consulter(req[2]));

		    case "RECH":
	            setReponse("MSG|" + serveur.rechercher(req));

		    case "DECO":
	            setReponse("MSG|" + serveur.seDeconnecter());

        }
        return reponse;
    }

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

}