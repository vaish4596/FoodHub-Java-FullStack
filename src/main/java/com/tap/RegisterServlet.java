package com.tap;

import java.io.IOException;
import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")


public class RegisterServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String address = req.getParameter("address");
		String role = req.getParameter("role");
				
	    String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
	     User user = new User(name, hashPassword, email, address, role);

	        // Step 4: Store user in database using DAO
	        UserDAOImpl userDao = new UserDAOImpl();
	        userDao.addUser(user);

	        // Step 5: Print success message
	       resp.sendRedirect("login.html");
	}

}
