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
CREATE SEQUENCE id_Message;

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
    primary key(maitre)
);

create table Joueur (
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    primary key(pseudonyme)
    
);

create table Message (
    id_Message number(6) default id_Message.nextval,
    datePub NVARCHAR2(40) not null,
    pseudonyme NVARCHAR2(20) not null references Utilisateur(pseudonyme),
    contenu NVARCHAR2(2000) not null,
    primary key(id_Message)
);