<%-- 
    Document   : connexion
    Created on : 7 avr. 2020, 13:16:09
    Author     : amalou
--%>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="connexion">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire.</p>
                <br/>
                
                
                <label for="nom">Pseudonyme <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="<c:out value="${utilisateur.nom}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['nom']}</span>
                
                <br/>
                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>

                <br/>
                <input type="submit" value="Connexion" class="sansLabel" />
                
                <span class="erreur">${form.erreurs['connexion']}</span>
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                
                <%-- Vérification de la présence d'un objet utilisateur en session --%>
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                    <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                    <p class="succes">Vous êtes connecté(e) avec le pseudonyme : ${sessionScope.sessionUtilisateur.nom}</p>
                    
                    <%
                    String redirectURL = "/projetAcol/restriction";
                    response.sendRedirect(redirectURL);
                    %>
                </c:if>
            </fieldset>
        <fieldset>
            <legend> Créer un compte </legend>
            <p>C’est rapide et facile </p>
            <td><a href="connexion?action=inscription">Inscription</a></td>
        </fieldset>
                
        </form>
    </body>
</html>