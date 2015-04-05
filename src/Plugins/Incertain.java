package Plugins;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class Incertain extends JPanel implements ActionListener{
		 private JTextField txtCheminVersLe;
		 JFileChooser chooser;
		 String choosertitle;
		 JButton btnNewButton;
		 JButton btnClaculer;
		 JTextPane textPaneSrptbnt;
		 JTabbedPane tabs;
		 JPanel ScripteBNT;
		 mxGraph graph;
		 Object parent;
		 JPanel BNTScripteBuild;
		 private JButton Noeuxbtn;
		 private JButton Suppbutton;
		 private JButton btnModifer;
		 Object [][] data2;
		 int nom = 1;
		 JTable tableau; 
		 HashMap <String, List<Float>> donnéesDist = new HashMap<String, List<Float>>();
		 public String[] columnsSaisie;
		 public Object[][] dataSaisie;
		 int colimnNonEditable;
		 String VertexCourant;
		 Vector <Float> tempAffich;
		 List<List<String>> edgeVec;
		 int modifRow=0;
		 JPanel panel;
		 Vector<String> vertex;
		 JButton btnNewButton_1;
		 JRadioButton rdbtnBnt;
		 JRadioButton rdbtnPnt;
		   JButton btnModifier;
 class ParametreProb extends JDialog implements ActionListener{
	 JTable table;
	 
			public ParametreProb()
		    {			
				   	   super();
					   setModal(true);
		               GridBagLayout gridBagLayout = new GridBagLayout();
		               gridBagLayout.columnWidths = new int[]{452, 0};
		               gridBagLayout.rowHeights = new int[]{427, 23, 0};
		               gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		               gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		               getContentPane().setLayout(gridBagLayout);
		               
		               
		               
		               //create table with data
		               MyDefaultTableModel model = new MyDefaultTableModel(dataSaisie, columnsSaisie);
		               table =new JTable(model);
				        table.setModel(model);
				        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				        table.setColumnSelectionAllowed(true);
				        table.setRowSelectionAllowed(true);
				        table.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 10));
				        table.setFont(new Font("Arial", Font.PLAIN, 20));
				        table.setRowHeight(30);
		               JScrollPane scrollPane = new JScrollPane(table);
		               DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
		               singleclick.setClickCountToStart(1);
		               for (int i = 0; i < table.getColumnCount(); i++) {
		                   table.setDefaultEditor(table.getColumnClass(i), singleclick);
		               } 
		               GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		               gbc_scrollPane.fill = GridBagConstraints.BOTH;
		               gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		               gbc_scrollPane.gridx = 0;
		               gbc_scrollPane.gridy = 0;
		               getContentPane().add(scrollPane, gbc_scrollPane);
		               InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		               KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		               KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		               im.put(enter, im.get(f2));
		        JButton btnValider = new JButton("Valider");
		        btnValider.addActionListener(this);
		        GridBagConstraints gbc_btnValider = new GridBagConstraints();
		        gbc_btnValider.anchor = GridBagConstraints.NORTH;
		        gbc_btnValider.gridx = 0;
		        gbc_btnValider.gridy = 1;
		        getContentPane().add(btnValider, gbc_btnValider);
		        table.setColumnSelectionAllowed(true);
		        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
		        .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none");
			    table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		        this.setTitle(VertexCourant+" paramètres");
		        getRootPane().setDefaultButton(btnValider);
		        this.pack();
		        this.setLocationRelativeTo(null);
		        this.setVisible(true);
		    }

			class MyDefaultTableModel extends DefaultTableModel {
				public MyDefaultTableModel(Object[][] data, String[] columnNames) {
					super(data, columnNames);
				}
				 public boolean isCellEditable(int row, int col) {
					 if(col<colimnNonEditable) return false;
				   return true;
				 }
}
			  
			@Override
			public void actionPerformed(ActionEvent e) {
				 table.setRowSelectionAllowed(true);
			     table.setColumnSelectionAllowed(false);
			swicth:	switch(e.getActionCommand()){
			
				case "Valider":
					 Float[] rowData = new Float[table.getRowCount()*2];
					 float x,y;
					 table.clearSelection();
					 for (int i = 0; i <table.getRowCount(); i++) {
						
					   //System.out.println("i "+rowData[i].toString()+" i+ "+rowData[i+(table.getRowCount())].toString());
					  try{ x=Float.parseFloat(table.getValueAt(i, colimnNonEditable).toString());
					       y=Float.parseFloat(table.getValueAt(i,colimnNonEditable+1).toString());
					  }
					  catch(NullPointerException  e1){
						  table.setRowSelectionInterval(i, i);
						  JOptionPane.showMessageDialog(null,
							    "Le format de nombre est erroné",
							    "Erreur",
							    JOptionPane.ERROR_MESSAGE);break swicth;} 
					  catch(NumberFormatException e1){
						  table.setRowSelectionInterval(i, i);
						  JOptionPane.showMessageDialog(null,
								    "Le format de nombre est erroné",
								    "Erreur",
								    JOptionPane.ERROR_MESSAGE); break swicth;
					  }
					  rowData[i]=x;
					  rowData[i+(table.getRowCount())]=y;
					  if((x+y)!=1&&rdbtnBnt.isSelected()){
						  table.setRowSelectionInterval(i, i);
						 JOptionPane.showMessageDialog(null,
							    "La somme de chaque ligne doit être égale à 1 !",
							    "Erreur",
							    JOptionPane.ERROR_MESSAGE);
					  break swicth;}
					  if((x<0 || y<0)){
						  
						  table.setRowSelectionInterval(i, i);
						 JOptionPane.showMessageDialog(null,
							    "Les valeurs doivent être positives!",
							    "Erreur",
							    JOptionPane.ERROR_MESSAGE);
					  break swicth;}
					  if((x!=1&&y!=1)&&rdbtnPnt.isSelected()){ 
						 table.setRowSelectionInterval(i, i);
						  JOptionPane.showMessageDialog(null,
							    "Dans chaque ligne une Cellule doit être égale à 1 !",
							    "Erreur",
							    JOptionPane.ERROR_MESSAGE);
					  break swicth;}
					 }
					 
					 System.out.println("rowData "+rowData.length+"VertexCourant = "+VertexCourant+" size "+donnéesDist.get(VertexCourant).size());
					 for(int i1=0;i1<rowData.length;i1++){
						 System.out.println(rowData[i1]);
					 }
					 donnéesDist.get(VertexCourant).clear();
					 for(int i1=0;i1<rowData.length;i1++){
						  donnéesDist
						  .get(VertexCourant)
						  .add(i1,rowData[i1]);
						  System.out.println(donnéesDist.get(VertexCourant)+" i1 "+i1);
						}
					 System.out.println("donnéesDist "+donnéesDist.keySet());
					 String temp;
					 System.out.println("i= "+donnéesDist.size());
					 for(int i=0;i<donnéesDist.size();i++){
						 
						 
						 temp=data2[i][0].toString();
						 if(VertexCourant.trim().equals(temp))
						 {data2[i][1]=donnéesDist.get(VertexCourant).toString();
//						 System.out.println("data2 "+data2[i][0].toString()+" VertexCourant "+VertexCourant+" i "+i );
						 

						 }
						 
						 
					 }
					 modifRow++;
					 if(modifRow==tableau.getRowCount()){btnNewButton_1.setEnabled(true);}
					 System.out.println("modifRow "+modifRow);
					   tableau.repaint();
					   btnModifier.setEnabled(false);
					 dispose();
			}
				
			}
}
class Fenetre extends JDialog implements ListSelectionListener,ActionListener{
		
