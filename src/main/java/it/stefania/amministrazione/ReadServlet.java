package it.stefania.amministrazione;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ReadServlet
 */
@WebServlet(urlPatterns = "/leggi")
public class ReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			ServletContext context = config.getServletContext();
			System.out.println("init()");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
			PrintWriter out = response.getWriter();
			out.print(" <h1><a href=\"reservedLink.html\"action = \"leggi\">Torna alla tua pagina</a></h1>");
			
			out.print("<tr>");
			out.print("<table>");
			out.print("<tr>");
			out.print("<th>");
			out.println("ID");
			out.print("</th>");
			out.print("<th>");
			out.println("Nome");
			out.print("</th>");
			out.print("<th>");
			out.println("Cognome");
			out.print("</th>");
			out.print("<th>");
			out.println("Email ");
			out.print("</th>");
			out.print("</tr>");
			
			while (resultSet.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.print(resultSet.getInt(1));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(2));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(3));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(4));
				out.println("</td>");
				out.println("</tr>");
			}
			out.print("</table>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			System.out.println("destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}