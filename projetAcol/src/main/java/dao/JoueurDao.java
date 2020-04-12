/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import beans.Utilisateur;
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;
import beans.Joueur;

/**
 *
 * @author amalou
 */
public class JoueurDao extends AbstractDataBaseDAO{
    
    public JoueurDao(DataSource ds){
        super(ds);
    }
    
    private void addJoueur(int idPartie, Joueur joueur){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("insert into Joueur (pseudonyme,idPartie) values (?,?)");) {
            st.setString(1, joueur.getPseudonyme());
            st.setDouble(2, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  + "id Partie " + idPartie + e.getMessage(), e);
        }
    }
    public void addJoueurs(int idPartie, List<Joueur> joueurs){
        for (Joueur j: joueurs){
            j.setIdPartie(idPartie);
            this.addJoueur(idPartie,j);
        }
    }
}
