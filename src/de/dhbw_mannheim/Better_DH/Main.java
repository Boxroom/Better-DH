package de.dhbw_mannheim.Better_DH;

import javafx.stage.Stage;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Main extends Application{

	static Stage window;
	
	public static void main(String[] args) {
		launch(args);	  
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		window = primaryStage;
		window.setTitle("Better DH"); //Erstelle Fenster und lege dessen Namen fest
		
		BorderPane border = new BorderPane(); //Layout ist BorderPane; Bereiche: Top, Left, Center
		Top_Menu tm = new Top_Menu(); //Erstelle Oberes Menü (Top)
		Left_Menu lm = new Left_Menu(); //Erstelle linkes Menü (Left)
		Overview ov = new Overview(); //Erstelle die Übersicht (Center)
		Bilanz bl = new Bilanz();
		
		ov.setCapacity(0.3); //Testweise
		ov.setQuali(0.7); //Testweise
		ov.setZfhDoz(1.0); //Testweise
		ov.setZfhStud(0.5); //Testweise
		ov.setUmsatz(150000); //Testweise
		ov.setPartner(200); //Testweise
		
		HBox hbox = tm.addHbox(); //Erstelle HBox als oberes Menü (Top)
		tm.addStackPane(hbox); //Füge das StackPane-Layout , welches nur den Button "Speichern" enthält oben rechts ein (Top), 
							  //weil sonst keine Möglichkeit den Button in der HBox immer rechts oben zu positionieren (unabhängig von Fenstergröße)
		GridPane gp = addGridPane(); //Erstelle GridPane als Center+linkes Menü des Layouts (Left+Center)
		ov.changeCenterToMain(gp); //Zeige die Übersicht an (Center)
		ov.deleteOverview(2, 2, gp);
		//bl.changeCenterToBilanz(gp); Muss noch als OnClick-Event festgelegt werden
		lm.changeLeftMenu(gp); //Füge Elemente in links Menü ein
		border.setTop(hbox); //Lege HBox als Top des Layouts fest
		border.setCenter(gp);//Lege GridPane als Center und Left des Layouts fest
		
		Scene scene = new Scene(border, 800, 600); //Fenster wird 800x600 Pixel groß
		scene.getStylesheets().add("/MainWindow.css"); //Fügt Stylesheet hinzu
		window.setScene(scene); //Zeige Tabelle im Fenster an
		window.show(); //Zeige Fenster
	}

	public GridPane addGridPane(){
		
		GridPane main = new GridPane(); //Erstelle Tabelle
		
		//main.setVgap(5); //Abstand zwischen Zeilen
		//main.setHgap(8); //Abstand zwischen Reihen
		main.setPadding(new Insets(0,0,0,0)); //Abstand zum Rand
		
		ColumnConstraints cc = new ColumnConstraints(); //Spalte erzeugen
		ColumnConstraints cc1 = new ColumnConstraints();
		ColumnConstraints cc2 = new ColumnConstraints();
		ColumnConstraints cc3 = new ColumnConstraints();
		ColumnConstraints cc4 = new ColumnConstraints();
		cc.setPercentWidth(22); //Breite der Spalte auf 22% der Breite des Gesamtfensters setzen
		cc1.setPercentWidth(22);
		cc2.setPercentWidth(22);
		cc3.setPercentWidth(22);
		cc4.setPercentWidth(22);
		main.getColumnConstraints().addAll(new ColumnConstraints(), cc, cc1, cc2, cc3, cc4); //Spalten in die Tabelle einfügen
		
		RowConstraints rc1 = new RowConstraints(); //Zeile erzeugen
		RowConstraints rc2 = new RowConstraints();
		RowConstraints rc3 = new RowConstraints();
		RowConstraints rc4 = new RowConstraints();
	    //rc1.setPercentHeight(20);
	    rc2.setPercentHeight(20); //Höhe der Zeile auf 20% der Höhe des Gesamtfensters setzen
	    rc3.setPercentHeight(20);
	    rc4.setPercentHeight(20);
		main.getRowConstraints().addAll(new RowConstraints(), rc1, rc2, rc3, rc4); //Zeilen zur Tabelle hinzufügen
		main.getStyleClass().add("grid"); //Style "grid" aus MainWindow.css anwenden
	    
		main.setGridLinesVisible(true);
		
		return main;
	}
}
