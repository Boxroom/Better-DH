package de.dhbw_mannheim.Better_DH;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Left_Menu {

	private int sem;
	private int week;
	
	public void setVariables(int woche, int semester){ //Übergebe die aktuelle Zahl des Semesters und der Woche und spiechere dies in lokale Variablen
		week = woche;
		sem = semester;
	}
	
	@SuppressWarnings("static-access")
	public GridPane changeLeftMenu(GridPane grid){
		
		setVariables(2,2); //Speicher als Zahl für aktuelles Semester 2 und Woche 2
		
		Button sim = new Button("Simulieren"); //Erstelle den "Simulieren" Button
		sim.setPrefSize(100, 50); //Setze die gewünschte Größe fest
		
		Button save = new Button("Speichern");
		save.setPrefSize(100, 50);
		
		Button overview = new Button ("Übersicht");
		overview.setPrefSize(420, 200);
		overview.setAlignment(Pos.BASELINE_LEFT); //Positioniere den Schriftzug des Buttons mittig links
		overview.getStyleClass().add("button-default"); //Style "button-default" aus MainWindow.css anwenden
		
		Button zfh = new Button ("Zufriedenheit");//zfh steht für Zufriedenheit
		zfh.setPrefSize(420, 200);
		zfh.setAlignment(Pos.BASELINE_LEFT);
		zfh.getStyleClass().add("button-default");
		
		Button personal = new Button ("Personal");
		personal.setPrefSize(420, 200);
		personal.setAlignment(Pos.BASELINE_LEFT);
		personal.getStyleClass().add("button-default");
		
		Button money = new Button ("Bilanz");
		money.setPrefSize(420, 200);
		money.setAlignment(Pos.BASELINE_LEFT);
		money.getStyleClass().add("button-default");
		
		Button buy = new Button ("Einkauf");
		buy.setPrefSize(420, 200);
		buy.setAlignment(Pos.BASELINE_LEFT);
		buy.getStyleClass().add("button-default");
		
		Label l_date = new Label("Semester: " + sem + " Woche: " + week); //Erstelle Label mit aktueller Semester- und Wochenzahl
		l_date.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //Label soll komplette Zelle ausfüllen
		l_date.getStyleClass().add("label1"); //Style "label1" aus MainWindow.css anwenden
		l_date.setAlignment(Pos.CENTER); //Positioniere den Text des Labels mittig im Label
		
		grid.setConstraints(overview, 1, 2); //Button "Übersicht" wird in 2. Spalte in 3. Zeile dargestellt
		grid.setHalignment(overview, HPos.LEFT); //Positioniere den Button horizontal links
		grid.setValignment(overview, VPos.CENTER); //Positioniere den Button vertikal mittig
		
		grid.setConstraints(zfh, 1, 3); //Button "Zufriedenheit" wird in 2. Spalte in 4. Zeile dargestellt
		grid.setHalignment(zfh, HPos.LEFT);
		grid.setValignment(zfh, VPos.CENTER);
		
		grid.setConstraints(personal, 1, 4); //Button "Personal" wird in 2. Spalte in 5. Zeile dargestellt
		grid.setHalignment(personal, HPos.LEFT);
		grid.setValignment(personal, VPos.CENTER);
		
		grid.setConstraints(money, 1, 5); //Button "Bilanz" wird in 2. Spalte in 6. Zeile dargestellt
		grid.setHalignment(money, HPos.LEFT);
		grid.setValignment(money, VPos.CENTER);
		
		grid.setConstraints(buy, 1, 6); //Button "Einkauf" wird in 2. Spalte in 7. Zeile dargestellt
		grid.setHalignment(buy, HPos.LEFT);
		grid.setValignment(buy, VPos.CENTER);
		
		grid.setConstraints(l_date, 1, 1); //"Semester: x Woche: y" wird in 2.Spalte, 2. Zeile dargestellt (WICHTIG: 1.Spalte is leer, aber musste leer erzeugt werden, weil sonst Exception)
		grid.setHalignment(l_date, HPos.CENTER);
		grid.setValignment(l_date, VPos.TOP);
		
		grid.getChildren().addAll(l_date, overview, zfh, personal, money, buy); //Buttons und Labels in die Tabelle einfügen
		
		return grid;
		
	}
}
