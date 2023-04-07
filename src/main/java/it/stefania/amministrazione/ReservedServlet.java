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
 * Servlet implementation class ReservedServlet
 */
@WebServlet(urlPatterns = "/profilo")
public class ReservedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// IL DISPATCHER AGGIUNGE IL FRAMMETO DI TEMPLATE (LINK.HTML)
		/*
		 * ATTACCO IL DISPACTCHER AL REQUEST, GLI ATTACCO IL PARAMETRO LINK, E LO
		 * INCLUDE (richiama metodo include che ha come parametri request e response
		 */
		request.getRequestDispatcher("link.html").include(request, response);
	
		// associo sessione
		HttpSession session = request.getSession(false);
		if (session != null) {
			String name = (String) session.getAttribute("name");

			out.print("Ciao, " + name + " Sei nella tua area riservata");
			out.print("<hr>");
			request.getRequestDispatcher("reservedLink.html").include(request, response);
		} else {
			out.print("Non hai fatto il Login");
			request.getRequestDispatcher("login.html").include(request, response);
		}
		
		out.close();
		
	}
}