		   private static final long serialVersionUID = 1L;
		   JList<String> list;
		   Vector <String>		listData;
		   JScrollPane  scrollPane_1;
		   JButton btnAjouterListe;
		   JButton btnSupprimer;
		   ButtonGroup group;
		   JRadioButton rdbtnNewRadioButton_1;
		   JRadioButton rdbtnNewRadioButton;
		   JRadioButton RdMIN;
		   final JComboBox<String> comboBox_1;
		   JComboBox<String> comboBox_2;
		   JComboBox<String> comboBox;
		   JTextPane textPane;
		   JButton Calculer;
		   int nbrRem=0;
		
		    public Fenetre(){
			   super();
			  setModal(true);
		      this.setLocationRelativeTo(null);
		      this.setTitle("BNT");
		      this.setSize(600, 250);
		      this.createContent();
		      
		      System.out.println(donnéesDist.keySet());
		     
		   
		      listData = new Vector<String>();
		      panel = new JPanel();
		      

		        
		        JLabel lblChoisissezUneEvidence = new JLabel("Noeud observé  :");
		        
		        comboBox = new JComboBox<String>();
			      comboBox.addItem("Par défaut");
			      for(String set : donnéesDist.keySet()){ comboBox.addItem(set);}
		        
		        JLabel lblNewLabel = new JLabel("Évidence :");
		        
		         comboBox_1 = new JComboBox<String>();
		         comboBox_2 = new JComboBox<String>();
		         JLabel lblNewLabel_1 = new JLabel("Vraisemblance :");
		        for(String set : donnéesDist.keySet()){ 
		        	comboBox_1.addItem(set);}
		        for(String set : donnéesDist.keySet()){ 
		        	comboBox_2.addItem(set);}
		      
		        btnAjouterListe = new JButton("Ajouter");
		        btnAjouterListe.setMnemonic(KeyEvent.VK_A);
		        btnAjouterListe.addActionListener(this);
		        
		        rdbtnNewRadioButton = new JRadioButton("Vraie",true);
		        rdbtnNewRadioButton.setActionCommand("Vraie");
		        scrollPane_1 = new JScrollPane();
		        
		        btnSupprimer = new JButton("Supprimer");
		        btnSupprimer.setMnemonic(KeyEvent.VK_S);
		        btnSupprimer.addActionListener(this);
		        btnNewButton_1 = new JButton("Générer le scripte");
		        btnNewButton_1.setMnemonic(KeyEvent.VK_G);
		        btnNewButton_1.setEnabled(false);
		        btnNewButton_1.addActionListener(this);
		        JScrollPane scrollPane = new JScrollPane();
		        
		        Calculer = new JButton("Calculer");
		        Calculer.setMnemonic(KeyEvent.VK_G);
		        Calculer.setEnabled(false);
		        Calculer.addActionListener(this);
		        rdbtnNewRadioButton_1 = new JRadioButton("Faux");
		        rdbtnNewRadioButton_1.setActionCommand("Faux");
		        ButtonGroup group = new ButtonGroup();
		        group.add(rdbtnNewRadioButton);
		        group.add(rdbtnNewRadioButton_1);
		        rdbtnNewRadioButton.addActionListener(this);
		        rdbtnNewRadioButton_1.addActionListener(this);
		        
		        JRadioButton Prod = new JRadioButton("Prod",true);
		        RdMIN = new JRadioButton("Min");
		        ButtonGroup groupProdMin = new ButtonGroup();
		        groupProdMin.add(Prod);
		        groupProdMin.add(RdMIN);
		        JRadioButton[] buttons = new JRadioButton[]{Prod,RdMIN};
		         if(rdbtnBnt.isSelected()){
		        	for (JRadioButton btn : buttons) {
			            btn.setEnabled(false);
			        }
		        }
		         System.out.println("le Row est de: "+modifRow);
			        if(modifRow!=0){
			        	this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			        }
		        btnModifier = new JButton("Modifier");
		        btnModifier.setMnemonic(KeyEvent.VK_M);
		        
		        this.setMinimumSize(new Dimension(400, 550));
		        btnModifier.addActionListener(this);
		        btnModifier.setEnabled(false);
		        GroupLayout groupLayout = new GroupLayout(getContentPane());
		        groupLayout.setHorizontalGroup(
		        	groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(6)
		        			.addComponent(btnModifier))
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(10)
		        			.addComponent(lblChoisissezUneEvidence)
		        			.addGap(6)
		        			.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        			.addGap(10)
		        			.addComponent(lblNewLabel_1)
		        			.addGap(4)
		        			.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(10)
		        			.addComponent(lblNewLabel)
		        			.addGap(4)
		        			.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        			.addGap(21)
		        			.addComponent(rdbtnNewRadioButton)
		        			.addGap(2)
		        			.addComponent(rdbtnNewRadioButton_1)
		        			.addGap(39)
		        			.addComponent(btnAjouterListe))
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(10)
		        			.addComponent(btnSupprimer))
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(10)
		        			.addComponent(btnNewButton_1)
		        			.addGap(27)
		        			.addComponent(Prod)
		        			.addGap(2)
		        			.addComponent(RdMIN))
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addGap(10)
		        			.addComponent(Calculer))
		        		.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        			.addGroup(GroupLayout.Alignment.CENTER, groupLayout.createSequentialGroup()
		        				.addGap(8)
		        				.addComponent(tableau, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		        			.addGroup(GroupLayout.Alignment.CENTER, groupLayout.createSequentialGroup()
		        				.addContainerGap()
		        				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        					.addComponent(scrollPane_1, GroupLayout.Alignment.CENTER)
		        					.addComponent(scrollPane, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))))
		        );
		        groupLayout.setVerticalGroup(
		        	groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addContainerGap()
		        			.addComponent(tableau, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        			.addGap(9)
		        			.addComponent(btnModifier)
		        			.addGap(6)
		        			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        				.addGroup(groupLayout.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblChoisissezUneEvidence))
		        				.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        				.addGroup(groupLayout.createSequentialGroup()
		        					.addGap(3)
		        					.addComponent(lblNewLabel_1))
		        				.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        			.addGap(12)
		        			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        				.addGroup(groupLayout.createSequentialGroup()
		        					.addGap(4)
		        					.addComponent(lblNewLabel))
		        				.addGroup(groupLayout.createSequentialGroup()
		        					.addGap(1)
		        					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        				.addComponent(rdbtnNewRadioButton)
		        				.addComponent(rdbtnNewRadioButton_1)
		        				.addComponent(btnAjouterListe))
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addComponent(btnSupprimer)
		        			.addGap(6)
		        			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
		        				.addComponent(btnNewButton_1)
		        				.addComponent(Prod)
		        				.addComponent(RdMIN))
		        			.addGap(11)
		        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
		        			.addPreferredGap(ComponentPlacement.RELATED)
		        			.addComponent(Calculer))
		        );
		        tableau.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				  
				    public void valueChanged(ListSelectionEvent event) {
				        if (tableau.getSelectedRow() > -1) {
				            // print first column value from selected row
				        	btnModifier.setEnabled(true);
				            System.out.println(tableau.getValueAt(tableau.getSelectedRow(), 0).toString());
				        }
				    }
				});
		        getContentPane().setLayout(groupLayout);
		         textPane = new JTextPane();
		         textPane.setEditable(false);
		        scrollPane.setViewportView(textPane);
		        
		        list = new JList<String>(listData );
		        list.addListSelectionListener( this );
		        scrollPane_1.setViewportView(list);
		        tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        getContentPane().setLayout(groupLayout);
		        System.out.println("le Row est de: "+modifRow);
		        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		        pack();
		        this.addWindowListener(new WindowAdapter() {
		        	public void windowClosing(WindowEvent we)
		        	  { if(modifRow!=0){
		        	    String ObjButtons[] = {"Continuer","Rester"};
		        	    int PromptResult = JOptionPane.showOptionDialog(null, 
		        	        "Etes vous sur de vouloir quitter ? \nsi vous sortez tous les information saisie seront perdus !", "confirmation de sortie", 
		        	        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
		        	        ObjButtons,ObjButtons[1]);
		        	    if(PromptResult==0)
		        	    { 
		        	    	dispose();         
		        	    }
		        	  }else{dispose(); }
		        	  }
		        	});
		   }
		   
		   void enableComponents(Container container, boolean enable) {
		        Component[] components = container.getComponents();
		        for (Component component : components) {
		            component.setEnabled(enable);
		            if (component instanceof Container) {
		                enableComponents((Container)component, enable);
		            }
		        }
		    }
		   
		   void printTruthTable(int n) {
		        int rows = (int) Math.pow(2,n);
		        int row=0;
		        for (int i=0; i<rows; i++) {
		        	int col=0;
		            for (int j=n-1; j>=0; j--) {
		            	
		            if(((i/(int) Math.pow(2, j))%2)== 0){dataSaisie[row][n-col-1]= "F";}else{dataSaisie[row][n-col-1]= "T";}
		                //System.out.print((i/(int) Math.pow(2, j))%2 + " ");
		            col++;
		            }
		            row++;
		           // System.out.println();
		        }
		    }
		   private void createContent(){
		      //Données de notre tableau
		      //Object[][] data = {};
		 
		      String  title[] = {"Noued ", "Distribution de probabilités "};
		      
		       
		      //Notre modèle d'affichage spécifique destiné à pallier
		      //les bugs d'affichage !
		      ZModel zModel = new ZModel(data2, title);
		       
		      tableau = new JTable(zModel);
		      tableau.setRowHeight(30);

		     // tableau.getColumn("modification").setCellEditor(new ModifButtonEditor(new JCheckBox()));
		       
		      //On ajoute le tableau
		     add(new JScrollPane(tableau), BorderLayout.CENTER);
		       
		       
		   }     
		   
	class ZModel extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		private Object[][] data;
		   private String[] title;
		    //Constructeur
		  
		   public ZModel(Object[][] data, String[] title){
		      this.data = data2;
		      this.title = title;
		   }
		    //Retourne le titre de la colonne à l'indice spécifié
		   public String getColumnName(int col) {
		     return this.title[col];
		   }
		   	//Retourne le nombre de colonnes
		   public int getColumnCount() {
		      return this.title.length;
		   }
		    //Retourne le nombre de lignes
		   public int getRowCount() {
		      return this.data.length;
		   }
		     //Retourne la valeur à l'emplacement spécifié
		   public Object getValueAt(int row, int col) {
		      return this.data[row][col];
		   }
		   
		    //Méthode permettant de retirer une ligne du tableau
		   public void modifRow(int position){
			   System.out.println("modifRow Function");
			   Object o = this.getValueAt(position, 0);
			   	String vertex=o.toString();
			   	donnéesDist.get(vertex);
			   	int nmbPere=0;
			   	for(int i=0;i<edgeVec.size();i++){
					if(edgeVec.get(i).get(1).equals(vertex)){
						nmbPere++;}}
			   	dataSaisie=new Object[((donnéesDist.get(vertex).size()/2))][(donnéesDist.get(vertex).size()/2)+1];
			   	for(int i=0;i<(donnéesDist.get(vertex).size())/2;i++){
			   		dataSaisie[i][(nmbPere)]=donnéesDist.get(vertex).get(i);
			   		dataSaisie[i][(nmbPere)+1]=donnéesDist.get(vertex).get(i+(donnéesDist.get(vertex).size())/2);
			   	}
				
			  	String[]tempStr=new String[(nmbPere)+2]; 
			  	System.out.println("this is the size !! "+tempStr.length);
			  	Arrays.fill(tempStr, "");
			   	tempStr[(nmbPere)]="P("+vertex+")=F";
				tempStr[(nmbPere)+1]="P("+vertex+")=T";
				colimnNonEditable=(nmbPere);
				VertexCourant=vertex;
				int pereVertex=0;
				for(int i=0;i<edgeVec.size();i++){
					if(edgeVec.get(i).get(1).equals(VertexCourant)){
						tempStr[pereVertex]=edgeVec.get(i).get(0);
						pereVertex++;}}
				columnsSaisie=tempStr;
				printTruthTable(colimnNonEditable);
				ParametreProb fen1 = new ParametreProb();
				System.out.println("haha !!!");
				fen1.setLocationRelativeTo(null);/////////
			    fen1.setVisible(true);
			  
			    
		      //Cette méthode permet d'avertir le tableau que les données
		      //ont été modifiées, ce qui permet une mise à jour complète du tableau
		      this.fireTableDataChanged();
		      
		   }
		    public boolean isCellEditable(int row, int col){
		     if(col==2) return true;
		     return false;
		   }
		}

	@Override
	public void valueChanged(ListSelectionEvent event) {

		if( event.getSource() == list
				&& !event.getValueIsAdjusting() )
{
	// Get the current selection and place it in the
	// edit field
	String stringValue = (String)list.getSelectedValue();
	if( stringValue != null )
		comboBox_1.setSelectedItem( stringValue );
}
	}
	
	public void appendString(String str) throws BadLocationException
	 {
	      StyledDocument document = (StyledDocument) textPaneSrptbnt.getDocument();
	      document.insertString(document.getLength(), str, null);
	                                                     // ^ or your style attribute  
	  }

	public void actionPerformed(ActionEvent event) {
		String textRadioBtn=null;
		String strPane = "";
		if(rdbtnNewRadioButton.isSelected()){textRadioBtn="Vraie";}
			else{textRadioBtn="Faux";}
		
		switch(event.getActionCommand()){
		case "Modifier":
			((ZModel)tableau.getModel()).modifRow(tableau.getSelectedRow());
			break;		
		case "Ajouter":
				{
					String stringValue = (String) comboBox_1.getSelectedItem();
					if( stringValue != null )
						{ if(listData.contains(stringValue+"(Vraie)")||listData.contains(stringValue+"(Faux)"))
							{
							JOptionPane.showMessageDialog(new JFrame(),
								    "Element existe déjà !",
									   "Erreur",
									   JOptionPane.ERROR_MESSAGE);}
							else{
							listData.addElement( stringValue+"("+textRadioBtn+")" );
							list.setListData( listData );
							scrollPane_1.revalidate();
							scrollPane_1.repaint();}
						}
		}break;
		case "Supprimer":
		{
			
			int selection = list.getSelectedIndex();
			if( selection >= 0 )
			{List<String> selected = list.getSelectedValuesList();
	                for (String value : selected) {
	                	listData.removeElement(value);
	                }
	                list.setListData( listData );
					scrollPane_1.revalidate();
					scrollPane_1.repaint();
					
				if( selection >= listData.size() )
					selection = listData.size() - 1;
				list.setSelectedIndex( selection );
			}
		}break;
		case "Générer le scripte":
		{
	
			String stringValue2 = (String) comboBox.getSelectedItem();
			String stringValue3 = (String) comboBox_2.getSelectedItem();
			if( stringValue2 != null )
				{if(listData.isEmpty()){ JOptionPane.showMessageDialog(new JFrame(),
					    "Il faut avoir au moin une evidence !",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);break;}
				if((listData.contains(stringValue3)))
				{
				 JOptionPane.showMessageDialog(new JFrame(),
					    "En element ne peut pas etre vraisemblance et une evidence !",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);break;}
				if((listData.contains(stringValue2+"(Vraie)")||listData.contains(stringValue2+"(Faux)"))&&!stringValue2.equals("Par défaut"))
					{
					 JOptionPane.showMessageDialog(new JFrame(),
						    "En element ne peut pas etre observé et une evidence !",
							   "Erreur",
							   JOptionPane.ERROR_MESSAGE);break;}
						
					else{
						 System.out.println("coucou du bouton : ");
						 	
						 Calculer.setEnabled(true);
						 textPane.setEditable(true);
						 String Min="directed=0;\nif acyclic(pnet.dag,directed)==0 \n engine = jtree_inf_engine(pnet);\n else \n engine = pearl_inf_engine(pnet);\nend \n ";
						 String Prod="directed=0;\nif acyclic(pnet.dag,directed)==0 \n engine = prod_jtree_inf_engine(pnet);\n else \n engine=prod_pearl_inf_engine(pnet);\nend  \n";
						 String BNTengine="engine=jtree_inf_engine(bnet);\n\n";
						 String PNTengine="[engine] = global_propagation(engine, evidence);\n";
						 String BNTengineAfter="[engine, loglik]=enter_evidence(engine,evidence);\n";
						 String PNTadd="instance_interest=2;\nBEL_Cdt_classique=marg.T(instance_interest);\n";
						 String net;
						 if (rdbtnBnt.isSelected()){
							 net="bnet";
						 }else{net="pnet";}
						 	strPane+=strPane+="N = "+donnéesDist.size()+";\n";
						      System.out.println("the string "+strPane);
						      strPane+="dag = zeros(N,N);\n";
						      for(int i=1;i<=donnéesDist.size();i++){
						    	 vertex.get(i-1);
						    	 strPane+=vertex.get(i-1).replaceAll(" ", "_")+" = "+i+";";
						      }
						      System.out.println("the string "+strPane);
						      strPane+="\n";
						      for(int i =0;i<edgeVec.size();i++)
						      {  strPane+="dag("+edgeVec.get(i).get(0).replaceAll(" ", "_")+","+edgeVec.get(i).get(1).replaceAll(" ", "_")+") = 1;\n";  }
						      strPane+="discrete_nodes = 1:N;\n";
						      strPane+="node_sizes = 2*ones(1,N);\n";
						      strPane+=net+" = mk_bnet(dag, node_sizes);\n";
						      String stringValue = (String) comboBox.getSelectedItem();
						      if(stringValue.equals("Par défaut")){
						    	  strPane+="onodes = [];\n";  
						      }else{
						    	  strPane+="onodes = ["+stringValue.replaceAll(" ", "_")+"];\n";
						    	  }
						      for(String set :donnéesDist.keySet()){
						    	  strPane+=net+".CPD{"+set.replaceAll(" ", "_")+"}=tabular_CPD("+net+", "+set.replaceAll(" ", "_")+","+donnéesDist.get(set).toString().replaceAll(",","")+");\n";
						      }
						      if(rdbtnBnt.isSelected()){strPane+=BNTengine;}
						      else{if (RdMIN.isSelected()){strPane+=Min;
						    	}else{strPane+=Prod;}
						      }
						      strPane+="evidence=cell(1,N);\n";
						      for (int i = 0; i < list.getModel().getSize(); i++) {
						            Object item = list.getModel().getElementAt(i);
						            System.out.println("Item = " + item);
						            if(item.toString().endsWith("(Vraie)")){
						            	strPane+="evidence{"+item.toString().substring(0, item.toString().lastIndexOf("(Vraie)")).replaceAll(" ", "_")+"} = 2;\n";
						            }else{
						            	strPane+="evidence{"+item.toString().substring(0, item.toString().lastIndexOf("(Faux)")).replaceAll(" ", "_")+"} = 1;\n";
						            }
						        }
						      if(rdbtnBnt.isSelected()){strPane+=BNTengineAfter;}else{strPane+=PNTengine;}
						      strPane+="marg=marginal_nodes(engine,"+stringValue3.replaceAll(" ", "_")+"); \n";
						      if(!rdbtnBnt.isSelected()){strPane+=PNTadd;}
						      strPane+="marg.T";
						      textPane.setText(strPane);
						      System.out.println("the string "+textPane.getText());
						      
						      //textPane.setText("[engine, loglik]=enter_evidence(engine,evidence);");
					}
				}
		}break;
		case "Calculer":
		{  	
			File file = new File(new File(System.getProperty("user.dir"), new File(new File(new File("src","Plugins"),"Incertain"),"Pnt").toString()),"calcul.m");
			PrintWriter writer = null;
			try {
				if(rdbtnBnt.isSelected()){writer = new PrintWriter("calcul.m", "UTF-8");}
				else{writer = new PrintWriter(file.toString(), "UTF-8");}
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				JOptionPane.showMessageDialog(new JFrame(),
					    "Erreur d'execution du scripte \nScripte non trouvée!",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);
			
			}
			writer.print(textPane.getText());
			writer.close();
			Process p = null;
			try {
				if (rdbtnBnt.isSelected()){
					System.out.println("path :"+System.getProperty("user.dir"));
					p=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile -wait output -r \"cd "+System.getProperty("user.dir")+";calcul;quit;\"");
				}else{
					
					File file1 = new File(System.getProperty("user.dir"), new File(new File(new File("src","Plugins"),"Incertain"),"Pnt").toString());
					String AddToPath= "p=genpath('"+file1.toString()+"');addpath(p);";
					p=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile -wait output -r \""+AddToPath+" cd "+file1.toString()+";calcul;quit;\"");
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(new JFrame(),
					    "Erreur d'execution du scripte \nVerifier si Matlab est correctement installée!",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("haha "+LireSousFormeString("output"));
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(new JFrame(),
					    "Erreur d'execution du scripte \nl'exécution du scripte a été interrompu !",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null,
				    
			LireSousFormeString("output").substring(LireSousFormeString("output").indexOf("ans =")),
				    "Resultat",
				    JOptionPane.PLAIN_MESSAGE);
			
		}
		
		}
	}
		
	}

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
	public void makeGraph(mxGraph graph,String str) {
	 
	    graph.getModel().beginUpdate();
	    try {
	      
	      graph.insertVertex(graph.getDefaultParent(), null, str, 10, 50, 100, 40,mxConstants.STYLE_SHAPE + "="+mxConstants.SHAPE_ELLIPSE);
	        
	        graph.getSelectionModel().clear();	
	    } finally {
	        graph.getModel().endUpdate();
	    }
	}
 
	
	HashMap <Integer, String> IdName = new HashMap<Integer, String>();
	private JButton btnValiderEtAttribuer;
	
	private JButton btnPrametres;
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
	
		

		graph = new mxGraph();
		parent = graph.getDefaultParent();
																																	 ButtonGroup group = new ButtonGroup();
