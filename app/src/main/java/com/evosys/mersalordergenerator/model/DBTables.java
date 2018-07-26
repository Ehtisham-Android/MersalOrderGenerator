package com.evosys.mersalordergenerator.model;

public class DBTables {

    public static final class allTables
    {
        // Orders table ////////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_ORDERS                    = "orderss";
        public static final String ORDER_CUSTOM_OBJ_ID                  = "_id";
        public static final String ORDER_USER_ID                        = "user_id";
        public static final String ORDER_DELIVERY_PRICE                 = "deliveryPrice";
        public static final String ORDER_NAME                           = "name";
        public static final String ORDER_DETAILS                        = "explanations";
        public static final String ORDER_ITEM_PRICE                     = "itemPrice";
        public static final String ORDER_IMAGE                          = "image";
        public static final String ORDER_CATEGORY                       = "category";
        public static final String ORDER_STATUS                         = "orderStatus";
        public static final String ORDER_DRIVER_USER_ID                 = "driverUserId";
        public static final String ORDER_DRIVER_RATING                  = "driverRating";
        public static final String ORDER_CUSTOMER_RATING                = "customerRating";
        public static final String ORDER_PUSHED_TIME                    = "pushedTime";
        public static final String ORDER_TIMER                          = "timer";
        public static final String ORDER_CUSTOMER_TOTAL_RATING          = "CustomerTotalRating";
        public static final String ORDER_APPROVAL_TIME                  = "orderapprovaltime";

        // temp fields
        public static final String ORDER_IS_FREE                        = "isFree";
        public static final String ORDER_AWARDED_AMOUNT                 = "orderawardedamount";

        // local fields only
        public static final String ORDER_UPDATED                        = "orderupdated";
        public static final String ORDER_DEST_LAT                       = "orderDestLat";
        public static final String ORDER_DEST_LONG                      = "orderDestLong";
        public static final String ORDER_SOURCE_LAT                     = "orderSourceLat";
        public static final String ORDER_SOURCE_LONG                    = "orderSourceLong";
        public static final String ORDER_DISTANCE_RANGE                 = "DistanceRange";

        // Drivers table ///////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_DRIVERS                   = "driver";
        public static final String DRIVER_CUSTOM_OBJECT_ID              = "_id";
        public static final String DRIVER_USER_ID                       = "user_id";
        public static final String DRIVER_PLATE_NO                      = "plateNo";
        public static final String DRIVER_ID_NUMBER                     = "IDNumber";
        public static final String DRIVER_MOBILE_NO                     = "mobileNo";
        public static final String DRIVER_NAME                          = "name";
        public static final String DRIVER_EMAIL                         = "driverEmail";
        public static final String DRIVER_IS_ACCOUNT_VERIFIED           = "isAccountVerified";
        public static final String DRIVER_IS_PHONE_VERIFIED             = "isPhoneVerified";
        public static final String DRIVER_RATING                        = "Rating";
        public static final String DRIVER_RATE_COUNT                    = "RateCount";
        public static final String DRIVER_IS_ONLINE                     = "isOnline";
        public static final String DRIVER_BLOB_ID                       = "blobID";
        public static final String DRIVER_PLATFORM                      = "Platform";
        public static final String DRIVER_IMAGE                         = "DriverProfilePic";
        public static final String DRIVER_CARPIC                        = "CarPic";
        public static final String DRIVER_IDPIC                         = "IdPic";
        public static final String DRIVER_LICENSE                       = "LicensePic";
        public static final String DRIVER_LATUTUDE                      = "Altitude";
        public static final String DRIVER_LONGITUDE                     = "Longitude";
        public static final String DRIVER_IBAN                          = "IBAN";

