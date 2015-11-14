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

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.prefs.Preferences;


import com.sun.glass.ui.GestureSupport;

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
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Die GUI dient als Einstieg in die Anwendung, da von hier die onClick Events optimal verarbeitet werden k�nnen.
 * Das Fenster wird initialisiert und mit Inhalt aus anderen Klassen die von View erben gef�llt.
 * 
 * @author Florian, Sebastian, Louisa
 */
public class GUI extends Application {

	private Scene MAIN, OVERVIEW, REPUTATION, SATISFACTION, STAFF, MONEY, MONEY_IN, MONEY_OUT, BUY, BUY2;
	
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

		MAIN = new Scene((new View("Hauptmen�", false, false, "Views/Main.fxml")).getView());
		OVERVIEW = new Scene((new View("�bersicht", true, true, "Views/Overview.fxml")).getView());
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
	 * Die �bergebene Scene wird ge�ffnet.
	 * 
	 * @param scene	Scene des Views
	 */
	private void setPage(Scene scene) {
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
					dialog.setHeaderText("W�hle deinen Spielstand aus");
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
				try {
				    //Die Klasse Media braucht eine URI
			        Media welcomeSound = new Media(new File("src/sounds/Willkommen.mp3").toURI().toString());
			        MediaPlayer mediaPlayer = new MediaPlayer(welcomeSound);
			        //Der Sound wird mit Hilfe der Media Player Klasse abgespielt
			        mediaPlayer.play();
			      } catch (Exception d) {
			        System.err.println(d.getMessage());
			      }
					System.out.println("More Stuff");//TODO
				});
		Button staff_less = (Button) STAFF.lookup("#button_staff_getLess");
		if(staff_less != null)
			staff_less.setOnMouseClicked(e -> {
				try {
				    //Die Klasse Media braucht eine URI
			        Media byeByeSound = new Media(new File("src/sounds/ByeBye.mp3").toURI().toString());
			        MediaPlayer mediaPlayer = new MediaPlayer(byeByeSound);
			        //Der Sound wird mit Hilfe der Media Player Klasse abgespielt
			        mediaPlayer.play();
			      } catch (Exception d) {
			        System.err.println(d.getMessage());
			      }
				System.out.println("Less Stuff");//TODO
				});
		Button money_more = (Button) STAFF.lookup("#button_staff_moneyMore");
		if(money_more != null)
			money_more.setOnMouseClicked(e -> {
				try {
				    //Die Klasse Media braucht eine URI
			        Media moreMoneySound = new Media(new File("src/sounds/Gehaltserhoehung.mp3").toURI().toString());
			        MediaPlayer mediaPlayer = new MediaPlayer(moreMoneySound);
			        //Der Sound wird mit Hilfe der Media Player Klasse abgespielt
			        mediaPlayer.play();
			      } catch (Exception d) {
			        System.err.println(d.getMessage());
			      }
				System.out.println("More Money");//TODO
				});
		Button money_less = (Button) STAFF.lookup("#button_staff_moneyLess");
		if(money_less != null)
			money_less.setOnMouseClicked(e -> {
				try {
				    //Die Klasse Media braucht eine URI
			        Media lessMoneySound = new Media(new File("src/sounds/GeldWirdGekuerzt.mp3").toURI().toString());
			        MediaPlayer mediaPlayer = new MediaPlayer(lessMoneySound);
			        //Der Sound wird mit Hilfe der Media Player Klasse abgespielt
			        mediaPlayer.play();
			      } catch (Exception d) {
			        System.err.println(d.getMessage());
			      }
				System.out.println("Less Money");//TODO
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
		PreDef.initButton((Button) MAIN.lookup("#button_main_start"));
		PreDef.initButton((Button) MAIN.lookup("#button_main_create"));
		PreDef.initButton((Button) MAIN.lookup("#button_main_load"));
		PreDef.initButton((Button) STAFF.lookup("#button_staff_getMore"));
		PreDef.initButton((Button) STAFF.lookup("#button_staff_getLess"));
		PreDef.initButton((Button) STAFF.lookup("#button_staff_moneyMore"));
		PreDef.initButton((Button) STAFF.lookup("#button_staff_moneyLess"));
		PreDef.initButton((Button) BUY.lookup("#button_buy_changeSIte"));
		PreDef.initButton((Button) BUY2.lookup("#button_buy2_changeSIte"));
	}
	
	private void updateLabels() {
		if(engine.hasPlayer()){
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_qualitydh"), ""+engine.getQualitaet(), 0.0);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_sales"), ""+engine.getKapital(), 0.2);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_lecturers"), ""+engine.getDozentZufriedenheit(), 1.4);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_reputation"), ""+engine.getAnsehen(), 0.5);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_venturer"), ""+engine.getPartnerunternehmenAnzahl(), 0.7);
			PreDef.initLabel((Label) OVERVIEW.lookup("#label_overview_students"), ""+engine.getStudentenZufriedenheit(), 0.8);

			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_quality"), ""+engine.getQualitaet(), 0.0);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_reputation"), ""+engine.getAnsehen(), 0.5);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber1"), ""+engine.getDozentenAnzahl(), 0.3);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_staffNumber2"), ""+engine.getDozentenAnzahl(), 0.3);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory1"), ""+engine.getInventar(), 0.3);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_inventory2"), ""+engine.getInventar(), 0.3);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events1"), ""+engine.getVeranstaltungen(), 0.6);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_events2"), ""+engine.getVeranstaltungen(), 0.6);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_food"), ""+engine.getEssen(), 0.9);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_money"), ""+engine.getKapital(), 0.1);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_tv"), ""+engine.getWerbung(), 0.5);
			PreDef.initLabel((Label) REPUTATION.lookup("#label_reputation_quality2"), ""+engine.getQualitaet(), 1.0);
			
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staff"), ""+engine.getDozentZufriedenheit(), 0.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_students"), ""+engine.getStudentenZufriedenheit(), 0.5);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_staffNumber"), ""+engine.getDozentenAnzahl(), 0.3);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory1"), ""+engine.getInventar(), 0.3);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_inventory2"), ""+engine.getInventar(), 0.3);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events1"), ""+engine.getVeranstaltungen(), 0.6);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_events2"), ""+engine.getVeranstaltungen(), 0.6);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food1"), ""+engine.getEssen(), 0.9);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_food2"), ""+engine.getEssen(), 0.9);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_money"), ""+engine.getDozentenGehalt(), 0.3);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_quality1"), ""+engine.getQualitaet(), 1.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_quality2"), ""+engine.getQualitaet(), 1.0);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation1"), ""+engine.getAnsehen(), 0.9);
			PreDef.initLabel((Label) SATISFACTION.lookup("#label_satisfaction_reputation2"), ""+engine.getAnsehen(), 0.9);
			
			PreDef.initLabel((Label) STAFF.lookup("#label_staff_satisfaction"), ""+engine.getDozentZufriedenheit(), 0.7);
			PreDef.initLabel((Label) STAFF.lookup("#label_staff_money"), ""+engine.getDozentenGehalt(), 0.0);
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
        	}
		});
	}
	
	/**
	 * Alle Events der oberen Men�buttons werden hier festgelegt.
	 */
	
	/**
	 * Der Statusbericht wird hier generiert
	 */
	
	private void status(){
		
		if(true /*Integer.parseInt(account.getWoche()) == 0*/){
			Alert status = new Alert(AlertType.INFORMATION);
			status.setTitle("Statusbericht");

			String statStuds = "Der Leiter der dualen Hochschule hat dieses Semester eine Ver�nderungen der Studentenzahl auf " + engine.getStudentenAnzahl() + " Studenten erreicht. ";
			String statQuality = "Der Leiter der dualen Hochschule bewirkte eine Ver�nderung ihrer Qualit�t auf " + engine.getQualitaet() + " .";
			String statCompanies = "Die Anzahl der Partnerunternehmen der dualen Hochschule hat sich dieses Semester auf " + engine.getPartnerunternehmenAnzahl() + " St�ck ver�ndert. ";
			String statSatStud = "Er erreichte es au�erdem die Zufriedenheit der Studenten auf einen Wert von " + engine.getStudentenZufriedenheit() + " zu bringen. ";
			String statSatStaff = "Des Weiteren ver�nderte sich die Zufriedenheit der Dozenten auf einen Wert von " + engine.getDozentZufriedenheit() + " . ";
			String statReputation = "Weiterhin hat sich dieses Semester eine Ver�nderung auf " + engine.getAnsehen() + " beim Ansehen der dualen Hochschule ergeben. ";
			String statMoney = "Eine weitere gro�e Ver�nderung ist der Umsatz der DH, welcher sich nach letztem Semester auf einen Wert von " + engine.getKapital() + " festsetzte. ";
			String statStaffMoney = "Was ebenfalls beachtet werden sollte, ist das aktuelle Gehalt der Dozenten, welches der DH mit " + engine.getDozentenGehalt() + " pro Dozent zu Buche schl�gt. ";
			String statEnd = "Insgesamt l�sst sich sagen, dass es beachtlich ist, wie die duale Hochschule sich letztes Semester ingesamt entwickelt hat. ";
			String statBegin = "Die duale Hochschule ist sehr vielen Menschen bekannt. Sie ist aktuell im " + engine.getSemester() + " und es ergaben sich so einige Ver�nderungen, die hier gleich erfahren werden. ";
			
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
			for (int i = 3; i>=0 ; --i){
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
			status.showAndWait();
		}
	}
	
	private void initiateTopMenu() {
		Scene scene = window.getScene();
		Button simulate = (Button) scene.lookup("#button_view_simulate");
		if(simulate != null)
			simulate.setOnAction(e -> {
					engine.simulate();
					updateLabels();
					status();
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