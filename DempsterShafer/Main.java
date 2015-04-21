import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

 class Agent{
	HashMap <String, Double>knowleges = new HashMap<String, Double>();
	String id;
	String name;
	}
 class AgentTrans{
	 HashMap <Set <String>, Double>knowleges = new HashMap<Set<String>, Double>();
	 String id;
	 String name;
 }
 class multiHash{
	 Set <String>set;
	 double mass;
 }

public class Main{
	private static double k=0;
	public static AgentTrans MultiAgYager (AgentTrans Agt1,AgentTrans Agt2,int count){

		Vector <multiHash> Vec= new Vector <multiHash>();
		//Creation du Set qui contient les du Agt1 et Agt2
		 Set<Set<String>> set = new HashSet<Set<String>>();
		 for (Set<String> number1 : Agt1.knowleges.keySet()) {
			set.add(number1);
		 }
		 for (Set<String> number2 : Agt2.knowleges.keySet()) {
				set.add(number2);
			 }
		 Set<String> in = new HashSet<String>();
		 in.add("h0");
		 set.add(in);//Creer l'ensemble vide et le ajouter dans l'ensemble globale
		//Remplissage du vec avec les elements du set
		 int omegaDetecter=0;
		 for (Set<String>number3 : set) {
			 multiHash mu =new multiHash();
			 mu.set=number3;
			 mu.mass=0;
			 Vec.add(mu);
			 if(omegaDetecter<number3.toString().length()){omegaDetecter=number3.toString().length();}
			}
		 //Calcul la constante k
		 //Calcul des nouvels masses
		 //double k=0;
		 laGrandeboucle:for (Set <String> number1 : Agt1.knowleges.keySet()) {
			 for(int i=0;i<Vec.size();i++){if(Agt1.knowleges.get(number1)==0) {continue laGrandeboucle;}}	
			 multiHash temp =new multiHash();
			 for (Set<String> number2 : Agt2.knowleges.keySet()) {
	    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
		    	  intersection.retainAll(number2);
		    	   temp.set=intersection;
		    	 temp.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
		    	  if (intersection.size()==0){k+=temp.mass;}//Calcul la constante k
		    	  //rechercher cet ensemble dans le tableau
		    	  loop: for(int i=0;i<Vec.size();i++){
			    		  if(Vec.get(i).set.equals(intersection)){
			    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;break loop;
			    		  }

		    	  }
		     }
		 }
		//System.out.println("k = "+k);
		 AgentTrans temp = new AgentTrans();
		for(int i=0;i<Vec.size();i++){
			if(Vec.get(i).set.size()!=0){//pour eviter l'ensemble vide
				if((Vec.get(i).set.toString().length()==omegaDetecter)&&count==1){
					
					Vec.get(i).mass= Vec.get(i).mass+k;
				}
			temp.knowleges.put(Vec.get(i).set,Vec.get(i).mass );
			}
		}
		
//		 Set<String> kk = new HashSet<String>();
//		 kk.add("K");
//		 if(!temp.knowleges.get(kk).equals(0)){};
//		 temp.knowleges.put(kk, (double)0);
		//System.out.println("yaya "+temp.knowleges.get("[h0]"));
		return temp;//Retouner la collection resultat
}
	public static AgentTrans MultiAgSmets(AgentTrans Agt1,AgentTrans Agt2){
		  Vector <multiHash> Vec= new Vector <multiHash>();
			//Creation du Set qui contient les du Agt1 et Agt2
			 Set<Set<String>> set = new HashSet<Set<String>>();
			 for (Set<String> number1 : Agt1.knowleges.keySet()) {
				set.add(number1);
			 }
			 for (Set<String> number2 : Agt2.knowleges.keySet()) {
					set.add(number2);
				 }
			 Set<String> in = new HashSet<String>();
			 in.add("h0");
			 set.add(in);//Creer l'ensemble vide et le ajouter dans l'ensemble globale
			//Remplissage du vec avec les elements du set
			 for (Set<String> number3 : set) {
				 multiHash mu =new multiHash();
				 mu.set=number3;
				 mu.mass=0;
				 Vec.add(mu);
				}
			 //Calcul la constante k
			 //Calcul des nouvels masses
			 double k=0;
			 laGrandeboucle	:for (Set<String> number1 : Agt1.knowleges.keySet()) {
				 for(int i=0;i<Vec.size();i++){if(Agt1.knowleges.get(number1)==0) {continue laGrandeboucle;}}				 
				 multiHash temp =new multiHash();
				 for (Set <String> number2 : Agt2.knowleges.keySet()) {
					// System.out.println("yo");
		    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
			    	  intersection.retainAll(number2);
			    	   temp.set=intersection;
			    	 temp.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
			    	  if (intersection.size()==0){k+=temp.mass;}//Calcul la constante k
			    	  //rechercher cet ensemble dans le tableau
			    	  for(int i=0;i<Vec.size();i++){
				    		  if(Vec.get(i).set.equals(intersection)){
				    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;
				    		  }
				    		  //System.out.println("Vec.get(i).set "+Vec.get(i).set);
				    		  if(Vec.get(i).set.toString().equals("[h0]")){
				    			  Vec.get(i).mass=k;
				    			  //System.out.println("Vec.get(i).set "+Vec.get(i).set);
				    		  }
			    	  }
			     }
			 }
			//System.out.println("k = "+k);
			 AgentTrans temp = new AgentTrans();
			for(int i=0;i<Vec.size();i++){
				if(Vec.get(i).set.size()!=0){//pour eviter l'ensemble vide
				 temp.knowleges.put(Vec.get(i).set,Vec.get(i).mass );
				}
			}
			return temp;//Retouner la collection resultat
	}
	public static AgentTrans MultiAgDuboisPrade(AgentTrans Agt1,AgentTrans Agt2){
		  Vector <multiHash> Vec= new Vector <multiHash>();
			//Creation du Set qui contient les du Agt1 et Agt2
			 Set<Set<String>> set = new HashSet<Set<String>>();
			 for (Set<String> number1 : Agt1.knowleges.keySet()){
				set.add(number1);
			 }
			 for (Set <String> number2 : Agt2.knowleges.keySet()){
					set.add(number2);
				 }
			//Remplissage du vec avec les elements du set
			 for (Set<String> number3 : set) {
				 multiHash mu =new multiHash();
				 mu.set=number3;
				 mu.mass=0;
				 Vec.add(mu);
				}

			 //Calcul des nouvels masses

			 laGrandeboucle:for (Set<String> number1 : Agt1.knowleges.keySet()) {
				 for(int i=0;i<Vec.size();i++){if(Agt1.knowleges.get(number1)==0) {continue laGrandeboucle;}}
				 multiHash temp =new multiHash();
				 multiHash tempUN =new multiHash();
	    	     for (Set<String> number2 : Agt2.knowleges.keySet()) { 
		    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
			    	  intersection.retainAll(number2);
			    	  temp.set=intersection;
			    	  tempUN.set= new HashSet<String>(number1);
			    	  tempUN.set.addAll(number2);
			    	  temp.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
			    	  tempUN.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
			    	  
			    	  
			    	  //rechercher cet ensemble dans le tableau
			    	  loop1 :for(int i=0;i<Vec.size();i++){
			    		  
				    		  if(Vec.get(i).set.equals(intersection)) {
				    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;break loop1;
				    		  }
			    	  }
			    	  loop2: for(int i=0;i<Vec.size();i++){
			    		  if(Vec.get(i).set.equals(tempUN.set)&&intersection.size()==0){
			    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;break loop2;
			    		  }
		    	     }
		    	 }
	    	 }
			AgentTrans temp = new AgentTrans();
			for(int i=0;i<Vec.size();i++){
			
				if(Vec.get(i).set.size()!=0){//pour eviter l'ensemble vide
				 temp.knowleges.put(Vec.get(i).set,Vec.get(i).mass );
				}
			}
			return temp;//Retouner la collection resultat
	}
	

