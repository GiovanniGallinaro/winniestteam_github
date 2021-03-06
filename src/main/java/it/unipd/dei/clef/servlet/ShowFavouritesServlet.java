package it.unipd.dei.clef.servlet;

import it.unipd.dei.clef.database.ShowFavouritesDatabase;
import it.unipd.dei.clef.resource.Author;
import it.unipd.dei.clef.resource.ClefUser;
import it.unipd.dei.clef.resource.Message;

import java.util.ArrayList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ShowFavouritesServlet extends AbstractDatabaseServlet {

	/**
	 * Searches author by his ID
	 * 
	 * @param req
	 *            the HTTP request from the client.
	 * @param res
	 *            the HTTP response from the server.
	 * 
	 * @throws ServletException
	 *             if any error occurs while executing the servlet.
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Request parameter
		String email;
		Message m = null;

		// Model
		ClefUser user = null;

		try {
		
			// Retrieves the request parameter
			email = req.getParameter("email");

			// Creates a new object for accessing the database and searching the author
			user = new ShowFavouritesDatabase(getDataSource().getConnection(), email)
					.showFavourites();
			
			m = new Message("Author successfully searched.");
			
		} catch (NumberFormatException ex) {
			m = new Message("Cannot search for the author. Invalid input parameters: the id must be an integer.", 
					"E100", ex.getMessage());
			
		} catch (SQLException ex) {
				m = new Message("Cannot search for the author: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		// Stores the author and the message as a request attribute
		req.setAttribute("user", user);
		req.setAttribute("message", m);
		
		// Forwards the control to the author JSP
		req.getRequestDispatcher("/jsp/user.jsp").forward(req, res);
	
	}

}
