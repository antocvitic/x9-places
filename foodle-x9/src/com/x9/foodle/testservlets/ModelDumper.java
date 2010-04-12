package com.x9.foodle.testservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.user.UserModel;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.venue.VenueModel;

@SuppressWarnings("serial")
public class ModelDumper extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		ServletOutputStream out = resp.getOutputStream();
		out
				.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>User Dumper</title></head><body>");

		String id = req.getParameter("id");
		String type = req.getParameter("type");

		Object model = null;
		if (type != null) {
			if (type.equals("user")) {
				if (id == null) {
					model = UserUtils.getCurrentUser(req);
					if (model != null)
						id = ((UserModel) model).getUsername();
				} else {
					model = UserModel.getFromDbByUsername(id);
				}
			} else if (type.equals("venue")) {
				if (id != null) {
					model = VenueModel.getFromSolr(id);
				}
			}
		}

		if (model == null) {
			out.println("No " + type + " with id: " + id);
		} else {
			out.println("Model (" + type + "): " + model);
		}

		out.println("<br/>");
		out.println("<br/>");

		out.println("<form action=\"dump-model\" method=\"GET\">");
		out.println("<label for=\"id\">ID (for User, username)</label>");
		out.println("<input name=\"id\" type=\"text\" value=\"" + id + "\"/>");
		out.println("<select name=\"type\"/>");

		String[] options = { "user", "venue" };
		for (String option : options) {
			out.println("<option value=\"" + option + "\"");
			if (option.equals(type))
				out.println("selected=\"selected\"");
			out.println(">" + option + "</option>");
		}
		out.println("</select/>");
		out.println("<input type=\"submit\" value=\"Dump model\"/>");
		out.println("</form>");

		out.println("</body></html>");
	}

}
