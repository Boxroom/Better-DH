/**
 * 
 */
package de.dhbw_mannheim.Better_DH.Views;

import java.io.IOException;

import de.dhbw_mannheim.Better_DH.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Florian
 *
 */
public class Money implements View {

	private BorderPane root;
	
	private GridPane center;
	
	public Money(int semester, int woche) {
		root = new BorderPane();

		root.setTop(getTopMenu("Finanzen", true));
		root.setLeft(getLeftMenu(semester, woche, true));

		center = new GridPane();
		center.getStyleClass().add("center");
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setVgap(4);
		center.setHgap(4);
		
		try {
			Parent money = FXMLLoader.load(getClass().getResource("Money.fxml"));
			center.add(money, 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		root.setCenter(center);
	}

	@Override
	public BorderPane getView() {

		return root;
	}

}
