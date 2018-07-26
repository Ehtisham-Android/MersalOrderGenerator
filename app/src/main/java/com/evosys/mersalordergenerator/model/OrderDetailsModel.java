package com.evosys.mersalordergenerator.model;

import android.graphics.Bitmap;

/**
 * Created by ehtisham on 4/4/16.
 */
public class OrderDetailsModel {

    private int ItemPrice = 0;
    private  String DeliveryPrice ="";
    private  String ProductName ="";
    private Bitmap ProductImage;
    private int timer = 0;
    private String CustomerOrderID = "";
    private String pProductDetails = "";
    private String pProductCategory = "";
    private double pOrderSourceLat = 0;
    private double pOrderSourceLong = 0;
    private double pOrderDestinationLat = 0;
    private double pOrderDestinationLong = 0;
    private String pOrderAddress = "";
    private String twoMinsTimer = "";
    private Boolean isFree = false;
    private int awardedValue = 0;
    private float NeedApprovalTimer = 0;
    private int pUserID = 0;
    private int pDriverUserID = 0;
    private boolean is2minsInProcess = false;
    private int pDistanceValue = 0;
    private String pPushedTime = "";
    private String pOrderStatus = "";
    private String pCustomerRating = "";


    /*********** Set Methods ******************/

    public void setItemPrice(int strItemPrice)
    {
        this.ItemPrice = strItemPrice;
    }

    public void setProductImage(Bitmap bmpImage)
    {
        this.ProductImage = bmpImage;
    }

    public void setDeliveryPrice(String strDeliveryPrice)
    {
        this.DeliveryPrice = strDeliveryPrice;
    }

    public void setProductName(String strProductName)
    {
        this.ProductName = strProductName;
    }

    public void setTwoMinsTimer(String strTwoMinsTimer)
    {
        this.twoMinsTimer = strTwoMinsTimer;
    }

    public void setIsFree(boolean strIsFree)
    {
        this.isFree = strIsFree;
    }

    public void setAwardedValue(int intAwardedValue)
    {
        this.awardedValue = intAwardedValue;
    }

    public void setNeedApprovalTimer(float floatNeedApprovalTimer)
    {
        this.NeedApprovalTimer = floatNeedApprovalTimer;
    }

    public void setTimer(int strTimer)
    {
        this.timer = strTimer;
    }

    public void setCustomerOrderID(String strCustomerOrderID)
    {
        this.CustomerOrderID = strCustomerOrderID;
    }

    public void setProductDetails(String strProductDetails)
    {
        this.pProductDetails = strProductDetails;
    }

    public void setProductCategory(String strProductCategory)
    {
        this.pProductCategory = strProductCategory;
    }

    public void setOrderSourceLat(double strOrderSourceLat)
    {
        this.pOrderSourceLat = strOrderSourceLat;
    }

    public void setOrderSourceLong(double strOrderSourceLong)
    {
        this.pOrderSourceLong = strOrderSourceLong;
    }

    public void setOrderDestinationLat(double strOrderDestinationLat)
    {
        this.pOrderDestinationLat = strOrderDestinationLat;
    }

    public void setOrderDestinationLong(double strOrderDestinationLong)
    {
        this.pOrderDestinationLong = strOrderDestinationLong;
    }

    public void setUserID(int strUserID)
    {
        this.pUserID = strUserID;
    }

    public void setDriverUserID(int strDriverUserID)
    {
        this.pDriverUserID = strDriverUserID;
    }

    public void setis2minsInProcess(boolean boolState)
    {
        this.is2minsInProcess = boolState;
    }

    public void setOrderAddress(String strOrderAddress)
    {
        this.pOrderAddress = strOrderAddress;
    }

    public void setDistanceValue(int strDistanceValue)
    {
        this.pDistanceValue = strDistanceValue;
    }

    public void setPushedTime(String strPushedTime)
    {
        this.pPushedTime = strPushedTime;
    }

    public void setOrderStatus(String strOrderStatus)
    {
        this.pOrderStatus = strOrderStatus;
    }

    public void setCustomerRating(String strCustomerRating)
    {
        this.pCustomerRating = strCustomerRating;
    }

    /*********** Get Methods ****************/

    public int getItemPrice()
    {
        return this.ItemPrice;
    }

    public Bitmap getProductImage()
    {
        return this.ProductImage;
    }

    public String getDeliveryPrice()
    {
        return this.DeliveryPrice;
    }

    public String getProductName()
    {
        return this.ProductName;
    }

    public int getTimer()
    {
        return this.timer;
    }

    public String getCustomerOrderID()
    {
        return this.CustomerOrderID;
    }

    // new methods //////////////////////////////////////////////////////////////////
    public String getProductDetails()
    {
        return this.pProductDetails;
    }

    public String getProductCategory()
    {
        return this.pProductCategory;
    }

    public double getOrderSourceLat()
    {
        return this.pOrderSourceLat;
    }

    public double getOrderSourceLong()
    {
        return this.pOrderSourceLong;
    }

    public double getOrderDestinationLat()
    {
        return this.pOrderDestinationLat;
    }

    public double getOrderDestinationLong()
    {
        return this.pOrderDestinationLong;
    }

    public int getUserID()
    {
        return this.pUserID;
    }

    public int getDriverUserID()
    {
        return this.pDriverUserID;
    }

    public boolean getis2minsInProcess()
    {
        return is2minsInProcess;
    }

    public String getTwoMinsTimer()
    {
        return this.twoMinsTimer;
    }

    public boolean getIsFree()
    {
        return this.isFree;
    }

    public int getAwardedValue()
    {
        return this.awardedValue;
    }

    public float getNeedApprovalTimer()
    {
        return this.NeedApprovalTimer;
    }

    public String getOrderAddress()
    {
        return this.pOrderAddress;
    }

    public int getDistanceValue()
    {
        return this.pDistanceValue;
    }

    public String getPushedTime()
    {
        return this.pPushedTime;
    }

    public String getOrderStatus()
    {
        return this.pOrderStatus;
    }

    public String getCustomerRating()
    {
        return this.pCustomerRating;
    }
    /////////////////////////////////////////////////////////////////////////////////
}
