package com.evosys.mersalordergenerator.helperclasses;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by ehtisham on 10/18/16.
 */
public class MyAppPermissions {

    private static MyAppPermissions instance;
    private ArrayList<String> AllPermissionsList = new ArrayList<>();


    public String customVar;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new MyAppPermissions();
        }
    }

    public static MyAppPermissions getInstance()
    {
        // Return the instance
        return instance;
    }

    private MyAppPermissions()
    {
        // Constructor hidden because this is a singleton
        SetAllPermissionsList();
    }

    private void SetAllPermissionsList()
    {
        AllPermissionsList.add(Manifest.permission.CAMERA);
        AllPermissionsList.add(Manifest.permission.READ_SMS);
        AllPermissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        AllPermissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        AllPermissionsList.add(Manifest.permission.READ_PHONE_STATE);
        AllPermissionsList.add(Manifest.permission.RECORD_AUDIO);
        AllPermissionsList.add(Manifest.permission.READ_CONTACTS);
    }

    private ArrayList<String> GetAllPermissionsList()
    {
        return AllPermissionsList;
    }

    public String[] GetPendingAppPermissions(Activity pActivity)
    {
        ArrayList<String> permissionList = new ArrayList<>();

        for(int i = 0; i < GetAllPermissionsList().size(); i++)
        {
            if(ContextCompat.checkSelfPermission(pActivity, GetAllPermissionsList().get(i)) != PackageManager.PERMISSION_GRANTED)
            {
                permissionList.add(GetAllPermissionsList().get(i));
            }
        }

        return permissionList.toArray(new String[permissionList.size()]);
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
