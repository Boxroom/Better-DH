/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import java.util.ArrayList;

/**
 * @author alle
 *
 */
public class Engine {
	private Account account;
	private MemoryManagement save;

	public Engine() {
		save = new MemoryManagement();
	}
	
	public final double mealMAX = 5, stockMAX = 8000, eventsMAX = 7, amountLecturerMAX = 500, capitalMAX = 10000000,
			adMAX = 6000, salaryLecturerMAX = 10000, amountCompaniesMAX = 4000, amountStudentsMAX = 10000;

	public boolean createPlayer(String name) {
		return save.createPlayer(name);
	}
	
	public boolean savePlayer() {
		return save.savePlayer(account);
	}

	public boolean loadPlayer(String name) {
		account = save.loadPlayer(name);
		return hasPlayer();
	}

	public boolean hasPlayer() {
		return account != null && account.getName() != null && account.getName().length() > 0;
	}

	// array Liste, die alle Namen der gespeicherten Dateien wiedergibt
	public ArrayList<String> getAllNames() {
		return save.getNames();
	}

	public boolean simulate() {
		if(getSemester() <= 6){
			double averageMeal = account.getEssen() / mealMAX;
			double averageStock = account.getInventar() / stockMAX;
			double averageEvents = account.getVeranstaltungen() / eventsMAX;
			double averageLecturer = account.getDozentenAnzahl() / amountLecturerMAX;
			double averageCapital = account.getKapital() / capitalMAX;
			double averageAd = account.getWerbung() / adMAX;
			double averageSalaryLecturer = account.getDozentenGehalt() / salaryLecturerMAX;
			double spots = account.getStudentenplaetze();

			double qualityDH = qualityDH(averageStock, averageEvents, averageMeal, averageLecturer, averageCapital);
			double reputationDH = reputationDH(averageStock, averageEvents, averageAd, averageLecturer, qualityDH);
			double satisfactionStudents = satisfactionStudents(averageStock, averageEvents, averageMeal, averageLecturer,
					qualityDH, reputationDH);
			double satisfactionLecturer = satisfactionLecturer(averageStock, averageEvents, averageMeal,
					averageSalaryLecturer, qualityDH, reputationDH);
			int amountCompanies = amountCompanies(qualityDH, averageStock, reputationDH, averageEvents, averageAd,
					satisfactionStudents);
			int amountStudents = amountStudents(satisfactionStudents, qualityDH, averageStock, reputationDH,
					amountCompanies, averageAd, averageEvents, averageMeal, spots);
			
			setQualitaet(qualityDH*100);
			setAnsehen(reputationDH*100);
			setStudentenZufriedenheit(satisfactionStudents*100);
			setDozentZufriedenheit(satisfactionLecturer*100);
			setPartnerunternehmenAnzahl(amountCompanies);
			setStudentenAnzahl(amountStudents);
			
			double kapital = getKapital();
			double ausgaben = 0;
			double einnahmen = 0;

			if(getWoche() % 4 == 0)
			{
				einnahmen += getEinnahmen();
				ausgaben += getAusgaben();
			}
			
			setKapital(kapital+einnahmen-ausgaben);
			
			if(!addWoche()){
				//Spiel vorbei
				return false;
			}
			return true;
		}
		return false;

	}

	// Die einzelnen Parameter werden nacheinander berechnet.
	// Zuerst mit Hilfe einer gewichteten Durchschnittsberechnung.
	// Dieser Prozentwert wird mit einer Sinusfunktion an die Realität
	// angenähert
	private double qualityDH(double stock, double events, double meal, double amountLecturer, double capital) {
		double qualityDH = 0;

		qualityDH = (3 * stock + 2 * events + meal + amountLecturer + 2 * capital) / 9;

		qualityDH = Math.sin((Math.PI / 2) * qualityDH);

		return Math.round(qualityDH * 100) / 100.00;
	}

	private double reputationDH(double stock, double events, double ad, double amountLecturer, double qualityDH) {
		double reputationDH = 0;

		reputationDH = (2 * stock + 3 * events + 2 * ad + amountLecturer + 2 * qualityDH) / 10;

		reputationDH = Math.sin((Math.PI / 2) * reputationDH);

		return Math.round(reputationDH * 100) / 100.00;
	}

	private double satisfactionStudents(double stock, double events, double meal, double amountLecturer,
			double qualityDH, double reputationDH) {
		double satisfactionStudents = 0;

		satisfactionStudents = (3 * stock + events + 2 * meal + 2 * amountLecturer + 3 * qualityDH + 2 * reputationDH)
				/ 13;

		satisfactionStudents = Math.sin((Math.PI / 2) * satisfactionStudents);

		return Math.round(satisfactionStudents * 100) / 100.00;
	}

