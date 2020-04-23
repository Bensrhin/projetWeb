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
public class JoueurDao extends AbstractDataBaseDAO{
    
    public JoueurDao(DataSource ds){
        super(ds);
    }
    
    public void addJoueur(Joueur joueur){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("insert into Joueur (pseudonyme,elimine,role,pouvoir) values (?,?,?,?)");) {
            st.setString(1, joueur.getPseudonyme());
            st.setInt(2,0);
            if (joueur.getRole() == Role.humain){
                st.setString(3, "humain");
            } else {
                st.setString(3, "loupGarou");
            }
            if (joueur.getPouvoir() == Pouvoir.aucun){
                st.setString(4, "aucun");
            } else if (joueur.getPouvoir() == Pouvoir.voyance){
                st.setString(4, "voyance");
            } else {
                st.setString(4, "contamination");
            }
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
    
    public void getInformations(Joueur joueur){
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("select * from Joueur where pseudonyme like ?");) {
            st.setString(1, joueur.getPseudonyme());
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                // pouvoir
                if (resultSet.getString("pouvoir").equals("aucun")){
                    joueur.setPouvoir(Pouvoir.aucun);
                } else if (resultSet.getString("pouvoir").equals("voyance")){
                    joueur.setPouvoir(Pouvoir.voyance);
                } else{
                    joueur.setPouvoir(Pouvoir.contamination);
                }
                // elimine
                if (resultSet.getInt("elimine") == 0){
                    joueur.setElimine(false);
                } else {
                    joueur.setElimine(true);
                }
                // Role
                if (resultSet.getString("role").equals("humain")){
                    joueur.setRole(Role.humain);
                }else {
                    joueur.setRole(Role.loupGarou);
                }
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
}
