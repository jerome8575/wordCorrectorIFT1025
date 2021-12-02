package packageDev1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.ArrayList;

public class DevVue extends JFrame{

    DevModele mainModel;
    DevControle mainControl;

    JPanel mainPanel = new JPanel();
    JTextArea tArea = new JTextArea();
    JButton corrButt = new JButton("Corriger");
    JLabel labelDictio = new JLabel("Dictionnaire");
    JLabel labelMots = new JLabel("Fichier Mots");


    public void initiateInterface()
    {
        this.setSize(700, 700);

        JMenu menuF = new JMenu("Mots");
        JMenuItem menuFimpo = new JMenuItem("Importer les mots");
        menuFimpo.addActionListener(mainControl);
        JMenuItem menuFsave  = new JMenuItem("Sauvegarder les corrections");
        menuFsave.addActionListener(mainControl);
        menuF.add(menuFimpo);
        menuF.add(menuFsave);

        JMenu menuD = new JMenu("Dictionnaire");
        JMenuItem menuDimp = new JMenuItem("Importer un dictionnaire");
        menuDimp.addActionListener(mainControl);
        menuD.add(menuDimp);


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuF);
        menuBar.add(menuD);

        this.setJMenuBar(menuBar);

        // creation du textArea 
        tArea.setRows(20);
        tArea.setColumns(15);
        tArea.addMouseListener(mainControl);
        tArea.addCaretListener(mainControl);

        tArea.addKeyListener( new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    System.out.println( tArea.getDocument().getDefaultRootElement().getElementCount() );
                }
            });

        corrButt.addActionListener(mainControl);


        mainPanel.setLayout(new GridLayout(2,0));
        mainPanel.setBackground(Color.black);

        mainPanel.add(tArea);
        mainPanel.add(corrButt);
        this.add(mainPanel);
        this.setVisible(true);
    }


    // Creer un JDialog pour afficher les recommandations de mots

    public void afficherMenuRecommandation(ArrayList<String> rec){
        JDialog menuRec = new JDialog();
        menuRec.setSize(300, 200);
        JButton[] buttonRec = new JButton[rec.size()];
        JPanel panel = (JPanel)menuRec.getContentPane();
        panel.setLayout(new FlowLayout());
        for (int i=0; i < buttonRec.length; i++){
            buttonRec[i] = new JButton(rec.get(i));

            buttonRec[i].addActionListener(mainControl);
            buttonRec[i].setSize(10,10);
            buttonRec[i].setVisible(true);
            panel.add(buttonRec[i]);
        }
        menuRec.setVisible(true);
    }


    public static void main(String[] args)
    {

        DevVue testAffi = new DevVue();
        testAffi.mainModel = new DevModele(testAffi);
        testAffi.mainControl = new DevControle(testAffi, testAffi.mainModel);
        testAffi.initiateInterface();
    }


}
