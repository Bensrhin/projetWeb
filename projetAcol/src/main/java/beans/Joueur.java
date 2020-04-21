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
    private boolean elimine;
    private Role role;
    private Pouvoir pouvoir;
    
    public Joueur(String pseudonyme){
        this.pseudonyme = pseudonyme;
    }
    

    public String getPseudonyme(){
        return this.pseudonyme;
    }
    
    public void setElimine(boolean bool){
        this.elimine = bool;
    }
    
    public void setRole(Role role){
        this.role = role;
    }
    
    public void setPouvoir(Pouvoir pouvoir){
        this.pouvoir = pouvoir;
    }
    
    public Role getRole(){
        return this.role;
    }
    
    public Pouvoir getPouvoir(){
        return this.pouvoir;
    }
    
    public boolean getElimine(){
        return this.elimine;
    }
}
