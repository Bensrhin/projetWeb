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
import beans.Pouvoir;
import beans.Role;
import beans.ExercerPouvoir;

/**
 *
 * @author amalou
 */
public class ExercerPouvoirDao extends AbstractDataBaseDAO{
    
    public ExercerPouvoirDao(DataSource ds){
        super(ds);
    }
    
    public List<Joueur> getHumains(){
        
        List<Joueur> humains = new ArrayList<Joueur>();
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("select * from Joueur where elimine = 0 and role like ?");) {
            st.setString(1, "humain");
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()){
                Joueur humain = new Joueur(resultSet.getString("pseudonyme"));
                humains.add(humain);
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
        
        return humains;
    }
    
    public List<Joueur> getJoeurs(String name){
        List<Joueur> joueurs = new ArrayList<Joueur>();
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("select * from Joueur where elimine = 0 and pseudonyme != ?");) {
            st.setString(1, name);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()){
                Joueur player = new Joueur(resultSet.getString("pseudonyme"));
                joueurs.add(player);
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
        
        return joueurs;
    }
    
    public void appliqueVoyance(ExercerPouvoir exercerPv){
        
        String par = exercerPv.getExercerPar();
        String sur = exercerPv.getExercerSur();
        
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("insert into ExercerPouvoir (exercerPar,exercerSur) values (?,?)");) {
            st.setString(1, par);
            st.setString(2, sur);
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    
    
    public void appliqueContamination(ExercerPouvoir exercerPv){
        
        String loupGarou = exercerPv.getExercerPar();
        String humain = exercerPv.getExercerSur();
        
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("update joueur set role = 'loupGarou' where pseudonyme like ?");) {
            st.setString(1, humain);
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
        
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("insert into ExercerPouvoir (exercerPar,exercerSur) values (?,?)");) {
            st.setString(1, loupGarou);
            st.setString(2, humain);
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    
    public void videTable(){
        
        try (
            
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("delete exercerPouvoir");) {
            st.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
        
    }
    
}
