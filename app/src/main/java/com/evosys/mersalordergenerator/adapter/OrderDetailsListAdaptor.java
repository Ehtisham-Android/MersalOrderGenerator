package com.evosys.mersalordergenerator.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evosys.mersalordergenerator.R;
import com.evosys.mersalordergenerator.model.OrderDetailsModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class OrderDetailsListAdaptor extends BaseAdapter {

        /*********** Declare Used Variables *********/
        private Activity activity;
        private ArrayList data;
        private static LayoutInflater inflater=null;
        public Resources res;
        private OrderDetailsModel tempValues = null;
        int i=0;


        private HashMap<TextView,CountDownTimer> counters;



        /*************  CustomAdapter Constructor *****************/
        public OrderDetailsListAdaptor(Activity a, ArrayList d, Resources resLocal) {

            /********** Take passed values **********/
            activity = a;
            data = d;
            res = resLocal;

            /***********  Layout inflator to call external xml layout () ***********/
            inflater = ( LayoutInflater )activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.counters = new HashMap<>();
        }


        /******** What is the size of Passed Arraylist Size ************/
        public int getCount() {

            if(data.size()<=0)
                return 1;
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        /********* Create a holder Class to contain inflated xml file elements *********/
        private static class ViewHolder{
            TextView CustomerOrderProductName,
                    CustomerOrderProductDetails,
                    CustomerOrderProductItemPrice,
                    CustomerOrderProductDeliveryPrice,
                    CustomerOrderRowAddress,
                    CustomerOrderRowTimer,
                    tv_OrderStatus;
        }

        /****** Depends upon data size called for each row , Create each ListView row *****/
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi = convertView;
            final ViewHolder holder;

            if(convertView == null){

                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.listview_orders_row, null);

                /****** View Holder Object to contain tabitem.xml file elements ******/
                holder = new ViewHolder();
                holder.CustomerOrderProductName = (TextView) vi.findViewById(R.id.tv_CustomerOrderRowProductName);
                holder.CustomerOrderProductDetails = (TextView) vi.findViewById(R.id.tv_CustomerOrderRowProductDetails);
                holder.CustomerOrderProductItemPrice = (TextView)vi.findViewById(R.id.tv_CustomerOrderRowProductItemPrice);
                holder.CustomerOrderProductDeliveryPrice = (TextView)vi.findViewById(R.id.tv_CustomerOrderRowProductDeliveryPrice);
                holder.CustomerOrderRowAddress = (TextView)vi.findViewById(R.id.tv_CustomerOrderRowAddress);
                holder.CustomerOrderRowTimer = (TextView)vi.findViewById(R.id.tv_CustomerOrderRowTimer);
                holder.tv_OrderStatus = (TextView)vi.findViewById(R.id.tv_OrderStatus);
                /************  Set holder with LayoutInflater ************/

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }


            // TIMER DATA /////////////////////////////////////////////////////////////////////////////
            ViewHolder holder2 = (ViewHolder) vi.getTag();
            final TextView tv = holder.CustomerOrderRowTimer;

            CountDownTimer cdt = counters.get(holder2.CustomerOrderRowTimer);
            if(cdt != null)
            {
                cdt.cancel();
                cdt = null;
            }
            // TIMER DATA /////////////////////////////////////////////////////////////////////////////


            if(data.size()<=0)
            {
                HideViews(holder);
            }
            else
            {
                /***** Get each Model object from Arraylist ********/
                tempValues = null;
                tempValues = ( OrderDetailsModel ) data.get( position );

                /************  Set Model values in Holder elements ***********/
                ShowViews(holder);
                holder.CustomerOrderProductName.setText(tempValues.getProductName());
                holder.CustomerOrderProductItemPrice.setText( GetStringFromResource(R.string.str_item_price) + " " + tempValues.getItemPrice() + " " + GetStringFromResource(R.string.str_sr));
                holder.CustomerOrderProductDeliveryPrice.setText( GetStringFromResource(R.string.str_deliverey_price) + " " +  tempValues.getDeliveryPrice() + " " + GetStringFromResource(R.string.str_sr));
                holder.CustomerOrderRowAddress.setText(tempValues.getOrderAddress());
                holder.tv_OrderStatus.setText(tempValues.getOrderStatus());
                holder.CustomerOrderProductDetails.setText(tempValues.getProductDetails());

                Log.d("TimerVal", (int)tempValues.getNeedApprovalTimer() + "");

                cdt = new CountDownTimer((int)tempValues.getNeedApprovalTimer(), 1000)
                {
                    @Override
                    public void onTick(long millisUntilFinished)
                    {
                        int days = 0;
                        int hours = 0;
                        int minutes = 0;
                        int seconds = 0;
                        String sDate = "";

                        if(millisUntilFinished > DateUtils.DAY_IN_MILLIS)
                        {
                            days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
                            sDate += days+"d";
                        }

                        millisUntilFinished -= (days*DateUtils.DAY_IN_MILLIS);

                        if(millisUntilFinished > DateUtils.HOUR_IN_MILLIS)
                        {
                            hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);
                        }

                        millisUntilFinished -= (hours*DateUtils.HOUR_IN_MILLIS);

                        if(millisUntilFinished > DateUtils.MINUTE_IN_MILLIS)
                        {
                            minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);
                        }

                        millisUntilFinished -= (minutes*DateUtils.MINUTE_IN_MILLIS);

                        if(millisUntilFinished > DateUtils.SECOND_IN_MILLIS)
                        {
                            seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);
                        }

                        sDate += " "+String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds);
                        tv.setText(sDate.trim());
                    }

                    @Override
                    public void onFinish() {
                        tv.setText("00:00");
                        //ViewOrdersActivity.GetAllOrderRecordLocalDB();
                    }
                };

                counters.put(tv, cdt);
                cdt.start();
            }
            return vi;
        }


    float roundTwoDecimals(double d, String criteria) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat twoDForm = new DecimalFormat(criteria, otherSymbols);
        return Float.valueOf(twoDForm.format(d));
    }


    private void HideViews(final ViewHolder holder)
    {
        holder.CustomerOrderProductName.setVisibility(View.GONE);
        holder.CustomerOrderProductItemPrice.setVisibility(View.GONE);
        holder.CustomerOrderProductDeliveryPrice.setVisibility(View.GONE);
        holder.CustomerOrderRowAddress.setVisibility(View.GONE);
        holder.CustomerOrderRowTimer.setVisibility(View.GONE);
    }

    private void ShowViews(final ViewHolder holder)
    {
        holder.CustomerOrderProductName.setVisibility(View.VISIBLE);
        holder.CustomerOrderProductName.setText("");
        holder.CustomerOrderProductItemPrice.setVisibility(View.VISIBLE);
        holder.CustomerOrderProductItemPrice.setText("");
        holder.CustomerOrderProductDeliveryPrice.setVisibility(View.VISIBLE);
        holder.CustomerOrderProductDeliveryPrice.setText("");
        holder.CustomerOrderRowAddress.setVisibility(View.VISIBLE);
        holder.CustomerOrderRowAddress.setText("");
        holder.CustomerOrderRowTimer.setVisibility(View.VISIBLE);
        holder.CustomerOrderRowTimer.setText("");
    }

    private String GetStringFromResource(int id)
    {
        return activity.getResources().getString(id);
    }

}
