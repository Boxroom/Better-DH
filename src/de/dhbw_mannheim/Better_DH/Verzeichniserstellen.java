package de.dhbw_mannheim.Better_DH;

import java.io.File; 
import java.io.IOException; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;

public class Verzeichniserstellen {

    public static void main(String[] args) throws IOException { 
    }
    
    
   public void Ordnererstellen(){
	   String s = System.getProperty("user.name");
	   String k = "C:/Users/"+s+"/XML";
	   if (checkDir(k))
	   {
	     }
	   else
	   {
		   String path = "C:/Users/"+s+"/"; 
	        String dirName = "XML"; 
	        File dir = new File(path + dirName); 

	        if (dir.mkdir()) { 
	            System.out.println("Ordner erstellt"); 
	        } else { 
	            System.out.println(dir + " konnte nicht erstellt werden"); 
	        } 
	   }
       
    } 
   
   private boolean checkDir(String dirName)
   {
       File stats = new File(dirName);
       if (stats.exists())    // Überprüfen, ob es den Ordner gibt
       {
           return true;
       }
       else
       {
           if (stats.mkdir())    // Erstellen des Ordners
           {
               return true;
           }
           else
           {
               return false;
           }
       }
   }
   
   public void defaultLadestand(){
	   try {
	   	 
	   		
	   		
	   		String 
	   		DozentZufriedenheit = "50",
	   		DozentenAnzahl = "5",
	   		DozentenGehalt = "5000",

	   		StudentenZufriedenheit = "50",
	   		StudentenAnzahl ="125",

	   		PartnerunternehmenAnzahl = "3",

	   		DhQualität = "60",
	   		DhAnsehen = "100",
	   		DhInventar = "1",
	   		DhEssen ="1",
	   		DhVeranstaltungen = "1",
	   		DhWerbung = "1",
	   		DhStudentenplätze = "200",
	   		DhKapital = "5000",
	   		Spielstandname = "Default",
	   		SemesterAnzahl ="1",
	   		Woche = "0"; 
	   		
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
} 

