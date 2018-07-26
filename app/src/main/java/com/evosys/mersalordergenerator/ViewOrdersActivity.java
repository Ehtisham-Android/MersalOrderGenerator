package com.evosys.mersalordergenerator;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.evosys.mersalordergenerator.adapter.DBAdapter;
import com.evosys.mersalordergenerator.adapter.OrderDetailsListAdaptor;
import com.evosys.mersalordergenerator.helperclasses.PubNubClass;
import com.evosys.mersalordergenerator.model.DBTables;
import com.evosys.mersalordergenerator.model.OrderDetailsModel;
import com.evosys.mersalordergenerator.utils.DialogsUtils;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewOrdersActivity extends AppCompatActivity {

    DBAdapter dbadapter;
    SubscribeCallback subscribeCallback;
    // ORDERS LIST VIEW VARIABLES //////////////////////////////////////////////////////////////////////////////////////////////
    ListView customerOrderList;
    OrderDetailsListAdaptor objOrderDetailsListAdapter;
    ArrayList<OrderDetailsModel> OrderDetailsListViewValuesArray = new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        InitializeDatabase();

        customerOrderList = (ListView) findViewById(R.id.lv_Order);
        objOrderDetailsListAdapter = new OrderDetailsListAdaptor(this, OrderDetailsListViewValuesArray, getResources());
        customerOrderList.setAdapter(objOrderDetailsListAdapter);

        // Get records from QB
        GetAllOrders();
        OrderStatesListener();
        //Save locally
        // Show in list




        // Start Pubnub (Lissa)
    }

    private void GetAllOrders() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.or("user_id", "", "", "");
        requestBuilder.or("orderStatus", "InProcess", "Delivered", "Picked", "Closed", "NeedApproval", "UnAttended", "Cancelled");

        QBCustomObjects.getObjects("order", requestBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {

                if (customObjects.size() > 0) {

                    for (int i = 0; i < customObjects.size(); i++) {
                        Cursor cursorOrder = dbadapter.GetRequestedOrders(DBTables.allTables.ORDER_CUSTOM_OBJ_ID, customObjects.get(i).getCustomObjectId());
                        if (cursorOrder.getCount() > 0) {


                            dbadapter.UpdateOrder(customObjects.get(i).getCustomObjectId(),
                                    DBTables.allTables.ORDER_STATUS, customObjects.get(i).get("orderStatus").toString(),
                                    DBTables.allTables.ORDER_DRIVER_USER_ID, customObjects.get(i).get("driverUserId").toString(),
                                    DBTables.allTables.ORDER_DRIVER_RATING, customObjects.get(i).get("driverRating").toString(),
                                    DBTables.allTables.ORDER_CUSTOMER_RATING, customObjects.get(i).get("customerRating").toString()
                            );
                            cursorOrder.close();

                        } else {

                            dbadapter.InsertOrder(
                                    customObjects.get(i).getUserId().toString(),
                                    customObjects.get(i).getCustomObjectId(),
                                    customObjects.get(i).get("deliveryPrice").toString(),
                                    customObjects.get(i).get("name").toString(),
                                    customObjects.get(i).get("explanations").toString(),
                                    customObjects.get(i).get("itemPrice").toString(),
                                    customObjects.get(i).get("category").toString(),
                                    customObjects.get(i).get("orderStatus").toString(),
                                    customObjects.get(i).get("driverUserId").toString(),
                                    customObjects.get(i).get("driverRating").toString(),
                                    customObjects.get(i).get("customerRating").toString(),
                                    customObjects.get(i).getArray("coordinates").get(1).toString(), // destination lat
                                    customObjects.get(i).getArray("coordinates").get(0).toString(), // destination long
                                    customObjects.get(i).getArray("coordinates").get(3).toString(), // source lat
                                    customObjects.get(i).getArray("coordinates").get(2).toString(), // source long
                                    String.valueOf(customObjects.get(i).getUpdatedAt().getTime()), // changed
                                    customObjects.get(i).get("timer").toString(),
                                    customObjects.get(i).get("DistanceRange").toString(),
                                    customObjects.get(i).get("CustomerTotalRating").toString(),
                                    customObjects.get(i).get("isFree").toString(),
                                    customObjects.get(i).get("AwardedAmount").toString()
                            );

                        }
                    }

                    // Fetch data from local db and set all orders record into live list view
                    GetAllOrderRecordLocalDB();
                } else {
                    DialogsUtils.getInstance().RemoveLoadingDialog();
                }
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d(" abcd ", errors.getMessage());
            }
        });
    }

    // getting all data from local db ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void GetAllOrderRecordLocalDB() {
        Cursor cursorOnGoingOrders = dbadapter.GetOrdersHistory("NeedApproval","InProcess","Picked","Delivered","Closed");
        if(cursorOnGoingOrders.getCount() > 0)
        {
            cursorOnGoingOrders.moveToFirst();
            // Get and set all record in list

                for(int i = 0; i < cursorOnGoingOrders.getCount(); i++)
                {
                    String status = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_STATUS));
                    String UserID = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_USER_ID));
                    String category = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_CATEGORY));
                    String ProductName = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_NAME));
                    String OrderObjectID = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_CUSTOM_OBJ_ID));
                    String ProductDetails = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_DETAILS));
                    String ProductItemPrice = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_ITEM_PRICE));
                    String ProductDelivereyPrice = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_DELIVERY_PRICE));
                    String OrderStatus = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_STATUS));
                    String PushedTime = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_PUSHED_TIME));
                    String TimerValue = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_TIMER));
                    String SourceLat = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_SOURCE_LAT));
                    String SourceLong = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_SOURCE_LONG));
                    String DestinationLat = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_DEST_LAT));
                    String DestinationLong = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_DEST_LONG));
                    String DriverUserID = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_DRIVER_USER_ID));
                    byte[] image = cursorOnGoingOrders.getBlob(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_IMAGE));
                    String ApprovedTime = cursorOnGoingOrders.getString(cursorOnGoingOrders.getColumnIndexOrThrow(DBTables.allTables.ORDER_APPROVAL_TIME));

                    if(ApprovedTime == null)
                    {
                        ApprovedTime = PushedTime;
                    }

                    final OrderDetailsModel lData = new OrderDetailsModel();
                    lData.setItemPrice(Integer.valueOf(ProductItemPrice));
                    lData.setDeliveryPrice(ProductDelivereyPrice);
                    lData.setProductName(ProductName);
                    lData.setTimer(1440);
                    lData.setOrderStatus(OrderStatus);
                    lData.setProductDetails(ProductDetails);
                    lData.setUserID(Integer.parseInt(UserID));
                    //lData.setDriverUserID(Integer.parseInt(DriverUserID));
                    lData.setProductCategory(category);
                    lData.setOrderSourceLat(Double.valueOf(SourceLat));
                    lData.setOrderSourceLong(Double.valueOf(SourceLong));
                    lData.setOrderDestinationLat(Double.valueOf(DestinationLat));
                    lData.setOrderDestinationLong(Double.valueOf(DestinationLong));
                    lData.setTwoMinsTimer(ApprovedTime);
                    lData.setCustomerOrderID(OrderObjectID);
                    lData.setNeedApprovalTimer(CompareTimeMillis(PushedTime, TimerValue));
                    OrderDetailsListViewValuesArray.add(lData);

                    cursorOnGoingOrders.moveToNext();
                }
                SetListViewDataLocal();
            }
        cursorOnGoingOrders.close();
    }

    public void SetListViewDataLocal() {
        objOrderDetailsListAdapter.notifyDataSetChanged();
        customerOrderList.invalidate();
    }
    // getting all data from local db END /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void InitializeDatabase()
    {
        dbadapter = new DBAdapter(getApplicationContext());
        dbadapter.open();
    }




    // PUBNUB LISTENERES STARTS //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void OrderStatesListener()
    {
//        subscribeCallback = new SubscribeCallback() {
//            @Override
//            public void status(PubNub pubnub, PNStatus status) {
//                PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", "Review orders Fragment listener status: " + status.isError());
//            }
//
//            @Override
//            public void message(PubNub pubnub, PNMessageResult message) {
//
//                final JSONObject jObjMessage = PubNubClass.getInstance().GetMessage(message);
//                final JSONObject jObjFields = PubNubClass.getInstance().GetFields(jObjMessage);
//
//                //PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", "Channel: " + message.getChannel() + " Message: " + jObjMessage.toString() + " Fields: " + jObjFields.toString());
//
//                try {
//                    if(!message.getChannel().equalsIgnoreCase(GlobalClass.PN_driverschannel) && !message.getChannel().equalsIgnoreCase(GlobalClass.PN_customerschannel) && !message.getChannel().equalsIgnoreCase(GlobalClass.PN_Allchannel))
//                    {
//
//                        if(jObjMessage.get("title").toString().equalsIgnoreCase(GlobalClass.PN_Title_activeOrder))
//                        {
//
//                            PubNubClass.getInstance().PubNubLog("DiffLog2", "OrderStatus: " + jObjFields.get("orderStatus").toString() +  " Channel: " + message.getChannel() + " Message: " + jObjMessage.toString() + " Fields: " + jObjFields.toString());
//
//                            if(jObjFields.get("orderStatus").toString().equalsIgnoreCase("InProcess") && jObjMessage.get("objectID").toString().equalsIgnoreCase(GlobalClass.SingleOrder.getCustomerOrderID()))
//                            {
//                                NeedApprovalOrderStateCheck(jObjMessage, jObjFields);
//                                PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", " Channel: ", " other ", " Title: ", GlobalClass.PN_Title_activeOrder, " Order Status: " , " Inprocess: ", " Message: " , message.toString());
//
//                            }
//                            else if(jObjFields.get("orderStatus").toString().equalsIgnoreCase("Picked") || jObjFields.get("orderStatus").toString().equalsIgnoreCase("Delivered"))
//                            {
//                                UpdatePickedDeliveredOrdersLocalDB(jObjMessage, jObjFields, null);
//                                PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", " Channel: ", " other ", " Title: ", GlobalClass.PN_Title_activeOrder, " Order Status: " , " Picked,Delivered: ", " Message: " , message.toString());
//                            }
//                            else if(jObjFields.get("orderStatus").toString().equalsIgnoreCase("Cancelled"))
//                            {
//                                if(jObjFields.has("CancelReason"))
//                                {
//                                    UpdatePickedDeliveredOrdersLocalDB(jObjMessage, jObjFields, jObjFields.get("CancelReason").toString());
//                                }else{
//                                    UpdatePickedDeliveredOrdersLocalDB(jObjMessage, jObjFields, "Unknown");
//                                }
//                                PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", " Channel: ", " other ", " Title: ", GlobalClass.PN_Title_activeOrder, " Order Status: " , " Cancelled ", " Message: " , message.toString());
//
//                            }else{
//                                //PubNubClass.getInstance().PubNubLog("DiffLog3", "OrderStatus: " + jObjFields.get("orderStatus").toString() +  " Channel: " + message.getChannel() + " Message: " + jObjMessage.toString() + " Fields: " + jObjFields.toString());
//                            }
//                        }else{
//                            //PubNubClass.getInstance().PubNubLog("DiffLog4", "Channel: " + message.getChannel() + " Message: " + jObjMessage.toString() + " Fields: " + jObjFields.toString());
//                        }
//                    }else if(message.getChannel().equalsIgnoreCase("All"))
//                    {
//                        if(jObjMessage.get("title").toString().equalsIgnoreCase(GlobalClass.PN_Title_locations))
//                        {
//                            SaveDriversDB(jObjMessage, jObjFields);
//                            PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", " Channel: ", " All ", " Title: ", GlobalClass.PN_Title_locations, " Message: " , message.toString());
//                        }
//                    }else{
//                        PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", " Channel: ", " UnKnown ", " Message: " , message.toString());
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
//            }
//        };
//
//        PubNubClass.getInstance().pubnub.addListener(subscribeCallback);
    }
    // PUBNUB LISTENERES ENDS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public float CompareTimeMillis(String pStrPushedTime, String pStrTimer)
    {
        Calendar cDate = Calendar.getInstance();
        int currentDay = cDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cDate.get(Calendar.MONTH);
        int currentYear = cDate.get(Calendar.YEAR);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(currentYear, currentMonth, currentDay);


        long mili =   currentDate.getTimeInMillis() - Long.parseLong(pStrPushedTime);
        long sec = mili / 1000;
        float diff =  (Integer.parseInt(pStrTimer) * 60) - 10;
        float finaldiff =  diff - sec;
        Log.d("SecondsCount", "Seconds: " + sec + " Difference: " + finaldiff);
        return finaldiff*1000;
    }


    @Override
    public void onResume() {
        super.onResume();
        OrderStatesListener();
        Log.d("onResume()", "onResume() I am here");
        //GetAllOrderRecordLocalDB();
    }

    @Override
    public void onPause() {
        super.onPause();
        PubNubClass.getInstance().pubnub.removeListener(subscribeCallback);
        subscribeCallback = null;
        PubNubClass.getInstance().PubNubLog("ReviewOrdrFragLis", "Review orders Fragment listener status: Removed");
    }
}
