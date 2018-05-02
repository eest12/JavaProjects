// CS2012 | Erika Estrada | Lab 10

package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextMonsterPersister implements MonsterPersister {

	@Override
	public void saveAttacks(File saveFile, ArrayList<MonsterAttack> attackList) {
		if (attackList.isEmpty()) {
			System.out.println("There are no attacks to save.");
		} else {
			try {
				PrintWriter writer = new PrintWriter(saveFile);
				for (MonsterAttack a : attackList) {
					writer.println(a.getAttackID() + "," + a.getMonth() + "/" + a.getDay() + "/" + a.getYear()
						+ "," + a.getMonsterName() + "," + a.getAttackLocation() + "," + a.getPersonReporting());
				}
				writer.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<MonsterAttack> loadAttacks(File loadFile) {
		ArrayList<MonsterAttack> attackList = new ArrayList<>();

		try {
			Scanner inFile = new Scanner(loadFile);
			while (inFile.hasNextLine()) {
				String attackLine = inFile.nextLine();
				String[] attackComponents = attackLine.split(",");
				
				int attackID = Integer.parseInt(attackComponents[0]);
				String date = attackComponents[1];
				String monsterName = attackComponents[2];
				String location = attackComponents[3];
				String reporter = attackComponents[4];
				
				attackList.add(new MonsterAttack(attackID, date, monsterName, location, reporter));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		return attackList;
	}

}
