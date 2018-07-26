package com.evosys.mersalordergenerator.helperclasses;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by ehtisham on 7/20/16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    Context mContext;
    int mCameraID;

    public CameraPreview(Context context, Camera camera, int cameraID) {
        super(context);
        mCamera = camera;
        mContext = context;
        mCameraID = cameraID;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("CameraPreview", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here



        Camera.Parameters parameters = mCamera.getParameters();
        Display display = ((WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();


        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        Camera.getCameraInfo(mCameraID, cameraInfo);

//        int result = 0;
//        if(display.getRotation() == Surface.ROTATION_0)
//        {
//            parameters.setPreviewSize(h, w);
//            result = (cameraInfo.orientation + 90) % 360;
//            result = (360 - result) % 360;
//            //mCamera.setDisplayOrientation(90);
//            mCamera.setDisplayOrientation(result);
//        }
//
//        if(display.getRotation() == Surface.ROTATION_90)
//        {
//            parameters.setPreviewSize(w, h);
//            result = (cameraInfo.orientation + 90) % 360;
//            result = (360 - result) % 360;
//            mCamera.setDisplayOrientation(result);
//        }
//
//        if(display.getRotation() == Surface.ROTATION_180)
//        {
//            parameters.setPreviewSize(h, w);
//            result = (cameraInfo.orientation + 180) % 360;
//            result = (360 - result) % 360;
//            mCamera.setDisplayOrientation(result);
//        }
//
//        if(display.getRotation() == Surface.ROTATION_270)
//        {
//            parameters.setPreviewSize(w, h);
//            result = (cameraInfo.orientation + 180) % 360;
//            result = (360 - result) % 360;
//            mCamera.setDisplayOrientation(result);
//            //mCamera.setDisplayOrientation(180);
//        }

        int degrees = 0;
        int rotation = display.getRotation();
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d("CameraPreview", "Error starting camera preview: " + e.getMessage());
        }
    }
}
