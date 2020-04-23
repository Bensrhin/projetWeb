<%-- 
    Document   : jeuMaitre
    Created on : 23 avr. 2020, 18:10:17
    Author     : amalou
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
        
        <div class="information">
            Vous êtes le maitre de jeu
        </div>
            
        <br><br>
        <c:choose>
            <c:when test="${maitrejeu == '1'&& periode eq'Jour'}">
                <form method="post" action = "GestionPartie">
                    <input type="submit" value="Nuit" class="Nuit"/>
                    <input type="hidden" name="action" value="passernuit" />
                </form>
                <form method="post" action = "GestionPartie">
                    <input type="submit" value="Jour" class="Jour" disabled/>
                    <input type="hidden" name="action" value="passeraujour" />    
                </form> 
            </c:when>
            <c:when test="${maitrejeu == '1' && periode == 'Nuit'}">
                <form method="post" action = "GestionPartie">
                    <input type="submit" value="Nuit" class="Nuit" disabled/>
                    <input type="hidden" name="action" value="passernuit" />
                </form>
                <form method="post" action = "GestionPartie">
                    <input type="submit" value="Jour" class="Jour" />
                    <input type="hidden" name="action" value="passeraujour" />    
                </form> 
            </c:when>
        </c:choose>        
        <div class="container">
          <div class="chat-container">
                <c:forEach items="${messages}" var="message">
                    <div class="message">
                    <div class="datetime">${message.date}</div>
                    <div class="pseudonyme">${message.nameUtilisateur}</div> 
                    <p>${message.contenu}</p>
                    </div>
                </c:forEach>
          </div>
        <form method="post" action="Jeu">
            <input type="text" name="contenu" value = "" placeholder="Your message">
            <input type="submit" value="Send" class="sansLabel" disabled/>
            <input type="hidden" name="action" value="SendMess"/>
        </form>
        </div>

    </body>
</html>
