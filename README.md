# Application WEB : Leu Loups-Garous vs Humains
  Ce projet contient une application web de fameux jeu : loups-garous vs Humains.

## Développeurs

  * Nadir AIT LAHMOUCH
  * Naima AMALOU
  * Hamza BENJELLOUN
  * Nabil BENSRHIER

## Usage

L'utilisation de l'application nécessite l'installation de Netbeans et maven. Nous avons utilisé maven via Netbeans, donc le lancement de l'application ne peut pas se faire en ligne de commande.

## install_bd.sql

Ce fichier contient tous les requetes necessaire pour installer la base de données dont l'application à besoin , ainsi que un certains requetes
qui permet d'ajouter des utilisateurs dans l'application.

## uninstall.sql

Permet de supprimer tous les tables installé par install_bd.sql

## Les identifants de connexion

  ### pour les utilisateurs de l'application :
    Pour des raisons de simplification et vu que le mot de passe est haché par l'application, les utilisateurs installé dans install_bd.sql ont le même mot de passe qui est  : test


  ### Pour le site adminer.ensimag.fr/:
    username : amaloun
    password : amaloun

## Lancer l'Application

Pour lancer l'application il suffit d'ouvrir le projet maven dans Netbeans, et configurer la connexion à la base de données dans services Database en utilisant les identifiants précedants.
Ou bien dans n'importe quelle base de données , mais il faut executer le ficher install_bd.sql avant de lancer l'application. Ensuite il suffit de cliquer sur build project après run project.
