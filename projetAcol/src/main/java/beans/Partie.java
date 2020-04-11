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
public class Partie {
    
    private long idPartie;
    private double probabilite;
    private double probaLoupGarou;

    public void setIdPartie(long id){
        this.idPartie = idPartie;
    }
    public void setProba(double proba) {
	this.probabilite = probabilite;
    }
    public void setProbaLoupGarou(double loupGarou) {
	this.probaLoupGarou = probaLoupGarou;
    }

    public long getIdPartie(){
        return this.idPartie;
    }
    public double getProba(){
        return this.probaLoupGarou;
    }
   
    public double getProbaLoupGarou(){
        return this.probaLoupGarou;
    }
}
