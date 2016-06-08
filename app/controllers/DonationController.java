package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class DonationController extends Controller {

	/**
	 * Renders default donation page
	 */
	public static void index() {

		render();
	}

	/**
	 * Renders new donation page if method donated not selected
	 */
	public static void donationerror() {
		String userId = session.get("logged_in_userid");
		User from = User.findById(Long.parseLong(userId));

		double progress = getProgress();

		render(from, progress);

	}

	/**
	 * Renders member's donation status page
	 */
	public static void donate() {
		String userId = session.get("logged_in_userid");
		User from = User.findById(Long.parseLong(userId));

		float progress = getProgress();

		render(from, progress);
	}

	/**
	 * Facilitates donation by logged in user Renders updated member's donation
	 * status page
	 * 
	 * @param methoddonated
	 * @param received
	 */
	public static void donation(String methoddonated, int received) {

		String userId = session.get("logged_in_userid");
		User from = User.findById(Long.parseLong(userId));

		if ((methoddonated == null) || (received < 1)) {
			Logger.info("No payment method selected");
			donationerror();
		}

		else {

			from.donate(received, methoddonated);
			Logger.info(from.email + " Just donated: " + received);
			float progress = getProgress();

			render(from, progress);
		}
	}

	/**
	 * Calculates and returns total donations made by a user
	 * 
	 * @param user
	 * @return contribution
	 */
	public static long getContribution(User from) {
		long contribution = 0;

		for (Donation d : from.donations) {
			contribution += d.received;

		}
		Logger.info(from.email + " Number contributions " + from.donations.size());
		Logger.info(from.email + " Total contributions: " + contribution);

		return contribution;
	}

	/**
	 * Calculates donation percentage against donation target
	 * 
	 * @return progress percentage of donation target
	 */
	public static float getProgress() {

		List<Donation> donations = Donation.findAll();
		int totaldonations = 0;

		for (Donation d : donations) {

			totaldonations += d.received;
		}

		float progress = ((float) (totaldonations * 100) / Donation.donationtarget);

		return progress;

	}

	/**
	 * Lists all donations and calculates total amount donated and target
	 * achieved renders totals to report page
	 */

	public static void report() {
		String userId = session.get("logged_in_userid");
		User from = User.findById(Long.parseLong(userId));

		List<Donation> donations = Donation.findAll();
		Logger.info(from.email + " Number donations: " + from.donations.size());

		int totaldonations = 0;

		// calculates total amount donated by all members
		for (Donation d : donations) {

			totaldonations += d.received;
		}

		float totalprogress = getProgress();

		Logger.info("Total donations received to date: " + totaldonations);
		Logger.info("Target achieved: " + totalprogress + " %");

		render(donations, totalprogress, totaldonations);
	}
}