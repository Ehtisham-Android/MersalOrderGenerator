package com.evosys.mersalordergenerator.definition;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;

import com.evosys.mersalordergenerator.model.UserModel;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

/**
 * Created by ehtisham on 3/21/16.
 */


public class GlobalClass {

    public static Activity gActivity = null;

    // Set preferences universal name to access it from anywhere
    private static final String ORDERDETAILS = "AppPrefs";



//    // QB Variables Live
    public static final String APP_ID = "32424";
    public static final String AUTH_KEY = "TBaJvj3wEe77EqF";
    public static final String AUTH_SECRET = "hFxLuFhaqdy8HQT";
    public static final String ACCOUNT_KEY = "xnA6HqsNksdrcTnWyLqz";
    public static final  String PROJECT_NUMBER = "137774698382";


    // QB Variables Test
//    public static final String APP_ID = "52995";
//    public static final String AUTH_KEY = "K6dBmb62GGGNGtn";
//    public static final String AUTH_SECRET = "5u9WUGKasjxXJJV";
//    public static final String ACCOUNT_KEY = "QvdRLYVzMzZHghgXY16C";
//    public static final  String PROJECT_NUMBER = "137774698382";


    //public static final String SINCH_APPLICATION_KEY = "8298afad-3417-4574-a0f5-200708644bd0"; // test
    public static final String SINCH_APPLICATION_KEY = "0496275e-7c06-4f6a-b66e-3258b70c4193"; // live


//    // Pubnub keys LIVE //////////////////////////////////////////////////////////////////////////////////
    public static final  String PUBNUB_SUBSCRIBER_KEY = "sub-c-e7618526-c03a-11e6-8036-0619f8945a4f";
    public static final  String PUBNUB_PUBLISH_KEY = "pub-c-49b6c560-279c-4fe8-8341-d1280663ccf0";
//    //////////////////////////////////////////////////////////////////////////////////////////////////////

    // Pubnub keys TEST //////////////////////////////////////////////////////////////////////////////////
//    public static final  String PUBNUB_SUBSCRIBER_KEY = "sub-c-31f4f0ec-bf9f-11e6-b490-02ee2ddab7fe";
//    public static final  String PUBNUB_PUBLISH_KEY = "pub-c-b8ddbe1a-67c5-403d-8fb5-34a9914a7c7c";
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public static final String PN_customerschannel = "customers";
    public static final String PN_driverschannel = "drivers";
    public static final String PN_Allchannel = "All";


    public static final String PN_Title_order = "order";
    public static final String PN_Title_activeOrder = "activeOrder";
    public static final String PN_Title_locations = "locations";
    public static final String PN_Title_block = "block";


    // NEW VARIABLES ///////////////////////////////////////////////////////////////////////////////
    public static int WhichUser = 0;
    public static UserModel UserSession = new UserModel();


    public static void SaveStringPrefs(Context con, String key, String val) {
        SharedPreferences sharedpreferences = con.getSharedPreferences(ORDERDETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }


    public static void SaveIntPrefs(Context con, String key, int val) {
        SharedPreferences sharedpreferences = con.getSharedPreferences(ORDERDETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static int GetIntPrefs(Context con, String key) {
        SharedPreferences sharedpreferences = con.getSharedPreferences(ORDERDETAILS, Context.MODE_PRIVATE);
        return sharedpreferences.getInt(key, 0);
    }

    public static String GetStringPrefs(Context con, String key) {
        SharedPreferences sharedpreferences = con.getSharedPreferences(ORDERDETAILS, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public static QBUser UserCredentials(String pStrUserName, String pStrPassword)
    {
        //QBUser user = new QBUser(); user.setEmail(pStrUserName); user.setPassword(pStrPassword);
        return new QBUser(pStrUserName, pStrPassword);
    }
}
