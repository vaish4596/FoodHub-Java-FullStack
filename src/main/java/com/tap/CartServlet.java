package com.tap;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        String action = request.getParameter("action");
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
        String source = request.getParameter("source");

        if (loggedInUser == null) {
            String returnUrl = buildMenuReturnUrl(restaurantId);
            response.sendRedirect("login.html?loginRequired=1&returnUrl="
                    + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8));
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if ("clearAndAdd".equalsIgnoreCase(action)) {
            cart.clear();
            addItemToCart(cart, menuId, restaurantId, request);
            redirectAfterCartAction(response, source, restaurantId);
            return;
        }

        if ("clear".equalsIgnoreCase(action)) {
            cart.clear();
            redirectAfterCartAction(response, source, restaurantId);
            return;
        }

        if ("add".equalsIgnoreCase(action)) {
            if (!cart.canAddFromRestaurant(restaurantId)) {
                session.setAttribute("cartRestaurantConflict", Boolean.TRUE);
                session.setAttribute("pendingMenuId", menuId);
                session.setAttribute("pendingRestaurantId", restaurantId);
                response.sendRedirect("menu.jsp?restaurantId=" + restaurantId + "&cartConflict=1");
                return;
            }
            addItemToCart(cart, menuId, restaurantId, request);
        } else if ("update".equalsIgnoreCase(action)) {
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cart.updateQuantity(menuId, quantity);
        } else if ("delete".equalsIgnoreCase(action)) {
            cart.remove(menuId);
        }

        redirectAfterCartAction(response, source, restaurantId);
    }

    private void addItemToCart(Cart cart, int menuId, int restaurantId, HttpServletRequest request) {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        MenuDAOImpl menuDAO = new MenuDAOImpl();
        Menu menu = menuDAO.getMenu(menuId);

        if (menu != null) {
            CartItem item = new CartItem(
                    menu.getMenuId(),
                    menu.getRestaurantId(),
                    menu.getItemName(),
                    menu.getPrice(),
                    quantity,
                    menu.getImagePath());
            cart.addOrIncrease(item);
        }
    }

    private void redirectAfterCartAction(HttpServletResponse response, String source, int restaurantId)
            throws IOException {

        if ("menu".equalsIgnoreCase(source)) {
            response.sendRedirect("menu.jsp?restaurantId=" + restaurantId);
        } else {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.sendRedirect("cart.jsp");
        }
    }

    private String buildMenuReturnUrl(int restaurantId) {
        return "menu.jsp?restaurantId=" + restaurantId;
    }
}
