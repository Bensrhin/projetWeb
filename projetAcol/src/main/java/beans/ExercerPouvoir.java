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
public class ExercerPouvoir {
    
    private String exercerPar;
    private String exercerSur;
    
    public void setExercerPar(String name){
        this.exercerPar = name;
    }
    
    public void setExercerSur(String name){
        this.exercerSur = name;
    }
    
    public String getExercerPar(){
        return this.exercerPar;
    }
    
    public String getExercerSur(){
        return this.exercerSur;
    }
}
