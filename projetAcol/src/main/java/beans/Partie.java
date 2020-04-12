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

    private int idPartie;
    private String maitre;

    private double probabilite;
    private double probaLoupGarou;

    public void setIdPartie(int id) {
        this.idPartie = idPartie;
    }

    public void setProba(double proba) {
        this.probabilite = probabilite;
    }

    public void setProbaLoupGarou(double loupGarou) {
        this.probaLoupGarou = probaLoupGarou;
    }

    public int getIdPartie() {
        return this.idPartie;
    }

    public double getProba() {
        return this.probaLoupGarou;
    }

    public double getProbaLoupGarou() {
        return this.probaLoupGarou;
    }

    public String getMaitre() {
        return maitre;
    }

    public void setMaitre(String maitre) {
        this.maitre = maitre;
    }
}
