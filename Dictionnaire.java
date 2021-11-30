package packageDev1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Dictionnaire
{
    //declare references
    Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();
    File ourFile;
    FileReader ourFR;
    BufferedReader ourInput;
    ArrayList<String> arToStr = new ArrayList<String>();
    ArrayList<Integer> arrayTab = new ArrayList<Integer>();
    String nomFichier;

    //Create a constructor for the class
    public Dictionnaire(String filePath, String fileName)
    {
        nomFichier = fileName;
        try
        {
            ourFile = new File(filePath);
            ourFR = new FileReader(ourFile);
            ourInput = new BufferedReader(ourFR, 100);
        }
        catch (FileNotFoundException e)
        {
            System.out.print("Votre fichier n'existe pas.");
        }
    }

    //creates an int array that stores the carret
    //position of the first letter of the word
    //in the textarea
    public void carretInd()
    {
        try
        {
            int compteur = 0;
            arrayTab.add(0);
            for (int i = 0; i < arToStr.size()-1; i++)
            {
                //+1 because carret takes word length + 1 possibles places
                compteur = compteur + arToStr.get(i).toString().length() + 1;
                arrayTab.add(compteur);
            }
            System.out.print(arrayTab.toString());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.print("PLACEHOLDER2");
        }
    }

    public ArrayList<MotIncorrect> checkIfIn(ArrayList<String> textInArrForm){

        ArrayList<MotIncorrect> motsIncorrects = new ArrayList<>();
        Set<Integer> setOfKeys = hashtable.keySet();

        String motDict;
        String mot;
        boolean motCorrect;

        for (int i = 0; i < textInArrForm.size(); i++){
            mot = textInArrForm.get(i);
            motCorrect = false;
            ArrayList<String> motsProche = new ArrayList<>();

            for (Integer key : setOfKeys){
                motDict = hashtable.get(key);
                if (mot.toLowerCase().equals(motDict)){
                    System.out.println(mot + " Est correct!");
                    motCorrect = true;
                    break;
                }
                else if (distance(mot, motDict) == 1){
                    motsProche.add(motDict);
                }
            }
            if (!motCorrect){
                MotIncorrect motIncorrect = new MotIncorrect(i, mot, motsProche);
                motsIncorrects.add(motIncorrect);
            }
            
        }

        /*for (MotIncorrect m : motsIncorrects){
            System.out.println(m.mot);
        }*/
        return motsIncorrects;


    }

    //converts the file contents
    //to an arraylist and a hashtable
    public void toArrays()
    {
        String word;
        try
        {
            word = ourInput.readLine();
            Integer key = 0;
            while (word != null)
            {
                arToStr.add(word);
                hashtable.put(key, word);
                word = ourInput.readLine();
                key++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.print("PLACEHOLDER");
        }

        catch  (IOException e)
        {
            System.err.print("PLACEHOLDER");
        }
    }

    public void printHash()
    {
        System.out.println(hashtable);
    }


    public Hashtable returnHash()
    {
        return hashtable;
    }

    //convert to string for the textarea
    public String arrLiToStr()
    {
        String bruh = "";
        for (int i = 0; i < arToStr.size(); i++)
        {
            bruh = bruh + arToStr.get(i) + "\n";
        }
        return bruh;
    }

    public int distance(String s1, String s2){
        int edits[][]=new int[s1.length()+1][s2.length()+1];
        for(int i=0;i<=s1.length();i++)
            edits[i][0]=i;
        for(int j=1;j<=s2.length();j++)
            edits[0][j]=j;
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                int u=(s1.charAt(i-1)==s2.charAt(j-1)?0:1);
                edits[i][j]=Math.min(
                                edits[i-1][j]+1,
                                Math.min(
                                   edits[i][j-1]+1,
                                   edits[i-1][j-1]+u
                                )
                            );
            }
        }
        return edits[s1.length()][s2.length()];
   }

}
