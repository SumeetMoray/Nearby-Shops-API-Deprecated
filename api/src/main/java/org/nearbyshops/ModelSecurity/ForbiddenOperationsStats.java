package org.nearbyshops.ModelSecurity;

/**
 * Created by sumeet on 3/12/16.
 */
public class ForbiddenOperationsStats {

    private int operationsCount;

    private Integer adminID;
    private Integer staffID;
    private Integer shopAdminID;
    private Integer shopStaffID;
    private Integer deliveryGuySelfID;
    private Integer endUserID;

    public int getOperationsCount() {
        return operationsCount;
    }

    public void setOperationsCount(int operationsCount) {
        this.operationsCount = operationsCount;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Integer getShopAdminID() {
        return shopAdminID;
    }

    public void setShopAdminID(Integer shopAdminID) {
        this.shopAdminID = shopAdminID;
    }

    public Integer getShopStaffID() {
        return shopStaffID;
    }

    public void setShopStaffID(Integer shopStaffID) {
        this.shopStaffID = shopStaffID;
    }

    public Integer getDeliveryGuySelfID() {
        return deliveryGuySelfID;
    }

    public void setDeliveryGuySelfID(Integer deliveryGuySelfID) {
        this.deliveryGuySelfID = deliveryGuySelfID;
    }

    public Integer getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(Integer endUserID) {
        this.endUserID = endUserID;
    }
}
