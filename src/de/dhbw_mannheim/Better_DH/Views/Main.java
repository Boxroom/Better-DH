/**
 * 
 */
package de.dhbw_mannheim.Better_DH.Views;

import de.dhbw_mannheim.Better_DH.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author Florian
 *
 */
public class Main implements View {

	private BorderPane root;
	
	/**
	 * 
	 */
	public Main() {
		root = new BorderPane();
	    
		root.setTop(getTopMenu("Hauptmenü", false));
		root.setLeft(getLeftMenu(false));
		
		GridPane center = new GridPane ();
		center.getStyleClass().add("center");
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setVgap(4);
		center.setHgap(4);
		
		Label l_welcome = new Label("Willkommen zu");
		l_welcome.setPrefWidth(Integer.MAX_VALUE);
		l_welcome.getStyleClass().add("label");
		l_welcome.setStyle("-fx-text-fill:black;-fx-font-size:56px;");
		l_welcome.setAlignment(Pos.CENTER);
		l_welcome.setPadding(new Insets(30, 0, 0, 0));
		center.add(l_welcome, 0, 0, 3, 1);
		
		HBox hb = new HBox();
		hb.setPrefWidth(Integer.MAX_VALUE);
		hb.setPrefHeight(Integer.MAX_VALUE);
		hb.getStyleClass().add("img");
		hb.setStyle("-fx-background-image: url('BetterDH.png');");
		center.add(hb, 0, 1, 3, 1);
		
		Button start = new Button ("Spiel starten");
		start.setPrefSize(Integer.MAX_VALUE, 100);
		start.setAlignment(Pos.CENTER);
		start.getStyleClass().add("button-default");
		center.add(start, 0, 2, 1, 1);
		
		HBox hbb = new HBox();
		hbb.setPrefWidth(Integer.MAX_VALUE);
		hbb.setPrefHeight(Integer.MAX_VALUE);
		center.add(hbb, 1, 2, 1, 1);
		
		Button load = new Button ("Spiel laden");
		load.setPrefSize(Integer.MAX_VALUE, 100);
		load.setAlignment(Pos.CENTER);
		load.getStyleClass().add("button-default");
		center.add(load, 2, 2, 1, 1);
		
		center.setGridLinesVisible(false);

		root.setCenter(center);
	}
	
	public BorderPane getView() {
		
		return root;
	}

}
