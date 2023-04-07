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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet(urlPatterns = "/createUser")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
Connection connection;
  
	/**
	 * @see Servlet#init(ServletConfig)
	 *///servletConfig serve per configurare, è fatto apposta
	public void init(ServletConfig config) throws ServletException {
		//creo connessione
		try {
			ServletContext context = config.getServletContext();

			System.out.println("init()");
			//enumation lettura dei tre parametri di inizializzazione di cui abbiamo bisogno (url root e pswr) 
			//li prendo dall'web.xlm --> <parameter>
			Enumeration<String> parameterNames = context.getInitParameterNames();
			
			while (parameterNames.hasMoreElements()) {
				String eachName = parameterNames.nextElement();
				System.out.println("Nome parametro: " + eachName);
				System.out.println("Valore parametro: " + context.getInitParameter(eachName));
			}

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
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//creo tt ciò che serve per inserimento dati
			//i parametri email password li prendo dalla form in index.html
			//
			System.out.println("doPost()");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			try {
				Statement statement = connection.createStatement();
				int result = statement.executeUpdate("INSERT INTO users(first_name,last_name,email,password) VALUES('" + firstName + "','" + lastName + "','"
						+ email + "','" + password + "')");
				PrintWriter out = response.getWriter();
				if (result > 0) {
					out.print("<h1>CREATO UN NUOVO UTENTE</h1>"); 
				} else {
					out.print("<h1>OPS, QUALCOSA E' ANDATO STORTO. ERRORE NELLA CREAZIONE</h1>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			
		}
			}

		
	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		//chiudo connessione
		try {
			System.out.println("destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	

}
