/**
 * 
 */
package de.dhbw_mannheim.Better_DH.Views;

import de.dhbw_mannheim.Better_DH.View;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Florian
 *
 */
public class Overview implements View {

	private BorderPane root;
	
	private GridPane center;
	
	public Overview() {
		root = new BorderPane();

		root.setTop(getTopMenu("Übersicht", true));
		root.setLeft(getLeftMenu(true));

		center = new GridPane();
		center.getStyleClass().add("center");
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setVgap(4);
		center.setHgap(4);

		

		root.setCenter(center);
	}

	@Override
	public BorderPane getView() {

		return root;
	}

}
