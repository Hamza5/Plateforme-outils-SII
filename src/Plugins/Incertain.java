package Plugins;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;



public class Incertain extends JPanel implements ActionListener{

	private static final long serialVersionUID = -8123406571694511514L;
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
	public void makeHelloWorldGraph(mxGraph graph,String str) {
	    
	    Object parent = graph.getDefaultParent();
	    graph.getModel().beginUpdate();
	    try {
	       // Object v1 = graph.insertVertex(parent, null, str, 20, 20, 80,
	       //         30,"opacity=0");
	        Object v1 =  graph.insertVertex(graph.getDefaultParent(), null, str, 10, 50, 100, 40,mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE);
	        Vec.add(v1);
	        graph.getSelectionModel().clear();	
	    } finally {
	        graph.getModel().endUpdate();
	    }
	}
	private JTextField txtCheminVersLe;
	JFileChooser chooser;
	String choosertitle;
	JButton btnNewButton;
	JButton btnClaculer;
	JTextPane textPane;
	JTabbedPane tabs;
	JPanel ScripteBNT;
	mxGraph graph;
	Object parent;
	JPanel BNTScripteBuild;
	private JButton Noeuxbtn;
	private JButton btnNewButton_1;
	int nom = 1;
	Vector<Object> Vec=new Vector<Object>();
	HashMap <Integer, String> IdName = new HashMap<Integer, String>();
	public Incertain() throws IOException, URISyntaxException {
		super();
        setName("Incertain"); // Will be the title of the tab
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tab 1", null, null,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		ClassLoader loader =ClassLoader.getSystemClassLoader();
		URL url=loader.getResource("Plugins");
		
 
    System.out.println("path : "+Paths.get(new File(url.toURI()).getAbsolutePath(),"FullBNT-1.0.4").toFile());
		GridBagConstraints gbc_txtCheminVersLe_1 = new GridBagConstraints();
		gbc_txtCheminVersLe_1.anchor = GridBagConstraints.WEST;
		gbc_txtCheminVersLe_1.fill = GridBagConstraints.BOTH;
		gbc_txtCheminVersLe_1.insets = new Insets(0, 0, 5, 5);
		gbc_txtCheminVersLe_1.gridx = 0;
		gbc_txtCheminVersLe_1.gridy = 0;
		GridBagConstraints gbc_Noeuxbtn_1 = new GridBagConstraints();
		gbc_Noeuxbtn_1.fill = GridBagConstraints.VERTICAL;
		gbc_Noeuxbtn_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_Noeuxbtn_1.insets = new Insets(0, 0, 5, 5);
		gbc_Noeuxbtn_1.gridx = 1;
		gbc_Noeuxbtn_1.gridy = 0;
		GridBagConstraints gbc_btnClaculer_1 = new GridBagConstraints();
		gbc_btnClaculer_1.anchor = GridBagConstraints.NORTH;
		gbc_btnClaculer_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnClaculer_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnClaculer_1.gridwidth = 2;
		gbc_btnClaculer_1.gridx = 0;
		gbc_btnClaculer_1.gridy = 1;
		GridBagConstraints gbc_textPane_1 = new GridBagConstraints();
		gbc_textPane_1.anchor = GridBagConstraints.WEST;
		gbc_textPane_1.fill = GridBagConstraints.BOTH;
		gbc_textPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_textPane_1.gridx = 1;
		gbc_textPane_1.gridy = 1;
	
		////////////////////////////////////////////////
		

		graph = new mxGraph();
		parent = graph.getDefaultParent();
								GridBagLayout gridBagLayout = new GridBagLayout();
								gridBagLayout.columnWidths = new int[]{383, 0};
								gridBagLayout.rowHeights = new int[]{315, 0};
								gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
								gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
								setLayout(gridBagLayout);
														tabs = new JTabbedPane();
														ScripteBNT = new JPanel();
														tabs.addTab("Scripte BNT ",ScripteBNT);
														GridBagLayout gbl_ubcsatPage = new GridBagLayout();
														gbl_ubcsatPage.columnWidths = new int[]{333, 115, 0};
														gbl_ubcsatPage.rowHeights = new int[]{23, 226, 23, 0};
														gbl_ubcsatPage.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
														gbl_ubcsatPage.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
														ScripteBNT.setLayout(gbl_ubcsatPage);
														
														
														btnNewButton = new JButton("Choisir un script");
														btnNewButton.addActionListener(this);
														
														txtCheminVersLe = new JTextField();
														txtCheminVersLe.setEditable(false);
														txtCheminVersLe.setText("Chemin vers le scripte");
														txtCheminVersLe.setToolTipText("");
														txtCheminVersLe.setColumns(10);
														setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabs, BNTScripteBuild, ScripteBNT, txtCheminVersLe, btnNewButton, textPane, btnClaculer}));
														GridBagConstraints gbc_txtCheminVersLe = new GridBagConstraints();
														gbc_txtCheminVersLe.fill = GridBagConstraints.HORIZONTAL;
														gbc_txtCheminVersLe.insets = new Insets(0, 0, 5, 5);
														gbc_txtCheminVersLe.gridx = 0;
														gbc_txtCheminVersLe.gridy = 0;
														ScripteBNT.add(txtCheminVersLe, gbc_txtCheminVersLe);
														btnNewButton.setActionCommand("choisir");
														GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
														gbc_btnNewButton.anchor = GridBagConstraints.WEST;
														gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
														gbc_btnNewButton.gridx = 1;
														gbc_btnNewButton.gridy = 0;
														ScripteBNT.add(btnNewButton, gbc_btnNewButton);
														
														textPane = new JTextPane();
														textPane.setEditable(false);
														GridBagConstraints gbc_textPane = new GridBagConstraints();
														gbc_textPane.fill = GridBagConstraints.BOTH;
														gbc_textPane.insets = new Insets(0, 0, 5, 0);
														gbc_textPane.gridwidth = 2;
														gbc_textPane.gridx = 0;
														gbc_textPane.gridy = 1;
														ScripteBNT.add(textPane, gbc_textPane);
														
														btnClaculer = new JButton("Claculer");
														btnClaculer.setActionCommand("Claculer");
														
														btnClaculer.addActionListener(this);
														btnClaculer.setEnabled(false);
														GridBagConstraints gbc_btnClaculer = new GridBagConstraints();
														gbc_btnClaculer.gridwidth = 2;
														gbc_btnClaculer.insets = new Insets(0, 0, 0, 5);
														gbc_btnClaculer.gridx = 0;
														gbc_btnClaculer.gridy = 2;
														ScripteBNT.add(btnClaculer, gbc_btnClaculer);
														BNTScripteBuild = new JPanel();
														
														
																tabs.addTab("BNT Scripte build", BNTScripteBuild);
																GridBagLayout gbl_BNTScripteBuild = new GridBagLayout();
																gbl_BNTScripteBuild.columnWidths = new int[]{97, 349, 0};
																gbl_BNTScripteBuild.rowHeights = new int[]{26, 0, 244, 0};
																gbl_BNTScripteBuild.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
																gbl_BNTScripteBuild.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
																BNTScripteBuild.setLayout(gbl_BNTScripteBuild);
																
																Noeuxbtn = new JButton("Ajouter noeud");
																Noeuxbtn.addActionListener(this);
																GridBagConstraints gbc_Noeudbtn = new GridBagConstraints();
																gbc_Noeudbtn.anchor = GridBagConstraints.SOUTH;
																gbc_Noeudbtn.fill = GridBagConstraints.HORIZONTAL;
																gbc_Noeudbtn.insets = new Insets(0, 0, 5, 5);
																gbc_Noeudbtn.gridx = 0;
																gbc_Noeudbtn.gridy = 0;
																BNTScripteBuild.add(Noeuxbtn, gbc_Noeudbtn);
																
																		mxGraphComponent graphComponent = new mxGraphComponent(graph);
																		GridBagConstraints gbc_graphComponent = new GridBagConstraints();
																		gbc_graphComponent.fill = GridBagConstraints.BOTH;
																		gbc_graphComponent.gridheight = 3;
																		gbc_graphComponent.gridx = 1;
																		gbc_graphComponent.gridy = 0;
																		BNTScripteBuild.add(graphComponent, gbc_graphComponent);
																		
																		btnNewButton_1 = new JButton("Supprimer");
																		btnNewButton_1.addActionListener(this);
																		btnNewButton_1.setVerticalAlignment(SwingConstants.TOP);
																		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
																		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
																		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
																		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
																		gbc_btnNewButton_1.gridx = 0;
																		gbc_btnNewButton_1.gridy = 1;
																		BNTScripteBuild.add(btnNewButton_1, gbc_btnNewButton_1);
																		tabs.setEnabledAt(1, true);
																		GridBagConstraints gbc_tabs = new GridBagConstraints();
																		gbc_tabs.fill = GridBagConstraints.BOTH;
																		gbc_tabs.gridx = 0;
																		gbc_tabs.gridy = 0;
																		add(tabs, gbc_tabs);
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
			textPane.setText(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).substring(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).indexOf("ans =")));
