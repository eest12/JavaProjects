// CS2012 | Erika Estrada | Lab 9

package application;

import gui.MapPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MapPane pane = new MapPane("Scaryville State Home for the Criminally Insane",
				20, 20, 500, 500, .25, 4, 6, 2);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("application/application.css");
		primaryStage.setTitle("Scaryville State Home for the Criminally Insane");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
