package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

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
	private JSONObject jsonObject = new JSONObject();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		String url = req.getRequestURI();
		String parse = url.split("/")[3];

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		switch (req.getMethod()) {
		case "GET":
			break;
		case "POST":
			if (parse.equals("signup"))
				handleSignup(req, resp);
			else if (parse.equals("login"))
				handleLogin(req, resp);
			break;
		case "PUT":
			handleUpdate(req, resp, Integer.parseInt(parse));
			break;
		case "DELETE":
			handleDelete(req, resp, Integer.parseInt(parse));
			break;
		}
	}

	private void handleSignup(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		User user = om.readValue(req.getReader(), User.class);
		user = userService.signup(user);

		// Set status and write json object
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), user);
	}

	private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		User user = om.readValue(req.getReader(), User.class);
		user = userService.login(user.getUsername(), user.getPassword());

		// Generate token
		String token = AuthUtil.generateToken(user);

		// Set status and write json object
		resp.setStatus(201);
		jsonObject.put("user", user.getUsername());
		jsonObject.put("token", token);
		om.writeValue(resp.getWriter(), jsonObject.toString());
	}

	private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, int id) {
		
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		int deleteCount = userService.deleteUser(id);

		if (deleteCount > 0) {
			// Set status and write json object
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), "User has been successfully deleted");
		}
	}
}
