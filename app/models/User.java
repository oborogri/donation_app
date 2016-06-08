package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import controllers.DonationController;
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
	@OneToMany(mappedBy = "from")
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
	 * Facilitates donation an amount of money by user
	 * 
	 * @param received
	 *            - amount donated by user
	 * @param methoddonated
	 */
	public void donate(long received, String methoddonated) {

		Donation donation = new Donation(methoddonated, received, this);

		// adds donation amount to user donations total
		this.donations.add(donation);
		donation.save();
		this.save();
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
	 * Validates user password
	 * 
	 * @param password
	 * @return true if password match
	 */
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
}