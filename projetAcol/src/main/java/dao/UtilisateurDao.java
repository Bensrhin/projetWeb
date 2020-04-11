/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author amalou
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


public class UtilisateurDao extends AbstractDataBaseDAO{
    
    public UtilisateurDao(DataSource ds){
        super(ds);
    }
    
    public void creerUtilisateur(String pseudonyme,String password, String email){
        try(
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement
                ("insert into Utilisateur (pseudonyme,password,email) values (?,?,?)");
            ){
            st.setString(1,pseudonyme);
            st.setString(2,password);
            st.setString(3,email);
            st.executeUpdate();
        } catch (SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Chercher dans la base de donnée si il y a un utilisateur avec 
     * le même pseudonyme
     * @param pseudonyme 
     */
    public boolean verifyPseudonyme(String pseudonyme){
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement
                ("select idUser,pseudonyme from Utilisateur where pseudonyme=? ");
            ){
            st.setString(1,pseudonyme);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public boolean verifyUniqueEmail(String email){
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement
                ("select idUser,email from Utilisateur where email=? ");
            ){
            st.setString(1,email);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
    public boolean connexion(Utilisateur user){
        String pseudonyme = user.getNom();
        String motDePasse = user.getMotDePasse();
        
        
        try (
                Connection conn = getConn();
                PreparedStatement st = conn.prepareStatement
                ("select idUser,pseudonyme,password from Utilisateur where pseudonyme=? ");
            ){
                st.setString(1,pseudonyme);
                ResultSet resultSet = st.executeQuery();
                if (resultSet.next()){
                    String password = resultSet.getString(3);
                    if (!password.equals(motDePasse)){
                        return false;
                    } else {
                        user.setIdUser(resultSet.getInt(1));
                        return true;
                    }
                } else {
                    return false;
                }
        } catch (SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public String hashPassword(String password){
        String generatedPass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPass = sb.toString();
         } catch (NoSuchAlgorithmException e) 
            {
                e.printStackTrace();
            }
        return generatedPass;
    }
    
}
