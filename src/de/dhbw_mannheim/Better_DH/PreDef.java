package de.dhbw_mannheim.Better_DH;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

}
