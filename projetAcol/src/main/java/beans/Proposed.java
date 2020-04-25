/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nabil
 */
public class Proposed {
    private final String pseudonyme;
    private List<String> quiVotent;
    
    public Proposed(String pseudonyme){
        this.pseudonyme = pseudonyme;
    }
    public int getNbVote(){
        return quiVotent.size();
    }
    public void addVote(List<String> vote){
        quiVotent = vote;
    }
    public List<String> getVote(){
        return quiVotent;
    }
    public String getPseudonyme(){
        return this.pseudonyme;
    }
    
    
}
