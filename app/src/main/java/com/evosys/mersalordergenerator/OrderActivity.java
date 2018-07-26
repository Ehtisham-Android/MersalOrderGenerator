package com.evosys.mersalordergenerator;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evosys.mersalordergenerator.adapter.DBAdapter;
import com.evosys.mersalordergenerator.definition.GlobalClass;
import com.evosys.mersalordergenerator.helperclasses.PubNubClass;
import com.evosys.mersalordergenerator.model.OrderDetailsModel;
import com.evosys.mersalordergenerator.model.ProductModel;
import com.evosys.mersalordergenerator.model.UserModel;
import com.evosys.mersalordergenerator.utils.DialogsUtils;
import com.google.android.gms.maps.model.LatLng;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.customobjects.model.QBPermissions;
import com.quickblox.customobjects.model.QBPermissionsLevel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    Button btn_generateorder, btn_showorder;
    int gestprice = 15;
    DBAdapter dbadapter;

    final ArrayList<UserModel> UsersArrayList = new ArrayList<>();
    final ArrayList<String> CategoriesArrayList = new ArrayList<>();
    final ArrayList<ProductModel> ProductsArrayListJeddah = new ArrayList<>();
    final ArrayList<ProductModel> ProductsArrayListJedda = new ArrayList<>();
    final ArrayList<ProductModel> ProductsArrayListRiyadh = new ArrayList<>();
    final ArrayList<ProductModel> ProductsArrayListMakkah = new ArrayList<>();
    final ArrayList<Integer> PriceArray = new ArrayList<>();
    final OrderDetailsModel objOrderDetailsModel = new OrderDetailsModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        InitializePrices();
        InitializeUsers();
        InitializeCategory();
        InitializeProductJeddah();
        InitializeProductRiyadh();
        InitializeProductMakkah();
        InitializeProductJedda();
        InitializeDatabase();

        btn_generateorder = (Button) findViewById(R.id.btn_generateorder);
        btn_showorder = (Button) findViewById(R.id.btn_showorder);
        btn_showorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });
        btn_generateorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateOrders();
            }
        });
    }

    private void InitializePrices()
    {
        PriceArray.add(20);
        PriceArray.add(30);
        PriceArray.add(25);
        PriceArray.add(35);
        PriceArray.add(23);
        PriceArray.add(33);
        PriceArray.add(28);
    }


    private void InitializeUsers()
    {
        // Ehtisham
        UserModel user1 = new UserModel();
        user1.setUserName("mcustomer");
        user1.setPassowrd("12345678");
        user1.setDestLocLat(21.5374740);
        user1.setDestLocLong(39.2105520);
        user1.setCity("Jedda");
        user1.setUserId(22799480);

        // Mohammad
        UserModel user2 = new UserModel();
        user2.setUserName("mimma77");
        user2.setPassowrd("12345678");
//        user2.setDestLocLat(21.4804726);
//        user2.setDestLocLong(39.2441568);

        user2.setDestLocLat(21.6117090);
        user2.setDestLocLong(39.2153930);
        user2.setCity("Jeddah");
        user2.setUserId(123);

        // Dr. Ahmed
        UserModel user3 = new UserModel();
        user3.setUserName("kattan");
        user3.setPassowrd("asdf1234");
        user3.setDestLocLat(24.815876);
        user3.setDestLocLong(46.6486406);
        user3.setCity("Riyadh");
        user3.setUserId(23604000);

        // Hassan
        UserModel user4 = new UserModel();
        user4.setUserName("hassan");
        user4.setPassowrd("asdf1234");
        user4.setDestLocLat(21.5669300);
        user4.setDestLocLong(39.7748710);
        user4.setCity("Jeddah");
        user4.setUserId(28372709);


        // Tariq
        UserModel user5 = new UserModel();
        user5.setUserName("tariq555");
        user5.setPassowrd("asdf1234");
        user5.setDestLocLat(24.7980520);
        user5.setDestLocLong(46.6721180);
        user5.setCity("Riyadh");
        user5.setUserId(28490896);

        UsersArrayList.add(user1);
        UsersArrayList.add(user2);
        UsersArrayList.add(user3);
        UsersArrayList.add(user4);
        UsersArrayList.add(user5);
    }

    private void InitializeCategory()
    {
        CategoriesArrayList.add("Grocery");
        CategoriesArrayList.add("Electronics");
        //CategoriesArrayList.add("Stationary");
    }

    private void InitializeProductMakkah()
    {
//        ProductModel p1 = new ProductModel();
//        p1.setcity("Makkah");
//        p1.setName("طلب من صيدلية");
//        p1.setDescription("1 panadol tablet");
//        p1.setprice(8);
//        p1.setSourceLocLat(21.5787304);
//        p1.setSourceLocLong(39.1455578);

        ProductModel p2 = new ProductModel();
        p2.setcity("Makkah");
        p2.setName("طلب من سوبر ماركيت");
        p2.setDescription("1 galaxy choclate box");
        p2.setprice(35);
        p2.setSourceLocLat(21.464239);
        p2.setSourceLocLong(39.1549033);


        ProductModel p3 = new ProductModel();
        p3.setcity("Makkah");
        p3.setName("طلب من سوبر ماركيت");
        p3.setDescription("Nido full cream milk powder 1.8 KG");
        p3.setprice(45);
        p3.setSourceLocLat(21.464239);
        p3.setSourceLocLong(39.1549033);


        ProductModel p4 = new ProductModel();
        p4.setcity("Makkah");
        p4.setName("طلب من سوبر ماركيت");
        p4.setDescription("Digestive biscuits 2 packs 500Grams");
        p4.setprice(32);
        p4.setSourceLocLat(21.464239);
        p4.setSourceLocLong(39.1549033);



        //ProductsArrayListMakkah.add(p1);
        ProductsArrayListMakkah.add(p2);
        ProductsArrayListMakkah.add(p3);
        ProductsArrayListMakkah.add(p4);
    }

    private void InitializeProductJedda()
    {
        ProductModel p1 = new ProductModel();
        p1.setcity("Jedda");
        p1.setName("HDMI cable");
        p1.setDescription("1 HDMI cable for samsung s7 edge");
        p1.setprice(45);
        p1.setSourceLocLat(21.5978533);
        p1.setSourceLocLong(39.0730336);


        ProductsArrayListJedda.add(p1);
    }

    private void InitializeProductJeddah()
    {
        ProductModel p1 = new ProductModel();
        p1.setcity("Jeddah");
        p1.setName("طلب من بنده");
        p1.setDescription("بسكوت دايجيستف ٢٥٠ جرام ");
        p1.setprice(8);
        p1.setSourceLocLat(21.5787304);
        p1.setSourceLocLong(39.1455578);

        ProductModel p2 = new ProductModel();
        p2.setcity("Jeddah");
        p2.setName("طلب من بنده");
        p2.setDescription("شوكولاته جالاكسي جواهر ٦٥٠ جرام");
        p2.setprice(40);
        p2.setSourceLocLat(21.464239);
        p2.setSourceLocLong(39.1549033);

        ProductModel p3 = new ProductModel();
        p3.setcity("Jeddah");
        p3.setName("طلب من بنده");
        p3.setDescription("مسحوق تايد للغسيل باور بلس مركز ٦ كيلو ");
        p3.setprice(45);
        p3.setSourceLocLat(21.5678696);
        p3.setSourceLocLong(39.1086954);

        ProductModel p4 = new ProductModel();
        p4.setcity("Jeddah");
        p4.setName("طلب من بنده");
        p4.setDescription("بسكوت اوريو علبه كامله ");
        p4.setprice(12);
        p4.setSourceLocLat(21.5473434);
        p4.setSourceLocLong(39.1334848);

        ProductModel p5 = new ProductModel();
        p5.setcity("Jeddah");
        p5.setName("طلب من بنده");
        p5.setDescription("شوكولاته سائلة نوتيلا ");
        p5.setprice(30);
        p5.setSourceLocLat(21.632421);
        p5.setSourceLocLong(39.0874901);

        ProductModel p6 = new ProductModel();
        p6.setcity("Jeddah");
        p6.setName("طلب من إكسترا");
        p6.setDescription("شاحن ايفون ٧ (بس الكيبل ) ");
        p6.setprice(89);
        p6.setSourceLocLat(21.5978533);
        p6.setSourceLocLong(39.0730336);

        ProductModel p7 = new ProductModel();
        p7.setcity("Jeddah");
        p7.setName("طلب من إكسترا");
        p7.setDescription("اس بي اس شاحن سياره (رقم المنتج 00195555) ");
        p7.setprice(60);
        p7.setSourceLocLat(21.5600525);
        p7.setSourceLocLong(39.1820342);

        ProductModel p8 = new ProductModel();
        p8.setcity("Jeddah");
        p8.setName("طلب من إكسترا");
        p8.setDescription("سوبريم بطاريه احتياطيه ٥٢٠٠ مل اسود (رقم المنتج ٠٠١٧٨٠٠٢) ");
        p8.setprice(49);
        p8.setSourceLocLat(21.6019463);
        p8.setSourceLocLong(39.1223106);


        ProductModel p9 = new ProductModel();
        p9.setcity("Jeddah");
        p9.setName("طلب من إكسترا");
        p9.setDescription("اس بي اس حامل للجوال بالسياره، لون اسود (٠٠١٩٤٤١٦)");
        p9.setprice(69);
        p9.setSourceLocLat(21.4909874);
        p9.setSourceLocLong(39.1530286);

        ProductModel p10 = new ProductModel();
        p10.setcity("Jeddah");
        p10.setName("طلب من إكسترا");
        p10.setDescription("شاحن منزلي ثلاثي يو اس بي مع مايكرو كيبل (رقم المنتج ٠٠١٦٧٣٤٠)");
        p10.setprice(79);
        p10.setSourceLocLat(21.5574984);
        p10.setSourceLocLong(39.1116546);

//        ProductModel p11 = new ProductModel();
//        p11.setcity("Jeddah");
//        p11.setName("طلب من جرير ");
//        p11.setDescription("");
//        p11.setprice(0);
//        p11.setSourceLocLat(21.5650334);
//        p11.setSourceLocLong(39.1528006);
//
//        ProductModel p12 = new ProductModel();
//        p12.setcity("Jeddah");
//        p12.setName("طلب من جرير ");
//        p12.setDescription("");
//        p12.setprice(0);
//        p12.setSourceLocLat(21.5751584);
//        p12.setSourceLocLong(39.0765536);
//
//
//        ProductModel p13 = new ProductModel();
//        p13.setcity("Jeddah");
//        p13.setName("طلب من جرير ");
//        p13.setDescription("");
//        p13.setprice(0);
//        p13.setSourceLocLat(21.6397033);
//        p13.setSourceLocLong(39.0623226);
//
//        ProductModel p14 = new ProductModel();
//        p14.setcity("Jeddah");
//        p14.setName("طلب من جرير ");
//        p14.setDescription("");
//        p14.setprice(0);
//        p14.setSourceLocLat(21.4827994);
//        p14.setSourceLocLong(39.1727026);
//
//        ProductModel p15 = new ProductModel();
//        p15.setcity("Jeddah");
//        p15.setName("طلب من جرير ");
//        p15.setDescription("");
//        p15.setprice(0);
//        p15.setSourceLocLat(21.4827994);
//        p15.setSourceLocLong(39.172702);


        ProductsArrayListJeddah.add(p1);
        ProductsArrayListJeddah.add(p2);
        ProductsArrayListJeddah.add(p3);
        ProductsArrayListJeddah.add(p4);
        ProductsArrayListJeddah.add(p5);
        ProductsArrayListJeddah.add(p6);
        ProductsArrayListJeddah.add(p7);
        ProductsArrayListJeddah.add(p8);
        ProductsArrayListJeddah.add(p9);
        ProductsArrayListJeddah.add(p10);
//        ProductsArrayListJeddah.add(p11);
//        ProductsArrayListJeddah.add(p12);
//        ProductsArrayListJeddah.add(p13);
//        ProductsArrayListJeddah.add(p14);
//        ProductsArrayListJeddah.add(p15);
    }

    private void InitializeProductRiyadh() {

        ProductModel p1 = new ProductModel();
        p1.setcity("Riyadh");
        p1.setName("طلب من بنده");
        p1.setDescription("بسكوت دايجيستف ٢٥٠ جرام ");
        p1.setprice(8);
        p1.setSourceLocLat(24.7901675);
        p1.setSourceLocLong(46.6950922);

        ProductModel p2 = new ProductModel();
        p2.setcity("Riyadh");
        p2.setName("طلب من بنده");
        p2.setDescription("شوكولاته جالاكسي جواهر ٦٥٠ جرام");
        p2.setprice(40);
        p2.setSourceLocLat(24.658235);
        p2.setSourceLocLong(46.7124963);

        ProductModel p3 = new ProductModel();
        p3.setcity("Riyadh");
        p3.setName("طلب من بنده");
        p3.setDescription("مسحوق تايد للغسيل باور بلس مركز ٦ كيلو ");
        p3.setprice(45);
        p3.setSourceLocLat(24.7531035);
        p3.setSourceLocLong(46.5151257);

        ProductModel p4 = new ProductModel();
        p4.setcity("Riyadh");
        p4.setName("طلب من بنده");
        p4.setDescription("بسكوت اوريو علبه كامله ");
        p4.setprice(12);
        p4.setSourceLocLat(24.5366255);
        p4.setSourceLocLong(46.5865385);

        ProductModel p5 = new ProductModel();
        p5.setcity("Riyadh");
        p5.setName("طلب من بنده");
        p5.setDescription("شوكولاته سائلة نوتيلا ");
        p5.setprice(30);
        p5.setSourceLocLat(24.6641847);
        p5.setSourceLocLong(46.6122156);

        ProductModel p6 = new ProductModel();
        p6.setcity("Riyadh");
        p6.setName("طلب من إكسترا");
        p6.setDescription("شاحن ايفون ٧ (بس الكيبل ) ");
        p6.setprice(89);
        p6.setSourceLocLat(24.8066627);
        p6.setSourceLocLong(46.5929896);

        ProductModel p7 = new ProductModel();
        p7.setcity("Riyadh");
        p7.setName("طلب من إكسترا");
        p7.setDescription("اس بي اس شاحن سياره (رقم المنتج 00195555) ");
        p7.setprice(60);
        p7.setSourceLocLat(24.5739418);
        p7.setSourceLocLong(46.5389736);

        ProductModel p8 = new ProductModel();
        p8.setcity("Riyadh");
        p8.setName("طلب من إكسترا");
        p8.setDescription("سوبريم بطاريه احتياطيه ٥٢٠٠ مل اسود (رقم المنتج ٠٠١٧٨٠٠٢) ");
        p8.setprice(49);
        p8.setSourceLocLat(24.8107642);
        p8.setSourceLocLong(46.4705981);

        ProductModel p9 = new ProductModel();
        p9.setcity("Riyadh");
        p9.setName("طلب من إكسترا");
        p9.setDescription("اس بي اس حامل للجوال بالسياره، لون اسود (٠٠١٩٤٤١٦)");
        p9.setprice(69);
        p9.setSourceLocLat(24.7824337);
        p9.setSourceLocLong(46.6602156);

        ProductModel p10 = new ProductModel();
        p10.setcity("Riyadh");
        p10.setName("طلب من إكسترا");
        p10.setDescription("شاحن منزلي ثلاثي يو اس بي مع مايكرو كيبل (رقم المنتج ٠٠١٦٧٣٤٠)");
        p10.setprice(79);
        p10.setSourceLocLat(24.7224747);
        p10.setSourceLocLong(46.6216206);


//        ProductModel p11 = new ProductModel();
//        p11.setcity("Riyadh");
//        p11.setName("طلب من جرير ");
//        p11.setDescription("");
//        p11.setprice(0);
//        p11.setSourceLocLat(24.6938078);
//        p11.setSourceLocLong(46.6001056);
//
//        ProductModel p12 = new ProductModel();
//        p12.setcity("Riyadh");
//        p12.setName("طلب من جرير ");
//        p12.setDescription("");
//        p12.setprice(0);
//        p12.setSourceLocLat(24.7220747);
//        p12.setSourceLocLong(46.7052886);
//
//        ProductModel p13 = new ProductModel();
//        p13.setcity("Riyadh");
//        p13.setName("طلب من جرير ");
//        p13.setDescription("");
//        p13.setprice(0);
//        p13.setSourceLocLat(24.8180487);
//        p13.setSourceLocLong(46.6168426);
//
//        ProductModel p14 = new ProductModel();
//        p14.setcity("Riyadh");
//        p14.setName("طلب من جرير ");
//        p14.setDescription("");
//        p14.setprice(0);
//        p14.setSourceLocLat(24.7899599);
//        p14.setSourceLocLong(46.7381802);
//
//        ProductModel p15 = new ProductModel();
//        p15.setcity("Riyadh");
//        p15.setName("طلب من جرير ");
//        p15.setDescription("");
//        p15.setprice(0);
//        p15.setSourceLocLat(24.7518258);
//        p15.setSourceLocLong(46.5138567);


        ProductsArrayListRiyadh.add(p1);
        ProductsArrayListRiyadh.add(p2);
        ProductsArrayListRiyadh.add(p3);
        ProductsArrayListRiyadh.add(p4);
        ProductsArrayListRiyadh.add(p5);
        ProductsArrayListRiyadh.add(p10);
        ProductsArrayListRiyadh.add(p10);
        ProductsArrayListRiyadh.add(p10);
        ProductsArrayListRiyadh.add(p10);
        ProductsArrayListRiyadh.add(p10);
    }

    private int GetCategoryCount()
    {
      return CategoriesArrayList.size();
    }

    private String GetCategoryItem(int index)
    {
        return CategoriesArrayList.get(index);
    }

    private ProductModel GetProductItemJeddah(int index)
    {
        return ProductsArrayListJeddah.get(index);
    }

    private ProductModel GetProductItemJedda(int index)
    {
        return ProductsArrayListJedda.get(index);
    }

    private ProductModel GetProductItemRiyadh(int index)
    {
        return ProductsArrayListRiyadh.get(index);
    }

    private ProductModel GetProductItemMakkah(int index)
    {
        return ProductsArrayListMakkah.get(index);
    }

    private int GetPrice(int index)
    {
        return PriceArray.get(index);
    }

    private UserModel GetUser()
    {
        return UsersArrayList.get(GlobalClass.WhichUser);
    }

    private int GenerateRandomNumber(int start, int end)
    {
        // Start include
        // End exclude
        end = end;
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }

    private void GenerateOrders()
    {
        DialogsUtils.getInstance().ShowLoadingDialog(GetStringFromResource(R.string.str_processing));
        ProductModel objProductModel = new ProductModel();
        int category = GenerateRandomNumber(0, GetCategoryCount());
        if(GlobalClass.UserSession.getCity().equalsIgnoreCase("Jeddah"))
        {
            switch (category)
            {
                case 0:
                    objProductModel = GetProductItemJeddah(GenerateRandomNumber(0, 5));
                    break;
                case 1:
                    objProductModel = GetProductItemJeddah(GenerateRandomNumber(5, 10));
                    break;
                case 2:
                    objProductModel = GetProductItemJeddah(GenerateRandomNumber(10, 15));
                    break;
            }

        }
        else if(GlobalClass.UserSession.getCity().equalsIgnoreCase("Riyadh")){
            switch (category)
            {
                case 0:
                    objProductModel = GetProductItemRiyadh(GenerateRandomNumber(0, 5));
                    break;
                case 1:
                    objProductModel = GetProductItemRiyadh(GenerateRandomNumber(5, 10));
                    break;
                case 2:
                    objProductModel = GetProductItemRiyadh(GenerateRandomNumber(10, 15));
                    break;
            }
        }
        else if(GlobalClass.UserSession.getCity().equalsIgnoreCase("Makkah")){
            switch (category)
            {
                case 0:
                    objProductModel = GetProductItemMakkah(GenerateRandomNumber(0, 2));
                    break;
                case 1:
                    objProductModel = GetProductItemMakkah(GenerateRandomNumber(0, 2));
                    break;
            }

        }
        else if(GlobalClass.UserSession.getCity().equalsIgnoreCase("Jedda")){
            switch (category)
            {
                case 0:
                    objProductModel = GetProductItemJedda(0);
                    break;
                case 1:
                    objProductModel = GetProductItemJedda(0);
                    break;
            }

        }
        else{
            // error
        }

        objOrderDetailsModel.setUserID(GetUser().getUserId());
        objOrderDetailsModel.setProductName(objProductModel.getName());
        objOrderDetailsModel.setProductDetails(objProductModel.getDescription());
        objOrderDetailsModel.setItemPrice(objProductModel.getprice());
        // Calculate Deliverey price here
        //objOrderDetailsModel.setDeliveryPrice();
        objOrderDetailsModel.setProductCategory(GetCategoryItem(category));
        objOrderDetailsModel.setDistanceValue(10000);
        objOrderDetailsModel.setTimer(1440);
        objOrderDetailsModel.setAwardedValue(0);
        objOrderDetailsModel.setPushedTime(getDateTime());
        objOrderDetailsModel.setIsFree(false);

        objOrderDetailsModel.setOrderDestinationLong(GlobalClass.UserSession.getDestLocLong());
        objOrderDetailsModel.setOrderDestinationLat(GlobalClass.UserSession.getDestLocLat());
        objOrderDetailsModel.setOrderSourceLong(objProductModel.getSourceLocLong());
        objOrderDetailsModel.setOrderSourceLat(objProductModel.getSourceLocLat());
        objOrderDetailsModel.setOrderStatus("NeedApproval");
        objOrderDetailsModel.setCustomerRating(GlobalClass.UserSession.getUserRating());


        getDirection(GlobalClass.UserSession.getDestLocLat(),
                GlobalClass.UserSession.getDestLocLong(),
                objProductModel.getSourceLocLat(),
                objProductModel.getSourceLocLong(), objOrderDetailsModel);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void QBInsertOrder(OrderDetailsModel pOrderDetailsModel)
    {
        QBPermissions permissions = new QBPermissions();
        QBCustomObject object = new QBCustomObject();
        object.putInteger("user_id", pOrderDetailsModel.getUserID());
        object.putString("name", pOrderDetailsModel.getProductName());
        object.putString("explanations", pOrderDetailsModel.getProductDetails());
        object.putInteger("itemPrice", pOrderDetailsModel.getItemPrice());
        //object.putInteger("deliveryPrice", gestprice);
        object.putInteger("deliveryPrice", GetPrice(GenerateRandomNumber(0, 7)));
        object.putString("category", pOrderDetailsModel.getProductCategory());
        object.putInteger("DistanceRange", pOrderDetailsModel.getDistanceValue());
        object.putInteger("timer", pOrderDetailsModel.getTimer());
        object.putString("pushedTime", pOrderDetailsModel.getPushedTime());
        object.putInteger("AwardedAmount", pOrderDetailsModel.getAwardedValue());
        object.putBoolean("isFree", pOrderDetailsModel.getIsFree());
        StringifyArrayList<String> coodinatesArray = new StringifyArrayList<>();
        coodinatesArray.add(String.valueOf(pOrderDetailsModel.getOrderDestinationLong()));
        coodinatesArray.add(String.valueOf(pOrderDetailsModel.getOrderDestinationLat()));
        coodinatesArray.add(String.valueOf(pOrderDetailsModel.getOrderSourceLong()));
        coodinatesArray.add(String.valueOf(pOrderDetailsModel.getOrderSourceLat()));
        object.putArray("coordinates", coodinatesArray);
        object.putString("orderStatus", pOrderDetailsModel.getOrderStatus());
        object.putString("CustomerTotalRating", pOrderDetailsModel.getCustomerRating());
        object.setClassName("order");

        ArrayList<String> usersTags = new ArrayList<>();
        usersTags.add("driver");
        usersTags.add("Customer");
        permissions.setDeletePermission(QBPermissionsLevel.OPEN_FOR_GROUPS, usersTags);
        permissions.setUpdatePermission(QBPermissionsLevel.OPEN);
        permissions.setDeletePermission(QBPermissionsLevel.OPEN);
        object.setPermission(permissions);


        QBCustomObjects.createObject(object).performAsync(new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(final QBCustomObject createdObject, Bundle params) {

                    PubNubClass.getInstance().PNPublishMessage(PubNubClass.getInstance().CreateOrderMessage(
                            GlobalClass.PN_Title_order,
                            createdObject.getFields(),
                            createdObject.getCustomObjectId(),
                            createdObject.getUserId(),
                            String.valueOf("false"),
                            createdObject.getCreatedAt().toString(),
                            String.valueOf(createdObject.getUpdatedAt().getTime())
                    ), GlobalClass.PN_driverschannel);

                    dbadapter.InsertNeedApprovalOrder(String.valueOf(createdObject.getUserId()),
                            createdObject.getCustomObjectId(),
                            createdObject.get("deliveryPrice").toString(),
                            createdObject.get("name").toString(),
                            createdObject.get("explanations").toString(),
                            createdObject.get("itemPrice").toString(),
                            null,
                            createdObject.get("category").toString(),
                            createdObject.get("orderStatus").toString(),
                            "",
                            "",
                            "",
                            //getDateTime(),
                            String.valueOf(createdObject.getUpdatedAt().getTime()),
                            String.valueOf("1440"),
                            createdObject.get("CustomerTotalRating").toString(),
                            createdObject.getArray("coordinates").get(1).toString(), // destination lat
                            createdObject.getArray("coordinates").get(0).toString(), // destination long
                            createdObject.get("isFree").toString(),
                            createdObject.getArray("coordinates").get(3).toString(), // source lat
                            createdObject.getArray("coordinates").get(2).toString(), // source long
                            createdObject.get("AwardedAmount").toString()
                    );

                    DialogsUtils.getInstance().QBSendPushNotification(GlobalClass.UserSession.getUserName(), createdObject);
                    DialogsUtils.getInstance().RemoveLoadingDialog();
                    Toast.makeText(getApplicationContext(), "Order posted successfuly", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d("PlaceAnOrderFragment", "Record Insertion Error");
            }
        });
    }

    private void InitializeDatabase()
    {
        dbadapter = new DBAdapter(getApplicationContext());
        dbadapter.open();
    }

    // GET DISTANCE AND TIME ///////////////////////////////////////////////////////////////////////
    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyD5lqkxqGzphPF2F8BTP0gxJgUQQIeDINY");
        return urlString.toString();
    }

    private void getDirection(final double fromLatitude, final double fromLongitude, final double toLatitude, final double toLongitude, final OrderDetailsModel objOrderDetailsModel) {
        //Getting the URL
        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);

        //Showing a dialog till we get the route
        //final ProgressDialog loading = ProgressDialog.show(this, GetStringFromResource(R.string.str_gettingroute), GetStringFromResource(R.string.str_please_wait), false, false);

        //Creating a string request
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //loading.dismiss();
//                        //Calling the method drawPath to draw the path
//                        Log.d("URLRes", response);
//                        drawPath(response, objOrderDetailsModel);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //loading.dismiss();
//                        Toast.makeText(getApplicationContext(), "Error getting estimated values", Toast.LENGTH_LONG).show();
//                        DialogsUtils.getInstance().RemoveLoadingDialog();
//                    }
//                });
//
//        //Adding the request to request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
    }


    public void drawPath(String result, OrderDetailsModel objOrderDetailsModel) {

        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            String time = routeArray.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
            float countdis = 0.0f;
            for (int i = 0; i < list.size(); i++) {
                float[] results = new float[1];
                LatLng from1 = list.get(i);
                LatLng to1;
                if (i < list.size() - 1) {
                    to1 = list.get(i + 1);
                } else {
                    to1 = list.get(i);
                }

                Location.distanceBetween(from1.latitude, from1.longitude, to1.latitude, to1.longitude, results);
                countdis += results[0];
            }


            // Getting estimated price
            float estdist = roundTwoDecimals(countdis / 1000, "#.#");
            int estprice = CalculateFare(estdist);
            if (estprice < 15) {
                estprice = 15;
            }

            gestprice = estprice;
            // Upload Order
            QBInsertOrder(objOrderDetailsModel);
            // Show values on top labels ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        } catch (JSONException e) {
            Log.d("DistResult", e.getMessage());
        }
    }

    float roundTwoDecimals(float d, String criteria) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat twoDForm = new DecimalFormat(criteria, otherSymbols);
        return Float.valueOf(twoDForm.format(d));
    }

    private int CalculateFare(float distance)
    {
        float temp = distance;
        int counter = 0;
        float price = 0;


        while(temp > 0)
        {
            counter++;
            if(counter == 1) {
                if(temp < 5)
                {
                    price = temp * 4;
                    temp = 0;
                    counter = 0;
                }else{
                    temp = temp - 5;
                    price = 20;
                }
            }else if(counter == 2)
            {
                if(temp > 5)
                {
                    price = price + 15;
                    temp = temp - 5;
                }else{
                    price = price + (temp * 3);
                    temp = 0;
                    counter = 0;
                }

            }else if(counter == 3)
            {
                if(temp > 5)
                {
                    price = price + 10;
                    temp = temp - 5;
                }else{
                    price = price + (temp * 2);
                    temp = 0;
                    counter = 0;
                }

            }else if(counter == 4)
            {
                if(temp > 15)
                {
                    price = price + 22.5f;
                    temp = temp - 15;
                }else{
                    price = price + (temp * 1.5f);
                    temp = 0;
                    counter = 0;
                }

            }else if(counter == 5)
            {
                if(temp > 20)
                {
                    price = price + 25;
                    temp = temp - 20;
                }else{
                    price = price + (temp * 1.25f);
                    temp = 0;
                    counter = 0;
                }

            }else if(counter == 6)
            {
                if(temp >= 1)
                {
                    price = price + temp;
                    temp = 0;
                    counter = 0;
                }
            }
        }

        return (int) price;
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private String GetStringFromResource(int id)
    {
        return getResources().getString(id);
    }

    private void ChangeActivity()
    {
        Intent intent = new Intent(this, ViewOrdersActivity.class);
        startActivity(intent);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
