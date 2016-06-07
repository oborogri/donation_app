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
	public int received;
	public long from_id;
	public static long donationtarget = 20000;

	/**
	 * Constructor method for Donation object
	 * 
	 * @param methoddonated
	 * @param received
	 * @param from_id
	 */
	public Donation(String methoddonated, int received, long from_id) {

		this.methoddonated = methoddonated;
		this.received = received;
		this.from_id = from_id;
	}
}