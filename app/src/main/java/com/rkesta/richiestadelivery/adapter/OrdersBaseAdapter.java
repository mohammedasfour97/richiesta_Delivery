package com.rkesta.richiestadelivery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiestadelivery.R;
import com.rkesta.richiestadelivery.model.order_model;

import java.util.ArrayList;

import static com.rkesta.richiestadelivery.app.constant.Driver_ID;
import static com.rkesta.richiestadelivery.app.constant.currency;

public class OrdersBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<order_model> order_list = new ArrayList<order_model>() ;

    public OrdersBaseAdapter(Context context , ArrayList<order_model> order_list) {
        this.context = context;
        this.order_list = order_list;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener {
    // each data item is just a string in this case
    TextView TV_SOnumber , TV_to , TV_from ;
    Button BTN_decline , BTN_accept,BTN_continue;
        LinearLayout ll_Pwaiting , ll_Paccepted;
    public OriginalViewHolder(View v) {
        super(v);

        TV_SOnumber = (TextView) v.findViewById(R.id.order_item_TV_SOnumber);
        TV_to = (TextView) v.findViewById(R.id.order_item_TV_to);
        TV_from = (TextView) v.findViewById(R.id.order_item_TV_from);

        BTN_accept = (Button) v.findViewById(R.id.order_item_BTN_accept);
        BTN_decline = (Button) v.findViewById(R.id.order_item_BTN_decline);
        BTN_continue = (Button) v.findViewById(R.id.order_item_BTN_continue);

        ll_Pwaiting  = (LinearLayout) v.findViewById(R.id.order_item_ll_Pwaiting);
        ll_Paccepted = (LinearLayout) v.findViewById(R.id.order_item_ll_Paccepted);

        BTN_accept.setOnClickListener(this);
        BTN_decline.setOnClickListener(this);
        BTN_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}


    //interface
    public interface OnItemAccept {
        void onItemClick(View view, order_model selectedorder, int position);
    }
    public interface OnItemDecline {
        void onItemClick(View view, order_model selectedorder, int position);
    }
    public interface OnItemContinue {
        void onItemClick(View view, order_model selectedorder, int position);
    }
    //object from interface
    private OnItemAccept ObjAcceptListener;
    private OnItemDecline ObjDeclineListener;
    private OnItemContinue ObjContinueListener;

//call from mainactivity
    public void setOnItemAcceptListener(OnItemAccept onItemClickListener) {
        this.ObjAcceptListener = onItemClickListener;
    }
    public void setOnItemDeclineListener(OnItemDecline onItemClickListener) {
        this.ObjDeclineListener = onItemClickListener;
    }
    public void setOnItemContinueListener(OnItemContinue onItemClickListener) {
        this.ObjContinueListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        vh = new OriginalViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder Item = (OriginalViewHolder) holder;
            final order_model order = order_list.get(position);

            Item.TV_to.setText(" من  : "+order.getBranchName());
            Item.TV_from.setText(" الى : "+order.getCustomerName()+" , ("+order.getUserCityNameEN()+" | "+order.getUserCityNameAR()+") , "+order.getUserAddress());

            Double TotalPrice = 0.0 ;
            if(order.getTotalPrice().isEmpty()){
                TotalPrice = 0.0;
            }else {
                TotalPrice = Double.parseDouble(order.getTotalPrice());
            }


            Double ShippingRate = 0.0 ;
            if(order.getShippingRate().isEmpty()){
                ShippingRate = 0.0;
            }else {
                ShippingRate = Double.parseDouble(order.getShippingRate());
            }


            Item.TV_SOnumber.setText(( TotalPrice + ShippingRate )+" "+currency+"  - #"+ order.getSONumber());
//            Log.d("onBi-TotalPrice", order.getTotalPrice());
//            Log.d("onBi-getShippingRate", order.getShippingRate());
//            setDefaultFont(Item.TV_SOnumber,context);
            if(order.getRK_DeliveryContact().equals(Driver_ID)){
//                Item.BTN_accept.setText(order.getRK_DeliveryContact());
                Item.ll_Pwaiting.setVisibility(View.GONE);
                Item.ll_Paccepted.setVisibility(View.VISIBLE);
            }else{
//                Item.BTN_accept.setText("موافقه");
                Item.ll_Pwaiting.setVisibility(View.VISIBLE);
                Item.ll_Paccepted.setVisibility(View.GONE);
            }

            /**
             * ONCLICK
             * */
            Item.BTN_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ObjAcceptListener!=null){
                        ObjAcceptListener.onItemClick(view , order , position);
                    }
                }
            });

            Item.BTN_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ObjDeclineListener!=null){
                        ObjDeclineListener.onItemClick(view , order , position);
                    }
                }
            });

            Item.BTN_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ObjContinueListener!=null){
                        ObjContinueListener.onItemClick(view , order , position);
                    }
                }
            });

            /**
             * END ONCLICK
             * */



        }
    }


    @Override
    public int getItemCount() {
        return order_list.size();
    }

    public ArrayList<order_model> getBean() {
        return order_list;
    }

}


