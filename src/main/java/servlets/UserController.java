package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import services.UserService;
import utils.AuthUtil;

public class UserController extends Controller {
	private UserService userService = new UserService();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		String url = req.getRequestURI();
		String[] parse = url.split("/");
		int id = 0;

		// Set CORS access
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		resp.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		// Check if id was provided in URI
		if (parse.length > 3 && parse[3].matches(".*\\d.*"))
			id = Integer.parseInt(parse[3]);

		// Call associated handler using request method
		switch (req.getMethod()) {
		case "GET":
			if (id != 0)
				handleGet(req, resp, id);
			else if (parse[2].equals("users"))
				handleGetAll(req, resp);
			break;
		case "POST":
			if (parse[3].equals("signup"))
				handleSignup(req, resp);
			else if (parse[3].equals("login"))
				handleLogin(req, resp);
			break;
		case "PUT":
			handleUpdate(req, resp, id);
			break;
		case "DELETE":
			handleDelete(req, resp, id);
			break;
		}
	}

	private void handleGet(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.getUser(id);

		// Set status and write json object
		if (user != null) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), user);
		} else {
			resp.setStatus(400);
			om.writeValue(resp.getWriter(), "Could not retrieve user");
		}
	}

	private void handleGetAll(HttpServletRequest req, HttpServletResponse resp)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<User> users = userService.getAllUsers();

		// Set status and write json object
		if (users != null) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), users);
		} else {
			resp.setStatus(400);
			om.writeValue(resp.getWriter(), "No users in the database");
		}
	}

	private void handleSignup(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		User user = om.readValue(req.getReader(), User.class);
		user = userService.signup(user);

		// Set status and write json object
		if (user != null) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), user);
		} else {
			resp.setStatus(400);
			// Write some meaningful json object
		}
	}

	private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		User user = om.readValue(req.getReader(), User.class);
		user = userService.login(user.getUsername(), user.getPassword());

		// Set status and write json object
		if (user != null) {
			// Generate token
			String token = AuthUtil.generateToken(user);
			
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), token);
		} else {
			resp.setStatus(400);
			om.writeValue(resp.getWriter(), "Incorrect username/password");
		}
	}

	private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonParseException, JsonMappingException, IOException {
		User user = om.readValue(req.getReader(), User.class);
		user = userService.updateUser(user, id);

		// Set status and write json object
		if (user != null) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), user);
		} else {
			resp.setStatus(400);
			om.writeValue(resp.getWriter(), "User could not be updated");
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		int deleteCount = userService.deleteUser(id);

		// Check if a user was deleted
		if (deleteCount > 0) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), "User has been successfully deleted");
		}
	}
}
