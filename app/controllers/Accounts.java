package controllers;

import play.*;
import play.mvc.*;

import java.util.List;

import java.util.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import models.*;

public class Accounts extends Controller {

	/**
	 * Renders signup page
	 */
	public static void signup() {
		render();
	}

	/**
	 * Renders login page
	 */
	public static void login() {
		render();
	}

	/**
	 * Renders error page
	 */
	public static void error() {
		render();
	}

	/**
	 * Logs out user and renders start page
	 */
	public static void logout() {
		session.clear();
		Welcome.index();
	}

	/**
	 * Checks login details are correct
	 * 
	 * @param email
	 * @param password
	 */
	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + " : " + password);
		User user = User.findByEmail(email);

		if ((user != null) && (user.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_userid", user.id);
			DonationController.donate();

		} else {
			Logger.info("Authentication failed");
			error();
		}
	}

	/**
	 * Registers new user with details entered on sign up page Displays error
	 * message if user already registered and if user not USA citizen
	 * 
	 * @param user
	 */
	public static void register(User user) {
		List<User> users = User.findAll();

		for (User a : users) {
			if (equalUser(user, a)) {
				Logger.info("Error - user " + user.email + " already registered!");
				error();
			}
		}
		if ((isValidEmailAddress(user.email) && ((user.usacitizen != false)))) {
			user.save();
			Logger.info("New member details: " + user.firstName + " " + user.lastName + " " + user.email + " "
					+ user.password);
			Welcome.index();
		} else {
			Logger.info("Error - user " + user.email + " not registered! Please check your details!");
			error();
		}
	}

	/**
	 * Compares two users based on their e-mails
	 * 
	 * @param User
	 *            a
	 * @param User
	 *            b
	 * 
	 * @return true if user e-mails are the same
	 */
	private static boolean equalUser(User a, User b) {
		return (a.email.equals(b.email));
	}

	/**
	 * Checks valid e-mail format
	 * 
	 * @param email
	 * @return true if e-mail not null and is a valid format
	 */
	public static boolean isValidEmailAddress(String email) {

		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Redirects to sign in page if user not logged and tries to access Make
	 * Donation
	 */
	public static void donate() {
		if (session.get("logged_in_userid") == null) {
			Welcome.index();
		}

		else {
			String userId = session.get("logged_in_userid");
			User user = User.findById(Long.parseLong(userId));
			render(user);
		}
	}

}