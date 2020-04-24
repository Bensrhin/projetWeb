<%-- 
    Document   : Jeu
    Created on : 20-Apr-2020, 19:16:55
    Author     : benjelloun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link type="text/css" rel="stylesheet" href="style_2.css" />
        <title>Jeu</title>    
    </head>
    <body>
        <h1>les loups-garous vs les humains</h1>
       
           
        <div class="information ">
        <p> Nom de joeur : "${joueur.pseudonyme}"</p>

         <c:choose>
            <c:when test="${joueur.elimine}">
                <p> Vous êtes éliminé de cette partie, vous pouvez lire encore la discussion de jeu. </p>
            </c:when>
        </c:choose>

        <p> Role : 
            <c:choose>
            <c:when test="${joueur.roleSt eq 'humain'}">
                Humain.
            </c:when>
            <c:when test="${joueur.roleSt eq 'loupGarou'}">
                Loup Garou.
            </c:when>
        </c:choose>
        </p>
        <p> Pouvoir : 
            <c:choose>
            <c:when test="${joueur.pouvoirSt eq 'voyance'}">
                Voyance
            </c:when>
            <c:when test="${joueur.pouvoirSt eq 'contamination'}">
                Contamination.
            </c:when>
            <c:when test="${joueur.pouvoirSt eq 'aucun'}">
                vous n'avez aucun pouvoir spéciale.
            </c:when>
        </c:choose>
        </p>
        <p>
            Periode : ${periode}  
        </p>
    </div>
<c:choose>
    <c:when test="${joueur.elimine}">
                <form method="post" action = "Jeu">
                    <input type="submit" value="Acceder au archive" class="Nuit"/>
                    <input type="hidden" name="action" value="archive" />
                </form>
    </c:when>       
</c:choose>
           
<div class="container">
  <div class="chat-container">
        <c:choose>
            <c:when test="${(joueur.role eq 'humain' && periode eq 'Jour') || (joueur.role eq 'loupGarou') || (joueur.elimine)}">
                <c:forEach items="${messages}" var="message">
                    <div class="message">
                    <div class="datetime">${message.date}</div>
                    <div class="pseudonyme">${message.nameUtilisateur}</div> 
                    <p>${message.contenu}</p>
                    </div>
                </c:forEach>
            </c:when>
            <c:when test="${joueur.role eq 'humain' && periode eq 'Nuit'}">
                <p>C'est la nuit vous ne pouvez pas discuter</p>
            </c:when>
        </c:choose>
  </div>
<form method="post" action="Jeu">
    <input type="text" name="contenu" value = "" placeholder="Your message">
    <c:choose>
        <c:when test="${(joueur.role eq 'humain' && periode eq 'Nuit') || (joueur.elimine)}">
            <input type="submit" value="Send" class="sansLabel" disabled/>
        </c:when>
        <c:otherwise>
            <input type="submit" value="Send" class="sansLabel"/>
        </c:otherwise>
    </c:choose>
    <input type="hidden" name="action" value="SendMess"/>
</form>
</div>

</body>
</html>