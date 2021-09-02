package com.rkesta.richiestadelivery.model;

import java.util.ArrayList;

public class SONumber_model {

    String id = "" ;
    String RK_SalesOrder = "" ;
    String UserNameAR = "" ;
    String UserNameEN = "" ;
    String SONumber = "" ;
    String UserAddress = "" ;
    String UserBuildingNumber = "" ;
    String UserFloorNum = "" ;
    String UserAptNum = "" ;
    String UserPhone = "" ;
    String BranchID = "" ;
    String BranchNameAr = "" ;
    String BranchNameEn = "" ;
    String BranchLatitude = "" ;
    String BranchLongitude = "" ;
    String UserLatitude = "" ;
    String UserLongitude = "" ;
    String BranchPhone = "" ;
    String BranchCell = "" ;
    String DeliveryProgressNotes = "" ;
    String ItemPrice = "" ;
    String SalesOrderDate = "" ;
    String IsComplete = "" ;
    boolean IsPickedByDelivery = false ;
    String PickedProblem  = "" ;
    ArrayList<SONumber_Details_model> SONumber_Details_model = new ArrayList<SONumber_Details_model>() ;
    boolean isExpanded = false ;

    public void addmoreDetails(SONumber_Details_model SONumberDetails) {
        this.SONumber_Details_model.add(SONumberDetails);
    }

    public SONumber_model(String id, String RK_SalesOrder, String userNameAR, String userNameEN, String SONumber, String userAddress, String userBuildingNumber, String userFloorNum, String userAptNum, String userPhone, String userLatitude, String userLongitude, String branchID, String branchNameAr, String branchNameEn, String branchLatitude, String branchLongitude, String branchPhone, String branchCell, String deliveryProgressNotes, String itemPrice, String salesOrderDate, String isComplete, Boolean isPickedByDelivery, String pickedProblem) {
        this.id = id;
        this.RK_SalesOrder = RK_SalesOrder;
        this.UserNameAR = userNameAR;
        this.UserNameEN = userNameEN;
        this.SONumber = SONumber;
        this.UserAddress = userAddress;
        this.UserBuildingNumber = userBuildingNumber;
        this.UserFloorNum = userFloorNum;
        this.UserAptNum = userAptNum;
        this.UserPhone = userPhone;
        this.UserLatitude =  userLatitude;
        this.UserLongitude = userLongitude;
        this.BranchID = branchID;
        this.BranchNameAr = branchNameAr;
        this.BranchNameEn = branchNameEn;
        this.BranchLatitude = branchLatitude;
        this.BranchLongitude = branchLongitude;
        this.BranchPhone = branchPhone;
        this.BranchCell = branchCell;
        this.DeliveryProgressNotes = deliveryProgressNotes;
        this.ItemPrice = itemPrice;
        this.SalesOrderDate = salesOrderDate;
        this.IsComplete = isComplete;
        this.IsPickedByDelivery = isPickedByDelivery;
        this.PickedProblem = pickedProblem;
        this.isExpanded = !isPickedByDelivery;
    }

    public String getUserLatitude() {
        return UserLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        UserLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return UserLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        UserLongitude = userLongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRK_SalesOrder() {
        return RK_SalesOrder;
    }

    public void setRK_SalesOrder(String RK_SalesOrder) {
        this.RK_SalesOrder = RK_SalesOrder;
    }

    public String getUserNameAR() {
        return UserNameAR;
    }

    public String getUserNameAR_EN() {
        return UserNameEN+" | "+UserNameAR;
    }

    public void setUserNameAR(String userNameAR) {
        UserNameAR = userNameAR;
    }

    public String getUserNameEN() {
        return UserNameEN;
    }

    public void setUserNameEN(String userNameEN) {
        UserNameEN = userNameEN;
    }

    public String getSONumber() {
        return SONumber;
    }

    public void setSONumber(String SONumber) {
        this.SONumber = SONumber;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserBuildingNumber() {
        return UserBuildingNumber;
    }

    public void setUserBuildingNumber(String userBuildingNumber) {
        UserBuildingNumber = userBuildingNumber;
    }

    public String getUserFloorNum() {
        return UserFloorNum;
    }

    public void setUserFloorNum(String userFloorNum) {
        UserFloorNum = userFloorNum;
    }

    public String getUserAptNum() {
        return UserAptNum;
    }

    public void setUserAptNum(String userAptNum) {
        UserAptNum = userAptNum;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getBranchID() {
        return BranchID;
    }

    public void setBranchID(String branchID) {
        BranchID = branchID;
    }

    public String getBranchNameAr() {
        return BranchNameAr;
    }

    public void setBranchNameAr(String branchNameAr) {
        BranchNameAr = branchNameAr;
    }

    public String getBranchNameEn() {
        return BranchNameEn;
    }

    public void setBranchNameEn(String branchNameEn) {
        BranchNameEn = branchNameEn;
    }

    public String getBranchLatitude() {
        return BranchLatitude;
    }

    public void setBranchLatitude(String branchLatitude) {
        BranchLatitude = branchLatitude;
    }

    public String getBranchLongitude() {
        return BranchLongitude;
    }

    public void setBranchLongitude(String branchLongitude) {
        BranchLongitude = branchLongitude;
    }

    public String getBranchPhone() {
        return BranchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        BranchPhone = branchPhone;
    }

    public String getBranchCell() {
        return BranchCell;
    }

    public void setBranchCell(String branchCell) {
        BranchCell = branchCell;
    }

    public String getDeliveryProgressNotes() {
        return DeliveryProgressNotes;
    }

    public void setDeliveryProgressNotes(String deliveryProgressNotes) {
        DeliveryProgressNotes = deliveryProgressNotes;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getSalesOrderDate() {
        return SalesOrderDate;
    }

    public void setSalesOrderDate(String salesOrderDate) {
        SalesOrderDate = salesOrderDate;
    }

    public String getIsComplete() {
        return IsComplete;
    }

    public void setIsComplete(String isComplete) {
        IsComplete = isComplete;
    }

    public boolean isPickedByDelivery() {
        return IsPickedByDelivery;
    }

    public void setPickedByDelivery(boolean pickedByDelivery) {
        IsPickedByDelivery = pickedByDelivery;
    }

    public String getPickedProblem() {
        return PickedProblem;
    }

    public void setPickedProblem(String pickedProblem) {
        PickedProblem = pickedProblem;
    }

    public ArrayList<com.rkesta.richiestadelivery.model.SONumber_Details_model> getSONumber_Details_model() {
        return SONumber_Details_model;
    }

    public void setSONumber_Details_model(ArrayList<com.rkesta.richiestadelivery.model.SONumber_Details_model> SONumber_Details_model) {
        this.SONumber_Details_model = SONumber_Details_model;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
