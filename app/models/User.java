package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.Logger;
import play.db.jpa.Blob;

import java.util.List;
import java.util.ArrayList;

@Entity
public class User extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public boolean usacitizen;

	/**
	 * Sets up a one to many relationship between User and Donation
	 */
	@OneToMany(mappedBy = "from_id")
	public List<Donation> donations = new ArrayList<Donation>();

	/**
	 * Constructor for user object
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param usacitizen
	 */

	public User(String firstName, String lastName, String email, String password, boolean usacitizen) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.usacitizen = usacitizen;

	}

	/**
	 * Facilitates identifying a user by their e-mail
	 * 
	 * @param email
	 * @return user
	 */
	public static User findByEmail(String email) {
		return find("email", email).first();
	}

	/**
	 * Facilitates rendering user full name
	 * 
	 * @param user
	 * @return userName
	 */
	public static String getName(User user) {
		String userName = user.firstName + " " + user.lastName;
		return userName;
	}

	/**
	 * Facilitates donation an amount of money by user
	 * 
	 * @param received
	 *            - amount donated by user
	 * @param methoddonated
	 */
	public void donate(int received, String methoddonated, User user) {

		Donation donation = new Donation(methoddonated, received, this.id);

		// adds donation amount to user donations total
		user.donations.add(donation);
		donation.save();
		save();

	}

	/**
	 * Validates user password
	 * 
	 * @param password
	 * @return true if password match
	 */

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
}