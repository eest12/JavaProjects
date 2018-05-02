// CS2012 | Erika Estrada | Lab 9

package data;

public class Coordinate {

	private int row;
	private int col;
	private char val; // space, [W]all, [S]tart, [E]nd, [G]uard, [L]unatic
	
	public Coordinate(int row, int col, char val) {
		this.row = row;
		this.col = col;
		this.val = val;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public char getVal() {
		return val;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setVal(char val) {
		this.val = val;
	}
	
	public boolean equals(Coordinate other) {
		return row == other.row && col == other.col;
	}
	
	public Coordinate copy() {
		return new Coordinate(row, col, val);
	}
	
	@Override
	public String toString() {
		return val + " : (" + row + "," + col + ")";
	}
}
