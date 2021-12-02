package packageDev1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class DevControle implements MouseListener, ActionListener, CaretListener{
    DevVue monAffichage;
    DevModele monModel;

    public DevControle(DevVue x, DevModele y)
    {
        monAffichage = x;
        monModel = y;
    }

    public void caretUpdate(CaretEvent e){

    }

    public void mouseClicked(MouseEvent e) {
        if ( SwingUtilities.isRightMouseButton(e) ){
            monModel.wordClicked(monAffichage.tArea.viewToModel( e.getPoint() ));
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
        if (e.getActionCommand() == "Importer les mots")
        {
            monModel.ouvrirFichier("importMots");
        }
        else if(e.getActionCommand() == "Importer un dictionnaire")
        {
            monModel.ouvrirFichier("importDictio");
        }

        else if(e.getActionCommand() == "Sauvegarder les corrections")
        {
            monModel.saveFile();
        }

        else if (e.getActionCommand() == "Corriger")
        {

            monModel.corrigerText(monAffichage.tArea.getText());
        }

        else 
        {
            monModel.changeWord(e.getActionCommand());
            System.out.print(e.getActionCommand());
        }
    }
}