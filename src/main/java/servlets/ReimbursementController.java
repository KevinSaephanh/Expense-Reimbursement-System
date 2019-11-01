package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import services.ReimbursementService;

public class ReimbursementController extends Controller {
	ReimbursementService rs = new ReimbursementService();
	private ObjectMapper om = new ObjectMapper();
	private JSONObject jsonObject = new JSONObject();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		String url = req.getRequestURI();
		String parse = url.split("/")[2];

		switch (req.getMethod()) {
		case "GET":
			if (parse.equals(""))
				handleGetAll(req, resp);
			else if (parse.matches("\\\\d+"))
				handleGet(req, resp, Integer.parseInt(parse));
			break;
		case "POST":
			handleCreate(req, resp);
			break;
		case "PUT":
			handleUpdate(req, resp, Integer.parseInt(parse));
			break;
		case "DELETE":
			handleDelete(req, resp, Integer.parseInt(parse));
			break;
		}
	}

	private void handleGetAll(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void handleGet(HttpServletRequest req, HttpServletResponse resp, int id) {

	}

	private void handleCreate(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		Reimbursement reimb = om.readValue(req.getReader(), Reimbursement.class);
		rs.createReimb(reimb);
		
		// Set status and write json object
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), reimb);
	}

	private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, int id) {

	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		int deleteCount = rs.deleteReimb(id);

		if (deleteCount > 0) {
			// Set status and write json object
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), "Reimbursement has been successfully deleted");
		}
	}
}
