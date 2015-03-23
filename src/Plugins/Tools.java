package Plugins;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Tools extends JPanel {
    private final JTabbedPane tabs;
    private final JPanel ubcsatPage;
    private final DefaultListModel<String> modèle;
    private final JList<String> modèleList;
    private final AbstractAction ajouterFormuleAction;
    private final AbstractAction supprimerFormuleAction;
    private final JTable wmodèleTable;
    private final AbstractAction wajouterFormuleAction;
    private final AbstractAction wsupprimerFormuleAction;
    private final DefaultTableModel wmodèle;
    private final AbstractAction calculerAction;
    private final JPanel weightedmaxsatPage;
    private final String title = "Tools";
    private final String dialogTitle = "Nouvelle formule";
    private final String dialogText = "La formule (Utiliser '-' pour la négation)";
    private final String ajouterFormuleText = "Ajouter une formule";
    private final String supprimerText = "Supprimer";
    private final String formuleInvalideText = "La syntaxe de la formule est invalide !";
    private final String formuleInvalideTitle = "Formule invalide";
    private final String formuleRegExp = "(([-]?\\w+)\\s+)*([-]?\\w+)";
    private final Color invalidForground = Color.WHITE;
    private final Color invalidBackground = Color.RED;
    private final String modèleListName = "list";
    private final String modèleTableName = "table";
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
                        jComponent.setBackground(invalidBackground);
                        jComponent.setForeground(invalidForground);
                        return false;
                    }
                }
            });
            clauseField.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent jComponent) {
                    String text = ((JTextField) jComponent).getText();
                    if (text.matches(formuleRegExp)) {
                        jComponent.setBackground(poidsFieldBackground);
                        jComponent.setForeground(poidsFieldForeground);
                        return true;
                    }
                    else {
                        jComponent.setBackground(invalidBackground);
                        jComponent.setForeground(invalidForground);
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
                    String text = clauseField.getText();
                    String poids = poidsField.getText();
                    if (!text.isEmpty() && !poidsField.getText().isEmpty())
                        if (text.matches(formuleRegExp)){ // Check whether the syntax is valid
                            try {
                                String[] data = {text.replaceAll("\\s+", " "), String.valueOf(Float.parseFloat(poids))};
                                wmodèle.addRow(data);
                            }
                            catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(dialog, "Le poids est invalide", formuleInvalideTitle, JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(dialog, formuleInvalideText, formuleInvalideTitle, JOptionPane.WARNING_MESSAGE);
                        }
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
        modèleList.setName(modèleListName);
        modèleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        final Component tools = this;
        ajouterFormuleAction = new AbstractAction(ajouterFormuleText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String formule = JOptionPane.showInputDialog(tools, dialogText, dialogTitle, JOptionPane.QUESTION_MESSAGE);
                if (formule != null && !formule.isEmpty())
                    if (formule.matches("(([-]?\\w+)\\s+)*([-]?\\w+)")) // Check whether the syntax is valid
                        modèle.addElement(formule.replaceAll("\\s+", " "));
                    else JOptionPane.showMessageDialog(tools, formuleInvalideText, formuleInvalideTitle, JOptionPane.WARNING_MESSAGE);
            }
        };
        ajouterFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        final JButton ajouterFormuleButton = new JButton(ajouterFormuleAction);
        supprimerFormuleAction = new AbstractAction(supprimerText) {
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
        ListDataListener calculerListDataListener = new ListDataListener() {
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
        };
        TableModelListener calculerTableModelListener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                calculerAction.setEnabled(tableModelEvent.getType() == TableModelEvent.INSERT);
                calculerAction.setEnabled(!(tableModelEvent.getType() == TableModelEvent.DELETE && wmodèleTable.getRowCount() == 0));
            }
        };
        modèle.addListDataListener(calculerListDataListener);
        JScrollPane modèleScrollPane = new JScrollPane(modèleList);
        TitledBorder modèleListBorder = BorderFactory.createTitledBorder("Le modèle");
        modèleScrollPane.setBorder(modèleListBorder);
        ubcsatPage.add(modèleScrollPane);
        Box buttonsBox = new Box(BoxLayout.LINE_AXIS);
        buttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing, 0, 0, 0));
        buttonsBox.add(ajouterFormuleButton);
        buttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
        buttonsBox.add(supprimerFormuleButton);
        ubcsatPage.add(buttonsBox);
        ubcsatPage.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        // Content of Weighted Max SAT
        weightedmaxsatPage = new JPanel();
        weightedmaxsatPage.setLayout(new BoxLayout(weightedmaxsatPage, BoxLayout.PAGE_AXIS));
        Vector<String> columnNames = new Vector<>(2);
        columnNames.add("Formule");
        columnNames.add("Poids");
        wmodèle = new DefaultTableModel(columnNames, 0);
        wmodèle.addTableModelListener(calculerTableModelListener);
        wmodèleTable = new JTable(wmodèle);
        wmodèleTable.setName(modèleTableName);
        wmodèleTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        wmodèleTable.setCellSelectionEnabled(false);
        wmodèleTable.setRowSelectionAllowed(true);
        wmodèleTable.setColumnSelectionAllowed(false);
        wajouterFormuleAction = new AbstractAction(ajouterFormuleText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                WeightedMaxSATDialog dialog = new WeightedMaxSATDialog();
                dialog.setLocationRelativeTo(tools);
                dialog.setVisible(true);
            }
        };
        wajouterFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        final JButton wajouterFormuleButton = new JButton(wajouterFormuleAction);
        wsupprimerFormuleAction = new AbstractAction(supprimerText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] selected = wmodèleTable.getSelectedRows();
                for (int i=selected.length-1; i>=0; i--) {
                    wmodèle.removeRow(selected[i]);
                }
            }
        };
        wsupprimerFormuleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        final JButton wsupprimerFormuleButton = new JButton(wsupprimerFormuleAction);
        wsupprimerFormuleAction.setEnabled(false);
        wmodèleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                wsupprimerFormuleAction.setEnabled(wmodèleTable.getSelectedRows().length > 0);
            }
        });
        Box wbuttonsBox = new Box(BoxLayout.LINE_AXIS);
        wbuttonsBox.setBorder(BorderFactory.createEmptyBorder(spacing, 0, 0, 0));
        wbuttonsBox.add(wajouterFormuleButton);
        wbuttonsBox.add(Box.createRigidArea(new Dimension(spacing, 0)));
        wbuttonsBox.add(wsupprimerFormuleButton);
        weightedmaxsatPage.add(new JScrollPane(wmodèleTable));
        weightedmaxsatPage.add(wbuttonsBox);
        weightedmaxsatPage.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        // Add the pages
        tabs.addTab("SAT", ubcsatPage);
        tabs.addTab("Weighted Max SAT", weightedmaxsatPage);
        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                calculerAction.setEnabled((tabs.getSelectedIndex() == 0 && !modèle.isEmpty()) || (tabs.getSelectedIndex() == 1 && wmodèle.getRowCount() > 0));
            }
        });
        Box bottomBox = Box.createHorizontalBox();
        bottomBox.setBorder(ubcsatPage.getBorder());
        bottomBox.add(calculerButton);
        add(bottomBox);
    }
}
