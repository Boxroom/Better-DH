package de.dhbw_mannheim.Better_DH;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class Bilanz {

	public void changeCenterToBilanz(GridPane grid) {

		// Student studenten = new Student(); Für später

		TableView<Data> table = new TableView<Data>();
		table.getColumns().addAll(Data.getColumn(table));
		table.setItems(getData());

		grid.setConstraints(table, 2, 2, 4, 4, HPos.CENTER, VPos.CENTER);

		grid.getChildren().add(table);

	}

	private static ObservableList<Data> getData() {

		ObservableList<Data> data = FXCollections.observableArrayList();
		// Hier Daten hinzufügen
		data.addAll(new Data("Grundstück", "200.000", "450.000"));
		data.addAll(new Data("Dozenten", "1.000", "5.000"));
		data.addAll(new Data("Studenten", "20.000", "50.000"));
		// data.addAll(new Data(studenten.getName, studenten.getEinnahmen,
		// Studenten.getAusgaben)); Für später

		return data;
	}

}
