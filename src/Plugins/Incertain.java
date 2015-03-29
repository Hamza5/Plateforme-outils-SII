package Plugins;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
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

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
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
 class ParametreProb extends JDialog implements ActionListener{
	 JTable table;
	 
			public ParametreProb()
		    {			
						
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
						  JOptionPane.showMessageDialog(null,
							    "Le format de nombre est erroné",
							    "Erreur",
							    JOptionPane.ERROR_MESSAGE);break swicth;} 
					  catch(NumberFormatException e1){
						  JOptionPane.showMessageDialog(null,
								    "Le format de nombre est erroné",
								    "Erreur",
								    JOptionPane.ERROR_MESSAGE); break swicth;
					  }
					  rowData[i]=x;
					  rowData[i+(table.getRowCount())]=y;
					  if((x+y)!=1){ JOptionPane.showMessageDialog(null,
							    "La somme de chaque ligne doit être égale à 1 !",
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
					 dispose();
			}
				
			}
}
class Fenetre extends JFrame implements ListSelectionListener,ActionListener{
		
		   private static final long serialVersionUID = 1L;
		   JList list;
		   Vector		listData;
		   JScrollPane  scrollPane_1;
		   JButton btnAjouterListe;
		   JButton btnSupprimer;
		   ButtonGroup group;
		   JRadioButton rdbtnNewRadioButton_1;
		   JRadioButton rdbtnNewRadioButton;
		   
