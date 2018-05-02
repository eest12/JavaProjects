// Erika Estrada

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AddressBook {

	private Set<Contact> contacts;
	private String fileName;
	
	public AddressBook() {
		contacts = new TreeSet<>();
		fileName = "contacts.txt";
	}
	
	/**
	 * Adds a contact to the address book
	 * @param c
	 */
	public void add(Contact c) {
		contacts.add(c);
	}
	
	/**
	 * Removes a contact from the address book
	 * @param c
	 */
	public void delete(Contact c) {
		contacts.remove(c);
	}
	
	/**
	 * Sets the specified change to a field of the given contact
	 * @param c
	 * @param field
	 * @param change
	 */
	public void edit(Contact c, String field, String change) {
		switch (field) {
		case "NAME":
			contacts.remove(c);
			c.setName(change);
			contacts.add(c);
			break;
		case "ADDRESS": c.setAddress(change); break;
		case "PHONE": c.setPhone(change); break;
		case "EMAIL": c.setEmail(change); break;
		case "BIRTHDAY": c.setBirthday(change); break;
		case "CATEGORY": c.setCategory(change); break;
		}
	}
	
	/**
	 * Finds a contact based on name
	 * @param name
	 * @return contact with the given name
	 */
	public Contact search(String name) {
		if (contacts.contains(new Contact(name))) {
			Iterator<Contact> iter = contacts.iterator();
			while (iter.hasNext()) {
				Contact c = iter.next();
				if (c.equals(new Contact(name))) {
					return c;
				}
			}
		}
		return null;
	}
	
	/**
	 * Loads all contacts' information from a text file
	 */
	public void load() {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNextLine()) {
				String name = in.nextLine();
				String address = in.nextLine();
				String phone = in.nextLine();
				String email = in.nextLine();
				String birthday = in.nextLine();
				String category = in.nextLine();
				Contact c = new Contact(name);
				c.setAddress(address);;
				c.setPhone(phone);
				c.setEmail(email);
				c.setBirthday(birthday);
				c.setCategory(category);
				add(c);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves all contacts' information to a text file
	 */
	public void save() {
		try {
			PrintWriter out = new PrintWriter(fileName);
			Iterator<Contact> iter = contacts.iterator();
			while(iter.hasNext()) {
				Contact c = iter.next();
				out.println(c.getName() + "\n" + c.getAddress() + "\n" + c.getPhone() + "\n"
						+ c.getEmail() + "\n" + c.getBirthday() + "\n" + c.getCategory());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a panel that displays a list of contacts as buttons
	 * @param listener
	 * @return a JPanel with contacts
	 */
	public JPanel createContactsPanel(ActionListener listener) {
		
		JPanel contactsPanel = new JPanel(new GridLayout(contacts.size(), 1));
		Iterator<Contact> iter = contacts.iterator();
		while(iter.hasNext()) {
			String name = iter.next().getName();
			JButton nameBtn = new JButton(name);
			nameBtn.addActionListener(listener);
			contactsPanel.add(nameBtn);
		}
		
		return contactsPanel;
	}
}
