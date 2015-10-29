/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Florian
 *
 */
public interface View {
	public default HBox getTopMenu(String title, boolean buttons) {
		HBox hbox = new HBox();
		hbox.getStyleClass().add("menu");
		hbox.setPrefHeight(80);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10,10,10,10));
		
		if(buttons) {
			Button sim = new Button("Simulieren");
			sim.setPrefSize(100, 50);
			sim.getStyleClass().add("button-menu");
			hbox.getChildren().addAll(sim);
		}
		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		HBox.setHgrow(hb, Priority.ALWAYS);
		Label l_title = new Label(title);
		hb.getChildren().add(l_title);
		hbox.getChildren().addAll(hb);
		
		if(buttons) {
			Button save = new Button("Speichern");
			save.setPrefSize(100, 50);
			save.getStyleClass().add("button-menu");
			hbox.getChildren().add(save);
		}
	    
		return hbox;
	}
	
	public default GridPane getLeftMenu(boolean buttons) {
		GridPane left = new GridPane ();
		left.getStyleClass().add("menu");
		left.setPadding(new Insets(10, 10, 10, 10));
	    left.setVgap(16);
	    left.setHgap(4);
	    left.setPrefWidth(170);
	    
	    if(buttons) {
			Label l_date = new Label("Semester " + 3 + " / Woche " + 4);
			l_date.setPrefWidth(Integer.MAX_VALUE);
			l_date.getStyleClass().add("label1");
			l_date.setAlignment(Pos.CENTER);
			left.add(l_date, 0, 0);
			
			Button overview = new Button ("Übersicht");
			overview.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			overview.setAlignment(Pos.CENTER);
			overview.getStyleClass().add("button-default");
			left.add(overview, 0, 1);
			
			Button zfh = new Button ("Zufriedenheit");
			zfh.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			zfh.setAlignment(Pos.CENTER);
			zfh.getStyleClass().add("button-default");
			left.add(zfh, 0, 2);
			
			Button personal = new Button ("Personal");
			personal.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			personal.setAlignment(Pos.CENTER);
			personal.getStyleClass().add("button-default");
			left.add(personal, 0, 3);
			
			Button money = new Button ("Bilanz");
			money.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			money.setAlignment(Pos.CENTER);
			money.getStyleClass().add("button-default");
			left.add(money, 0, 4);
			
			Button buy = new Button ("Einkauf");
			buy.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
			buy.setAlignment(Pos.CENTER);
			buy.getStyleClass().add("button-default");
			left.add(buy, 0, 5);
	    }
		
	    return left;
	}

	public BorderPane getView();
}
