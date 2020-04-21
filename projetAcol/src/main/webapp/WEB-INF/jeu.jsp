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
        <!DOCTYPE html>

<form method="post" action="Jeu">
<div class="container">
  <div class="chat-container">
        <c:forEach items="${messages}" var="message">
            <div class="message">
            <div class="datetime">sent by ${message.nameUtilisateur} at ${message.date}</div>
            <p>${message.contenu}</p>
            </div>
        </c:forEach>
  </div>

    
    <input type="text" name="contenu" value = "" placeholder="Your message">
    <input type="submit" value="Send" class="sansLabel" />
    <input type="hidden" name="action" value="SendMess" />
  </form>
</div>

</body>
</html>