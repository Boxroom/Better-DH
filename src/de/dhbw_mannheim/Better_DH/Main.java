package de.dhbw_mannheim.Better_DH;
//Hello
import javafx.stage.Stage;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Main extends Application{

	static Stage window;
	static int sem;
	static int week;
	int umsatz;
	//String date = "Semester: " + sem + " Week: " + week;
	
	public static void main(String[] args) {
		setVariables(2,2);
		launch(args);
		  
	}
	
	public static void setVariables(int woche, int semester){
		week = woche;
		sem = semester;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		window = primaryStage;
		window.setTitle("Better DH"); //Erstelle Fenster und lege dessen Namen fest
		
		BorderPane border = new BorderPane(); //Layout ist BorderPane
		HBox hbox = addHbox(); //Erstelle HBox als Top des Layouts fest
		addStackPane(hbox);
		GridPane gp = addGridPane(); //Erstelle GridPane als Center des Layouts
		changeCenterToMain(gp);
		border.setTop(hbox);
		border.setCenter(gp);
		
		Scene scene = new Scene(border, 800, 600); //Fenster wird 800x600 Pixel groß
		scene.getStylesheets().add("/MainWindow.css"); //Fügt Stylesheet hinzu
		window.setScene(scene); //Zeige Tabelle im Fenster an
		window.show(); //Zeige Fenster
	}
	
	public static HBox addHbox(){
		HBox hbox = new HBox();
	    hbox.setStyle("-fx-background-color: FFE4C4;");
	    hbox.setPadding(new Insets(10,10,10,10));
		Button sim = new Button("Simulieren");
		sim.setPrefSize(100, 50);
	    hbox.getChildren().addAll(sim);
	    
	    return hbox;
	}

	public void addStackPane(HBox hb) {
		StackPane stack = new StackPane();
	    stack.setAlignment(Pos.CENTER_RIGHT);
		Button save = new Button("Speichern");
		save.setPrefSize(100, 50);
		stack.getChildren().add(save);
	    hb.getChildren().add(stack); 
	    HBox.setHgrow(stack, Priority.ALWAYS); 
	    
	}
	
	public GridPane addGridPane(){
		
		GridPane main = new GridPane(); //Erstelle Tabelle
		
		//main.setVgap(5); //Abstand zwischen Zeilen
		//main.setHgap(8); //Abstand zwischen Reihen
		main.setPadding(new Insets(0,0,0,0)); //Abstand zum Rand

		
		Button sim = new Button("Simulieren");
		sim.setPrefSize(100, 50);
		//sim.setStyle("-fx-background-color: brown");
		Button save = new Button("Speichern");
		save.setPrefSize(100, 50);
		
		Button overview = new Button ("Übersicht");
		overview.setPrefSize(420, 200);
		overview.setAlignment(Pos.BASELINE_LEFT);
		overview.getStyleClass().add("button-default");
		
		Button zfh = new Button ("Zufriedenheit");//zfh steht für Zufriedenheit
		zfh.setPrefSize(420, 200);
		zfh.setAlignment(Pos.BASELINE_LEFT);
		zfh.getStyleClass().add("button-default");
		
		Button personal = new Button ("Personal");
		personal.setPrefSize(420, 200);
		personal.setAlignment(Pos.BASELINE_LEFT);
		personal.getStyleClass().add("button-default");
		
		Button money = new Button ("Bilanz");
		money.setPrefSize(420, 200);
		money.setAlignment(Pos.BASELINE_LEFT);
		money.getStyleClass().add("button-default");
		
		Button buy = new Button ("Einkauf");
		buy.setPrefSize(420, 200);
		buy.setAlignment(Pos.BASELINE_LEFT);
		buy.getStyleClass().add("button-default");
		
		Label l_date = new Label("Semester: " + sem + " Woche: " + week);
		l_date.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		l_date.getStyleClass().add("label1");
		l_date.setAlignment(Pos.CENTER);
		
		main.setConstraints(overview, 1, 2); //Button "Übersicht" wird in 2. Spalte in 3. Zeile dargestellt
		main.setHalignment(overview, HPos.LEFT);
		main.setValignment(overview, VPos.CENTER);
		
		main.setConstraints(zfh, 1, 3); //Button "Zufriedenheit" wird in 2. Spalte in 4. Zeile dargestellt
		main.setHalignment(zfh, HPos.LEFT);
		main.setValignment(zfh, VPos.CENTER);
		
		main.setConstraints(personal, 1, 4); //Button "Personal" wird in 2. Spalte in 5. Zeile dargestellt
		main.setHalignment(personal, HPos.LEFT);
		main.setValignment(personal, VPos.CENTER);
		
		main.setConstraints(money, 1, 5); //Button "Bilanz" wird in 2. Spalte in 6. Zeile dargestellt
		main.setHalignment(money, HPos.LEFT);
		main.setValignment(money, VPos.CENTER);
		
		main.setConstraints(buy, 1, 6); //Button "Einkauf" wird in 2. Spalte in 7. Zeile dargestellt
		main.setHalignment(buy, HPos.LEFT);
		main.setValignment(buy, VPos.CENTER);
		
		main.setConstraints(l_date, 1, 1); //"Semester: x Woche: y" wird in 2.Spalte, 2. Zeile dargestellt (WICHTIG: 1.Spalte is leer, aber musste leer erzeugt werden, weil sonst Exception)
		main.setHalignment(l_date, HPos.CENTER);
		main.setValignment(l_date, VPos.TOP);
		
		
		ColumnConstraints cc = new ColumnConstraints(); //Spalte erzeugen
		ColumnConstraints cc1 = new ColumnConstraints();
		ColumnConstraints cc2 = new ColumnConstraints();
		ColumnConstraints cc3 = new ColumnConstraints();
		ColumnConstraints cc4 = new ColumnConstraints();
		cc.setPercentWidth(22); //Breite der Spalte auf 22% der Breite des Gesamtfensters setzen
		cc1.setPercentWidth(22);
		cc2.setPercentWidth(22);
		cc3.setPercentWidth(22);
		cc4.setPercentWidth(22);
		main.getColumnConstraints().addAll(new ColumnConstraints(), cc, cc1, cc2, cc3, cc4); //Spalten in die Tabelle einfügen
		
		RowConstraints rc1 = new RowConstraints(); //Zeile erzeugen
		RowConstraints rc2 = new RowConstraints();
		RowConstraints rc3 = new RowConstraints();
		RowConstraints rc4 = new RowConstraints();
	    //rc1.setPercentHeight(20);
	    rc2.setPercentHeight(18); //Höhe der Zeile auf 17% der Höhe des Gesamtfensters setzen
	    rc3.setPercentHeight(18);
	    rc4.setPercentHeight(18);
		main.getRowConstraints().addAll(new RowConstraints(), rc1, rc2, rc3, rc4); //Zeilen zur Tabelle hinzufügen
		main.getStyleClass().add("grid");
	    
		main.getChildren().addAll(l_date, overview, zfh, personal, money, buy); //Buttons, Textfelder etc. in die Tabelle einfügen
		main.setGridLinesVisible(true);
		
		return main;
	}
	
	public void changeCenterToMain(GridPane grid){
		
		Image balken = new Image("balken.png");
		ImageView iv = new ImageView(balken);
		
		Label l_capacity = new Label("Kapazität"/*, Grafik.png*/);
		l_capacity.setPrefSize(200,100);
		l_capacity.setAlignment(Pos.CENTER);
		l_capacity.setGraphic(iv);
		l_capacity.setWrapText(true);
		l_capacity.setContentDisplay(ContentDisplay.BOTTOM);
        iv.setFitWidth(180);
        iv.setFitHeight(25);
        //iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        
		Label l_dozenten = new Label("Dozenten"/*, Grafik.png*/);
		l_dozenten.setPrefSize(200,100);
		l_dozenten.setAlignment(Pos.CENTER);
		
		Label l_quali = new Label("Qualität"/*, Grafik.png*/);
		l_quali.setPrefSize(200,100);
		l_quali.setAlignment(Pos.CENTER);
		
		Label l_umsatz = new Label("Umsatz\n" + this.umsatz/*, Grafik.png*/);
		l_umsatz.setPrefSize(200,100);
		l_umsatz.setAlignment(Pos.CENTER);
		
		Label l_students = new Label("Studenten"/*, Grafik.png*/);
		l_students.setPrefSize(200,100);
		l_students.setAlignment(Pos.CENTER);
		
		Label l_partner = new Label("Partnerunternehmen"/*, Grafik.png*/);
		l_partner.setPrefSize(200,100);
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
}
