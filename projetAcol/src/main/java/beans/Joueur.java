/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author amalou
 */
public class Joueur {
    
    private String pseudonyme;
    private int idPartie;
    
    public Joueur(String pseudonyme){
        this.pseudonyme = pseudonyme;
    }
    
    public void setIdPartie(int idPartie){
        this.idPartie = idPartie;
    }
    public String getPseudonyme(){
        return this.pseudonyme;
    }
    
    public int getIdPartie(){
        return this.idPartie;
    }
}
