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
import org.xml.sax.SAXException;
 class Agent{
	HashMap <String, Double>knowleges = new HashMap<String, Double>();
	String id;
	String name;
	}
 class AgentTrans{
	 HashMap <Set, Double>knowleges = new HashMap<Set, Double>();
	 String id;
	 String name;
 }
 class multiHash{
	 Set set;
	 double mass;
 }

public class Main{
	public static AgentTrans MultiAgSmets(AgentTrans Agt1,AgentTrans Agt2){
		  Vector <multiHash> Vec= new Vector <multiHash>();
			//Creation du Set qui contient les du Agt1 et Agt2
			 Set<Set<String>> set = new HashSet<Set<String>>();
			 for (Set number1 : Agt1.knowleges.keySet()) {
				set.add(number1);
			 }
			 for (Set number2 : Agt2.knowleges.keySet()) {
					set.add(number2);
				 }
			 HashMap <Set, Double> vide =new HashMap<Set, Double>();
			 Set<String> in = new HashSet<String>();
			 in.add("h0");
			 set.add(in);//Creer l'ensemble vide et le ajouter dans l'ensemble globale
			//Remplissage du vec avec les elements du set
			 for (Set number3 : set) {
				 multiHash mu =new multiHash();
				 mu.set=number3;
				 mu.mass=0;
				 Vec.add(mu);
				}
			 //Calcul la constante k
			 //Calcul des nouvels masses
			 double k=0;
			 for (Set number1 : Agt1.knowleges.keySet()) {
				 multiHash temp =new multiHash();
				 for (Set number2 : Agt2.knowleges.keySet()) {
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
				    			  System.out.println("Vec.get(i).set "+Vec.get(i).set);
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
	public static AgentTrans MultiAgDuboisPrade(AgentTrans Agt1,AgentTrans Agt2){
		  Vector <multiHash> Vec= new Vector <multiHash>();
			//Creation du Set qui contient les du Agt1 et Agt2
			 Set<Set<String>> set = new HashSet<Set<String>>();
			 for (Set number1 : Agt1.knowleges.keySet()) {
				set.add(number1);
			 }
			 for (Set number2 : Agt2.knowleges.keySet()) {
					set.add(number2);
				 }
			//Remplissage du vec avec les elements du set
			 for (Set number3 : set) {
				 multiHash mu =new multiHash();
				 mu.set=number3;
				 mu.mass=0;
				 Vec.add(mu);
				}
			//Calcul la constante k
			 //Calcul des nouvels masses
			 double k=0;
			 for (Set number1 : Agt1.knowleges.keySet()) {
				 multiHash temp =new multiHash();
				 multiHash tempUN =new multiHash();
	    	     for (Set number2 : Agt2.knowleges.keySet()) { 
		    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
			    	  intersection.retainAll(number2);
			    	   temp.set=intersection;
			    	   tempUN.set= new HashSet<String>(number1);
			    	   tempUN.set.addAll(number2);
			    	  temp.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
			    	  tempUN.mass=Agt1.knowleges.get(number1)*Agt2.knowleges.get(number2);
			    	  
			    	  if (intersection.size()==0){k+=temp.mass;}//Calcul la constante k
			    	  //rechercher cet ensemble dans le tableau
			    	  for(int i=0;i<Vec.size();i++){
			    		  
				    		  if(Vec.get(i).set.equals(intersection)){
				    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;
				    		  }
			    	  }
			    	  for(int i=0;i<Vec.size();i++){
			    		  if(Vec.get(i).set.equals(tempUN.set)&&intersection.size()==0){
			    			  Vec.get(i).mass=Vec.get(i).mass+temp.mass;
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
	  Vector <multiHash> Vec= new Vector <multiHash>();
		//Creation du Set qui contient les du Agt1 et Agt2
		 Set<Set<String>> set = new HashSet<Set<String>>();
		 for (Set number1 : Agt1.knowleges.keySet()) {
			set.add(number1);
		 }
		 for (Set number2 : Agt2.knowleges.keySet()) {
				set.add(number2);
			 }
		//Remplissage du vec avec les elements du set
		 for (Set number3 : set) {
			 multiHash mu =new multiHash();
			 mu.set=number3;
			 mu.mass=0;
			 Vec.add(mu);
			}
		 //Calcul la constante k
		 //Calcul des nouvels masses
		 double k=0;
		 for (Set number1 : Agt1.knowleges.keySet()) {
			 multiHash temp =new multiHash();
    	     for (Set number2 : Agt2.knowleges.keySet()) { 
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
		    	  }
	    	 }
    	 }
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
			 HashMap <Set, Double>vect = new HashMap<Set, Double>();
	          Set set1 = Ag.knowleges.entrySet();
		      Set set2 =Ag.knowleges.keySet();
		      Iterator i = set1.iterator();
		      while(i.hasNext()){
		         Map.Entry me = (Map.Entry)i.next();
		         Set<String> set =  new HashSet<String>() ;
		       //faire deviser la chaine avec l'espression reguliere "-"
		         String[] splitString = ((me.getKey().toString()).split("-"));
		         for(int i1=0;i1<splitString.length;i1++){set.add(splitString[i1]);}
		         vect.put(set,(double)me.getValue());
		      }
		      AgentTrans AgTran = new AgentTrans();
		      AgTran.knowleges=vect;
		      AgTran.id=Ag.id;
		      AgTran.name=Ag.name;
		      return AgTran;
		}
		//Methode calcul croyance  plausibilité
		public static void clalculPlBel(AgentTrans Ag,String NomFichier){
			try {
			    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document= builder.newDocument();
    		    final Element racine = document.createElement("DSTO");
    		    document.appendChild(racine);
				  // Get a set of the entries
				  HashMap <Set, Double>vect = new HashMap<Set, Double>();
				  vect=Ag.knowleges;
	             for (Set number1 : vect.keySet()) {
		    	     double som1=0;
		    	     for (Set number2 : vect.keySet()) {
		    	    	 if(number1.containsAll(number2)){som1=som1+ vect.get(number2);}
		    	     }
		    	     double som2=0;
		    	     for (Set number3 : vect.keySet()) {
			    		  Set<String> intersection = new HashSet<String>(number1); // use the copy constructor
				    	  intersection.retainAll(number3);
			    		  if(intersection.size()!=0){som2=som2+ vect.get(number3);}
		    	     }
		    	     //arrondir à 4 chiffres après la virgule
		    	     DecimalFormat df = new DecimalFormat ();
		    	     df.setMaximumFractionDigits (4);
		    	     //System.out.println(number1.toString()+" mass: "+vect.get(number1)+" bel "+som1+" Pl "+som2);
	                 //Creation du fichier xml
		        	 //  System.out.println(number1.toString()+" mass: "+vect.get(number1)+" bel "+df.format(som1)+" Pl "+df.format(som2));
		    			final Element hypo = document.createElement("Hypothese");
		    		    racine.appendChild(hypo);
		    		    hypo.setAttribute("id",number1.toString().substring(1,number1.toString().length()-1).replaceAll(", ", "-"));
		    		    //System.out.println("number "+number1.toString().length());
		    		    hypo.setAttribute("mass",df.format(Double.parseDouble(vect.get(number1).toString())));
		    		    final Element Bel = document.createElement("Bel");
		    		    final Element Pl = document.createElement("Pl");
		    		    hypo.appendChild(Bel);
		    		    hypo.appendChild(Pl);
		    		    Bel.appendChild(document.createTextNode(df.format(som1)));
		    		    Pl.appendChild(document.createTextNode(df.format(som2)));
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
	     	}
		    catch (final ParserConfigurationException e) {e.printStackTrace();}
			catch (TransformerConfigurationException e) {e.printStackTrace();}
			catch (TransformerException e) {e.printStackTrace();}
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

		    //Assuming that T[] is an array with no repeated elements ...
		    public static Set<Set<String>> powerSet(Vector input) {
		     if (input.size() == 0) {
		            Set <Set<String>>emptySet = new LinkedHashSet<>();
		            emptySet.add(new LinkedHashSet<String>());
		            return emptySet;
		        }
		     	String head = (String) input.get(0);
		        Vector newInputSet =  new Vector();
		        for (int i = 1; i < input.size(); ++i) {
		            newInputSet.add(i - 1, input.get(i));
		        }

		        Set<Set<String>> all = combine(head, powerSet(newInputSet));

		        return all;
		    }
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
      
		if(args.length!=2){Runtime.getRuntime().exit(1);}
		HashSet<Agent> hashSet = new HashSet<Agent>();
		SchemaFactory xsdf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setSchema(xsdf.newSchema(new File("validation.xsd")));
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document données = db.parse(args[0]);
		HashSet<Element> agents = new HashSet<>();
		NodeList agt = données.getElementsByTagName("Agent");
		for(int i=0; i<agt.getLength(); i++) agents.add((Element)agt.item(i));
	    boolean AgentVerifier=false;
		for(Element a : agents){
			//System.out.printf("Agent : %s%n", a.getAttribute("id"));
			if (a.getAttribute("disabled").equals("true")){continue;}//condition pour éliminer les agent desactivés 
			else{AgentVerifier=true;}
			Agent A =new Agent();
			A.id=new String(a.getAttribute("id"));
			A.name=new String(a.getAttribute("name"));
			NodeList hyp =  a.getElementsByTagName("Knowledge");
			double massVerifier=0;
			
			double omegaToAdd=0;
			for(int i=0; i<hyp.getLength(); i++){
				Element e = (Element)(hyp.item(i));
				//System.out.printf("Hypothèse %s masse = %s%n", e.getAttribute("id"), e.getAttribute("mass"));
				massVerifier+=Double.parseDouble(e.getAttribute("mass"));
				omegaToAdd+=Double.parseDouble(e.getAttribute("mass"))*Double.parseDouble(e.getAttribute("weaking"));
				A.knowleges.put(e.getAttribute("id"),Double.parseDouble(e.getAttribute("mass"))*(1-Double.parseDouble(e.getAttribute("weaking"))));//String to double
				//System.out.println(e.getAttribute("mass"));
			
				
			} 
			
			//Ajouter a Omega les Alpha d'affaiblissement des sous ensembles de Omega
//			for(int i=0; i<hyp.getLength(); i++){
//				Element e = (Element)(hyp.item(i)); 
//				if(/*e.getAttribute("id").length()==omegaDetecter*/false)
//					{A.knowleges.put(e.getAttribute("id"),Double.parseDouble(e.getAttribute("mass"))+omegaToAdd);}
//			}
			DecimalFormat df = new DecimalFormat ();
   	        df.setMaximumFractionDigits (4);
   	    
   	      //verif
   	    
            //System.out.println("massVerif "+(float)massVerifier+" round "+df.format(massVerifier));
			if( ((float)massVerifier)>1){/*System.out.println("massVerif "+massVerifier);*/Runtime.getRuntime().exit(2);}
   	     	Vector HopoGen=new Vector(); 
			//Lecture des Hypotheses integrer les Hypotheses avec des masses nulles 
			HashSet<Element> Etats = new HashSet<>();
			NodeList hyposs = données.getElementsByTagName("Etats");
			for(int i=0; i<hyposs.getLength(); i++) Etats.add((Element)hyposs.item(i));
			int omegaDetecter =0;
			for(Element b : Etats){
				NodeList Etat =  b.getElementsByTagName("Etat");
				for(int i=0; i<Etat.getLength(); i++){
					Element e1 = (Element)(Etat.item(i));
					omegaDetecter++;
					HopoGen.add(e1.getAttribute("id")); 
					//System.out.printf("Hypothèse %s masse = %s%n", e.getAttribute("id"), e.getAttribute("mass"));
//					if(!A.knowleges.containsKey(e1.getAttribute("id"))){
//						A.knowleges.put(e1.getAttribute("id"),(double) 0);//Ajouter pour chaque agent l'Hypothese non ewistante avec mass=0 
//					}
				}
			}
			
			hashSet.add(A);//Ajouter l'element dans le hashSet
			
			  Set<Set<String>> setTest = powerSet(HopoGen);
			  
			
//			  Set<String> outputSet = new LinkedHashSet<>();
//	            outputSet.add("h0");
//	            setTest.add(outputSet);
	            //System.out.println("A "+setTest);
			  if(massVerifier < 1){/*System.out.print("omegaToAdd avent "+omegaToAdd);*/omegaToAdd+=(1-massVerifier);/* System.out.println("massVerifier "+(massVerifier)+" omegaToAdd "+(float)omegaToAdd);*/}//Ajouter la difference entre la somme des masses et 1
			  for (Set elm : setTest){
				  int length=elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-").length();
				 if(!A.knowleges.containsKey(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"))&& elm.size()!=0){
					 //Ajouter pour chaque agent l'Hypothese non existante avec mass=0 
					 //System.out.println("elm "+elm);
					 if(length==omegaDetecter*2+omegaDetecter-1){A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),omegaToAdd);
					/* System.out.println("length "+length+" omegaDetecter+omegaDetecter-1 "+(omegaDetecter*2+omegaDetecter-1));*/}
					  else{A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),(double) 0);}
				 }else{
					 //System.out.println("length "+length+" omegaDetecter*2+omegaDetecter-1 "+(omegaDetecter*2+omegaDetecter-1));
					 if(length==omegaDetecter*2+omegaDetecter-1){
						 //System.out.println("Omega mass "+A.knowleges.get(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"))+"omegaToAdd"+omegaToAdd);
						 A.knowleges.put(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"),A.knowleges.get(elm.toString().substring(1,elm.toString().length()-1).replaceAll(", ", "-"))+omegaToAdd);
					  }
				 }
				 //System.out.println("Apres ajout du omega "+ A.knowleges);
			  }
			//System.out.println("Lala "+setTest);
			
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
		}
				Iterator<Agent> it = hashSet.iterator();
				if(hashSet.size()==1){clalculPlBel(Trans(it.next()),args[1]);}
				else{
					if(hashSet.size()>=1){
					AgentTrans AgTr =new AgentTrans();
					AgTr=Trans(it.next());
					while (it.hasNext()) {
						AgentTrans ag=Trans( it.next());
						if (choix==1){AgTr=MultiAg(AgTr,ag);}//Calculer le Multi Agent Dempster and Shaver
						if (choix==2){AgTr=MultiAgDuboisPrade(AgTr,ag);}//Calculer le Multi Agent Dubois Prade
						if (choix==3){AgTr=MultiAgSmets(AgTr,ag);}//Calculer le Multi Agent Smets
						 Set set = ag.knowleges.entrySet();
				         Iterator iterator = set.iterator();
				         while(iterator.hasNext()) {
					         Map.Entry mentry = (Map.Entry)iterator.next();
					        // System.out.print("key is: "+ mentry.getKey() + " & Value is: ");//Affichage pour tester
					         //System.out.println(mentry.getValue());
				        }
					 }
					clalculPlBel(AgTr,args[1]);//Appler la fonction pour calcul Bel et Pl et creer le fichier xml
				}
			        }
				
		//System.exit 0 pas d'erreur
		//System.exit 1 pas de fichier en entré 
		//System.exit 2 somme de masse > 1
		//System.exit 3 nombre d'agent ==0 
	}
}