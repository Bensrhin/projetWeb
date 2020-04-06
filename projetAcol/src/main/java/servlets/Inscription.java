/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import forms.InscriptionForm;
import beans.Utilisateur;
import dao.UtilisateurDao;
import dao.DAOException;

/**
 *
 * @author amalou
 */
@WebServlet(name = "Inscription", urlPatterns = {"/inscription"})
public class Inscription extends HttpServlet {
    
    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/WEB-INF/inscription.jsp";
		
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();
        UtilisateurDao userDao = new UtilisateurDao(ds);
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur(request, userDao);
		
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
