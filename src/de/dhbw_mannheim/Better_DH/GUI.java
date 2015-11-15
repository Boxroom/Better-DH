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

import java.awt.MouseInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Die GUI dient als Einstieg in die Anwendung, da von hier die onClick Events optimal verarbeitet werden können.
 * Das Fenster wird initialisiert und mit Inhalt aus anderen Klassen die von View erben gefüllt.
 * 
 * @author Florian, Sebastian, Louisa
 */
public class GUI extends Application {

	private Scene MAIN, OVERVIEW, REPUTATION, SATISFACTION, STAFF, MONEY, MONEY_IN, MONEY_OUT, BUY, BUY2;
	
	private Engine engine;
	private Stage window;
	private boolean simulateable;

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
		simulateable=false;
		window.setTitle("Better DH");
		window.getIcons().add(new Image("Images/BetterDH_Icon.png"));

		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		double x = userPrefs.getDouble("stage.x", 100);
		double y = userPrefs.getDouble("stage.y", 100);
		double w = userPrefs.getDouble("stage.width", 800);
		double h = userPrefs.getDouble("stage.height", 600);
		boolean m = userPrefs.getBoolean("stage.maximized", false);

		MAIN = new Scene((new View("Hauptmenü", false, false, "Views/Main.fxml")).getView());
		OVERVIEW = new Scene((new View("Übersicht", true, true, "Views/Overview.fxml")).getView());
		REPUTATION = new Scene((new View("Reputation", true, true, "Views/Reputation.fxml")).getView());
		SATISFACTION = new Scene((new View("Zufriedenheit", true, true, "Views/Satisfaction.fxml")).getView());
		STAFF = new Scene((new View("Personal", true, true, "Views/Staff.fxml")).getView());
		MONEY = new Scene((new View("Finanzen", true, true, "Views/Money.fxml")).getView());
		MONEY_IN = new Scene((new View("Einnahmen", true, true, "Views/Money_1.fxml")).getView());
		MONEY_OUT = new Scene((new View("Ausgaben", true, true, "Views/Money_1.fxml")).getView());
		BUY = new Scene((new View("Einkauf", true, true, "Views/Buy.fxml")).getView());
		BUY2 = new Scene((new View("Einkauf", true, true, "Views/Buy_2.fxml")).getView());
		initButtons();
		
		setPage(MAIN);
		initiateCloseWindow();
		updateLabels();
		
