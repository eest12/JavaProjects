// CS 2012 | Erika Estrada | Lab 10

package data;

import java.io.File;
import java.util.ArrayList;

public interface MonsterPersister {

	public void saveAttacks(File saveFile, ArrayList<MonsterAttack> attackList);
	public ArrayList<MonsterAttack> loadAttacks(File loadFile);
}
