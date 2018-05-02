// CS2012 | Erika Estrada | Lab 10

package data;

import java.io.Serializable;

public class MonsterAttack implements Serializable {

	private static final long serialVersionUID = 1L;
	private int attackID;
	private int day;
	private int month;
	private int year;
	private String monsterName;
	private String attackLocation;
	private String personReporting;
	
	public MonsterAttack() {}
	
	public MonsterAttack(int attackID, String date, String monsterName, String attackLocation, String personReporting) {
		this.attackID = attackID;
		this.monsterName = monsterName;
		this.attackLocation = attackLocation;
		this.personReporting = personReporting;
		
		String[] dateComponents = date.split("/");
		month = Integer.parseInt(dateComponents[0]);
		day = Integer.parseInt(dateComponents[1]);
		year = Integer.parseInt(dateComponents[2]);
	}
	
	// Accessors
	
	public int getAttackID() {
		return attackID;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getMonsterName() {
		return monsterName;
	}
	
	public String getAttackLocation() {
		return attackLocation;
	}
	
	public String getPersonReporting() {
		return personReporting;
	}
	
	// Mutators
	
	public void setAttackID(int attackID) {
		this.attackID = attackID;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
	
	public void setAttackLocation(String attackLocation) {
		this.attackLocation = attackLocation;
	}
	
	public void setPersonReporting(String personReporting) {
		this.personReporting = personReporting;
	}
	
	@Override
	public String toString() {
		return "Attack # " + attackID + ": " + monsterName + " attacked " + attackLocation + " on "
				+ month + "/" + day + "/" + year + ". Reported by " + personReporting + ".";
	}
}
