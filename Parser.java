package packageDev1;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public ArrayList<String> parse(String textToParse){

        String mots[] = textToParse.split(" |\\\n");
        System.out.println("This is the array mots : ");
        System.out.println(Arrays.toString(mots));
        String mot;
        String nouveauMot;
        ArrayList<String> parsedWords = new ArrayList<>();

        for (int i =0; i < mots.length; i++){
            mot = mots[i];
            nouveauMot = mot.replaceAll(",|\\.|\\ ", "");

            parsedWords.add(nouveauMot);
        }
        return parsedWords;


    }
    
}
