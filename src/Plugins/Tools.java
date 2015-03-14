package Plugins;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class Tools extends JPanel {
    private final JTabbedPane tabs;
    private final JPanel ubcsatPage;
    private final DefaultListModel<String> modèle;
    private final JList<String> modèleList;
    private final AbstractAction ajouterFormuleAction;
    private final AbstractAction supprimerFormuleAction;
    private final JPanel weightedmaxsatPage;
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
                if (formule != null && !formule.isEmpty())
                    if (formule.matches("(([!-]?\\w+)\\s+)*([!-]?\\w+)"))
                        modèle.addElement(formule.replaceAll("\\s+", " "));
                    else JOptionPane.showMessageDialog(tools, "La syntaxe de la formule est invalide !", "Formule invalide", JOptionPane.WARNING_MESSAGE);
            }
        };
        ajouterFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        final JButton ajouterFormuleButton = new JButton(ajouterFormuleAction);
        supprimerFormuleAction = new AbstractAction("Supprimer") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<String> selected = modèleList.getSelectedValuesList();
                for (String value : selected) {
                    modèle.removeElement(value);
                }
            }
        };
        supprimerFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        final JButton supprimerFormuleButton = new JButton(supprimerFormuleAction);
        supprimerFormuleButton.setEnabled(false);
        modèleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                supprimerFormuleButton.setEnabled(modèleList.getSelectedIndices().length > 0);
            }
        });
        ubcsatPage.add(new JScrollPane(modèleList));
        Box buttonsBox = new Box(BoxLayout.LINE_AXIS);
        buttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing,0,0,0));
        buttonsBox.add(ajouterFormuleButton);
        buttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
        buttonsBox.add(supprimerFormuleButton);
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
