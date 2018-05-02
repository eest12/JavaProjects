// CS2012 | Erika Estrada | Lab 10

package data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BinaryMonsterPersister implements MonsterPersister {

	@Override
	public void saveAttacks(File saveFile, ArrayList<MonsterAttack> attackList) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveFile)));
			out.writeObject(attackList);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<MonsterAttack> loadAttacks(File loadFile) {
		ObjectInputStream in = null;
		ArrayList<MonsterAttack> attacks = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(loadFile)));
			attacks = (ArrayList<MonsterAttack>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attacks;
	}

}
