package com.tap.utility;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

public class TestClass {

    public static void main(String[] args) {

        UserDAOImpl userDaoImpl = new UserDAOImpl();

//        User user = new User("Pravin", "pravin@gmail.com", "tapit",
//                             "Thiruvananthapuram", "customer");
//
//        userDaoImpl.addUser(user);
        
       User user = userDaoImpl.getUser(1);
       user.setEmail("ipdate@1223");
        System.out.println(user);
        
        userDaoImpl.updateUser(user);
    }
}