	private double satisfactionLecturer(double stock, double events, double meal, double salaryLecturer,
			double qualityDH, double reputationDH) {
		double satisfactionLecturer = 0;

		satisfactionLecturer = (2 * stock + 2 * events + meal + 3 * salaryLecturer + 2 * qualityDH + 2 * reputationDH)
				/ 12;

		satisfactionLecturer = Math.sin((Math.PI / 2) * satisfactionLecturer);

		return Math.round(satisfactionLecturer * 100) / 100.00;
	}

	private int amountCompanies(double qualityDH, double stock, double reputationDH, double events, double ad,
			double satisfactionStudents) {
		double amountCompanies = 0;

		amountCompanies = (3 * qualityDH + stock + 2 * reputationDH + events + 2 * ad + satisfactionStudents) / 10;
		amountCompanies = Math.sin((Math.PI / 2) * amountCompanies);
		return (int) (amountCompanies * amountCompaniesMAX);

	}

	private int amountStudents(double satisfactionStudents, double qualityDH, double stock, double reputationDH,
			int amountCompanies, double ad, double events, double meal, double spots) {
		double prozentStudenten = 0;
		int anzahlStudenten = 0;

		prozentStudenten = (3 * satisfactionStudents + qualityDH + 2 * stock + 2 * reputationDH + 2 * (amountCompanies/amountCompaniesMAX)
				+ ad + events + meal) / 13;
		prozentStudenten = Math.sin((Math.PI / 2) * prozentStudenten);
		anzahlStudenten = (int) (prozentStudenten * amountStudentsMAX);
		if (anzahlStudenten <= spots) {
			return anzahlStudenten;
		} else {
			return (int) spots;
		}
	}
	
	public double getEinnahmen(){
		double einnahmen = 0;
		//Spenden von dritten abhängig vom Ansehen und Qualität
		double spenden = (getQualitaet()+getAnsehen())/2.0;
		if(spenden <= 20) {
			einnahmen += 25000;
		} else if(spenden <= 40) {
			einnahmen += 70000;
		} else if(spenden <= 60) {
			einnahmen += 150000;
		} else if(spenden <= 80) {
			einnahmen += 270000;
		} else if(spenden <= 95) {
			einnahmen += 400000;
		} else{
			einnahmen += 600000;
		}

		//Pro Dozent 7000€ und pro Student 2000€ vom Land
		einnahmen += getDozentenAnzahl()*7000+getStudentenAnzahl()*2000;
		//Pro Student 120€ Studiengebühr
		einnahmen += getStudentenAnzahl()*120;
		//Pro Firma 600€
		einnahmen += getPartnerunternehmenAnzahl()*600;
		
		return einnahmen;
	}
	
	public double getAusgaben(){
		double ausgaben = 0;
		
		//Gehalt für alle Dozenten
		ausgaben += getDozentenGehalt()*getDozentenAnzahl();
		//Inventar Instanthaltungskosten
		switch(getInventar()){
		case 1: ausgaben += 700; break;
		case 2: ausgaben += 1000; break;
		case 3: ausgaben += 1700; break;
		case 4: ausgaben += 2500; break;
		case 5: ausgaben += 3400; break;
		}
		//Regelmäßige Veranstaltungen
		switch(getVeranstaltungen()){
		case 1: ausgaben += 230; break;
		case 2: ausgaben += 500; break;
		case 3: ausgaben += 1000; break;
		case 4: ausgaben += 2000; break;
		case 5: ausgaben += 3800; break;
		}
		//Essensqualität
		switch(getEssen()){
		case 1: ausgaben += 30*getStudentenAnzahl(); break;
		case 2: ausgaben += 31*getStudentenAnzahl(); break;
		case 3: ausgaben += 32*getStudentenAnzahl(); break;
		case 4: ausgaben += 34*getStudentenAnzahl(); break;
		case 5: ausgaben += 36*getStudentenAnzahl(); break;
		}
		//Werbeintensität
		switch(getWerbung()){
		case 1: ausgaben += 100; break;
		case 2: ausgaben += 500; break;
		case 3: ausgaben += 1400; break;
		case 4: ausgaben += 2900; break;
		case 5: ausgaben += 4600; break;
		}
		//Studentenplätze
		switch(getStudentenplaetze()){
		case 1: ausgaben += 165*getStudentenAnzahl(); break;
		case 2: ausgaben += 160*getStudentenAnzahl(); break;
		case 3: ausgaben += 155*getStudentenAnzahl(); break;
		case 4: ausgaben += 150*getStudentenAnzahl(); break;
		case 5: ausgaben += 145*getStudentenAnzahl(); break;
		}
		return ausgaben;
	}
	
