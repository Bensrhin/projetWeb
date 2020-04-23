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
    private String roleSt;
    private String pouvoirSt;
    
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
        if (role == Role.humain){
            this.roleSt = "humain";
        }else {
            this.roleSt = "loupGarou";
        }
    }
    
    public void setPouvoir(Pouvoir pouvoir){
        this.pouvoir = pouvoir;
        if (pouvoir == Pouvoir.aucun){
            this.pouvoirSt = "aucun";
        } else if (pouvoir == Pouvoir.contamination){
            this.pouvoirSt = "contamination";
        }else {
            this.pouvoirSt = "voyance";
        }
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
    
    public String getRoleSt(){
        return this.roleSt;
    }
    
    public String getPouvoirSt(){
        return this.pouvoirSt;
    }
}
