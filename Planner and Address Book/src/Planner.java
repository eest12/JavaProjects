// Erika Estrada

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Planner {

	private Map<String, Set<Event>> dailyEvents; // day -> events set
	private String fileName;
	
	public Planner() {
		dailyEvents = new TreeMap<>();
		fileName = "events.txt";
	}
	
	/**
	 * Adds an event to the planner
	 * @param event
	 */
	public void addEvent(Event e) {
		String date = e.getDate();
		Set<Event> events = dailyEvents.get(date);
		if (events == null) {
			events = new TreeSet<>();
		}
		events.add(e);
		dailyEvents.put(date, events);
	}
	
	/**
	 * Removes an event from the planner
	 * @param event
	 */
	public void removeEvent(Event e) {
		Set<Event> events = dailyEvents.get(e.getDate());
		if (events != null) {
			events.remove(e);
			if (events.size() != 0) {
				dailyEvents.put(e.getDate(), events);
			} else {
				dailyEvents.remove(e.getDate());
			}
		}
	}
	
	public Event search(String eventInfo) {		
		
		//eventInfo = "yyyy/MM/dd HH:mm desc..."
		String date = eventInfo.substring(0, 10);
		String time = eventInfo.substring(11, 16);
		String desc = eventInfo.substring(17);
		
		Set<Event> day = dailyEvents.get(date);
		Iterator<Event> iter = day.iterator();
		while (iter.hasNext()) {
			Event e = iter.next();
			if (e.equals(new Event(desc, date, time))) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Changes an element of an event based on the name and text of the given component
	 * and updates that element in the planner
	 * @param event
	 * @param component
	 */
	public void edit(Event e, Component c) {
		String name = c.getName();
		if (name != null) {
			switch (c.getName()) {
			case "DESCRIPTION":
				JTextField desc = (JTextField) c;
				if (!e.getDescription().equals(desc.getText())) {
					removeEvent(e);
					e.setDescription((String) desc.getText());
					addEvent(e);
				}
				break;
			case "YEAR":
				JComboBox year = (JComboBox) c;
				if (!e.getYear().equals(year.getSelectedItem())) {
					removeEvent(e);
					e.setYear((String) year.getSelectedItem());
					addEvent(e);
				}
				break;
			case "MONTH":
				JComboBox month = (JComboBox) c;
				if (!e.getYear().equals(month.getSelectedItem())) {
					removeEvent(e);
					e.setMonth((String) month.getSelectedItem());
					addEvent(e);
				}
				break;
			case "DAY":
				JComboBox day = (JComboBox) c;
				if (!e.getDay().equals(day.getSelectedItem())) {
					removeEvent(e);
					e.setDay((String) day.getSelectedItem());
					addEvent(e);
				}
				break;
			case "HOUR":
				JComboBox hour = (JComboBox) c;
				if (!e.getHour().equals(hour.getSelectedItem())) {
					removeEvent(e);
					e.setHour((String) hour.getSelectedItem());
					addEvent(e);
				}
				break;
			case "MINUTE":
				JComboBox minute = (JComboBox) c;
				if (!e.getMinute().equals(minute.getSelectedItem())) {
					removeEvent(e);
					e.setMinute((String) minute.getSelectedItem());
					addEvent(e);
				}
				break;
			case "COMPLETED":
				JCheckBox completed = (JCheckBox) c;
				e.setCompleted(completed.isSelected());
				break;
			case "NOTES":
				JTextArea notes = (JTextArea) c;
				e.setNotes(notes.getText());
				break;
			}
		}
	}
	
	/**
	 * Loads all events' information from a text file
	 */
	public void load() {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNextLine()) {
				String date = in.nextLine();
				String desc = in.nextLine();
				String completed = in.nextLine();
				String time = in.nextLine();
				String notes = in.nextLine();
				Event e = new Event(date);
				e.setTime(time);;
				switch (completed) {
				case "false": e.setCompleted(false); break;
				case "true": e.setCompleted(true); break;
				}
				e.setDescription(desc);
				e.setNotes(notes);
				addEvent(e);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves all events' information to a text file
	 */
	public void save() {
		try {
			PrintWriter out = new PrintWriter(fileName);
			Set<String> days = dailyEvents.keySet();
			Iterator<String> daysIter = days.iterator();
			while(daysIter.hasNext()) {
				String date = daysIter.next();
				Set<Event> events = dailyEvents.get(date);
				Iterator<Event> eventsIter = events.iterator();
				while (eventsIter.hasNext()) {
					Event e = eventsIter.next();
					out.println(date + "\n" + e.getDescription() + "\n" + e.completed() + "\n"
							+ e.getTime() + "\n" + e.getNotes());
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates a list of the most recent upcoming events, up to the specified number of events
	 * @param numOfEvents
	 * @return LinkedList of upcoming Events
	 */
	public List<Event> upcomingEvents(int numOfEvents) {
		List<Event> upcomingEvents = new LinkedList<>();
		Set<String> days = dailyEvents.keySet();
		Iterator<String> iter = days.iterator();
		int eventsAdded = 0;
		while (iter.hasNext() && eventsAdded < numOfEvents) {
			String d = iter.next();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			if (d.compareTo(dateFormat.format(date)) >= 0) {
				Set<Event> events = dailyEvents.get(d);
				Iterator<Event> iter2 = events.iterator();
				for (; iter2.hasNext() && eventsAdded < numOfEvents; eventsAdded++) {
					upcomingEvents.add(iter2.next());
				}
			}
		}
		return upcomingEvents;
	}
	
	/**
	 * Creates a panel that displays a list of days with events
	 * @param listener to open each day when clicked
	 * @return JPanel of active days
	 */
	public JPanel createDaysPanel(ActionListener listener) {
		JPanel daysPanel = new JPanel(new GridLayout(dailyEvents.size(), 1));
		Set<String> days = dailyEvents.keySet();

		Iterator<String> iter = days.iterator();
		while(iter.hasNext()) {
			String date = iter.next();
			JButton nameBtn = new JButton(date);
			nameBtn.addActionListener(listener);
			daysPanel.add(nameBtn);
		}
		
		return daysPanel;
	}
	
	/**
	 * Creates a panel that displays all the events in the specified day
	 * @param date
	 * @param listener to open each event when clicked
	 * @return JPanel with events
	 */
	public JPanel createDayPanel(String date, ActionListener listener) {
		Set<Event> dayEvents = dailyEvents.get(date);
		JPanel dayPanel = new JPanel(new GridLayout(dayEvents.size() * 2, 1));
		Iterator<Event> iter = dayEvents.iterator();
		while (iter.hasNext()) {
			Event e = iter.next();
			
			JTextField descField = new JTextField();
			descField.setText(e.getDescription());
			descField.setEditable(false);
			
			JButton editBtn = new JButton("View");
			editBtn.setName(date + " " + e.getTime() + " " + e.getDescription());
			editBtn.addActionListener(listener);
			
			JPanel eventPanel = new JPanel(new GridLayout(3, 1));
			eventPanel.add(new JLabel(e.getTime()));
			eventPanel.add(descField);
			eventPanel.add(editBtn);
			
			dayPanel.add(eventPanel);
		}
		return dayPanel;
	}
}

