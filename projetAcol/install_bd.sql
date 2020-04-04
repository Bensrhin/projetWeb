/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  amalou
 * Created: 24 mars 2020
 */
CREATE SEQUENCE id_seq;

create table Utilisateur
(
    pseudonyme NVARCHAR2(20) primary key not null,
    password NVARCHAR2(60) not null,
    email NVARCHAR2(60) not null
); 

create table Partie
(
    idPartie number(6) DEFAULT id_seq.nextval PRIMARY KEY,
    nbrPart  INT(3)  not null,
    dureeJour float(10) not null,
    dureeNuit float(10) not null,
    heureDeb DATE not null,
    probaPouvoir float(10) not null,
    propLoup float(10) not null
);

create table Joeur
(
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    idPartie number(6) not null references Partie(idPartie),
    primary key(pseudonyme,idPartie)
    
);

create table Message
(
    idPartie number(6) not null references Partie(idPartie),
    datePub DATE not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVRCHAR(10000) not null,
    primary key(idPartie,datePub,pseudonyme)
);