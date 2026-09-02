package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderItemDAO;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO orderitem(orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?)";

    private static final String GET_QUERY =
            "SELECT * FROM orderitem WHERE orderItemId = ?";

    private static final String GET_ALL_QUERY =
            "SELECT * FROM orderitem";

    private static final String UPDATE_QUERY =
            "UPDATE orderitem SET orderId=?, menuId=?, quantity=?, itemTotal=? WHERE orderItemId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM orderitem WHERE orderItemId=?";

    @Override
    public void addOrderItem(OrderItem orderItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getMenuId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getItemTotal());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Order Item Added Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {

        OrderItem orderItem = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, orderItemId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                orderItem = extractOrderItemFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getMenuId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getItemTotal());
            pstmt.setInt(5, orderItem.getOrderItemId());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Order Item Updated Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int orderItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, orderItemId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Order Item Deleted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> getAllOrderItems() {

        List<OrderItem> orderItemList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)) {

            while (rs.next()) {
                orderItemList.add(extractOrderItemFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItemList;
    }

    private OrderItem extractOrderItemFromResultSet(ResultSet rs) throws SQLException {

        return new OrderItem(
                rs.getInt("orderItemId"),
                rs.getInt("orderId"),
                rs.getInt("menuId"),
                rs.getInt("quantity"),
                rs.getDouble("itemTotal")
        );
    }
}