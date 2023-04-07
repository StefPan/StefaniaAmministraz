package it.stefania.amministrazione;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		/*
		 * dispatcher: ha due metodi forward che segue la destinazione e il metodo
		 * include che rimane ..vedi lezione day13 sulla richiesta chiedo di prendere
		 * quello che sta in link.html e chiedo di includerlo
		 */
		/*
		 * partendo dall'inpur request associo il link.html con dentro i link alle
		 * servlet, con la request e la response
		 */
		request.getRequestDispatcher("link.html").include(request, response);

		String name = request.getParameter("name");
		String password = request.getParameter("password");

		if (password.equals("admin")) {
			out.print("Benvenuto " + name);
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
		} else {
			out.print("La password non è corretta");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		out.close();
	}
}
