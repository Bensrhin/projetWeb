/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Message;
import beans.Utilisateur;
import dao.DAOException;
import dao.MessageDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import static servlets.Connexion.VUE;

/**
 *
 * @author benjelloun
 */
@WebServlet(name = "Jeu", urlPatterns = {"/Jeu"})
public class Jeu extends HttpServlet {
    @Resource(name = "jdbc/bibliography")
    private DataSource ds;
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_MESSAGES     = "messages";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE              = "/WEB-INF/jeu.jsp";
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        MessageDao messageDao = new MessageDao(ds);
       if (action == null){
            List<Message> messages = messageDao.getListeMessages();
            System.err.println("messages = " + messages);
            request.setAttribute(ATT_MESSAGES, messages);
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
            }
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
                request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        MessageDao messageDao = new MessageDao(ds);
        if(action.equals("SendMess")){
           String pseudoName = ((Utilisateur)session.getAttribute(ATT_SESSION_USER)).getNom();
           String contenu = request.getParameter("contenu");
           Date date = new Date(System.currentTimeMillis());
           Message m = new Message(date, pseudoName, contenu);
           messageDao.addMessage(m);
           List<Message> messages = messageDao.getListeMessages();
           System.err.println("messages = " + messages);
           request.setAttribute(ATT_MESSAGES, messages);
           this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
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
