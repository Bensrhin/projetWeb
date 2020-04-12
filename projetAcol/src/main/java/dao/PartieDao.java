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

public class PartieDao extends AbstractDataBaseDAO {

    public PartieDao(DataSource ds) { 
        super(ds);
    }

    public void creerPartie(String maitre, double probabilite, double loupgarou) {
        try (
            Connection conn = getConn();  PreparedStatement st = conn.prepareStatement("insert into Partie (maitre,probaPouvoir,propLoup) values (?,?,?)");) {
            st.setString(1, maitre);
            st.setDouble(2, probabilite);
            st.setDouble(3, loupgarou);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
