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

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User sessionUser = (User) session.getAttribute("loggedInUser");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (oldPassword == null || newPassword == null || confirmPassword == null
                || oldPassword.isBlank() || newPassword.isBlank()) {
            response.sendRedirect("profile.jsp?pwdError=missing");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect("profile.jsp?pwdError=mismatch");
            return;
        }

        UserDAOImpl dao = new UserDAOImpl();
        User dbUser = dao.getUser(sessionUser.getUserId());

        if (dbUser == null || !BCrypt.checkpw(oldPassword, dbUser.getPassword())) {
            response.sendRedirect("profile.jsp?pwdError=old");
            return;
        }

        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        dao.updatePassword(sessionUser.getUserId(), hashed);

        User updatedUser = dao.getUser(sessionUser.getUserId());
        session.setAttribute("loggedInUser", updatedUser);

        response.sendRedirect("profile.jsp?pwdUpdated=1");
    }
}
