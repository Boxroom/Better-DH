package de.dhbw_mannheim.Better_DH;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Diese Klasse hat lediglich statische Elemente und wird zu keiner Zeit instanziiert.
 * Enthalten sind Codesegmente, die eventuell von mehreren Klassen ben�tigt werden k�nnen um z.B. einen global
 * gleich aussehenden Button zu erstellen.
 * 
 * @author Florian
 */
public class PreDef {
	
	/**
	 * Es wird ein Platzhalter erstellt
	 *
	 * @return		Eine HBox die so breit ist, das jeder Parent Node, in dem diese HBox plaziert wird,
	 * 				komplett in der Breite ausgef�llt wird.
	 */
	public static HBox fill_width() {
		HBox fill_width = new HBox();
		fill_width.setPrefWidth(Integer.MAX_VALUE);
		fill_width.setPrefHeight(Integer.MAX_VALUE);
		return fill_width;
	}
	
	/**
	 * Standardbuttons werden hier erstellt und zur�ckgegeben
	 *
	 * @param text	Beschriftung des Buttons
	 * @param id	Identifikationsstring
	 * @return		Standardisierter Button
	 */
	public static Button button(String text, String id) {
		Button button = new Button(text);
		button.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		button.setAlignment(Pos.CENTER);
		button.getStyleClass().add("button-default");
		button.setId(id);
        
		return button;
	}

	/**
	 * Standardbuttons werden hier erstellt und zur�ckgegeben
	 *
	 * @param text	Beschriftung des Buttons
	 * @param id	Identifikationsstring
	 * @return		Standardisierter Button f�r oberes Men�
	 */
	public static Button button_menu(String text, String id) {
		Button button = new Button(text);
		button.setAlignment(Pos.CENTER);
		button.setPrefSize(100, 50);
		button.getStyleClass().add("button-menu");
		button.setId(id);
		return button;
	}

