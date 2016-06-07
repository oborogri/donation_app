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
		User user = User.findById(Long.parseLong(userId));

		double progress = getProgress();

		render(user, progress);

	}

	/**
	 * Renders member's donation status page
	 */
	public static void donate() {
		String userId = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(userId));

		double progress = getProgress();

		render(user, progress);
	}

	/**
	 * Facilitates donation by logged in user Renders updated member's donation
	 * status page
	 * 
	 * @param methoddonated
	 * @param received
	 */
	public static void donation(String methoddonated, int received) {

		String from_id = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(from_id));

		if (methoddonated == null) {
			Logger.info("No payment method selected");
			donationerror();

		} else {
			user.donate(received, methoddonated, user);

			double progress = getProgress();

			render(user, progress);
		}
	}

	/**
	 * Calculates and returns total donations made by a user
	 * 
	 * @param user
	 * @return contribution
	 */
	public static long getContribution(User user) {
		long contribution = 0;

		for (Donation d : user.donations) {
			contribution += d.received;
		}
		return contribution;
	}

	/**
	 * Calculates donation percentage against donation target
	 * 
	 * @return progress percentage of donation target
	 */
	public static double getProgress() {

		String userId = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(userId));

		double progress = (getContribution(user) * 100) / Donation.donationtarget;
		return progress;

	}
}