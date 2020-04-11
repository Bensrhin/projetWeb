/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

/**
 *
 * @author nadir
 */
import java.util.HashMap;
import java.util.Map;
import dao.UtilisateurDao;
import javax.servlet.http.HttpServletRequest;
import beans.Utilisateur;



public class PartieForm {
    
    private static final String CHAMP_PROBABILITE    = "probabilite";
    private static final String CHAMP_LOUPGAROU    = "loupgarou";
    
    private String resultat;
    private Map<String, String> erreurs  = new HashMap<String, String>();
   

    
    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    
    public Partie configurerPartie( HttpServletRequest request , UtilisateurDao userDao) {
        

        double probabilite = getValeurChampProbabilite( request, CHAMP_PROBABILITE );
        double loupgarou = getValeurChampLoupGarou( request, CHAMP_LOUPGAROU );

        Partie partie = new Partie();

        try {
            validationProbabilite( probabilite );
        } catch ( Exception e ) {
            setErreur( CHAMP_PROBABILITE, e.getMessage() );
        }
        partie.setProbabilite( probabilite );

        try {
            validationLoupGarou( loupgarou );
        } catch ( Exception e ) {
            setErreur( CHAMP_LOUPGAROU, e.getMessage() );
        }
        partie.setLoupGarou( loupgarou );

        

        if ( erreurs.isEmpty() ) {
            userDao.creerUtilisateur(nom, hashPass, email);
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }
        
        return partie;
    }
    
    private void validationProbabilite( double probabilite) throws Exception {
        if (probabilite > 1 || probabilite < 0 ){
             throw new Exception("La probabilité doit être entre 0 et 1");
        } 
    }

    private void validationLoupGarou( double loupgarou) throws Exception {
        if (loupgarou > 0.33 || loupgarou < 0 ){
             throw new Exception("La proportion de loup garou doit être entre 0 et 0.33");
        } 
    }
    

/*
 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
 */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

/*
 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
 * sinon.
 */
    private static double getValeurChampLoupGarou( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null ) {
            return 0.33;
        } else {
            return Double.valueOf(valeur);
        }
    }
    
        private static double getValeurChampProbabilite( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null ) {
            return 0;
        } else {
            return Double.valueOf(valeur);
        }
    }


}