		window.setX(x);
		window.setY(y);
		window.setWidth(w);
		window.setHeight(h);
		window.setMaximized(m);
		window.show();
	}

	/**
	 * Die Applikation wird beendet. 
	 * Die Position und größe der Anwendung wird gespeichert.
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
	 * Die übergebene Scene wird geöffnet.
	 * 
	 * @param scene	Scene des Views
	 */
	private void setPage(Scene scene) {
		scene.getRoot();
		window.setScene(scene);
		if(scene != MAIN) {
			initiateLeftMenu(""+engine.getSemester(), ""+engine.getWoche());
			initiateTopMenu();
		}
	}
	
	private void initButtons(){
		Button start = (Button) MAIN.lookup("#button_main_start");
		if(start != null)
			start.setOnMouseClicked(e -> {
					if(engine.hasPlayer()){
						setPage(OVERVIEW);
						updateLabels();
						if(engine.getSemester()>6)
							simulateable=false;
						else
							simulateable=true;
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
			create.setOnMouseClicked(e -> {
					TextInputDialog dialog = new TextInputDialog(System.getProperty("user.name"));
					dialog.setTitle("Neuen Account erstellen");
					dialog.setHeaderText("Bitte gebe deinen Namen ein um einen\nneuen Spielstand zu erstellen.");
					dialog.setContentText("Name:");
					dialog.initOwner(window);

					Optional<String> result = dialog.showAndWait();
					result.ifPresent(name -> {
							if(engine.createPlayer(name)){
								Alert alert = new Alert(AlertType.INFORMATION);
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
			load.setOnMouseClicked(e -> {
					ArrayList<String> choices = engine.getAllNames();
					
					ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.isEmpty()?null:choices.get(0), choices);
					dialog.setTitle("Spiel laden");
					dialog.setHeaderText("Wähle deinen Spielstand aus");
					dialog.setContentText("Name:");
					dialog.initOwner(window);
					dialog.setGraphic(null);
					
					Optional<String> result = dialog.showAndWait();
					result.ifPresent(name -> {
							if(engine.loadPlayer(name))
								start.setDisable(false);
							else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Account defekt");
								alert.setHeaderText(null);
								alert.setContentText("Der Account konnte leider nicht geladen werden.\nEs scheint die Daten sind verloren...");
								alert.initOwner(window);
								alert.showAndWait();
							}
						});
				});
		Button staff_more = (Button) STAFF.lookup("#button_staff_getMore");
		if(staff_more != null)
			staff_more.setOnMouseClicked(e -> {
					Alert alert1 = PreDef.getSliderDialog("Dozenten einstellen", "Einstellen: ", "Anzahl der Dozenten erhöhen.", 0, 500-engine.getDozentenAnzahl(), 0, 1);
					alert1.initOwner(window);
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						Slider slider = (Slider) alert1.getGraphic();
						engine.addDozentenAnzahl((int) Math.round(slider.getValue()));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media welcomeSound = new Media(new File("src/sounds/Willkommen.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(welcomeSound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					}
				});
		Button staff_less = (Button) STAFF.lookup("#button_staff_getLess");
		if(staff_less != null)
			staff_less.setOnMouseClicked(e -> {
					Alert alert1 = PreDef.getSliderDialog("Dozenten feuern", "Entlassen: ", "Anzahl der Dozenten verringern.", 0, engine.getDozentenAnzahl(), 0, 1);
					alert1.initOwner(window);
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						Slider slider = (Slider) alert1.getGraphic();
						engine.addDozentenAnzahl((int) Math.round(-slider.getValue()));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media byeByeSound = new Media(new File("src/sounds/ByeBye.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(byeByeSound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					}
				});
		Button money_more = (Button) STAFF.lookup("#button_staff_moneyMore");
		if(money_more != null)
			money_more.setOnMouseClicked(e -> {
					Alert alert1 = PreDef.getSliderDialog("Gehälter erhöhen", "Aufschlag(€): ", "Lohn der Dozenten um den Betrag erhöhen.", 0, 10000-engine.getDozentenGehalt(), 0, 50);
					alert1.initOwner(window);
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						Slider slider = (Slider) alert1.getGraphic();
						engine.addDozentenGehalt((double) Math.round(slider.getValue()));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media moreMoneySound = new Media(
									new File("src/sounds/Gehaltserhoehung.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(moreMoneySound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					}
				});
		Button money_less = (Button) STAFF.lookup("#button_staff_moneyLess");
		if(money_less != null)
			money_less.setOnMouseClicked(e -> {
					Alert alert1 = PreDef.getSliderDialog("Gehälter kürzen", "Senkung(€): ", "Lohn der Dozenten um den Betrag verringern.", 0, engine.getDozentenGehalt()-5000, 0, 50);
					alert1.initOwner(window);
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						Slider slider = (Slider) alert1.getGraphic();
						engine.addDozentenGehalt((double) Math.round(-slider.getValue()));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media lessMoneySound = new Media(
									new File("src/sounds/GeldWirdGekuerzt.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(lessMoneySound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					}
				});
		Label revenue = (Label) MONEY.lookup("#label_money_revenue");
		if(revenue != null)
			revenue.setOnMouseClicked(e -> {
					setPage(MONEY_IN);
				});
		Label expenditure = (Label) MONEY.lookup("#label_money_expenditure");
		if(expenditure != null)
			expenditure.setOnMouseClicked(e -> {
					setPage(MONEY_OUT);
				});
		Button changeSIte = (Button) BUY.lookup("#button_buy_changeSIte");
		if(changeSIte != null)
			changeSIte.setOnMouseClicked(e -> {
					setPage(BUY2);
				});
		Button changeSIte2 = (Button) BUY2.lookup("#button_buy2_changeSIte");
		if(changeSIte2 != null)
			changeSIte2.setOnMouseClicked(e -> {
					setPage(BUY);
				});
		Button inventory = (Button) BUY.lookup("#button_buy_inventory");
		if(inventory != null)
			inventory.setOnMouseClicked(e -> {
				ChoiceDialog<String> rating = PreDef.getRatingDialog("Inventar", "Qualität des Inventars", null, ""+((int)engine.getInventar()));
				rating.initOwner(window);
				Optional<String> result = rating.showAndWait();
				result.ifPresent(choice -> {
						engine.setInventar(Integer.parseInt(choice));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media inventorySound = new Media(new File("src/sounds/NeuesInventar.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(inventorySound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					});
				});
		Button tv = (Button) BUY.lookup("#button_buy_tv");
		if(tv != null)
			tv.setOnMouseClicked(e -> {
				ChoiceDialog<String> rating = PreDef.getRatingDialog("Werbung", "Qualität der Werbung", null, ""+((int)engine.getWerbung()));
				rating.initOwner(window);
				Optional<String> result = rating.showAndWait();
				result.ifPresent(choice -> {
						engine.setWerbung(Integer.parseInt(choice));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media adSound = new Media(new File("src/sounds/HoertHoert.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(adSound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					});
				});
		Button events = (Button) BUY.lookup("#button_buy_events");
		if(events != null)
			events.setOnMouseClicked(e -> {
				ChoiceDialog<String> rating = PreDef.getRatingDialog("Veranstaltungen", "Qualität der Veranstaltungen", null, ""+((int)engine.getVeranstaltungen()));
				rating.initOwner(window);
				Optional<String> result = rating.showAndWait();
				result.ifPresent(choice -> {
						engine.setVeranstaltungen(Integer.parseInt(choice));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media eventsSound = new Media(new File("src/sounds/NeueEvents.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(eventsSound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					});
				});
		Button students = (Button) BUY2.lookup("#button_buy2_students");
		if(students != null)
			students.setOnMouseClicked(e -> {
					Alert alert1 = PreDef.getSliderDialog("Studentenplätze", "Gebäudekapazität: ", "Maximal Anzahl unterzubringender Studenten: (max 10.000)", 0, 10000, engine.getStudentenplaetze(), 100);
					alert1.initOwner(window);
					Optional<ButtonType> result = alert1.showAndWait();
					if (result.get() == ButtonType.OK) {
						Slider slider = (Slider) alert1.getGraphic();
						engine.setStudentenplaetze((int) Math.round(slider.getValue()));
						updateLabels();
						try {
							// Die Klasse Media braucht eine URI
							Media studentSound = new Media(new File("src/sounds/MehrPlaetze.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(studentSound);
							// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
							mediaPlayer.play();
						} catch (Exception d) {
						}
					}
				});
		Button food = (Button) BUY2.lookup("#button_buy2_food");
		if(food != null)
			food.setOnMouseClicked(e -> {
					ChoiceDialog<String> rating = PreDef.getRatingDialog("Essen", "Qualität des Essens", null, ""+((int)engine.getEssen()));
					rating.initOwner(window);
					Optional<String> result = rating.showAndWait();
					result.ifPresent(choice -> {
							engine.setEssen(Integer.parseInt(choice));
							updateLabels();
							try {
								// Die Klasse Media braucht eine URI
								Media foodSound = new Media(new File("src/sounds/BesseresEssen.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(foodSound);
								// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
								mediaPlayer.play();
							} catch (Exception d) {
							}
						});
				});
		PreDef.initButton((Button) MAIN.lookup("#button_main_start"), true);
		PreDef.initButton((Button) MAIN.lookup("#button_main_create"), true);
		PreDef.initButton((Button) MAIN.lookup("#button_main_load"), true);
		PreDef.initButton((Button) STAFF.lookup("#button_staff_getMore"), true);
		PreDef.initButton((Button) STAFF.lookup("#button_staff_getLess"), true);
		PreDef.initButton((Button) STAFF.lookup("#button_staff_moneyMore"), true);
		PreDef.initButton((Button) STAFF.lookup("#button_staff_moneyLess"), true);
		PreDef.initButton((Button) BUY.lookup("#button_buy_changeSIte"), true);
		PreDef.initButton((Button) BUY2.lookup("#button_buy2_changeSIte"), true);
		PreDef.initButton((Button) BUY.lookup("#button_buy_inventory"), true);
		PreDef.initButton((Button) BUY.lookup("#button_buy_tv"), true);
		PreDef.initButton((Button) BUY.lookup("#button_buy_events"), true);
		PreDef.initButton((Button) BUY2.lookup("#button_buy2_students"), true);
		PreDef.initButton((Button) BUY2.lookup("#button_buy2_food"), true);
	}
	
	private void updateLabels() {
		if(engine.hasPlayer()){
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_qualitydh"), ""+Math.round(engine.getQualitaet())+" %", engine.getQualitaet()/100.0);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_sales"), ""+Math.round(engine.getKapital()*100.0)/100.0+" €", engine.getKapital()/(engine.capitalMAX/10));
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_lecturers"), ""+Math.round(engine.getDozentZufriedenheit())+" %", engine.getDozentZufriedenheit()/100.0, ""+Math.round(engine.getDozentenAnzahl())+" Dozenten");
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_reputation"), ""+Math.round(engine.getAnsehen())+" %", engine.getAnsehen()/100.0);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_venturer"), ""+Math.round(engine.getPartnerunternehmenAnzahl()), engine.getPartnerunternehmenAnzahl()/1000.0);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_students"), ""+Math.round(engine.getStudentenZufriedenheit())+" %", engine.getStudentenZufriedenheit()/100.0, ""+Math.round(engine.getStudentenAnzahl())+" Studenten");

			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_quality"), ""+Math.round(engine.getQualitaet())+" %", engine.getQualitaet()/100.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_reputation"), ""+Math.round(engine.getAnsehen())+" %", engine.getAnsehen()/100.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber1"), ""+engine.getDozentenAnzahl()+" Dozenten", engine.getDozentenAnzahl()/(engine.amountLecturerMAX*3/4));
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber2"), ""+engine.getDozentenAnzahl()+" Dozenten", engine.getDozentenAnzahl()/(engine.amountLecturerMAX*3/4));
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory1"), ""+engine.getInventar()+" Sterne", (engine.getInventar())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory2"), ""+engine.getInventar()+" Sterne", (engine.getInventar())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events1"), ""+engine.getVeranstaltungen()+" Sterne", (engine.getVeranstaltungen())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events2"), ""+engine.getVeranstaltungen()+" Sterne", (engine.getVeranstaltungen())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_food"), ""+engine.getEssen()+" Sterne", (engine.getEssen())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_money"), ""+Math.round(engine.getKapital()*100.0)/100.0+" €", engine.getKapital()/(engine.capitalMAX/10));
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_tv"), ""+engine.getWerbung()+" Sterne", (engine.getWerbung())/5.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_quality2"), ""+Math.round(engine.getQualitaet())+" %", engine.getQualitaet()/100.0);
			
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staff"), ""+Math.round(engine.getDozentZufriedenheit())+" %", engine.getDozentZufriedenheit()/100.0, ""+Math.round(engine.getDozentenAnzahl())+" Dozenten");
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_students"), ""+Math.round(engine.getStudentenZufriedenheit())+" %", engine.getStudentenZufriedenheit()/100.0, ""+Math.round(engine.getStudentenAnzahl())+" Studenten");
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staffNumber"), ""+engine.getDozentenAnzahl()+" Dozenten", engine.getDozentenAnzahl()/(engine.amountLecturerMAX*3/4));
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory1"), ""+engine.getInventar()+" Sterne", (engine.getInventar())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory2"), ""+engine.getInventar()+" Sterne", (engine.getInventar())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events1"), ""+engine.getVeranstaltungen()+" Sterne", (engine.getVeranstaltungen())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events2"), ""+engine.getVeranstaltungen()+" Sterne", (engine.getVeranstaltungen())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food1"), ""+engine.getEssen()+" Sterne", (engine.getEssen())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food2"), ""+engine.getEssen()+" Sterne", (engine.getEssen())/5.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_money"), ""+Math.round(engine.getDozentenGehalt()*100.0)/100.0+" €", engine.getDozentenGehalt()/(engine.salaryLecturerMAX*3/4));
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_quality1"), ""+Math.round(engine.getQualitaet())+" %", engine.getQualitaet()/100.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_quality2"), ""+Math.round(engine.getQualitaet())+" %", engine.getQualitaet()/100.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation1"), ""+Math.round(engine.getAnsehen())+" %", engine.getAnsehen()/100.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation2"), ""+Math.round(engine.getAnsehen())+" %", engine.getAnsehen()/100.0);
			
			PreDef.initLabel((Label) STAFF.lookup("#label_staff_satisfaction"), ""+Math.round(engine.getDozentZufriedenheit())+" %", engine.getDozentZufriedenheit()/100.0, ""+Math.round(engine.getDozentenAnzahl())+" Dozenten");
			PreDef.initLabel((Label) STAFF.lookup("#label_staff_money"), ""+Math.round(engine.getDozentenGehalt()*100.0)/100.0+" €", 0.0);
			
			PreDef.initLabel((Label) MONEY.lookup("#label_money_sales"), ""+Math.round(engine.getKapital()*100.0)/100.0+" €", 0.0);
			PreDef.initLabel((Label) MONEY.lookup("#label_money_revenue"), ""+Math.round(engine.getEinnahmen()*100.0)/100.0+" €", 0.0);
			PreDef.initLabel((Label) MONEY.lookup("#label_money_expenditure"), ""+Math.round(engine.getAusgaben()*100.0)/100.0+" €", 0.0);

			PreDef.initLabel((Label) BUY.lookup("#label_buy_inventory"), ""+engine.getInventar()+" Sterne", 0.0);
			PreDef.initLabel((Label) BUY.lookup("#label_buy_tv"), ""+engine.getWerbung()+" Sterne", 0.0);
			PreDef.initLabel((Label) BUY.lookup("#label_buy_events"), ""+engine.getVeranstaltungen()+" Sterne", 0.0);
			PreDef.initLabel((Label) BUY2.lookup("#label_buy2_students"), ""+engine.getStudentenplaetze(), 0.0);
			PreDef.initLabel((Label) BUY2.lookup("#label_buy2_food"), ""+engine.getEssen()+" Sterne", 0.0);
			
			HBox img_inv = (HBox) BUY.lookup("#image_buy_inventory");
			switch(engine.getInventar()){
			case 1: img_inv.setStyle("-fx-background-image: url('./Images/Grafiken/Inventar/Tacker.png')"); break;
			case 2: case 3: img_inv.setStyle("-fx-background-image: url('./Images/Grafiken/Inventar/computer-313456_1280.png')"); break;
			case 4: img_inv.setStyle("-fx-background-image: url('./Images/Grafiken/Inventar/monitor-32743_1280.png')"); break;
			case 5: img_inv.setStyle("-fx-background-image: url('./Images/Grafiken/Inventar/laptop-33521_1280.png')"); break;
			}
			
			HBox img_tv = (HBox) BUY.lookup("#image_buy_tv");
			switch(engine.getWerbung()){
			case 1: case 2: img_tv.setStyle("-fx-background-image: url('./Images/Grafiken/Werbung/Newsletter.png')"); break;
			case 3: img_tv.setStyle("-fx-background-image: url('./Images/Grafiken/Werbung/Bild6.png')"); break;
			case 4: img_tv.setStyle("-fx-background-image: url('./Images/Grafiken/Werbung/Megaphon.png')"); break;
			case 5: img_tv.setStyle("-fx-background-image: url('./Images/Grafiken/Werbung/Bild5.png')"); break;
			}
			
			HBox img_students = (HBox) BUY2.lookup("#image_buy2_students");
			if(engine.getStudentenplaetze() <= 1000){
				img_students.setStyle("-fx-background-image: url('./Images/Grafiken/Gebäude/Bild4.png')");
			} else if(engine.getStudentenplaetze() <= 3500){
				img_students.setStyle("-fx-background-image: url('./Images/Grafiken/Gebäude/Bild3.png')");
			} else if(engine.getStudentenplaetze() <= 7000){
				img_students.setStyle("-fx-background-image: url('./Images/Grafiken/Gebäude/Bild2.png')");
			} else {
				img_students.setStyle("-fx-background-image: url('./Images/Grafiken/Gebäude/Bild1.png')");
			}
			
			HBox img_food = (HBox) BUY2.lookup("#image_buy2_food");
			switch(engine.getEssen()){
			case 1: img_food.setStyle("-fx-background-image: url('./Images/Grafiken/Essen/Apfel.png')"); break;
			case 2: case 3: img_food.setStyle("-fx-background-image: url('./Images/Grafiken/Essen/Spaghetti.png')"); break;
			case 4: img_food.setStyle("-fx-background-image: url('./Images/Grafiken/Essen/NuggetsPommes.png')"); break;
			case 5: img_food.setStyle("-fx-background-image: url('./Images/Grafiken/Essen/Festmahl.png')"); break;
			}
		}
	}
	
	/**
	 * Alle Events des Fensters werden hier festgelegt.
	 */
	private void initiateCloseWindow() {
        window.setOnCloseRequest(e -> {
        	if(engine.hasPlayer()) {
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
        	}
		});
	}
	
	/**
	 * Alle Events der oberen Menübuttons werden hier festgelegt.
	 */
	
	/**
	 * Der Statusbericht wird hier generiert
	 */
	private void status(boolean end){
		Random incident = new Random();
		incident.setSeed((long) (System.currentTimeMillis()*MouseInfo.getPointerInfo().getLocation().getX()*MouseInfo.getPointerInfo().getLocation().getY()));
		if(engine.getWoche() == 1){
			Alert status = new Alert(AlertType.INFORMATION);
			status.setTitle(end?"Abschlussbericht":"Statusbericht");

			String statStuds = "Der Leiter der dualen Hochschule hat dieses Semester eine Veränderungen der Studentenzahl auf " + engine.getStudentenAnzahl() + " Studenten erreicht. ";
			String statQuality = "Der Leiter der dualen Hochschule bewirkte eine Veränderung ihrer Qualität auf " + (int)engine.getQualitaet() + "%. ";
			String statCompanies = "Die Anzahl der Partnerunternehmen der dualen Hochschule hat sich dieses Semester auf " + engine.getPartnerunternehmenAnzahl() + " Stück verändert. ";
			String statSatStud = "Er erreichte es außerdem die Zufriedenheit der Studenten auf einen Wert von " + (int)engine.getStudentenZufriedenheit() + "% zu bringen. ";
			String statSatStaff = "Des Weiteren veränderte sich die Zufriedenheit der Dozenten auf einen Wert von " + (int)engine.getDozentZufriedenheit() + "%. ";
			String statReputation = "Weiterhin hat sich dieses Semester eine Veränderung auf " + (int)engine.getAnsehen() + "% beim Ansehen der dualen Hochschule ergeben. ";
			String statMoney = "Eine weitere große Veränderung ist der Umsatz der DH, welcher sich nach letztem Semester auf einen Wert von " + (int)engine.getKapital() + "€ festsetzte. ";
			String statStaffMoney = "Was ebenfalls beachtet werden sollte, ist das aktuelle Gehalt der Dozenten, welches der DH mit " + (int)engine.getDozentenGehalt() + "€ pro Dozent zu Buche schlägt. ";
			String statEnd = "Insgesamt lässt sich sagen, dass es beachtlich ist, wie die duale Hochschule sich letztes Semester ingesamt entwickelt hat. ";
			String statBegin = "Die duale Hochschule ist sehr vielen Menschen bekannt. Sie ist aktuell im " + engine.getSemester() + " Semester und es ergaben sich so einige Veränderungen, die hier gleich erfahren werden. ";
			
			String[] statFirstArr = new String[2];
			String[] statFirstRand = new String[2];
			statFirstArr[0] = statStuds;
			statFirstArr[1] = statQuality;
			Random rand1 = new Random();
			int rand1Int = rand1.nextInt();
			int z1 = rand1Int % 2;
			z1 = Math.abs(z1);
			for (int i = 1; i>=0 ; --i){
				statFirstRand[z1] = statFirstArr[i];
				z1++;
				z1 = z1 % 2;
			}
			
			String[] statSecArr = new String[6];
			String[] statSecRand = new String[6];
			statSecArr[0] = statCompanies;
			statSecArr[1] = statSatStud;
			statSecArr[2] = statSatStaff;
			statSecArr[3] = statReputation;
			statSecArr[4] = statMoney;
			statSecArr[5] = statStaffMoney;
			Random rand2 = new Random();
			int rand2Int = rand2.nextInt();
			int z2 = rand2Int % 6;
			z2 = Math.abs(z2);
			for (int i = 5; i>=0 ; --i){
				statSecRand[z2] = statSecArr[i];
				z2++;
				z2 = z2 % 6;
			}
			String statusContent = statBegin + statFirstRand[0] + statSecRand[0] + statSecRand[1] + statSecRand[2] + statSecRand[3] + statSecRand[4] + statSecRand[5] + statFirstRand[1] + statEnd;
			status.setContentText(statusContent);
			status.setHeaderText("Aktuelles vom Campus");
			ImageView graphic = new ImageView("./Images/Grafiken/Werbung/Bild6.png");
			graphic.setFitWidth(200);
			graphic.setFitHeight(130);
			status.setGraphic(graphic);
			status.initOwner(window);
			try {
				// Die Klasse Media braucht eine URI
				Media foodSound = new Media(new File("src/sounds/applause.wav").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(foodSound);
				// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
				mediaPlayer.play();
			} catch (Exception d) {
			}
			status.showAndWait();
		} else if(incident.nextInt() % 20 == 0){
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Unvorhergesehenes Ereignis");
			switch(incident.nextInt() % 5){
			default:
			case 0:
				alert1.setHeaderText("Streik");
				alert1.setContentText("");
				engine.addKosten(20000*(1+(incident.nextInt()%10)/10));
				break;
			case 1:
				alert1.setHeaderText("Heizungsausfall");
				alert1.setContentText("");
				engine.addKosten(25000*(1+(incident.nextInt()%10)/10));
				break;
			case 2:
				alert1.setHeaderText("Rohrbruch");
				alert1.setContentText("");
				engine.addKosten(50000*(1+(incident.nextInt()%10)/10));
				break;
			case 3:
				alert1.setHeaderText("Serverausfall");
				alert1.setContentText("");
				engine.addKosten(10000*(1+(incident.nextInt()%10)/10));
				break;
			case 4:
				alert1.setHeaderText("Amoklauf");
				alert1.setContentText("");
				engine.addKosten(250000*(1+(incident.nextInt()%10)/10));
				break;
			}
			alert1.initOwner(window);
			try {
				// Die Klasse Media braucht eine URI
				Media foodSound = new Media(new File("src/sounds/coin-fall.wav").toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(foodSound);
				// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
				mediaPlayer.play();
			} catch (Exception d) {
			}
			alert1.showAndWait();
		}
	}
	
	private void initiateTopMenu() {
		Scene scene = window.getScene();
		Button simulate = (Button) scene.lookup("#button_view_simulate");
		if(simulate != null)
			PreDef.initButton(simulate, false);
			simulate.setOnMouseClicked(e -> {
					if(simulateable){
						if(engine.simulate()){
							status(false);
						} else {
							simulateable = false;
							status(true);
						}
						updateLabels();
						updateDate(""+engine.getSemester(), ""+engine.getWoche());
					}
				});
		Button save = (Button) scene.lookup("#button_view_save");
		if(save != null)
			PreDef.initButton(save, false);
			save.setOnMouseClicked(e -> {
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
	}
	
	/**
	 * Update des Labels zum anzeigen der aktuellen Zeit
	 * @param semester
	 * @param week
	 */
	private void updateDate(String semester, String week){
		Scene scene = window.getScene();
		Label date = (Label) scene.lookup("#label_view_date");
		if(date != null)
			date.setText("Semester "+semester+" / Woche "+week);
	}
	
	/**
	 * Alle Events der linken Menübuttons werden hier festgelegt.
	 * Sowie das Label mit dem aktuellen Semester/Wochen fortschritt.
	 */
	private void initiateLeftMenu(String semester, String week) {
		Scene scene = window.getScene();
		updateDate(semester, week);
		Button overview = (Button) scene.lookup("#button_view_overview");
		if(overview != null)
			PreDef.initButton(overview, false);
			overview.setOnMouseClicked(e -> {
					setPage(OVERVIEW);
				});
		Button reputation = (Button) scene.lookup("#button_view_reputation");
		if(reputation != null)
			PreDef.initButton(reputation, false);
			reputation.setOnMouseClicked(e -> {
					setPage(REPUTATION);
				});
		Button satisfaction = (Button) scene.lookup("#button_view_satisfaction");
		if(satisfaction != null)
			PreDef.initButton(satisfaction, false);
			satisfaction.setOnMouseClicked(e -> {
					setPage(SATISFACTION);
				});
		Button staff = (Button) scene.lookup("#button_view_staff");
		if(staff != null)
			PreDef.initButton(staff, false);
			staff.setOnMouseClicked(e -> {
					setPage(STAFF);
				});
		Button money = (Button) scene.lookup("#button_view_money");
		if(money != null)
			PreDef.initButton(money, false);
			money.setOnMouseClicked(e -> {
					setPage(MONEY);
				});
		Button buy = (Button) scene.lookup("#button_view_buy");
		if(buy != null)
			PreDef.initButton(buy, false);
			buy.setOnMouseClicked(e -> {
					setPage(BUY);
				});
	}
}