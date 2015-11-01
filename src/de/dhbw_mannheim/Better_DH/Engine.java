/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import java.util.List;

/**
 * @author Florian
 *
 */
public class Engine {

	private Account player = null;
	private MemoryManagement mm = null;

	public Engine() {
		mm = new MemoryManagement();
	}
	
	public void simulate() {
		// TODO simulate
	}

	public String getName() {
		return player == null ? "" : player.getName();
	}
	
	public int getSemester() {
		return player == null ? 0 : player.getSemester();
	}
	
	public int getWoche() {
		return player == null ? 0 : player.getWoche();
	}
	
	public int getDozenten_zahl() {
		return player == null ? 0 : player.getDozenten_zahl();
	}

	public List<String> getNames() {
		return mm.getNames();
	}

	public boolean loadPlayer(String name) {
		player = new Account(name);
		return true;
	}

	public boolean savePlayer() {
		// TODO Save current temporary variables from Account in MemoryManagement
		
		return true;
	}

	public boolean createPlayer(String name) {
		// TODO New Player in MemoryManagement
		return true;
	}

	public boolean hasPlayer() {
		return player != null;
	}

}