	//Methode pour caluler le Multi Agent
	public static AgentTrans MultiAg(AgentTrans Agt1,AgentTrans Agt2){
	//	System.out.println("Agt1 "+Agt1.knowleges);
	//	System.out.println("Agt2 "+Agt2.knowleges);
	  Vector <multiHash> Vec= new Vector <multiHash>();
		//Creation du Set qui contient les du Agt1 et Agt2
		 Set<Set<String>> set = new HashSet<Set<String>>();
		 for (Set<String> number1 : Agt1.knowleges.keySet()) {
			set.add(number1);
		 }
		 for (Set<String> number2 : Agt2.knowleges.keySet()) {
				set.add(number2);
			 }
		//Remplissage du vec avec les elements du set
		 for (Set <String>number3 : set) {
			 multiHash mu =new multiHash();
			 mu.set=number3;
			 mu.mass=0;
			 Vec.add(mu);
			}
		 //Calcul la constante k
		 //Calcul des nouvels masses
		 double k=0;
		 laGrandeboucle:for (Set <String>number1 : Agt1.knowleges.keySet()) {
			 for(int i=0;i<Vec.size();i++){if(Agt1.knowleges.get(number1)==0) {continue laGrandeboucle;}}
	//System.out.println("number1 "+number1);
		 multiHash temp =new multiHash();
    	     for (Set<String> number2 : Agt2.knowleges.keySet()) { 

	    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
		    	  intersection.retainAll(number2);
		    	  temp.set=intersection;
		    	  temp.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
		    	  if (intersection.size()==0){k+=temp.mass;}//Calcul la constante k
		    	  //rechercher cet ensemble dans le tableau
		    	  loop:for(int i=0;i<Vec.size();i++){
			    		  if(Vec.get(i).set.equals(intersection)){
			    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;break loop;
			    		  }
		    	  }
	    	 }
    	 }
		// System.out.println("k "+k);
		k=1-k;
        //Calcul la nouvel masse
		
		AgentTrans temp = new AgentTrans();
		for(int i=0;i<Vec.size();i++){
			Vec.get(i).mass*=1/k;//deviser les masses des ensembles sur la constante k
			if(Vec.get(i).set.size()!=0){//pour eviter l'ensemble vide
			 temp.knowleges.put(Vec.get(i).set,Vec.get(i).mass );
			}
		}
		return temp;//Retouner la collection resultat
	}
        //Methode transforamtion de format <String, Double> -> <Set, Double>
		public static AgentTrans Trans(Agent Ag){
			HashMap <Set<String>, Double>vect = new HashMap<Set<String>, Double>();
	          Set set1 = Ag.knowleges.entrySet();
	          Iterator i = set1.iterator();
		      while(i.hasNext()){
		         Map.Entry me = (Map.Entry)i.next();
		         Set<String> set =  new HashSet<String>();
		       //faire deviser la chaine avec l'espression reguliere "-"
		         String[] splitString = ((me.getKey().toString()).split("-"));
		         for(int i1=0;i1<splitString.length;i1++){set.add(splitString[i1]);}
		         vect.put(set,(double)me.getValue());
			     //System.out.println("Trans dans la fonction apres "+set+" = "+me.getValue());	         
		      }
		      AgentTrans AgTran = new AgentTrans();
		      AgTran.knowleges=vect;
		      AgTran.id=Ag.id;
		      AgTran.name=Ag.name;
		      //System.out.println("Trans dans la fonction apres "+AgTran.knowleges);
		      return AgTran;
		}
		//Methode calcul croyance  plausibilité
		public static void clalculPlBel(AgentTrans Ag,Document données,String NomFichier){
			try {
				//System.out.printf("AG BL PL "+Ag.knowleges);
			    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document= builder.newDocument();
    		    final Element racine = document.createElement("DSTO");

//    		    SchemaFactory xsdf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//    			dbf.setSchema(xsdf.newSchema(new File("validation.xsd")));
//    			DocumentBuilder db = dbf.newDocumentBuilder();
//    			Document données = db.parse(NomFichierEntrée);
    			document.appendChild(racine);
			     // Get a set of the entries
			    HashMap <Set<String>, Double>vect = new HashMap<Set<String>, Double>();
			    vect=Ag.knowleges;
			    final Element Titre = document.createElement("Title");
    		    racine.appendChild(Titre);
    	     	final Element Description = document.createElement("Description");
    		    racine.appendChild(Description);	
    		    final Element Method = document.createElement("Method");
    		    racine.appendChild(Method);
     		    final Element Decision = document.createElement("Decision");
    		    racine.appendChild(Decision);
    		    final Element Etats = document.createElement("Etats");
    		    racine.appendChild(Etats);
    		    final Element Hypotheses = document.createElement("Hypotheses");
    		    racine.appendChild(Hypotheses);
  
    		    
    		    final NodeList racineNoeuds = données.getChildNodes();
    		    final Element ElementAEcrire = (Element) racineNoeuds.item(0);
    		    Element nom = (Element) ElementAEcrire.getElementsByTagName("Title").item(0);
    		    Titre.appendChild(document.createTextNode(nom.getTextContent()));
    		    nom = (Element) ElementAEcrire.getElementsByTagName("Description").item(0);
    		    Description.appendChild(document.createTextNode(nom.getTextContent()));
    		    nom = (Element) ElementAEcrire.getElementsByTagName("Method").item(0);
    		    Method.appendChild(document.createTextNode(nom.getTextContent()));
    		    Element DecisionGrab = (Element) ElementAEcrire.getElementsByTagName("Decision").item(0);
                Decision.appendChild(document.createTextNode(DecisionGrab.getTextContent()));
     		   
    		    String DecisionStr = new String(DecisionGrab.getTextContent());
    		    
    		    NodeList agt = données.getElementsByTagName("Etat");
    		    double maxDecision1=0;
    		    double maxDecision2=0;
    		    int choix =0; 
    		    Set <String> setDeci = null;
	            // System.out.println("DecisionStr "+DecisionStr+" "+choix+" Optimiste "+" Pessimsite " +" Pignistique" ); 
    		    switch (DecisionStr){
    		    case "Optimiste" :choix=0;break;
    		    case "Pessimiste" :choix=1;break;
    		    case "Pignistique" :choix=2;break;
    		    }

    		    for(int i=0; i<agt.getLength(); i++) {
    				Element e = (Element)(agt.item(i));
    				final Element Etat = document.createElement("Etat");
    				Etats.appendChild(Etat);
    				Etat.setAttribute("id",e.getAttribute("id"));
    				Etat.setAttribute("title",e.getAttribute("title"));}

    		     HashMap <String, Double> vectSingleton = new HashMap<String, Double>();
	             for (Set <String> number1 : vect.keySet()) {
		    	     double som1=0;
		    	     for (Set <String>number2 : vect.keySet()) {
		    	    	 if(number1.containsAll(number2)){som1=som1+ vect.get(number2);}
		    	     }
		    	     double som2=0;
		    	     double som2Decision =0;
		    	     for (Set<String> number3 : vect.keySet()) {
			    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
				    	  intersection.retainAll(number3);
			    		  if(intersection.size()!=0){som2=som2+ vect.get(number3);
			    		  if(choix==2){som2Decision+=vect.get(number3)/number3.size();}
			    			  }
			    		  
		    	     }
		    	     //arrondir à 4 chiffres après la virgule
		    	     DecimalFormat df = new DecimalFormat ();
		    	     df.setMaximumFractionDigits (10);
		    	   
	                 //Creation du fichier xml

		    		    final Element hypo = document.createElement("Hypothese");
		    		    Hypotheses.appendChild(hypo);
		    		    hypo.setAttribute("id",number1.toString().substring(1,number1.toString().length()-1).replaceAll(", ", "-"));
		    		    hypo.setAttribute("mass",df.format(Double.parseDouble(vect.get(number1).toString())).toString().replaceAll(",","."));
		    		    final Element Bel = document.createElement("Bel");
		    		    final Element Pl = document.createElement("Pl");
		    		    hypo.appendChild(Bel);
		    		    hypo.appendChild(Pl);
		    		    Bel.appendChild(document.createTextNode(df.format(som1).toString().replaceAll(",",".")));
		    		    Pl.appendChild(document.createTextNode(df.format(som2).toString().replaceAll(",",".")));
		    		    if(choix==0&&(number1.size()==1)&&(maxDecision2<som2)){
		    		    	maxDecision2=som2;
		    		    	setDeci=number1;}
		    		    if(choix==1&&(number1.size()==1)&&(maxDecision1<som1)){
		    		    	maxDecision1=som1;
		    		    	setDeci=number1;}
		    		    if((choix==2)&&(number1.size()==1)){vectSingleton.put(number1.toString().substring(1,number1.toString().length()-1),som2Decision );}
		    		    
		    		}
	             if(choix==2){
	            	 double max=0 ;
	            	 Set <String> temp = new HashSet<String>();
	    		    for(String setMax : vectSingleton.keySet()) {
	    		    	if(max<vectSingleton.get(setMax)){
	    		    		max=vectSingleton.get(setMax);
	    		    		temp.clear();
	    		    		//temp=null;
	    		    		temp.add(setMax);
	    		    		setDeci=temp;}
	    				}
	    		     }

	             	final Element ResultatDecision = document.createElement("ResultatDecision");
	    		    racine.appendChild(ResultatDecision);
	    		    ResultatDecision.appendChild(document.createTextNode(setDeci.toString().substring(1,setDeci.toString().length()-1)));
	             	final DOMSource source = new DOMSource(document);
	    		    final TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         	    final Transformer transformer = transformerFactory.newTransformer();
	         	    final StreamResult sortie = new StreamResult(new File(NomFichier));
         		 //prologue
	         	    transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
	         	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	         	    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
	         	    		
	         	 //formatage
	         	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	         	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

	         	    //sortie
	         	    transformer.transform(source, sortie);
	     	}
		    catch (final ParserConfigurationException e) {
		    Runtime.getRuntime().exit(6);
           System.err.println("Erreur d'analyse du fichier XML (ParserConfigurationException)");}
			catch (TransformerConfigurationException e) {
				System.err.println("Erreur de configuration du ficheir XML" +e.getMessage());
						}
			catch (TransformerException e) {
				System.err.println("Erreur est produite pendant le processus de transformation du ficher XML :"+e.getMessage());
				e.printStackTrace();}
		  }


