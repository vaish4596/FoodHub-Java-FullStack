package com.tap.utility;

import java.sql.Timestamp;

import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.model.Menu;

public class TestMenu {

    public static void main(String[] args) {

        MenuDAOImpl menuDAO = new MenuDAOImpl();

        // INSERT

//        Menu menu = new Menu(
//                1,
//                "Chicken Biryani",
//                "Special Dum Biryani",
//                250.0,
//                true,
//                "Main Course",
//                new Timestamp(System.currentTimeMillis()),
//                new Timestamp(System.currentTimeMillis()),
//                null);
//
//        menuDAO.addMenu(menu);

        // GET

        Menu menu = menuDAO.getMenu(1);
        System.out.println(menu);

        // UPDATE

//        menu.setPrice(300);
//        menuDAO.updateMenu(menu);

        // DELETE

//        menuDAO.deleteMenu(1);
    }
}