		   final JComboBox comboBox_1;
		   JComboBox comboBox_2;
		   JComboBox<String> comboBox;
		   JTextPane textPane;
		   JButton Calculer;
		   int nbrRem=0;
		   public Fenetre(){
		      this.setLocationRelativeTo(null);
		      this.setTitle("BNT");
		      this.setSize(600, 250);
		      this.createContent();
		      
		      System.out.println(donnéesDist.keySet());
		     
		   
		      listData = new Vector();
		      panel = new JPanel();
		      
		        GroupLayout groupLayout = new GroupLayout(getContentPane());
		         groupLayout.setHorizontalGroup(
		        	groupLayout.createParallelGroup(Alignment.LEADING)
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addContainerGap()
		        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 375, Short.MAX_VALUE)
		        				.addGroup(groupLayout.createSequentialGroup()
		        					.addComponent(tableau, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
		        					.addContainerGap())))
		        );
		        groupLayout.setVerticalGroup(
		        	groupLayout.createParallelGroup(Alignment.LEADING)
		        		.addGroup(groupLayout.createSequentialGroup()
		        			.addContainerGap()
		        			.addComponent(tableau, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        			.addPreferredGap(ComponentPlacement.UNRELATED)
		        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
		        );
		        
		        JLabel lblChoisissezUneEvidence = new JLabel("Noeud observé  :");
		        
		        comboBox = new JComboBox<String>();
			      comboBox.addItem("Par défaut");
			      for(String set : donnéesDist.keySet()){ comboBox.addItem(set);}
		        
		        JLabel lblNewLabel = new JLabel("Évidence :");
		        
		         comboBox_1 = new JComboBox();
		         comboBox_2 = new JComboBox();
		         JLabel lblNewLabel_1 = new JLabel("vraisemblance :");
		        for(String set : donnéesDist.keySet()){ 
		        	comboBox_1.addItem(set);}
		        for(String set : donnéesDist.keySet()){ 
		        	comboBox_2.addItem(set);}
		        
		        btnAjouterListe = new JButton("Ajouter");
		        btnAjouterListe.addActionListener(this);
		        
		        rdbtnNewRadioButton = new JRadioButton("vraie",true);
		        rdbtnNewRadioButton.setActionCommand("vraie");
		        scrollPane_1 = new JScrollPane();
		        
		        btnSupprimer = new JButton("Supprimer");
		        btnSupprimer.addActionListener(this);
		        btnNewButton_1 = new JButton("Générer le scripte");
		        btnNewButton_1.setEnabled(false);
		        btnNewButton_1.addActionListener(this);
		        JScrollPane scrollPane = new JScrollPane();
		        
		        Calculer = new JButton("Calculer");
		        Calculer.setEnabled(false);
		        Calculer.addActionListener(this);
		        rdbtnNewRadioButton_1 = new JRadioButton("faux");
		        rdbtnNewRadioButton_1.setActionCommand("faux");
		        ButtonGroup group = new ButtonGroup();
		        group.add(rdbtnNewRadioButton);
		        group.add(rdbtnNewRadioButton_1);
		        rdbtnNewRadioButton.addActionListener(this);
		        rdbtnNewRadioButton_1.addActionListener(this);
		        
		        if(modifRow!=0){
		        	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        addWindowListener(new WindowAdapter() {
		        	public void windowClosing(WindowEvent we)
		        	  { 
		        	    String ObjButtons[] = {"Continuer","Rester"};
		        	    int PromptResult = JOptionPane.showOptionDialog(null, 
		        	        "Etes vous sur de vouloir quitter ? \nsi vous sortez tous les information saisie seront perdus !", "confirmation de sortie", 
		        	        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
		        	        ObjButtons,ObjButtons[1]);
		        	    if(PromptResult==0)
		        	    {
		        	    	dispose();         
		        	    }
		        	  }
		        	});
		        }
		        GroupLayout gl_panel = new GroupLayout(panel);
		        gl_panel.setHorizontalGroup(
		            	gl_panel.createParallelGroup(Alignment.LEADING)
		            		.addGroup(gl_panel.createSequentialGroup()
		            			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		            				.addComponent(btnSupprimer)
		            				.addComponent(btnNewButton_1)
		            				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
		            				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
		            				.addGroup(gl_panel.createSequentialGroup()
		            					.addContainerGap()
		            					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		            						.addComponent(lblChoisissezUneEvidence)
		            						.addComponent(lblNewLabel))
		            					.addPreferredGap(ComponentPlacement.UNRELATED)
		            					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		            						.addGroup(gl_panel.createSequentialGroup()
		            							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            							.addGap(18)
		            							.addComponent(rdbtnNewRadioButton)
		            							.addPreferredGap(ComponentPlacement.RELATED)
		            							.addComponent(rdbtnNewRadioButton_1)
		            							.addGap(18)
		            							.addComponent(btnAjouterListe))
		            						.addGroup(gl_panel.createSequentialGroup()
		            							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            							.addGap(18)
		            							.addComponent(lblNewLabel_1)
		            							.addGap(41)
		            							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
		            			.addGap(4))
		            		.addGroup(gl_panel.createSequentialGroup()
		            			.addComponent(Calculer)
		            			.addContainerGap())
		            );
		            gl_panel.setVerticalGroup(
		            	gl_panel.createParallelGroup(Alignment.LEADING)
		            		.addGroup(gl_panel.createSequentialGroup()
		            			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		            				.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            				.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            				.addComponent(lblNewLabel_1)
		            				.addComponent(lblChoisissezUneEvidence))
		            			.addGap(5)
		            			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		            				.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            				.addComponent(rdbtnNewRadioButton_1)
		            				.addComponent(btnAjouterListe)
		            				.addComponent(rdbtnNewRadioButton)
		            				.addComponent(lblNewLabel))
		            			.addGap(5)
		            			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
		            			.addPreferredGap(ComponentPlacement.RELATED)
		            			.addComponent(btnSupprimer)
		            			.addGap(5)
		            			.addComponent(btnNewButton_1)
		            			.addGap(7)
		            			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
		            			.addPreferredGap(ComponentPlacement.RELATED)
		            			.addComponent(Calculer)
		            			.addGap(7))
		        );
		         textPane = new JTextPane();
		         textPane.setEditable(false);
		        scrollPane.setViewportView(textPane);
		        
		        list = new JList(listData );
		        list.addListSelectionListener( this );
		        scrollPane_1.setViewportView(list);
		        panel.setLayout(gl_panel);
		        getContentPane().setLayout(groupLayout);
		 
		        pack();
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
		 
		      String  title[] = {"Noued ", "Distribution de probabilités ","modification"};
		      
		       
		      //Notre modèle d'affichage spécifique destiné à pallier
		      //les bugs d'affichage !
		      ZModel zModel = new ZModel(data2, title);
		       
		      tableau = new JTable(zModel);
		      tableau.setRowHeight(30);

		      tableau.getColumn("modification").setCellEditor(new ModifButtonEditor(new JCheckBox()));
		       
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
				ParametreProb fen = new ParametreProb();
				fen.setLocationRelativeTo(null);
			    fen.setVisible(true);
			  
			    
		      //Cette méthode permet d'avertir le tableau que les données
		      //ont été modifiées, ce qui permet une mise à jour complète du tableau
		      this.fireTableDataChanged();
		      
		   }
		    public boolean isCellEditable(int row, int col){
		     if(col==2) return true;
		     return false;
		   }
		}

	class ModifButtonEditor extends DefaultCellEditor {
	   private static final long serialVersionUID = 1L;
		protected JButton button;
		   private ModifButtonListener bListener = new ModifButtonListener();
		     public ModifButtonEditor(JCheckBox checkBox) {
		      //Par défaut, ce type d'objet travaille avec un JCheckBox
		      super(checkBox);
		       //On crée à nouveau notre bouton
		      button = new JButton();
		       button.setOpaque(true);
		       //On lui attribue un listener
		       button.addActionListener(bListener);
		   }
		 //affichege du botton
		   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		      //On définit le numéro de ligne à notre listener
		      bListener.setRow(row);
		      //On passe aussi en paramètre le tableau pour des actions potentielles
		      bListener.setTable(table);
		      //On réaffecte le libellé au bouton
		      button.setText( (value ==null) ? "" : value.toString() );
		      //On renvoie le bouton
		       return button;
		   }
		    
		   class ModifButtonListener implements ActionListener {
		         
		        private int row;
		        private JTable table;
		         
		        public void setRow(int row){this.row = row;}
		        public void setTable(JTable table){this.table = table;}
		         
		        public void actionPerformed(ActionEvent event) {
		            
		            //On affiche un message mais vous pourriez faire ce que vous voulez
		            System.out.println("coucou du bouton : "+ ((JButton)event.getSource()).getText() );
		            //On affecte un nouveau libellé à une celulle de la ligne
		            ((ZModel)table.getModel()).modifRow(this.row);
		        }
		        
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
						      strPane+="bnet = mk_bnet(dag, node_sizes);\n";
						      String stringValue = (String) comboBox.getSelectedItem();
						      if(stringValue.equals("Par défaut")){
						    	  strPane+="onodes = [];\n";  
						      }else{
						    	  strPane+="onodes = ["+stringValue.replaceAll(" ", "_")+"];\n";
						    	  }
						      for(String set :donnéesDist.keySet()){
						    	  strPane+="bnet.CPD{"+set.replaceAll(" ", "_")+"}=tabular_CPD(bnet, "+set.replaceAll(" ", "_")+","+donnéesDist.get(set).toString().replaceAll(",","")+");\n";
						      }
						      strPane+="engine=jtree_inf_engine(bnet);\n";
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
						      strPane+="[engine, loglik]=enter_evidence(engine,evidence);\n";
						      strPane+="marg=marginal_nodes(engine,"+stringValue3.replaceAll(" ", "_")+"); \n";
						      strPane+="marg.T";
						      textPane.setText(strPane);
						      System.out.println("the string "+textPane.getText());
						      
						      //textPane.setText("[engine, loglik]=enter_evidence(engine,evidence);");
					}
				}
		}break;
		case "Calculer":
		{
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("calcul.m", "UTF-8");
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
				//Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile -wait output -r calcul;quit;");
				p=Runtime.getRuntime().exec("matlab -nodesktop -nodisplay -minimize -noFigureWindows -nosplash -logfile -wait output -r \"cd "+System.getProperty("user.dir")+";calcul;quit;\"");
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
																										
																										
																												tabs.addTab("BNT Scripte build", BNTScripteBuild);
																																	GridBagLayout gbl_BNTScripteBuild = new GridBagLayout();
																																	gbl_BNTScripteBuild.columnWidths = new int[]{114, 329, 0};
																																	gbl_BNTScripteBuild.rowHeights = new int[]{23, 23, 23, 23, 166, 0};
																																	gbl_BNTScripteBuild.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
																																	gbl_BNTScripteBuild.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
																																	BNTScripteBuild.setLayout(gbl_BNTScripteBuild);
																																	btnModifer = new JButton("Modifier");
																																	btnModifer.addActionListener(this);
																																	
																																	Noeuxbtn = new JButton("Ajouter noeud");
																																	Noeuxbtn.addActionListener(this);
																																	GridBagConstraints gbc_Noeuxbtn = new GridBagConstraints();
																																	gbc_Noeuxbtn.anchor = GridBagConstraints.NORTH;
																																	gbc_Noeuxbtn.fill = GridBagConstraints.HORIZONTAL;
																																	gbc_Noeuxbtn.insets = new Insets(0, 0, 5, 5);
																																	gbc_Noeuxbtn.gridx = 0;
																																	gbc_Noeuxbtn.gridy = 0;
																																	BNTScripteBuild.add(Noeuxbtn, gbc_Noeuxbtn);
																																	
																																						final mxGraphComponent graphComponent = new mxGraphComponent(graph);
																																						new mxKeyboardHandler( graphComponent);
																																						GridBagConstraints gbc_graphComponent = new GridBagConstraints();
																																						gbc_graphComponent.fill = GridBagConstraints.BOTH;
																																						gbc_graphComponent.gridheight = 5;
																																						gbc_graphComponent.gridx = 1;
																																						gbc_graphComponent.gridy = 0;
																																						BNTScripteBuild.add(graphComponent, gbc_graphComponent);
																																	btnPrametres = new JButton("Parametres");
																																	btnPrametres.addActionListener(this);
																																	
																																	Suppbutton = new JButton("Supprimer");
																																	Suppbutton.addActionListener(this);
																																	
																																	
																																	
																																	Suppbutton.setVerticalAlignment(SwingConstants.TOP);
																																	GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
																																	gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
																																	gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
																																	gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
																																	gbc_btnNewButton_1.gridx = 0;
																																	gbc_btnNewButton_1.gridy = 1;
																																	BNTScripteBuild.add(Suppbutton, gbc_btnNewButton_1);
																																	
																																	btnValiderEtAttribuer = new JButton("Valider");
																																	btnValiderEtAttribuer.addActionListener(this);
																																	setLayout(new FormLayout(new ColumnSpec[] {
																																			ColumnSpec.decode("453px:grow"),},
																																		new RowSpec[] {
																																			RowSpec.decode("fill:309px:grow"),}));
																																	GridBagConstraints gbc_btnValiderEtAttribuer = new GridBagConstraints();
																																	gbc_btnValiderEtAttribuer.anchor = GridBagConstraints.NORTH;
																																	gbc_btnValiderEtAttribuer.fill = GridBagConstraints.HORIZONTAL;
																																	gbc_btnValiderEtAttribuer.insets = new Insets(0, 0, 5, 5);
																																	gbc_btnValiderEtAttribuer.gridx = 0;
																																	gbc_btnValiderEtAttribuer.gridy = 2;
																																	BNTScripteBuild.add(btnValiderEtAttribuer, gbc_btnValiderEtAttribuer);
																																	btnPrametres.setEnabled(false);
																																	GridBagConstraints gbc_btnPrametres = new GridBagConstraints();
																																	gbc_btnPrametres.anchor = GridBagConstraints.NORTH;
																																	gbc_btnPrametres.fill = GridBagConstraints.HORIZONTAL;
																																	gbc_btnPrametres.insets = new Insets(0, 0, 5, 5);
																																	gbc_btnPrametres.gridx = 0;
																																	gbc_btnPrametres.gridy = 3;
																																	BNTScripteBuild.add(btnPrametres, gbc_btnPrametres);
																																	btnModifer.setEnabled(false);
																																	GridBagConstraints gbc_btnModifer = new GridBagConstraints();
																																	gbc_btnModifer.anchor = GridBagConstraints.NORTH;
																																	gbc_btnModifer.fill = GridBagConstraints.HORIZONTAL;
																																	gbc_btnModifer.insets = new Insets(0, 0, 0, 5);
																																	gbc_btnModifer.gridx = 0;
																																	gbc_btnModifer.gridy = 4;
																																	BNTScripteBuild.add(btnModifer, gbc_btnModifer);
																																	tabs.setEnabledAt(1, true);
																																	add(tabs, "1, 1, fill, fill");
									                                
																									
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
		    for(int i=0;i<vertex.size();i++){data2[i][0]=vertex.get(i);}
		    
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
