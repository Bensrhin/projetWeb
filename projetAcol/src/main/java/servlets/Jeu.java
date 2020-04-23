/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Message;
import beans.Partie;
import beans.Utilisateur;
import beans.Joueur;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import dao.DAOException;
import dao.MessageDao;
import dao.PartieDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
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
import dao.JoueurDao;
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
    public static final String ATT_MAITRE     = "maitrejeu";
    public static final String ATT_FORM         = "form";
    public static final String ATT_PERIODE      = "periode";
    public static final String ATT_JOEUR = "joueur";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE_JOUEUR              = "/WEB-INF/jeuJoueur.jsp";
    public static final String VUE_ARCHIVE              = "/WEB-INF/jeuArchive.jsp";
    public static final String VUE_MAITRE             = "/WEB-INF/jeuMaitre.jsp";
    public static final String ACCES_PUBLIC     = "/WEB-INF/connexion.jsp";
    public static final String ROLE     = "role";
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
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String maitre = request.getParameter(ATT_MAITRE);
        MessageDao messageDao = new MessageDao(ds);
        Partie partie = new Partie();
        PartieDao partiedao = new PartieDao(ds);
        partiedao.partieEnCours(partie);
        String pseudonyme = ((Utilisateur)session.getAttribute(ATT_SESSION_USER)).getNom(); 
        Joueur joueur = new Joueur(pseudonyme);
        JoueurDao joueurdao = new JoueurDao(ds);
        joueurdao.getInformations(joueur);
       if (action == null){
           
            if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
                    /* Redirection vers la page publique */
                    //response.sendRedirect( request.getContextPath() + ACCES_PUBLIC );
                     this.getServletContext().getRequestDispatcher( ACCES_PUBLIC ).forward( request, response );
            } else {
                List<Message> messages = messageDao.getListeMessages(partie.getPeriode());
                
                /** Chercher les informations sur le joueur **/
                
                request.setAttribute(ATT_MESSAGES, messages);
                request.setAttribute(ATT_PERIODE, partie.getPeriode());
                if(maitre != null){
                    request.setAttribute(ATT_MAITRE, "1");
                    this.getServletContext().getRequestDispatcher( VUE_MAITRE ).forward( request, response );
                }
                else{
                    request.setAttribute(ATT_JOEUR, joueur);
                    request.setAttribute(ATT_MAITRE, "0");
                    this.getServletContext().getRequestDispatcher( VUE_JOUEUR).forward( request, response );
                }
                
                }
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
        Partie partie = new Partie();
        PartieDao partiedao = new PartieDao(ds);
        partiedao.partieEnCours(partie);
        List<Message> messages;
        if(action.equals("SendMess")){
           String pseudoName = ((Utilisateur)session.getAttribute(ATT_SESSION_USER)).getNom();
           String contenu = request.getParameter("contenu");
           Date date = new Date();
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
           String dateString = sdf.format(date);
           Message m = new Message(dateString, pseudoName, contenu, partie.getPeriode());
           messageDao.addMessage(m, partie.getPeriode());
           messages = messageDao.getListeMessages(partie.getPeriode());
           request.setAttribute(ATT_MESSAGES, messages);
           response.sendRedirect("/projetAcol/Jeu");
       }
        if(action.equals("archive")){
            messages = messageDao.getListeMessages("archive");
            request.setAttribute(ATT_MESSAGES, messages);
            this.getServletContext().getRequestDispatcher( VUE_ARCHIVE).forward( request, response );
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
