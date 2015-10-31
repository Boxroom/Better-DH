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
	private int dozenten_zahl;
	
	public String getName() {
		return name;
	}
	
	public int getDozenten_zahl() {
		return dozenten_zahl;
	}

	public Account(String name) {
		this.name = name;
		dozenten_zahl = 200;
	}

}
