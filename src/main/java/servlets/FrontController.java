package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
