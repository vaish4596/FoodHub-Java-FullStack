package com.tap;

import java.io.IOException;
import java.util.List;

import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.model.OrderTable;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myorders")
public class MyOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        OrderTableDAOImpl dao = new OrderTableDAOImpl();

        List<OrderTable> orders =
                dao.getOrdersByUserId(user.getUserId());

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("myorders.jsp")
               .forward(request, response);
    }
}