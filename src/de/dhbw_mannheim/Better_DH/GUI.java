/**
 * Conventions:
 * 	ID's:
 * 			[Type]+"_"+[Class]+"_"+[English name]
 * 			Everything lower case
 * 		Example:
 * 			"button_view_save"
 * 			"label_overview_example"
 */
package de.dhbw_mannheim.Better_DH;

import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Die GUI dient als Einstieg in die Anwendung, da von hier die onClick Events optimal verarbeitet werden k�nnen.
 * Das Fenster wird initialisiert und mit Inhalt aus anderen Klassen die von View erben gef�llt.
 * 
 * @author Florian
 */
public class GUI extends Application {

	private Scene MAIN, OVERVIEW, REPUTATION, SATISFACTION, STAFF, MONEY, BUY;
	
	private Engine engine;
	private Stage window;

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Die Applikation wird gestartet. Das Hauptfenster wird initialisiert und alle Eigenschaften
	 * gesetzt, die die Aplikation definieren. Dazu werden unteranderem die letzten Fenstereigenschaften abgerufen.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		engine = new Engine();
		window = primaryStage;
		window.setTitle("Better DH");
		window.getIcons().add(new Image("Images/BetterDH_Icon.png"));

		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		double x = userPrefs.getDouble("stage.x", 100);
		double y = userPrefs.getDouble("stage.y", 100);
		double w = userPrefs.getDouble("stage.width", 800);
		double h = userPrefs.getDouble("stage.height", 600);
		boolean m = userPrefs.getBoolean("stage.maximized", false);

