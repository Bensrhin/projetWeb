<%--
    Document   : Jeu
    Created on : 20-Apr-2020, 19:16:55
    Author     : benjelloun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="beans.Joueur" %>
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
        <p> Nom du joeur : "${joueur.pseudonyme}"</p>

         <c:choose>
            <c:when test="${joueur.elimine}">
                <p> Vous êtes éliminé de cette partie, vous pouvez lire encore la discussion de jeu. </p>
            </c:when>
        </c:choose>

        <p> Rôle :
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
            Période : ${periode}
        </p>
        </div>
            
        <br>
        <br>
        
        <c:if test="${mort!=null}">
            <div class="new-death">
                <h3>"${mort.pseudonyme}" est trouvé mort.</h3>
                
            </div>
        </c:if>

              
        <br>
        <br>
<c:choose>
    <c:when test="${joueur.elimine}">
                <form method="post" action = "Jeu">
                    <input type="submit" value="Acceder au archive" class="Nuit"/>
                    <input type="hidden" name="action" value="archive" />
                </form>
    </c:when>

    <c:when test="${not joueur.elimine && periode eq 'Jour'}">
                <form method="post" action = "Jeu">
                    <label>Proposer un villageois : </label> : 
                <select name="villageois">
                    <c:forEach items="${villageois}" var="villageois">
                    <option value="${villageois.pseudonyme}">${villageois.pseudonyme}</option>
                    </c:forEach>
                    <option value="nothing" selected>Choisir un villageois</option>
                </select>
                    <c:forEach items="${proposed}" var="proposed">
                        <c:if test="${proposed.getVote().contains(joueur.pseudonyme)}">
                            <c:set var = "propose" value = "${proposed.pseudonyme}"/>
                        </c:if>
                    </c:forEach>
                    <input type="submit" value="Valider"/>
                    <input type="hidden" name="propose" value="${propose}" />
                    <input type="hidden" name="action" value="proposer" />
                </form>
    </c:when>   
    <c:when test="${not joueur.elimine && periode eq 'Nuit' && joueur.role eq 'loupGarou'}">
                <form method="post" action = "Jeu">
                    <label>Proposer un villageois : </label> : 
                <select name="villageois">
                    <c:forEach items="${villageois}" var="villageois">
                    <option value="${villageois.pseudonyme}">${villageois.pseudonyme}</option>
                    </c:forEach>
                    <option value="nothing" selected>Choisir un villageois</option>
                </select>
                    <c:forEach items="${proposed}" var="proposed">
                        <c:if test="${proposed.getVote().contains(joueur.pseudonyme)}">
                            <c:set var = "propose" value = "${proposed.pseudonyme}"/>
                        </c:if>
                    </c:forEach>
                    <input type="submit" value="Valider"/>
                    <input type="hidden" name="propose" value="${propose}" />
                    <input type="hidden" name="action" value="proposer" />
                </form>
    </c:when>   
</c:choose>

<br>  
<br>
<br>
        <c:if test="${proposed!=null && proposed.size()!=0}">
            <table align="center">
            <tr>
                <th> Villageois Proposés </th>
                <th> Ractifier la proposition </th>
                <th> Nombre de votes </th>
            </tr>
            <c:forEach items="${proposed}" var="proposed">
            <tr>
                <td> ${proposed.pseudonyme} </td>
                <td><a href="Jeu?action=addVote&&id=${proposed.pseudonyme}">voter</a></td>
                <td> ${proposed.getNbVote()} </td>
            </tr>
            </c:forEach>
            

        </table>
        </c:if>
        



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


<!-- Traitement des pouvoir spéciale --->
<!-- voyance -->
<c:if test="${joueur.pouvoirSt eq 'voyance' && periode eq 'Nuit'}">
    <div class="voyance">
        <p>
            Chaque nuit vous avez le droit de connaitre le role et le pouvoir d'un joueur
        </p>
        <form method="post" action="Jeu">
             <c:choose>
            <c:when test="${not exercerPouvoir}">
                <p> Vous n'avez pas encore exercer votre pouvoir cette nuit</p>
            </c:when>
        </c:choose>
        <input type="hidden" name="action" value="pouvoir"/>
        </form>
    </div>
</c:if>


<!-- contamination -->
<c:if test="${joueur.pouvoirSt eq 'contamination' && periode eq 'Nuit' && joueur.roleSt eq 'loupGarou'}">
    <div class="contamination">
        <p>
            Chaque nuit vous avez le droit de transformer un humain en un loup Garou
        </p>
        <form method="post" action="Jeu">
             <c:choose>
            <c:when test="${not exercerPouvoir}">
                <p> Vous n'avez pas encore exercer votre pouvoir cette nuit</p>
                <p>
                <label for="contamine">Veuillez choisir le joueur à transformer en loup Garou:</label>
                <select name="contamine" id="contamine">
                     <c:forEach items="${humain}" var="humain">
                         <option value="${humain.pseudonyme}">${humain.pseudonyme}</option>
                    </c:forEach>

                </select>
            </p>
            <input type="submit" name="bouton" value="Contaminé cet humain">
            </c:when>
            
            <c:when test="${exercerPouvoir}">
                <p> Vous avez déja exercer votre pouvoir sur ${exercerSur} </p>

            </c:when>
        </c:choose>

        <input type="hidden" name="action" value="pouvoirContamination"/>
        </form>
    </div>
</c:if>
</body>
</html>
