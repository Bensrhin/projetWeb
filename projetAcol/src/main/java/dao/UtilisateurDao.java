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
    
    public void verifyPseudonyme(String pseudonyme){
        
    }
    
}
