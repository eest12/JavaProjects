//Erika Estrada

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlannerUI extends JFrame {
	
	private Planner planner = new Planner();
	private AddressBook adrBook = new AddressBook();
	private JButton mainMenuBtn;
	private JButton adrBookBtn;
	private JButton plannerBtn;
	
	public PlannerUI() {
		setTitle("Planner and Address Book");
		setPreferredSize(new Dimension(600, 500));
		pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				planner.save();
				adrBook.save();
				System.exit(0);
			}
		});
		planner.load();
		adrBook.load();
		navigationBtns();
		openMainMenu();
	}
	
	/**
	 * Generates the main buttons to jump from screen to screen
	 */
	private void navigationBtns() {
		ActionListener naviBtnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				switch (btn.getText()) {
				case "Main Menu": openMainMenu(); break;
				case "Planner": openPlanner(); break;
				case "Address Book": openAddressBook(); break;
				}
			}
		};
		
		mainMenuBtn = new JButton("Main Menu");
		mainMenuBtn.addActionListener(naviBtnListener);
		adrBookBtn = new JButton("Address Book");
		adrBookBtn.addActionListener(naviBtnListener);
		plannerBtn = new JButton("Planner");
		plannerBtn.addActionListener(naviBtnListener);
	}
	
	/**
	 * Opens main menu screen
	 */
	private void openMainMenu() {
		
		JLabel menuLabel = new JLabel("Main Menu");
		menuLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JTextArea remindersTxtArea = new JTextArea();
		remindersTxtArea.setPreferredSize(new Dimension(100, 150));
		remindersTxtArea.setEditable(false);
		
		List<Event> list = planner.upcomingEvents(5);
		if (list == null) {
			remindersTxtArea.setText("No upcoming events");
		} else {
			remindersTxtArea.setText("Upcoming events\n\n");
			for (int i = 0; i < list.size(); i++) {
				String text = list.get(i).getDate() + " " + list.get(i).getDescription();
				remindersTxtArea.setText(remindersTxtArea.getText() + text + "\n");
			}
		}
		
		JPanel menuPanel = new JPanel(new GridLayout(3, 1));
		menuPanel.add(menuLabel);
		menuPanel.add(plannerBtn);
		menuPanel.add(adrBookBtn);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(menuPanel, BorderLayout.CENTER);
		mainPanel.add(remindersTxtArea, BorderLayout.SOUTH);
		
		getContentPane().removeAll();
		add(mainPanel);
		revalidate();
	}
	
	/**
	 * Opens planner screen, which displays the days that have events
	 */
	private void openPlanner() {
		JLabel abLabel = new JLabel("Planner");
		
		ActionListener dayBtnLstnr = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				openDay(btn.getText());
			}
		};
		
		JPanel daysPanel = planner.createDaysPanel(dayBtnLstnr);
		
		JButton newEventBtn = new JButton("New Event");
		newEventBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				Event event = new Event(dateFormat.format(date));
				planner.addEvent(event);
				openEvent(event);
			}
		});
		
		JPanel optionsPanel = new JPanel(new GridLayout(1, 2));
		optionsPanel.add(newEventBtn);
		optionsPanel.add(mainMenuBtn);
		
		JPanel plPanel = new JPanel(new BorderLayout());
		plPanel.add(abLabel, BorderLayout.NORTH);
		plPanel.add(daysPanel, BorderLayout.CENTER);
		plPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		getContentPane().removeAll();
		add(plPanel);
		revalidate();
	}
	
	/**
	 * Opens day screen, which displays the events for that day
	 * @param d
	 */
	private void openDay(String d) {
		
		JLabel abLabel = new JLabel(d);
		
		JPanel dayPanel = new JPanel(new BorderLayout());
		
		ActionListener eventBtnLstnr = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				openEvent(planner.search(btn.getName()));
			}
		};

		JPanel dayInfoPanel = planner.createDayPanel(d, eventBtnLstnr);
		
		JPanel optionsPanel = new JPanel(new GridLayout(1, 2));
		optionsPanel.add(plannerBtn);
		optionsPanel.add(mainMenuBtn);
		
		dayPanel.add(dayInfoPanel, BorderLayout.CENTER);
		dayPanel.add(abLabel, BorderLayout.NORTH);
		dayPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		getContentPane().removeAll();
		add(dayPanel);
		revalidate();
	}
	
	/**
	 * Opens event screen, which displays event info and allows changes
	 * @param event
	 */
	private void openEvent(Event event) {
		JLabel eventLabel = new JLabel("Event");
		
		JPanel eventInfoPanel = event.createEventPanel();
		
		JButton editSaveBtn = new JButton("Edit");
		editSaveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				for (int i = 0; i < eventInfoPanel.getComponentCount(); i++){
					Component comp = eventInfoPanel.getComponent(i);
					if (comp instanceof JTextField) {
						JTextField txtField = (JTextField) comp;
						txtField.setEditable(!txtField.isEditable());
					} else if (comp instanceof JCheckBox) {
						JCheckBox checkBox = (JCheckBox) comp;
						checkBox.setEnabled(!checkBox.isEnabled());
					} else if (eventInfoPanel.getComponent(i) instanceof JComboBox) {
						JComboBox comboBox = (JComboBox) comp;
						comboBox.setEnabled(!comboBox.isEnabled());
					} else if (comp instanceof JTextArea) {
						JTextArea txtArea = (JTextArea) comp;
						txtArea.setEditable(!txtArea.isEditable());
					}
					if (btn.getText().equals("Save")) {
						planner.edit(event, comp);
					}
				}
				switch (btn.getText()) {
				case "Edit":
					btn.setText("Save");
					break;
				case "Save":
					btn.setText("Edit");
					break;
				}
			}
		});
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showOptionDialog(null,
						"Are you sure you want to delete this event?", "Delete Event",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						null, null);
				if (selection == JOptionPane.OK_OPTION) {
					planner.removeEvent(event);
					openPlanner();
				}
			}
		});
		
		JPanel optionsPanel = new JPanel(new GridLayout(1, 4));
		optionsPanel.add(editSaveBtn);
		optionsPanel.add(deleteBtn);
		optionsPanel.add(plannerBtn);
		optionsPanel.add(mainMenuBtn);
		
		JPanel eventPanel = new JPanel(new BorderLayout());
		eventPanel.add(eventLabel, BorderLayout.NORTH);
		eventPanel.add(eventInfoPanel, BorderLayout.CENTER);
		eventPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		getContentPane().removeAll();
		add(eventPanel);
		revalidate();
	}
	
	/**
	 * Opens address book screen, which displays all contacts' names
	 */
	private void openAddressBook() {
		JLabel abLabel = new JLabel("Address Book");
		
		ActionListener contactBtnLstnr = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				openContact(adrBook.search(btn.getText()));
			}
		};
		
		JPanel contactsPanel = adrBook.createContactsPanel(contactBtnLstnr);
		
		JButton newContactBtn = new JButton("New Contact");
		newContactBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Contact c = new Contact();
				adrBook.add(c);
				openContact(c);
			}
		});
		
		JPanel optionsPanel = new JPanel(new GridLayout(1, 2));
		optionsPanel.add(newContactBtn);
		optionsPanel.add(mainMenuBtn);
		
		JPanel abPanel = new JPanel(new BorderLayout());
		abPanel.add(abLabel, BorderLayout.NORTH);
		abPanel.add(optionsPanel, BorderLayout.SOUTH);
		abPanel.add(contactsPanel, BorderLayout.CENTER);
		
		getContentPane().removeAll();
		add(abPanel);
		revalidate();
	}
	
	/**
	 * Opens contact screen, which displays contact info and allows changes
	 * @param c
	 */
	private void openContact(Contact c) {
		JLabel contactLabel = new JLabel("Contact");
		
		JPanel contactPanel = new JPanel(new BorderLayout());
		contactPanel.add(contactLabel, BorderLayout.NORTH);
		
		JPanel contactInfoPanel = c.createContactPanel();
		contactPanel.add(contactInfoPanel, BorderLayout.CENTER);
		
		JButton editSaveBtn = new JButton("Edit");
		editSaveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				for (int i = 0; i < contactInfoPanel.getComponentCount(); i++){
					if (contactInfoPanel.getComponent(i) instanceof JTextField) {
						JTextField txtField = (JTextField) contactInfoPanel.getComponent(i);
						txtField.setEditable(!txtField.isEditable());
						adrBook.edit(c, txtField.getName(), txtField.getText());
					}
				}
				switch (btn.getText()) {
				case "Edit":
					btn.setText("Save");
					break;
				case "Save":
					btn.setText("Edit");
					break;
				}
			}
		});
		
		JButton deleteBtn = new JButton("Delete Contact");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showOptionDialog(null,
						"Are you sure you want to delete this contact?", "Delete Contact",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						null, null);
				if (selection == JOptionPane.OK_OPTION) {
					adrBook.delete(c);
					openAddressBook();
				}
			}
		});
		
		JPanel optionsPanel = new JPanel(new GridLayout(1, 4));
		optionsPanel.add(editSaveBtn);
		optionsPanel.add(deleteBtn);
		optionsPanel.add(adrBookBtn);
		optionsPanel.add(mainMenuBtn);
		
		contactPanel.add(optionsPanel, BorderLayout.SOUTH);
		
		getContentPane().removeAll();
		add(contactPanel);
		revalidate();
	}

	public static void main(String[] args) {
		PlannerUI planner = new PlannerUI();
		planner.setVisible(true);
	}
}
