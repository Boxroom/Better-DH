/**
 * 
 */
package de.dhbw_mannheim.Better_DH.Views;

import de.dhbw_mannheim.Better_DH.PreDef;
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
public class Main extends View {

	private BorderPane root;
	
	private GridPane center;
	private Label l_welcome;
	private HBox fill_width;
	private Button start, load, create;
	
	public Main() {
		root = new BorderPane();

		root.setTop(getTopMenu("Hauptmenü", false));
		root.setLeft(getLeftMenu(false));

		center = new GridPane();
		center.getStyleClass().add("center");
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setVgap(4);
		center.setHgap(4);

		l_welcome = new Label("Willkommen zu");
		l_welcome.setPrefWidth(Integer.MAX_VALUE);
		l_welcome.getStyleClass().add("label_h1");
		l_welcome.setStyle("-fx-text-fill:black;-fx-font-size:56px;");
		l_welcome.setAlignment(Pos.CENTER);
		l_welcome.setPadding(new Insets(30, 0, 0, 0));
		center.add(l_welcome, 0, 0, 3, 1);

		fill_width = PreDef.fill_width();
		fill_width.getStyleClass().add("img");
		fill_width.setStyle("-fx-background-image: url('Images/BetterDH.png');");
		center.add(fill_width, 0, 1, 3, 1);

		start = PreDef.button("Spiel starten", "button_main_start");
		start.setPrefHeight(100);
		start.setDisable(true);
		center.add(start, 0, 2, 1, 1);

		create = PreDef.button("Neuer Account", "button_main_create");
		create.setPrefHeight(100);
		center.add(create, 1, 2, 1, 1);

		load = PreDef.button("Spiel laden", "button_main_load");
		load.setPrefHeight(100);
		center.add(load, 2, 2, 1, 1);

		root.setCenter(center);
	}

	@Override
	public BorderPane getView() {
		return root;
	}

}
