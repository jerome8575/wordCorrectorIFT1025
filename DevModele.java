package packageDev1;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;
import java.awt.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DevModele extends JFrame{
    Dictionnaire dictioImport; //Dictionnary imported by user
    Dictionnaire motsImport; //Text imported by user
    DevVue monAffichage;
    String absolutePath;
    String word;
    String insideFile;

    ArrayList<MotIncorrect> motsIncorrects;

    public DevModele(DevVue test)
    {
        monAffichage = test;
        motsIncorrects = new ArrayList<MotIncorrect>();
    }


    // Surligne un mot incorrect
    public void redHightligher(ArrayList<MotIncorrect> motsIncorrects, String text)
    {
        try
        {
            int carretStart = 0;
            int carretEnd = 0;
            Highlighter.HighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
            for (int i = 0; i < motsIncorrects.size(); i++)
            {
                carretStart = motsImport.arrayTab.get(motsIncorrects.get(i).index);
                carretEnd = carretStart + motsImport.arToStr.get(motsIncorrects.get(i).index).length();
                monAffichage.tArea.getHighlighter().addHighlight(carretStart, carretEnd, redPainter);
            }
        }
        catch (Exception e)
        {
            System.out.print("Impossible de surligner");
        }

    }

    public void wordClicked(int offset)
    {
        try
        {
            System.out.println( monAffichage.tArea.modelToView( offset ) );
            int start = Utilities.getWordStart(monAffichage.tArea,offset);
            int end = Utilities.getWordEnd(monAffichage.tArea, offset);
            word = monAffichage.tArea.getDocument().getText(start, end-start);

            int rowStart = Utilities.getRowStart(monAffichage.tArea, offset);
            int rowEnd = Utilities.getRowEnd(monAffichage.tArea, offset);
            monAffichage.tArea.select(rowStart, rowEnd);

            recommander(word);

        }
        catch (Exception e2) {}
    }

    // remplace un mot par la recommendation selectione
    public void changeWord(String replacer)
    {
        motsImport.replaceWord(replacer, word);
        monAffichage.tArea.setText(motsImport.arrLiToStr());
        corrigerText(monAffichage.tArea.getText());
    }

    public void saveFile()
    {
        try{
            FileWriter filewriter = new FileWriter(absolutePath);
            BufferedWriter writer = new BufferedWriter(filewriter);
            for (int i = 0; i<motsImport.arToStr.size(); i++)
            {
                System.out.print(motsImport.arToStr.get(i));
                writer.write(motsImport.arToStr.get(i) + "\n");
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.print("Impossible de sauvegarder fichier");
        }

    }

    public void ouvrirFichier(String type)
    {
        try
        {
            JFileChooser filePicker = new JFileChooser();
            filePicker.showOpenDialog(null);
            absolutePath = filePicker.getSelectedFile().getAbsolutePath();
            String fileName = filePicker.getSelectedFile().getName();

            if (type.equals("importDictio"))
            {
                dictioImport = new Dictionnaire(absolutePath, fileName);
                dictioImport.toArrays();
                monAffichage.labelDictio.setText(dictioImport.nomFichier);
            }

            else if (type.equals("importMots"))
            {
                File ourFile = new File(absolutePath);
                FileReader ourFR = new FileReader(ourFile);
                BufferedReader ourInput = new BufferedReader(ourFR, 100);

                String sline = ourInput.readLine();
                while (sline != null)
                {
                    insideFile = insideFile + "\n" + sline;
                    sline = ourInput.readLine();
                }
                ourInput.close();
                monAffichage.tArea.setText(insideFile);
            }
        }

        catch(Exception e)
        {
            System.err.print("Type n'existe pas");
        }
    }


    // Utilise un objet dictionnaire pour voir si les mots sont incorrects
    // appel a la fonction redHiglighter pour surligner les mots incorrects
    public void corrigerText(String text){

        motsImport = new Dictionnaire(text);
        monAffichage.tArea.setText(motsImport.arrLiToStr());

        Parser parser = new Parser();
        ArrayList<String> parsedTextArr = parser.parse(text);

        motsIncorrects = dictioImport.checkIfIn(parsedTextArr);
        redHightligher(motsIncorrects, text);
    }

    public void recommander(String mot){
        for (MotIncorrect motIncorrect : motsIncorrects){
            if (motIncorrect.mot.equals(mot)){
                monAffichage.afficherMenuRecommandation(motIncorrect.recommendations);
            }
        }
    }


}