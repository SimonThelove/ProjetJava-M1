-- REQUETES SERVEUR

-- Verifier si le compte n'existe pas déjà
SELECT mail_utilisateur	
FROM Utilisateurs
WHERE mail_utilisateur = adresseMail;
-- Dans le java, faire if requete=adresseMail > Impossible de creer le compte, sinon go creation compte

-- Créer un compte
INSERT INTO Utilisateurs VALUES (adresseMail, motDePasse);
INSERT INTO Informations (mail, nom, prenom) VALUES (adresseMail, nom, prenom);
INSERT INTO Visibilite VALUES ('', '', adresseMail);
-- Il faut definir comment on gere les visibilite + visiblite par defaut.

-- Connexion
SELECT mdp_utilisateur
FROM Utilisateurs
WHERE mail_utilisateur = adresseMail;
-- Dans le java, si requete = motDePasse, alors on peut se connecter

-- Modifications des informations dans informations
UPDATE Informations
SET infos_set
WHERE mail = adresseMail;
-- Il faudra construire la variable infos_set, avec comme possibilité dedans en fonction de ce qui est modifié
-- annee_diplomation=XXX, competences=XXX, diplomes=XXX, nom=XXX, prenom=XXX, telephone = XXX	

-- Modifications des informations dans visibilité
UPDATE Visiblite
SET infos_set
WHERE mail = adresseMail;
-- Il faudra construire la variable infos_set, avec comme possibilité dedans en fonction de ce qui est modifié
-- infos_visible_anonymes=XXX, infos_visible_utilisateurs=XXX

-- Modifications des informations dans utilisateur (Modification du mail))
UPDATE Utilisateurs
SET mail_utilisateur = new_adresseMail
WHERE mail_utilisateur = adresseMail; 

UPDATE Informations
SET mail = new_adresseMail
WHERE mail = adresseMail;

UPDATE Visibilite
SET mail = new_adresseMail
WHERE mail = adresseMail;
-- sous entendu, adresseMail = l'ancien mail


-- Verifier l'identification
SELECT mdp_utilisateur
FROM Utilisateurs
WHERE mail_utilisateur = adresseMail;


-- Requete de consultation
-- Je pense qu'on va récuperer tous le profil, et on fera l'affichage en fonction du type de compte et de ses droits
SELECT annee_diplomation, competences, diplomes, mail, nom, prenom, telephone
FROM Informations
WHERE mail=adresseMail -- sous entendu le mail dont on veut le profil evidemment

SELECT infos_visible_utilisateurs
FROM Visibilite
WHERE mail = adresseMail;

SELECT infos_visible_anonymes
FROM Visibilite
WHERE mail = adresseMail;

-- Requete de recherche par mots-clés








