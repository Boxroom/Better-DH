package de.dhbw_mannheim.Better_DH;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Diese Klasse hat lediglich statische Elemente und wird zu keiner Zeit instanziiert.
 * Enthalten sind Codesegmente, die von mehreren Klassen benötigt werden können um z.B. einen global
 * gleich aussehenden Button zu erstellen.
 * 
 * @author Florian
 */
public class PreDef {
	
	/**
	 * Es wird ein Platzhalter erstellt
	 *
	 * @return		Eine HBox die so breit ist, das jeder Parent Node, in dem diese HBox plaziert wird,
	 * 				komplett in der Breite ausgefüllt wird.
	 */
	public static HBox fill_width() {
		HBox fill_width = new HBox();
		fill_width.setPrefWidth(Integer.MAX_VALUE);
		fill_width.setPrefHeight(Integer.MAX_VALUE);
		return fill_width;
	}
	
	/**
	 * Standardbuttons werden hier erstellt und zurückgegeben
	 *
	 * @return	Standardisierter Button
	 */
	public static Button button(String text, String id) {
		Button button = new Button(text);
		button.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		button.setAlignment(Pos.CENTER);
		button.getStyleClass().add("button-default");
		button.setId(id);
        
		return button;
	}

	/**
	 * Standardbuttons werden hier erstellt und zurückgegeben
	 *
	 * @return	Standardisierter Button für oberes Menü
	 */
	public static Button button_menu(String text, String id) {
		Button button = new Button(text);
		button.setAlignment(Pos.CENTER);
		button.setPrefSize(100, 50);
		button.getStyleClass().add("button-menu");
		button.setId(id);
		return button;
	}

	/**
	 * Alle Buttons auf den Fenstern werden nach dem selben Muster initialisiert.
	 */
	public static void initButton(Button button) {
		if(button != null) {
			DoubleProperty fontSize = new SimpleDoubleProperty(30);
	        fontSize.bind(button.widthProperty().add(button.heightProperty()).divide(17));
	        button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
		}
	}

	/**
	 * Alle Labels auf den Fenstern werden nach dem selben Muster initialisiert.
	 */
	public static void initLabel(Label label, String insert, double progress) {
		if(label != null) {
			DoubleProperty fontSize = new SimpleDoubleProperty(30);
	        fontSize.bind(label.widthProperty().add(label.heightProperty()).divide(17));
	        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
			if(label.getGraphic() != null && label.getGraphic() instanceof Label){
				Label label2 = (Label) label.getGraphic();
				label2.setText(insert);
				
		        label2.styleProperty().bind(label.styleProperty());
		        
				if(label2.getGraphic() != null && label2.getGraphic() instanceof ProgressBar) {
					ProgressBar bar = (ProgressBar) label2.getGraphic();
					bar.setProgress(progress);
					bar.setTooltip(new Tooltip(""+Math.min(Math.round(progress*100),100)+"%"));
					label2.prefWidthProperty().bind(label.widthProperty());
					bar.prefWidthProperty().bind(label2.widthProperty());
					bar.setPadding(new Insets(0,15,0,15));
			        
			        if(progress < 0.5) {
						bar.getStyleClass().add("red-bar");
					} else if(progress < 0.75) {
						bar.getStyleClass().add("orange-bar");
					} else {
						bar.getStyleClass().add("green-bar");
					}
				}
			} else if(label.getGraphic() != null && label.getGraphic() instanceof ProgressBar){
				ProgressBar bar = (ProgressBar) label.getGraphic();
				bar.setProgress(progress);
				bar.setTooltip(new Tooltip(""+insert));
		        
			    if(progress < 0.5) {
			        bar.getStyleClass().add("red-bar");
				} else if(progress < 0.75) {
					bar.getStyleClass().add("orange-bar");
				} else {
					bar.getStyleClass().add("green-bar");
				}
			}
		}
	}

}
