package com.evosys.mersalordergenerator.helperclasses;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;

import com.evosys.mersalordergenerator.R;


/**
 * Created by ehtisham on 10/18/16.
 */
public abstract class RuntimePermissionsActivity extends AppCompatActivity implements View.OnClickListener {
    private SparseIntArray mErrorString;
    Dialog PermissionsSettingDialog;
    Button btnExit, btnSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
        CreateEnablePermissionsDialoge();

        btnExit = (Button) PermissionsSettingDialog.findViewById(R.id.btn_dialog_exit);
        btnSettings = (Button) PermissionsSettingDialog.findViewById(R.id.btn_dialog_settings);

        btnExit.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }

        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
            PermissionsSettingDialog.dismiss();
        } else {
//            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
//                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            intent.addCategory(Intent.CATEGORY_DEFAULT);
//                            intent.setData(Uri.parse("package:" + getPackageName()));
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                            startActivity(intent);
//                        }
//                    }).show();

            PermissionsSettingDialog.show();
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED, shouldShowRequestPermissionRationaleCount = 0;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);

            boolean abc = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);

            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
            shouldShowRequestPermissionRationaleCount = shouldShowRequestPermissionRationale ? (shouldShowRequestPermissionRationaleCount+1):shouldShowRequestPermissionRationaleCount;
        }

        // here we can restrict shouldShowRequestPermissionRationale = false
        shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationaleCount == permissionCheck;


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
//                Snackbar.make(findViewById(android.R.id.content), stringId,
//                        Snackbar.LENGTH_INDEFINITE).setAction("GRANT",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ActivityCompat.requestPermissions(RuntimePermissionsActivity.this, requestedPermissions, requestCode);
//                            }
//                        }).show();
                PermissionsSettingDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            PermissionsSettingDialog.dismiss();
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    private void CreateEnablePermissionsDialoge() {
        PermissionsSettingDialog = new Dialog(this);
        PermissionsSettingDialog.setContentView(R.layout.enable_permissions_dialoge);
        PermissionsSettingDialog.setCancelable(false);
        //phoneDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_dialog_exit:
                finish();
                break;
            case R.id.btn_dialog_settings:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                break;
        }
    }
}
