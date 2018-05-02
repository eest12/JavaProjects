// CS2012 | Erika Estrada | Lab 9

package gui;

import java.util.ArrayList;

import data.AsylumMap;
import data.Coordinate;
import data.Lunatic;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapPane extends BorderPane {

	private GridPane grid; // grid of labels
	private Label[][] labelArray; // same labels as grid for easy access
	private AsylumMap mapData;
	private int rows;
	private int cols;
	private int height;
	private int width;
	private double percentWalls;
	private int headStart;
	private int numLunatics;
	private int lunMovesPerTurn;
	private int guardMoves;
	private boolean gameOver = false;
	private TextField headStartTxt;
	private TextField numLunaticsTxt;
	private TextField lunMovesTxt;
	private Label gameOverLabel = new Label();
	
	public MapPane(String title, int rows, int cols, int height, int width, double percentWalls,
			int headStart, int numLunatics, int lunMovesPerTurn) {
		this.rows = rows;
		this.cols = cols;
		this.height = height;
		this.width = width;
		this.percentWalls = percentWalls;
		this.headStart = headStart;
		this.numLunatics = numLunatics;
		this.lunMovesPerTurn = lunMovesPerTurn;
		generateMap();
		generateTitleLabel(title);
		generateBottomBox();
		generateKeyEventHandler();
	}
	
	/*
	 * Accessors
	 */
	
	public GridPane getGrid() {
		return grid;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getMapHeight() {
		return height;
	}
	
	public int getMapWidth() {
		return width;
	}
	
	public double getPercentWalls() {
		return percentWalls;
	}
	
	public int getHeadStart() {
		return headStart;
	}
	
	public int getNumLunatics() {
		return numLunatics;
	}
	
	public int getLunMovesPerTurn() {
		return lunMovesPerTurn;
	}
	
	/*
	 * Mutators
	 */
	
	public void setGrid(GridPane grid) {
		this.grid = grid;
		setCenter(grid);
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public void setMapHeight(int height) {
		this.height = height;
	}
	
	public void setMapWidth(int width) {
		this.width = width;
	}
	
	public void setPercentWalls(double percentWalls) {
		this.percentWalls = percentWalls;
	}
	
	public void setHeadStart(int headStart) {
		this.headStart = headStart;
	}
	
	public void setNumLunatics(int numLunatics) {
		this.numLunatics = numLunatics;
	}
	
	public void setLunMovesPerTurn(int lunMovesPerTurn) {
		this.lunMovesPerTurn = lunMovesPerTurn;
	}
	
	private void generateMap() {
		grid = new GridPane();
		labelArray = new Label[rows][cols];
		mapData = new AsylumMap(rows, cols, percentWalls);
		for (int i = 0; i < mapData.getRows(); i++) {
			for (int j = 0; j < mapData.getCols(); j++) {
				String value = "" + mapData.getCoords()[i][j].getVal();
				Label label = generateGridLabel(value);
				GridPane.setConstraints(label, j, i);
				grid.add(label, j, i);
				labelArray[i][j] = label;
			}
		}
		
		HBox gridHBox = new HBox();
		gridHBox.getStyleClass().add("box");
		gridHBox.getChildren().add(grid);
		
		VBox gridVBox = new VBox();
		gridVBox.getStyleClass().add("box");
		gridVBox.getChildren().add(gridHBox);
		setCenter(gridVBox);
	}
	
	private Label generateGridLabel(String value) {
		Label label = new Label(value);

		label.setPrefHeight(height/rows);
		label.setPrefWidth(width/cols);

		switch (value) {
		case " ": label.getStyleClass().add("label-space"); break;
		case "W": label.getStyleClass().add("label-wall"); break;
		case "S":
		case "E": label.getStyleClass().add("label-start-end"); break;
		case "G": label.getStyleClass().add("label-guard"); break;
		case "L": label.getStyleClass().add("label-lunatic"); break;
		}
		
		return label;
	}
	
	private void generateTitleLabel(String title) {
		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("label-title");
		
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(titleLabel);
		setTop(titleBox);
	}
	
	private void generateBottomBox() {
		HBox levelAdjustBox = generateOptionsBox();
		Button resetBtn = generateResetButton();
		
		HBox bottomBox = new HBox();
		bottomBox.getChildren().add(levelAdjustBox);
		bottomBox.getChildren().add(resetBtn);
		bottomBox.getChildren().add(gameOverLabel);
		setBottom(bottomBox);
	}
	
	private HBox generateOptionsBox() {
		Label headStartLabel = new Label("Head start:");
		Label numLunaticsLabel = new Label("Lunatics:");
		Label lunMovesLabel = new Label("Lunatic moves:");
		
		headStartTxt = new TextField("" + headStart);
		headStartTxt.setPrefWidth(40);
		numLunaticsTxt = new TextField("" + numLunatics);
		numLunaticsTxt.setPrefWidth(40);
		lunMovesTxt = new TextField("" + lunMovesPerTurn);
		lunMovesTxt.setPrefWidth(40);
		
		HBox optionsBox = new HBox();
		optionsBox.getChildren().add(headStartLabel);
		optionsBox.getChildren().add(headStartTxt);
		optionsBox.getChildren().add(numLunaticsLabel);
		optionsBox.getChildren().add(numLunaticsTxt);
		optionsBox.getChildren().add(lunMovesLabel);
		optionsBox.getChildren().add(lunMovesTxt);
		
		return optionsBox;
	}
	
	private Button generateResetButton() {
		Button resetBtn = new Button("New Game");
		resetBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				newGame();
			}
			
		});
		
		return resetBtn;
	}
	
	private void generateKeyEventHandler() {
		final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {			
			
			@Override
			public void handle(final KeyEvent keyEvent) {
				
				if (keyEvent.getCode() == KeyCode.F10) { // start new game
					newGame();
				} else if (keyEvent.getCode() == KeyCode.UP) { // move guard up
					int row = mapData.getGuard().getRow();
					int col = mapData.getGuard().getCol();
					if (!gameOver && mapData.moveGuard(1)) {
						guardMoves++;
						updateLabel(row, col);
						updateLabel(row-1, col);
						gameOver = mapData.gameOver();
						if (gameOver) {
							generateGameOverMessage();
						} else {
							if (guardMoves >= headStart) {
								moveAndUpdateLunatics();
								if (mapData.getLunatics().size() < numLunatics) {
									Coordinate lunPos = mapData.spawnLunatic();
									if (lunPos != null) {
										updateLabel(lunPos.getRow(), lunPos.getCol());
									}
								}
							}
						}
					}
				} else if (keyEvent.getCode() == KeyCode.DOWN) { // move guard down
					int row = mapData.getGuard().getRow();
					int col = mapData.getGuard().getCol();
					if (!gameOver && mapData.moveGuard(2)) {
						guardMoves++;
						updateLabel(row, col);
						updateLabel(row+1, col);
						gameOver = mapData.gameOver();
						if (gameOver) {
							generateGameOverMessage();
						} else {
							if (guardMoves >= headStart) {
								moveAndUpdateLunatics();
								if (mapData.getLunatics().size() < numLunatics) {
									Coordinate lunPos = mapData.spawnLunatic();
									if (lunPos != null) {
										updateLabel(lunPos.getRow(), lunPos.getCol());
									}
								}
							}
						}
					}
				} else if (keyEvent.getCode() == KeyCode.LEFT) { // move guard left
					int row = mapData.getGuard().getRow();
					int col = mapData.getGuard().getCol();
					if (!gameOver && mapData.moveGuard(3)) {
						guardMoves++;
						updateLabel(row, col);
						updateLabel(row, col-1);
						gameOver = mapData.gameOver();
						if (gameOver) {
							generateGameOverMessage();
						} else {
							if (guardMoves >= headStart) {
								moveAndUpdateLunatics();
								if (mapData.getLunatics().size() < numLunatics) {
									Coordinate lunPos = mapData.spawnLunatic();
									if (lunPos != null) {
										updateLabel(lunPos.getRow(), lunPos.getCol());
									}
								}
							}
						}
					}
				} else if (keyEvent.getCode() == KeyCode.RIGHT) { // move guard right
					int row = mapData.getGuard().getRow();
					int col = mapData.getGuard().getCol();
					if (!gameOver && mapData.moveGuard(4)) {
						guardMoves++;
						updateLabel(row, col);
						updateLabel(row, col+1);
						gameOver = mapData.gameOver();
						if (gameOver) {
							generateGameOverMessage();
						} else {
							if (guardMoves >= headStart) {
								moveAndUpdateLunatics();
								if (mapData.getLunatics().size() < numLunatics) {
									Coordinate lunPos = mapData.spawnLunatic();
									if (lunPos != null) {
										updateLabel(lunPos.getRow(), lunPos.getCol());
									}
								}
							}
						}
					}
				}
			}
		};
		
		super.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);
	}
	
	private void updateLabel(int row, int col) {
		String newValue = "" + mapData.getCoords()[row][col].getVal();
		Label label = labelArray[row][col];
		label.setText(newValue);
		label.getStyleClass().remove(label.getStyleClass().size() - 1);
		
		switch (newValue) {
		case " ": label.getStyleClass().add("label-space"); break;
		case "W": label.getStyleClass().add("label-wall"); break;
		case "S":
		case "E": label.getStyleClass().add("label-start-end"); break;
		case "G": label.getStyleClass().add("label-guard"); break;
		case "L": label.getStyleClass().add("label-lunatic"); break;
		}
	}
	
	private void moveAndUpdateLunatics() {
		ArrayList<Lunatic> lunatics = mapData.getLunatics();
		
		for (Lunatic l : lunatics) {
			int lunRow = l.getCoord().getRow();
			int lunCol = l.getCoord().getCol();
			
			for (int j = 0; j < lunMovesPerTurn && !gameOver; j++) {
				mapData.moveLunatic(l);
				gameOver = mapData.gameOver();
			}
			
			int lunRowNew = l.getCoord().getRow();
			int lunColNew = l.getCoord().getCol();
			updateLabel(lunRow, lunCol);
			updateLabel(lunRowNew, lunColNew);
		}
		
		if (gameOver) {
			generateGameOverMessage();
		}
	}
	
	private void generateGameOverMessage() {
		String message;
		if (mapData.win()) {
			message = "You win!";
		} else {
			message = "RIP";
		}
		gameOverLabel.setText(message);
	}
	
	private void newGame() {
		gameOver = false;
		guardMoves = 0;
		
		// set custom difficulty properties
		try {
			int headStartIn = Integer.parseInt(headStartTxt.getText());
			if (headStartIn >= 0) {
				headStart = headStartIn;
			}
		} catch (NumberFormatException e) {
			// head start unchanged
		}
		try {
			int numLunaticsIn = Integer.parseInt(numLunaticsTxt.getText());
			if (numLunaticsIn >= 1) {
				numLunatics = numLunaticsIn;
			}
		} catch (NumberFormatException e) {
			// number of lunatics unchanged
		}
		try {
			int lunMovesPerTurnIn = Integer.parseInt(lunMovesTxt.getText());
			if (lunMovesPerTurnIn >= 1) {
				lunMovesPerTurn = lunMovesPerTurnIn;
			}
		} catch (NumberFormatException e) {
			// lunatic moves per turn unchanged
		}
		
		// remove game over message
		gameOverLabel.setText("");
		
		generateMap();
	}

}
