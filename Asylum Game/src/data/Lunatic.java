// CS2012 | Erika Estrada | Lab 9

package data;

public class Lunatic {
	
	private Coordinate pos;
	private Coordinate lastSeenGuardPos;
	
	public Lunatic(Coordinate pos) {
		this.pos = pos;
	}
	
	public Coordinate getCoord() {
		return pos;
	}
	
	public Coordinate getLastSeenGuardPos() {
		return lastSeenGuardPos;
	}

	public Coordinate nextStep(Coordinate[][] map, Coordinate guardPos) {
		int row = pos.getRow();
		int col = pos.getCol();
		
		// look in current row
		if (row == guardPos.getRow()) {
			// look to the right
			if (col < guardPos.getCol()) {
				for (int i = col + 1; map[row][i].getVal() != 'W'; i++) { // look until you hit a wall
					if (map[row][i].equals(guardPos)) { // if found guard without hitting a wall
						lastSeenGuardPos = guardPos.copy();
						if (map[row][col+1].getVal() != 'L') { // if the spot is available
							pos = new Coordinate(row, col+1, 'L'); // move one right
						}
						return pos; // if the spot was taken by another lunatic, there's no change
					}
				}
			// look to the left
			} else {
				for (int i = col - 1; map[row][i].getVal() != 'W'; i--) {
					if (map[row][i].equals(guardPos)) {
						lastSeenGuardPos = guardPos.copy();
						if (map[row][col-1].getVal() != 'L') {
							pos = new Coordinate(row, col-1, 'L'); // move one left
						}
						return pos;
					}
				}
			}
		// look in current col
		} else if (pos.getCol() == guardPos.getCol()) {
			// look down
			if (pos.getRow() < guardPos.getRow()) {
				for (int i = row + 1; map[i][col].getVal() != 'W'; i++) {
					if (map[i][col].equals(guardPos)) {
						lastSeenGuardPos = guardPos.copy();
						if (map[row+1][col].getVal() != 'L') {
							pos = new Coordinate(row+1, col, 'L'); // move one down
						}
						return pos;
					}
				}
			// look up
			} else {
				for (int i = row - 1; map[i][col].getVal() != 'W'; i--) {
					if (map[i][col].equals(guardPos)) {
						lastSeenGuardPos = guardPos.copy();
						if (map[row-1][col].getVal() != 'L') {
							pos = new Coordinate(row-1, col, 'L'); // move one up
						}
						return pos;
					}
				}
			}
		}
		
		// guard not in view
		return nextStepOutOfView(map);
	}
	
	// moves toward the position where the guard was last seen, or randomly if no such location is known
	private Coordinate nextStepOutOfView(Coordinate[][] map) {
		if (lastSeenGuardPos == null) { // if there's no last seen position, move randomly
			pos = nextStepRandom(map);
			return pos;
		} else if (lastSeenGuardPos.equals(pos)) { // if reached, clear last seen position and move randomly
			lastSeenGuardPos = null;
			pos = nextStepRandom(map);
			return pos;
		} else { // move toward the position where the guard was last seen
			pos = nextStep(map, lastSeenGuardPos);
			return pos;
		}
	}
	
	private Coordinate nextStepRandom(Coordinate[][] map) {
		int row = pos.getRow();
		int col = pos.getCol();
		boolean[] possMoves = new boolean[4];
		for (int i = 0; i < possMoves.length; i++) {
			possMoves[i] = true; // first mark every direction as available
		}
		
		// while there are possible moves
		while (possMoves[0] == true || possMoves[1] == true || possMoves[2] == true || possMoves[3] == true) {
			int randDirection = (int) (Math.random() * 4);
			
			switch (randDirection) {
			case 0: // up
				if (map[row-1][col].getVal() == ' ') {
					return new Coordinate(row-1, col, 'L');
				} else {
					possMoves[0] = false; // mark spot as not available
				}
				break;
			case 1: // down
				if (map[row+1][col].getVal() == ' ') {
					return new Coordinate(row+1, col, 'L');
				} else {
					possMoves[1] = false;
				}
				break;
			case 2: // left
				if (map[row][col-1].getVal() == ' ') {
					return new Coordinate(row, col-1, 'L');
				} else {
					possMoves[2] = false;
				}
				break;
			case 3: // right
				if (map[row][col+1].getVal() == ' ') {
					return new Coordinate(row, col+1, 'L');
				} else {
					possMoves[3] = false;
				}
				break;
			}
		}
		
		return pos; // no available moves; lunatic doesn't move
	}
}
