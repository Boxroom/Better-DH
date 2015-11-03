/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;

/**
 * @author alle
 *
 */
public class Engine {
	
	public static void main(String[] args){

		           
	
		
	}

	
	
	
	
	
	static void Export(){
		
		  try {
			 
			Account getVar = new Account();
		
			String 
			DozentZufriedenheit = getVar.getDozentZufiredenheit() ,
			DozentenAnzahl = getVar.getDozentenAnzahl(),
			DozentenGehalt = getVar.getDozentenGehalt(),

			StudentenZufriedenheit = getVar.getStudentenZufriedenheit(),
			StudentenAnzahl = getVar.getStudentenAnzahl(),

			PartnerunternehmenAnzahl = getVar.getPartnerunternehmenAnzahl(),

			DhQualität = getVar.getDhQualität(),
			DhAnsehen = getVar.getDhAnsehen(),
			DhInventar = getVar.getDhInventar(),
			DhEssen = getVar.getDhEssen(),
			DhVeranstaltungen = getVar.getDhVeranstaltungen(),
			DhWerbung = getVar.getDhWerbung(),
			DhStudentenplätze = getVar.getDhStudentenplätze(),
			DhKapital = getVar.getDhKapital(),
			Spielstandname = getVar.getSpielstandname(),
			SemesterAnzahl = getVar.getSemesterAnzahl(),
			Woche = getVar.getWoche(); 
			
			//holt sich alle aktuellen Variablen aus Account

			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//Initialisiert den DocumentBuilder

			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Uni");
			doc.appendChild(rootElement);
			//Der WurzelKnoten wird initialisiert

			// Unterelement nach welchem wir suchen können
			Element Speicher = doc.createElement("Speicherstand");
			rootElement.appendChild(Speicher);


			Attr attr = doc.createAttribute("Name");
			attr.setValue(Spielstandname);
			Speicher.setAttributeNode(attr);
			// Zur Namensgebung, damit nicht jede Datei gleich aussieht


			//Erstellung aller Kind Elemente, welche an Speicherstand angefügt werden und damit alle Daten aus Account
			//wiederspiegeln
			Element DZ = doc.createElement("DozentZufriedenheit");
			DZ.appendChild(doc.createTextNode(DozentZufriedenheit));
			Speicher.appendChild(DZ);
			


			Element DA = doc.createElement("DozentenAnzahl");
			DA.appendChild(doc.createTextNode(DozentenAnzahl));
			Speicher.appendChild(DA);

			Element DG = doc.createElement("DozentenGehalt");
			DG.appendChild(doc.createTextNode(DozentenGehalt));
			Speicher.appendChild(DG);

			Element SZ = doc.createElement("StudentenZufriedenheit");
			SZ.appendChild(doc.createTextNode(StudentenZufriedenheit));
			Speicher.appendChild(SZ);
			
			Element SA = doc.createElement("StudentenAnzahl");
			SA.appendChild(doc.createTextNode(StudentenAnzahl));
			Speicher.appendChild(SA);

			Element PUA = doc.createElement("PartnerunternehmenAnzahl");
			PUA.appendChild(doc.createTextNode(PartnerunternehmenAnzahl));
			Speicher.appendChild(PUA);

			Element DhQ = doc.createElement("DhQualität");
			DhQ.appendChild(doc.createTextNode(DhQualität));
			Speicher.appendChild(DhQ);

			Element DhA = doc.createElement("DhAnsehen");
			DhA.appendChild(doc.createTextNode(DhAnsehen));
			Speicher.appendChild(DhA);

			Element DhI = doc.createElement("DhInventar");
			DhI.appendChild(doc.createTextNode(DhInventar));
			Speicher.appendChild(DhI);

			Element DhE= doc.createElement("DhEssen");
			DhE.appendChild(doc.createTextNode(DhEssen));
			Speicher.appendChild(DhE);

			Element DhV = doc.createElement("DhVeranstaltungen");
			DhV.appendChild(doc.createTextNode(DhVeranstaltungen));
			Speicher.appendChild(DhV);

			Element DhW = doc.createElement("DhWerbung");
			DhW.appendChild(doc.createTextNode(DhWerbung));
			Speicher.appendChild(DhW);
			
			Element DhSP = doc.createElement("DhStudentenplätze");
			DhSP.appendChild(doc.createTextNode(DhStudentenplätze));
			Speicher.appendChild(DhSP);

			
			Element DhK = doc.createElement("DhKapital");
			DhK.appendChild(doc.createTextNode(DhKapital));
			Speicher.appendChild(DhK);
			

			Element SEAN = doc.createElement("SemesterAnzahl");
			DhK.appendChild(doc.createTextNode(SemesterAnzahl));
			Speicher.appendChild(SEAN);
			

			Element WO = doc.createElement("Woche");
			DhK.appendChild(doc.createTextNode(Woche));
			Speicher.appendChild(WO);
			

			// Alles wird in eine XML DAtei geschrieben
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
		    String s = System.getProperty("user.name");
			StreamResult result = new StreamResult(new File("C:\\Users\\"+s+"\\XML\\"+Spielstandname+".xml"));

			

			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
			
			
			
		}
	
	public void Import(String Speicherstandname){
		
		  try { //testet ob die Datei vorhanden ist
			  
	    String s = System.getProperty("user.name");

		File fXmlFile = new File("C:/Users/"+s+"/Better-DH/"+Speicherstandname+".xml");
		//path zur Datei
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		//erstellt wieder einen Document Builder
		doc.getDocumentElement().normalize();
	

		
				
		NodeList nList = doc.getElementsByTagName("Speicherstand");
				//alle Elemente unter Speicherstand

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
					
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				//Inhalt der Knoten wird den Variablen zugeordnet

				String 
				DozentZufiredenheit =eElement.getElementsByTagName("DozentZufriedenheit").item(0).getTextContent(),
				DozentenAnzahl = eElement.getElementsByTagName("DozentenAnzahl").item(0).getTextContent(),
				DozentenGehalt = eElement.getElementsByTagName("DozentenGehalt").item(0).getTextContent(),

				StudentenZufriedenheit = eElement.getElementsByTagName("StudentenZufriedenheit").item(0).getTextContent(),
			    StudentenAnzahl = eElement.getElementsByTagName("StudentenAnzahl").item(0).getTextContent(),

				PartnerunternehmenAnzahl = eElement.getElementsByTagName("PartnerunternehmenAnzahl").item(0).getTextContent(),

				DhQualität = eElement.getElementsByTagName("DhQualität").item(0).getTextContent(),
				DhAnsehen = eElement.getElementsByTagName("DhAnsehen").item(0).getTextContent(),
				DhInventar = eElement.getElementsByTagName("DhInventar").item(0).getTextContent(),
				DhEssen = eElement.getElementsByTagName("DhEssen").item(0).getTextContent(),
				DhVeranstaltungen = eElement.getElementsByTagName("DhVeranstaltungen").item(0).getTextContent(),
				DhWerbung = eElement.getElementsByTagName("DhWerbung").item(0).getTextContent(),
				DhStudentenplätze = eElement.getElementsByTagName("DhStudentenplätze").item(0).getTextContent(),
				DhKapital = eElement.getElementsByTagName("DhKapital").item(0).getTextContent(),
				
				Woche = eElement.getElementsByTagName("Woche").item(0).getTextContent(),
				SemesterAnzahl = eElement.getElementsByTagName("SemesterAnzahl").item(0).getTextContent();
		
				
				//AccountVariablen werden gesetzt
				Account setVar = new Account();
				setVar.setDozentZufiredenheit(DozentZufiredenheit);
				setVar.setDozentenAnzahl(DozentenAnzahl);
				setVar.setDozentenGehalt(DozentenGehalt);
				setVar.setStudentenZufriedenheit(StudentenZufriedenheit);
				setVar.setStudentenAnzahl(StudentenAnzahl);
				
				setVar.setPartnerunternehmenAnzahl(PartnerunternehmenAnzahl);
				setVar.setDhQualität(DhQualität);
				setVar.setDhAnsehen(DhAnsehen);
				setVar.setDhInventar(DhInventar);
				setVar.setDhEssen(DhEssen);
				setVar.setDhVeranstaltungen(DhVeranstaltungen);
				setVar.setDhWerbung(DhWerbung);
				setVar.setDhStudentenplätze(DhStudentenplätze);
				setVar.setDhKapital(DhKapital);
				setVar.setSemesterAnzahl(SemesterAnzahl);
				setVar.setWoche(Woche);
				
				
			}
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		  
	  }

	public boolean hasPlayer() {
		
		return true;
		//da Default immer existieren wird
	}
	
	
	//array Liste, die alle Namen der gespeicherten Dateien wiedergibt
	public ArrayList<String> getAllNames(){
		Engine t = new Engine();
		   ArrayList arr = new ArrayList();
		arr=t.SaveNames();
		return arr;
	}
	
	//Namen der gespeicherten DAteien werden gelesen und gespeichert
		public ArrayList<String> SaveNames()    {
		    ArrayList<String> Names = new ArrayList<String>();
			
		    String s = System.getProperty("user.name");
			File maindir = new File("C:/Users/"+s+"/XML");
			File files[] = maindir.listFiles();
			
			for (int i = 0; i < files.length; i++) {
				String Name =  files[i].getName();
				String Namegekurzt;

				Namegekurzt = Name.substring(0, Name.length()-4);
				Names.add(Namegekurzt);
			}
		    
		    return(Names);
		 }
		
	
		public void loadPlayer(String name){
			Import(name);
			
		}

	

}

