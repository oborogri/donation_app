package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class DonationController extends Controller {

	public static void index() {

		render();
	}

	public static void donate() {
		String userId = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(userId));
		render(user);
	}
	
	public static void donation(String methoddonated, int received) {
		String from_id = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(from_id));
		
		user.donate(received, methoddonated);
		
		render(user);
	}
}