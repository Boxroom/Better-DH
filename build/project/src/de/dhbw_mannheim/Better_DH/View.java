package de.dhbw_mannheim.Better_DH;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Als Basis aller für die Simulation verwendeter Fenster dient diese Klasse, um alle Fenster
 * generell gleich zu halten und redundanz zu sparen.
 * Dem Konstruktor werden die Eigenschaften des Fensters übergeben
 * Durch getView() erhät die GUI ein BorderPane, welches als Scene gesetzt werden kann.
 * 
 * @author Florian
 */
public class View {
	private BorderPane root;
	
	public View(String title, boolean topButtons, boolean leftButtons, String fxmlRessource) {
		root = new BorderPane();
		
		root.setTop(getTopMenu(title, topButtons));
		root.setLeft(getLeftMenu(leftButtons));
		
		try {
			GridPane center = new GridPane();
			center = (GridPane)FXMLLoader.load(getClass().getResource(fxmlRessource));
			center.getStyleClass().add("center");
			center.setPadding(new Insets(10, 10, 10, 10));
			center.setVgap(4);
			center.setHgap(4);
			center.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			root.setCenter(center);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Es wird ein GridPane zurückgegeben, welches alle Elemente zum aufbau des oberen Menü enthält und in dieser
	 * Methode dynamisch erstellt und gefüllt wird, dank der übergebenen Parameter.
	 * Das zurückgegebene Element kann dann zum hinzufügen in das aktuelle Fenster genutzt werden.
	 *
	 * @param  title  	Titel des aktuellen Fensters, der im oberen Menü angezeigt werden soll
	 * @param  buttons 	ob die Buttons angezeigt werden sollen
	 * @return      	ein GridPane welches das obere Menü enthält
	 */
	private HBox getTopMenu(String title, boolean buttons) {
		HBox hbox = new HBox();
		hbox.getStylesheets().add("/MainWindow.css");
		hbox.getStyleClass().add("menu");
		hbox.setPrefHeight(80);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10, 10, 10, 10));

		if (buttons) {
			Button sim = PreDef.button_menu("Simulieren", "button_view_simulate");
			hbox.getChildren().addAll(sim);
		}

		HBox hb = PreDef.fill_width();
		hb.setAlignment(Pos.CENTER);
		Label l_title = new Label(title);
		l_title.getStyleClass().add("label_h1");
		hb.getChildren().add(l_title);
		hbox.getChildren().addAll(hb);

		if (buttons) {
			Button save = PreDef.button_menu("Speichern", "button_view_save");
			hbox.getChildren().add(save);
		}

		return hbox;
	}
	
	/**
	 * Es wird ein GridPane zurückgegeben, welches alle Elemente zum aufbau des linken Menü enthält und in dieser
	 * Methode dynamisch erstellt und gefüllt wird, dank der übergebenen Parameter.
	 * Das zurückgegebene Element kann dann zum hinzufügen in das aktuelle Fenster genutzt werden.
	 *
	 * @param  semester  	aktuelles Semester
	 * @param  woche 		aktuelle Woche
	 * @param  buttons 		ob die Buttons angezeigt werden sollen
	 * @return      		ein GridPane welches das Linke Menü mit allen verlinkungen der Simulationsübersichten enthält
	 */
	private GridPane getLeftMenu(boolean buttons) {
		GridPane left = new GridPane();
		left.getStylesheets().add("/MainWindow.css");
		left.getStyleClass().add("menu");
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setVgap(16);
		left.setHgap(4);
		left.setPrefWidth(170);

		if (buttons) {
			Label l_date = new Label("Semester 0 / Woche 0");
			l_date.setPrefWidth(Integer.MAX_VALUE);
			l_date.getStyleClass().add("label_h3");
			l_date.setAlignment(Pos.CENTER);
			l_date.setId("label_view_date");;
			left.add(l_date, 0, 0);

			Button overview = PreDef.button("Übersicht", "button_view_overview");
			left.add(overview, 0, 1);

			Button rep = PreDef.button("Reputation", "button_view_reputation");
			left.add(rep, 0, 2);
			
			Button sat = PreDef.button("Zufriedenheit", "button_view_satisfaction");
			left.add(sat, 0, 3);

			Button personal = PreDef.button("Personal", "button_view_staff");
			left.add(personal, 0, 4);

			Button money = PreDef.button("Finanzen", "button_view_money");
			left.add(money, 0, 5);

			Button buy = PreDef.button("Einkauf", "button_view_buy");
			left.add(buy, 0, 6);
		}

		return left;
	}

	/**
	 * @return      ein BorderPane welches das komplette Fenster darstellt
	 */
	public BorderPane getView() {
		return root;
	}
}
