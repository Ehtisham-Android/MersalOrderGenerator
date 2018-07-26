package com.evosys.mersalordergenerator.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationType;
import com.quickblox.messages.model.QBPushType;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by ehtisham on 1/2/17.
 */

public class DialogsUtils {

    private static DialogsUtils instance = null;
    private Context appContext;
    private ProgressDialog pDialog;
    private final String IMAGE_DIRECTORY_NAME = "MersalMedia";
    private String CreatedPicPath = "";

    public static DialogsUtils InitializeDialogsUtils(Context pAppContext)
    {
        if(instance == null)
        {
            instance = new DialogsUtils(pAppContext);
        }

        Log.d("DialogsUtils", "Initialized Successfully!");
        return instance;
    }

    public static DialogsUtils getInstance()
    {
        return instance;
    }

    public DialogsUtils(Context pAppContext) {
        appContext = pAppContext;
        CreateLoadingDialog();
    }

    public static void ResetInstance()
    {
        instance = null;
    }

    public void showDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }


    // PROGRESS DIALOGS FOR SHOW PROGRESS ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void CreateLoadingDialog()
    {
        pDialog = new ProgressDialog(appContext);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
    }

    public void RemoveLoadingDialog()
    {
        if (pDialog != null)
            pDialog.dismiss();
    }

    public void ShowLoadingDialog(String text)
    {
        if (pDialog != null){
            pDialog.setMessage(text);
            pDialog.show();
        }
    }
    // PROGRESS DIALOGS FOR SHOW PROGRESS ///////////////////////////////////////////////////////////////////////////////////////////////////


    // GET TIME STAMP ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String GetTimeStamp()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
    // GET TIME STAMP ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET BYTES FROM BITMAP ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public byte[] getBytes(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        return null;
    }
    // GET BYTES FROM BITMAP ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET BITMAP IMAGE FROM BYTES ARRAY ////////////////////////////////////////////////////////////////////////////////////////////////////
    public Bitmap getImage(byte[] image) {
        if(image != null)
        {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }
    // GET BITMAP IMAGE FROM BYTES ARRAY ////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET SPLITTED STRING BASED ON COMMA ///////////////////////////////////////////////////////////////////////////////////////////////////
    public List<String> SplitString(String pStrCoordinates)
    {
        return Arrays.asList(pStrCoordinates.split(","));
    }
    // GET SPLITTED STRING BASED ON COMMA ///////////////////////////////////////////////////////////////////////////////////////////////////


    // SHOW TOAST ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ShowToast(String text, Context con)
    {
        Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
    }
    // SHOW TOAST ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    public double GetDistanceInfo(double latFrom, double lngFrom, double latTo, double lngTo) {
//        StringBuilder stringBuilder = new StringBuilder();
//        Double dist = 0.0;
//        try {
//
//            //destinationAddress = destinationAddress.replaceAll(" ","%20");
//            //String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR&key=YOUR_API_KEY";
//            String urll = "https://maps.googleapis.com/maps/api/distancematrix/json?origin=" + latFrom + "," + lngFrom + "&destination=" + latTo + "," + lngTo + "&mode=driving&sensor=false&key=AIzaSyBF1H1cz31bfzCYIsfWyBH4-fCYKQTsjvA";
//
//
//            URL httpUrl = new URL(urll);
//            HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
//            try {
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                readStream(in);
//            } finally {
//                urlConnection.disconnect();
//            }
//
//
//
//            HttpPost httppost = new HttpPost(url);
//
//            HttpClient client = new DefaultHttpClient();
//            HttpResponse response;
//            stringBuilder = new StringBuilder();
//
//
//            response = client.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            InputStream stream = entity.getContent();
//            int b;
//            while ((b = stream.read()) != -1) {
//                stringBuilder.append((char) b);
//            }
//        } catch (ClientProtocolException e) {
//        } catch (IOException e) {
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//
//            jsonObject = new JSONObject(stringBuilder.toString());
//
//            JSONArray array = jsonObject.getJSONArray("routes");
//
//            JSONObject routes = array.getJSONObject(0);
//
//            JSONArray legs = routes.getJSONArray("legs");
//
//            JSONObject steps = legs.getJSONObject(0);
//
//            JSONObject distance = steps.getJSONObject("distance");
//
//            Log.i("Distance", distance.toString());
//            dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return dist;
//    }


    // PICTURE RELATED METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String SaveImageToTemp(String path)
    {
        String newPath = "";
        try
        {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(path);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(path);
            options = new BitmapFactory.Options();

            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth/640, inHeight/480);

            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, 640, 480);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);


            int rotation = 0;
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(path);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        resizedBitmap = rotateImage(resizedBitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        resizedBitmap = rotateImage(resizedBitmap, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        resizedBitmap = rotateImage(resizedBitmap, 270);
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);

            //Bitmap resizedBitmap = Bitmap.createBitmap(roughBitmap, 0,0 ,(int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), matrix, true);
            newPath = saveToInternalSorage(resizedBitmap);
            try
            {
                FileOutputStream out = new FileOutputStream(newPath);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
            catch (Exception e)
            {
                Log.e("Image", e.getMessage(), e);
            }
        }
        catch (IOException e)
        {
            Log.e("Image", e.getMessage(), e);
        }

        return newPath;

    }

    @SuppressLint("NewApi")
    public String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private String saveToInternalSorage(Bitmap bitmapImage)
    {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("RecordVideo", "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = "pic_" + DialogsUtils.getInstance().GetTimeStamp();
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "mersalmedia_" + timeStamp + ".jpg");
        CreatedPicPath = mediaFile.getPath();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mediaFile);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaFile.getAbsolutePath();
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public int Scalefactor(Bitmap img)
    {
        int thumbFactor = 1; // choose a power of 2
        if(img.getWidth() > 300 && img.getWidth() < 600 || img.getHeight() > 300 && img.getHeight() < 600)
        {
            thumbFactor = 2;
        }else if(img.getWidth() > 600 && img.getWidth() < 1000 || img.getHeight() > 600 && img.getHeight() < 1000)
        {
            thumbFactor = 3;
        }else if(img.getWidth() > 1000 || img.getHeight() > 1000)
        {
            thumbFactor = 4;
        }
        return thumbFactor;
    }


    public int ScalefactorSpecial(Bitmap img)
    {
        int thumbFactor = 1; // choose a power of 2
        if(img.getWidth() > 300 && img.getWidth() < 600 || img.getHeight() > 300 && img.getHeight() < 600)
        {
            thumbFactor = 2;
        }else if(img.getWidth() > 600 && img.getWidth() < 1000 || img.getHeight() > 600 && img.getHeight() < 1000)
        {
            thumbFactor = 4;
        }else if(img.getWidth() > 1000 || img.getHeight() > 1000)
        {
            thumbFactor = 5;
        }
        return thumbFactor;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void QBSendPushNotification(String pStrSenderName, QBCustomObject objQBCustomObject)
    {
        StringifyArrayList<String> usersTags = new StringifyArrayList<>();
        usersTags.add("driver");
        // recipients
        QBEvent event = new QBEvent();
        event.setUserTagsAny(usersTags);
        event.setEnvironment(QBEnvironment.PRODUCTION);
        //event.setEnvironment(QBEnvironment.DEVELOPMENT);
        event.setNotificationType(QBNotificationType.PUSH);
        event.setPushType(QBPushType.GCM);
        //event.setPushType(QBPushType.APNS);
        HashMap<String, Object> data = new HashMap<>();
        data.put("data.message", "New order received by " + pStrSenderName);
        data.put("data.type", "Driver");
        //data.put("content", objQBCustomObject);
        event.setMessage(data);

        JSONObject objJSONObject = new JSONObject();
        try{

            objJSONObject.put("user_id", "234");
            objJSONObject.put("thread_id", "8343");

        }catch (Exception ex)
        {

        }

        //event.setMessage(objJSONObject.toString());

        QBPushNotifications.createEvent(event, new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle args) {
                // sent
                Log.d("PushNotttt", "Success: " + qbEvent.getMessage());
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d("PushNotttt", "Failed: " + errors.getMessage());
            }
        });
    }

    public void QBSendPushNotification1to1(String pStrReveiverId, String pStrMessage)
    {
        StringifyArrayList<Integer> userIds = new StringifyArrayList<>();
        userIds.add(Integer.valueOf(pStrReveiverId));

        // recipients
        QBEvent event = new QBEvent();
        event.setUserIds(userIds);
        event.setEnvironment(QBEnvironment.PRODUCTION);
        //event.setEnvironment(QBEnvironment.DEVELOPMENT);
        event.setNotificationType(QBNotificationType.PUSH);
        event.setPushType(QBPushType.GCM);
        HashMap<String, Object> data = new HashMap<>();
        data.put("data.message", pStrMessage);
        data.put("data.type", "Mersal");
        event.setMessage(data);

        QBPushNotifications.createEvent(event, new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle args) {
                // sent
                Log.d("PushNotttt", "Success: " + qbEvent.getMessage());
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d("PushNotttt", "Failed: " + errors.getMessage());
            }
        });
    }


    private String GetRandomCharacter()
    {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        return String.valueOf(c);
    }

    public String GeneratePromoCode(String pStrUserId)
    {
        StringBuilder sb = new StringBuilder(pStrUserId);
        sb.insert(3, "-");
        sb.insert(7, "-");
        sb.insert(8, GetRandomCharacter());
        sb.insert(10, GetRandomCharacter());
        Log.d("PromoCode", sb.toString());
        return sb.toString();
    }


}