	public void addKosten(double kosten){
		if(kosten > 0)
			setKapital(getKapital()-kosten);
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getDozentenAnzahl()
	 */
	public int getDozentenAnzahl() {
		return account.getDozentenAnzahl();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getStudentenAnzahl()
	 */
	public int getStudentenAnzahl() {
		return account.getStudentenAnzahl();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getPartnerunternehmenAnzahl()
	 */
	public int getPartnerunternehmenAnzahl() {
		return account.getPartnerunternehmenAnzahl();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getStudentenplaetze()
	 */
	public int getStudentenplaetze() {
		return account.getStudentenplaetze();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getDozentZufriedenheit()
	 */
	public double getDozentZufriedenheit() {
		return account.getDozentZufriedenheit();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getDozentenGehalt()
	 */
	public double getDozentenGehalt() {
		return account.getDozentenGehalt();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getStudentenZufriedenheit()
	 */
	public double getStudentenZufriedenheit() {
		return account.getStudentenZufriedenheit();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getQualitaet()
	 */
	public double getQualitaet() {
		return account.getQualitaet();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getAnsehen()
	 */
	public double getAnsehen() {
		return account.getAnsehen();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getInventar()
	 */
	public int getInventar() {
		return account.getInventar();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getEssen()
	 */
	public int getEssen() {
		return account.getEssen();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getVeranstaltungen()
	 */
	public int getVeranstaltungen() {
		return account.getVeranstaltungen();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getWerbung()
	 */
	public int getWerbung() {
		return account.getWerbung();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getKapital()
	 */
	public double getKapital() {
		return account.getKapital();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getName()
	 */
	public String getName() {
		return account.getName();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getSemester()
	 */
	public int getSemester() {
		return account.getSemester();
	}

	/**
	 * @return
	 * @see de.dhbw_mannheim.Better_DH.Account#getWoche()
	 */
	public int getWoche() {
		return account.getWoche();
	}

	/**
	 * @param dozentenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentenAnzahl(int)
	 */
	private boolean setDozentenAnzahl(int dozentenAnzahl) {
		if(dozentenAnzahl < 0){
			account.setDozentenAnzahl(0);
			return false;
		} else if(dozentenAnzahl > 500){
			account.setDozentenAnzahl(500);
			return false;
		}
		account.setDozentenAnzahl(dozentenAnzahl);
		return true;
	}

	/**
	 * @param dozentenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentenAnzahl(int)
	 */
	public boolean addDozentenAnzahl(int dozentenAnzahl) {
		return setDozentenAnzahl(getDozentenAnzahl()+dozentenAnzahl);
	}

	/**
	 * @param studentenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenAnzahl(int)
	 */
	public boolean setStudentenAnzahl(int studentenAnzahl) {
		if(studentenAnzahl < 0){
			account.setStudentenAnzahl(0);
			return false;
		} else if(studentenAnzahl > getStudentenplaetze()){
			account.setStudentenAnzahl(getStudentenplaetze());
			return false;
		}
		account.setStudentenAnzahl(studentenAnzahl);
		return true;
	}

	/**
	 * @param partnerunternehmenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setPartnerunternehmenAnzahl(int)
	 */
	private boolean setPartnerunternehmenAnzahl(int partnerunternehmenAnzahl) {
		if(partnerunternehmenAnzahl < 0){
			account.setPartnerunternehmenAnzahl(0);
			return false;
		}
		account.setPartnerunternehmenAnzahl(partnerunternehmenAnzahl);
		return true;
	}

	/**
	 * @param studentenplaetze
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenplaetze(int)
	 */
	public boolean setStudentenplaetze(int studentenplaetze) {
		if(studentenplaetze < 0){
			account.setStudentenplaetze(0);
			return false;
		} else if(studentenplaetze > 10000){
			account.setStudentenplaetze(10000);
			return false;
		}
		account.setStudentenplaetze(studentenplaetze);
		return true;
	}

	/**
	 * @param dozentZufriedenheit
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentZufriedenheit(double)
	 */
	private boolean setDozentZufriedenheit(double dozentZufriedenheit) {
		if(dozentZufriedenheit < 0){
			account.setDozentZufriedenheit(0);
			return false;
		} else if(dozentZufriedenheit > 100){
			account.setDozentZufriedenheit(100);
			return false;
		}
		account.setDozentZufriedenheit(dozentZufriedenheit);
		return true;
	}

	/**
	 * @param dozentenGehalt
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentenGehalt(double)
	 */
	private boolean setDozentenGehalt(double dozentenGehalt) {
		if(dozentenGehalt < 5000){
			account.setDozentenGehalt(5000);
			return false;
		} else if(dozentenGehalt > 10000){
			account.setDozentenGehalt(10000);
			return false;
		}
		account.setDozentenGehalt(dozentenGehalt);
		return true;
	}

	/**
	 * @param dozentenGehalt
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentenGehalt(double)
	 */
	public boolean addDozentenGehalt(double dozentenGehalt) {
		return setDozentenGehalt(getDozentenGehalt()+dozentenGehalt);
	}

	/**
	 * @param studentenZufriedenheit
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenZufriedenheit(double)
	 */
	private boolean setStudentenZufriedenheit(double studentenZufriedenheit) {
		if(studentenZufriedenheit < 0){
			account.setStudentenZufriedenheit(0);
			return false;
		} else if(studentenZufriedenheit > 100){
			account.setStudentenZufriedenheit(100);
			return false;
		}
		account.setStudentenZufriedenheit(studentenZufriedenheit);
		return true;
	}

	/**
	 * @param qualitaet
	 * @see de.dhbw_mannheim.Better_DH.Account#setQualitaet(double)
	 */
	private boolean setQualitaet(double qualitaet) {
		if(qualitaet < 0){
			account.setQualitaet(0);
			return false;
		} else if(qualitaet > 100){
			account.setQualitaet(100);
			return false;
		}
		account.setQualitaet(qualitaet);
		return true;
	}

	/**
	 * @param ansehen
	 * @see de.dhbw_mannheim.Better_DH.Account#setAnsehen(double)
	 */
	private boolean setAnsehen(double ansehen) {
		if(ansehen < 0){
			account.setAnsehen(0);
			return false;
		} else if(ansehen > 100){
			account.setAnsehen(100);
			return false;
		}
		account.setAnsehen(ansehen);
		return true;
	}

	/**
	 * @param inventar
	 * @see de.dhbw_mannheim.Better_DH.Account#setInventar(double)
	 */
	public boolean setInventar(int inventar) {
		if(inventar < 0){
			account.setInventar(0);
			return false;
		} else if(inventar > 5){
			account.setInventar(5);
			return false;
		}
		account.setInventar(inventar);
		return true;
	}

	/**
	 * @param essen
	 * @see de.dhbw_mannheim.Better_DH.Account#setEssen(double)
	 */
	public boolean setEssen(int essen) {
		if(essen < 0){
			account.setEssen(0);
			return false;
		} else if(essen > 5){
			account.setEssen(5);
			return false;
		}
		account.setEssen(essen);
		return true;
	}

	/**
	 * @param veranstaltungen
	 * @see de.dhbw_mannheim.Better_DH.Account#setVeranstaltungen(double)
	 */
	public boolean setVeranstaltungen(int veranstaltungen) {
		if(veranstaltungen < 0){
			account.setVeranstaltungen(0);
			return false;
		} else if(veranstaltungen > 5){
			account.setVeranstaltungen(5);
			return false;
		}
		account.setVeranstaltungen(veranstaltungen);
		return true;
	}

	/**
	 * @param werbung
	 * @see de.dhbw_mannheim.Better_DH.Account#setWerbung(double)
	 */
	public boolean setWerbung(int werbung) {
		if(werbung < 0){
			account.setWerbung(0);
			return false;
		} else if(werbung > 5){
			account.setWerbung(5);
			return false;
		}
		account.setWerbung(werbung);
		return true;
	}

	/**
	 * @param kapital
	 * @see de.dhbw_mannheim.Better_DH.Account#setKapital(double)
	 */
	private boolean setKapital(double kapital) {
		account.setKapital(kapital);
		return true;
	}

	/**
	 * @param semester
	 * @see de.dhbw_mannheim.Better_DH.Account#setSemester(int)
	 */
	private boolean addSemester() {
		if(getSemester()+1 > 6){
			account.setSemester(7);
			return false;
		}
		account.setSemester(account.getSemester()+1);
		return true;
	}

	/**
	 * @param woche
	 * @see de.dhbw_mannheim.Better_DH.Account#setWoche(int)
	 */
	private boolean addWoche() {
		if(getWoche()+1 > 24){
			account.setWoche(1);
			return addSemester();
		}
		account.setWoche(getWoche()+1);
		return true;
	}
	
}
