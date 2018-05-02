// Erika Estrada

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Event implements Comparable<Event> {

	private String description;
	private String date;
	private String time;
	private String notes;
	private boolean completed;
	
	private String[] years = {"2015", "2016", "2017", "2018"};
	private String[] months = {"01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12"};
	private String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	private String[] hours = {"--", "00", "01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24"};
	private String[] minutes = {"--", "00", "01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
			"41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
			"51", "52", "53", "54", "55", "56", "57", "58", "59"};
	
	public Event(String date) {
		this("[No Title]", date, "--:--");
	}
	
	public Event(String description, String date) {
		this(description, date, "--:--");
	}
	
	public Event(String description, String date, String time) {
		this.description = description;
		this.date = date;
		this.time = time;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setYear(String year) {
		date = year + date.substring(4);
	}
	
	public void setMonth(String month) {
		date = date.substring(0, 5) + month + date.substring(7);
	}
	
	public void setDay(String day) {
		date = date.substring(0, 8) + day;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public void setHour(String hour) {
		time = hour + time.substring(2);
	}
	
	public void setMinute(String minute) {
		time = time.substring(0, 3) + minute;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setCompleted(boolean complete) {
		completed = complete;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getYear() {
		return date.substring(0, 4);
	}
	
	public String getMonth() {
		return date.substring(5, 7);
	}
	
	public String getDay() {
		return date.substring(8);
	}
	
	public String getTime() {
		return time;
	}
	
	public String getHour() {
		return time.substring(0, 2);
	}
	
	public String getMinute() {
		return time.substring(3);
	}
	
	public String getNotes() {
		return notes;
	}
	
	public boolean completed() {
		return completed;
	}
	
	/**
	 * Creates a panel that displays event info
	 * @return a JPanel with event info
	 */
	public JPanel createEventPanel() {
		JLabel descLabel = new JLabel("Description");
		JLabel dateLabel = new JLabel("Date (yyyy/mm/dd)");
		JLabel timeLabel = new JLabel("Time (hh/mm)");
		JLabel notesLabel = new JLabel("Notes");
		
		JCheckBox completedCheck = new JCheckBox("Completed");
		completedCheck.setSelected(completed);
		completedCheck.setEnabled(false);
		completedCheck.setName("COMPLETED");
		
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8);
		String hour = time.substring(0, 2);
		String minute = time.substring(3);
		
		int yearIndex = 0;
		while (!year.equals(years[yearIndex])) {
			yearIndex++;
		}
		int monthIndex = 0;
		while (!month.equals(months[monthIndex])) {
			monthIndex++;
		}
		int dayIndex = 0;
		while (!day.equals(days[dayIndex])) {
			dayIndex++;
		}
		int hourIndex = 0;
		while (!hour.equals(hours[hourIndex])) {
			hourIndex++;
		}
		int minuteIndex = 0;
		while (!minute.equals(minutes[minuteIndex])) {
			minuteIndex++;
		}
		
		JComboBox yearBox = new JComboBox(years);
		yearBox.setSelectedIndex(yearIndex);
		yearBox.setEnabled(false);
		yearBox.setName("YEAR");
		
		JComboBox monthBox = new JComboBox(months);
		monthBox.setSelectedIndex(monthIndex);
		monthBox.setEnabled(false);
		monthBox.setName("MONTH");
		
		JComboBox dayBox = new JComboBox(days);
		dayBox.setSelectedIndex(dayIndex);
		dayBox.setEnabled(false);
		dayBox.setName("DAY");
		
		JComboBox hourBox = new JComboBox(hours);
		hourBox.setSelectedIndex(hourIndex);
		hourBox.setEnabled(false);
		hourBox.setName("HOUR");
		
		JComboBox minuteBox = new JComboBox(minutes);
		minuteBox.setSelectedIndex(minuteIndex);
		minuteBox.setEnabled(false);
		minuteBox.setName("MINUTE");
		
		JTextField descField = new JTextField();
		descField.setText(description);
		descField.setEditable(false);
		descField.setName("DESCRIPTION");
		
		JTextArea notesTxtArea = new JTextArea();
		notesTxtArea.setText(notes);
		notesTxtArea.setEditable(false);
		notesTxtArea.setName("NOTES");
		
		JPanel eventPanel = new JPanel(new GridLayout(4, 4));
		eventPanel.add(descLabel);
		eventPanel.add(descField);
		eventPanel.add(completedCheck);
		eventPanel.add(new JPanel());
		eventPanel.add(dateLabel);
		eventPanel.add(yearBox);
		eventPanel.add(monthBox);
		eventPanel.add(dayBox);
		eventPanel.add(timeLabel);
		eventPanel.add(hourBox);
		eventPanel.add(minuteBox);
		eventPanel.add(new JPanel());
		eventPanel.add(notesLabel);
		eventPanel.add(notesTxtArea);
		
		return eventPanel;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Event) {
			Event e = (Event) other;
			return date.equals(e.date) && time.equals(e.time) && description.equals(e.description);
		}
		return false;
	}
	
	@Override
	public int compareTo(Event other) {
		if (date.equals(other.date)) {
			if (time.equals(other.time)) {
				return -1;
			}
			return time.compareTo(other.time);
		}
		return date.compareTo(other.date);
	}
}
