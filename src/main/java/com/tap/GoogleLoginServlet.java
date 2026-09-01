package com.tap;

import java.io.IOException;

import com.tap.utility.GoogleOAuthConfig;
import com.tap.utility.GoogleOAuthHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/googleLogin")
public class GoogleLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            GoogleOAuthConfig config = GoogleOAuthConfig.load(getServletContext());
            if (!config.isConfigured()) {
                response.sendRedirect("login.html?error=google_config");
                return;
            }
            response.sendRedirect(GoogleOAuthHelper.buildAuthorizationUrl(config));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=google");
        }
    }
}
