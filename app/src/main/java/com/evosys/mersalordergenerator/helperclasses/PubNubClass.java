package com.evosys.mersalordergenerator.helperclasses;

import android.util.Log;

import com.evosys.mersalordergenerator.definition.GlobalClass;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ehtisham on 12/25/16.
 */
public class PubNubClass {

    private static PubNubClass instance;

    public PubNub pubnub;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new PubNubClass();
        }
    }

    public static PubNubClass getInstance()
    {
        // Return the instance
        return instance;
    }

    private PubNubClass()
    {
        // Constructor hidden because this is a singleton
        InitializePubnub();
    }


    // PUBNUB METHODS STARTS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void InitializePubnub()
    {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(GlobalClass.PUBNUB_SUBSCRIBER_KEY);
        pnConfiguration.setPublishKey(GlobalClass.PUBNUB_PUBLISH_KEY);
        pnConfiguration.setSecure(false);

        pubnub = new PubNub(pnConfiguration);
        PubNubLog("InitializePubnub", "Pubnub initialized successfully! ");
    }

    //public void PNPublishMessage(final HashMap<String, Object>, final String pObjMessage, final String pStrChannel)
    public void PNPublishMessage(final HashMap<String, Object> pObjMessage, final String pStrChannel)
    {
        pubnub.publish()
                //.message(Arrays.asList("hello", "there"))
                .message(pObjMessage)
                .channel(pStrChannel)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // handle publish result, status always present, result if successful
                        // status.isError to see if error happened
                        if (status.isError()) {
                            PNPublishMessage(pObjMessage, pStrChannel);
                        }
                    }
                });

        PubNubLog("PNPublishMessage",
                "Channel: ", pStrChannel,
                " Title: ", pObjMessage.get("title").toString(),
                " Fields: ",  pObjMessage.toString()
        );
    }

    public void PNSubscribeChannel(List<String> pStrChannel)
    {
        pubnub.subscribe()
                .channels(pStrChannel)
                .execute();

        PubNubLog("PNSubscribeChannel", pStrChannel.toString());
    }

    public void PNUnSubscribeChannel(List<String> pStrChannel)
    {
        pubnub.unsubscribe()
                .channels(pStrChannel)
                .execute();

        PubNubLog("PNUnSubscribeChannel", pStrChannel.toString());
    }

    public void PNDestroy()
    {
        pubnub.destroy();
        PubNubLog("PNDestroy", "Pubnub instance destroyed !");
    }

    public void PNRemoveListener()
    {
        SubscribeCallback subscribeCallback = new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        };

        pubnub.addListener(subscribeCallback);

        // some time later
        pubnub.removeListener(subscribeCallback);
    }

    public HashMap<String, Object> CreateOrderMessage(final String pStrTitle, final HashMap<String, Object> fields ,final String pStrObjectID, final int pIntuserID, final String pStrIsFree , final String pStrCreatedAt, final String pStrUpdatedAt)
    {
        HashMap<String, Object> map =  new HashMap<>();
        map.put("title", pStrTitle);
        map.put("fields", fields);
        map.put("objectID", pStrObjectID);
        map.put("userID", pIntuserID);
        map.put("createdAt", pStrCreatedAt);
        map.put("updatedAt", pStrUpdatedAt);
        map.put("isFree", pStrIsFree);

        return map;
    }

    public HashMap<String, Object> CreateMessage(final String pStrTitle, final HashMap<String, Object> fields ,final String pStrObjectID, final int pIntuserID, final String pStrCreatedAt, final String pStrUpdatedAt)
    {
        HashMap<String, Object> map =  new HashMap<>();
        map.put("title", pStrTitle);
        map.put("fields", fields);
        map.put("objectID", pStrObjectID);
        map.put("userID", pIntuserID);
        map.put("createdAt", pStrCreatedAt);
        map.put("updatedAt", pStrUpdatedAt);

        return map;
    }

    public HashMap<String, Object> CreateLocationMessage(final String pStrTitle, final HashMap<String, Object> fields ,final String pStrObjectID, final int pIntuserID)
    {
        HashMap<String, Object> map =  new HashMap<>();
        map.put("title", pStrTitle);
        map.put("fields", fields);
        map.put("objectID", pStrObjectID);
        map.put("userID", pIntuserID);

        return map;
    }

    public HashMap<String, Object> CreateBlockedMessage(final String pStrTitle ,final String pStrObjectID, final int pIntuserID, final int pIntBlockedID, final String pStrStatus)
    {
        HashMap<String, Object> map =  new HashMap<>();
        map.put("title", pStrTitle);
        map.put("objectID", pStrObjectID);
        map.put("userID", pIntuserID);
        map.put("blockedID", pIntBlockedID);
        map.put("status", pStrStatus);

        return map;
    }

    public JSONObject GetMessage(PNMessageResult message)
    {
        JSONObject jObjMessage = null;
        try {
            jObjMessage = new JSONObject(message.getMessage().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObjMessage;
    }

    public JSONObject GetFields(JSONObject message)
    {
        JSONObject jObjFields = null;
        try {
            jObjFields = (JSONObject) message.get("fields");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObjFields;
    }


    public void PubNubLog(String pStrKey, String... pStrValues)
    {
        String value = "";
        for (String pStrValue : pStrValues) {
            value += pStrValue;
        }
        Log.d(pStrKey,value);
    }
    // PUBNUB METHODS ENDS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
