/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  amalou
 * Created: 24 mars 2020
 */
CREATE SEQUENCE id_partie;
CREATE SEQUENCE id_user;

create table Utilisateur
(
    idUser number(6) default id_user.nextval,
    pseudonyme NVARCHAR2(20) not null,
    password NVARCHAR2(65) not null,
    email VARCHAR(320) not null,
    primary key(idUser,pseudonyme)
); 

CREATE TABLE Partie (
    idPartie number(6) DEFAULT id_partie.nextval PRIMARY KEY,
    nbrPart  integer  not null,
    dureeJour float(10) not null,
    dureeNuit float(10) not null,
    heureDeb DATE not null,
    probaPouvoir float(10) not null,
    propLoup float(10) not null
);

create table Joeur (
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    idPartie number(6) not null references Partie(idPartie),
    primary key(pseudonyme,idPartie)
    
);

create table Message (
    idPartie number(6) references Partie(idPartie),
    datePub DATE not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVRCHAR(10000) not null,
    primary key(idPartie,datePub,pseudonyme)
);