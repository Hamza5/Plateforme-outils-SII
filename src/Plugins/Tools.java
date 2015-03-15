package Plugins;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Tools extends JPanel {
    private final JTabbedPane tabs;
    private final JPanel ubcsatPage;
    private final DefaultListModel<String> modèle;
    private final JList<String> modèleList;
    private final AbstractAction ajouterFormuleAction;
    private final AbstractAction supprimerFormuleAction;
    private final AbstractAction calculerAction;
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
                String formule = JOptionPane.showInputDialog(tools, "La formule (Utiliser '-' pour le négation)", "Nouvelle formule", JOptionPane.QUESTION_MESSAGE);
                if (formule != null && !formule.isEmpty())
                    if (formule.matches("(([-]?\\w+)\\s+)*([-]?\\w+)"))
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
        supprimerFormuleAction.setEnabled(false);
        modèleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                supprimerFormuleAction.setEnabled(modèleList.getSelectedIndices().length > 0);
            }
        });
        calculerAction = new AbstractAction("Calculer") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    File tempFile = File.createTempFile("UBCSAT", ".txt");
                    PrintWriter writer = new PrintWriter(tempFile);
                    HashMap<String, Integer> clauses = new HashMap<>();
                    int k = 1;
                    String content = "";
                    for (int i=0; i<modèle.size(); i++) {
                        for (String c : modèle.get(i).split("\\s")) {
                            String c_ = c.replace("-", "");
                            if (!clauses.keySet().contains(c_))
                                clauses.put(c_, k++);
                            content += (c.charAt(0) == '-' ? "-" : "") + clauses.get(c_)+"\t";
                        }
                        content += "0\n";
                    }
                    content = "p\tcnf\t"+clauses.size()+'\t'+modèle.size()+'\n' + content;
                    Runtime runtime = Runtime.getRuntime();
                    writer.print(content);
                    writer.close();
                    URL pluginsURL = ClassLoader.getSystemClassLoader().getResource("Plugins");
                    if (pluginsURL == null) throw new FileNotFoundException("Plugins package not found");
                    String pluginsPath = pluginsURL.getPath();
                    String[] cmd = {Paths.get(pluginsPath, "ubcsat").toString(), "-alg", "saps", "-i",
                                    tempFile.getAbsolutePath(), "-solve", "-r", "out", "null", "-r", "stats", "null"};
                    Process process = runtime.exec(cmd, null, new File(pluginsPath));
                    InputStream output = process.getInputStream();
                    InputStream errorsOutput = process.getErrorStream();
                    int c;
                    String results = "";
                    while ((c = output.read()) != -1) {
                        results += (char)c;
                    }
                    String errors = "";
                    while ((c = errorsOutput.read()) != -1) {
                        errors += (char)c;
                    }
                    if (!errors.isEmpty()) JOptionPane.showMessageDialog(tools, errors, "Erreur", JOptionPane.ERROR_MESSAGE);
                    else JOptionPane.showMessageDialog(tools, results, "Résultats", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(tools, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        calculerAction.setEnabled(false);
        calculerAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        final JButton calculerButton = new JButton(calculerAction);
        modèle.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent listDataEvent) {
                calculerAction.setEnabled(true);
            }

            @Override
            public void intervalRemoved(ListDataEvent listDataEvent) {
                calculerAction.setEnabled(!modèle.isEmpty());
            }

            @Override
            public void contentsChanged(ListDataEvent listDataEvent) {

            }
        });
        ubcsatPage.add(new JScrollPane(modèleList));
        Box buttonsBox = new Box(BoxLayout.LINE_AXIS);
        buttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing,0,0,0));
        buttonsBox.add(ajouterFormuleButton);
        buttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
        buttonsBox.add(supprimerFormuleButton);
        buttonsBox.add(Box.createHorizontalGlue());
        buttonsBox.add(calculerButton);
        ubcsatPage.add(buttonsBox);
        ubcsatPage.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        // Content of Weighted Max SAT
        weightedmaxsatPage = new JPanel();
        // Add the pages
        tabs.addTab("UBCSAT", ubcsatPage);
        tabs.addTab("Weighted Max SAT", weightedmaxsatPage);
    }
}