	/**
	 * Alle Buttons auf den Fenstern werden nach dem selben Muster initialisiert.
	 * 
	 * @param button	Button
	 * @param resize	Vergr��ern mit Fenster
	 */
	public static void initButton(Button button, boolean resize) {
		if(button != null) {
			if(resize){
				DoubleProperty fontSize = new SimpleDoubleProperty(30);
		        fontSize.bind(button.widthProperty().add(button.heightProperty()).divide(17));
		        button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
			}
	        button.setOnAction(e -> {
		        	try {
					// Die Klasse Media braucht eine URI
					Media buttonSound = new Media(GUI.class.getResource("/sounds/two_tone_nav.mp3").toExternalForm().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
					// Der Sound wird mit Hilfe der Media Player Klasse abgespielt
					mediaPlayer.play();
					} catch (Exception d) {
					}
	        	});
			
		}
	}

	/**
	 * Alle Labels auf den Fenstern werden nach dem selben Muster initialisiert.
	 * initLabel ohne tooltip bzw. tooltip == null zeigt die Daten aus insert als Tooltip an
	 * 
	 * @param label			Label mit Progressbalken
	 * @param insert		Beschreibung des Inhaltes des Progress Balken
	 * @param progress		Fortschritt des Progressbalken in Prozent 0.0-1.0
	 */
	public static void initLabel(Label label, String insert, double progress) {
		initLabel(label, insert, progress, null);
	}

	/**
	 * Alle Labels auf den Fenstern werden nach dem selben Muster initialisiert.
	 * 
	 * @param label			Label mit Progressbalken
	 * @param insert		Beschreibung des Inhaltes des Progress Balken
	 * @param progress		Fortschritt des Progressbalken in Prozent 0.0-1.0
	 * @param tooltip		Tooltip f�r Progressbar, sofern der Inhalt von der Variable insert abweichen soll
	 */
	public static void initLabel(Label label, String insert, double progress, String tooltip) {
		if(label != null) {
			DoubleProperty fontSize = new SimpleDoubleProperty(30);
	        fontSize.bind(label.widthProperty().add(label.heightProperty()).divide(17));
	        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
			if(label.getGraphic() != null && label.getGraphic() instanceof Label){
				Label label2 = (Label) label.getGraphic();
				label2.setText(insert);
				
		        label2.styleProperty().bind(label.styleProperty());
		        
				if(label2.getGraphic() != null && label2.getGraphic() instanceof ProgressBar) {
					ProgressBar bar = (ProgressBar) label2.getGraphic();
					bar.setProgress(progress);
					bar.setTooltip(new Tooltip(""+(tooltip==null?(Math.min(Math.round(progress*100),100)+"%"):tooltip)));
					label2.prefWidthProperty().bind(label.widthProperty());
					bar.prefWidthProperty().bind(label2.widthProperty());
					bar.setPadding(new Insets(0,15,0,15));
					bar.getStyleClass().remove("red-bar");
					bar.getStyleClass().remove("orange-bar");
					bar.getStyleClass().remove("green-bar");
			        
			        if(progress < 0.5) {
						bar.getStyleClass().add("red-bar");
					} else if(progress < 0.75) {
						bar.getStyleClass().add("orange-bar");
					} else {
						bar.getStyleClass().add("green-bar");
					}
				}
			} else if(label.getGraphic() != null && label.getGraphic() instanceof ProgressBar){
				ProgressBar bar = (ProgressBar) label.getGraphic();
				bar.setProgress(progress);
				bar.setTooltip(new Tooltip(""+(tooltip==null?insert:tooltip)));
				bar.getStyleClass().remove("red-bar");
				bar.getStyleClass().remove("orange-bar");
				bar.getStyleClass().remove("green-bar");
		        
			    if(progress <= 0.35) {
			        bar.getStyleClass().add("red-bar");
				} else if(progress <= 0.65) {
					bar.getStyleClass().add("orange-bar");
				} else {
					bar.getStyleClass().add("green-bar");
				}
			}
		}
	}
	
	/**
	 * Dialogfenster mit einem Slider
	 * 
	 * @param title		Text f�r den Dialogtitel
	 * @param head		Text f�r die Anzeige neben dem Slider
	 * @param content	Text f�r die Beschreibung des Fensters
	 * @param min		Minimaler Wert des Slider
	 * @param max		Maximaler Wert des Slider
	 * @param start		Startwert beim �ffnen des Slider
	 * @param scale		Schrittweite des Slider
	 * @return			Das Dialogfenster mit einem Slider
	 */
	public static Alert getSliderDialog(String title, String head, String content, double min, double max, double start, double scale) {
		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setTitle(title);
		alert1.setHeaderText(""+head+""+start);
		alert1.setContentText(content);
		Slider slider = new Slider(min, max, scale);
		slider.setPrefWidth(300);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(5);
		slider.setMinorTickCount(4);
		slider.setValue(start);
		slider.setBlockIncrement(scale);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observable,
		            Number oldValue, Number newValue) {
				alert1.setHeaderText(head+Math.round(newValue.doubleValue()*(1/scale))/(1/scale));
		    }
		});
		alert1.setGraphic(slider);
		
		return alert1;
	}
	
	/**
	 * Dialogfenster mit Rating
	 * 
	 * @param title		Titel des Dialogfenster
	 * @param head		Beschreibung des Fensters
	 * @param def		Aktueller Ratingwert der als Auswahl des Dropdown vordefiniert wird
	 * @return			Das Dialogfenster mit einer Dropdown auswahl von 1 bis 5
	 */
	public static ChoiceDialog<String> getRatingDialog(String title, String head, String def) {
		String[] list = {"1", "2", "3", "4", "5"};
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>(def, list);
		dialog.setTitle(title);
		dialog.setHeaderText(head);
		dialog.setContentText("Sterne: ");
		dialog.setGraphic(null);
		
		return dialog;
	}
}
