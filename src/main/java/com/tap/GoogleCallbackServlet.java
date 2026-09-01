package com.tap;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;
import com.tap.utility.GoogleOAuthConfig;
import com.tap.utility.GoogleOAuthHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/oauth2callback")
public class GoogleCallbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = request.getParameter("error");
        if (error != null) {
            response.sendRedirect("login.html?error=google_denied");
            return;
        }

        String code = request.getParameter("code");
        if (code == null || code.isBlank()) {
            response.sendRedirect("login.html?error=google");
            return;
        }

        try {
            GoogleOAuthConfig config = GoogleOAuthConfig.load(getServletContext());
            Map<String, String> tokenResponse = GoogleOAuthHelper.exchangeCodeForToken(config, code);
            String accessToken = tokenResponse.get("access_token");

            Map<String, String> profile = GoogleOAuthHelper.fetchUserInfo(accessToken);
            String email = profile.get("email");
            String name = profile.get("name");

            if (email == null || email.isBlank()) {
                response.sendRedirect("login.html?error=google_email");
                return;
            }

            UserDAOImpl userDao = new UserDAOImpl();
            User user = userDao.getUserByEmail(email);

            if (user == null) {
                String username = buildUniqueUsername(userDao, name, email);
                String randomSecret = UUID.randomUUID().toString();
                String hashed = BCrypt.hashpw(randomSecret, BCrypt.gensalt(12));
                Timestamp now = new Timestamp(System.currentTimeMillis());

                user = new User(username, hashed, email, "Not provided", "Customer", now, now);
                userDao.addUser(user);
                user = userDao.getUserByEmail(email);
            }

            user.setLastLoginDate(new Timestamp(System.currentTimeMillis()));
            userDao.updateUser(user);
            user = userDao.getUser(user.getUserId());

            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);

            response.sendRedirect("home.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=google");
        }
    }

    private String buildUniqueUsername(UserDAOImpl userDao, String name, String email) {
        String base;
        if (name != null && !name.isBlank()) {
            base = name.trim().replaceAll("\\s+", "_");
        } else {
            base = email.substring(0, email.indexOf('@'));
        }

        base = base.replaceAll("[^a-zA-Z0-9_]", "");
        if (base.isEmpty()) {
            base = "user";
        }

        String candidate = base;
        int suffix = 1;
        while (userDao.getUserByUsername(candidate) != null) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }
}
