package com.tap.utility;

import java.sql.Timestamp;

import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.model.OrderTable;

public class TestOrderTable {

    public static void main(String[] args) {

        OrderTableDAOImpl orderDAO = new OrderTableDAOImpl();

        // INSERT

//        OrderTable order = new OrderTable(
//                1,
//                1,
//                new Timestamp(System.currentTimeMillis()),
//                500.0,
//                "Delivered",
//                "UPI");
//
//        orderDAO.addOrder(order);

        // GET

        OrderTable order = orderDAO.getOrder(1);
        System.out.println(order);

        // UPDATE

//        order.setStatus("Completed");
//        orderDAO.updateOrder(order);

        // DELETE

//        orderDAO.deleteOrder(1);
    }
}