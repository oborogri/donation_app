package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.Logger;
import play.db.jpa.Blob;

import java.util.List;
import java.util.ArrayList;

/**
 * Donation model class
 * 
 * @author Grigore Oboroceanu
 *
 */
@Entity
public class Donation extends Model {
	public String methoddonated;
	public long received;
	public static long donationtarget = 20000;

	@ManyToOne
	public User from;

	/**
	 * Constructor method for Donation object
	 * 
	 * @param methoddonated
	 * @param received
	 * @param user
	 */
	public Donation(String methoddonated, long received, User from) {

		this.methoddonated = methoddonated;
		this.received = received;
		this.from = from;
	}
}