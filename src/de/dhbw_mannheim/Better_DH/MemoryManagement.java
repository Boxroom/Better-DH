/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Lucas, Florian
 *
 */
public class MemoryManagement {
	private String pathName;
	
	public MemoryManagement() {
		pathName = "./XML/";
		createFolder();
	}
	
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<>();
		File files[] = new File(pathName).listFiles();
		if(files != null){
			for (File file : files) {
				String name = file.getName();
				names.add(name.substring(0, name.length() - 4));
			}
		}
		return names;
	}

	private void createFolder() {
		File filePath = new File(pathName);
		filePath.mkdirs();
	}
	
	public boolean savePlayer(Account getVar) {
		try {
			String dozentZufriedenheit = getVar.getDozentZufiredenheit(), dozentenAnzahl = getVar.getDozentenAnzahl(),
					dozentenGehalt = getVar.getDozentenGehalt(),

			studentenZufriedenheit = getVar.getStudentenZufriedenheit(), studentenAnzahl = getVar.getStudentenAnzahl(),

			partnerunternehmenAnzahl = getVar.getPartnerunternehmenAnzahl(),

			dhQualität = getVar.getDhQualität(), dhAnsehen = getVar.getDhAnsehen(), dhInventar = getVar.getDhInventar(),
					dhEssen = getVar.getDhEssen(), dhVeranstaltungen = getVar.getDhVeranstaltungen(),
					dhWerbung = getVar.getDhWerbung(), dhStudentenplätze = getVar.getDhStudentenplätze(),
					dhKapital = getVar.getDhKapital(), spielstandname = getVar.getSpielstandname(),
					semesterAnzahl = getVar.getSemesterAnzahl(), woche = getVar.getWoche();

			// holt sich alle aktuellen Variablen aus Account

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// Initialisiert den DocumentBuilder

			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Uni");
			doc.appendChild(rootElement);
			// Der WurzelKnoten wird initialisiert

			// Unterelement nach welchem wir suchen können
			Element speicher = doc.createElement("Speicherstand");
			rootElement.appendChild(speicher);

			Attr attr = doc.createAttribute("Name");
			attr.setValue(spielstandname);
			speicher.setAttributeNode(attr);
			// Zur Namensgebung, damit nicht jede Datei gleich aussieht

			// Erstellung aller Kind Elemente, welche an Speicherstand angefügt
			// werden und damit alle Daten aus Account
			// wiederspiegeln
			Element dZ = doc.createElement("DozentZufriedenheit");
			dZ.appendChild(doc.createTextNode(dozentZufriedenheit));
			speicher.appendChild(dZ);

			Element dA = doc.createElement("DozentenAnzahl");
			dA.appendChild(doc.createTextNode(dozentenAnzahl));
			speicher.appendChild(dA);

			Element dG = doc.createElement("DozentenGehalt");
			dG.appendChild(doc.createTextNode(dozentenGehalt));
			speicher.appendChild(dG);

			Element sZ = doc.createElement("StudentenZufriedenheit");
			sZ.appendChild(doc.createTextNode(studentenZufriedenheit));
			speicher.appendChild(sZ);

			Element sA = doc.createElement("StudentenAnzahl");
			sA.appendChild(doc.createTextNode(studentenAnzahl));
			speicher.appendChild(sA);

			Element pUA = doc.createElement("PartnerunternehmenAnzahl");
			pUA.appendChild(doc.createTextNode(partnerunternehmenAnzahl));
			speicher.appendChild(pUA);

			Element dhQ = doc.createElement("DhQualität");
			dhQ.appendChild(doc.createTextNode(dhQualität));
			speicher.appendChild(dhQ);

			Element dhA = doc.createElement("DhAnsehen");
			dhA.appendChild(doc.createTextNode(dhAnsehen));
			speicher.appendChild(dhA);

			Element dhI = doc.createElement("DhInventar");
			dhI.appendChild(doc.createTextNode(dhInventar));
			speicher.appendChild(dhI);

			Element dhE = doc.createElement("DhEssen");
			dhE.appendChild(doc.createTextNode(dhEssen));
			speicher.appendChild(dhE);

			Element dhV = doc.createElement("DhVeranstaltungen");
			dhV.appendChild(doc.createTextNode(dhVeranstaltungen));
			speicher.appendChild(dhV);

			Element dhW = doc.createElement("DhWerbung");
			dhW.appendChild(doc.createTextNode(dhWerbung));
			speicher.appendChild(dhW);

			Element dhSP = doc.createElement("DhStudentenplätze");
			dhSP.appendChild(doc.createTextNode(dhStudentenplätze));
			speicher.appendChild(dhSP);

			Element dhK = doc.createElement("DhKapital");
			dhK.appendChild(doc.createTextNode(dhKapital));
			speicher.appendChild(dhK);

			Element sEAN = doc.createElement("SemesterAnzahl");
			sEAN.appendChild(doc.createTextNode(semesterAnzahl));
			speicher.appendChild(sEAN);

			Element wO = doc.createElement("Woche");
			wO.appendChild(doc.createTextNode(woche));
			speicher.appendChild(wO);

			// Alles wird in eine XML DAtei geschrieben
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(pathName + spielstandname + ".xml"));

			transformer.transform(source, result);
			return true;
		} catch (ParserConfigurationException pce) {
			return false;
		} catch (TransformerException tfe) {
			return false;
		}
	}

	public Account loadPlayer(String name) {
		try { // testet ob die Datei vorhanden ist
			File fXmlFile = new File(pathName + name + ".xml");
			if(!fXmlFile.exists()){
				return null;
			}
			// path zur Datei
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			// erstellt wieder einen Document Builder
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Speicherstand");
			// alle Elemente unter Speicherstand

			for (int temp = 0; temp < nList.getLength(); ++temp) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// Inhalt der Knoten wird den Variablen zugeordnet

					String dozentZufiredenheit = eElement.getElementsByTagName("DozentZufriedenheit").item(0).getTextContent(),
							dozentenAnzahl = eElement.getElementsByTagName("DozentenAnzahl").item(0).getTextContent(),
							dozentenGehalt = eElement.getElementsByTagName("DozentenGehalt").item(0).getTextContent(),

							studentenZufriedenheit = eElement.getElementsByTagName("StudentenZufriedenheit").item(0).getTextContent(),
							studentenAnzahl = eElement.getElementsByTagName("StudentenAnzahl").item(0).getTextContent(),

							partnerunternehmenAnzahl = eElement.getElementsByTagName("PartnerunternehmenAnzahl").item(0).getTextContent(),

							dhQualität = eElement.getElementsByTagName("DhQualität").item(0).getTextContent(),
							dhAnsehen = eElement.getElementsByTagName("DhAnsehen").item(0).getTextContent(),
							dhInventar = eElement.getElementsByTagName("DhInventar").item(0).getTextContent(),
							dhEssen = eElement.getElementsByTagName("DhEssen").item(0).getTextContent(),
							dhVeranstaltungen = eElement.getElementsByTagName("DhVeranstaltungen").item(0).getTextContent(),
							dhWerbung = eElement.getElementsByTagName("DhWerbung").item(0).getTextContent(),
							dhStudentenplätze = eElement.getElementsByTagName("DhStudentenplätze").item(0).getTextContent(),
							dhKapital = eElement.getElementsByTagName("DhKapital").item(0).getTextContent(),

							woche = eElement.getElementsByTagName("Woche").item(0).getTextContent(),
							semesterAnzahl = eElement.getElementsByTagName("SemesterAnzahl").item(0).getTextContent();

					// AccountVariablen werden gesetzt
					Account setVar = new Account();
					setVar.setDozentZufiredenheit(dozentZufiredenheit);
					setVar.setDozentenAnzahl(dozentenAnzahl);
					setVar.setDozentenGehalt(dozentenGehalt);
					setVar.setStudentenZufriedenheit(studentenZufriedenheit);
					setVar.setStudentenAnzahl(studentenAnzahl);

					setVar.setPartnerunternehmenAnzahl(partnerunternehmenAnzahl);
					setVar.setDhQualität(dhQualität);
					setVar.setDhAnsehen(dhAnsehen);
					setVar.setDhInventar(dhInventar);
					setVar.setDhEssen(dhEssen);
					setVar.setDhVeranstaltungen(dhVeranstaltungen);
					setVar.setDhWerbung(dhWerbung);
					setVar.setDhStudentenplätze(dhStudentenplätze);
					setVar.setDhKapital(dhKapital);
					setVar.setSpielstandname(name);
					setVar.setSemesterAnzahl(semesterAnzahl);
					setVar.setWoche(woche);
					return setVar;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}
	
	public boolean existPlayer(String name){
		File files[] = new File(pathName).listFiles();
		if(files != null){
			for (File file : files) {
				String n = file.getName();
				if(n.substring(0, n.length() - 4).equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean createPlayer(String name) {
		if(existPlayer(name)){
			return false;
		}
		try {
			String dozentZufriedenheit = "50", dozentenAnzahl = "5", dozentenGehalt = "5000",

			studentenZufriedenheit = "50", studentenAnzahl = "125",

			partnerunternehmenAnzahl = "3",

			dhQualität = "60", dhAnsehen = "100", dhInventar = "1", dhEssen = "1", dhVeranstaltungen = "1",
					dhWerbung = "1", dhStudentenplätze = "200", dhKapital = "5000", spielstandname = name,
					semesterAnzahl = "1", woche = "0";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// Initialisiert den DocumentBuilder

			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Uni");
			doc.appendChild(rootElement);
			// Der WurzelKnoten wird initialisiert

			// Unterelement nach welchem wir suchen können
			Element speicher = doc.createElement("Speicherstand");
			rootElement.appendChild(speicher);

			Attr attr = doc.createAttribute("Name");
			attr.setValue(spielstandname);
			speicher.setAttributeNode(attr);
			// Zur Namensgebung, damit nicht jede Datei gleich aussieht

			// Erstellung aller Kind Elemente, welche an Speicherstand angefügt
			// werden und damit alle Daten aus Account
			// wiederspiegeln
			Element dZ = doc.createElement("DozentZufriedenheit");
			dZ.appendChild(doc.createTextNode(dozentZufriedenheit));
			speicher.appendChild(dZ);

			Element dA = doc.createElement("DozentenAnzahl");
			dA.appendChild(doc.createTextNode(dozentenAnzahl));
			speicher.appendChild(dA);

			Element dG = doc.createElement("DozentenGehalt");
			dG.appendChild(doc.createTextNode(dozentenGehalt));
			speicher.appendChild(dG);

			Element sZ = doc.createElement("StudentenZufriedenheit");
			sZ.appendChild(doc.createTextNode(studentenZufriedenheit));
			speicher.appendChild(sZ);

			Element sA = doc.createElement("StudentenAnzahl");
			sA.appendChild(doc.createTextNode(studentenAnzahl));
			speicher.appendChild(sA);

			Element pUA = doc.createElement("PartnerunternehmenAnzahl");
			pUA.appendChild(doc.createTextNode(partnerunternehmenAnzahl));
			speicher.appendChild(pUA);

			Element dhQ = doc.createElement("DhQualität");
			dhQ.appendChild(doc.createTextNode(dhQualität));
			speicher.appendChild(dhQ);

			Element dhA = doc.createElement("DhAnsehen");
			dhA.appendChild(doc.createTextNode(dhAnsehen));
			speicher.appendChild(dhA);

			Element dhI = doc.createElement("DhInventar");
			dhI.appendChild(doc.createTextNode(dhInventar));
			speicher.appendChild(dhI);

			Element dhE = doc.createElement("DhEssen");
			dhE.appendChild(doc.createTextNode(dhEssen));
			speicher.appendChild(dhE);

			Element dhV = doc.createElement("DhVeranstaltungen");
			dhV.appendChild(doc.createTextNode(dhVeranstaltungen));
			speicher.appendChild(dhV);

			Element dhW = doc.createElement("DhWerbung");
			dhW.appendChild(doc.createTextNode(dhWerbung));
			speicher.appendChild(dhW);

			Element dhSP = doc.createElement("DhStudentenplätze");
			dhSP.appendChild(doc.createTextNode(dhStudentenplätze));
			speicher.appendChild(dhSP);

			Element dhK = doc.createElement("DhKapital");
			dhK.appendChild(doc.createTextNode(dhKapital));
			speicher.appendChild(dhK);

			Element sEAN = doc.createElement("SemesterAnzahl");
			sEAN.appendChild(doc.createTextNode(semesterAnzahl));
			speicher.appendChild(sEAN);

			Element wO = doc.createElement("Woche");
			wO.appendChild(doc.createTextNode(woche));
			speicher.appendChild(wO);

			// Alles wird in eine XML DAtei geschrieben
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(pathName + spielstandname + ".xml"));

			transformer.transform(source, result);
			return true;

		} catch (ParserConfigurationException pce) {
			return false;
		} catch (TransformerException tfe) {
			return false;
		}
	}
}
