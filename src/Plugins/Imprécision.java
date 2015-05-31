package Plugins;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Imprécision extends JPanel {
    private static final String FuzzyButtonText = "Lancer Fuzzy Logic Toolbox ™";
    private static final String FuzzyDescription = "<html><div style=\"text-align: center;\">Fuzzy Logic Toolbox ™ fournit des fonctions, des applications, et un bloc Simulink® pour l'analyse, la conception et la simulation des systèmes basés sur la logique floue .</html>";
    private static final int spacing = 5;
    public Imprécision(){
        super();
        final Imprécision Imprécision = this;
        setName("Imprécision");
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(mainLayout);
        JButton DecPosButton = new JButton(new AbstractAction(FuzzyButtonText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
           
                    
                    Runnable externalProgramLauncher = new Runnable() {
                    	Process process = null;
                        public void run() {
                            try {
                            	process=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize  -nosplash -wait  -r \"fuzzy;\"");
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(Imprécision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    };
                    Thread t = new Thread(externalProgramLauncher);
                    t.start();
               
            }
        });
        DecPosButton.setMnemonic('F');
        Box DecPosBox = new Box(BoxLayout.PAGE_AXIS);
        DecPosBox.setBorder(new TitledBorder("Fuzzy Logic"));
        Box descriptionBox =  Box.createHorizontalBox();
        descriptionBox.add(new JLabel(FuzzyDescription,SwingConstants.CENTER));
        descriptionBox.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
        DecPosBox.add(descriptionBox);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(descriptionBox.getBorder());
        buttonBox.add(DecPosButton);
        DecPosBox.add(buttonBox);
        add(DecPosBox);
    }
}
