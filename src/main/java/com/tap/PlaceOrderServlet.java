package com.tap;

import java.io.IOException;
import java.sql.Timestamp;

import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.OrderItem;
import com.tap.model.OrderTable;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {


protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {


HttpSession session=request.getSession();


User user=(User)session.getAttribute("loggedInUser");

if(user==null){
    response.sendRedirect("login.html");
    return;
}

UserDAOImpl userDao = new UserDAOImpl();
User freshUser = userDao.getUser(user.getUserId());
if (freshUser != null) {
    user = freshUser;
    session.setAttribute("loggedInUser", user);
}

Cart cart=(Cart)session.getAttribute("cart");


if(user==null){
    response.sendRedirect("login.html");
    return;
}


if(cart==null || cart.getItems().isEmpty()){
    response.sendRedirect("cart.jsp");
    return;
}



String paymentMethod=request.getParameter("paymentMethod");



double grandTotal=(Double)session.getAttribute("grandTotal");



int restaurantId =
cart.getItems()
.values()
.iterator()
.next()
.getRestaurantId();



OrderTable order=new OrderTable();


order.setUserId(user.getUserId());

order.setRestaurantId(restaurantId);

order.setOrderDate(new Timestamp(System.currentTimeMillis()));

order.setTotalAmount(grandTotal);

order.setStatus("PLACED");

order.setPaymentMethod(paymentMethod);



OrderTableDAOImpl orderDAO=new OrderTableDAOImpl();


int orderId=orderDAO.addOrder(order);



OrderItemDAOImpl orderItemDAO=new OrderItemDAOImpl();



for(CartItem item:cart.getItems().values()){


OrderItem orderItem=new OrderItem();


orderItem.setOrderId(orderId);

orderItem.setMenuId(item.getMenuId());

orderItem.setQuantity(item.getQuantity());

orderItem.setItemTotal(
        item.getPrice()*item.getQuantity()
);


orderItemDAO.addOrderItem(orderItem);


}



session.removeAttribute("cart");

session.removeAttribute("grandTotal");



request.setAttribute("orderId", orderId);


request.getRequestDispatcher("orderSuccess.jsp")
.forward(request,response);


}

}