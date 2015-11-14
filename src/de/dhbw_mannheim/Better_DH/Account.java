/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

/**
 * @authorLucas
 *
 */
public class Account {

	private int dozentenAnzahl, studentenAnzahl, partnerunternehmenAnzahl, studentenplaetze;
	private double dozentZufriedenheit, dozentenGehalt, studentenZufriedenheit, qualitaet, ansehen, inventar,
			essen, veranstaltungen, werbung, kapital;

	private String name;
	private int semester, woche;
	
	public Account(String name) {
		this.name = name;
	}

	/**
	 * @return the dozentenAnzahl
	 */
	public int getDozentenAnzahl() {
		return dozentenAnzahl;
	}

	/**
	 * @return the studentenAnzahl
	 */
	public int getStudentenAnzahl() {
		return studentenAnzahl;
	}

	/**
	 * @return the partnerunternehmenAnzahl
	 */
	public int getPartnerunternehmenAnzahl() {
		return partnerunternehmenAnzahl;
	}

	/**
	 * @return the studentenplaetze
	 */
	public int getStudentenplaetze() {
		return studentenplaetze;
	}

	/**
	 * @return the dozentZufriedenheit
	 */
	public double getDozentZufriedenheit() {
		return dozentZufriedenheit;
	}

	/**
	 * @return the dozentenGehalt
	 */
	public double getDozentenGehalt() {
		return dozentenGehalt;
	}

	/**
	 * @return the studentenZufriedenheit
	 */
	public double getStudentenZufriedenheit() {
		return studentenZufriedenheit;
	}

	/**
	 * @return the qualitaet
	 */
	public double getQualitaet() {
		return qualitaet;
	}

	/**
	 * @return the ansehen
	 */
	public double getAnsehen() {
		return ansehen;
	}

	/**
	 * @return the inventar
	 */
	public double getInventar() {
		return inventar;
	}

	/**
	 * @return the essen
	 */
	public double getEssen() {
		return essen;
	}

	/**
	 * @return the veranstaltungen
	 */
	public double getVeranstaltungen() {
		return veranstaltungen;
	}

	/**
	 * @return the werbung
	 */
	public double getWerbung() {
		return werbung;
	}

	/**
	 * @return the kapital
	 */
	public double getKapital() {
		return kapital;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the semester
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * @return the woche
	 */
	public int getWoche() {
		return woche;
	}

	/**
	 * @param dozentenAnzahl the dozentenAnzahl to set
	 */
	public void setDozentenAnzahl(int dozentenAnzahl) {
		this.dozentenAnzahl = dozentenAnzahl;
	}

	/**
	 * @param studentenAnzahl the studentenAnzahl to set
	 */
	public void setStudentenAnzahl(int studentenAnzahl) {
		this.studentenAnzahl = studentenAnzahl;
	}

	/**
	 * @param partnerunternehmenAnzahl the partnerunternehmenAnzahl to set
	 */
	public void setPartnerunternehmenAnzahl(int partnerunternehmenAnzahl) {
		this.partnerunternehmenAnzahl = partnerunternehmenAnzahl;
	}

	/**
	 * @param studentenplaetze the studentenplaetze to set
	 */
	public void setStudentenplaetze(int studentenplaetze) {
		this.studentenplaetze = studentenplaetze;
	}

	/**
	 * @param dozentZufriedenheit the dozentZufriedenheit to set
	 */
	public void setDozentZufriedenheit(double dozentZufriedenheit) {
		this.dozentZufriedenheit = dozentZufriedenheit;
	}

	/**
	 * @param dozentenGehalt the dozentenGehalt to set
	 */
	public void setDozentenGehalt(double dozentenGehalt) {
		this.dozentenGehalt = dozentenGehalt;
	}

	/**
	 * @param studentenZufriedenheit the studentenZufriedenheit to set
	 */
	public void setStudentenZufriedenheit(double studentenZufriedenheit) {
		this.studentenZufriedenheit = studentenZufriedenheit;
	}

	/**
	 * @param qualitaet the qualitaet to set
	 */
	public void setQualitaet(double qualitaet) {
		this.qualitaet = qualitaet;
	}

	/**
	 * @param ansehen the ansehen to set
	 */
	public void setAnsehen(double ansehen) {
		this.ansehen = ansehen;
	}

	/**
	 * @param inventar the inventar to set
	 */
	public void setInventar(double inventar) {
		this.inventar = inventar;
	}

	/**
	 * @param essen the essen to set
	 */
	public void setEssen(double essen) {
		this.essen = essen;
	}

	/**
	 * @param veranstaltungen the veranstaltungen to set
	 */
	public void setVeranstaltungen(double veranstaltungen) {
		this.veranstaltungen = veranstaltungen;
	}

	/**
	 * @param werbung the werbung to set
	 */
	public void setWerbung(double werbung) {
		this.werbung = werbung;
	}

	/**
	 * @param kapital the kapital to set
	 */
	public void setKapital(double kapital) {
		this.kapital = kapital;
	}

	/**
	 * @param semester the semester to set
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * @param woche the woche to set
	 */
	public void setWoche(int woche) {
		this.woche = woche;
	}
	
}