//																																        JRadioButton[] buttons = new JRadioButton[]{rdbtnBnt,rdbtnPnt};
//																																         for (JRadioButton btn : buttons) {
//																																	            btn.setEnabled(false);
//																																	        }
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
																																	//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabs, BNTScripteBuild, ScripteBNT, txtCheminVersLe, btnNewButton, textPane, btnClaculer}));
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
																																	
																																	textPaneSrptbnt = new JTextPane();
																																	textPaneSrptbnt.setEditable(false);
																																	GridBagConstraints gbc_textPane = new GridBagConstraints();
																																	gbc_textPane.fill = GridBagConstraints.BOTH;
																																	gbc_textPane.insets = new Insets(0, 0, 5, 0);
																																	gbc_textPane.gridwidth = 2;
																																	gbc_textPane.gridx = 0;
																																	gbc_textPane.gridy = 1;
																																	ScripteBNT.add(textPaneSrptbnt, gbc_textPane);
																																	
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
																																	
																																	
																																			tabs.addTab("BNT/PNT Scripte build", BNTScripteBuild);
																																			
																																			Noeuxbtn = new JButton("Ajouter noeud");
																																			Noeuxbtn.addActionListener(this);
																																			
																																								final mxGraphComponent graphComponent = new mxGraphComponent(graph);
																																								new mxKeyboardHandler( graphComponent);
																																								
																																								btnValiderEtAttribuer = new JButton("Valider");
																																								btnValiderEtAttribuer.addActionListener(this);
																																								btnPrametres = new JButton("Parametres");
																																								btnPrametres.addActionListener(this);
																																								btnModifer = new JButton("Modifier");
																																								btnModifer.addActionListener(this);
