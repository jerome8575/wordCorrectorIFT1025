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

    //constructor for dictioImport
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

    //constructor for motsImport
    public Dictionnaire(String text)
    {
        String[] tempArr = text.split("\\W");
        for (int i = 0; i<tempArr.length; i++)
        {
            arToStr.add(tempArr[i]);
            hashtable.put(i, tempArr[i]);
        }
        carretInd();
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


    // Utilise un boucle pour verifier si un mot appartient au dictionnaire
    // Trouve les mots a distance 1 de mot incorrect
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
                else if (distance(mot, motDict) == 1 && motsProche.size() < 5){
                    motsProche.add(motDict);
                }
            }
            if (!motCorrect){
                if (motsProche.size() < 5){
                    findGreaterDistanceRecs(mot, motsProche, 2);
                }
                MotIncorrect motIncorrect = new MotIncorrect(i, mot, motsProche);
                motsIncorrects.add(motIncorrect);
            }
            
        }
        return motsIncorrects;


    }

    // Trouve les recommendations de mots de distance plus grande que 1
    // arrete des qu l'algorithme en trouve 5
    public void findGreaterDistanceRecs(String mot, ArrayList<String> recs, int currentDistance){

        Set<Integer> setOfKeys = hashtable.keySet();
        String motDict;

        for (int key : setOfKeys){
            motDict = hashtable.get(key);
            if (recs.size()== 5){
                return;
            }
            if (distance(mot, motDict) == currentDistance){
                recs.add(motDict);
            }
        }
        findGreaterDistanceRecs(mot, recs, currentDistance + 1);

    }



    public void replaceWord(String replacer, String replaced)
    {
        for (int i = 0; i<arToStr.size(); i++)
        {
            if (arToStr.get(i).equals(replaced))
            {
                arToStr.set(i, replacer);

            }
            else
            {

            }
        }
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
            System.err.print("Fichier n'existe pas");
        }

        catch  (IOException e)
        {
            System.err.print("Impossible d'ouvrir le fichier");
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
        String txInArea = "";
        for (int i = 0; i < arToStr.size(); i++)
        {
            txInArea = txInArea + arToStr.get(i) + "\n";
        }
        return txInArea;
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
