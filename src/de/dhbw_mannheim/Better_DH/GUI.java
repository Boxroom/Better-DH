/**
 * 
 */
package de.dhbw_mannheim.Better_DH;

import java.util.prefs.Preferences;

import de.dhbw_mannheim.Better_DH.Views.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Florian
 *
 */
public class GUI extends Application {

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

		setPage(0);
		
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
		switch(id){
		case 0:
			view = new Main();
			Scene scene = new Scene(view.getView());
			scene.getStylesheets().add("/MainWindow.css");
			window.setScene(scene);
			break;
		}
	}
}