/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

/**
 * @author Florian
 *
 */
public class Account {

	private String name;
	private int semester, woche;
	private int dozenten_zahl;
	
	public String getName() {
		return name;
	}
	
	public int getSemester() {
		return semester;
	}
	
	public int getWoche() {
		return woche;
	}
	
	public int getDozenten_zahl() {
		return dozenten_zahl;
	}

	public Account(String name) {
		this.name = name;
		dozenten_zahl = 200;
		semester = 3;
		woche = 4;
	}

}
