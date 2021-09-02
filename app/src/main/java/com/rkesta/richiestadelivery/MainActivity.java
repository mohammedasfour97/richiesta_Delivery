package com.rkesta.richiestadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rkesta.richiestadelivery.adapter.OrdersBaseAdapter;
import com.rkesta.richiestadelivery.api.WebService;
import com.rkesta.richiestadelivery.app.constant;
import com.rkesta.richiestadelivery.location_service.LocationService;
import com.rkesta.richiestadelivery.model.order_details_model;
import com.rkesta.richiestadelivery.model.order_model;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiestadelivery.app.constant.Driver_ID;
import static com.rkesta.richiestadelivery.app.constant.is_locationaccepted;
import static com.rkesta.richiestadelivery.util.utility.goToNotificationSettings;
import static com.rkesta.richiestadelivery.util.utility.openSettings;

public class MainActivity extends AppCompatActivity {
    private RecyclerView RV_Orders;
    private ImageButton IB_Refresh;
    private ImageButton IB_logout;
    private ImageButton IB_notificationSetting;

    private OrdersBaseAdapter OrdersAdapter;

    private Handler mHandler = new Handler();
    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            new Async_getOrders().execute();
            Toast.makeText(MainActivity.this, "تم تحديث البيانات", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(this, 60*1000);
        }
    };
    public void start_request_order() {
        //mHandler.postDelayed(mToastRunnable, 5000);
        mToastRunnable.run();
    }
    public void stop_request_order() {
        mHandler.removeCallbacks(mToastRunnable);
    }


    @Override
    protected void onResume() {
//        Toast.makeText(this, "welcome back", Toast.LENGTH_SHORT).show();
        start_request_order();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mToastRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mToastRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mToastRunnable);
    }

    ArrayList<order_model> order_list = new ArrayList<order_model>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();

