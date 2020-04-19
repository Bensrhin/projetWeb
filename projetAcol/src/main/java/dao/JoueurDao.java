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
    
    public void addJoueur(Joueur joueur){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("insert into Joueur (pseudonyme) values (?)");) {
            st.setString(1, joueur.getPseudonyme());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    public void addJoueurs(List<Joueur> joueurs){
        for (Joueur j: joueurs){
            this.addJoueur(j);
        }
    }
}
