/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import java.util.ArrayList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Sebastian, Florian
 *
 */
public class Data {
	private String name, ausgaben;

	public Data(String name, String ausgaben) {
		super();
		this.name = name;
		this.ausgaben = ausgaben;
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

	public static ArrayList<TableColumn<Data, ?>> getColumn(TableView<?> table, String type) {

		int i;
		ArrayList<TableColumn<Data, ?>> columns = new ArrayList<TableColumn<Data, ?>>();

		String[] columnNames = { "Bezeichnung", type };
		String[] variableNames = { "name", "ausgaben" };
		Integer[] column_width = { 32, 32 };

		i = 0;
		TableColumn<Data, String> nameCol = new TableColumn<>(columnNames[i++]);
		TableColumn<Data, String> ausCol = new TableColumn<>(columnNames[i++]);

		i = 0;
		nameCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));
		ausCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));

		i = 0;
		nameCol.setCellValueFactory(new PropertyValueFactory<Data, String>(variableNames[i++]));
		ausCol.setCellValueFactory(new PropertyValueFactory<Data, String>(variableNames[i++]));

		columns.add(nameCol);
		columns.add(ausCol);

		return columns;
	}
}
