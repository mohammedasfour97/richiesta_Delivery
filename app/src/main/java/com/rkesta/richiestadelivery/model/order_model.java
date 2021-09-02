package com.rkesta.richiestadelivery.model;

import java.util.ArrayList;

public class order_model {

    String ID;
    String SONumber;
    String StoreId = "" ;
    String RK_DeliveryContact = "" ;
    String StoreLogo = "" ;
    String RK_Branch = "" ;
    String BranchName = "" ;
    String TotalPrice = "" ;
    String DiscountAmount = "";
    String RK_User = "" ;
    String CustomerName = "" ;
    String UserAddress = "" ;
    String UserCityNameAR = "" ;
    String UserCityNameEN = "" ;
    String ShippingRate = "" ;
    ArrayList<order_details_model> OrderDetails_list = new ArrayList<order_details_model>() ;

    public void setShippingRate(String shippingRate) {
        ShippingRate = shippingRate;
    }

    public String getShippingRate() {
        return ShippingRate;
    }

    public order_model(String ID, String SONumber, String storeId, String RK_DeliveryContact, String storeLogo, String RK_Branch,
                       String branchName, String totalPrice, String discountAmount, String RK_User, String customerName, String userAddress,
                       String userCityNameAR, String userCityNameEN , String ShippingRate, order_details_model orderDetails) {
        this.ID = ID;
        this.SONumber = SONumber;
        this.StoreId = storeId;
        this.RK_DeliveryContact = RK_DeliveryContact;
        this.StoreLogo = storeLogo;
        this.RK_Branch = RK_Branch;
        this.BranchName = branchName;
        this.TotalPrice = totalPrice;
        this.DiscountAmount = discountAmount;
        this.RK_User = RK_User;
        this.CustomerName = customerName;
        this.UserAddress = userAddress;
        this.UserCityNameAR = userCityNameAR;
        this.UserCityNameEN = userCityNameEN;
        this.ShippingRate = ShippingRate;

        this.OrderDetails_list.add(orderDetails);
    }

    public void addmoreDetails(order_details_model orderDetails) {
        this.OrderDetails_list.add(orderDetails);
    }

    public String getRK_DeliveryContact() {
        return RK_DeliveryContact;
    }

    public void setRK_DeliveryContact(String RK_DeliveryContact) {
        this.RK_DeliveryContact = RK_DeliveryContact;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreLogo() {
        return StoreLogo;
    }

    public void setStoreLogo(String storeLogo) {
        StoreLogo = storeLogo;
    }

    public String getRK_Branch() {
        return RK_Branch;
    }

    public void setRK_Branch(String RK_Branch) {
        this.RK_Branch = RK_Branch;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getRK_User() {
        return RK_User;
    }

    public void setRK_User(String RK_User) {
        this.RK_User = RK_User;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserCityNameAR() {
        return UserCityNameAR;
    }

    public void setUserCityNameAR(String userCityNameAR) {
        UserCityNameAR = userCityNameAR;
    }

    public String getUserCityNameEN() {
        return UserCityNameEN;
    }

    public void setUserCityNameEN(String userCityNameEN) {
        UserCityNameEN = userCityNameEN;
    }

    public ArrayList<order_details_model> getOrderDetails_list() {
        return OrderDetails_list;
    }

    public void setOrderDetails_list(ArrayList<order_details_model> orderDetails_list) {
        OrderDetails_list = orderDetails_list;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSONumber() {
        return SONumber;
    }

    public void setSONumber(String SONumber) {
        this.SONumber = SONumber;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }
}
