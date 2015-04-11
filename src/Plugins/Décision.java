package Plugins;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Décision extends JPanel {
    private static final String DecPosButtonText = "Lancer DecPos";
    public Décision(){
        super();
        final Décision décision = this;
        setName("Décision");
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(mainLayout);
        JButton DecPosButton = new JButton(new AbstractAction(DecPosButtonText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    final URL decPosURL = ClassLoader.getSystemClassLoader().getResource("Plugins/décision/DecPos");
                    if (decPosURL == null) throw new FileNotFoundException("DecPos folder not found");
                    final File decPosFolderPath = new File(decPosURL.toURI());
                    String[] libs = {"lib/orbital-core.jar", "lib/orbital-ext.jar", "lib/org.sat4j.core-src.jar",
                                    "lib/org.sat4j.core.jar"};
                    final StringBuilder sb = new StringBuilder();
                    for (String s : libs) {
                        sb.append(':');
                        sb.append(s);
                    }
                    Runnable externalProgramLauncher = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ProcessBuilder decPosPB = new ProcessBuilder("java","-cp","."+sb.toString(),"Fusion");
                                decPosPB.directory(decPosFolderPath);
                                decPosPB.redirectError(ProcessBuilder.Redirect.INHERIT); // Show DecPos errors
                                decPosPB.start();
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(décision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    };
                    Thread t = new Thread(externalProgramLauncher);
                    t.start();
                } catch (IOException | URISyntaxException e) {
                    JOptionPane.showMessageDialog(décision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        DecPosButton.setMnemonic('P');
        Box boxButton = new Box(BoxLayout.LINE_AXIS);
        boxButton.setBorder(new TitledBorder("DecPos"));
        boxButton.add(DecPosButton);
        add(boxButton);
    }
}