//Pour effacer le fichier output de l'execution precedente
			break;
		case "Ajouter noeud":
			JOptionPane jop = new JOptionPane();
		    String nom = jop.showInputDialog(null, "Donner le nom du noeud !", "Nouvaux noued !", JOptionPane.QUESTION_MESSAGE);
			if(!nom.equals("")){
		    makeHelloWorldGraph(graph,nom);
			}
			break;
		case "Supprimer":
			
		    
			graph.clearSelection(); 
			graph.selectAll();
		    //graph.getModel().remove(nom);
//			graph.selectChildCell();
//	        graph.removeCells();
//	      Object[] cells = graph.getSelectionCells(); //here you have all cells
//			for (Object c : cells) {
//			mxCell cell = (mxCell) c; //cast
//			 mxCell myCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(cell.getId());
//			
//			  System.out.println(cell.getId()+" value "+myCell.getValue());
//			  graph.getModel().beginUpdate();
//			  try {
//			    graph.getModel().remove( cell);
//			 } finally {
//			    graph.getModel().endUpdate();
//			 }
//			}
//	        Object[] cells = graph.getSelectionCells(); //here you have all cells
//			for (Object c : cells) {
//			mxCell cell = (mxCell) c; //cast
//			 mxCell myCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(cell.getId());
//			if(myCell.isVertex()){
//			  System.out.println(cell.getId()+" value "+myCell.getValue());
//			  graph.getModel().beginUpdate();
//			  try {
//			    graph.getModel().remove( cell);
//			 } finally {
//			    graph.getModel().endUpdate();
//			 }
//			  }
//			}
			break;
		}
	      
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graph.setMultigraph(false);
		graph.setAllowDanglingEdges(false);
		graphComponent.setConnectable(true);
		graphComponent.setToolTips(true);
		new mxRubberband(graphComponent);
		new mxKeyboardHandler(graphComponent);
		graph.setAutoOrigin(true);
	    graph.setAutoSizeCells(true);
	    graph.setCellsLocked(false);
	    graph.setAllowDanglingEdges(false);
	    graph.setCellsCloneable(false);
	    graphComponent.setAutoExtend(true);
	    graphComponent.setExportEnabled(true);
	    graphComponent.setCenterZoom(true);
	    graph.setCellsEditable(false) ;
	    mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());

        mxGraphComponent graphUIComponent = new mxGraphComponent(graph);

       graphUIComponent.setEnabled(false);
    //////////////////////////////
       for (Object c : Vec) {
    	   mxCell cell = (mxCell) c;
    	   if (cell.isVertex()) { //isVertex
    		   System.out.println(cell.getId());   
    	   }
    	   }
       /////////////////////////
graph.clearSelection(); 
graph.selectAll();
Object[] cells = graph.getSelectionCells(); //here you have all cells
for (Object c : cells) {
mxCell cell = (mxCell) c; //cast
if (cell.isEdge()) { //isVertex
 System.out.println(cell.getId());
 
}else{ //is not a vertex, so u can get source and target 
  //todo
	 
  cell.getChildCount(); //Returns the number of child cells. (edges)
 // cell.getChildAt(0); //Returns the child at the specified index. (target)
}
}
			
		///////////////////////
		}
}
