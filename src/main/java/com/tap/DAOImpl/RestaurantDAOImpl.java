package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO restaurant(name, cuisineType, deliveryTime, address, rating, isActive, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_QUERY =
            "SELECT * FROM restaurant WHERE restaurantId = ?";

    private static final String GET_ALL_QUERY =
            "SELECT * FROM restaurant";

    private static final String UPDATE_QUERY =
            "UPDATE restaurant SET name=?, cuisineType=?, deliveryTime=?, address=?, rating=?, isActive=?, imagePath=? WHERE restaurantId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM restaurant WHERE restaurantId=?";

    @Override
    public void addRestaurant(Restaurant restaurant) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getCuisineType());
            pstmt.setInt(3, restaurant.getDeliveryTime());
            pstmt.setString(4, restaurant.getAddress());
            pstmt.setDouble(5, restaurant.getRating());
            pstmt.setBoolean(6, restaurant.isActive());
            pstmt.setString(7, restaurant.getImagePath());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant getRestaurant(int restaurantId) {

        Restaurant restaurant = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                restaurant = new Restaurant(
                        rs.getInt("restaurantId"),
                        rs.getString("name"),
                        rs.getString("cuisineType"),
                        rs.getInt("deliveryTime"),
                        rs.getString("address"),
                        rs.getDouble("rating"),
                        rs.getBoolean("isActive"),
                        rs.getString("imagePath")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getCuisineType());
            pstmt.setInt(3, restaurant.getDeliveryTime());
            pstmt.setString(4, restaurant.getAddress());
            pstmt.setDouble(5, restaurant.getRating());
            pstmt.setBoolean(6, restaurant.isActive());
            pstmt.setString(7, restaurant.getImagePath());
            pstmt.setInt(8, restaurant.getRestaurantId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRestaurant(int restaurantId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, restaurantId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        List<Restaurant> restaurantList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)) {

            while (rs.next()) {
                restaurantList.add(extractRestaurantFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    // ================= SEARCH METHOD =================

    public List<Restaurant> searchRestaurant(String keyword) {

        List<Restaurant> restaurantList = new ArrayList<>();

        String SEARCH_QUERY =
                "SELECT * FROM restaurant WHERE name LIKE ? OR cuisineType LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SEARCH_QUERY)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                restaurantList.add(extractRestaurantFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    // ================= COMMON METHOD =================

    private Restaurant extractRestaurantFromResultSet(ResultSet rs) throws SQLException {

        return new Restaurant(
                rs.getInt("restaurantId"),
                rs.getString("name"),
                rs.getString("cuisineType"),
                rs.getInt("deliveryTime"),
                rs.getString("address"),
                rs.getDouble("rating"),
                rs.getBoolean("isActive"),
                rs.getString("imagePath")
        );
    }
}