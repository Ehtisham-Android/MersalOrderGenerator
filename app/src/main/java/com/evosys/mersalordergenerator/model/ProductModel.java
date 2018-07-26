package com.evosys.mersalordergenerator.model;

/**
 * Created by ehtisham on 4/4/16.
 */
public class ProductModel {

    private String Name;
    private String Description;
    private int price;
    private double SourceLocLat;
    private double SourceLocLong;
    private String city;



    /*********** Set Methods ******************/

    public void setName(String strName)
    {
        this.Name = strName;
    }

    public void setDescription(String strDescription)
    {
        this.Description = strDescription;
    }

    public void setprice(int strprice)
    {
        this.price = strprice;
    }

    public void setSourceLocLat(double strSourceLocLat)
    {
        this.SourceLocLat = strSourceLocLat;
    }

    public void setSourceLocLong(double strSourceLocLong)
    {
        this.SourceLocLong = strSourceLocLong;
    }

    public void setcity(String strcity)
    {
        this.city = strcity;
    }


    public String getName()
    {
        return Name;
    }
    public String getDescription()
    {
        return Description;
    }
    public int getprice()
    {
        return price;
    }
    public double getSourceLocLat()
    {
        return SourceLocLat;
    }
    public double getSourceLocLong()
    {
        return SourceLocLong;
    }
    public String getcity()
    {
        return city;
    }
    /////////////////////////////////////////////////////////////////////////////////
}
