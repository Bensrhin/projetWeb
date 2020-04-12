/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Partie;
import dao.DAOException;
import dao.PartieDao;
import forms.PartieForm;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author nadir
 */
@WebServlet(name = "ConfigurationPartie", urlPatterns = {"/configurationpartie"})
public class ConfigurationPartie extends HttpServlet {

    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String ATT_PARTIE = "partie";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/WEB-INF/partie.jsp";

    /* pages d'erreurs */
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/controleurErreur.jsp").forward(request, response);
    }

    private void erreurBD(HttpServletRequest request,
            HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace(); // permet d’avoir le détail de l’erreur dans catalina.out
        request.setAttribute("erreurMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/bdErreur.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Affichage de la page de configuration */
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        PartieForm partieform = new PartieForm();
        PartieDao partieDao = new PartieDao(ds);
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        try {
            Partie partie = partieform.configurerPartie(request, partieDao);
            /* Stockage du formulaire et du bean dans l'objet request */
            request.setAttribute(ATT_FORM, partieform);
            request.setAttribute(ATT_PARTIE, partie);

            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }

}
