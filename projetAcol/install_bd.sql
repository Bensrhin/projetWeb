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
CREATE SEQUENCE id_MessageJour;
CREATE SEQUENCE id_MessageNuit;
CREATE SEQUENCE id_Archive;

create table Utilisateur
(
    idUser number(6) default id_user.nextval,
    pseudonyme NVARCHAR2(20) not null unique,
    password NVARCHAR2(65) not null,
    email VARCHAR(320) not null,
    primary key(idUser,pseudonyme)
);

CREATE TABLE Partie (
    maitre NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    probaPouvoir float(10) not null,
    propLoup float(10) not null,
    periode NVARCHAR2(10) not null check (periode in ('Jour', 'Nuit')),
    primary key(maitre)
);

create table Joueur (
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    elimine NUMBER(1) not null check (elimine in (1,0)),
    role NVARCHAR2(20) not null check (role in ('humain','loupGarou')),
    pouvoir NVARCHAR2(20) not null check (pouvoir in ('aucun', 'voyance', 'contamination')),
    primary key(pseudonyme)

);

create table MessageJour (
    id_MessageJour number(6) default id_MessageJour.nextval,
    datePub NVARCHAR2(40) not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVARCHAR2(2000) not null,
    primary key(id_MessageJour)
);

create table MessageNuit (
    id_MessageNuit number(6) default id_MessageNuit.nextval,
    datePub NVARCHAR2(40) not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVARCHAR2(2000) not null,
    primary key(id_MessageNuit)
);

create table Archive (
    id_Archive number(6) default id_Archive.nextval,
    datePub NVARCHAR2(40) not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVARCHAR2(2000) not null,
    primary key(id_Archive)
);

/* Une table qui permet de stocker les noms des joueurs qui ont déja exercer
    leur pouvoir pendant la nuit */
create table ExercerPouvoir(
    exercerPar NVARCHAR2(20) not null references Joueur(pseudonyme),
    exercerSur NVARCHAR2(20) not null references Joueur(pseudonyme),
    primary key(exercerPar)
);
/* peupler la base de donnée*/
