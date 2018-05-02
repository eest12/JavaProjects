// CS2012 | Erika Estrada | Lab 9

package data;

import java.util.ArrayList;

public class AsylumMap {

	private Coordinate[][] coords;
	private int rows;
	private int cols;
	private Coordinate guard = new Coordinate(1, 1, 'G');
	private ArrayList<Lunatic> lunatics = new ArrayList<>();
	
	public AsylumMap(int rows, int cols, double percentWalls) {
		this.rows = rows;
		this.cols = cols;
		coords = new Coordinate[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// set edges and % of the grid as walls
				if (i == 0 || i == rows-1 || j == 0 || j == cols-1 || Math.random() < percentWalls) {
					coords[i][j] = new Coordinate(i, j, 'W');
				} else {
					coords[i][j] = new Coordinate(i, j, ' ');
				}
			}
		}
		
		// set guard and ending point
		coords[1][1] = guard;
		coords[rows-1][cols-2] = new Coordinate(rows-2, cols-2, 'E');
	}
	
	public Coordinate[][] getCoords() {
		return coords;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public Coordinate getGuard() {
		return guard;
	}
	
	public ArrayList<Lunatic> getLunatics() {
		return lunatics;
	}
	
	// UP:1 DOWN:2 LEFT:3 RIGHT:4
	// returns true if guard changed positions
	public boolean moveGuard(int direction) {
		int row = guard.getRow();
		int col = guard.getCol();
		
		switch (direction) {
		case 1:
			if (row > 1 && coords[row-1][col].getVal() != 'W') {
				guard.setRow(row-1);
				coords[row-1][col] = guard;
				coords[row][col] = new Coordinate(row, col, ' ');
				return true;
			}
			break;
		case 2:
			if (row < (rows-1) && coords[row+1][col].getVal() != 'W') {
				guard.setRow(row+1);
				coords[row+1][col] = guard;
				coords[row][col] = new Coordinate(row, col, ' ');
				return true;
			}
			break;
		case 3:
			if (col > 1 && coords[row][col-1].getVal() != 'W') {
				guard.setCol(col-1);
				coords[row][col-1] = guard;
				coords[row][col] = new Coordinate(row, col, ' ');
				return true;
			}
			break;
		case 4:
			if (col < (cols-1) && coords[row][col+1].getVal() != 'W') {
				guard.setCol(col+1);
				coords[row][col+1] = guard;
				coords[row][col] = new Coordinate(row, col, ' ');
				return true;
			}
			break;
		}
		
		return false;
	}
	
	// returns coordinate where spawned or null if could not spawn
	public Coordinate spawnLunatic() {
		if (coords[1][1].getVal() != 'L') {
			coords[1][1].setVal('L');
			lunatics.add(new Lunatic(coords[1][1]));
			return coords[1][1];
		}
		return null;
	}
	
	public void moveLunatic(Lunatic lun) {
		// clear current lunatic position
		Coordinate currentPos = lun.getCoord();
		coords[currentPos.getRow()][currentPos.getCol()].setVal(' ');
		// move lunatic to new position
		Coordinate nextPos = lun.nextStep(coords, guard);
		coords[nextPos.getRow()][nextPos.getCol()].setVal('L');
	}
	
	public boolean gameOver() {
		return win() || lose();
	}
	
	public boolean win() {
		return guard.getRow() == rows - 1 && guard.getCol() == cols - 2;
	}
	
	public boolean lose() {
		for (Lunatic l : lunatics) {
			if (l.getCoord().getRow() == guard.getRow() && l.getCoord().getCol() == guard.getCol()) {
				return true;
			}
		}
		return false;
	}

}
