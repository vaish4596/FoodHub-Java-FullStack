package com.tap;

import java.io.IOException;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        UserDAOImpl dao = new UserDAOImpl();

        User user = dao.getUser(userId);

        user.setUserName(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setRole(role);

        dao.updateUserProfile(user);

        User refreshed = dao.getUser(userId);

        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", refreshed);

        response.sendRedirect("profile.jsp");
    }
}