// CS2012 | Erika Estrada | Lab 10

package application;

import gui.AttackMonitorPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AttackMonitorPane monitorPane = new AttackMonitorPane();
		Scene scene = new Scene(monitorPane);
		scene.getStylesheets().add("application/application.css");
		primaryStage.setTitle("Monster Attack Records");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
