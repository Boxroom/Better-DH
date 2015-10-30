package de.dhbw_mannheim.Better_DH;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

public class old_Overview {

	private int umsatz;
	private int partner;
	private double pb_cap_val;
	private double pb_zfh_doz_val;
	private double pb_zfh_stud_val;
	private double pb_quali_val;

	public void setUmsatz(int money) {
		umsatz = money;
	}

	public void setPartner(int anz_partner) {
		partner = anz_partner;
	}

	public void setCapacity(double cap) {
		pb_cap_val = cap;
	}

	public void setZfhDoz(double doz) {
		pb_zfh_doz_val = doz;
	}

	public void setZfhStud(double stud) {
		pb_zfh_stud_val = stud;
	}

	public void setQuali(double quali) {
		pb_quali_val = quali;
	}

	@SuppressWarnings("static-access")
	public void changeCenterToMain(GridPane grid) {

		ProgressBar pb_cap = new ProgressBar();
		pb_cap.setProgress(pb_cap_val);
		ProgressBar pb_doz = new ProgressBar();
		pb_doz.setProgress(pb_zfh_doz_val);
		ProgressBar pb_quali = new ProgressBar();
		pb_quali.setProgress(pb_quali_val);
		ProgressBar pb_stud = new ProgressBar();
		pb_stud.setProgress(pb_zfh_stud_val);
		pb_stud.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		if (pb_cap_val <= 0.5) {
			pb_cap.getStyleClass().add("red-bar");
		} else {
			pb_cap.getStyleClass().add("green-bar");
		}
		;
		if (pb_zfh_doz_val <= 0.5) {
			pb_doz.getStyleClass().add("red-bar");
		} else {
			pb_doz.getStyleClass().add("green-bar");
		}
		;
		if (pb_zfh_stud_val <= 0.5) {
			pb_stud.getStyleClass().add("red-bar");
		} else {
			pb_stud.getStyleClass().add("green-bar");
		}
		;
		if (pb_quali_val <= 0.5) {
			pb_quali.getStyleClass().add("red-bar");
		} else {
			pb_quali.getStyleClass().add("green-bar");
		}
		;

		Label l_capacity = new Label("Kapazität"/* , Grafik.png */);
		l_capacity.setPrefSize(200, 100);
		l_capacity.setAlignment(Pos.CENTER);
		l_capacity.setGraphic(pb_cap);
		// l_capacity.setWrapText(true);
		l_capacity.setContentDisplay(ContentDisplay.BOTTOM);
		pb_cap.setCache(true);

		Label l_dozenten = new Label("Dozenten"/* , Grafik.png */);
		l_dozenten.setPrefSize(200, 100);
		l_dozenten.setAlignment(Pos.CENTER);
		l_dozenten.setGraphic(pb_doz);
		l_dozenten.setContentDisplay(ContentDisplay.BOTTOM);

		Label l_quali = new Label("Qualität"/* , Grafik.png */);
		l_quali.setPrefSize(200, 100);
		l_quali.setAlignment(Pos.CENTER);
		l_quali.setGraphic(pb_quali);
		l_quali.setContentDisplay(ContentDisplay.BOTTOM);

		Label l_umsatz = new Label("Umsatz\n" + this.umsatz/* , Grafik.png */);
		l_umsatz.setPrefSize(200, 100);
		l_umsatz.setAlignment(Pos.CENTER);

		Label l_students = new Label("Studenten"/* , Grafik.png */);
		l_students.setPrefSize(200, 100);
		l_students.setAlignment(Pos.CENTER);
		l_students.setGraphic(pb_stud);
		l_students.setContentDisplay(ContentDisplay.BOTTOM);

		Label l_partner = new Label(
				"Partnerunternehmen\n" + this.partner/* , Grafik.png */);
		l_partner.setPrefSize(200, 100);
		l_partner.setAlignment(Pos.CENTER);

		grid.setConstraints(l_capacity, 2, 2, 2, 1);
		grid.setHalignment(l_capacity, HPos.CENTER);
		grid.setValignment(l_capacity, VPos.CENTER);

		grid.setConstraints(l_dozenten, 4, 2, 2, 1);
		grid.setHalignment(l_dozenten, HPos.CENTER);
		grid.setValignment(l_dozenten, VPos.CENTER);

		grid.setConstraints(l_quali, 2, 3, 2, 2);
		grid.setHalignment(l_quali, HPos.CENTER);
		grid.setValignment(l_quali, VPos.CENTER);

		grid.setConstraints(l_umsatz, 2, 5, 2, 2);
		grid.setHalignment(l_umsatz, HPos.CENTER);
		grid.setValignment(l_umsatz, VPos.CENTER);

		grid.setConstraints(l_students, 4, 3, 2, 2);
		grid.setHalignment(l_students, HPos.CENTER);
		grid.setValignment(l_students, VPos.CENTER);

		grid.setConstraints(l_partner, 4, 5, 2, 2);
		grid.setHalignment(l_partner, HPos.CENTER);
		grid.setValignment(l_partner, VPos.CENTER);

		grid.getChildren().addAll(l_capacity, l_dozenten, l_quali, l_umsatz, l_students, l_partner);

	}

	@SuppressWarnings("static-access")
	public void deleteOverview(int row, int column, GridPane grid) {

		Node delete = null;
		ObservableList<Node> childrens = grid.getChildren();

		for (Node node : childrens) {
			if (node == null || grid.getRowIndex(node) == null) {
				continue;
			}
			if (((grid.getRowIndex(node) == row)) && (grid.getColumnIndex(node) == column)) {
				grid.getChildren().remove(node);
				break;
			}
		}
	}
}
