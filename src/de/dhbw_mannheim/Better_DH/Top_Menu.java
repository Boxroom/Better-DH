package de.dhbw_mannheim.Better_DH;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class Top_Menu {
	
	public HBox addHbox(){
		HBox hbox = new HBox(); //Erstelle neue HBox (=Horizontal Box)
	    hbox.setStyle("-fx-background-color: FFE4C4;"); //Lege Hintergrundfarbe der HBox fest
	    hbox.setPadding(new Insets(10,10,10,10)); //Lege 10px Abstand zwischen Rand der HBox und den darin enthaltenen Elementen fest
		Button sim = new Button("Simulieren"); //Erstelle Button "Simulieren"
		sim.setPrefSize(100, 50); //Setze die gewünschte Größe für den Button
	    hbox.getChildren().addAll(sim); //Füge Button in die HBox ein
	    	    
	    return hbox;
	}

	public void addStackPane(HBox hb) {
		StackPane stack = new StackPane(); //Erstelle neues StackPane-Layout, welches nur den Button "Speichern" beinhaltet
	    stack.setAlignment(Pos.CENTER_RIGHT); //Positioniere das Layout rechts oben in der HBox
		Button save = new Button("Speichern");
		save.setPrefSize(100, 50);
		stack.getChildren().add(save); //Füge Button "Speichern" in das StackPane-Layout ein
	    hb.getChildren().add(stack); //Füge das StackPane-Layout in die HBox ein
	    HBox.setHgrow(stack, Priority.ALWAYS); //Setze das StackPane-Layout immer oben rechts in die Ecke
	}

}