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

	public default GridPane getLeftMenu(int semester, int woche, boolean buttons) {
		GridPane left = new GridPane();
		left.getStyleClass().add("menu");
		left.setPadding(new Insets(10, 10, 10, 10));
		left.setVgap(16);
		left.setHgap(4);
		left.setPrefWidth(170);

		if (buttons) {
			Label l_date = new Label("Semester " + semester + " / Woche " + woche);
			l_date.setPrefWidth(Integer.MAX_VALUE);
			l_date.getStyleClass().add("label_h3");
			l_date.setAlignment(Pos.CENTER);
			left.add(l_date, 0, 0);

			Button overview = PreDef.button("Übersicht", "button_view_overview");
			left.add(overview, 0, 1);

			Button zfh = PreDef.button("Zufriedenheit", "button_view_satisfaction");
			left.add(zfh, 0, 2);

			Button personal = PreDef.button("Personal", "button_view_staff");
			left.add(personal, 0, 3);

			Button money = PreDef.button("Finanzen", "button_view_money");
			left.add(money, 0, 4);

			Button buy = PreDef.button("Einkauf", "button_view_buy");
			left.add(buy, 0, 5);
		}

		return left;
	}

	public BorderPane getView();
}
