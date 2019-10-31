package servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReimbursementController extends Controller {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURI();
		String parse = url.split("/")[2];
	}

}
