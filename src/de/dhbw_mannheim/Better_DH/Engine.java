/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

/**
 * @author Florian
 *
 */
public class Engine {

	private Account player = null;

	public Engine() {
	}

	public String getName() {
		return player == null ? "" : player.getName();
	}

	public boolean loadPlayer(String name) {
		player = new Account(name);
		return true;
	}

	public void savePlayer() {
		// TODO Save current temporary variables from Account in MemoryManagement
	}

	public boolean createPlayer(String name) {
		// TODO New Player in MemoryManagement
		return true;
	}

	public boolean hasPlayer() {
		return player != null;
	}

}
