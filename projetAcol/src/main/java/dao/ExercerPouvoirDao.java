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
    
}