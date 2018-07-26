package com.evosys.mersalordergenerator.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.evosys.mersalordergenerator.model.DBTables;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.customobjects.QBCustomObjectsFiles;
import com.quickblox.customobjects.model.QBCustomObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DBAdapter {
    private static final int DATABASE_VERSION = 2; // changed for rewarded money
    private static final String DATABASE_NAME = "muwassilati.db";
    private static DBHelper objDBHelper ;
    private Context con;
    private SQLiteDatabase db;

    public DBAdapter(Context Context)
    {
        con = Context;
        if(objDBHelper == null)
            objDBHelper = new DBHelper(Context);
    }

    private static class  DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

            // Customers table
            final String SQL_CREATE_CUSTOMER_TABLE = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_CUSTOMERS + "("
                    + DBTables.allTables.CUSTOMER_USER_ID + " INTEGER PRIMARY KEY,"
                    + DBTables.allTables.CUSTOMER_CUSTOM_OBJECT_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.CUSTOMER_IS_VALIDATED + " TEXT NOT NULL, "
                    + DBTables.allTables.CUSTOMER_RATE_COUNT + " TEXT NOT NULL, "
                    + DBTables.allTables.CUSTOMER_RATING + " TEXT NOT NULL, "
                    + DBTables.allTables.CUSTOMER_BLOB_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.CUSTOMER_IMAGE + " BLOB, "
                    + DBTables.allTables.CUSTOMER_NAME + " TEXT NULL, "
                    + DBTables.allTables.CUSTOMER_EMAIL + " TEXT NULL, "
                    + DBTables.allTables.CUSTOMER_MOBILE_NO + " TEXT NULL, "
                    + DBTables.allTables.CUSTOMER_FREE_ORDERS_COUNT + " TEXT NULL, "
                    + DBTables.allTables.CUSTOMER_PLATFORM + " TEXT);";

            db.execSQL(SQL_CREATE_CUSTOMER_TABLE);


            // Drivers table
            final String SQL_CREATE_DRIVER_TABLE = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_DRIVERS + "("
                    + DBTables.allTables.DRIVER_USER_ID + " INTEGER PRIMARY KEY,"
                    + DBTables.allTables.DRIVER_CUSTOM_OBJECT_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_PLATE_NO + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_ID_NUMBER + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_MOBILE_NO + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_IS_ACCOUNT_VERIFIED + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_IS_PHONE_VERIFIED + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_RATING + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_RATE_COUNT + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_IS_ONLINE + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_BLOB_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_PLATFORM + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_EMAIL + " TEXT NOT NULL, "
                    + DBTables.allTables.DRIVER_IMAGE + " BLOB, "
                    + DBTables.allTables.DRIVER_CARPIC + " BLOB, "
                    + DBTables.allTables.DRIVER_IDPIC + " BLOB, "
                    + DBTables.allTables.DRIVER_LICENSE + " BLOB, "
                    + DBTables.allTables.DRIVER_LATUTUDE + " TEXT NULL, "
                    + DBTables.allTables.DRIVER_LONGITUDE + " TEXT NULL, "
                    + DBTables.allTables.DRIVER_IBAN + " TEXT NULL, "
                    + DBTables.allTables.DRIVER_NAME + " TEXT NOT NULL);";

            db.execSQL(SQL_CREATE_DRIVER_TABLE);


            // order table
            final String SQL_CREATE_ORDER_TABLE = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_ORDERS + "("
                    + DBTables.allTables.ORDER_USER_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_CUSTOM_OBJ_ID + " TEXT PRIMARY KEY, "
                    + DBTables.allTables.ORDER_DELIVERY_PRICE + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_NAME + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_DETAILS + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_ITEM_PRICE + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_IMAGE + " BLOB NULL, "
                    + DBTables.allTables.ORDER_CATEGORY + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_STATUS + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_DRIVER_USER_ID + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_DRIVER_RATING + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_UPDATED + " TEXT, "
                    + DBTables.allTables.ORDER_DEST_LAT + " TEXT, "
                    + DBTables.allTables.ORDER_DEST_LONG + " TEXT, "
                    + DBTables.allTables.ORDER_SOURCE_LAT + " TEXT, "
                    + DBTables.allTables.ORDER_SOURCE_LONG + " TEXT, "
                    + DBTables.allTables.ORDER_PUSHED_TIME + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_TIMER + " TEXT NOT NULL, "
                    + DBTables.allTables.ORDER_DISTANCE_RANGE + " TEXT, "
                    + DBTables.allTables.ORDER_CUSTOMER_TOTAL_RATING + " TEXT, "
                    + DBTables.allTables.ORDER_APPROVAL_TIME + " TEXT, "
                    + DBTables.allTables.ORDER_IS_FREE + " TEXT, "
                    + DBTables.allTables.ORDER_AWARDED_AMOUNT + " TEXT, "
                    + DBTables.allTables.ORDER_CUSTOMER_RATING + " TEXT NOT NULL);";

            db.execSQL(SQL_CREATE_ORDER_TABLE);


            // Blocked Users table
            final String SQL_CREATE_BLOCKEDUSERS_TABLE = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS + "("
                    + DBTables.allTables.BLOCKEDUSERS_CUSTOM_OBJECT_ID + " TEXT PRIMARY KEY, "
                    + DBTables.allTables.BLOCKEDUSERS_USER_ID + " TEXT NOT NULL,"
                    + DBTables.allTables.BLOCKEDUSERS_REMARKS + " TEXT NOT NULL,"
                    + DBTables.allTables.BLOCKEDUSERS_BLOCKEDID + " TEXT NOT NULL);";

            db.execSQL(SQL_CREATE_BLOCKEDUSERS_TABLE);


            // Wallet table
            final String TABLE_NAME_WALLET = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_WALLET + "("
                    + DBTables.allTables.WALLET_OBJECT_ID + " TEXT PRIMARY KEY, "
                    + DBTables.allTables.WALLET_DRIVER_ID + " TEXT NULL,"
                    + DBTables.allTables.WALLET_ORDERS_COUNT + " TEXT NULL,"
                    + DBTables.allTables.WALLET_AMOUNT + " TEXT NULL,"
                    + DBTables.allTables.WALLET_REDEEM_STATUS + " TEXT NULL,"
                    + DBTables.allTables.WALLET_REDEEM_DATE + " TEXT NULL,"
                    + DBTables.allTables.WALLET_CREATED_AT + " TEXT NULL);";

            db.execSQL(TABLE_NAME_WALLET);


            // IBAN info table
            final String TABLE_NAME_IBAN_INFO = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_IBAN_INFO + "("
                    + DBTables.allTables.IBAN_INFO_OBJECT_ID + " TEXT PRIMARY KEY, "
                    + DBTables.allTables.IBAN_INFO_NAME + " TEXT NULL,"
                    + DBTables.allTables.IBAN_INFO_BANK_NAME + " TEXT NULL,"
                    + DBTables.allTables.IBAN_INFO_BRANCH_NAME + " TEXT NULL,"
                    + DBTables.allTables.IBAN_INFO_IBAN_NUMBER + " TEXT NULL);";

            db.execSQL(TABLE_NAME_IBAN_INFO);

            // CONTACTS info table
            final String TABLE_NAME_CONTACTS = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_CONTACTS + "("
                    + DBTables.allTables.CONTACTS_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DBTables.allTables.CONTACTS_OBJECT_ID + " TEXT NULL, "
                    + DBTables.allTables.CONTACTS_USER_ID + " TEXT NULL,"
                    + DBTables.allTables.CONTACTS_NUMBER + " TEXT NULL);";

            db.execSQL(TABLE_NAME_CONTACTS);

            // NEW TABLES //////////////////////////////////////////////////////////////////////////
            final String TABLE_NAME_SOURCE_LOC = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_SOURCE_LOC + "("
                    + DBTables.allTables.SOURCE_LOC_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DBTables.allTables.SOURCE_LOC_SOURCE_LAT + " TEXT NULL, "
                    + DBTables.allTables.SOURCE_LOC_SOURCE_LONG + " TEXT NULL);";

            db.execSQL(TABLE_NAME_SOURCE_LOC);


            final String TABLE_NAME_DEST_LOC = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_DEST_LOC + "("
                    + DBTables.allTables.DEST_LOC_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DBTables.allTables.DEST_LOC_SOURCE_LAT + " TEXT NULL, "
                    + DBTables.allTables.DEST_LOC_SOURCE_LONG + " TEXT NULL);";

            db.execSQL(TABLE_NAME_DEST_LOC);


            final String TABLE_NAME_PRODUCTS = "CREATE TABLE "
                    + DBTables.allTables.TABLE_NAME_PRODUCTS + "("
                    + DBTables.allTables.PRODUCTS_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DBTables.allTables.PRODUCTS_NAME + " TEXT NULL, "
                    + DBTables.allTables.PRODUCTS_CATEGORY + " TEXT NULL, "
                    + DBTables.allTables.PRODUCTS_PRICE + " TEXT NULL);";

            db.execSQL(TABLE_NAME_PRODUCTS);

            ////////////////////////////////////////////////////////////////////////////////////////

            Log.e("Database", "Created");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_CUSTOMERS);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_DRIVERS);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_WALLET);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_IBAN_INFO);
            db.execSQL("DROP TABLE IF EXISTS " + DBTables.allTables.TABLE_NAME_CONTACTS);
            onCreate(db);
        }
    }

    public DBAdapter open()
    {
        db = objDBHelper.getWritableDatabase();
        return this;
    }


    public void close ()
    {
        objDBHelper.close();
    }

    // INSERT DATA METHODS STARTS ////////////////////////////////////////////////////////////////////////////////////////////////////////
    synchronized public void InsertOrder(
            String ORDER_USER_ID,
            String ORDER_CUSTOM_OBJ_ID ,
            String ORDER_DELIVERY_PRICE,
            String ORDER_NAME,
            String ORDER_DETAILS,
            String ORDER_ITEM_PRICE,
            String ORDER_CATEGORY,
            String ORDER_STATUS,
            String ORDER_DRIVER_USER_ID,
            String ORDER_DRIVER_RATING,
            String ORDER_CUSTOMER_RATING,
            String ORDER_DEST_LAT,
            String ORDER_DEST_LONG,
            String ORDER_SOURCE_LAT,
            String ORDER_SOURCE_LONG,
            String ORDER_PUSHED_TIME,
            String ORDER_TIMER,
            String ORDER_DISTANCE_RANGE,
            String ORDER_CUSTOMER_TOTAL_RATING,
            String ORDER_IS_FREE,
            String ORDER_AWARDED_AMOUNT
            //String ORDER_APPROVAL_TIME
            )
    {
        ContentValues CV = new ContentValues();

        CV.put(DBTables.allTables.ORDER_USER_ID, ORDER_USER_ID);
        CV.put(DBTables.allTables.ORDER_CUSTOM_OBJ_ID, ORDER_CUSTOM_OBJ_ID);
        CV.put(DBTables.allTables.ORDER_DELIVERY_PRICE, ORDER_DELIVERY_PRICE);
        CV.put(DBTables.allTables.ORDER_NAME, ORDER_NAME);
        CV.put(DBTables.allTables.ORDER_DETAILS, ORDER_DETAILS);
        CV.put(DBTables.allTables.ORDER_ITEM_PRICE, ORDER_ITEM_PRICE);
        CV.put(DBTables.allTables.ORDER_CATEGORY, ORDER_CATEGORY);
        CV.put(DBTables.allTables.ORDER_STATUS, ORDER_STATUS);
        CV.put(DBTables.allTables.ORDER_DRIVER_USER_ID, ORDER_DRIVER_USER_ID);
        CV.put(DBTables.allTables.ORDER_DRIVER_RATING, ORDER_DRIVER_RATING);
        CV.put(DBTables.allTables.ORDER_CUSTOMER_RATING, ORDER_CUSTOMER_RATING);
        CV.put(DBTables.allTables.ORDER_UPDATED, "updated");
        CV.put(DBTables.allTables.ORDER_DEST_LAT, ORDER_DEST_LAT);
        CV.put(DBTables.allTables.ORDER_DEST_LONG, ORDER_DEST_LONG);
        CV.put(DBTables.allTables.ORDER_SOURCE_LAT, ORDER_SOURCE_LAT);
        CV.put(DBTables.allTables.ORDER_SOURCE_LONG, ORDER_SOURCE_LONG);
        CV.put(DBTables.allTables.ORDER_PUSHED_TIME, ORDER_PUSHED_TIME);
        CV.put(DBTables.allTables.ORDER_TIMER, ORDER_TIMER);
        CV.put(DBTables.allTables.ORDER_DISTANCE_RANGE, ORDER_DISTANCE_RANGE);
        CV.put(DBTables.allTables.ORDER_CUSTOMER_TOTAL_RATING, ORDER_CUSTOMER_TOTAL_RATING);
        CV.put(DBTables.allTables.ORDER_IS_FREE, ORDER_IS_FREE);
        CV.put(DBTables.allTables.ORDER_AWARDED_AMOUNT, ORDER_AWARDED_AMOUNT);
        //CV.put(DBTables.allTables.ORDER_APPROVAL_TIME, ORDER_APPROVAL_TIME);


        long result =  db.insert(DBTables.allTables.TABLE_NAME_ORDERS, null, CV);

        if(result == -1)
        {
            Log.e("DatabaseINS", " ORDER insertion error !");
        }
        else
        {
            Log.e("DatabaseINS", " ORDER record inserted !");
            GetOrderPicServer(ORDER_CUSTOM_OBJ_ID);
        }
    }


    synchronized public void InsertNeedApprovalOrder(
            String ORDER_USER_ID,
            String ORDER_CUSTOM_OBJ_ID ,
            String ORDER_DELIVERY_PRICE,
            String ORDER_NAME,
            String ORDER_DETAILS,
            String ORDER_ITEM_PRICE,
            byte[] ORDER_IMAGE,
            String ORDER_CATEGORY,
            String ORDER_STATUS,
            String ORDER_DRIVER_USER_ID,
            String ORDER_DRIVER_RATING,
            String ORDER_CUSTOMER_RATING,
            String ORDER_PUSHED_TIME,
            String ORDER_TIMER,
            String ORDER_CUSTOMER_TOTAL_RATING,
            String ORDER_DEST_LAT,
            String ORDER_DEST_LONG,
            String ORDER_IS_FREE,
            String ORDER_SOURCE_LAT,
            String ORDER_SOURCE_LONG,
            String ORDER_AWARDED_AMOUNT)
    {
        ContentValues CV = new ContentValues();

        CV.put(DBTables.allTables.ORDER_USER_ID, ORDER_USER_ID);
        CV.put(DBTables.allTables.ORDER_CUSTOM_OBJ_ID, ORDER_CUSTOM_OBJ_ID);
        CV.put(DBTables.allTables.ORDER_DELIVERY_PRICE, ORDER_DELIVERY_PRICE);
        CV.put(DBTables.allTables.ORDER_NAME, ORDER_NAME);
        CV.put(DBTables.allTables.ORDER_DETAILS, ORDER_DETAILS);
        CV.put(DBTables.allTables.ORDER_ITEM_PRICE, ORDER_ITEM_PRICE);
        CV.put(DBTables.allTables.ORDER_IMAGE, ORDER_IMAGE);
        CV.put(DBTables.allTables.ORDER_CATEGORY, ORDER_CATEGORY);
        CV.put(DBTables.allTables.ORDER_STATUS, ORDER_STATUS);
        CV.put(DBTables.allTables.ORDER_DRIVER_USER_ID, ORDER_DRIVER_USER_ID);
        CV.put(DBTables.allTables.ORDER_DRIVER_RATING, ORDER_DRIVER_RATING);
        CV.put(DBTables.allTables.ORDER_CUSTOMER_RATING, ORDER_CUSTOMER_RATING);
        CV.put(DBTables.allTables.ORDER_PUSHED_TIME, ORDER_PUSHED_TIME);
        CV.put(DBTables.allTables.ORDER_TIMER, ORDER_TIMER);
        CV.put(DBTables.allTables.ORDER_CUSTOMER_TOTAL_RATING, ORDER_CUSTOMER_TOTAL_RATING);
        CV.put(DBTables.allTables.ORDER_DEST_LAT, ORDER_DEST_LAT);
        CV.put(DBTables.allTables.ORDER_DEST_LONG, ORDER_DEST_LONG);
        CV.put(DBTables.allTables.ORDER_IS_FREE, ORDER_IS_FREE);
        CV.put(DBTables.allTables.ORDER_SOURCE_LAT, ORDER_SOURCE_LAT);
        CV.put(DBTables.allTables.ORDER_SOURCE_LONG, ORDER_SOURCE_LONG);
        CV.put(DBTables.allTables.ORDER_AWARDED_AMOUNT, ORDER_AWARDED_AMOUNT);


        long result =  db.insert(DBTables.allTables.TABLE_NAME_ORDERS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " ORDER insertion error !");
        }
        else
        {
            Log.e("Database", " ORDER record inserted !");
        }
    }

    synchronized public void InsertDriver(
            int DRIVER_USER_ID,
            String DRIVER_CUSTOM_OBJECT_ID,
            String DRIVER_PLATE_NO,
            String DRIVER_ID_NUMBER,
            String DRIVER_MOBILE_NO,
            String DRIVER_IS_ACCOUNT_VERIFIED,
            String DRIVER_IS_PHONE_VERIFIED,
            String DRIVER_RATING,
            String DRIVER_RATE_COUNT,
            String DRIVER_IS_ONLINE,
            String DRIVER_BLOB_ID,
            String DRIVER_PLATFORM,
            String DRIVER_EMAIL,
            //byte[] DRIVER_IMAGE,
            String DRIVER_NAME,
            String DRIVER_LATUTUDE,
            String DRIVER_LONGITUDE,
            String DRIVER_IBAN
    )
    {
        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.DRIVER_USER_ID,DRIVER_USER_ID);
        CV.put(DBTables.allTables.DRIVER_CUSTOM_OBJECT_ID,DRIVER_CUSTOM_OBJECT_ID);
        CV.put(DBTables.allTables.DRIVER_PLATE_NO, DRIVER_PLATE_NO);
        CV.put(DBTables.allTables.DRIVER_ID_NUMBER, DRIVER_ID_NUMBER);
        CV.put(DBTables.allTables.DRIVER_MOBILE_NO, DRIVER_MOBILE_NO);
        CV.put(DBTables.allTables.DRIVER_IS_ACCOUNT_VERIFIED, DRIVER_IS_ACCOUNT_VERIFIED);
        CV.put(DBTables.allTables.DRIVER_IS_PHONE_VERIFIED, DRIVER_IS_PHONE_VERIFIED);
        CV.put(DBTables.allTables.DRIVER_RATING, DRIVER_RATING);
        CV.put(DBTables.allTables.DRIVER_RATE_COUNT, DRIVER_RATE_COUNT);
        CV.put(DBTables.allTables.DRIVER_IS_ONLINE, DRIVER_IS_ONLINE);
        CV.put(DBTables.allTables.DRIVER_BLOB_ID, DRIVER_BLOB_ID);
        CV.put(DBTables.allTables.DRIVER_PLATFORM, DRIVER_PLATFORM);
        CV.put(DBTables.allTables.DRIVER_EMAIL, DRIVER_EMAIL);
        //CV.put(DBTables.allTables.DRIVER_IMAGE, DRIVER_IMAGE);
        CV.put(DBTables.allTables.DRIVER_NAME, DRIVER_NAME);
        CV.put(DBTables.allTables.DRIVER_LATUTUDE, DRIVER_LATUTUDE);
        CV.put(DBTables.allTables.DRIVER_LONGITUDE, DRIVER_LONGITUDE);
        CV.put(DBTables.allTables.DRIVER_IBAN, DRIVER_IBAN);


        long result =  db.insert(DBTables.allTables.TABLE_NAME_DRIVERS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " DRIVER insertion error !");
        }
        else
        {
            Log.e("Database", " DRIVER record inserted !");
        }
    }

    synchronized public void InsertCustomer(
            int CUSTOMER_USER_ID,
            String CUSTOMER_CUSTOM_OBJECT_ID,
            String CUSTOMER_IS_VALIDATED ,
            String CUSTOMER_RATE_COUNT,
            String CUSTOMER_RATING,
            String CUSTOMER_PLATFORM,
            String CUSTOMER_BLOB_ID,
            String CUSTOMER_NAME,
            String CUSTOMER_EMAIL,
            String CUSTOMER_FREE_ORDERS_COUNT,
            String CUSTOMER_MOBILE_NO
    )
    {
        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.CUSTOMER_USER_ID, CUSTOMER_USER_ID);
        CV.put(DBTables.allTables.CUSTOMER_CUSTOM_OBJECT_ID, CUSTOMER_CUSTOM_OBJECT_ID);
        CV.put(DBTables.allTables.CUSTOMER_IS_VALIDATED, CUSTOMER_IS_VALIDATED);
        CV.put(DBTables.allTables.CUSTOMER_RATE_COUNT, CUSTOMER_RATE_COUNT);
        CV.put(DBTables.allTables.CUSTOMER_RATING, CUSTOMER_RATING);
        CV.put(DBTables.allTables.CUSTOMER_PLATFORM, CUSTOMER_PLATFORM);
        CV.put(DBTables.allTables.CUSTOMER_BLOB_ID, CUSTOMER_BLOB_ID);
        CV.put(DBTables.allTables.CUSTOMER_NAME, CUSTOMER_NAME);
        CV.put(DBTables.allTables.CUSTOMER_EMAIL, CUSTOMER_EMAIL);
        CV.put(DBTables.allTables.CUSTOMER_FREE_ORDERS_COUNT, CUSTOMER_FREE_ORDERS_COUNT);
        CV.put(DBTables.allTables.CUSTOMER_MOBILE_NO, CUSTOMER_MOBILE_NO);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_CUSTOMERS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " CUSTOMER insertion error !");
        }
        else
        {
            Log.e("Database", " CUSTOMER inserted !");
        }
    }

    synchronized public void InsertBlockedUsers(
            int BLOCKEDUSERS_USER_ID,
            String BLOCKEDUSERS_CUSTOM_OBJECT_ID,
            String BLOCKEDUSERS_BLOCKEDID,
            String BLOCKEDUSERS_REMARKS)
    {
        Log.d("Database", "value = " + BLOCKEDUSERS_REMARKS);

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.BLOCKEDUSERS_USER_ID, BLOCKEDUSERS_USER_ID);
        CV.put(DBTables.allTables.BLOCKEDUSERS_CUSTOM_OBJECT_ID, BLOCKEDUSERS_CUSTOM_OBJECT_ID);
        CV.put(DBTables.allTables.BLOCKEDUSERS_BLOCKEDID, BLOCKEDUSERS_BLOCKEDID);
        CV.put(DBTables.allTables.BLOCKEDUSERS_REMARKS, BLOCKEDUSERS_REMARKS);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_BLOCKEDUSERS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " BLOCKEDUSERS insertion error !");
        }
        else
        {
            Log.e("Database", " BLOCKEDUSERS inserted !");
        }
    }

    synchronized public void InsertWallet(
            String WALLET_OBJECT_ID,
            String WALLET_DRIVER_ID,
            String WALLET_ORDERS_COUNT,
            String WALLET_AMOUNT,
            String WALLET_REDEEM_STATUS,
            String WALLET_REDEEM_DATE,
            String WALLET_CREATED_AT)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.WALLET_OBJECT_ID, WALLET_OBJECT_ID);
        CV.put(DBTables.allTables.WALLET_DRIVER_ID, WALLET_DRIVER_ID);
        CV.put(DBTables.allTables.WALLET_ORDERS_COUNT, WALLET_ORDERS_COUNT);
        CV.put(DBTables.allTables.WALLET_AMOUNT, WALLET_AMOUNT);
        CV.put(DBTables.allTables.WALLET_REDEEM_STATUS, WALLET_REDEEM_STATUS);
        CV.put(DBTables.allTables.WALLET_REDEEM_DATE, WALLET_REDEEM_DATE);
        CV.put(DBTables.allTables.WALLET_CREATED_AT, WALLET_CREATED_AT);


        long result =  db.insert(DBTables.allTables.TABLE_NAME_WALLET, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_WALLET insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_WALLET inserted !");
        }
    }


    synchronized public void InsertIbanInfo(
            String IBAN_INFO_OBJECT_ID,
            String IBAN_INFO_NAME,
            String IBAN_INFO_BANK_NAME,
            String IBAN_INFO_BRANCH_NAME,
            String IBAN_INFO_IBAN_NUMBER)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.IBAN_INFO_OBJECT_ID, IBAN_INFO_OBJECT_ID);
        CV.put(DBTables.allTables.IBAN_INFO_NAME, IBAN_INFO_NAME);
        CV.put(DBTables.allTables.IBAN_INFO_BANK_NAME, IBAN_INFO_BANK_NAME);
        CV.put(DBTables.allTables.IBAN_INFO_BRANCH_NAME, IBAN_INFO_BRANCH_NAME);
        CV.put(DBTables.allTables.IBAN_INFO_IBAN_NUMBER, IBAN_INFO_IBAN_NUMBER);


        long result =  db.insert(DBTables.allTables.TABLE_NAME_IBAN_INFO, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_IBAN_INFO insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_IBAN_INFO inserted !");
        }
    }

    synchronized public void InsertContacts(
            String CONTACTS_OBJECT_ID,
            String CONTACTS_USER_ID,
            String CONTACTS_NUMBER)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.CONTACTS_OBJECT_ID, CONTACTS_OBJECT_ID);
        CV.put(DBTables.allTables.CONTACTS_USER_ID, CONTACTS_USER_ID);
        CV.put(DBTables.allTables.CONTACTS_NUMBER, CONTACTS_NUMBER);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_CONTACTS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_CONTACTS insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_CONTACTS inserted !");
        }
    }



    synchronized public void InsertSourceLoc(
            String SOURCE_LOC_SOURCE_LAT,
            String SOURCE_LOC_SOURCE_LONG)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.SOURCE_LOC_SOURCE_LAT, SOURCE_LOC_SOURCE_LAT);
        CV.put(DBTables.allTables.SOURCE_LOC_SOURCE_LONG, SOURCE_LOC_SOURCE_LONG);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_SOURCE_LOC, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_SOURCE_LOC insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_SOURCE_LOC inserted !");
        }
    }

    synchronized public void InsertDestLoc(
            String DEST_LOC_SOURCE_LAT,
            String DEST_LOC_SOURCE_LONG)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.DEST_LOC_SOURCE_LAT, DEST_LOC_SOURCE_LAT);
        CV.put(DBTables.allTables.DEST_LOC_SOURCE_LONG, DEST_LOC_SOURCE_LONG);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_DEST_LOC, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_DEST_LOC insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_DEST_LOC inserted !");
        }
    }

    synchronized public void InsertProduct(
            String PRODUCTS_NAME,
            String PRODUCTS_CATEGORY,
            String PRODUCTS_PRICE)
    {

        ContentValues CV = new ContentValues();
        CV.put(DBTables.allTables.PRODUCTS_NAME, PRODUCTS_NAME);
        CV.put(DBTables.allTables.PRODUCTS_CATEGORY, PRODUCTS_CATEGORY);
        CV.put(DBTables.allTables.PRODUCTS_PRICE, PRODUCTS_PRICE);

        long result =  db.insert(DBTables.allTables.TABLE_NAME_PRODUCTS, null, CV);

        if(result == -1)
        {
            Log.e("Database", " TABLE_NAME_PRODUCTS insertion error !");
        }
        else
        {
            Log.e("Database", " TABLE_NAME_PRODUCTS inserted !");
        }
    }
    // INSERT DATA METHODS ENDS /////////////////////////////////////////////////////////////////////////////////////////////////////////


    // UPDATE DATA METHODS STARTS ///////////////////////////////////////////////////////////////////////////////////////////////////////
    synchronized public void UpdateOrder(String pStrRecordID, String... pStrValues)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pStrValues.length; i+=2)
        {
            cv.put(pStrValues[i],pStrValues[i+1]);
        }
        long res = db.update(DBTables.allTables.TABLE_NAME_ORDERS, cv, DBTables.allTables.ORDER_CUSTOM_OBJ_ID + "=\"" + pStrRecordID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateOrder", " Record cannot be Updated successfuly !");
        }
        else
        {
            Log.e("UpdateOrder", " Record Updated successfuly into DB !");
        }
    }

    synchronized public void UpdateOrderImage(String pStrRecordID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.ORDER_IMAGE, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_ORDERS, cv, DBTables.allTables.ORDER_CUSTOM_OBJ_ID + "=\"" + pStrRecordID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateOrdrImg", " Image not inserted successfuly !");
        }
        else
        {
            Log.e("UpdateOrdrImg", " Image inserted successfuly into DB !");
        }
    }

    synchronized public void UpdateDriver(String pStrRecordID, String... pStrValues)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pStrValues.length; i+=2)
        {
            cv.put(pStrValues[i],pStrValues[i+1]);
        }
        //db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_CUSTOM_OBJECT_ID + " = " + pStrRecordID, null);
        db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_CUSTOM_OBJECT_ID + "=\"" + pStrRecordID + "\"", null);
    }

    synchronized public void UpdateDriverImage(int pStrUserID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.DRIVER_IMAGE, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_USER_ID + "=\"" + pStrUserID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateDriImg", " Image not inserted successfuly !");
        }
        else
        {
            Log.e("UpdateDriImg", " Image inserted successfuly into DB !");
        }
    }

    public void UpdateDriverCarImage(int pStrUserID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.DRIVER_CARPIC, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_USER_ID + "=\"" + pStrUserID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateCarImg", " Image not inserted successfuly !" + pByteImage.length);
        }
        else
        {
            Log.e("UpdateCarImg", " Image inserted successfuly into DB !" + pByteImage.length);
        }
    }

    public void UpdateDriverIdImage(int pStrUserID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.DRIVER_IDPIC, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_USER_ID + "=\"" + pStrUserID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateIdPic", " Image not inserted successfuly ! - " + pByteImage.length);
        }
        else
        {
            Log.e("UpdateIdPic", " Image inserted successfuly into DB !" + pByteImage.length);
        }
    }

    public void UpdateDriverLicenseImage(int pStrUserID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.DRIVER_LICENSE, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_DRIVERS, cv, DBTables.allTables.DRIVER_USER_ID + "=\"" + pStrUserID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateLicensePic", " Image not inserted successfuly !" + pByteImage.length);
        }
        else
        {
            Log.e("UpdateLicensePic", " Image inserted successfuly into DB !" + pByteImage.length);
        }
    }

    synchronized public void UpdateCustomer(String pIntRecordID, String... pStrValues)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pStrValues.length; i+=2)
        {
            cv.put(pStrValues[i],pStrValues[i+1]);
        }
        //db.update(DBTables.allTables.TABLE_NAME_CUSTOMERS, cv, DBTables.allTables.CUSTOMER_CUSTOM_OBJECT_ID + " = " + pIntRecordID, null);
        db.update(DBTables.allTables.TABLE_NAME_CUSTOMERS, cv, DBTables.allTables.CUSTOMER_CUSTOM_OBJECT_ID + "=\"" + pIntRecordID + "\"", null);
    }


    synchronized public void UpdateWallet(String pStrObjectID, String... pStrValues)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pStrValues.length; i+=2)
        {
            cv.put(pStrValues[i],pStrValues[i+1]);
        }
        db.update(DBTables.allTables.TABLE_NAME_WALLET, cv, DBTables.allTables.WALLET_OBJECT_ID + "=\"" + pStrObjectID + "\"", null);
    }


    synchronized public void UpdateIbanInfo(String pStrObjectID, String... pStrValues)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < pStrValues.length; i+=2)
        {
            cv.put(pStrValues[i],pStrValues[i+1]);
        }
        db.update(DBTables.allTables.TABLE_NAME_IBAN_INFO, cv, DBTables.allTables.IBAN_INFO_OBJECT_ID + "=\"" + pStrObjectID + "\"", null);
    }

    synchronized public void UpdateCustomerImage(int pStrUserID, byte[] pByteImage)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBTables.allTables.CUSTOMER_IMAGE, pByteImage);
        long res = db.update(DBTables.allTables.TABLE_NAME_CUSTOMERS, cv, DBTables.allTables.CUSTOMER_USER_ID + "=\"" + pStrUserID + "\"", null);
        if(res == -1)
        {
            Log.e("UpdateCusImage", " Image not inserted successfuly !");
        }
        else
        {
            Log.e("UpdateCusImage", " Image inserted successfuly into DB !");
        }
    }
    // UPDATE DATA METHODS ENDS /////////////////////////////////////////////////////////////////////////////////////////////////////////


    // CLEAR DATA METHODS STARTS ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ClearAllOrderTableValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_ORDERS);
    }

    public void DeleteOneOrderTableValue(String pStrOrderID)
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE _id = ?", new String[]{pStrOrderID});
        Log.e("Delete", "Delete Order from DB successfuly!");
    }

    public void ClearAllDriverTableValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_DRIVERS);
    }

    public void ClearAllCustomerTableValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_CUSTOMERS);
        Log.e("Delete", "Delete All Customers from DB successfuly!");
    }

    public void ClearAllBlockedUsersTableValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS);
    }

    public void DeleteBlockedUsersTableValue(String pFieldName, String value)
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS + " WHERE " + pFieldName + " = ?", new String[]{value});
    }


    public void ClearFreeOrdersLogValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_WALLET);
    }

    public void ClearIbanInfoValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_IBAN_INFO);
    }

    public void ClearContactsoValues()
    {
        db.execSQL("delete from " + DBTables.allTables.TABLE_NAME_CONTACTS);
    }
    // CLEAR DATA METHODS ENDS /////////////////////////////////////////////////////////////////////////////////////////////////////////



    // GET ALL DATA METHODS STARTS /////////////////////////////////////////////////////////////////////////////////////////////////////
    public Cursor GetAllOrders()
    {//
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_ORDERS, null);
        //return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE orderStatus = ?", new String[]{"NotProcessed"});
    }

    public Cursor GetNeedApprovalAndUnAttendedOrder()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_ORDERS + " where orderStatus = " + "\"" + "NeedApproval" + "\"" + "OR orderStatus = " + "\"" + "UnAttended" + "\"", null);
    }

    public Cursor GetAllDrivers()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_DRIVERS, null);
    }

    public Cursor GetAllCustomers()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_CUSTOMERS, null);
    }

    public Cursor GetAllBlockedUsers()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS, null);
    }

    public Cursor GetAllWallet()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_WALLET, null);
    }

    public Cursor GetAllIbanInfo()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_IBAN_INFO, null);
    }

    public Cursor GetAllContacts()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_CONTACTS, null);
    }


    public Cursor GetAllSourceLoc()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_SOURCE_LOC, null);
    }
    public Cursor GetAllDestLoc()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_DEST_LOC, null);
    }
    public Cursor GetAllProducts()
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_PRODUCTS, null);
    }

    // GET ALL DATA METHODS ENDS ///////////////////////////////////////////////////////////////////////////////////////////////////////



    // GET ONE ROW DATA METHODS STARTS /////////////////////////////////////////////////////////////////////////////////////////////////

    public Cursor GetOrdersState(String pFieldName, String value1, String value2)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE " + pFieldName + " = ? OR " + pFieldName + " = ?", new String[]{value1, value2});
    }

    public Cursor GetRequestedOrders(String pFieldName, String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE " + pFieldName + " = ?", new String[]{value});
    }

    public Cursor GetFreeOrdersFree(String pFieldName1, String value1, String pFieldName2, String value2)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE " + pFieldName1 + " = ? " + " AND " + pFieldName2 + " = ?", new String[]{value1,value2});
    }

    public Cursor GetFreeOrders(String value1, String value2, String value3, String value4)
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_ORDERS + " where orderStatus = " + "\"" + value1 + "\"" + "OR orderStatus = " + "\"" + value2 + "\"" + "OR orderStatus = " + "\"" + value3 + "\"" + "OR orderStatus = " + "\"" + value4 + "\"", null);
    }

    public Cursor GetOrdersHistory(String value1, String value2, String value3, String value4,String value5)
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_ORDERS + " where orderStatus = " + "\"" + value1 + "\"" + " OR orderStatus = " + "\"" + value2 + "\"" + " OR orderStatus = " + "\"" + value3 + "\"" + " OR orderStatus = " + "\"" + value4 + "\"" + " OR orderStatus = " + "\"" + value5 + "\"" + " order by orderStatus ASC", null);
    }

    public Cursor GetRequestedDrivers(String pFieldName, String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_DRIVERS + " WHERE " + pFieldName + " = ?", new String[]{value});
    }

    public Cursor GetOnlineDrivers(String pFieldName, String value1, String value2)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_DRIVERS + " WHERE " + pFieldName + " = ? " + " OR " + pFieldName + " = ?", new String[]{value1,value2});
    }

    public Cursor GetRequestedDriverUserID(String pFieldName, int value)
    {
        String q = "SELECT * FROM "  + DBTables.allTables.TABLE_NAME_DRIVERS + " WHERE " + pFieldName + " = " + value;
        return db.rawQuery(q, null);
    }

    public Cursor GetRequestedCustomers(String pFieldName, String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_CUSTOMERS + " WHERE " + pFieldName + " = ?", new String[]{value});
    }

    public Cursor GetRequestedCustomerUserID(String pFieldName, int value)
    {
        String q = "SELECT * FROM "  + DBTables.allTables.TABLE_NAME_CUSTOMERS + " WHERE " + pFieldName + " = " + value;
        return db.rawQuery(q, null);
    }


    public Cursor GetRequestedBlockedUsers(String pFieldName, String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS + " WHERE " + pFieldName + " = ?", new String[]{value});
    }

    public Cursor GetRequestedBlockedUserRow(String value1, String value2)
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS + " where user_id = " + "\"" + value1 + "\"" + "AND blockedID = " + "\"" + value2 + "\"", null);
    }

    public Cursor GetRequestedBlockedUser(String value)
    {
        return db.rawQuery("SELECT * FROM " + DBTables.allTables.TABLE_NAME_BLOCKEDUSERS + " where user_id = " + "\"" + value + "\"" + "OR blockedID = " + "\"" + value + "\"", null);
    }

    public Cursor GetRequestedWallet(String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_WALLET + " WHERE walletObjectId = ?", new String[]{value});
    }

    public Cursor GetRequestedIbanInfo(String value)
    {
        return db.rawQuery("SELECT * FROM "  + DBTables.allTables.TABLE_NAME_IBAN_INFO + " WHERE ibaninfoobjectid = ?", new String[]{value});
    }
    // GET ONE ROW DATA METHODS ENDS ///////////////////////////////////////////////////////////////////////////////////////////////////


    public byte[] GetOrderPicLocal(String pFieldName, final String value)
    {
        Cursor c =  db.rawQuery("SELECT image FROM " + DBTables.allTables.TABLE_NAME_ORDERS + " WHERE " + pFieldName + " = ?", new String[]{value});
        if(c.getCount() > 0)
        {
            c.moveToFirst();
            return c.getBlob(c.getColumnIndexOrThrow(DBTables.allTables.ORDER_IMAGE));
        }
        c.close();
        return null;
    }

    private void GetOrderPicServer(final String pStrOrderCustomObjectId)
    {
        QBCustomObject customObject = new QBCustomObject("order",pStrOrderCustomObjectId);

        QBCustomObjectsFiles.downloadFile(customObject, "image", new QBEntityCallback<InputStream>() {
            @Override
            public void onSuccess(InputStream inputStream, Bundle params) {
                Log.d(" imageGet ", "Image retrieved Suucessfuly");
                new RetrieveImageTask(pStrOrderCustomObjectId).execute(inputStream);
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d(" imageGet ", errors.getMessage());
            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {

            }
        });
    }


    class RetrieveImageTask extends AsyncTask<InputStream, Void, byte[]> {

        private Exception exception;
        byte[] dImageBytes = null;
        String strOrderObjectId;


        public RetrieveImageTask(String pStrOrderCustomObjectId){
            this.strOrderObjectId = pStrOrderCustomObjectId;
        }

        protected byte[] doInBackground(InputStream... urls) {
            try {
                try {
                    dImageBytes = readInputStreamFully(urls[0]);
                }
                catch (MalformedURLException e) {
                }
                catch (IOException e) {
                }

            } catch (Exception e) {
                this.exception = e;
                return null;
            }
            return dImageBytes;
        }

        protected void onPostExecute(byte[] finalBytesImage) {
            Log.d(" imageGet ", "Image Inserted Suucessfuly");
            UpdateOrderImage(this.strOrderObjectId, finalBytesImage);
        }
    }

    public static byte[] readInputStreamFully(InputStream input) throws IOException
    {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
    //
}
