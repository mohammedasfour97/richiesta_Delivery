package com.rkesta.richiestadelivery.api;

import java.util.ArrayList;
import java.util.HashMap;

public class WebService {
    public ArrayList<HashMap<String, String>> RK_DeliverLogin(String Phone , String Password , String fbid) {
        MasterSlayer MS = new MasterSlayer("RK_DeliverLogin");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("Phone");
        send_params.add("Password");
        send_params.add("fbid");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(Phone);
        send_params_value.add(Password);
        send_params_value.add(fbid);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> DeliveryApp_PendingOrders(String DeliveryId) {
        MasterSlayer MS = new MasterSlayer("DeliveryApp_PendingOrders");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("DeliveryId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(DeliveryId);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        SONumber:  elem["SONumber"].element?.text ?? ""
//        StoreId:  elem["StoreId"].element?.text ?? ""
//        StoreLogo:  elem["StoreLogo"].element?.text ?? ""
//        RK_Branch:  elem["RK_Branch"].element?.text ?? ""
//        BranchName:  elem["BranchName"].element?.text ?? ""
//        TotalPrice:  elem["TotalPrice"].element?.text ?? ""
//        RK_User:  elem["RK_User"].element?.text ?? ""
//        CustomerName:  elem["CustomerName"].element?.text ?? ""
//        UserAddress: elem["UserAddress"].element?.text ?? ""
//        UserCityNameAR: elem["UserCityNameAR"].element?.text ?? ""
//        UserCityNameEN: elem["UserCityNameEN"].element?.text ?? ""
        request_params.add("ID"); //order_model
        request_params.add("SONumber"); //order_model
        request_params.add("StoreId"); //order_model
        request_params.add("RK_DeliveryContact"); //order_model
        request_params.add("StoreName"); //order_model
        request_params.add("StoreLogo"); //order_model
        request_params.add("RK_Branch"); //order_model
        request_params.add("BranchName"); //order_model
        request_params.add("RK_Products");
        request_params.add("ProductName");
        request_params.add("ProductPicture");
        request_params.add("ProductColorID");
        request_params.add("ColorName");
        request_params.add("ColorPrice");
        request_params.add("ProductAdditionalID");
        request_params.add("AdditionalName");
        request_params.add("AdditionalPrice");
        request_params.add("ProductSizeID");
        request_params.add("SizeName");
        request_params.add("SizePrice");
        request_params.add("ProductPrice");
        request_params.add("Quantity");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("OrderDate");
        request_params.add("IsComplete");
        request_params.add("RK_User"); //order_model
        request_params.add("CustomerName"); //order_model
        request_params.add("UserAddress"); //order_model
        request_params.add("UserCityID");
        request_params.add("UserCityNameAR");
        request_params.add("UserCityNameEN");
        request_params.add("UserCityLongitude");
        request_params.add("UserCityLatitude");
        request_params.add("UserCountryID");
        request_params.add("UserCountryNameAR"); //order_model
        request_params.add("UserCountryNameEN"); //order_model
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public ArrayList<HashMap<String, String>> SelectPendingSODT(String DeliveryId) {
        MasterSlayer MS = new MasterSlayer("SelectPendingSODT");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("DeliveryId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(DeliveryId);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        SONumber:  elem["SONumber"].element?.text ?? ""
//        StoreId:  elem["StoreId"].element?.text ?? ""
//        StoreLogo:  elem["StoreLogo"].element?.text ?? ""
//        RK_Branch:  elem["RK_Branch"].element?.text ?? ""
//        BranchName:  elem["BranchName"].element?.text ?? ""
//        TotalPrice:  elem["TotalPrice"].element?.text ?? ""
//        RK_User:  elem["RK_User"].element?.text ?? ""
//        CustomerName:  elem["CustomerName"].element?.text ?? ""
//        UserAddress: elem["UserAddress"].element?.text ?? ""
//        UserCityNameAR: elem["UserCityNameAR"].element?.text ?? ""
//        UserCityNameEN: elem["UserCityNameEN"].element?.text ?? ""
        request_params.add("ID"); //order_model
        request_params.add("SONumber"); //order_model
        request_params.add("StoreId"); //order_model
        request_params.add("RK_DeliveryContact"); //order_model
        request_params.add("StoreName"); //order_model
        request_params.add("StoreLogo"); //order_model
        request_params.add("RK_Branch"); //order_model
        request_params.add("BranchName"); //order_model
        request_params.add("RK_Products");
        request_params.add("ProductName");
        request_params.add("ProductPicture");
        request_params.add("ProductColorID");
        request_params.add("ColorName");
        request_params.add("ColorPrice");
        request_params.add("ProductAdditionalID");
        request_params.add("AdditionalName");
        request_params.add("AdditionalPrice");
        request_params.add("ProductSizeID");
        request_params.add("SizeName");
        request_params.add("SizePrice");
        request_params.add("ProductPrice");
        request_params.add("Quantity");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("OrderDate");
        request_params.add("IsComplete");
        request_params.add("RK_User"); //order_model
        request_params.add("CustomerName"); //order_model
        request_params.add("UserAddress"); //order_model
        request_params.add("UserCityID");
        request_params.add("UserCityNameAR");
        request_params.add("UserCityNameEN");
        request_params.add("UserCityLongitude");
        request_params.add("UserCityLatitude");
        request_params.add("UserCountryID");
        request_params.add("UserCountryNameAR"); //order_model
        request_params.add("UserCountryNameEN"); //order_model
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public ArrayList<HashMap<String, String>> SelectPendingOrderDS(String DeliveryId) {
        MasterSlayer MS = new MasterSlayer("SelectPendingOrderDS");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("DeliveryId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(DeliveryId);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        SONumber:  elem["SONumber"].element?.text ?? ""
//        StoreId:  elem["StoreId"].element?.text ?? ""
//        StoreLogo:  elem["StoreLogo"].element?.text ?? ""
//        RK_Branch:  elem["RK_Branch"].element?.text ?? ""
//        BranchName:  elem["BranchName"].element?.text ?? ""
//        TotalPrice:  elem["TotalPrice"].element?.text ?? ""
//        RK_User:  elem["RK_User"].element?.text ?? ""
//        CustomerName:  elem["CustomerName"].element?.text ?? ""
//        UserAddress: elem["UserAddress"].element?.text ?? ""
//        UserCityNameAR: elem["UserCityNameAR"].element?.text ?? ""
//        UserCityNameEN: elem["UserCityNameEN"].element?.text ?? ""
        request_params.add("ID"); //order_model
        request_params.add("SONumber"); //order_model
        request_params.add("StoreId"); //order_model
//        request_params.add("RK_DeliveryContact"); //order_model
        request_params.add("DeliveryProgess_DeliveryContact"); //order_model
        request_params.add("PendingDeliv_DeliveryContact"); //order_model
        request_params.add("StoreName"); //order_model
        request_params.add("StoreLogo"); //order_model
        request_params.add("RK_Branch"); //order_model
        request_params.add("BranchName"); //order_model
        request_params.add("RK_Products");
        request_params.add("ProductName");
        request_params.add("ProductPicture");
        request_params.add("ProductColorID");
        request_params.add("ColorName");
        request_params.add("ColorPrice");
        request_params.add("ProductAdditionalID");
        request_params.add("AdditionalName");
        request_params.add("AdditionalPrice");
        request_params.add("ProductSizeID");
        request_params.add("SizeName");
        request_params.add("SizePrice");
        request_params.add("ProductPrice");
        request_params.add("Quantity");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("OrderDate");
        request_params.add("IsComplete");
        request_params.add("RK_User"); //order_model
        request_params.add("CustomerName"); //order_model
        request_params.add("UserAddress"); //order_model
        request_params.add("UserCityID");
        request_params.add("UserCityNameAR");
        request_params.add("UserCityNameEN");
        request_params.add("UserCityLongitude");
        request_params.add("UserCityLatitude");
        request_params.add("UserCountryID");
        request_params.add("UserCountryNameAR"); //order_model
        request_params.add("UserCountryNameEN"); //order_model
        request_params.add("ShippingRate");
        request_params.add("DiscountAmount");

        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public ArrayList<HashMap<String, String>> DeliveryApp_DeliveryAcceptOrder(String SONumber , String DeliveryContactID) {
        MasterSlayer MS = new MasterSlayer("DeliveryApp_DeliveryAcceptOrder");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        send_params.add("DeliveryContactID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        send_params_value.add(DeliveryContactID);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> RK_DeliveryAcceptance(String RK_DeliveryContact, String SONumber,String isAccepted, String isIgnored, String isRejected) {
        MasterSlayer MS = new MasterSlayer("RK_DeliveryAcceptance");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("RK_DeliveryContact");
        send_params.add("SONumber");
        send_params.add("isAccepted");
        send_params.add("isIgnored");
        send_params.add("isRejected");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(RK_DeliveryContact);
        send_params_value.add(SONumber);
        send_params_value.add(isAccepted);
        send_params_value.add(isIgnored);
        send_params_value.add(isRejected);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> DeliveryApp_TrackDelivery(String SONumber , String _UserID) {
        MasterSlayer MS = new MasterSlayer("DeliveryApp_TrackDelivery");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        send_params.add("_UserID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        send_params_value.add(_UserID);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();


        request_params.add("id");
        request_params.add("RK_SalesOrder");
        request_params.add("SONumber");
        request_params.add("UserID");
        request_params.add("UserNameAR");
        request_params.add("UserNameEN");
        request_params.add("UserBuildingNumber");
        request_params.add("UserAddress");
        request_params.add("UserFloorNum");
        request_params.add("UserAptNum");
        request_params.add("UserPhone");
        request_params.add("UserRegionID");
        request_params.add("UserRegionNameAR");
        request_params.add("UserRegionNameEN");
        request_params.add("UserCityID");
        request_params.add("UserCityNameAr");
        request_params.add("UserCityNameEN");
        request_params.add("UserCountryID");
        request_params.add("UserCountryNameAR");
        request_params.add("UserCountryNameEN");
        request_params.add("UserLatitude");
        request_params.add("UserLongitude");
        request_params.add("DeliveryContactID");
        request_params.add("DeliveryContactNameEN");
        request_params.add("DeliveryContactNameAR");
        request_params.add("BranchID");
        request_params.add("BranchNameAr");
        request_params.add("BranchNameEn");
        request_params.add("BranchLatitude");
        request_params.add("BranchLongitude");
        request_params.add("BranchPhone");
        request_params.add("BranchCell");
        request_params.add("IsRecieved");
        request_params.add("IsReadyforPickup");
        request_params.add("IsDeliveryAccepted");
        request_params.add("IsPickedByDelivery");
        request_params.add("IsCustomerDelivered");
        request_params.add("ItemPrice");
        request_params.add("PaidAmount");
        request_params.add("DeliveryProgressNotes");


        request_params.add("ID");
        request_params.add("StoreId");
        request_params.add("RKPrdNameAR");
        request_params.add("RKPrdNameEN");
        request_params.add("ProductPic");
        request_params.add("RK_Branch");
        request_params.add("RK_Products");
        request_params.add("ProductColor");
        request_params.add("ProductAdditionals");
        request_params.add("ProductSize");
        request_params.add("ColorUnitPrice");
        request_params.add("AdditionalUnitPrice");
        request_params.add("SizeUnitPrice");
        request_params.add("SalesUnitPrice");
        request_params.add("Qty");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("IsComplete");
        request_params.add("OrderDate");
        request_params.add("RK_User");
        request_params.add("RK_User_DetailID");
        request_params.add("Notes");
        request_params.add("ShippingRate");

        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        MS.setIsImage1("ProductPic");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public ArrayList<HashMap<String, String>> DeliveryApp_DeliveryPickDelivery(String SONumber , String BranchID) {
        MasterSlayer MS = new MasterSlayer("DeliveryApp_DeliveryPickDelivery");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        send_params.add("BranchID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        send_params_value.add(BranchID);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> DeliveryApp_DeliveryCustomerDelivery(String SONumber , String BranchID, String PaidAmount, String ReasonIfNotPaid) {
        MasterSlayer MS = new MasterSlayer("DeliveryApp_DeliveryCustomerDelivery");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        send_params.add("BranchID");
        send_params.add("PaidAmount");
        send_params.add("ReasonIfNotPaid");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        send_params_value.add(BranchID);
        send_params_value.add(PaidAmount);
        send_params_value.add(ReasonIfNotPaid);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> RK_DeliveryContactLocationUpdate(String DeliveryContact_ID , String Longitude, String Latitude) {
        MasterSlayer MS = new MasterSlayer("RK_DeliveryContactLocationUpdate");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("DeliveryContact_ID");
        send_params.add("Longitude");
        send_params.add("Latitude");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(DeliveryContact_ID);
        send_params_value.add(Longitude);
        send_params_value.add(Latitude);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> SelectDelAcceptance(String SONumber) {
        MasterSlayer MS = new MasterSlayer("SelectDelAcceptance");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

}
