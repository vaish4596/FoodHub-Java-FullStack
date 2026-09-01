package com.tap;

import java.io.IOException;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.Cart;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        User sessionUser = (User) session.getAttribute("loggedInUser");

        if (sessionUser == null) {
            resp.sendRedirect("login.html");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            resp.sendRedirect("cart.jsp");
            return;
        }

        UserDAOImpl userDao = new UserDAOImpl();
        User user = userDao.getUser(sessionUser.getUserId());

        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        session.setAttribute("loggedInUser", user);

        double subtotal = cart.getTotalPrice();
        double delivery = 40;
        double gst = subtotal * 0.05;
        double grandTotal = subtotal + delivery + gst;

        session.setAttribute("grandTotal", grandTotal);

        req.setAttribute("user", user);
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("delivery", delivery);
        req.setAttribute("gst", gst);
        req.setAttribute("grandTotal", grandTotal);

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        req.getRequestDispatcher("checkout.jsp").forward(req, resp);
    }
}
