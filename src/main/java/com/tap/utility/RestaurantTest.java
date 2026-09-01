package com.tap.utility;

import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

public class RestaurantTest {

    public static void main(String[] args) {

        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();

        // INSERT

//        Restaurant restaurant = new Restaurant(
//                "Empire Restaurant",
//                "South Indian",
//                30,
//                "Bangalore",
//                1,
//                4.5,
//                true);
//
//        restaurantDAO.addRestaurant(restaurant);

        // GET

        Restaurant restaurant = restaurantDAO.getRestaurant(1);
        System.out.println(restaurant);

        // UPDATE

        restaurant.setRating(4.8);
        restaurantDAO.updateRestaurant(restaurant);
        System.out.println(restaurant);

        // DELETE

        restaurantDAO.deleteRestaurant(1);
        
    }
}