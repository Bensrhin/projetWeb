/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Partie;
import dao.PartieDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import dao.ExercerPouvoirDao;

/**
 *
 * @author benjelloun
 */
@WebServlet(name = "GestionPartie", urlPatterns = {"/GestionPartie"})
public class GestionPartie extends HttpServlet {
    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String VUE              = "/WEB-INF/jeuMaitre.jsp";
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
        if(action.equals("passernuit")){
            partiedao.passerPeriode("Nuit", partie);
            request.setAttribute("periode", "Nuit");
        }
        if(action.equals("passeraujour")){
            partiedao.passerPeriode("Jour", partie);
            request.setAttribute("periode", "Jour");
            /** vider la table de exercer pouvoir **/
            ExercerPouvoirDao exercerPv = new ExercerPouvoirDao(ds);
            exercerPv.videTable();
            
        }
        request.setAttribute("maitrejeu", "1");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
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
