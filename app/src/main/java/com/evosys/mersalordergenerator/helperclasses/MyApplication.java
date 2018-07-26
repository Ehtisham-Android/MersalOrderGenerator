package com.evosys.mersalordergenerator.helperclasses;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by ehtisham on 10/18/16.
 */
public class MyApplication extends Application
{

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        MyAppPermissions.initInstance();
    }

    public void customAppMethod()
    {
        // Custom application method
    }
}
