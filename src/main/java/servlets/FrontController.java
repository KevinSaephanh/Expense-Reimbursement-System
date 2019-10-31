package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import services.UserService;

/*
 * FrontController
 * 		Controller A, B, C, etc.
 * 
 * Do this in FrontController
 * 		Controller c = getController (url)
 * 		String method = getMethod(req.getMethodType())
 * 		switch (method) {
 * 			case "get": c.get(req, res); break;
 * 			case "post": c.post(req, res); break;
 * 			...
 * 		}
 * 
 * 		method getController (url) {
 * 			parse = url.split("/")[2];
 * 			switch (parse) {
 * 				case a: return a;
 * 				case b: return b;
 * 				...
 * 			}
 * 		}
 * 
 * */

@WebServlet(name = "FrontController", urlPatterns = "/*")
public class FrontController extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String route = req.getPathInfo();
		System.out.println(route);
		System.out.println(req.getMethod());

		Controller c = getController(req.getPathInfo());
		c.process(req, resp);

		// Move these to separate controllers with switch case for method type
//		if (route.equals("/users/login")) {
//			ObjectMapper om = new ObjectMapper();
//			User user = om.readValue(req.getReader(), User.class);
//			user = userService.login(user.getUsername(), user.getPassword());
//			resp.setStatus(201);
//			om.writeValue(resp.getWriter(), user);
//		} else if (route.equals("/users/signup")) {
//			ObjectMapper om = new ObjectMapper();
//			User user = om.readValue(req.getReader(), User.class);
//			user = userService.signup(user);
//			resp.setStatus(201);
//			om.writeValue(resp.getWriter(), user);
//		} else if (route.matches(".*\\d.*")) {
//			route = route.replaceAll("\\D+", "");
//			System.out.println(route);
//			// Call some service method that requires id
//		} else {
//			// 404?
//		}
	}

	private Controller getController(String url) {
		if (url.startsWith("/users/"))
			return new UserController();
		else if (url.startsWith("/reimbursements/"))
			return new ReimbursementController();
		else
			return null;
	}
}