//																																								setLayout(new FormLayout(new ColumnSpec[] {
//																																										ColumnSpec.decode("454px:grow"),},
//																																									new RowSpec[] {
//																																										RowSpec.decode("309px:grow"),}));
																																								btnModifer.setEnabled(false);
																																								btnPrametres.setEnabled(false);
																																								
																																								rdbtnBnt = new JRadioButton("BNT",true);
																																								
																																								rdbtnPnt = new JRadioButton("PNT");
																																								group.add(rdbtnPnt);
																																								group.add(rdbtnBnt);
																																								JRadioButton[] buttons = new JRadioButton[]{rdbtnBnt,rdbtnPnt};
																																						         if(rdbtnBnt.isSelected()){
																																						        	for (JRadioButton btn : buttons) {
																																							            btn.setEnabled(false);
																																							        }}
																																								
																																								Suppbutton = new JButton("Supprimer");
																																								Suppbutton.addActionListener(this);
																																								
																																								
																																								
																																								Suppbutton.setVerticalAlignment(SwingConstants.TOP);
																																								GroupLayout gl_BNTScripteBuild = new GroupLayout(BNTScripteBuild);
																																								gl_BNTScripteBuild.setHorizontalGroup(
																																									gl_BNTScripteBuild.createParallelGroup(Alignment.LEADING)
																																										.addGroup(gl_BNTScripteBuild.createSequentialGroup()
																																											.addGap(5)
																																											.addGroup(gl_BNTScripteBuild.createParallelGroup(Alignment.LEADING)
																																												.addComponent(Noeuxbtn)
																																												.addComponent(Suppbutton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
																																												.addComponent(btnValiderEtAttribuer, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
																																												.addComponent(btnModifer, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
																																												.addGroup(gl_BNTScripteBuild.createSequentialGroup()
																																													.addGap(1)
																																													.addComponent(rdbtnBnt)
																																													.addComponent(rdbtnPnt))
																																												.addGroup(gl_BNTScripteBuild.createSequentialGroup()
																																													.addGap(1)
																																													.addComponent(btnPrametres, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
																																											.addPreferredGap(ComponentPlacement.RELATED)
																																											.addComponent(graphComponent, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
																																											.addGap(0))
																																								);
																																								gl_BNTScripteBuild.setVerticalGroup(
																																									gl_BNTScripteBuild.createParallelGroup(Alignment.LEADING)
																																										.addGroup(gl_BNTScripteBuild.createSequentialGroup()
																																											.addGap(5)
																																											.addGroup(gl_BNTScripteBuild.createParallelGroup(Alignment.LEADING)
																																												.addComponent(graphComponent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																												.addGroup(gl_BNTScripteBuild.createSequentialGroup()
																																													.addComponent(Noeuxbtn)
																																													.addGap(6)
																																													.addComponent(Suppbutton)
																																													.addGap(6)
																																													.addComponent(btnValiderEtAttribuer)
																																													.addGap(6)
																																													.addComponent(btnModifer)
																																													.addGap(2)
																																													.addGroup(gl_BNTScripteBuild.createParallelGroup(Alignment.LEADING)
																																														.addComponent(rdbtnBnt)
																																														.addComponent(rdbtnPnt))
																																													.addGap(7)
																																													.addComponent(btnPrametres)
																																													.addGap(111))))
																																								);
																																								BNTScripteBuild.setLayout(gl_BNTScripteBuild);
																																								tabs.setEnabledAt(1, true);
																																								GroupLayout groupLayout = new GroupLayout(this);
																																								groupLayout.setHorizontalGroup(
																																									groupLayout.createParallelGroup(Alignment.LEADING)
																																										.addGroup(groupLayout.createSequentialGroup()
																																											.addComponent(tabs, GroupLayout.PREFERRED_SIZE, 449, Short.MAX_VALUE)
																																											.addContainerGap())
																																								);
																																								groupLayout.setVerticalGroup(
																																									groupLayout.createParallelGroup(Alignment.LEADING)
																																										.addComponent(tabs, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
																																								);
																																								setLayout(groupLayout);
									                                
																									
	}

	

	public void actionPerformed(ActionEvent e) {

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
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
					JOptionPane.showMessageDialog(new JFrame(),
						    "Erreur d'execution du scripte \nScripte non trouvée!",
							   "Erreur",
							   JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(new JFrame(),
					    "Erreur d'execution du scripte \nVerifier si Matlab est correctement installée!",
						   "Erreur",
						   JOptionPane.ERROR_MESSAGE);
			}
			textPaneSrptbnt.setText(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).substring(LireSousFormeString(Paths.get(chooser.getCurrentDirectory().toString(),"output").toFile().toString()).indexOf("ans =")));
//Pour effacer le fichier output de l'execution precedente
			break;
		case "Ajouter noeud":
//			JOptionPane jop = new JOptionPane();
//		    String nom = jop.showInputDialog(null, "Donner le nom du noeud !", "Nouvaux noued !", JOptionPane.QUESTION_MESSAGE);
//			if(!nom.equals("")){
//		    }
			makeGraph(graph,"Sans Nom_"+nom++);
			break;
		case "Supprimer":

	        Object[] cells1 = graph.getSelectionCells(); //here you have all cells
			for (Object c : cells1) {
			mxCell cell = (mxCell) c; //cast
			 mxCell myCell = (mxCell) ((mxGraphModel)graph.getModel()).getCell(cell.getId());
			if(myCell.isVertex()||myCell.isEdge()){
			  graph.getModel().beginUpdate();
			  try {
			    graph.getModel().remove( cell);
			 } finally {
			    graph.getModel().endUpdate();
			 }
			  }
			}
			break;
		case "Ajouter":
			System.out.println("AJOUTER  !!");
			break;
		case "Valider":
			btnModifer.setEnabled(true);
			Noeuxbtn.setEnabled(false);
			Suppbutton.setEnabled(false);
			btnValiderEtAttribuer.setEnabled(false);
			JRadioButton[] buttons = new JRadioButton[]{rdbtnBnt,rdbtnPnt};
	         if(rdbtnBnt.isSelected()){
	        	for (JRadioButton btn : buttons) {
		            btn.setEnabled(true);
		        }
	        }
	        
			btnPrametres.setEnabled(true);
			graph.setCellsEditable(false);
		    graph.setAllowDanglingEdges(false);
		    graph.setAllowLoops(false);
		    graph.setCellsDisconnectable(false);
		    graph.setDropEnabled(false);
		    graph.setSplitEnabled(false);
		    graph.setCellsBendable(false);
		    graph.setConnectableEdges(false);
		    graph.setCellsLocked(true);
		    graphComponent.setExportEnabled(false);
		    graphComponent.setConnectable(false);
			break;
		case "Modifier":
			donnéesDist.clear();
			btnValiderEtAttribuer.setEnabled(true);
			Noeuxbtn.setEnabled(true);
			btnPrametres.setEnabled(false);
			btnModifer.setEnabled(false);
			Suppbutton.setEnabled(true);
			graph.setCellsEditable(true);
			graph.setCellsDisconnectable(true);
			graph.setDropEnabled(true);
			graph.setCellsBendable(true);
		    graph.setConnectableEdges(true);
		    graph.setAllowDanglingEdges(true);
		    graph.setAllowLoops(true);
		    graph.setDropEnabled(true);
		    graph.setSplitEnabled(true);
		    graph.setCellsLocked(false);
		    graphComponent.setExportEnabled(true);
		    graphComponent.setConnectable(true);
		    buttons = new JRadioButton[]{rdbtnBnt,rdbtnPnt};
	         if(rdbtnBnt.isSelected()){
	        	for (JRadioButton btn : buttons) {
		            btn.setEnabled(false);
		        }}
			break;
		case "Parametres":
		
			modifRow=0;
			 vertex = new Vector<>();
		    edgeVec = new ArrayList<List<String>>();
		    graph.clearSelection(); 
		    graph.selectAll();
		     Object[] cells = graph.getSelectionCells();
              for (Object c : cells) { 
			    mxCell cell = (mxCell) c; 
			    if (cell.isVertex()) {
			    	System.out.println(cell.getValue().toString()+" cell.getSource(): ");
			    	vertex.add(cell.getValue().toString());
			    	}
			    }
		    data2=new Object[vertex.size()][3];
		    System.out.println("i= "+vertex.size());
		    for(int i=0;i<vertex.size();i++){data2[i][0]=vertex.get(i);System.out.println("added to data2 :"+vertex.get(i));}
		    
		    for (Object c : cells) { 
		    mxCell cell = (mxCell) c; 
		    if (cell.isEdge()) { 
		    	System.out.println(cell.getValue().toString()+" cell.getSource(): ");
		    	mxCell edge;
		    	edge=cell;
			    if (!(edge.isEdge())) {
					throw new IllegalArgumentException("Input 'edge' is not an edge.");
				}
				mxICell source = edge.getSource();
				if (source == null) {
					return;
				}
				mxGeometry sourceGeometry = source.getGeometry();
				if (sourceGeometry == null) {
					throw new IllegalArgumentException("Source vertex of input 'edge' has null position data.");
				}
				mxICell target = edge.getTarget();
				if (target == null) {
					return;
				}
				mxGeometry targetGeometry = target.getGeometry();
				if (targetGeometry == null) {
					throw new IllegalArgumentException("Target vertex of input 'edge' has no position data.");
				}

				if (source.equals(target)) {
					
					return;
				}

				Object sourceValue = source.getValue();
				Object targetValue = target.getValue();
				ArrayList <String> temp = new ArrayList <String>();
				if ((sourceValue != null) && (targetValue != null)) {
					System.out.println("Routing edge from " + sourceValue.toString() + " to " + targetValue.toString() + ".");
					temp.add(sourceValue.toString());
					temp.add(targetValue.toString());
					edgeVec.add(temp);
				}
				
			for(int i=0;i<vertex.size();i++){
				tempAffich=new Vector <Float>();
					data2[i][0]=vertex.get(i);
					int num=1;
					for(int j=0;j<edgeVec.size();j++){if(edgeVec.get(j).get(1).equals(vertex.get(i))){num++;}}
					int pow =(int) Math.pow(2.0, num);
					for(int i1=0;i1<pow;i1++){tempAffich.add((float) 0);}
					donnéesDist.put(vertex.get(i), tempAffich);
					 System.out.print(tempAffich+" size "+tempAffich.size());
					data2[i][1]=tempAffich.toString();
					data2[i][2]="modifier";
				}
		    	cell.getSource().getValue().toString();
		    // System.out.println(cell.getValue().toString());
		    	cell.getSource();
		    }else{ 
		      cell.getChildCount(); //Returns the number of child cells. (edges)
		     // cell.getChildAt(0); //Returns the child at the specified index. (target)
		    }
		    }

		    Fenetre fen = new Fenetre();
		    fen.setLocationRelativeTo(null);
		    fen.setVisible(true);
			break;
		}
		graph.setMultigraph(true);
		graph.setAllowDanglingEdges(false);
		graphComponent.setConnectable(true);
		graphComponent.setToolTips(true);
		new mxRubberband(graphComponent);
		new mxKeyboardHandler(graphComponent);
		graph.setAutoOrigin(true);
	    graph.setAutoSizeCells(true);
	   // graph.setCellsLocked(false);
	    graph.setCellsCloneable(false);
	    graphComponent.setAutoExtend(true);
	    graphComponent.setExportEnabled(true);
	    graphComponent.setCenterZoom(true);
	    mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
        mxGraphComponent graphUIComponent = new mxGraphComponent(graph);
        graphUIComponent.setEnabled(false);


			
		
		}
}
