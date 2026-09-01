package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderTableDAO;
import com.tap.model.OrderTable;
import com.tap.utility.DBConnection;

public class OrderTableDAOImpl implements OrderTableDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO OrderTable(userId, restaurantId, orderDate, totalAmount, status, paymentMethod) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_QUERY =
            "SELECT * FROM OrderTable WHERE orderId=?";

    private static final String GET_ALL_QUERY =
            "SELECT * FROM OrderTable";

    private static final String UPDATE_QUERY =
            "UPDATE OrderTable SET userId=?, restaurantId=?, orderDate=?, totalAmount=?, status=?, paymentMethod=? WHERE orderId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM OrderTable WHERE orderId=?";
    
    private static final String GET_USER_ORDERS =
            "SELECT * FROM OrderTable WHERE userId=? ORDER BY orderDate DESC";

    @Override
  
    public int addOrder(OrderTable order) {

        int orderId = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt =
                 con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());

            if(order.getOrderDate()==null){
                pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            }
            else{
                pstmt.setTimestamp(3,order.getOrderDate());
            }

            pstmt.setDouble(4,order.getTotalAmount());
            pstmt.setString(5,order.getStatus());
            pstmt.setString(6,order.getPaymentMethod());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                orderId = rs.getInt(1);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return orderId;
    }

    @Override
    public OrderTable getOrder(int orderId) {

        OrderTable order = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, orderId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                order = new OrderTable(
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        rs.getTimestamp("orderDate"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status"),
                        rs.getString("paymentMethod"),
                        rs.getInt("restaurantId")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public void updateOrder(OrderTable order) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setTimestamp(3, order.getOrderDate());
            pstmt.setDouble(4, order.getTotalAmount());
            pstmt.setString(5, order.getStatus());
            pstmt.setString(6, order.getPaymentMethod());
            pstmt.setInt(7, order.getOrderId());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Order Updated Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, orderId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Order Deleted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderTable> getAllOrders() {

        List<OrderTable> orderList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)) {

            while (rs.next()) {
                orderList.add(extractOrderFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
    
    @Override
    public List<OrderTable> getOrdersByUserId(int userId) {

        List<OrderTable> orderList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_USER_ORDERS)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orderList.add(extractOrderFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    private OrderTable extractOrderFromResultSet(ResultSet rs) throws SQLException {

        return new OrderTable(
                rs.getInt("orderId"),
                rs.getInt("userId"),
                rs.getTimestamp("orderDate"),
                rs.getDouble("totalAmount"),
                rs.getString("status"),
                rs.getString("paymentMethod"),
                rs.getInt("restaurantId")
        );
    }
}