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
import beans.Joueur;
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
import beans.Pouvoir;
import beans.Proposed;
import beans.Role;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public int nbJoueurs(){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("select count(*) from joueur where elimine = 0");) {
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                 return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    public void proposerVillageois(String pseudo, String voter){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("insert into Proposed (pseudonyme,voter) values (?,?)");) {
            st.setString(1, pseudo);
            st.setString(2, voter);   
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    public void retirerVote(String pseudo, String voter){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("delete from Proposed where pseudonyme=? and voter=?");) {
            st.setString(1, pseudo);
            st.setString(2, voter);   
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    public List<Proposed> getProposed(){
        List<Proposed> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM PROPOSED");
	     ) {
            Map<String, List<String>> map = new HashMap<>();
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                List<String> list = map.get(rs.getString("pseudonyme"));
                if (list != null){
                    list.add(rs.getString("voter"));
                }
                else{
                    list = new ArrayList<>();
                    list.add(rs.getString("voter"));
                }
                map.put(rs.getString("pseudonyme"), list);
            }
            Set<String> keys = map.keySet();
            for (String pseudo:keys){
                Proposed proposed = new Proposed(pseudo);
                proposed.addVote(map.get(pseudo));
                result.add(proposed);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    public boolean partieEnCours(Partie partie){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("select * from Partie");) {
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()){
                 partie.setMaitre(resultSet.getString("maitre"));
                 partie.setPeriode(resultSet.getString("periode"));
                 partie.setNbJoueurs(nbJoueurs());
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    public void viderProposed(){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("delete from Proposed");) {   
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    public void changeStatut(String removed){
        try (
            /* changer le statut de l'utilisateur*/
            Connection conn = getConn();
            PreparedStatement st1 = conn.prepareStatement("Update joueur set elimine=1 where pseudonyme=?");){ 
            st1.setString(1, removed);
            st1.executeUpdate();
            /* vider la table du joueur tué*/
            PreparedStatement st2 = conn.prepareStatement
            ("delete from Proposed");  
            st2.executeUpdate();
            /* ajouter le nouveau mort*/
            PreparedStatement st3 = conn.prepareStatement("insert into Removed (pseudonyme) values (?)");
            st3.setString(1, removed);
            st3.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD "  +  ex.getMessage(), ex);
        }

    }
    public Joueur nouveauMort(){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement("select * from removed");) {
            ResultSet rt = st.executeQuery();
            Joueur joueur = null;
            if (rt.next()){
                joueur = new Joueur(rt.getString("pseudonyme"));
                PreparedStatement st1 = conn.prepareStatement("select * from Joueur where pseudonyme=?");
                st1.setString(1, joueur.getPseudonyme());
                ResultSet resultSet = st1.executeQuery();
                if (resultSet.next()){
                    if (resultSet.getString("pouvoir").equals("aucun")){
                        joueur.setPouvoir(Pouvoir.aucun);
                    } else if (resultSet.getString("pouvoir").equals("voyance")){
                        joueur.setPouvoir(Pouvoir.voyance);
                    } else{
                        joueur.setPouvoir(Pouvoir.contamination);
                    }

                    if (resultSet.getInt("elimine") == 0){
                        joueur.setElimine(false);
                    } else {
                        joueur.setElimine(true);
                    }

                    if (resultSet.getString("role").equals("humain")){
                        joueur.setRole(Role.humain);
                    }else {
                        joueur.setRole(Role.loupGarou);
                    }
                }
            }
            
            return joueur;
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD "  +  ex.getMessage(), ex);
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
            PreparedStatement st2 = conn.prepareStatement("insert into Archive (datePub,pseudonyme,contenu, periode) values (?,?,?, ?)");) {
            st2.setString(1, resultSet.getString("datePub"));
            st2.setString(2, resultSet.getString("pseudonyme"));
            st2.setString(3, resultSet.getString("contenu"));
            st2.setString(4, complem);
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
