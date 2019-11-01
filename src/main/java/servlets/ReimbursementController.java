package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import services.ReimbursementService;

/*
 * Receipt is an image to be stored
 * Users can add a receipt to a reimbursement form then send the form request to the server
 * The server will take call the service and then dao to save to the db
 * The db will send data back to the dao then back to the service ... to the client
 * The image will then be sent to the server to be added to the database and the process repeats
 * until some json (image, success, etc.) is sent to the client
 * */
public class ReimbursementController extends Controller {
	ReimbursementService rs = new ReimbursementService();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		String url = req.getRequestURI();
		String[] parse = url.split("/");
		int id = 0;

		// Check if id was provided in URI
		if (parse.length > 3)
			id = Integer.parseInt(parse[3]);

		switch (req.getMethod()) {
		case "GET":
			if (id != 0)
				handleGet(req, resp, id);
			else if (parse[2].equals("reimbursements"))
				handleGetAll(req, resp);
			break;
		case "POST":
			if (parse[2].equals("reimbursements"))
				handleCreate(req, resp);
			break;
		case "PUT":
			handleUpdate(req, resp, id);
			break;
		case "DELETE":
			handleDelete(req, resp, id);
			break;
		}
	}

	private void handleGetAll(HttpServletRequest req, HttpServletResponse resp)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Reimbursement> reimbs = rs.getAllReimbs();
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), reimbs);
	}

	private void handleGet(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonParseException, JsonMappingException, IOException {
		Reimbursement reimb = rs.getReimb(id);
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), reimb);
	}

	private void handleCreate(HttpServletRequest req, HttpServletResponse resp)
			throws JsonParseException, JsonMappingException, IOException {
		Reimbursement reimb = om.readValue(req.getReader(), Reimbursement.class);
		rs.createReimb(reimb);
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), reimb);
	}

	private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonParseException, JsonMappingException, IOException {
		Reimbursement reimb = om.readValue(req.getReader(), Reimbursement.class);
		reimb = rs.updateReimb(reimb.getId(), reimb.getResolverId(), reimb.getReimbStatusId());

		// Set status and write json object
		resp.setStatus(201);
		om.writeValue(resp.getWriter(), reimb);
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp, int id)
			throws JsonGenerationException, JsonMappingException, IOException {
		int deleteCount = rs.deleteReimb(id);
		if (deleteCount > 0) {
			resp.setStatus(201);
			om.writeValue(resp.getWriter(), "Reimbursement has been successfully deleted");
		}
	}
}
