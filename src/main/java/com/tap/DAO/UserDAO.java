package com.tap.DAO;

import java.util.List;

import com.tap.model.User;

public interface UserDAO {
	
	void addUser(User user);
	User getUser(int userId);
	User getUserByUsername(String username);
	User getUserByEmail(String email);
	void updateUser(User user);
	void updateUserProfile(User user);
	void updatePassword(int userId, String hashedPassword);
	void deleteUser(int userId);
	List<User> getAllUser();

}