		    private static   Set<Set<String>> combine(String head, Set<Set<String>> set) {
		        Set<Set<String>> all = new LinkedHashSet<>();

		        for (Set<String> currentSet : set) {
		            Set<String> outputSet = new LinkedHashSet<>();
		            outputSet.add(head);
		            outputSet.addAll(currentSet);
		            all.add(outputSet);
		        }
		        all.addAll(set);        
		        return all;
		    }

		    public static Set<Set<String>> powerSet(Vector<String> input) {
		     if (input.size() == 0) {
		            Set <Set<String>>emptySet = new LinkedHashSet<>();
		            emptySet.add(new LinkedHashSet<String>());
		            return emptySet;
		        }
		     	String head = (String) input.get(0);
		        Vector <String>newInputSet =  new Vector<String>();
		        for (int i = 1; i < input.size(); ++i) {
		            newInputSet.add(i - 1, (String) input.get(i));
		        }

		        Set<Set<String>> all = combine(head, powerSet(newInputSet));

		        return all;
		    }
		    
	
	public static void main(String[] args) {
		// Show the usage to then user
		switch(args.length) {
			case 2:
				File in = new File(args[0]);
				if(!in.isFile()){
					System.err.println("Le fichier "+in.getAbsolutePath()+" est introuvable");
					System.exit(1);
				}
				else if(!in.canRead()){
					System.err.println("Impossible de lire le fichier "+in.getAbsolutePath());
					System.exit(1);
				}
				break;
			default:
				System.err.println("Usage : Main <file>.dsti.xml <file>.dsto.xml");
				System.exit(1);
		}
		HashSet<Agent> hashSet = new HashSet<Agent>();
		SchemaFactory xsdf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		Document données=null; 
		      

	     
		try {
		dbf.setSchema(xsdf.newSchema(new File("validation.xsd")));
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		
		db.setErrorHandler(new ErrorHandler() {
		    @Override
		    public void error(SAXParseException arg0) throws SAXException {
		    	 System.err.println("Erreur d'analyse du fichier XML (SAXException)");
		        	Runtime.getRuntime().exit(5);
		        throw arg0;
		    }
		 
		    @Override
		    public void fatalError(SAXParseException arg0) throws SAXException {
		    	 System.err.println("Erreur d'analyse du fichier XML (SAXException)");
		        	Runtime.getRuntime().exit(5);
		        throw arg0;                 
		    }
		 
		    @Override
		    public void warning(SAXParseException arg0) throws SAXException {
		    	 System.err.println("Erreur d'analyse du fichier XML (SAXException)");
		        	Runtime.getRuntime().exit(5);
		        throw arg0;                 
		    }
		});
		données = db.parse(args[0]);
	 
		 }catch (ParserConfigurationException e) {
			 System.err.println("Erreur d'analyse du fichier XML (ParserConfigurationException)");
	            Runtime.getRuntime().exit(6);
	         } catch (SAXException e) {
	        	 System.err.println("Erreur d'analysedu fichier XML (SAXException)");
	        	Runtime.getRuntime().exit(5);
	        } catch (IOException e) {
	        	System.err.println("Général E/S exception: " + e.getMessage());
	        	Runtime.getRuntime().exit(4);
	         }
	
		HashSet<Element> agents = new HashSet<>();
		NodeList agt = données.getElementsByTagName("Agent");
		for(int i=0; i<agt.getLength(); i++) agents.add((Element)agt.item(i));
	    boolean AgentVerifier=false;
	     	Vector <String>HopoGen=new Vector<String>(); 
		//Lecture des Hypotheses integrer les Hypotheses avec des masses nulles 
		HashSet<Element> Etats = new HashSet<>();
		NodeList hyposs = données.getElementsByTagName("Etats");
		for(int i=0; i<hyposs.getLength(); i++) Etats.add((Element)hyposs.item(i));
		int omegaDetecter =0;
		int omegaLength =0;
		for(Element b : Etats){
			NodeList Etat =  b.getElementsByTagName("Etat");
			for(int i=0; i<Etat.getLength(); i++){
				Element e1 = (Element)(Etat.item(i));
				omegaDetecter++;
				HopoGen.add(e1.getAttribute("id")); 
				omegaLength+=e1.getAttribute("id").length();
				//System.out.printf("Hypothèse %s masse = %s%n", e.getAttribute("id"), e.getAttribute("mass"));
//				if(!A.knowleges.containsKey(e1.getAttribute("id"))){
//					A.knowleges.put(e1.getAttribute("id"),(double) 0);//Ajouter pour chaque agent l'Hypothese non ewistante avec mass=0 
//				}
			}
		}
			Set<Set<String>> setTest = powerSet(HopoGen);
		    Set<String> outputSet = new LinkedHashSet<>();
            outputSet.add("h0");
            setTest.add(outputSet);
            
		for(Element a : agents){
			//System.out.printf("Agent : %s%n", a.getAttribute("id"));
			if (a.getAttribute("disabled").equals("true")){continue;}//condition pour éliminer les agent desactivés 
			else{AgentVerifier=true;}
			Agent A =new Agent();
			A.id=new String(a.getAttribute("id"));
			A.name=new String(a.getAttribute("name"));
			NodeList hyp =  a.getElementsByTagName("Knowledge");
			double massVerifier=0;
			double reliability= Double.parseDouble(a.getAttribute("reliability"));
			double omegaToAdd=0;
			for(int i=0; i<hyp.getLength(); i++){
				Element e = (Element)(hyp.item(i));
				//System.out.printf("Hypothèse %s masse = %s%n", e.getAttribute("id"), e.getAttribute("mass"));
				
				massVerifier+=Double.parseDouble(e.getAttribute("mass"));
				//omegaToAdd+=Double.parseDouble(e.getAttribute("mass"))*Double.parseDouble(e.getAttribute("weaking"));///*/commentée apres changement de fiabilitée
				if(e.getAttribute("id").length()!=(omegaLength+omegaDetecter-1)){
				A.knowleges.put(e.getAttribute("id"),Double.parseDouble(e.getAttribute("mass"))*reliability);//String to double
				//A.knowleges.put(e.getAttribute("id"),Double.parseDouble(e.getAttribute("mass"))*(1-Double.parseDouble(e.getAttribute("weaking"))));//String to double///*/commentée apres changement de fiabilitée
				}
				}
			//System.out.println("omaga to Add "+omegaToAdd);
			//Ajouter a Omega les Alpha d'affaiblissement des sous ensembles de Omega
//			for(int i=0; i<hyp.getLength(); i++){
//				Element e = (Element)(hyp.item(i)); 
//				if(/*e.getAttribute("id").length()==omegaDetecter*/false)
//					{A.knowleges.put(e.getAttribute("id"),Double.parseDouble(e.getAttribute("mass"))+omegaToAdd);}
//			}
			DecimalFormat df = new DecimalFormat ();
   	        df.setMaximumFractionDigits (10);
   	    
   	      //verif
   	    
            //System.out.println("massVerif "+(float)massVerifier+" round "+df.format(massVerifier));
			if( ((float)massVerifier)>1){/*System.out.println("massVerif "+massVerifier);*/Runtime.getRuntime().exit(2);}

	            //System.out.println("A "+setTest);
			  if(massVerifier < 1){/*System.out.print("omegaToAdd avent "+omegaToAdd);*/omegaToAdd+=(1-massVerifier);/* System.out.println("massVerifier "+(massVerifier)+" omegaToAdd "+(float)omegaToAdd);*/}//Ajouter la difference entre la somme des masses et 1
		         
			  for (Set <String> elm : setTest){
				  Set<String> set =  new HashSet<String>();
				  String[] splitString = ((elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-").split("-")));
			         for(int i1=0;i1<splitString.length;i1++){set.add(splitString[i1]);}
			         Agent AgentTempTest=new Agent();
			         AgentTempTest.knowleges=A.knowleges;
			         AgentTrans AgentTransTempTest =new AgentTrans();
			         AgentTransTempTest =Trans(AgentTempTest);
			         int length=elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-").length();
			         
				 // System.out.println("element a rechercher "+elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"));
				 //System.out.println("A.knowleges "+A.knowleges);
				 //System.out.println("tester l'existance "+elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"));
				  //if(!A.knowleges.containsKey(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"))&& elm.size()!=0)
				 if(!AgentTransTempTest.knowleges.containsKey(set)&& elm.size()!=0){
					 //Ajouter pour chaque agent l'Hypothese non existante avec mass=0 
					 //System.out.println("elm "+elm);
					 //System.out.println("element  "+elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-")+" non trouvé \n");
					  
					  if(omegaDetecter==elm.size()){
						  
						 //System.out.println("ensemble "+elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-")+" Avant "+ A.knowleges.get(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-")));
						 A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),1-reliability*(1-omegaToAdd));
						
						 //System.out.println("length "+length+" omegaDetecter+omegaDetecter-1 "+(omegaDetecter*2+omegaDetecter-1)+" omega "+elm+"="+A.knowleges.get(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-")));
						 
					  }
					  else{A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),(double) 0);
					  /* System.out.println("Omega ne faut qu'il soit ici"+elm);*/}
				 }else{
					 //System.out.println("length "+length+" omegaDetecter*2+omegaDetecter-1 "+(omegaDetecter*2+omegaDetecter-1));
					 String omegaString = null;

					 if(omegaDetecter==elm.size()){
						 
//						  System.out.println("omegaDetecter "+omegaDetecter+" elm.size "+elm.size());
//						  System.out.println("length "+length+" omegaDetecter*2+omegaDetecter-1 "+(omegaDetecter*2+omegaDetecter-1));
						  
						  for(String elm2 : A.knowleges.keySet()){if(elm2.length()==length){omegaString=new String(elm2);}}
			//			  System.out.println("omegaString "+omegaString);
						 //for(int i=0;i>A.knowleges.keySet().size();i++){if()}
						 //System.out.println("Omega mass "+A.knowleges+"omegaToAdd"+omegaToAdd);
			             
						  A.knowleges.put(omegaString,1-reliability*(1-A.knowleges.get(omegaString)+omegaToAdd));
						 // System.out.println("omega poids: "+(1-A.knowleges.get(omegaString)+omegaToAdd));
			            //A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),(double) 0);
					  }
				 }
				//System.out.println("Apres ajout du omega "+ A.knowleges);
			  }
			//System.out.println("Lala "+setTest);
			  //System.out.println("Apres ajout du omega 2222 "+ A.knowleges);
			  hashSet.add(A);//Ajouter l'element dans le hashSet
		}
		
		if(AgentVerifier==false){Runtime.getRuntime().exit(3);}
		int choix =0;
		final Element methode = (Element) données.getElementsByTagName("Method").item(0);
		switch (methode.getTextContent()){
		case "Dempster-Shafer":
			choix = 1;break; 
		case "Dubois-Prade":
			choix = 2;break; 
		case "Smets":
			choix = 3;break; 
		case "Yager":
			choix = 4;break; 
		}
				Iterator<Agent> it = hashSet.iterator();
				if(hashSet.size()==1){
					clalculPlBel(Trans(it.next()),données,args[1]);
					 }
				else{
					if(hashSet.size()>=1){
						int count=hashSet.size();
					AgentTrans AgTr =new AgentTrans();
					AgTr=Trans(it.next());
					while (it.hasNext()) {
						count--;
						AgentTrans ag=Trans(it.next());
						if (choix==1){AgTr=MultiAg(AgTr,ag);}//Calculer le Multi Agent Dempster and Shaver
						if (choix==2){AgTr=MultiAgDuboisPrade(AgTr,ag);}//Calculer le Multi Agent Dubois Prade
						if (choix==3){AgTr=MultiAgSmets(AgTr,ag);}//Calculer le Multi Agent Smets
						if (choix==4){AgTr=MultiAgYager(AgTr,ag,count);}//Calculer le Multi Agent Yager
						
					 }
					clalculPlBel(AgTr,données,args[1]);//Appler la fonction pour calcul Bel et Pl et creer le fichier xml
				}
			        }
		//System.exit 0 pas d'erreur
		//System.exit 1 pas de fichier en entré 
		//System.exit 2 somme de masse > 1
		//System.exit 3 nombre d'agent ==0 
		//System.exit 4 IOException thrown	
		//System.exit 5 Parsing XML failed
		//System.exit 6 Parser not configured
	}
}