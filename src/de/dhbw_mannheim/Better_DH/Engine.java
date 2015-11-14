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
	
	final static double mealMAX = 5, stockMAX = 8000, eventsMAX = 7, amountLecturerMAX = 500, capitalMAX = 10000000,
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

	public void simulate() {
		// TODO
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

	}

	// Die einzelnen Parameter werden nacheinander berechnet.
	// Zuerst mit Hilfe einer gewichteten Durchschnittsberechnung.
	// Dieser Prozentwert wird mit einer Sinusfunktion an die Realität
	// angenähert
	public static double qualityDH(double stock, double events, double meal, double amountLecturer, double capital) {
		double qualityDH = 0;

		qualityDH = (3 * stock + 2 * events + meal + amountLecturer + 2 * capital) / 9;

		qualityDH = Math.sin((Math.PI / 2) * qualityDH);

		return Math.round(qualityDH * 100) / 100.00;
	}

	public static double reputationDH(double stock, double events, double ad, double amountLecturer, double qualityDH) {
		double reputationDH = 0;

		reputationDH = (2 * stock + 3 * events + 2 * ad + amountLecturer + 2 * qualityDH) / 10;

		reputationDH = Math.sin((Math.PI / 2) * reputationDH);

		return Math.round(reputationDH * 100) / 100.00;
	}

	public static double satisfactionStudents(double stock, double events, double meal, double amountLecturer,
			double qualityDH, double reputationDH) {
		double satisfactionStudents = 0;

		satisfactionStudents = (3 * stock + events + 2 * meal + 2 * amountLecturer + 3 * qualityDH + 2 * reputationDH)
				/ 13;

		satisfactionStudents = Math.sin((Math.PI / 2) * satisfactionStudents);

		return Math.round(satisfactionStudents * 100) / 100.00;
	}

	public static double satisfactionLecturer(double stock, double events, double meal, double salaryLecturer,
			double qualityDH, double reputationDH) {
		double satisfactionLecturer = 0;

		satisfactionLecturer = (2 * stock + 2 * events + meal + 3 * salaryLecturer + 2 * qualityDH + 2 * reputationDH)
				/ 12;

		satisfactionLecturer = Math.sin((Math.PI / 2) * satisfactionLecturer);

		return Math.round(satisfactionLecturer * 100) / 100.00;
	}

	public static int amountCompanies(double qualityDH, double stock, double reputationDH, double events, double ad,
			double satisfactionStudents) {
		double amountCompanies = 0;

		amountCompanies = (3 * qualityDH + stock + 2 * reputationDH + events + 2 * ad + satisfactionStudents) / 10;
		amountCompanies = Math.sin((Math.PI / 2) * amountCompanies);
		return (int) (amountCompanies * amountCompaniesMAX);

	}

	public static int amountStudents(double satisfactionStudents, double qualityDH, double stock, double reputationDH,
			int amountCompanies, double ad, double events, double meal, double spots) {
		double prozentStudenten = 0;
		int anzahlStudenten = 0;

		prozentStudenten = (3 * satisfactionStudents + qualityDH + 2 * stock + 2 * reputationDH + 2 * amountCompanies
				+ ad + events + meal) / 13;
		prozentStudenten = Math.sin((Math.PI / 2) * prozentStudenten);
		anzahlStudenten = (int) (prozentStudenten * amountStudentsMAX);
		if (anzahlStudenten <= spots) {
			return anzahlStudenten;
		} else {
			return (int) spots;
		}
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
	public void setDozentenAnzahl(int dozentenAnzahl) {
		account.setDozentenAnzahl(dozentenAnzahl);
	}

	/**
	 * @param studentenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenAnzahl(int)
	 */
	public void setStudentenAnzahl(int studentenAnzahl) {
		account.setStudentenAnzahl(studentenAnzahl);
	}

	/**
	 * @param partnerunternehmenAnzahl
	 * @see de.dhbw_mannheim.Better_DH.Account#setPartnerunternehmenAnzahl(int)
	 */
	public void setPartnerunternehmenAnzahl(int partnerunternehmenAnzahl) {
		account.setPartnerunternehmenAnzahl(partnerunternehmenAnzahl);
	}

	/**
	 * @param studentenplaetze
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenplaetze(int)
	 */
	public void setStudentenplaetze(int studentenplaetze) {
		account.setStudentenplaetze(studentenplaetze);
	}

	/**
	 * @param dozentZufriedenheit
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentZufriedenheit(double)
	 */
	public void setDozentZufriedenheit(double dozentZufriedenheit) {
		account.setDozentZufriedenheit(dozentZufriedenheit);
	}

	/**
	 * @param dozentenGehalt
	 * @see de.dhbw_mannheim.Better_DH.Account#setDozentenGehalt(double)
	 */
	public void setDozentenGehalt(double dozentenGehalt) {
		account.setDozentenGehalt(dozentenGehalt);
	}

	/**
	 * @param studentenZufriedenheit
	 * @see de.dhbw_mannheim.Better_DH.Account#setStudentenZufriedenheit(double)
	 */
	public void setStudentenZufriedenheit(double studentenZufriedenheit) {
		account.setStudentenZufriedenheit(studentenZufriedenheit);
	}

	/**
	 * @param qualitaet
	 * @see de.dhbw_mannheim.Better_DH.Account#setQualitaet(double)
	 */
	public void setQualitaet(double qualitaet) {
		account.setQualitaet(qualitaet);
	}

	/**
	 * @param ansehen
	 * @see de.dhbw_mannheim.Better_DH.Account#setAnsehen(double)
	 */
	public void setAnsehen(double ansehen) {
		account.setAnsehen(ansehen);
	}

	/**
	 * @param inventar
	 * @see de.dhbw_mannheim.Better_DH.Account#setInventar(double)
	 */
	public void setInventar(int inventar) {
		account.setInventar(inventar);
	}

	/**
	 * @param essen
	 * @see de.dhbw_mannheim.Better_DH.Account#setEssen(double)
	 */
	public void setEssen(int essen) {
		account.setEssen(essen);
	}

	/**
	 * @param veranstaltungen
	 * @see de.dhbw_mannheim.Better_DH.Account#setVeranstaltungen(double)
	 */
	public void setVeranstaltungen(int veranstaltungen) {
		account.setVeranstaltungen(veranstaltungen);
	}

	/**
	 * @param werbung
	 * @see de.dhbw_mannheim.Better_DH.Account#setWerbung(double)
	 */
	public void setWerbung(int werbung) {
		account.setWerbung(werbung);
	}

	/**
	 * @param kapital
	 * @see de.dhbw_mannheim.Better_DH.Account#setKapital(double)
	 */
	public void setKapital(double kapital) {
		account.setKapital(kapital);
	}

	/**
	 * @param semester
	 * @see de.dhbw_mannheim.Better_DH.Account#setSemester(int)
	 */
	public void setSemester(int semester) {
		account.setSemester(semester);
	}

	/**
	 * @param woche
	 * @see de.dhbw_mannheim.Better_DH.Account#setWoche(int)
	 */
	public void setWoche(int woche) {
		account.setWoche(woche);
	}
	
}
