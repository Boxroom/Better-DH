/**
 * 
 */
package de.dhbw_mannheim.Better_DH.Views;

import java.io.IOException;

import de.dhbw_mannheim.Better_DH.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Florian
 *
 */
public class Buy extends View {

	private BorderPane root;
	
	private GridPane center;
	
	public Buy() {
		root = new BorderPane();

		root.setTop(getTopMenu("Einkauf", true));
		root.setLeft(getLeftMenu(true));
		
		try {
			center = (GridPane)FXMLLoader.load(getClass().getResource("Buy.fxml"));
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

	public BorderPane getView() {
		return root;
	}

}
