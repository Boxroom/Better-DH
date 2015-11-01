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
public class Satisfaction implements View {

	private BorderPane root;
	
	private GridPane center;
	
	public Satisfaction(int semester, int woche) {
		root = new BorderPane();

		root.setTop(getTopMenu("Zufriedenheit", true));
		root.setLeft(getLeftMenu(semester, woche, true));

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
