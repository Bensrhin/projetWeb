/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import beans.Utilisateur;
import dao.UtilisateurDao;
import forms.ConnexionForm;
import dao.DAOException;
import dao.PartieDao;
import beans.Partie;

public class Restriction extends HttpServlet {
    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String ACCES_PUBLIC     = "/WEB-INF/connexion.jsp";
    public static final String ACCES_RESTREINT  = "/WEB-INF/comptePremierePage.jsp";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_PARTIE = "partieEnCours";
    public static final String ATT_PARTIE_C = "partieC";
    
    private void erreurBD(HttpServletRequest request,
                HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace(); // permet d’avoir le détail de l’erreur dans catalina.out
        request.setAttribute("erreurMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/bdErreur.jsp").forward(request, response);
    }
    
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération de la session depuis la requête */
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        /*
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
            /* Redirection vers la page publique */
            response.sendRedirect( request.getContextPath() + ACCES_PUBLIC );
        } else 
            {
                /* Affichage de la page restreinte */
                /* verification d'une partie en cours */
                PartieDao partieDao = new PartieDao(ds);
                Partie partie = new Partie();
                try {
                        boolean reponse = partieDao.partieEnCours(partie);
                        if (reponse){
                             request.setAttribute(ATT_PARTIE,"1");
                        } else {
                            request.setAttribute(ATT_PARTIE,"0");
                        }
                        request.setAttribute(ATT_PARTIE_C,partie);
                        this.getServletContext().getRequestDispatcher( ACCES_RESTREINT ).forward( request, response );
                     }
                catch (DAOException e)
                    {
                          erreurBD(request,response,e);
                     }
           
            }
    }
}