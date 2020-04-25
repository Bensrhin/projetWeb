/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Joueur;
import beans.Partie;
import beans.Proposed;
import dao.PartieDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import dao.ExercerPouvoirDao;
import dao.JoueurDao;
import dao.MessageDao;

/**
 *
 * @author benjelloun
 */
@WebServlet(name = "GestionPartie", urlPatterns = {"/GestionPartie"})
public class GestionPartie extends HttpServlet {
    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String VUE              = "/WEB-INF/jeuMaitre.jsp";
    public static final String VUE_FIN              = "/WEB-INF/fin.jsp";
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        PartieDao partiedao = new PartieDao(ds);
        Partie partie = new Partie();
        partiedao.partieEnCours(partie);
        List<Proposed> proposed = partiedao.getProposed();
        request.setAttribute("proposed", proposed);
        if(action.equals("passernuit")){
            partiedao.passerPeriode("Nuit", partie);
            request.setAttribute("periode", "Nuit");
            eliminerRactifier(request, response, partiedao, proposed);
        }
        if(action.equals("passeraujour")){
            partiedao.passerPeriode("Jour", partie);
            request.setAttribute("periode", "Jour");

            eliminerRactifier(request, response, partiedao, proposed);

            /** vider la table de exercer pouvoir **/
            ExercerPouvoirDao exercerPv = new ExercerPouvoirDao(ds);
            exercerPv.videTable();
           
        }
        request.setAttribute("maitrejeu", "1");
        JoueurDao joueurdao = new JoueurDao(ds);
        Boolean finpartie =  joueurdao.finPartie();
        if(finpartie){
            String gagant = joueurdao.gagnant();
            request.setAttribute("gagnant", gagant);
            request.setAttribute("joueurs", joueurdao.getListeJoueurs());
            request.setAttribute("messages", (new MessageDao(ds)).getListeMessages("archive"));
            this.getServletContext().getRequestDispatcher( VUE_FIN ).forward( request, response );
        }
        else{
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
    }

    private void eliminerRactifier(HttpServletRequest request, 
                         HttpServletResponse response, PartieDao partiedao,
                         List<Proposed> proposed) 
            throws ServletException, IOException {
            int max = 0;
            String eliminer = null;
            for (Proposed joueur:proposed){
                if (joueur.getNbVote() > max){
                    max = joueur.getNbVote();
                    eliminer = joueur.getPseudonyme();
                }
            }
            if (eliminer != null){
                partiedao.changeStatut(eliminer);
                partiedao.viderProposed();
                
            }
            
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