        // Customers table /////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_CUSTOMERS                 = "customers";
        public static final String CUSTOMER_CUSTOM_OBJECT_ID            = "_id";
        public static final String CUSTOMER_USER_ID                     = "user_id";
        public static final String CUSTOMER_IS_VALIDATED                = "isvalidated";
        public static final String CUSTOMER_RATING                      = "Rating";
        public static final String CUSTOMER_RATE_COUNT                  = "RateCount";
        public static final String CUSTOMER_BLOB_ID                     = "BlobId";
        public static final String CUSTOMER_IMAGE                       = "image";
        public static final String CUSTOMER_PLATFORM                    = "Platform";
        public static final String CUSTOMER_NAME                        = "name";
        public static final String CUSTOMER_EMAIL                       = "customeremail";
        public static final String CUSTOMER_MOBILE_NO                   = "MobileNo";

        // TEMPORARY OFFERS VALUES /////////////////////////////////////////////////////////////////
        public static final String CUSTOMER_FREE_ORDERS_COUNT           = "freeorderscount";
        ////////////////////////////////////////////////////////////////////////////////////////////

        // Blocked User table //////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_BLOCKEDUSERS              = "blockedUsers";
        public static final String BLOCKEDUSERS_USER_ID                 = "user_id";
        public static final String BLOCKEDUSERS_CUSTOM_OBJECT_ID        = "_id";
        public static final String BLOCKEDUSERS_BLOCKEDID               = "blockedID";
        public static final String BLOCKEDUSERS_REMARKS                 = "Remarks";


        // Wallet table ////////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_WALLET                    = "Wallet";
        public static final String WALLET_OBJECT_ID                     = "walletObjectId";
        public static final String WALLET_DRIVER_ID                     = "walletDriverId";
        public static final String WALLET_ORDERS_COUNT                  = "walletOrdersCount";
        public static final String WALLET_AMOUNT                        = "walletAmount";
        public static final String WALLET_REDEEM_STATUS                 = "walletRedeemStatus";
        public static final String WALLET_REDEEM_DATE                   = "walletRedeemDate";
        public static final String WALLET_CREATED_AT                    = "walletCreatedAt";


        // IBAN table //////////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_IBAN_INFO                 = "IbanInfo";
        public static final String IBAN_INFO_OBJECT_ID                  = "ibaninfoobjectid";
        public static final String IBAN_INFO_NAME                       = "ibaninfoname";
        public static final String IBAN_INFO_BANK_NAME                  = "ibaninfobankname";
        public static final String IBAN_INFO_BRANCH_NAME                = "ibaninfobranchname";
        public static final String IBAN_INFO_IBAN_NUMBER                = "ibaninfoibannumber";


        // CONTACTS table //////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_CONTACTS                  = "contacts";
        public static final String CONTACTS_ROW_ID                      = "contactsrowid";
        public static final String CONTACTS_OBJECT_ID                   = "contactsobjectid";
        public static final String CONTACTS_USER_ID                     = "contactsuserid";
        public static final String CONTACTS_NUMBER                      = "contactsnumber";


        // SourceLocations table ///////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_SOURCE_LOC                 = "TABLE_NAME_SOURCE_LOC";
        public static final String SOURCE_LOC_ROW_ID                     = "SOURCE_LOC_ROW_ID";
        public static final String SOURCE_LOC_SOURCE_LAT                 = "SOURCE_LOC_SOURCE_LAT";
        public static final String SOURCE_LOC_SOURCE_LONG                = "SOURCE_LOC_SOURCE_LONG";

        // DestinationLocations table //////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_DEST_LOC                   = "TABLE_NAME_DEST_LOC";
        public static final String DEST_LOC_ROW_ID                       = "DEST_LOC_ROW_ID";
        public static final String DEST_LOC_SOURCE_LAT                   = "DEST_LOC_SOURCE_LAT";
        public static final String DEST_LOC_SOURCE_LONG                  = "DEST_LOC_SOURCE_LONG";

        // PRODUCTS table //////////////////////////////////////////////////////////////////////////
        public static final String TABLE_NAME_PRODUCTS                   = "TABLE_NAME_PRODUCTS";
        public static final String PRODUCTS_ROW_ID                       = "PRODUCTS_ROW_ID";
        public static final String PRODUCTS_NAME                         = "PRODUCTS_NAME";
        public static final String PRODUCTS_CATEGORY                     = "PRODUCTS_CATEGORY";
        public static final String PRODUCTS_PRICE                        = "PRODUCTS_PRICE";

    }
}
