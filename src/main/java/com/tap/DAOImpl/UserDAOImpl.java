package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO User(username,email,password,address,role,createdDate,lastLoginDate) VALUES(?,?,?,?,?,?,?)";

    private static final String GET_QUERY =
            "SELECT * FROM User WHERE userId=?";

    private static final String GET_ALL_QUERY =
            "SELECT * FROM User";

    private static final String UPDATE_QUERY =
            "UPDATE User SET username=?, password=?, email=?, address=?, role=?, lastLoginDate=? WHERE userId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM User WHERE userId=?";

    @Override
    public void addUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getRole());

            pstmt.setTimestamp(6,
                    user.getCreatedDate() != null ?
                            user.getCreatedDate() :
                            new Timestamp(System.currentTimeMillis()));

            pstmt.setTimestamp(7,
                    user.getLastLoginDate() != null ?
                            user.getLastLoginDate() :
                            new Timestamp(System.currentTimeMillis()));

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " User Added Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int userId) {

        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                user = new User(
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("lastLoginDate")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username) {

        User user = null;

        String sql = "SELECT * FROM User WHERE username=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                user = new User(
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getTimestamp("createdDate"),
                        rs.getTimestamp("lastLoginDate")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {

        User user = null;
        String sql = "SELECT * FROM User WHERE email=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void updateUserProfile(User user) {

        String sql = "UPDATE User SET username=?, email=?, address=?, role=? WHERE userId=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getRole());
            pstmt.setInt(5, user.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int userId, String hashedPassword) {

        String sql = "UPDATE User SET password=? WHERE userId=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {

        String sql = "UPDATE User SET username=?, email=?, password=?, address=?, role=?, lastLoginDate=? WHERE userId=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getRole());
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(7, user.getUserId());

            int rows = pstmt.executeUpdate();

            if(rows > 0){
                System.out.println("Profile Updated Successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() {

        List<User> list = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)) {

            while (rs.next()) {

                list.add(extractUserFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static User extractUserFromResultSet(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("userId"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("role"),
                rs.getTimestamp("createdDate"),
                rs.getTimestamp("lastLoginDate")
        );
    }

    @Override
    public void deleteUser(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, userId);

            int rows = pstmt.executeUpdate();

            System.out.println(rows + " User Deleted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}