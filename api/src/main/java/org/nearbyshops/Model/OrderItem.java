package org.nearbyshops.Model;

/**
 * Created by sumeet on 29/5/16.
 */
public class OrderItem {

    private int itemID;
    private int orderID;
    private int itemQuantity;
    private int itemPriceAtOrder;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemPriceAtOrder() {
        return itemPriceAtOrder;
    }

    public void setItemPriceAtOrder(int itemPriceAtOrder) {
        this.itemPriceAtOrder = itemPriceAtOrder;
    }
}
