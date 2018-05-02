//Erika Estrada

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Contact implements Comparable<Contact> {
	
	private String name;
	private String address;
	private String phone;
	private String email;
	private String birthday;
	private String category;
	
	public Contact() {
		this("[New Contact]");
	}
	
	public Contact(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public String getCategory() {
		return category;
	}
	
	/**
	 * Creates a panel that displays all information of the contact
	 * @return a JPanel with contact info
	 */
	public JPanel createContactPanel() {
		
		JPanel contactPanel = new JPanel(new GridLayout(6, 2));
		
		contactPanel.add(new JLabel("Name:"));
		JTextField nameField = new JTextField(name);
		nameField.setName("NAME");
		nameField.setEditable(false);
		contactPanel.add(nameField);
		
		contactPanel.add(new JLabel("Address:"));
		JTextField addressField = new JTextField(address);
		addressField.setName("ADDRESS");
		addressField.setEditable(false);
		contactPanel.add(addressField);
		
		contactPanel.add(new JLabel("Phone:"));
		JTextField phoneField = new JTextField(phone);
		phoneField.setName("PHONE");
		phoneField.setEditable(false);
		contactPanel.add(phoneField);
		
		contactPanel.add(new JLabel("Email:"));
		JTextField emailField = new JTextField(email);
		emailField.setName("EMAIL");
		emailField.setEditable(false);
		contactPanel.add(emailField);
		
		contactPanel.add(new JLabel("Birthday:"));
		JTextField birthdayField = new JTextField(birthday);
		birthdayField.setName("BIRTHDAY");
		birthdayField.setEditable(false);
		contactPanel.add(birthdayField);
		
		contactPanel.add(new JLabel("Category:"));
		JTextField categoryField = new JTextField(category);
		categoryField.setName("CATEGORY");
		categoryField.setEditable(false);
		contactPanel.add(categoryField);
		
		return contactPanel;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Contact) {
			Contact c = (Contact) other;
			return name.equals(c.name);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(Contact c) {
		return name.compareTo(c.name);
	}
}
