 <%-- 
    Document   : comptePremierePage
    Created on : Apr 11, 2020, 3:02:04 AM
    Author     : nadir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="style.css" />
        <title>Page d'accueil compte</title>    
    </head>
    <body>
        <br><br><br><br><br><br>
        <h1>Bienvenue dans votre compte  ${sessionScope.sessionUtilisateur.nom} </h1>
        <br><br>
        <h2>Que voulez vous faire ? </h2>
        <br><br>
        <p align="center">
            <c:choose> 
            <c:when test="${partieEnCours == 0}">
               <a href="configurationpartie" class="button">Nouvelle Partie</a>
            </c:when>
            <c:otherwise>
                 Une partie crée par : ${partieC.maitre} est actuellement en cours , veuillez attendre la fin de cette partie, pour créer une nouvelle partie
                   <br><br>
              TODO : ce bouton doit apparaitre uniquement si l'utilisatuer est un joueur dans une partie en cours 
              <br></br>
              <a href="" class="button">Accéder à la partie en cours </a>
            </c:otherwise>
          </c:choose>
               
          
        </p>
    </body>
</html>
