package packageDev1;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class DevModele extends JFrame{
    Dictionnaire dictioImport; //Dictionnary imported by user
    Dictionnaire motsImport; //Text imported by user
    DevAffichage monAffichage;

    ArrayList<MotIncorrect> motsIncorrects;

    public DevModele(DevAffichage test)
    {
        monAffichage = test;
        motsIncorrects = new ArrayList<MotIncorrect>();
    }

    //highlight particular word in textarea
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
            System.out.print("PLACEHOLDER4");
        }

    }

    //for the
    public void ouvrirFichier(String type)
    {
        try
        {
            JFileChooser filePicker = new JFileChooser();
            filePicker.showOpenDialog(null);
            String absolutePath = filePicker.getSelectedFile().getAbsolutePath();
            String fileName = filePicker.getSelectedFile().getName();

            if (type.equals("importDictio"))
            {
                dictioImport = new Dictionnaire(absolutePath, fileName);
                dictioImport.toArrays();
                monAffichage.labelDictio.setText(dictioImport.nomFichier);
            }

            else if (type.equals("importMots"))
            {
                motsImport = new Dictionnaire(absolutePath, fileName);
                motsImport.toArrays();
                monAffichage.tArea.setText(motsImport.arrLiToStr());
                monAffichage.labelMots.setText(motsImport.nomFichier);
                motsImport.carretInd();
                //ArrayList<MotIncorrect> motsIncorrects = dictioImport.checkIfIn(motsImport.arToStr);
                //redHightligher(motsIncorrects);
            }
            
        }

        catch(Exception e)
        {
            System.err.print("PLACEHOLDER1");
        }
    }

    public void corrigerText(String text){
        // corriger text
        System.out.println("Corriger text: " + text);
        Parser parser = new Parser();
        ArrayList<String> parsedTextArr = parser.parse(text);

        for (String mot : parsedTextArr){
            System.out.print(mot + " , ");
        }

        motsIncorrects = dictioImport.checkIfIn(parsedTextArr);
        redHightligher(motsIncorrects, text);
    }

    public void recommander(String mot){
        for (MotIncorrect motIncorrect : motsIncorrects){
            if (motIncorrect.mot.equals(mot)){
                monAffichage.afficherMenuRecommandation(motIncorrect.recommendations);
            }
        }
        // do nothing
    }


}
