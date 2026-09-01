package com.tap.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Integer, CartItem> items = new HashMap<>();

    public Cart() { }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public Integer getRestaurantId() {
        if (items.isEmpty()) {
            return null;
        }
        return items.values().iterator().next().getRestaurantId();
    }

    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean canAddFromRestaurant(int restaurantId) {
        if (items.isEmpty()) {
            return true;
        }
        Integer existing = getRestaurantId();
        return existing != null && existing == restaurantId;
    }

    public void addOrIncrease(CartItem item) {
        CartItem existing = items.get(item.getMenuId());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            items.put(item.getMenuId(), item);
        }
    }

    public void updateQuantity(int menuId, int delta) {
        CartItem existing = items.get(menuId);
        if (existing == null) {
            return;
        }

        int newQty = existing.getQuantity() + delta;
        if (newQty <= 0) {
            items.remove(menuId);
        } else {
            existing.setQuantity(newQty);
        }
    }

    public void remove(int menuId) {
        items.remove(menuId);
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
