<%-- 
    Document   : comptePremierePage
    Created on : Apr 11, 2020, 3:02:04 AM
    Author     : nadir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page d'accueil compte</title>
        <style>
            body {
                background-image: url("pics/background_1.png");
                background-size: 2000px;
            }
            h1 {
                text-align: center;
                margin: 1em 0 0.5em 0;
                font-weight: 600;
                font-family: 'Titillium Web', sans-serif;
                position: relative;  
                font-size: 40px;
                line-height: 40px;
                padding: 15px 15px 15px 15%;
                color: #355681;
                box-shadow: 
                    inset 0 0 0 1px rgba(53,86,129, 0.4), 
                    inset 0 0 5px rgba(53,86,129, 0.5),
                    inset -285px 0 35px white;
                border-radius: 0 10px 0 10px;
                background: #fff url("pics/loup_garou.png") no-repeat center left;
                background-size: 200px;
            }
            h2{
                text-align: center;
                margin: 1em 0 0.5em 0;
                font-weight: normal;
                position: relative;
                text-shadow: 0 -1px rgba(0,0,0,0.6);
                font-size: 35px;
                line-height: 40px;
                background: #355681;
                background: rgba(53,86,129, 0.8);
                border: 1px solid #fff;
                padding: 5px 15px;
                color: white;
                border-radius: 0 10px 0 10px;
                box-shadow: inset 0 0 5px rgba(53,86,129, 0.5);
                font-family: 'Muli', sans-serif;

            }
            .button {
                
                display: inline-block;
                padding: 15px 25px;
                font-size: 24px;
                cursor: pointer;
                text-align: center;
                text-decoration: none;
                outline: none;
                color: #fff;
                background-color: #4CAF50;
                border: none;
                border-radius: 15px;
                box-shadow: 0 9px #999;
            }

            .button:hover {background-color: #3e8e41}

            .button:active {
                background-color: #3e8e41;
                box-shadow: 0 5px #666;
                transform: translateY(4px);
            }

        </style>    
    </head>
    <body>
        <br><br><br><br><br><br>
        <h1>Bienvenue dans votre compte  ${sessionScope.sessionUtilisateur.nom} </h1>
        <br><br>
        <h2>Que voulez vous faire ? </h2>
        <br><br>
        <p align="center">
            <a href="partie" class="button">Nouvelle Partie</a>
            <a href="" class="button">Rejoindre une partie</a>
        </p>
    </body>
</html>
