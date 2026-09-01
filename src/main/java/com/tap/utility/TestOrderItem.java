package com.tap.utility;

import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.model.OrderItem;

public class TestOrderItem {

    public static void main(String[] args) {

        OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();

        // INSERT

//        OrderItem item = new OrderItem(
//                1,
//                1,
//                2,
//                500.0);
//
//        orderItemDAO.addOrderItem(item);

        // GET

        OrderItem item = orderItemDAO.getOrderItem(1);
        System.out.println(item);

        // UPDATE

//        item.setQuantity(5);
//        orderItemDAO.updateOrderItem(item);

        // DELETE

//        orderItemDAO.deleteOrderItem(1);
    }
}