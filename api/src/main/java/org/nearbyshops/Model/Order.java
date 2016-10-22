package org.nearbyshops.Model;

import org.nearbyshops.ModelDeliverySelf.DeliveryAddress;
import org.nearbyshops.ModelStats.OrderStats;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class Order {




    private int orderID;
    private int endUserID;
    private int shopID;
    //int orderStatus;

    private int statusHomeDelivery;
    private int statusPickFromShop;
    private boolean deliveryReceived;
    private boolean paymentReceived;

    private int deliveryCharges;
    private int deliveryAddressID;
    private boolean pickFromShop;

    private int deliveryVehicleSelfID;

    private Timestamp dateTimePlaced;


    private DeliveryAddress deliveryAddress;

    private OrderStats orderStats;

    public int getDeliveryVehicleSelfID() {
        return deliveryVehicleSelfID;
    }

    public void setDeliveryVehicleSelfID(int deliveryVehicleSelfID) {
        this.deliveryVehicleSelfID = deliveryVehicleSelfID;
    }

    public OrderStats getOrderStats() {
        return orderStats;
    }

    public void setOrderStats(OrderStats orderStats) {
        this.orderStats = orderStats;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public boolean isDeliveryReceived() {
        return deliveryReceived;
    }

    public void setDeliveryReceived(boolean deliveryReceived) {
        this.deliveryReceived = deliveryReceived;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public int getStatusHomeDelivery() {
        return statusHomeDelivery;
    }

    public void setStatusHomeDelivery(int statusHomeDelivery) {
        this.statusHomeDelivery = statusHomeDelivery;
    }

    public int getStatusPickFromShop() {
        return statusPickFromShop;
    }

    public void setStatusPickFromShop(int statusPickFromShop) {
        this.statusPickFromShop = statusPickFromShop;
    }


    public int getDeliveryAddressID() {
        return deliveryAddressID;
    }

    public void setDeliveryAddressID(int deliveryAddressID) {
        this.deliveryAddressID = deliveryAddressID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }


    public int getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(int deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }


    public boolean getPickFromShop() {
        return pickFromShop;
    }

    public void setPickFromShop(boolean pickFromShop) {
        this.pickFromShop = pickFromShop;
    }


    public Timestamp getDateTimePlaced() {
        return dateTimePlaced;
    }

    public void setDateTimePlaced(Timestamp dateTimePlaced) {
        this.dateTimePlaced = dateTimePlaced;
    }

}
