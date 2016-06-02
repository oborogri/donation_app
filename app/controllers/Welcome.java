package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Welcome extends Controller {

	public static void index() {
		render();
		
		/*
		if (session.get("logged_in_userid") == null) {
			Welcome.index();
		}

		else {
			String userId = session.get("logged_in_userid");
			User user = User.findById(Long.parseLong(userId));
			render(user);
		}*/
	}
	public static void donate() {
		
			index();
		}
	}

