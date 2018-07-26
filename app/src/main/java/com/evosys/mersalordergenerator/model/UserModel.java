package com.evosys.mersalordergenerator.model;

import android.graphics.Bitmap;

/**
 * Created by ehtisham on 4/4/16.
 */
public class UserModel {

    private String UserName;
    private String Passowrd;
    private double DestLocLat;
    private double DestLocLong;
    private String City;
    private int UserId;
    private String UserRating;



    /*********** Set Methods ******************/

    public void setUserName(String strUserName)
    {
        this.UserName = strUserName;
    }

    public void setPassowrd(String strPassowrd)
    {
        this.Passowrd = strPassowrd;
    }

    public void setDestLocLat(double strDestLocLat)
    {
        this.DestLocLat = strDestLocLat;
    }

    public void setDestLocLong(double strDestLocLong)
    {
        this.DestLocLong = strDestLocLong;
    }

    public void setCity(String strCity)
    {
        this.City = strCity;
    }

    public void setUserRating(String strUserRating)
    {
        this.UserRating = strUserRating;
    }

    public String getUserName()
    {
        return UserName;
    }

    public String getPassowrd()
    {
        return Passowrd;
    }

    public double getDestLocLat()
    {
        return DestLocLat;
    }

    public double getDestLocLong()
    {
        return DestLocLong;
    }

    public void setUserId(int strUserId)
    {
        this.UserId = strUserId;
    }

    public String getCity()
    {
        return City;
    }
    public int getUserId()
    {
        return UserId;
    }

    public String getUserRating()
    {
        return this.UserRating;
    }
    /////////////////////////////////////////////////////////////////////////////////
}
