package com.rkesta.richiestadelivery.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.rkesta.richiestadelivery.R;
import com.rkesta.richiestadelivery.api.WebService;
import com.rkesta.richiestadelivery.order_active;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiestadelivery.app.constant.currency;

public class Confirm_Dialog extends DialogFragment {
    Button submit;

    String total , SONumber, ShippingRate , ReasonIfNotPaid , BranchID;
    Dialog dialog;
    TextView ShippingAmount , OrderAmount , TotalAmount ;
    EditText  PaidAmount ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Activity mListener;

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        total = (String) bundle.get("total");
        SONumber = (String) bundle.get("SONumber");
        ShippingRate = (String) bundle.get("ShippingRate");
        ReasonIfNotPaid = (String) bundle.get("ReasonIfNotPaid");
        BranchID = (String) bundle.get("BranchID");
        dialog = new Dialog(getActivity());

/*

        // Make us non-modal, so that others can receive touch events.  HASSAN BADAWI TEST No EXIT ON touch out side
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        // ...but notify us that it happened.   HASSAN BADAWI TEST No EXIT ON touch out side
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
*/

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // set the layout for the dialog
        dialog.setContentView(R.layout.confirm_delivery_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

//        CD_Dialog_ShippingAmount
//        CD_Dialog_OrderAmount

        ShippingAmount = (TextView) dialog.findViewById(R.id.CD_Dialog_ShippingAmount);
        OrderAmount    = (TextView) dialog.findViewById(R.id.CD_Dialog_OrderAmount);

        TotalAmount = (TextView) dialog.findViewById(R.id.CD_Dialog_TotalAmount);
        PaidAmount = (EditText) dialog.findViewById(R.id.CD_Dialog_ET_PaidAmount);


        Double ShippingRateDouble = 0.0 ;
        if(ShippingRate.isEmpty()){
            ShippingRateDouble = 0.0;
        }else {
            ShippingRateDouble = Double.parseDouble(ShippingRate);
        }

        Double totalDouble = 0.0 ;
        if(total.isEmpty()){
            totalDouble = 0.0;
        }else {
            totalDouble = Double.parseDouble(total);
        }
        OrderAmount.setText(totalDouble+"");
        ShippingAmount.setText(ShippingRateDouble+"");

        TotalAmount.setText((  totalDouble +  ShippingRateDouble  )+" "+currency);
        submit = (Button) dialog.findViewById(R.id.CD_Dialog_submit);

        // Close
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // User cancelled the dialog
                dismiss();
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PaidAmount.getText().toString().isEmpty()){
                    new Async_completeDeliver(PaidAmount.getText().toString()).execute();
                }else{
                    Toast.makeText(mListener, "لا يمكن ترك الخانه فارغه", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return dialog;

    }

    private ProgressDialog pDialog;
    class Async_completeDeliver extends AsyncTask<String, String, String> {
        String paidamount;
        public Async_completeDeliver(String Paidamount) {
            this.paidamount = Paidamount;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> PendingOrders = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            PendingOrders = WS.DeliveryApp_DeliveryCustomerDelivery(SONumber , "0", paidamount ,  ReasonIfNotPaid);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            mListener.finish();
            dismiss();
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onAttach(Activity activity) {
        mListener = (Activity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

}
