/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


/**
 *
 * @author benjelloun
 */
public class Message {

    public Message(String date, String nameUtilisateur, String contenu, String periode) {
        this.date = date;
        this.nameUtilisateur = nameUtilisateur;
        this.contenu = contenu;
        this.periode = periode;
    }
    String date;
    String nameUtilisateur;
    String contenu;
    String periode;

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameUtilisateur() {
        return nameUtilisateur;
    }

    public void setNameUtilisateur(String nameUtilisateur) {
        this.nameUtilisateur = nameUtilisateur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
