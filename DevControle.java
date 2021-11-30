package packageDev1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;

public class DevControle implements MouseListener, ActionListener, CaretListener{
    DevAffichage monAffichage;
    DevModele monModel;

    public DevControle(DevAffichage x, DevModele y)
    {
        monAffichage = x;
        monModel = y;
    }

    public void caretUpdate(CaretEvent e)
        {
            int caretPosition = monAffichage.tArea.getCaretPosition();
            Element root = monAffichage.tArea.getDocument().getDefaultRootElement();
            int row = root.getElementIndex( caretPosition );
            int column = caretPosition - root.getElement( row ).getStartOffset();
            System.out.println( "Row   : " + ( row + 1 ) );
            System.out.println( "Column: " + ( column + 1 ) );
        }

    public void mouseClicked(MouseEvent e) {
        if ( SwingUtilities.isRightMouseButton(e) ){
            try
                {
                    int offset = monAffichage.tArea.viewToModel( e.getPoint() );
                    System.out.println( monAffichage.tArea.modelToView( offset ) );
                    int start = Utilities.getWordStart(monAffichage.tArea,offset);
                    int end = Utilities.getWordEnd(monAffichage.tArea, offset);
                    String word = monAffichage.tArea.getDocument().getText(start, end-start);
                    System.out.println( "Selected word: " + word);
                    int rowStart = Utilities.getRowStart(monAffichage.tArea, offset);
                    int rowEnd = Utilities.getRowEnd(monAffichage.tArea, offset);
                    System.out.println( "Row start offset: " + rowStart );
                    System.out.println( "Row end   offset: " + rowEnd );
                    monAffichage.tArea.select(rowStart, rowEnd);

                    // recommander des mots

                    monModel.recommander(word);

                }
            catch (Exception e2) {}
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Importer Mots")
        {
            monModel.ouvrirFichier("importMots");
            //monModel.dictioImport.checkIfIn(monModel.motsImport.arToStr);
        }
        else if(e.getActionCommand() == "Importer Dictionnaire")
        {
            monModel.ouvrirFichier("importDictio");
        }
        else if (e.getActionCommand() == "Corriger"){
            monModel.corrigerText(monAffichage.tArea.getText());
        }
        else if(e.getActionCommand() == "Importer Fichier Mots")
        {
            System.out.print("no");
        }
    }
}
