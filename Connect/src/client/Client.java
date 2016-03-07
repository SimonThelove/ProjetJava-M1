/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

/**
 *
 * @author Simon
 */
public class Client {
    
    private String nom, prenom, mail, tel, diplome, annee, competences, mdp;
    //private String mailCo, nomCo, prenomCo; //Ces variable permettent d'identifier la personnes connectee
    private String chaine;
    
    private Client client;
    
    public Client(){
        super();
    }

    public Client(String mail, String nom, String prenom, String diplome, String annee, String competences, String chaine){
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.diplome = diplome;
        this.annee = annee;
        this.competences = competences;
        this.chaine = chaine;
    }
    
    public Client getClient(){
        return client;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }
    
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
        public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
         this.chaine = chaine;
    }
    
    /*public String getMailCo() {
    return mailCo;
    }
    
    public void setMailCo(String mailCo) {
    this.mailCo = mailCo;
    }
    
    public String getNomCo() {
    return nomCo;
    }
    
    public void setNomCo(String nomCo) {
    this.nomCo = nomCo;
    }
    
    public String getPrenomCo() {
    return prenomCo;
    }
    
    public void setPrenomCo(String prenomCo) {
    this.prenomCo = prenomCo;
    }*/
}
