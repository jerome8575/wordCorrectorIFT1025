package packageDev1;

import java.util.ArrayList;

// Objet motIncorrect contenant une liste de recommandation de mots

public class MotIncorrect {
    
    int index;
    String mot;
    ArrayList<String> recommendations;

    public MotIncorrect(int index, String mot, ArrayList<String> recommendations){
        this.index = index;
        this.mot = mot;
        this.recommendations = recommendations;
    }


}
