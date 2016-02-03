-- Rappel pour l'accès à la  base :
-- sur le web : https://phpmyadmin.alwaysdata.com/
-- id : stri
-- mdp : STRISTRI
-- Serveur : mysql-stri.alwaysdata.net

drop table Utilisateurs cascade constraint;
drop table Informations cascade constraint;
drop table Visibilite cascade constraint;
drop table Administrateurs cascade constraint;

create table Utilisateurs(
	mail VARCHAR(30) NOT NULL,
	mdp VARCHAR(20),
    	constraint Pk_Utilisateurs primary key(mail)
);

create table Administrateurs(
	mail VARCHAR(30),
	mdp VARCHAR(20)
);


create table Informations(
	mail VARCHAR(30),
	nom VARCHAR(20),
	prenom VARCHAR(20),
	telephone INT(10),
	diplomes VARCHAR(30),
	annee_diplomation INT(4),
	competences VARCHAR(100),
	constraint Fk_Informations_Utilisateurs foreign key(mail) references Utilisateurs(mail)
);

create table Visibilite(
	mail VARCHAR(30),
	infos_visibles_anonymes VARCHAR(20),
	infos_visibles_utilisateurs VARCHAR(20),
	constraint Fk_Visibilite_Utilisateurs foreign key(mail) references Utilisateurs(mail)
);

