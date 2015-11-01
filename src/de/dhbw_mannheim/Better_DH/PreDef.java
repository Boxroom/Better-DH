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

public class PreDef {
	
	public static HBox fill_width() {
		HBox fill_width = new HBox();
		fill_width.setPrefWidth(Integer.MAX_VALUE);
		fill_width.setPrefHeight(Integer.MAX_VALUE);
		return fill_width;
	}
	
	public static Button button(String text, String id) {
		Button button = new Button(text);
		button.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		button.setAlignment(Pos.CENTER);
		button.getStyleClass().add("button-default");
		button.setId(id);
        
		return button;
	}
	
	public static Button button_menu(String text, String id) {
		Button button = new Button(text);
		button.setAlignment(Pos.CENTER);
		button.setPrefSize(100, 50);
		button.getStyleClass().add("button-menu");
		button.setId(id);
		return button;
	}
	
	public static void initLabel(Label label, String insert, double progress) {
		if(label != null) {
			Label label2 = (Label) label.getGraphic();
			if(label2 != null) {
				label2.setText(insert);
				ProgressBar bar = (ProgressBar) label2.getGraphic();
				bar.setProgress(progress);
				bar.setTooltip(new Tooltip(""+Math.min(Math.round(progress*100),100)+"%"));
				label2.prefWidthProperty().bind(label.widthProperty());
				bar.prefWidthProperty().bind(label2.widthProperty());
				bar.setPadding(new Insets(0,15,0,15));
				
				DoubleProperty fontSize = new SimpleDoubleProperty(30);
		        fontSize.bind(label.widthProperty().add(label.heightProperty()).divide(20));
		        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
		        label.getStyleClass().add("label_view");
		        label2.styleProperty().bind(label.styleProperty());
		        
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
