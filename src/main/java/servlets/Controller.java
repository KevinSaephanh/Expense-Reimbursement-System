package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class Controller {
	public abstract void process(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException;
}
