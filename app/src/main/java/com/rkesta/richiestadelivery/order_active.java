package com.rkesta.richiestadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiestadelivery.adapter.ActiveOrdersBaseAdapter;
import com.rkesta.richiestadelivery.api.WebService;
import com.rkesta.richiestadelivery.model.SONumber_Details_model;
import com.rkesta.richiestadelivery.model.SONumber_model;
import com.rkesta.richiestadelivery.util.Confirm_Dialog;
import com.rkesta.richiestadelivery.util.NotCompleteDialog;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiestadelivery.app.constant.Driver_ID;
import static com.rkesta.richiestadelivery.app.constant.currency;

public class order_active extends AppCompatActivity {

    ActiveOrdersBaseAdapter ActiveOrders_Adapter ;
    RecyclerView RV_activeSONumber;

    ArrayList<SONumber_model> SONumber_list = new ArrayList<SONumber_model>() ;
    //    SO430147000720601
    String SONumber = "";
    String phone = "";
    String UserLatitude = "";
    String UserLongitude = "";
    String ShippingRate = "";
    String RK_User = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_active);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                SONumber = null;
                ShippingRate = null;
                RK_User = null;
            } else {
                SONumber = extras.getString("SONumber");
                ShippingRate = extras.getString("ShippingRate");
                RK_User = extras.getString("RK_User");
            }
        } else {
            SONumber = (String) savedInstanceState.getSerializable("SONumber");
            ShippingRate = (String) savedInstanceState.getSerializable("ShippingRate");
            RK_User = (String) savedInstanceState.getSerializable("RK_User");
        }

        initRecyclerView();
        initAction();
        new Async_getactiveOrders().execute();
    }

    private void initRecyclerView() {
        RV_activeSONumber = findViewById(R.id.AOA_RV_activeSONumber);
        ActiveOrders_Adapter = new ActiveOrdersBaseAdapter(this,SONumber_list);
        RV_activeSONumber.setLayoutManager(new LinearLayoutManager(this));
        RV_activeSONumber.setAdapter(ActiveOrders_Adapter);

    }

    private void initAction() {
        ActiveOrders_Adapter.setOnItemPickedUPListener(new ActiveOrdersBaseAdapter.OnItemPickedUP() {
            @Override
            public void onItemClick(View view, SONumber_model selectedSONumber, int position) {
                SONumber_list.get(position).setPickedByDelivery(true);
                new Async_pickup(selectedSONumber.getSONumber(),selectedSONumber.getBranchID()).execute();
            }
        });


        ((ImageButton)findViewById(R.id.AOA_IB_Backpressed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((Button)findViewById(R.id.AO_BTN_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(phone);
            }
        });
        ((Button)findViewById(R.id.AO_BTN_Navigate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_map_directory();
            }
        });
        ((TextView)findViewById(R.id.AOA_TV_SONumber_header)).setText("#"+SONumber);

        ((Button)findViewById(R.id.AO_BTN_Complete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double totalPrice = 0.0 ;
                String ReasonIfNotPaid = "";
                String BranchID = ""; // Todo ???!!!??!?!?!?!?

                boolean Done = true;
                for (SONumber_model Master: SONumber_list) {
                    if(!Master.isPickedByDelivery()){
                        if(Master.getPickedProblem().isEmpty()){
                            Done = false;
                        }
                    }
                    totalPrice = Double.parseDouble(Master.getItemPrice()) + totalPrice;
                    if(!Master.getPickedProblem().isEmpty()){
                        ReasonIfNotPaid = Master.getBranchNameEn()+ " : " + Master.getPickedProblem()+"\n";
                    }
                }

                if(Done){

                    Confirm_Dialog customDialogFragment = new Confirm_Dialog();
                    customDialogFragment.onAttach(order_active.this);
                    Bundle arguments = new Bundle();
                    arguments.putString("total", totalPrice+"");
                    arguments.putString("SONumber", SONumber);
                    arguments.putString("ShippingRate", ShippingRate);
                    arguments.putString("ReasonIfNotPaid", ReasonIfNotPaid);
                    arguments.putString("BranchID", BranchID);

                    customDialogFragment.setArguments(arguments);
                    customDialogFragment.show(getSupportFragmentManager(), "");
                }else{
                    NotCompleteDialog cdd=new NotCompleteDialog(order_active.this);
                    cdd.show();
                }
            }
        });

    }

    private void makePhoneCall(String PhoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+PhoneNumber));
            startActivity(intent);
        } catch (Exception e) {
        }
    }
    private void make_map_directory() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=&daddr="+UserLatitude+","+UserLongitude));
        startActivity(intent);
    }



    private ProgressDialog pDialog;
    class Async_getactiveOrders extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(order_active.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.DeliveryApp_TrackDelivery(SONumber,RK_User);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(PendingOrders.size() == 0){
                pDialog.dismiss();
                Toast.makeText(order_active.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
                new Async_getactiveOrders().execute();
                //something went wrong stay
            }else{
                prepareoder(PendingOrders);
                pDialog.dismiss();
            }
        }
    }
    private void prepareoder(ArrayList<HashMap<String, String>> PendingOrders) {
        SONumber_list.clear();

        for(int i = 0 ; i < PendingOrders.size() ; i++) {
            if(SONumber_list.isEmpty()){//add direct
                SONumber_list.add(new SONumber_model(
                        PendingOrders.get(i).get("id"),
                        PendingOrders.get(i).get("RK_SalesOrder"),
                        PendingOrders.get(i).get("UserNameAR"),
                        PendingOrders.get(i).get("UserNameEN"),
                        PendingOrders.get(i).get("SONumber"),
                        PendingOrders.get(i).get("UserAddress"),
                        PendingOrders.get(i).get("UserBuildingNumber"),
                        PendingOrders.get(i).get("UserFloorNum"),
                        PendingOrders.get(i).get("UserAptNum"),
                        PendingOrders.get(i).get("UserPhone"),
                        PendingOrders.get(i).get("UserLatitude"),
                        PendingOrders.get(i).get("UserLongitude"),
                        PendingOrders.get(i).get("BranchID"),
                        PendingOrders.get(i).get("BranchNameAr"),
                        PendingOrders.get(i).get("BranchNameEn"),
                        PendingOrders.get(i).get("BranchLatitude"),
                        PendingOrders.get(i).get("BranchLongitude"),
                        PendingOrders.get(i).get("BranchPhone"),
                        PendingOrders.get(i).get("BranchCell"),
                        PendingOrders.get(i).get("DeliveryProgressNotes"),
                        PendingOrders.get(i).get("ItemPrice"),
                        PendingOrders.get(i).get("SalesOrderDate"),
                        PendingOrders.get(i).get("IsComplete"),
                        Boolean.parseBoolean(PendingOrders.get(i).get("IsPickedByDelivery")),
                        ""
                ));
            }else{//not empty

                if(!PendingOrders.get(i).get("id").equals("")){//check if master

                    boolean is_same_Branch = false ;
                    int postion = 0 ;
                    for (int j = 0; j < SONumber_list.size() ; j++) {
                        if(SONumber_list.get(j).getBranchID().equals(PendingOrders.get(i).get("BranchID"))){
                            is_same_Branch = true ;
                            postion = j ;
                        }
                    }

                    if(is_same_Branch){
                        SONumber_list.get(postion).setItemPrice(Double.parseDouble(SONumber_list.get(postion).getItemPrice())+
                                Double.parseDouble(PendingOrders.get(i).get("ItemPrice"))+"");
                    }else{
                        SONumber_list.add(new SONumber_model(
                                PendingOrders.get(i).get("id"),
                                PendingOrders.get(i).get("RK_SalesOrder"),
                                PendingOrders.get(i).get("UserNameAR"),
                                PendingOrders.get(i).get("UserNameEN"),
                                PendingOrders.get(i).get("SONumber"),
                                PendingOrders.get(i).get("UserAddress"),
                                PendingOrders.get(i).get("UserBuildingNumber"),
                                PendingOrders.get(i).get("UserFloorNum"),
                                PendingOrders.get(i).get("UserAptNum"),
                                PendingOrders.get(i).get("UserPhone"),
                                PendingOrders.get(i).get("UserLatitude"),
                                PendingOrders.get(i).get("UserLongitude"),
                                PendingOrders.get(i).get("BranchID"),
                                PendingOrders.get(i).get("BranchNameAr"),
                                PendingOrders.get(i).get("BranchNameEn"),
                                PendingOrders.get(i).get("BranchLatitude"),
                                PendingOrders.get(i).get("BranchLongitude"),
                                PendingOrders.get(i).get("BranchPhone"),
                                PendingOrders.get(i).get("BranchCell"),
                                PendingOrders.get(i).get("DeliveryProgressNotes"),
                                PendingOrders.get(i).get("ItemPrice"),
                                PendingOrders.get(i).get("SalesOrderDate"),
                                PendingOrders.get(i).get("IsComplete"),
                                Boolean.parseBoolean(PendingOrders.get(i).get("IsPickedByDelivery")),
                                ""
                        ));
                    }




                }else{

                    for (int j = 0; j < SONumber_list.size() ; j++) {

                        if(SONumber_list.get(j).getBranchID().equals(PendingOrders.get(i).get("RK_Branch")) && SONumber_list.get(j).getSONumber().equals(PendingOrders.get(i).get("SONumber"))){
                            SONumber_list.get(j).addmoreDetails(new SONumber_Details_model(
                                    PendingOrders.get(i).get("ID"),
                                    PendingOrders.get(i).get("SONumber"),
                                    PendingOrders.get(i).get("StoreId"),
                                    PendingOrders.get(i).get("RKPrdNameAR"),
                                    PendingOrders.get(i).get("RKPrdNameEN"),
                                    PendingOrders.get(i).get("ProductPic"),
                                    PendingOrders.get(i).get("RK_Branch"),
                                    PendingOrders.get(i).get("RK_Products"),
                                    PendingOrders.get(i).get("ProductColor"),
                                    PendingOrders.get(i).get("ProductAdditionals"),
                                    PendingOrders.get(i).get("ProductSize"),
                                    PendingOrders.get(i).get("ColorUnitPrice"),
                                    PendingOrders.get(i).get("AdditionalUnitPrice"),
                                    PendingOrders.get(i).get("SizeUnitPrice"),
                                    PendingOrders.get(i).get("SalesUnitPrice"),
                                    PendingOrders.get(i).get("Qty"),
                                    PendingOrders.get(i).get("TotalPrice"),
                                    PendingOrders.get(i).get("OrderNotes"),
                                    PendingOrders.get(i).get("IsComplete"),
                                    PendingOrders.get(i).get("OrderDate"),
                                    PendingOrders.get(i).get("RK_User"),
                                    PendingOrders.get(i).get("RK_User_DetailID"),
                                    PendingOrders.get(i).get("Notes")
                            ));
                            ShippingRate = PendingOrders.get(i).get("ShippingRate");
                        }

                    }

                }




            }
        }

        ((TextView)findViewById(R.id.AOA_TV_customerdetails)).setText(SONumber_list.get(0).getUserNameEN()+" - "+SONumber_list.get(0).getUserAddress()+" - Building : "+SONumber_list.get(0).getUserBuildingNumber()+" - Floor : "+SONumber_list.get(0).getUserFloorNum()+" - Apartment : "+SONumber_list.get(0).getUserFloorNum());
        phone = SONumber_list.get(0).getUserPhone();
        UserLatitude =  SONumber_list.get(0).getUserLatitude();
        UserLongitude =  SONumber_list.get(0).getUserLongitude();


        ActiveOrders_Adapter.notifyDataSetChanged();


    }



    class Async_pickup extends AsyncTask<String, String, String> {
        String SoNumber;
        String BranchID;

        public Async_pickup(String soNumber, String branchID) {
            this.SoNumber = soNumber ;
            this.BranchID = branchID ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(order_active.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.DeliveryApp_DeliveryPickDelivery(SoNumber,BranchID);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            ActiveOrders_Adapter.notifyDataSetChanged();
        }
    }

}