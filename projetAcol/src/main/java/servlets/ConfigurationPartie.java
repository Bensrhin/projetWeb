/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Partie;
import dao.DAOException;
import dao.PartieDao;
import dao.JoueurDao;
import forms.PartieForm;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import beans.Utilisateur;
import dao.UtilisateurDao;
import java.io.*;
import java.util.List;
import beans.Joueur;
import java.util.ArrayList;

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
    private List<Joueur> joueurs = new ArrayList<>();
    private Partie partie;

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
        /** Affichage des utilisateur en lignes dans la pages **/
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        UtilisateurDao userDao = new UtilisateurDao(ds);

        try {
            if (action == null) {
                Utilisateur maitre = (Utilisateur) session.getAttribute(Connexion.ATT_SESSION_USER);
                assert(maitre != null);
                List<Utilisateur> utilisateurs = userDao.getListeUtilisateurs(maitre.getNom()); 
                /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
                 * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
                 * avant un forward, comme ici)
                 * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
                request.setAttribute("utilisateur", utilisateurs);
                this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
            } else if (action.equals("addUser")){
                
                actionAddUser(request, response, userDao);
               
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
      
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        try {
            if (action.equals("AddPlayers")) {
                PartieForm partieform = new PartieForm();
                PartieDao partieDao = new PartieDao(ds);
                UtilisateurDao userDao = new UtilisateurDao(ds);
                this.partie = partieform.configurerPartie(request, partieDao);
                /* Stockage du formulaire et du bean dans l'objet request */
                request.setAttribute(ATT_FORM, partieform);
                request.setAttribute(ATT_PARTIE, partie);
                Utilisateur maitre = (Utilisateur) session.getAttribute(Connexion.ATT_SESSION_USER);
                assert(maitre != null);
                List<Utilisateur> utilisateurs = userDao.getListeUtilisateurs(maitre.getNom()); 
                /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
                 * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
                 * avant un forward, comme ici)
                 * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
                request.setAttribute("utilisateur", utilisateurs);
                this.getServletContext().getRequestDispatcher("/WEB-INF/ajouteJoueur.jsp").forward(request, response);
            } else if (action.equals("LancerPartie")){
                
                /**
                 * ancienne version
                JoueurDao joueurs = new JoueurDao(ds);
                joueurs.addJoueurs(this.joueurs);
                **/
                
                String redirectURL = "/projetAcol/restriction";
                response.sendRedirect(redirectURL);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }
    
    public void actionAddUser(HttpServletRequest request, HttpServletResponse response, 
            UtilisateurDao userDao) throws ServletException, IOException {
        String name = request.getParameter("name");
        Joueur joueur = new Joueur(name);
        //ancienne version
        //this.joueurs.add(joueur);
        JoueurDao joueurDao = new JoueurDao(ds);
        joueurDao.addJoueur(joueur);
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Utilisateur maitre = (Utilisateur) session.getAttribute(Connexion.ATT_SESSION_USER);
        List<Utilisateur> utilisateurs = userDao.getListeUtilisateurs(maitre.getNom()); 
        request.setAttribute("utilisateur", utilisateurs);
        this.getServletContext().getRequestDispatcher("/WEB-INF/ajouteJoueur.jsp").forward(request, response);
    }
       
}
