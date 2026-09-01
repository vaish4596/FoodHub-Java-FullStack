package com.tap.DAO;

import java.util.List;
import com.tap.model.OrderTable;

public interface OrderTableDAO {

	int addOrder(OrderTable order);

    OrderTable getOrder(int orderId);

    void updateOrder(OrderTable order);

    void deleteOrder(int orderId);

    List<OrderTable> getAllOrders();
    
    // NEW METHOD
    List<OrderTable> getOrdersByUserId(int userId);
}