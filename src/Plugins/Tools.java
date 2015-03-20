package Plugins;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

public class Tools extends JPanel {
    private final JTabbedPane tabs;
    private final JPanel ubcsatPage;
    private final DefaultListModel<String> modèle;
    private final JList<String> modèleList;
    private final AbstractAction ajouterFormuleAction;
    private final AbstractAction supprimerFormuleAction;
    private final JList<String> wmodèleList;
    private final AbstractAction wajouterFormuleAction;
    private final AbstractAction wsupprimerFormuleAction;
    private final DefaultListModel<String> wmodèle;
    private final AbstractAction calculerAction;
    private final JPanel weightedmaxsatPage;
    private final String title = "Tools";
    private final String dialogTitle = "Nouvelle clasue";
    private final String dialogText = "La clause (Utiliser '-' pour la négation)";
    private final int spacing = 5;
    class WeightedMaxSATDialog extends JDialog {
        private final JLabel clauseLabel;
        private final JTextField clauseField;
        private final JLabel poidsLabel;
        private final JTextField poidsField;
        private final JButton cancelButton;
        private final JButton okButton;
        WeightedMaxSATDialog() {
            super();
            setModal(true);
            BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
            getContentPane().setLayout(layout);
            setTitle(dialogTitle);
            clauseLabel = new JLabel(dialogText);
            clauseField = new JTextField();
            poidsLabel = new JLabel("Poids");
            poidsField = new JTextField(3);
            final Color poidsFieldBackground = poidsField.getBackground();
            final Color poidsFieldForeground = poidsField.getForeground();
            poidsField.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent jComponent) {
                    String text = ((JTextField) jComponent).getText();
                    try {
                        Float.parseFloat(text);
                        jComponent.setBackground(poidsFieldBackground);
                        jComponent.setForeground(poidsFieldForeground);
                        return true;
                    } catch (NumberFormatException e) {
                        jComponent.setBackground(Color.RED);
                        jComponent.setForeground(Color.WHITE);
                        return false;
                    }
                }
            });
            final WeightedMaxSATDialog dialog = this;
            AbstractAction cancelAction = new AbstractAction("Annuler") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dialog.setVisible(false);
                }
            };
            cancelButton = new JButton(cancelAction);
            getRootPane().getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
            getRootPane().getActionMap().put("cancel", cancelAction);
            AbstractAction okAction = new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dialog.setVisible(false);
                }
            };
            okButton = new JButton(okAction);
            Box clauseBox = Box.createHorizontalBox();
            clauseBox.add(clauseLabel);
            Box fieldsBox =  Box.createHorizontalBox();
            fieldsBox.add(clauseField);
            fieldsBox.add(Box.createRigidArea(new Dimension(2*spacing, 0)));
            fieldsBox.add(poidsLabel);
            fieldsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
            fieldsBox.add(poidsField);
            poidsField.setMaximumSize(poidsField.getPreferredSize());
            Box buttonsBox = Box.createHorizontalBox();
            buttonsBox.add(okButton);
            buttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
            buttonsBox.add(cancelButton);
            clauseBox.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
            fieldsBox.setBorder(new EmptyBorder(0, spacing, spacing, spacing));
            buttonsBox.setBorder(fieldsBox.getBorder());
            add(clauseBox);
            add(fieldsBox);
            add(buttonsBox);
            pack();
            getRootPane().setDefaultButton(okButton);
            setResizable(false);
        }
    }
    class ResultsDialog extends JDialog {
        private final JTextArea resultsTextArea;
        private final AbstractAction closeAction;
        private final JLabel label;
        ResultsDialog() {
            super();
            BorderLayout layout = new BorderLayout();
            setLayout(layout);
            setPreferredSize(new Dimension(300, 200));
            setModal(true);
            final ResultsDialog dialog = this;
            closeAction = new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dialog.setVisible(false);
                }
            };
            label = new JLabel("Resultats");
            label.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
            resultsTextArea = new JTextArea();
            resultsTextArea.setFont(new Font("Monospaced", Font.BOLD, 14));
            resultsTextArea.setEditable(false);
            JButton okButton = new JButton(closeAction);
            Box textAreaBox = Box.createHorizontalBox();
            textAreaBox.setBorder(new EmptyBorder(0, spacing, 0, spacing));
            textAreaBox.add(new JScrollPane(resultsTextArea));
            Box buttonsBox = Box.createHorizontalBox();
            buttonsBox.setBorder(label.getBorder());
            buttonsBox.add(Box.createHorizontalGlue());
            buttonsBox.add(okButton);
            buttonsBox.add(Box.createHorizontalGlue());
            add(label, BorderLayout.PAGE_START);
            add(textAreaBox, BorderLayout.CENTER);
            add(buttonsBox, BorderLayout.PAGE_END);
            getRootPane().setDefaultButton(okButton);
        }
        void setText(String text){
            resultsTextArea.setText(text);
        }
    }
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
        modèleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        final Component tools = this;
        ajouterFormuleAction = new AbstractAction("Ajouter une clause") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String formule = JOptionPane.showInputDialog(tools, dialogText, dialogTitle, JOptionPane.QUESTION_MESSAGE);
                if (formule != null && !formule.isEmpty())
                    if (formule.matches("(([-]?\\w+)\\s+)*([-]?\\w+)")) // Check whether the syntax is valid
                        modèle.addElement(formule.replaceAll("\\s+", " "));
                    else JOptionPane.showMessageDialog(tools, "La syntaxe de la clause est invalide !", "Clause invalide", JOptionPane.WARNING_MESSAGE);
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
                    Vector<String> clauses  = new Vector<>();
                    Vector<Integer> indices = new Vector<>();
                    int k = 1;
                    String content = "";
                    for (int i=0; i<modèle.size(); i++) {
                        for (String c : modèle.get(i).split("\\s")) {
                            String c_ = c.replace("-", "");
                            if (!clauses.contains(c_))
                                clauses.add(c_);
                                indices.add(k++);
                            content += (c.charAt(0) == '-' ? "-" : "") + indices.get(clauses.indexOf(c_))+"\t";
                        }
                        content += "0\n";
                    }
                    content = "p\tcnf\t"+clauses.size()+'\t'+modèle.size()+'\n' + content;
                    Runtime runtime = Runtime.getRuntime();
                    writer.print(content);
                    writer.close();
                    URL toolsURL = ClassLoader.getSystemClassLoader().getResource("Plugins/tools/ubcsat");
                    if (toolsURL == null) throw new FileNotFoundException("Plugins package not found");
                    String toolsPath = new File(toolsURL.toURI()).getAbsolutePath();
                    String[] cmd = {Paths.get(toolsPath, "ubcsat").toString(), "-alg", "saps", "-i",
                                    tempFile.getAbsolutePath(), "-solve", "-r", "out", "null", "-r", "stats", "null"};
                    Process process = runtime.exec(cmd, null, new File(toolsPath));
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
                    else {
                        results = results.replaceAll("^(?:.*\\n){3}", "").substring(1);
                        for (int i=0; i<clauses.size(); i++)
                            results = results.replaceAll(indices.elementAt(i).toString(), clauses.elementAt(i));
                        ResultsDialog resultsDialog = new ResultsDialog();
                        resultsDialog.setText(results);
                        resultsDialog.pack();
                        resultsDialog.setLocationRelativeTo(tools);
                        resultsDialog.setVisible(true);
                    }
                }
                catch (IOException | URISyntaxException e){
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
                // Nothing
            }
        });
        JScrollPane modèleScrollPane = new JScrollPane(modèleList);
        TitledBorder modèleListBorder = BorderFactory.createTitledBorder("Le modèle");
        modèleScrollPane.setBorder(modèleListBorder);
        ubcsatPage.add(modèleScrollPane);
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
        weightedmaxsatPage.setLayout(new BoxLayout(weightedmaxsatPage, BoxLayout.PAGE_AXIS));
        wmodèle = new DefaultListModel<>();
        wmodèleList = new JList<>(wmodèle);
        TitledBorder wmodèleListBorder = BorderFactory.createTitledBorder("Le modèle");
        wmodèleList.setBorder(wmodèleListBorder);
        wmodèleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        wajouterFormuleAction = new AbstractAction("Ajouter une clause") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                WeightedMaxSATDialog dialog = new WeightedMaxSATDialog();
                dialog.setLocationRelativeTo(tools);
                dialog.setVisible(true);
            }
        };
        wajouterFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        final JButton wajouterFormuleButton = new JButton(wajouterFormuleAction);
        wsupprimerFormuleAction = new AbstractAction("Supprimer") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<String> selected = wmodèleList.getSelectedValuesList();
                for (String value : selected) {
                    wmodèle.removeElement(value);
                }
            }
        };
        wsupprimerFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        final JButton wsupprimerFormuleButton = new JButton(wsupprimerFormuleAction);
        wsupprimerFormuleAction.setEnabled(false);
        wmodèleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                wsupprimerFormuleAction.setEnabled(wmodèleList.getSelectedIndices().length > 0);
            }
        });
        Box wbuttonsBox = new Box(BoxLayout.LINE_AXIS);
        wbuttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing,0,0,0));
        wbuttonsBox.add(wajouterFormuleButton);
        wbuttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
        wbuttonsBox.add(wsupprimerFormuleButton);
        wbuttonsBox.add(Box.createHorizontalGlue());
        weightedmaxsatPage.add(new JScrollPane(wmodèleList));
        weightedmaxsatPage.add(wbuttonsBox);
        weightedmaxsatPage.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        // Add the pages
        tabs.addTab("SAT", ubcsatPage);
        tabs.addTab("Weighted Max SAT", weightedmaxsatPage);
    }
}
