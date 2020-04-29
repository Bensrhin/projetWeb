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
    
    
    
    public List<Joueur> getListeJoueursVivants(Joueur joueur){
        List<Joueur> result = new ArrayList<>();
        try (
            
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
             /** selon la période **/
                ("SELECT * FROM JOUEUR WHERE elimine=0 and pseudonyme not in (select pseudonyme from PROPOSED)");
	     ) {

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Joueur jou = new Joueur(rs.getString("pseudonyme"));
                result.add(jou);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    public List<Joueur> getListeJoueurs(){
        List<Joueur> result = new ArrayList<>();
        try (
            
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM JOUEUR");
	     ) {

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Joueur jou = new Joueur(rs.getString("pseudonyme"));
                
                String r = rs.getString("role");
                Role role;
                if(r.equals("humain")){
                    role = Role.humain; 
                }
                else{
                    role = Role.loupGarou;
                }
                jou.setRole(role);
                String p = rs.getString("pouvoir");
                Pouvoir pouvoir;
                if(p.equals("aucun")){
                    pouvoir = Pouvoir.aucun; 
                }
                else if(p.equals("voyance")){
                    pouvoir = Pouvoir.voyance;
                }
                else{
                    pouvoir = Pouvoir.contamination;
                }
                jou.setRole(role);
                jou.setPouvoir(pouvoir);
                if(rs.getInt("elimine") == 1){
                    jou.setElimine(true);
                }
                else{
                    jou.setElimine(false);
                }
                result.add(jou);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
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
    
    public Joueur checkExercerPv(Joueur exercerPar){
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("select exercerSur from ExercerPouvoir where exercerPar like ?");) {
            st.setString(1, exercerPar.getPseudonyme());
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                // pouvoir
                Joueur exercerSur = new Joueur(resultSet.getString("exercerSur"));
                return exercerSur;
                }
            return null;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }

    public Boolean finPartie() {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM JOUEUR WHERE elimine=0");
	     ) {
            ResultSet rs = st.executeQuery();
            String role = "humain";
            if(rs.next()){
               role = rs.getString("role");
            }
            while (rs.next()) {
               if(!rs.getString("role").equals(role)){
                   return false;
               }
            }
            return true;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }

  public String gagnant(){
        assert(this.finPartie());
        try (
	    Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
            ("SELECT * FROM JOUEUR WHERE elimine=0");
	    ) {
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getString("role");
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	} 
}

    public void deleteJoueurs() {
            /* supprimer Joueurs*/
    try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("delete from Joueur");) {
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
  
}