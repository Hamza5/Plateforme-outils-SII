package Plugins;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Décision extends JPanel {
    private static final String DecPosButtonText = "Lancer DecPos";
    private static final String DecPosDescription = "Programme pour calculer les décisions. Développé par Nougui";
    private static final int spacing = 5;
    private static final String FuzzyButtonText = "Lancer GraphViz02";
    private static final String FuzzyDescription = "<html><div style=\"text-align: center;\">Cette Application offre la possibilité de traiter les processus suivants :"+
"<br>– La décomposition d’un diagramme d’influence possibiliste vers :<br>– Deux réseaux possibilites sans réduction des noeuds d’utilité"+
"<br>– Deux réseaux possibilites avec réduction des noeuds d’utilité"+
"<br>– La transformation d’un diagramme d’influence possibiliste vers un réseau possibiliste"+
"<br>– La fusion de graphes :"+
	"<br>– La fusion de graphes U-acycliques."+
	"<br>– La fusion de graphes U-cycliques."+
"<br>– Le calcul de la décision qualitative optimale optimiste en utilisant :"+
		"<br>– Le premier modèle (avec et sans réduction)."+
		"<br>– Le deuxième modèle."+
"<br>– La génération de plusieurs diagrammes d’influence possibilistes."+
"<br>– La transformation d’une base possibiliste vers un réseau possibiliste</html>";

 
    public Décision(){
    	  
    	 super();
    	 JTabbedPane tabs;
    	 
 	    tabs = new JTabbedPane();
 	    JPanel DecPos = new JPanel();
 		tabs.addTab("Logique",DecPos);
 		 JPanel khoula = new JPanel();
  		tabs.addTab("Graphique",khoula);
        
        final Décision Décision = this;
        setName("Décision");
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(mainLayout);
     
        JButton DecPosButton = new JButton(new AbstractAction(DecPosButtonText) {
             public void actionPerformed(ActionEvent actionEvent) {
                try {
                    final URL decPosURL = ClassLoader.getSystemClassLoader().getResource("Plugins/Décision/DecPos");
                    if (decPosURL == null) throw new FileNotFoundException("DecPos folder not found");
                    final File decPosFolderPath = new File(decPosURL.toURI());
                    String[] libs = {"lib/orbital-core.jar", "lib/orbital-ext.jar", "lib/org.sat4j.core-src.jar",
                                    "lib/org.sat4j.core.jar"};
                    final StringBuilder sb = new StringBuilder();
                    for (String s : libs) {
                        sb.append(File.pathSeparator);
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
                                JOptionPane.showMessageDialog(Décision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    };
                    Thread t = new Thread(externalProgramLauncher);
                    t.start();
                } catch (IOException | URISyntaxException e) {
                    JOptionPane.showMessageDialog(Décision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton Khaoula = new JButton(new AbstractAction(FuzzyButtonText) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
           
            	final URL PNTURL = ClassLoader.getSystemClassLoader().getResource("Plugins/Décision/Pnt");
            	
				
                    Runnable externalProgramLauncher = null;
					
						externalProgramLauncher = new Runnable() {
							Process process = null;
							 
							//Path path = Paths.get(System.getProperty("user.dir"),"src","Plugins","Décision","Pnt");	
							
							public void run() {
						        try {
						        	final String cmd="matlab -nodesktop -nodisplay -minimize  -nosplash -wait  -r \"cd "+PNTURL.toURI().getPath().toString().substring(1)+"; addpath(genpathKPM(pwd)); GUI;";
						        	if (PNTURL == null) throw new FileNotFoundException("PNT folder not found");
						        	System.out.println("cmd "+cmd);
						        	process=Runtime.getRuntime().exec(cmd);
						        } catch (IOException e) {
						            JOptionPane.showMessageDialog(Décision, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						        } catch (URISyntaxException e) {
						        	JOptionPane.showMessageDialog(null,
			    						    "Erreur d'exécution du script \nScripte non trouvée!",
			    							   "Erreur",
			    							   JOptionPane.ERROR_MESSAGE);
								}
						    }
					
						};
					
                    Thread t = new Thread(externalProgramLauncher);
                    t.start();
               
            }
        });
        
        DecPosButton.setMnemonic('P');
        Box DecPosBox = new Box(BoxLayout.PAGE_AXIS);
        DecPosBox.setBorder(new TitledBorder("DecPos"));
        Box descriptionBox =  Box.createHorizontalBox();
        descriptionBox.add(Box.createHorizontalGlue());
        descriptionBox.add(new JLabel(DecPosDescription));
        descriptionBox.add(Box.createHorizontalGlue());
        descriptionBox.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
        DecPosBox.add(descriptionBox);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(descriptionBox.getBorder());
        buttonBox.add(DecPosButton);
        DecPosBox.add(buttonBox);
        GroupLayout gl_DecPos = new GroupLayout(DecPos);
        gl_DecPos.setHorizontalGroup(
        	gl_DecPos.createParallelGroup(Alignment.LEADING)
        		.addComponent(DecPosBox, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        gl_DecPos.setVerticalGroup(
        	gl_DecPos.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_DecPos.createSequentialGroup()
        			.addGap(5)
        			.addComponent(DecPosBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(187, Short.MAX_VALUE))
        );
        DecPos.setLayout(gl_DecPos);
        
        
        Khaoula.setMnemonic('F');
        Box KhaoulaBox = new Box(BoxLayout.PAGE_AXIS);
        KhaoulaBox.setBorder(new TitledBorder("GraphViz02"));
        Box khaoulaDescBox =  Box.createHorizontalBox();
        khaoulaDescBox.add(new JLabel(FuzzyDescription,SwingConstants.CENTER));
        khaoulaDescBox.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
        KhaoulaBox.add(khaoulaDescBox);
        Box khaoulabuttonBox = Box.createHorizontalBox();
        khaoulabuttonBox.setBorder(khaoulaDescBox.getBorder());
        khaoulabuttonBox.add(Khaoula);
        KhaoulaBox.add(khaoulabuttonBox);
        GroupLayout gl_khoula = new GroupLayout(khoula);
        gl_khoula.setHorizontalGroup(
        	gl_khoula.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_khoula.createSequentialGroup()
        			.addComponent(KhaoulaBox, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        			.addGap(2))
        );
        gl_khoula.setVerticalGroup(
        	gl_khoula.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_khoula.createSequentialGroup()
        			.addGap(5)
        			.addComponent(KhaoulaBox, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
        			.addGap(0, 0, Short.MAX_VALUE))
        );
        khoula.setLayout(gl_khoula);
        add(tabs);
        
    }
}