		(MAIN = new Scene((new View("Hauptmen�", false, false, "Views/Main.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(OVERVIEW = new Scene((new View("�bersicht", true, true, "Views/Overview.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(REPUTATION = new Scene((new View("Reputation", true, true, "Views/Reputation.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(SATISFACTION = new Scene((new View("Zufriedenheit", true, true, "Views/Satisfaction.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(STAFF = new Scene((new View("Personal", true, true, "Views/Staff.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(MONEY = new Scene((new View("Finanzen", true, true, "Views/Money.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		(BUY = new Scene((new View("Einkauf", true, true, "Views/Buy.fxml")).getView())).getStylesheets().add("/MainWindow.css");
		initButtons();
		
		setPage(MAIN);
		
		window.setX(x);
		window.setY(y);
		window.setWidth(w);
		window.setHeight(h);
		window.setMaximized(m);
		window.show();
	}

	/**
	 * Die Applikation wird beendet. 
	 * Die Position und gr��e der Anwendung wird gespeichert.
	 */
	@Override
	public void stop() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		if (!window.isMaximized()) {
			userPrefs.putDouble("stage.x", window.getX());
			userPrefs.putDouble("stage.y", window.getY());
			userPrefs.putDouble("stage.width", window.getWidth());
			userPrefs.putDouble("stage.height", window.getHeight());
		}
		userPrefs.putBoolean("stage.maximized", window.isMaximized());
	}
	
	/**
	 * Das Fenster wird hier mit Inhalt gef�llt. Die �bergebene id legt dabei fest, welche Seite ge�ffnet wird.
	 * 
	 * @param id	Identifikationsnummer des Views
	 */
	private void setPage(Scene scene) {
		window.setScene(scene);
		if(scene != MAIN) {
			initiateSimWindow();
			initiateLeftMenu(engine.getSemesterAnzahlInAccount(), engine.getWocheInAccount());
			initiateTopMenu();
		}
	}
	
	private void initButtons(){
		Button start = (Button) MAIN.lookup("#button_main_start");
		if(start != null)
			start.setOnAction(e -> {
					if(engine.hasPlayer()){
						setPage(OVERVIEW);
						updateViews();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText("Dein Account konnte nicht geladen werden!");
						alert.initOwner(window);
						alert.showAndWait();
					}
				});
		Button create = (Button) MAIN.lookup("#button_main_create");
		if(create != null)
			create.setOnAction(e -> {
					TextInputDialog dialog = new TextInputDialog(System.getProperty("user.name"));
					dialog.setTitle("Neuen Account erstellen");
					dialog.setHeaderText("Bitte gebe deinen Namen ein um einen\nneuen Spielstand zu erstellen.");
					dialog.setContentText("Name:");
					dialog.initOwner(window);

					Optional<String> result = dialog.showAndWait();
					result.ifPresent(name -> {
							if(engine.createPlayer(name)){
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Account erstellt");
								alert.setHeaderText(null);
								alert.setContentText("Dein Account wurde erfolgreich erstellt!");
								alert.initOwner(window);
								alert.showAndWait();
								if(engine.loadPlayer(name))
									start.setDisable(false);
							}else{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Account exestiert");
								alert.setHeaderText(null);
								alert.setContentText("Ein anderer Account mit dem gleichen Namen existiert bereits!");
								alert.initOwner(window);
								alert.showAndWait();
							}
						});
				});
		Button load = (Button) MAIN.lookup("#button_main_load");
		if(load != null)
			load.setOnAction(e -> {
					ArrayList<String> choices = engine.getAllNames();
					
					ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.isEmpty()?null:choices.get(0), choices);
					dialog.setTitle("Spiel laden");
					dialog.setHeaderText("W�hle deinen Spielstand aus");
					dialog.setContentText("Name:");
					dialog.initOwner(window);
					dialog.setGraphic(null);
					
					Optional<String> result = dialog.showAndWait();
					result.ifPresent(name -> {
							if(engine.loadPlayer(name))
								start.setDisable(false);
						});
				});
	}
	
	private void updateViews() {
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_qualitydh"), "", 0.0);
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_sales"), "", 0.2);
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_lecturers"), "", 1.4);
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_reputation"), "", 0.5);
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_venturer"), "", 0.7);
		PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_students"), "", 0.8);

		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_quality"), "", 0.0);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_reputation"), "", 0.5);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber1"), "", 0.3);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber2"), "", 0.3);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory1"), "", 0.3);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory2"), "", 0.3);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events1"), "", 0.6);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events2"), "", 0.6);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_food"), "", 0.9);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_money"), "", 0.1);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_tv"), "", 0.5);
		PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_qualitydh"), "", 1.0);
		
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staff"), "", 0.0);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_students"), "", 0.5);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staffNumber"), "", 0.3);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory1"), "", 0.3);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory2"), "", 0.3);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events1"), "", 0.6);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events2"), "", 0.6);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food"), "", 0.9);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food2"), "", 0.9);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_money"), "", 0.3);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_qualitydh1"), "", 1.0);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_qualitydh2"), "", 1.0);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation1"), "", 0.9);
		PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation2"), "", 0.9);
		
		PreDef.initLabel((Label) STAFF.lookup("#label_staff_satisfaction"), "", 0.7);
		PreDef.initLabel((Label) STAFF.lookup("#label_staff_money"), "", 0.0); //@Flo: Was machen, wenn das Label keine Progressbar hat?	
		//@Flo: Was machen bei einem Button in der View? PreDef.button ist in dem Fall nicht passend
		//@Flo: Was machen wenn ein Bild in der View ist (siehe Staff.fxml)?
	}
	
	/**
	 * Alle Events des Fensters werden hier festgelegt.
	 */
	private void initiateSimWindow() {
        window.setOnCloseRequest(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Simulation speichern");
			alert.setHeaderText("M�chtest du den aktuellen Simulationsstand speichern?");
			alert.setContentText("Dadurch wird der alte Stand �berschrieben.");
			alert.initOwner(window);
			
			ButtonType buttonTypeYes = new ButtonType("Ja");
			ButtonType buttonTypeNo = new ButtonType("Nein");
			ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
			
			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes){
				if(engine.savePlayer()) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Gespeichert");
					alert1.setHeaderText(null);
					alert1.setContentText("Simulation erfolgreich gespeichert!");
					alert1.initOwner(window);
					alert1.showAndWait();
				}else{
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error Dialog");
					alert1.setHeaderText(null);
					alert1.setContentText("Dein Account konnte nicht gespeichert werden!");
					alert1.initOwner(window);
					alert1.showAndWait();
				}
			} else if (result.get() == buttonTypeNo) {
				//close
			} else {
		        e.consume();
			}
		});
	}
	
	/**
	 * Alle Events der oberen Men�buttons werden hier festgelegt.
	 */
	private void initiateTopMenu() {
		Scene scene = window.getScene();
		Button simulate = (Button) scene.lookup("#button_view_simulate");
		if(simulate != null)
			simulate.setOnAction(e -> {
					engine.simulate();
					updateViews();
				});
		Button save = (Button) scene.lookup("#button_view_save");
		if(save != null)
			save.setOnAction(e -> {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Simulation speichern");
					alert.setHeaderText("M�chtest du den aktuellen Simulationsstand speichern?");
					alert.setContentText("Dadurch wird der alte Stand �berschrieben.");
					alert.initOwner(window);
					
					ButtonType buttonTypeYes = new ButtonType("Ja");
					ButtonType buttonTypeNo = new ButtonType("Nein");
					
					alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
	
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonTypeYes){
						if(engine.savePlayer()) {
							Alert alert1 = new Alert(AlertType.INFORMATION);
							alert1.setTitle("Gespeichert");
							alert1.setHeaderText(null);
							alert1.setContentText("Simulation erfolgreich gespeichert!");
							alert1.initOwner(window);
							alert1.showAndWait();
						}else{
							Alert alert1 = new Alert(AlertType.ERROR);
							alert1.setTitle("Error Dialog");
							alert1.setHeaderText(null);
							alert1.setContentText("Dein Account konnte nicht gespeichert werden!");
							alert1.initOwner(window);
							alert1.showAndWait();
						}
					}
				});
	}
	
	/**
	 * Alle Events der linken Men�buttons werden hier festgelegt.
	 * Sowie das Label mit dem aktuellen Semester/Wochen fortschritt.
	 */
	private void initiateLeftMenu(String semester, String week) {
		Scene scene = window.getScene();
		Label date = (Label) scene.lookup("#label_view_date");
		if(date != null)
			date.setText(date.getText().replaceFirst("&VAR&", ""+semester).replaceFirst("&VAR&", ""+week));
		Button overview = (Button) scene.lookup("#button_view_overview");
		if(overview != null)
			overview.setOnAction(e -> {
					setPage(OVERVIEW);
				});
		Button reputation = (Button) scene.lookup("#button_view_reputation");
		if(reputation != null)
			reputation.setOnAction(e -> {
					setPage(REPUTATION);
				});
		Button satisfaction = (Button) scene.lookup("#button_view_satisfaction");
		if(satisfaction != null)
			satisfaction.setOnAction(e -> {
					setPage(SATISFACTION);
				});
		Button staff = (Button) scene.lookup("#button_view_staff");
		if(staff != null)
			staff.setOnAction(e -> {
					setPage(STAFF);
				});
		Button money = (Button) scene.lookup("#button_view_money");
		if(money != null)
			money.setOnAction(e -> {
					setPage(MONEY);
				});
		Button buy = (Button) scene.lookup("#button_view_buy");
		if(buy != null)
			buy.setOnAction(e -> {
					setPage(BUY);
				});
	}
}