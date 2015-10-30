package de.dhbw_mannheim.Better_DH;

import java.util.ArrayList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class old_Data {
	private String name, ausgaben, einnahmen;

	public old_Data(String name, String ausgaben, String einnahmen) {
		super();
		this.name = name;
		this.ausgaben = ausgaben;
		this.einnahmen = einnahmen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAusgaben() {
		return ausgaben;
	}

	public void setAusgaben(String ausgaben) {
		this.ausgaben = ausgaben;
	}

	public String getEinnahmen() {
		return einnahmen;
	}

	public void setEinnahmen(String einnahmen) {
		this.einnahmen = einnahmen;
	}

	public static ArrayList<TableColumn<old_Data, ?>> getColumn(TableView table) {

		int i;
		ArrayList<TableColumn<old_Data, ?>> columns = new ArrayList<TableColumn<old_Data, ?>>();

		String[] columnNames = { "Bezeichnung", "Ausgaben", "Einnahmen" };
		String[] variableNames = { "name", "ausgaben", "einnahmen" };
		Integer[] column_width = { 32, 32, 32 };

		i = 0;
		TableColumn<old_Data, String> nameCol = new TableColumn<>(columnNames[i++]);
		TableColumn<old_Data, String> ausCol = new TableColumn<>(columnNames[i++]);
		TableColumn<old_Data, String> einCol = new TableColumn<>(columnNames[i++]);

		i = 0;
		nameCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));
		ausCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));
		einCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));

		i = 0;
		nameCol.setCellValueFactory(new PropertyValueFactory<old_Data, String>(variableNames[i++]));
		ausCol.setCellValueFactory(new PropertyValueFactory<old_Data, String>(variableNames[i++]));
		einCol.setCellValueFactory(new PropertyValueFactory<old_Data, String>(variableNames[i++]));

		columns.add(nameCol);
		columns.add(ausCol);
		columns.add(einCol);

		return columns;
	}

}
