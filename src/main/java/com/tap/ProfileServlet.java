package com.tap;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User sessionUser = (User) session.getAttribute("loggedInUser");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        UserDAOImpl dao = new UserDAOImpl();
        User dbUser = dao.getUser(sessionUser.getUserId());

        if (dbUser == null) {
            response.sendRedirect("login.html");
            return;
        }

        dbUser.setUserName(username);
        dbUser.setEmail(email);
        dbUser.setAddress(address);

        dao.updateUserProfile(dbUser);

        User updatedUser = dao.getUser(sessionUser.getUserId());
        session.setAttribute("loggedInUser", updatedUser);

        response.sendRedirect("profile.jsp?updated=1");
    }
}
