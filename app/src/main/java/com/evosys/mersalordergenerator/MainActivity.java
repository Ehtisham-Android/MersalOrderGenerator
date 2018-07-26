package com.evosys.mersalordergenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.evosys.mersalordergenerator.adapter.DBAdapter;
import com.evosys.mersalordergenerator.definition.GlobalClass;
import com.evosys.mersalordergenerator.helperclasses.PubNubClass;
import com.evosys.mersalordergenerator.model.UserModel;
import com.evosys.mersalordergenerator.utils.DialogsUtils;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DBAdapter dbadapter;
    final ArrayList<UserModel> UsersArrayList = new ArrayList<>();
    Spinner spinner;
    Button btn_Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.sp_users);
        btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_Login.setEnabled(false);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformLogin();
                //Log.d("RandVal: ", GenerateRandomNumber(0, 3) + "");
            }
        });

        PubNubClass.initInstance();
        DialogsUtils.ResetInstance();
        DialogsUtils.InitializeDialogsUtils(MainActivity.this);

        InitializeDatabase();
        InitializeUsers();
        PopulateUsers();
        QBCreateSession();
        Log.d("RandVal: ", GenerateRandomNumber(0, 3) + "");
    }


    private void QBCreateSession() {
        DialogsUtils.getInstance().ShowLoadingDialog(GetStringFromResource(R.string.str_initializing_database));
        QBSettings.getInstance().init(getApplicationContext(), GlobalClass.APP_ID, GlobalClass.AUTH_KEY, GlobalClass.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(GlobalClass.ACCOUNT_KEY);

        btn_Login.setEnabled(true);
        DialogsUtils.getInstance().RemoveLoadingDialog();

//        QBAuth.createSession(new QBEntityCallback<QBSession>() {
//            @Override
//            public void onSuccess(QBSession session, Bundle params) {
//                btn_Login.setEnabled(true);
//                DialogsUtils.getInstance().RemoveLoadingDialog();
//            }
//
//            @Override
//            public void onError(QBResponseException error) {
//                // errors
//                Toast.makeText(getApplicationContext(), "Database not connected - reload app again", Toast.LENGTH_SHORT).show();
//                DialogsUtils.getInstance().RemoveLoadingDialog();
//            }
//        });
    }

    private void PerformLogin()
    {
        DialogsUtils.getInstance().ShowLoadingDialog(GetStringFromResource(R.string.str_Login));
        QBUsers.signIn(GlobalClass.UserCredentials(GetSelectedUser().getUserName(), GetSelectedUser().getPassowrd())).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                DialogsUtils.getInstance().RemoveLoadingDialog();
                GlobalClass.UserSession.setUserId(qbUser.getId());
                GlobalClass.UserSession.setUserName(qbUser.getFullName());
                GlobalClass.UserSession.setDestLocLat(GetSelectedUser().getDestLocLat());
                GlobalClass.UserSession.setDestLocLong(GetSelectedUser().getDestLocLong());
                GlobalClass.UserSession.setCity(GetSelectedUser().getCity());
                GetCustomerDetails(qbUser.getId());

                // regsitering pubnub customer related channels here ////////////////////////////////////////////////////////////////////////////////////////////
                PubNubClass.getInstance().PNSubscribeChannel(
                        Arrays.asList(
                                GlobalClass.PN_Allchannel,
                                GlobalClass.PN_customerschannel,
                                qbUser.getId().toString(),
                                "",
                                ""
                        )
                );
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            @Override
            public void onError(QBResponseException e) {
                DialogsUtils.getInstance().RemoveLoadingDialog();
                Toast.makeText(getApplicationContext(), "user login failed - try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetCustomerDetails(int UserId) {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.eq("user_id", UserId);

        QBCustomObjects.getObjects("customers", requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {

                if (customObjects.size() > 0) {
                    DialogsUtils.getInstance().RemoveLoadingDialog();
                    GlobalClass.UserSession.setUserRating(customObjects.get(0).get("Rating").toString());
                    ChangeActivity();
                }
            }

            @Override
            public void onError(QBResponseException errors) {
                DialogsUtils.getInstance().RemoveLoadingDialog();
                Toast.makeText(getApplicationContext(), "user login failed - try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String GetStringFromResource(int id)
    {
        return getResources().getString(id);
    }

    private void InitializeDatabase()
    {
        dbadapter = new DBAdapter(getApplicationContext());
        dbadapter.open();
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

        // Mohammad
        UserModel user2 = new UserModel();
        user2.setUserName("mimma77");
        user2.setPassowrd("12345678");
        user2.setDestLocLat(21.4804726);
        user2.setDestLocLong(39.2441568);
        user2.setCity("Jeddah");

        // Dr. Ahmed
        UserModel user3 = new UserModel();
        user3.setUserName("kattan");
        user3.setPassowrd("asdf1234");
        user3.setDestLocLat(24.815876);
        user3.setDestLocLong(46.6486406);
        user3.setCity("Riyadh");


        // Hassan
        UserModel user4 = new UserModel();
        user4.setUserName("hassan");
        user4.setPassowrd("asdf1234");
        user4.setDestLocLat(21.5669300);
        user4.setDestLocLong(39.7748710);
        user4.setCity("Makkah");
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

    private UserModel GetSelectedUser()
    {
        GlobalClass.WhichUser = spinner.getSelectedItemPosition();
        return UsersArrayList.get(spinner.getSelectedItemPosition());
    }

    private void PopulateUsers()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void ChangeActivity()
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private int GenerateRandomNumber(int start, int end)
    {
        // Start include
        // End exclude
        end = end;
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }
}
