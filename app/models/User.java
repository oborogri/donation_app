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

	@OneToMany(mappedBy = "from_id")
	public List<Donation> donations = new ArrayList<Donation>();

    public User(String firstName, String lastName, String email, String password, boolean usacitizen) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.usacitizen = usacitizen;
	}
    
    public void donate(int received, String methoddonated) {
			Donation donation = new Donation(methoddonated, received, this.id);
			donations.add(donation);
			donation.save();
			save();
	
		}

	public static User findByEmail(String email) {
		return find("email", email).first();
	}

	public static String getName(User user) {
		String userName = user.firstName + " " + user.lastName;
		return userName;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
}