/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author nadir
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import beans.Utilisateur;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import beans.Partie;

public class PartieDao extends AbstractDataBaseDAO {

    public PartieDao(DataSource ds) { 
        super(ds);
    }

    public void creerPartie(String maitre, double probabilite, double loupgarou) {
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("insert into Partie (maitre,probaPouvoir,propLoup,periode) values (?,?,?,?)");) {
            st.setString(1, maitre);
            st.setDouble(2, probabilite);
            st.setDouble(3, loupgarou);
            st.setString(4, "Jour");
            st.executeUpdate();
            /** retourne la valeur de IdPartie **/
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        
    }
    
    public boolean partieEnCours(Partie partie){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("select * from Partie");) {
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                 partie.setMaitre(resultSet.getString("maitre"));
                 partie.setPeriode(resultSet.getString("periode"));
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    public void passerPeriode(String periode, Partie partie){
        /*modifier la periode*/
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("UPDATE Partie SET periode  = ? WHERE Maitre = ?");) {
            st.setString(1, periode);
            st.setString(2, partie.getMaitre());
            st.executeUpdate();  
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
       String complem;
        if(periode.equals("Nuit")){
            complem = "Jour";
        }
        else{
            complem = "Nuit";
        }
        /*ajouter les messages dans l'archive*/
               try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("select * from Message"+complem);) {
            ResultSet resultSet = st.executeQuery();
            while(resultSet.next()){
                 try (
            PreparedStatement st2 = conn.prepareStatement("insert into Archive (datePub,pseudonyme,contenu) values (?,?,?)");) {
            st2.setString(1, resultSet.getString("datePub"));
            st2.setString(2, resultSet.getString("pseudonyme"));
            st2.setString(3, resultSet.getString("contenu"));
            st2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        /* enlever les messages de la table messageComplem*/
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("delete from Message" + complem);) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
