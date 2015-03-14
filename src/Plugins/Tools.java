package Plugins;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Tools extends JPanel {
    private JTabbedPane tabs;
    private JPanel ubcsatPage;
    private DefaultListModel<String> modèle;
    private JList<String> modèleList;
    private AbstractAction ajouterFormuleAction;
    private JPanel weightedmaxsatPage;
    private final String title = "Tools";
    private final int spacing = 5;
    public Tools(){
        super();
        setName(title); // Will be the title of the tab
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layout);
        tabs = new JTabbedPane();
        add(tabs);
        // Content of UBCSAT page
        ubcsatPage = new JPanel();
        ubcsatPage.setLayout(new BoxLayout(ubcsatPage, BoxLayout.PAGE_AXIS));
        modèle = new DefaultListModel<>();
        modèleList = new JList<>(modèle);
        TitledBorder modèleListBorder = BorderFactory.createTitledBorder("Le modèle");
        modèleList.setBorder(modèleListBorder);
        modèleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        final Component tools = this;
        ajouterFormuleAction = new AbstractAction("Ajouter une formule") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String formule = JOptionPane.showInputDialog(tools, "La formule (Utiliser '-' ou '!' pour le négation)", "Nouvelle formule", JOptionPane.QUESTION_MESSAGE);
                if ( formule != null && !formule.isEmpty())
                    if (formule.matches("(([!-]?\\w+)\\s+)*([!-]?\\w+)"))
                        modèle.addElement(formule.replaceAll("\\s+", " "));
                    else JOptionPane.showMessageDialog(tools, "La syntaxe de la formule est invalide !", "Formule invalide", JOptionPane.WARNING_MESSAGE);
            }
        };
        JButton ajouterFormuleButton = new JButton(ajouterFormuleAction);
        ubcsatPage.add(new JScrollPane(modèleList));
        Box buttonsBox = new Box(BoxLayout.LINE_AXIS);
        buttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing,0,0,0));
        buttonsBox.add(ajouterFormuleButton);
        buttonsBox.add(Box.createHorizontalGlue());
        ubcsatPage.add(buttonsBox);
        ubcsatPage.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        // Content of Weighted Max SAT
        weightedmaxsatPage = new JPanel();
        // Add the pages
        tabs.addTab("UBCSAT", ubcsatPage);
        tabs.addTab("Weighted Max SAT", weightedmaxsatPage);
    }
}
