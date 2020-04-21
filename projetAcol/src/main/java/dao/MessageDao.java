/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Message;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author benjelloun
 */
public class MessageDao extends AbstractDataBaseDAO{
        public MessageDao(DataSource ds){
        super(ds);
    }
    public void addMessage(Message m){
        try (
            Connection conn = getConn();  
            PreparedStatement st = conn.prepareStatement
            ("insert into Message (datePub, pseudonyme, contenu) values (?, ?, ?)");) {
            st.setString(1, m.getDate());
            st.setString(2, m.getNameUtilisateur());
            st.setString(3, m.getContenu());
            System.err.println("Name " +m.getNameUtilisateur());
            System.err.println("Name " +m.getContenu());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD "  +  e.getMessage(), e);
        }
    }
    public List<Message> getListeMessages(){
        List<Message> result = new ArrayList<>();
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM Message");
	     ) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getString("datePub"), rs.getString("pseudonyme"), rs.getString("contenu"));
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
}
