package Plugins;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import matlabcontrol.MatlabInvocationException;
public class Incertain extends JPanel implements ActionListener{
	private static String LireSousFormeString(String filePath) {
		 byte[] buffer = new byte[(int) new File(filePath).length()];
		 BufferedInputStream f = null;
		 try {
		 try {
			f = new BufferedInputStream(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			
		}
		 try {
			f.read(buffer);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 } finally {
		 if (f != null) try { f.close(); } catch (IOException ignored) { }
		 }
		 return new String(buffer);
		 }
	private JTextField txtCheminVersLe;
	JFileChooser chooser;
	String choosertitle;
	JButton btnNewButton;
	JButton btnClaculer;
	JTextPane textPane;
	public Incertain() throws MatlabInvocationException, IOException, URISyntaxException {
		super();
        setName("Incertain"); // Will be the title of the tab
		
		ClassLoader loader =ClassLoader.getSystemClassLoader();
		URL url=loader.getResource("Plugins");
		//url.getPath();
		
//		}
    Process p;
    System.out.println("path : "+Paths.get(new File(url.toURI()).getAbsolutePath(),"FullBNT-1.0.4").toFile()); 
   //  p=Runtime.getRuntime().exec("cd "+Paths.get(new File(url.toURI()).getAbsolutePath(),"FullBNT-1.0.4").toFile());

     p=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -r addpath(genpathKPM(pwd));quit;",null,Paths.get(new File(url.toURI()).getAbsolutePath(),"FullBNT-1.0.4").toFile());
     try {p.waitFor();} catch (InterruptedException e) {e.printStackTrace();
	}
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{306, 109, 0};
		gridBagLayout.rowHeights = new int[]{20, 150, 39, 14, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 9.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		txtCheminVersLe = new JTextField();
		txtCheminVersLe.setEditable(false);
		txtCheminVersLe.setText("Chemin vers le scripte");
		txtCheminVersLe.setToolTipText("");
		txtCheminVersLe.setColumns(10);
		GridBagConstraints gbc_txtCheminVersLe = new GridBagConstraints();
		gbc_txtCheminVersLe.fill = GridBagConstraints.BOTH;
		gbc_txtCheminVersLe.insets = new Insets(0, 0, 5, 5);
		gbc_txtCheminVersLe.gridx = 0;
		gbc_txtCheminVersLe.gridy = 0;
		add(txtCheminVersLe, gbc_txtCheminVersLe);
		
		
		btnNewButton = new JButton("Choisir un script");
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("choisir");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridwidth = 2;
		gbc_textPane.gridheight = 2;
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);
		
		btnClaculer = new JButton("Claculer");
		btnClaculer.setActionCommand("Claculer");
		
		btnClaculer.addActionListener(this);
		btnClaculer.setEnabled(false);
		GridBagConstraints gbc_btnClaculer = new GridBagConstraints();
		gbc_btnClaculer.fill = GridBagConstraints.VERTICAL;
		gbc_btnClaculer.gridwidth = 2;
		gbc_btnClaculer.gridx = 0;
		gbc_btnClaculer.gridy = 3;
		add(btnClaculer, gbc_btnClaculer);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "choisir":
			FileNameExtensionFilter matlabFilter = new FileNameExtensionFilter("Matlab", "m");
		    chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("choisir le scripte a executer");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    chooser.addChoosableFileFilter(matlabFilter);
		    chooser.setFileFilter(matlabFilter);
            chooser.setAcceptAllFileFilterUsed(false);
		    
		    //    
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		      System.out.println("getCurrentDirectory(): " 
		         +  chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " 
		         +  chooser.getSelectedFile());
		      txtCheminVersLe.setText(chooser.getSelectedFile().toString());
		      btnClaculer.setEnabled(true);
		    }
		    else {
		    	btnClaculer.setEnabled(false);
		      System.out.println("No Selection ");
		      }
		    File f = new File(Paths.get(chooser.getCurrentDirectory().toString(),"output").toString());
		    break;
		case "Claculer":  
        
			try {
				f = new File(chooser.getSelectedFile().toString());
				if(!f.exists() || f.isDirectory()) {JOptionPane.showMessageDialog(new JFrame(),
					    "Scripte introuvable",
					   "Erreur",
					   JOptionPane.ERROR_MESSAGE);break;
				  }
				if (!f.canRead()||f.isHidden()){JOptionPane.showMessageDialog(new JFrame(),
					    "Erreur dans l'ouverture du fichier",
					   "Erreur",
					   JOptionPane.ERROR_MESSAGE);break;}
				
				//System.out.println(chooser.getSelectedFile().getName());
				//p = Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile output -r \"cd C:\\Users\\ahmed\\Desktop\\L3;multi_connected_1;quit;\"");
				
				Process p;
				p=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile -wait output -r "+chooser.getSelectedFile().getName().substring(0, chooser.getSelectedFile().getName().length()-2)+";quit;",null,chooser.getCurrentDirectory());
				
				try {
					p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//System.out.println(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString());
			textPane.setText(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).substring(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).indexOf("ans =")));
//			File f = new File(Paths.get(chooser.getCurrentDirectory().toString(),"output").toString());
//			f.delete();//Pour effacer le fichier output de l'execution precedente
		}
		}
}
