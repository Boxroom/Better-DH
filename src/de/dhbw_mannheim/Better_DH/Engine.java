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
		account = new Account();
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
		return account.getSpielstandname() != null && account.getSpielstandname().length() > 0;
	}

	// array Liste, die alle Namen der gespeicherten Dateien wiedergibt
	public ArrayList<String> getAllNames() {
		return save.getNames();
	}

	public void simulate() {
		// TODO
		double averageMeal = 3 / mealMAX;
		double averageStock = 4000 / stockMAX;
		double averageEvents = 2 / eventsMAX;
		double averageLecturer = 50 / amountLecturerMAX;
		double averageCapital = 100000 / capitalMAX;
		double averageAd = 6000 / adMAX;
		double averageSalaryLecturer = 10000 / salaryLecturerMAX;
		double spots = 10000;

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

	public void setDozentZufiredenheitInAccount(int change) {
		Account set = new Account();
		String temp = set.getDozentZufiredenheit();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDozentZufiredenheit(temp);
	}

	public void setDozentenAnzahlInAccount(int change) {
		Account set = new Account();
		String temp = set.getDozentenAnzahl();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDozentenAnzahl(temp);
	}

	public void setDozentenGehaltInAccount(int change) {
		Account set = new Account();
		String temp = set.getDozentenGehalt();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDozentenGehalt(temp);
	}

	public void setStudentenZufriedenheitInAccount(int change) {
		Account set = new Account();
		String temp = set.getStudentenZufriedenheit();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setStudentenZufriedenheit(temp);
	}

	public void setStudentenAnzahlInAccount(int change) {
		Account set = new Account();
		String temp = set.getStudentenAnzahl();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setStudentenAnzahl(temp);
	}

	public void setPartnerunternehmenAnzahlInAccount(int change) {
		Account set = new Account();
		String temp = set.getPartnerunternehmenAnzahl();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setPartnerunternehmenAnzahl(temp);
	}

	public void setDhQualitätInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhQualität();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhQualität(temp);
	}

	public void setDhAnsehenInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhAnsehen();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhAnsehen(temp);
	}

	public void setDhInventarInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhInventar();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhInventar(temp);
	}

	public void setDhEssenInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhEssen();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhEssen(temp);
	}

	public void setDhVeranstaltungenInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhVeranstaltungen();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhVeranstaltungen(temp);
	}

	public void setDhWerbungInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhWerbung();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhWerbung(temp);
	}

	public void setDhStudentenplätzeInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhStudentenplätze();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhStudentenplätze(temp);
	}

	public void setDhKapitalInAccount(int change) {
		Account set = new Account();
		String temp = set.getDhKapital();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setDhKapital(temp);
	}

	public void setSemesterAnzahlInAccount(int change) {
		Account set = new Account();
		String temp = set.getSemesterAnzahl();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setSemesterAnzahl(temp);
	}

	public void setWocheInAccount(int change) {
		Account set = new Account();
		String temp = set.getWoche();
		int tempInt = Integer.parseInt(temp);

		tempInt += change;
		temp = Integer.toString(tempInt);
		set.setWoche(temp);
	}

	public String getDozentZufriedenheitInAccount() {
		Account set = new Account();
		String temp = set.getDozentZufiredenheit();
		return temp;
	}

	public String getDozentenAnzahlInAccount() {
		Account set = new Account();
		String temp = set.getDozentenAnzahl();
		return temp;
	}

	public String getDozentenGehaltInAccount() {
		Account set = new Account();
		String temp = set.getDozentenGehalt();
		return temp;
	}

	public String getStudentenZufriedenheitInAccount() {
		Account set = new Account();
		String temp = set.getStudentenZufriedenheit();
		return temp;
	}

	public String getStudentenAnzahlInAccount() {
		Account set = new Account();
		String temp = set.getStudentenAnzahl();
		return temp;
	}

	public String getPartnerunternehmenAnzahlInAccount() {
		Account set = new Account();
		String temp = set.getPartnerunternehmenAnzahl();
		return temp;
	}

	public String getDhQualitätInAccount() {
		Account set = new Account();
		String temp = set.getDhQualität();
		return temp;
	}

	public String getDhAnsehenInAccount() {
		Account set = new Account();
		String temp = set.getDhAnsehen();
		return temp;
	}

	public String getDhInventarInAccount() {
		Account set = new Account();
		String temp = set.getDhInventar();
		return temp;
	}

	public String getDhEssenInAccount() {
		Account set = new Account();
		String temp = set.getDhEssen();
		return temp;
	}

	public String getDhVeranstaltungenInAccount() {
		Account set = new Account();
		String temp = set.getDhVeranstaltungen();
		return temp;
	}

	public String getDhWerbungInAccount() {
		Account set = new Account();
		String temp = set.getDhWerbung();
		return temp;
	}

	public String getDhStudentenplätzeInAccount() {
		Account set = new Account();
		String temp = set.getDhStudentenplätze();
		return temp;
	}

	public String getDhKapitalInAccount() {
		Account set = new Account();
		String temp = set.getDhKapital();
		return temp;
	}

	public String getSemesterAnzahlInAccount() {
		Account set = new Account();
		String temp = set.getSemesterAnzahl();
		return temp;
	}

	public String getWocheInAccount() {
		Account set = new Account();
		String temp = set.getWoche();
		return temp;
	}

}
