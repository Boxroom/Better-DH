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

import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import de.dhbw_mannheim.Better_DH.Views.Buy;
import de.dhbw_mannheim.Better_DH.Views.Main;
import de.dhbw_mannheim.Better_DH.Views.Money;
import de.dhbw_mannheim.Better_DH.Views.Overview;
import de.dhbw_mannheim.Better_DH.Views.Satisfaction;
import de.dhbw_mannheim.Better_DH.Views.Staff;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Florian
 *
 */
public class GUI extends Application {

	public final static int MAIN = 0, OVERVIEW = 1, SATISFACTION = 2, STAFF = 3, MONEY = 4, BUY = 5;
	
	private int pid;
	private Engine engine;
	private Stage window;
	private View view;

	public static void main(String[] args) {
		launch(args);
	}

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

		setPage(MAIN);
		
		window.setX(x);
		window.setY(y);
		window.setWidth(w);
		window.setHeight(h);
		window.setMaximized(m);
		window.show();
	}

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

	public void setPage(int id) {
		pid = id;
		switch(pid){
		case MAIN:
			view = new Main();
			Scene main = new Scene(view.getView());
			main.getStylesheets().add("/MainWindow.css");
			window.setScene(main);
			
			Button start = (Button) main.lookup("#button_main_start");
			if(start != null)
				start.setOnAction(e -> {
						if(engine.hasPlayer()){
							setPage(OVERVIEW);
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Dialog");
							alert.setHeaderText(null);
							alert.setContentText("Dein Account konnte nicht geladen werden!");
							alert.initOwner(window);
							alert.showAndWait();
						}
					});
			Button load = (Button) main.lookup("#button_main_load");
			if(load != null)
				load.setOnAction(e -> {
						List<String> choices = engine.getNames();
						
						ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
						dialog.setTitle("Spiel laden");
						dialog.setHeaderText("Wähle deinen Spielstand aus");
						dialog.setContentText("Name:");
						dialog.initOwner(window);
						dialog.setGraphic(null);
						
						Optional<String> result = dialog.showAndWait();
						result.ifPresent(name -> {
								if(engine.loadPlayer(name))
									start.setDisable(false);
							});
					});
			break;
		case OVERVIEW:
			view = new Overview(engine.getSemester(), engine.getWoche());
			Scene overview = new Scene(view.getView());
			overview.getStylesheets().add("/MainWindow.css");
			window.setScene(overview);
			
			initiateMenuButtons();
			
			PreDef.initLabel((Label) overview.lookup("#label_overview_qualitydh"), ""+engine.getDozenten_zahl(), 0.0);
			PreDef.initLabel((Label) overview.lookup("#label_overview_sales"), ""+engine.getDozenten_zahl(), 0.2);
			PreDef.initLabel((Label) overview.lookup("#label_overview_lecturers"), ""+engine.getDozenten_zahl(), 1.4);
			PreDef.initLabel((Label) overview.lookup("#label_overview_reputation"), ""+engine.getDozenten_zahl(), 0.5);
			PreDef.initLabel((Label) overview.lookup("#label_overview_venturer"), ""+engine.getDozenten_zahl(), 0.7);
			PreDef.initLabel((Label) overview.lookup("#label_overview_students"), ""+engine.getDozenten_zahl(), 0.8);
			
			break;
		case SATISFACTION:
			view = new Satisfaction(engine.getSemester(), engine.getWoche());
			Scene satisfaction = new Scene(view.getView());
			satisfaction.getStylesheets().add("/MainWindow.css");
			window.setScene(satisfaction);
			
			initiateMenuButtons();
			
			break;
		case STAFF:
			view = new Staff(engine.getSemester(), engine.getWoche());
			Scene staff = new Scene(view.getView());
			staff.getStylesheets().add("/MainWindow.css");
			window.setScene(staff);
			
			initiateMenuButtons();
			
			break;
		case MONEY:
			view = new Money(engine.getSemester(), engine.getWoche());
			Scene money = new Scene(view.getView());
			money.getStylesheets().add("/MainWindow.css");
			window.setScene(money);
			
			initiateMenuButtons();
			
			break;
		case BUY:
			view = new Buy(engine.getSemester(), engine.getWoche());
			Scene buy = new Scene(view.getView());
			buy.getStylesheets().add("/MainWindow.css");
			window.setScene(buy);
			
			initiateMenuButtons();
			
			break;
		}
	}
	
	private void initiateMenuButtons() {
        window.setOnCloseRequest(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Simulation speichern");
			alert.setHeaderText("Möchtest du den aktuellen Simulationsstand speichern?");
			alert.setContentText("Dadurch wird der alte Stand überschrieben.");
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
		Scene scene = window.getScene();
		Button save = (Button) scene.lookup("#button_view_save");
		if(save != null)
			save.setOnAction(e -> {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Simulation speichern");
					alert.setHeaderText("Möchtest du den aktuellen Simulationsstand speichern?");
					alert.setContentText("Dadurch wird der alte Stand überschrieben.");
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
		Button simulate = (Button) scene.lookup("#button_view_simulate");
		if(simulate != null)
			simulate.setOnAction(e -> {
					engine.simulate();
					setPage(pid);
				});
		Button overview = (Button) scene.lookup("#button_view_overview");
		if(overview != null)
			overview.setOnAction(e -> {
					setPage(OVERVIEW);
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