//        new Async_getOrders().execute();


    }


    private void initviews(){
        RV_Orders = (RecyclerView) findViewById(R.id.RV_orders);
        IB_Refresh = (ImageButton) findViewById(R.id.MainActivity_Refresh);
        IB_logout = (ImageButton) findViewById(R.id.MainActivity_logout);
        IB_notificationSetting = (ImageButton) findViewById(R.id.MainActivity_notificationSetting);

        RV_Orders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RV_Orders.setHasFixedSize(true);

        OrdersAdapter = new OrdersBaseAdapter(this,order_list);
        RV_Orders.setAdapter(OrdersAdapter);

        OrdersAdapter.setOnItemAcceptListener(new OrdersBaseAdapter.OnItemAccept() {
            @Override
            public void onItemClick(View view, order_model selectedorder, int position) {
                if(is_locationaccepted){
//                Toast.makeText(MainActivity.this, "Accepted "+selectedorder.getSONumber(), Toast.LENGTH_SHORT).show();

                    new Async_IsAcceptedBefor(selectedorder).execute();

                }else{

                    // Requesting ACCESS_FINE_LOCATION using Dexter library
                    Dexter.withActivity(MainActivity.this)
                            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    constant.is_locationaccepted = true;
                                    startService(new Intent(getApplicationContext(), LocationService.class));
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    if (response.isPermanentlyDenied()) {
                                        // open device settings when the permission is
                                        // denied permanently
                                        openSettings(getApplicationContext());
                                        constant.is_locationaccepted = false;
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                }
            }
        });

        OrdersAdapter.setOnItemDeclineListener(new OrdersBaseAdapter.OnItemDecline() {
            @Override
            public void onItemClick(View view, order_model selectedorder, int position) {
//                Toast.makeText(MainActivity.this, "Decline "+selectedorder.getSONumber(), Toast.LENGTH_SHORT).show();
                new Async_Acceptance(selectedorder.getSONumber(),Driver_ID,false,false,true).execute();
                new Async_getOrders().execute();
            }
        });
        OrdersAdapter.setOnItemContinueListener(new OrdersBaseAdapter.OnItemContinue() {
            @Override
            public void onItemClick(View view, order_model selectedorder, int position) {

                Log.d("SONumber", "onItemClick: "+selectedorder.getSONumber());
                Intent i = new Intent(MainActivity.this, order_active.class);
                i.putExtra("SONumber", selectedorder.getSONumber());
                i.putExtra("ShippingRate", selectedorder.getShippingRate());
                i.putExtra("RK_User", selectedorder.getRK_User());
                startActivity(i);
            }
        });
        IB_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Async_getOrders().execute();
            }
        });
        IB_notificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNotificationSettings(null, getApplicationContext());

            }
        });
        IB_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, login.class);
                startActivity(i);
                // close this activity
                finish();

            }
        });
    }


    private void prepareoder(ArrayList<HashMap<String, String>> PendingOrders) {
        order_list.clear();

        for(int i = 0 ; i < PendingOrders.size() ; i++) {

                if(order_list.isEmpty()){

                    order_model order = new order_model(PendingOrders.get(i).get("ID")+"",
                            PendingOrders.get(i).get("SONumber")+"",
                            PendingOrders.get(i).get("StoreId")+"",
                            PendingOrders.get(i).get("DeliveryProgess_DeliveryContact")+"",
                            PendingOrders.get(i).get("StoreLogo")+"",
                            PendingOrders.get(i).get("RK_Branch")+"",
                            PendingOrders.get(i).get("BranchName")+"",
                            PendingOrders.get(i).get("TotalPrice")+"",
                            PendingOrders.get(i).get("DiscountAmount")+"",
                            PendingOrders.get(i).get("RK_User")+"",
                            PendingOrders.get(i).get("CustomerName")+"",
                            PendingOrders.get(i).get("UserAddress")+"",
                            PendingOrders.get(i).get("UserCityNameAR")+"",
                            PendingOrders.get(i).get("UserCityNameEN")+"",
                            PendingOrders.get(i).get("ShippingRate")+"",
                            new order_details_model(PendingOrders.get(i).get("ID")+"",
                                    PendingOrders.get(i).get("SONumber")+"",
                                    PendingOrders.get(i).get("StoreId")+"",
                                    PendingOrders.get(i).get("StoreName")+"",
                                    PendingOrders.get(i).get("StoreLogo")+"",
                                    PendingOrders.get(i).get("RK_Branch")+"",
                                    PendingOrders.get(i).get("BranchName")+"",
                                    PendingOrders.get(i).get("RK_Products")+"",
                                    PendingOrders.get(i).get("ProductName")+"",
                                    PendingOrders.get(i).get("ProductPicture")+"",
                                    PendingOrders.get(i).get("ProductColorID")+"",
                                    PendingOrders.get(i).get("ColorName")+"",
                                    PendingOrders.get(i).get("ColorPrice")+"",
                                    PendingOrders.get(i).get("ProductAdditionalID")+"",
                                    PendingOrders.get(i).get("AdditionalName")+"",
                                    PendingOrders.get(i).get("AdditionalPrice")+"",
                                    PendingOrders.get(i).get("ProductSizeID")+"",
                                    PendingOrders.get(i).get("SizeName")+"",
                                    PendingOrders.get(i).get("SizePrice")+"",
                                    PendingOrders.get(i).get("ProductPrice")+"",
                                    PendingOrders.get(i).get("Quantity")+"",
                                    PendingOrders.get(i).get("TotalPrice")+"",
                                    PendingOrders.get(i).get("OrderNotes")+"",
                                    PendingOrders.get(i).get("OrderDate")+"",
                                    PendingOrders.get(i).get("IsComple")+"",
                                    PendingOrders.get(i).get("RK_User")+"",
                                    PendingOrders.get(i).get("CustomerName")+"")
                    );

                    order_list.add(order);

                }
                else{

                    //check if ID is repeated that mean he is accepted it and this is a repeated number
                    Boolean is_repeated_ID = false ;
                    for(int n = 0 ; n < order_list.size() ; n++) {
                        if(order_list.get(n).getID().equals(PendingOrders.get(i).get("ID")+"")){
                            is_repeated_ID = true;
                        }else{

                        }
                    }

                    if(!is_repeated_ID) {


                        Boolean is_repeated_SOnumber = false;
                        Boolean is_diffrent_Branch = false;
                        int position = 0;
                        for (int n = 0; n < order_list.size(); n++) {
                            if (order_list.get(n).getSONumber().equals(PendingOrders.get(i).get("SONumber"))) {
                                is_repeated_SOnumber = true;
                                if (!order_list.get(n).getRK_Branch().equals(PendingOrders.get(i).get("RK_Branch"))) {
                                    is_diffrent_Branch = true;
                                } else {

                                }
                                position = n;
                            } else {

                            }
                        }

                        if (is_repeated_SOnumber) {//add more details order

                            order_list.get(position).setTotalPrice(Double.parseDouble(PendingOrders.get(i).get("TotalPrice")) +
                                    Double.parseDouble(order_list.get(position).getTotalPrice()) + "");

                            if (is_diffrent_Branch) {//add new name to branch with ,
                                order_list.get(position).setBranchName(order_list.get(position).getBranchName() + " , "
                                        + PendingOrders.get(i).get("BranchName"));
                            }
                            order_list.get(position).addmoreDetails(new order_details_model(PendingOrders.get(i).get("ID") + "",
                                    PendingOrders.get(i).get("SONumber") + "",
                                    PendingOrders.get(i).get("StoreId") + "",
                                    PendingOrders.get(i).get("StoreName") + "",
                                    PendingOrders.get(i).get("StoreLogo") + "",
                                    PendingOrders.get(i).get("RK_Branch") + "",
                                    PendingOrders.get(i).get("BranchName") + "",
                                    PendingOrders.get(i).get("RK_Products") + "",
                                    PendingOrders.get(i).get("ProductName") + "",
                                    PendingOrders.get(i).get("ProductPicture") + "",
                                    PendingOrders.get(i).get("ProductColorID") + "",
                                    PendingOrders.get(i).get("ColorName") + "",
                                    PendingOrders.get(i).get("ColorPrice") + "",
                                    PendingOrders.get(i).get("ProductAdditionalID") + "",
                                    PendingOrders.get(i).get("AdditionalName") + "",
                                    PendingOrders.get(i).get("AdditionalPrice") + "",
                                    PendingOrders.get(i).get("ProductSizeID") + "",
                                    PendingOrders.get(i).get("SizeName") + "",
                                    PendingOrders.get(i).get("SizePrice") + "",
                                    PendingOrders.get(i).get("ProductPrice") + "",
                                    PendingOrders.get(i).get("Quantity") + "",
                                    PendingOrders.get(i).get("TotalPrice") + "",
                                    PendingOrders.get(i).get("OrderNotes") + "",
                                    PendingOrders.get(i).get("OrderDate") + "",
                                    PendingOrders.get(i).get("IsComple") + "",
                                    PendingOrders.get(i).get("RK_User") + "",
                                    PendingOrders.get(i).get("CustomerName") + ""));

                        } else {//add new order

                            order_model order = new order_model(PendingOrders.get(i).get("ID") + "",
                                    PendingOrders.get(i).get("SONumber") + "",
                                    PendingOrders.get(i).get("StoreId") + "",
                                    PendingOrders.get(i).get("DeliveryProgess_DeliveryContact") + "",
                                    PendingOrders.get(i).get("StoreLogo") + "",
                                    PendingOrders.get(i).get("RK_Branch") + "",
                                    PendingOrders.get(i).get("BranchName") + "",
                                    PendingOrders.get(i).get("TotalPrice") + "",
                                    PendingOrders.get(i).get("DiscountAmount"),
                                    PendingOrders.get(i).get("RK_User") + "",
                                    PendingOrders.get(i).get("CustomerName") + "",
                                    PendingOrders.get(i).get("UserAddress") + "",
                                    PendingOrders.get(i).get("UserCityNameAR") + "",
                                    PendingOrders.get(i).get("UserCityNameEN") + "",
                                    PendingOrders.get(i).get("ShippingRate") + "",
                                    new order_details_model(PendingOrders.get(i).get("ID") + "",
                                            PendingOrders.get(i).get("SONumber") + "",
                                            PendingOrders.get(i).get("StoreId") + "",
                                            PendingOrders.get(i).get("StoreName") + "",
                                            PendingOrders.get(i).get("StoreLogo") + "",
                                            PendingOrders.get(i).get("RK_Branch") + "",
                                            PendingOrders.get(i).get("BranchName") + "",
                                            PendingOrders.get(i).get("RK_Products") + "",
                                            PendingOrders.get(i).get("ProductName") + "",
                                            PendingOrders.get(i).get("ProductPicture") + "",
                                            PendingOrders.get(i).get("ProductColorID") + "",
                                            PendingOrders.get(i).get("ColorName") + "",
                                            PendingOrders.get(i).get("ColorPrice") + "",
                                            PendingOrders.get(i).get("ProductAdditionalID") + "",
                                            PendingOrders.get(i).get("AdditionalName") + "",
                                            PendingOrders.get(i).get("AdditionalPrice") + "",
                                            PendingOrders.get(i).get("ProductSizeID") + "",
                                            PendingOrders.get(i).get("SizeName") + "",
                                            PendingOrders.get(i).get("SizePrice") + "",
                                            PendingOrders.get(i).get("ProductPrice") + "",
                                            PendingOrders.get(i).get("Quantity") + "",
                                            PendingOrders.get(i).get("TotalPrice") + "",
                                            PendingOrders.get(i).get("OrderNotes") + "",
                                            PendingOrders.get(i).get("OrderDate") + "",
                                            PendingOrders.get(i).get("IsComple") + "",
                                            PendingOrders.get(i).get("RK_User") + "",
                                            PendingOrders.get(i).get("CustomerName") + ""));
                            order_list.add(order);
                        }
                    }else{

                    }
                }


            if(PendingOrders.get(i).get("DeliveryProgess_DeliveryContact").isEmpty()){

            }else{//if accepted will add id to SO PendingOrders.get(i).get("DeliveryProgess_DeliveryContact") not empty have a number

                for(int n = 0 ; n < order_list.size() ; n++) {
                    if(order_list.get(n).getSONumber().equals(PendingOrders.get(i).get("SONumber"))){
                        order_list.get(n).setRK_DeliveryContact(PendingOrders.get(i).get("DeliveryProgess_DeliveryContact"));
                        order_list.get(n).setShippingRate(PendingOrders.get(i).get("ShippingRate"));
                    }else{

                    }
                }

            }


        }


        OrdersAdapter.notifyDataSetChanged();
    }

    private ProgressDialog pDialog;
    class Async_getOrders extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = new ArrayList<HashMap<String, String>>();
            PendingOrders = WS.SelectPendingOrderDS(Driver_ID);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(PendingOrders.size() == 0){
                Toast.makeText(MainActivity.this, "لا يوجد طلبات حتى الان", Toast.LENGTH_SHORT).show();
                //something went wrong stay
            }else{
                prepareoder(PendingOrders);
            }
            pDialog.dismiss();
        }

    }

    class Async_IsAcceptedBefor extends AsyncTask<String, String, String> {
        order_model selectedorder ;
        public Async_IsAcceptedBefor(order_model selectedorder_model) {
            this.selectedorder = selectedorder_model;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.SelectDelAcceptance(selectedorder.getSONumber());
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            if(PendingOrders.get(0).get("result").equals("0")){
                new Async_acceptOrders(selectedorder.getSONumber(),Driver_ID).execute();

                Log.d("SONumber", "onItemClick: "+selectedorder.getSONumber());
                Intent i = new Intent(MainActivity.this, order_active.class);
                i.putExtra("SONumber", selectedorder.getSONumber());
                i.putExtra("ShippingRate", selectedorder.getShippingRate());
                i.putExtra("RK_User", selectedorder.getRK_User());
                startActivity(i);
            }else{
                Toast.makeText(MainActivity.this, "عذرا, هذا الطلب تم قبوله من قبل طيار اخر", Toast.LENGTH_SHORT).show();
                new Async_getOrders().execute();
            }
        }

    }

    class Async_acceptOrders extends AsyncTask<String, String, String> {
        String SoNumber ="";
        String driver_ID = "";
        public Async_acceptOrders(String soNumber, String driver_id) {
            this.SoNumber = soNumber;
            this.driver_ID = driver_id;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.DeliveryApp_DeliveryAcceptOrder(SoNumber,driver_ID);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            new Async_Acceptance(SoNumber,driver_ID,true,false,false).execute();
        }

    }

    class Async_Acceptance extends AsyncTask<String, String, String> {
        String SoNumber ="";
        String driver_ID = "";
        String isAccepted = "";
        String isIgnored = "";
        String isRejected = "";
        public Async_Acceptance(String soNumber, String driver_id,boolean isAccepted, boolean isIgnored, boolean isRejected) {
            this.SoNumber = soNumber;
            this.driver_ID = driver_id;
            this.isAccepted = String.valueOf(isAccepted);
            this.isIgnored = String.valueOf(isIgnored);
            this.isRejected = String.valueOf(isRejected);
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.RK_DeliveryAcceptance(driver_ID,SoNumber,isAccepted,isIgnored,isRejected);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
        }

    }



}