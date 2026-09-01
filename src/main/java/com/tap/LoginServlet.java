package com.tap;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String REMEMBER_EMAIL_COOKIE = "foodhub_remember_email";
    private static final int REMEMBER_DAYS = 30;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        String returnUrl = request.getParameter("returnUrl");

        UserDAOImpl userDao = new UserDAOImpl();
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            user = userDao.getUserByEmail(username);
        }

        if (user == null) {
            redirectLoginWithError(response, returnUrl, "username");
            return;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            redirectLoginWithError(response, returnUrl, "password");
            return;
        }

        user.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
        userDao.updateUser(user);

        User freshUser = userDao.getUser(user.getUserId());
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", freshUser);

        handleRememberMeCookie(response, rememberMe, freshUser.getEmail());

        if (returnUrl != null && !returnUrl.isBlank() && isSafeReturnUrl(returnUrl)) {
            response.sendRedirect(returnUrl);
        } else {
            response.sendRedirect("home.jsp");
        }
    }

    private void handleRememberMeCookie(HttpServletResponse response, String rememberMe, String email) {
        Cookie cookie = new Cookie(REMEMBER_EMAIL_COOKIE, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        if ("on".equalsIgnoreCase(rememberMe) && email != null && !email.isBlank()) {
            cookie = new Cookie(REMEMBER_EMAIL_COOKIE, email.trim());
            cookie.setPath("/");
            cookie.setMaxAge(REMEMBER_DAYS * 24 * 60 * 60);
            cookie.setHttpOnly(true);
        }

        response.addCookie(cookie);
    }

    private void redirectLoginWithError(HttpServletResponse response, String returnUrl, String error)
            throws IOException {

        StringBuilder target = new StringBuilder("login.html?error=").append(error);
        if (returnUrl != null && !returnUrl.isBlank() && isSafeReturnUrl(returnUrl)) {
            target.append("&returnUrl=")
                    .append(URLEncoder.encode(returnUrl, StandardCharsets.UTF_8));
        }
        response.sendRedirect(target.toString());
    }

    /** Only allow relative in-app redirects. */
    private boolean isSafeReturnUrl(String returnUrl) {
        if (returnUrl.contains("://") || returnUrl.startsWith("//")) {
            return false;
        }
        return returnUrl.startsWith("menu.jsp")
                || returnUrl.startsWith("home.jsp")
                || returnUrl.startsWith("cart.jsp")
                || returnUrl.startsWith("profile.jsp");
    }
}
