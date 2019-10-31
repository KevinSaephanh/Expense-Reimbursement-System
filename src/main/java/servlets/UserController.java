package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import services.UserService;

public class UserController extends Controller {
	private UserService userService = new UserService();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		String url = req.getRequestURI();
		String parse = url.split("/")[3];
		System.out.println("This is the parsed url " + parse);
		
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (parse.equals(""))
        	System.out.println(parse);
        else if (parse.equals("signup"))
        	handleSignup(req, resp);
        else if (parse.equals("login"))
        	handleLogin(req, resp);
        else if (parse.matches(".*\\d.*")) {
        	
        }
        
//		switch (parse) {
//		case "":
//			break;
//		case "signup":
//			handleSignup(req, resp);
//			break;
//		case "login":
//			handleLogin(req, resp);
//			break;
//		default:
//			break;
//		}
	}

	private void handleSignup(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(req.getReader(), User.class);
		
		// Call service signup method
		user = userService.signup(user);
		
		// Return json
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), user);
	}
	
	private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(req.getReader(), User.class);
		
		// Call service login method
		user = userService.login(user.getUsername(), user.getPassword());
		
		// Return json
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), user);
	}
}
