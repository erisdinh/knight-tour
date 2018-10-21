package quynh;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/KnightTour")
public class KnightTour extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KnightTour() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		RequestDispatcher rd;

		String method = request.getParameter("method");

		// if the user chooses nonIntelligent method -> using RequesDispatcher to
		// NonIntelligent servlet
		if (method.equals("nonIntelligent")) {
			rd = request.getRequestDispatcher("NonIntelligent");
			rd.forward(request, response);
		} else {

			// if the user chooses Heuristic method -> using sendRedirect to Heuristic
			// servlet (passing parameter)
			HttpSession session = request.getSession(false);
			session.setAttribute("xPosition", request.getParameter("xPosition"));
			session.setAttribute("yPosition", request.getParameter("yPosition"));
			session.setAttribute("time", request.getParameter("time"));
			response.sendRedirect("Heuristic?session="+session);
		}
	}

}
