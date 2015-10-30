package de.dhbw_mannheim.Better_DH;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class old_Bilanz {

	public void changeCenterToBilanz(GridPane grid) {

		// Student studenten = new Student(); Für später

		TableView<old_Data> table = new TableView<old_Data>();
		table.getColumns().addAll(old_Data.getColumn(table));
		table.setItems(getData());

		grid.setConstraints(table, 2, 2, 4, 4, HPos.CENTER, VPos.CENTER);

		grid.getChildren().add(table);

	}

	private static ObservableList<old_Data> getData() {

		ObservableList<old_Data> data = FXCollections.observableArrayList();
		// Hier Daten hinzufügen
		data.addAll(new old_Data("Grundstück", "200.000", "450.000"));
		data.addAll(new old_Data("Dozenten", "1.000", "5.000"));
		data.addAll(new old_Data("Studenten", "20.000", "50.000"));
		// data.addAll(new Data(studenten.getName, studenten.getEinnahmen,
		// Studenten.getAusgaben)); Für später

		return data;
	}

}
