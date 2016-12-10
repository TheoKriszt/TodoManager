package View;

import Controller.BilanController;
import Controller.MainFrameController;
import Model.Bilan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 07/12/2016.
 */
public class MainFrame extends JFrame implements Observer {

    private JMenuBar menuBar;
    private JMenu menuFichier, menuEdition;
    private JMenuItem menuItemQuitter, menuItemNewTask, menuItemNewCategory;
    private JTabbedPane tabbedPane;
    private JComponent tabGeneral, tabCategories, tabBilan, tabIntermediaire, tabTop8;


    public MainFrame(MainFrameController mfc){
        super("Todo List Manager");
        setLocationRelativeTo(null);
        setSize(new Dimension(750, 600));

        buildMenu();
        buildTabs();
        mfc.setListeners(this);

        setVisible(true);
    }

    /**
     * Créé les onglets qui donneront les différents modes d'affichage de l'application
     */
    private void buildTabs() {
        tabbedPane = new JTabbedPane();
        tabGeneral = new GeneralPanel ();
        tabCategories = new CategoriesPanel();
        tabIntermediaire = new IntermediatePanel();
        tabTop8 = new Top8Panel();

        Bilan bilan = new Bilan();
        BilanController bpc = new BilanController(bilan);
        tabBilan = new BilanPanel(bpc);
        bilan.setView((BilanPanel) tabBilan);

        tabbedPane.addTab("Général", tabGeneral);
        tabbedPane.addTab("Intermediaire", tabIntermediaire);
        tabbedPane.addTab("Intermediaire", tabIntermediaire);
        tabbedPane.addTab("Top 8", tabTop8);
        tabbedPane.addTab("Bilan", tabBilan);

        setContentPane(tabbedPane);
    }

    /**
     * Créé l'arborescence des menus (Quiter, Nouvelle tâche, Nouvelle Cétagorie ...)
     */
    private void buildMenu() {
        menuBar = new JMenuBar();

        menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F); //Touche d'accès rapide

        menuEdition = new JMenu("Edition");
        menuEdition.setMnemonic(KeyEvent.VK_E);

        menuBar.add(menuFichier);
        menuBar.add(menuEdition);

        menuItemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
        menuItemNewTask = new JMenuItem("Nouvelle tâche", KeyEvent.VK_T);
        menuItemNewCategory = new JMenuItem("Nouvelle catégorie", KeyEvent.VK_C);

        menuFichier.add(menuItemQuitter);
        menuEdition.add(menuItemNewTask);
        menuEdition.add(menuItemNewCategory);

        setJMenuBar(menuBar);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Sending main update");
        ObserverPanel activePanel = (ObserverPanel) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        activePanel.update(null, null);
    }

    public JMenuItem getMenuItemQuitter() {
        return menuItemQuitter;
    }

    public JMenuItem getMenuItemNewTask() {
        return menuItemNewTask;
    }

    public JMenuItem getMenuItemNewCategory() {
        return menuItemNewCategory;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
