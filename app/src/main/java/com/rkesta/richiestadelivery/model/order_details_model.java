package com.rkesta.richiestadelivery.model;

public class order_details_model {

    String ID = "" ;
    String SONumber = "" ;
    String StoreId = "" ;
    String StoreName = "" ;
    String StoreLogo = "" ;
    String RK_Branch = "" ;
    String BranchName = "" ;
    String RK_Products = "" ;
    String ProductName = "" ;
    String ProductPicture = "" ;
    String ProductColorID = "" ;
    String ColorName = "" ;
    String ColorPrice = "" ;
    String ProductAdditionalID = "" ;
    String AdditionalName = "" ;
    String AdditionalPrice = "" ;
    String ProductSizeID = "" ;
    String SizeName = "" ;
    String SizePrice = "" ;
    String ProductPrice = "" ;
    String Quantity = "" ;
    String TotalPrice = "" ;
    String OrderNotes = "" ;
    String OrderDate = "" ;
    String IsComplet= "" ;
    String RK_User = "" ;
    String CustomerName = "" ;


    public order_details_model(String ID, String SONumber, String storeId, String storeName, String storeLogo, String RK_Branch, String branchName, String RK_Products, String productName, String productPicture, String productColorID, String colorName, String colorPrice, String productAdditionalID, String additionalName, String additionalPrice, String productSizeID, String sizeName, String sizePrice, String productPrice, String quantity, String totalPrice, String orderNotes, String orderDate, String isComplet, String RK_User, String customerName) {
        this.ID = ID;
        this.SONumber = SONumber;
        StoreId = storeId;
        StoreName = storeName;
        StoreLogo = storeLogo;
        this.RK_Branch = RK_Branch;
        BranchName = branchName;
        this.RK_Products = RK_Products;
        ProductName = productName;
        ProductPicture = productPicture;
        ProductColorID = productColorID;
        ColorName = colorName;
        ColorPrice = colorPrice;
        ProductAdditionalID = productAdditionalID;
        AdditionalName = additionalName;
        AdditionalPrice = additionalPrice;
        ProductSizeID = productSizeID;
        SizeName = sizeName;
        SizePrice = sizePrice;
        ProductPrice = productPrice;
        Quantity = quantity;
        TotalPrice = totalPrice;
        OrderNotes = orderNotes;
        OrderDate = orderDate;
        IsComplet = isComplet;
        this.RK_User = RK_User;
        CustomerName = customerName;
    }
}
