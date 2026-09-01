package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.MenuDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO Menu(restaurantId, itemName, description, price, isAvailable, imagePath) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_QUERY =
            "SELECT * FROM Menu WHERE menuId = ?";

    private static final String GET_ALL_QUERY =
            "SELECT * FROM Menu";
    
    private static final String GET_MENU_BY_RESTAURANT =
            "SELECT * FROM Menu WHERE restaurantId = ?";

    private static final String UPDATE_QUERY =
            "UPDATE Menu SET restaurantId=?, itemName=?, description=?, price=?, isAvailable=?, imagePath=? WHERE menuId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM Menu WHERE menuId=?";

    @Override
    public void addMenu(Menu menu) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setBoolean(5, menu.isAvailable());
            pstmt.setString(6, menu.getImagePath());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Menu Added Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Menu getMenu(int menuId) {

        Menu menu = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, menuId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                menu = extractMenuFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menu;
    }
    
    @Override
    public List<Menu> getMenuByRestaurantId(int restaurantId) {

        List<Menu> menuList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_MENU_BY_RESTAURANT)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                menuList.add(extractMenuFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuList;
    }

    @Override
    public void updateMenu(Menu menu) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setBoolean(5, menu.isAvailable());
            pstmt.setString(6, menu.getImagePath());
            pstmt.setInt(7, menu.getMenuId());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Menu Updated Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenu(int menuId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, menuId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " Menu Deleted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> getAllMenu() {

        List<Menu> menuList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)) {

            while (rs.next()) {

                menuList.add(extractMenuFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuList;
    }

    private Menu extractMenuFromResultSet(ResultSet rs) throws SQLException {

        return new Menu(
                rs.getInt("menuId"),
                rs.getInt("restaurantId"),
                rs.getString("itemName"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getBoolean("isAvailable"),
                rs.getString("imagePath")
        );
    }
}