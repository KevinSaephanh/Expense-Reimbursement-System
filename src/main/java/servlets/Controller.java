package servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
	public abstract void process(HttpServletRequest req, HttpServletResponse resp);
}
