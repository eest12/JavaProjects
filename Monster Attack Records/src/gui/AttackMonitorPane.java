// CS2012 | Erika Estrada | Lab 10

package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import data.BinaryMonsterPersister;
import data.MonsterAttack;
import data.MonsterPersister;
import data.TextMonsterPersister;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AttackMonitorPane extends BorderPane {
	
	private ArrayList<MonsterAttack> attackList = new ArrayList<>();
	
	public AttackMonitorPane() {
		setPrefWidth(800);
		setPrefHeight(400);
		generateTestData();
		generateMenu();
		displayAttacks();
	}
	
	/*
	 * Populates attackList with 3 attacks for testing
	 */
	private void generateTestData() {
		attackList.add(new MonsterAttack(125, "06/25/2009", "Godzilla", "Tokyo", "Kyohei Yamane"));
		attackList.add(new MonsterAttack(583, "03/10/2017", "King Kong", "New York", "Carl Denham"));
		attackList.add(new MonsterAttack(886, "11/02/2001", "Randall Boggs", "Monstropolis", "James Sullivan"));
	}
	
	/*
	 * Generates the menu to view, add, delete, save, and load data
	 */
	private void generateMenu() {
		Label menuTitleLbl = new Label("Menu");
		menuTitleLbl.getStyleClass().add("menu-title");
		
		EventHandler<Event> mouseEnteredHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Label srcLbl = (Label) event.getSource();
				srcLbl.getStyleClass().clear();
				srcLbl.getStyleClass().add("menu-hover");
			}
		};
		
		EventHandler<Event> mouseExitedHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Label srcLbl = (Label) event.getSource();
				srcLbl.getStyleClass().clear();
				srcLbl.getStyleClass().add("menu-text");
			}
		};
		
		Label showAttacksLbl = new Label("• View list of attacks");
		showAttacksLbl.getStyleClass().add("menu-text");
		showAttacksLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		showAttacksLbl.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		showAttacksLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				displayAttacks();
			}
		});
		
		Label inputAttackLbl = new Label("• Add a monster attack");
		inputAttackLbl.getStyleClass().add("menu-text");
		inputAttackLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		inputAttackLbl.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		inputAttackLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				displayInputScreen();
			}
		});
		
		Label deleteAttackLbl = new Label("• Delete an attack");
		deleteAttackLbl.getStyleClass().add("menu-text");
		deleteAttackLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		deleteAttackLbl.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		deleteAttackLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				displayDeleteScreen();
			}
		});
		
		Label saveAttacksLbl = new Label("• Save attacks");
		saveAttacksLbl.getStyleClass().add("menu-text");
		saveAttacksLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		saveAttacksLbl.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		saveAttacksLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				displaySaveScreen();
			}
		});
		
		Label loadAttacksLbl = new Label("• Load attacks");
		loadAttacksLbl.getStyleClass().add("menu-text");
		loadAttacksLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnteredHandler);
		loadAttacksLbl.addEventHandler(MouseEvent.MOUSE_EXITED, mouseExitedHandler);
		loadAttacksLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				displayLoadScreen();
			}
		});
		
		VBox menuBox = new VBox();
		menuBox.getStyleClass().add("menu");
		menuBox.getChildren().add(menuTitleLbl);
		menuBox.getChildren().add(showAttacksLbl);
		menuBox.getChildren().add(inputAttackLbl);
		menuBox.getChildren().add(deleteAttackLbl);
		menuBox.getChildren().add(saveAttacksLbl);
		menuBox.getChildren().add(loadAttacksLbl);
		setLeft(menuBox);
	}
	
	/*
	 * Shows the list of attacks
	 */
	private void displayAttacks() {
		VBox attackListBox = new VBox();
		attackListBox.getStyleClass().add("box");
		
		Label attacksTitleLbl = new Label("Attacks");
		attacksTitleLbl.getStyleClass().add("body-title");
		attackListBox.getChildren().add(attacksTitleLbl);
		
		if (attackList.isEmpty()) {
			Label noAttacksLbl = new Label("No attacks to show");
			noAttacksLbl.getStyleClass().add("body-text");
			attackListBox.getChildren().add(noAttacksLbl);
		} else {
			for (MonsterAttack attack : attackList) {
				Label attackLbl = new Label(attack.toString());
				attackLbl.getStyleClass().add("body-text");
				attackListBox.getChildren().add(attackLbl);
			}
		}
		
		setCenter(attackListBox);
	}
	
	/*
	 * Displays the elements for the user to add a monster attack
	 */
	private void displayInputScreen() {
		Label inputTitleLbl = new Label("Add a monster attack");
		inputTitleLbl.getStyleClass().add("body-title");
		
		// attack ID
		Label attackIdLbl = new Label("Attack ID:");
		attackIdLbl.getStyleClass().add("body-text");
		TextField attackIdTxt = new TextField();
		HBox attackIdBox = new HBox();
		attackIdBox.getChildren().add(attackIdLbl);
		attackIdBox.getChildren().add(attackIdTxt);
		
		// date
		Label dateLbl = new Label("Date (MM/DD/YYYY):");
		dateLbl.getStyleClass().add("body-text");
		ComboBox monthCombo = new ComboBox();
		monthCombo.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12");
		monthCombo.setValue("01");
		ComboBox dayCombo = new ComboBox();
		dayCombo.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
		dayCombo.setValue("01");
		TextField yearTxt = new TextField();
		HBox dateBox = new HBox();
		dateBox.getChildren().add(dateLbl);
		dateBox.getChildren().add(monthCombo);
		dateBox.getChildren().add(dayCombo);
		dateBox.getChildren().add(yearTxt);
		
		// monster name
		Label monsterNameLbl = new Label("Monster name:");
		monsterNameLbl.getStyleClass().add("body-text");
		TextField monsterNameTxt = new TextField();
		HBox monsterNameBox = new HBox();
		monsterNameBox.getChildren().add(monsterNameLbl);
		monsterNameBox.getChildren().add(monsterNameTxt);
		
		// location
		Label locationLbl = new Label("Attack Location:");
		locationLbl.getStyleClass().add("body-text");
		TextField locationTxt = new TextField();
		HBox locationBox = new HBox();
		locationBox.getChildren().add(locationLbl);
		locationBox.getChildren().add(locationTxt);
		
		// reporter
		Label reporterLbl = new Label("Name of person reporting:");
		reporterLbl.getStyleClass().add("body-text");
		TextField reporterTxt = new TextField();
		HBox reporterBox = new HBox();
		reporterBox.getChildren().add(reporterLbl);
		reporterBox.getChildren().add(reporterTxt);
		
		// save button
		Button saveBtn = new Button("Save");
		saveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Button srcBtn = (Button) event.getSource();
				if (srcBtn.getText().equals("Save")) {
					try {
						int attackID = Integer.parseInt(attackIdTxt.getText());
						String date = (String) monthCombo.getValue() + "/" + (String) dayCombo.getValue() + "/" + yearTxt.getText();
						String monsterName = monsterNameTxt.getText();
						String location = locationTxt.getText();
						String reporter = reporterTxt.getText();
						MonsterAttack attack = new MonsterAttack(attackID, date, monsterName, location, reporter);
						attackList.add(attack);
						
						attackIdTxt.setEditable(false);
						monsterNameTxt.setEditable(false);
						locationTxt.setEditable(false);
						reporterTxt.setEditable(false);
						
						srcBtn.setText("New attack");
					} catch (NumberFormatException e) {
						numberErrorMessage();
					}
				} else if (srcBtn.getText().equals("New attack")) {
					displayInputScreen();
				}
			}
		});
		
		VBox inputBox = new VBox();
		inputBox.getStyleClass().add("box");
		inputBox.getChildren().add(inputTitleLbl);
		inputBox.getChildren().add(attackIdBox);
		inputBox.getChildren().add(dateBox);
		inputBox.getChildren().add(monsterNameBox);
		inputBox.getChildren().add(locationBox);
		inputBox.getChildren().add(reporterBox);
		inputBox.getChildren().add(saveBtn);
		setCenter(inputBox);
	}
	
	/*
	 * Displays a message asking the user for correct input for the integer fields
	 */
	private void numberErrorMessage() {
		Alert dialog = new Alert(Alert.AlertType.ERROR);
		dialog.setContentText("Please make sure Attack ID and Year are both integers.");
		dialog.showAndWait();
	}
	
	/*
	 * Displays the list of attacks, which the user can select in order to delete them
	 */
	private void displayDeleteScreen() {
		VBox attackListBox = new VBox();
		attackListBox.getStyleClass().add("box");
		
		Label deleteTitleLbl = new Label("Delete an attack");
		deleteTitleLbl.getStyleClass().add("body-title");
		attackListBox.getChildren().add(deleteTitleLbl);
		
		if (attackList.isEmpty()) {
			Label noAttacksLbl = new Label("No attacks to delete");
			noAttacksLbl.getStyleClass().add("body-text");
			attackListBox.getChildren().add(noAttacksLbl);
		} else {
			Label deleteLbl = new Label("Select an attack to delete:");
			deleteLbl.getStyleClass().add("body-text");
			attackListBox.getChildren().add(deleteLbl);
			for (MonsterAttack attack : attackList) {
				Label attackLbl = new Label(attack.toString());
				attackLbl.getStyleClass().add("body-text");
				attackLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						Label srcLbl = (Label) event.getSource();
						srcLbl.getStyleClass().clear();
						srcLbl.getStyleClass().add("body-hover");
					}
				});
				attackLbl.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						Label srcLbl = (Label) event.getSource();
						srcLbl.getStyleClass().clear();
						srcLbl.getStyleClass().add("body-text");
					}
				});
				attackLbl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						if (confirmDelete(attack)) {
							attackList.remove(attack);
							displayDeleteScreen();
						}
					}
				});
				attackListBox.getChildren().add(attackLbl);
			}
		}
		
		setCenter(attackListBox);
	}
	
	/*
	 * Displays a confirmation message when the user is about to delete an attack
	 */
	private boolean confirmDelete(MonsterAttack attack) {
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
		dialog.setContentText("Are you sure you want to delete this attack?\n" + attack);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
	
	/*
	 * Asks the user to choose between a binary and text file, and saves the data in the opened file
	 */
	private void displaySaveScreen() {
		VBox saveBox = new VBox();
		saveBox.getStyleClass().add("box");
		
		Label saveTitleLbl = new Label("Save attacks");
		saveTitleLbl.getStyleClass().add("body-title");
		saveBox.getChildren().add(saveTitleLbl);
		
		if (attackList.isEmpty()) {
			Label noAttacksLbl = new Label("No attacks to save");
			noAttacksLbl.getStyleClass().add("body-text");
			saveBox.getChildren().add(noAttacksLbl);
		} else {
			Label fileTypeLbl = new Label("Select a saving option:");
			fileTypeLbl.getStyleClass().add("body-text");
			saveBox.getChildren().add(fileTypeLbl);
			
			RadioButton binaryRadio = new RadioButton("Binary file");
			RadioButton textRadio = new RadioButton("Text file");
			
			binaryRadio.setSelected(true);
			binaryRadio.getStyleClass().add("body-text");
			binaryRadio.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					RadioButton srcRadio = (RadioButton) event.getSource();
					if (srcRadio.isSelected()) {
						textRadio.setSelected(false);
					} else if (!srcRadio.isSelected()) {
						srcRadio.setSelected(true);
					}
				}
			});
			
			textRadio.getStyleClass().add("body-text");
			textRadio.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					RadioButton srcRadio = (RadioButton) event.getSource();
					if (srcRadio.isSelected()) {
						binaryRadio.setSelected(false);
					} else if (!srcRadio.isSelected()) {
						srcRadio.setSelected(true);
					}
				}
			});
			
			HBox fileTypeBox = new HBox();
			fileTypeBox.getChildren().add(binaryRadio);
			fileTypeBox.getChildren().add(textRadio);
			saveBox.getChildren().add(fileTypeBox);
			
			Button saveBtn = new Button("Select File");
			saveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Select File");
					fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.csv"));
					File selectedFile = fileChooser.showSaveDialog(new Stage());
					if (selectedFile != null) {
						if (binaryRadio.isSelected()) {
							MonsterPersister persister = new BinaryMonsterPersister();
							persister.saveAttacks(selectedFile, attackList);
						} else if (textRadio.isSelected()) {
							MonsterPersister persister = new TextMonsterPersister();
							persister.saveAttacks(selectedFile, attackList);
						}
					}
				}
			});
			saveBox.getChildren().add(saveBtn);
		}
		
		setCenter(saveBox);
	}
	
	/*
	 * Asks the user to choose between a binary and text file, and loads the data from the opened file
	 */
	private void displayLoadScreen() {
		VBox loadBox = new VBox();
		loadBox.getStyleClass().add("box");
		
		Label loadTitleLbl = new Label("Load attacks");
		loadTitleLbl.getStyleClass().add("body-title");
		loadBox.getChildren().add(loadTitleLbl);
		
		Label fileTypeLbl = new Label("Select a loading option:");
		fileTypeLbl.getStyleClass().add("body-text");
		loadBox.getChildren().add(fileTypeLbl);
		
		RadioButton binaryRadio = new RadioButton("Binary file");
		RadioButton textRadio = new RadioButton("Text file");
		
		binaryRadio.setSelected(true);
		binaryRadio.getStyleClass().add("body-text");
		binaryRadio.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				RadioButton srcRadio = (RadioButton) event.getSource();
				if (srcRadio.isSelected()) {
					textRadio.setSelected(false);
				} else if (!srcRadio.isSelected()) {
					srcRadio.setSelected(true);
				}
			}
		});
		
		textRadio.getStyleClass().add("body-text");
		textRadio.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				RadioButton srcRadio = (RadioButton) event.getSource();
				if (srcRadio.isSelected()) {
					binaryRadio.setSelected(false);
				} else if (!srcRadio.isSelected()) {
					srcRadio.setSelected(true);
				}
			}
		});
		
		HBox fileTypeBox = new HBox();
		fileTypeBox.getChildren().add(binaryRadio);
		fileTypeBox.getChildren().add(textRadio);
		loadBox.getChildren().add(fileTypeBox);
		
		Button loadBtn = new Button("Select File");
		loadBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select File");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.csv"));
				File selectedFile = fileChooser.showOpenDialog(new Stage());
				if (selectedFile != null) {
					if (binaryRadio.isSelected()) {
						MonsterPersister persister = new BinaryMonsterPersister();
						attackList = persister.loadAttacks(selectedFile);
					} else if (textRadio.isSelected()) {
						MonsterPersister persister = new TextMonsterPersister();
						attackList = persister.loadAttacks(selectedFile);
					}
				}
			}
		});
		loadBox.getChildren().add(loadBtn);
		
		setCenter(loadBox);
	}
}
