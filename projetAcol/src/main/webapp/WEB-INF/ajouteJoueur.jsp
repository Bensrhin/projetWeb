<%-- 
    Document   : ajouteJoueur.jsp
    Created on : 12 avr. 2020, 23:24:41
    Author     : amalou
--%>


<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="configurationpartie">
            <fieldset>
                
                <legend> Selectionner les utilisateur à jouer dans la partie </legend>
                <br/>
                <table>
                <tr>
                    <th><!--Utilisateur--></th>
                    <th><!-- ajouter --></th>
                </tr>
                <c:forEach items="${utilisateur}" var="utilisateur">
                    <tr>
                        <td>${utilisateur.nom}</td>
                        <td><a href="configurationpartie?action=addUser&name=${utilisateur.nom}">ajouter</a></td>

                    </tr>
                </c:forEach>
                </table>
                
                <input type="hidden" name="action" value="LancerPartie"/>
                <input type="submit" value="Lancez la partie" class="sansLabel" />
                <br />

                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
    </body>
</html>
