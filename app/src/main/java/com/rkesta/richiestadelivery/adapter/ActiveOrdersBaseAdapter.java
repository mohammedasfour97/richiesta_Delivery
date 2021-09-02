package com.rkesta.richiestadelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.rkesta.richiestadelivery.R;
import com.rkesta.richiestadelivery.app.constant;
import com.rkesta.richiestadelivery.model.SONumber_Details_model;
import com.rkesta.richiestadelivery.model.SONumber_model;
import com.rkesta.richiestadelivery.util.utility;

import java.util.ArrayList;

import static com.rkesta.richiestadelivery.app.constant.currency;

public class ActiveOrdersBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<SONumber_model> SONumber_list = new ArrayList<SONumber_model>() ;

    public ActiveOrdersBaseAdapter(Context context , ArrayList<SONumber_model> SONumber_list) {
        this.context = context;
        this.SONumber_list = SONumber_list;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {
    // each data item is just a string in this case
    TextView TV_itemDetails , TV_Total , TV_BranchName ;
    Button BTN_PickedUP , BTN_Navigate , BTN_call , BTN_saveproblem;

        ImageButton IB_expand_colisap;
        EditText ET_pickedProblem;
        LinearLayout RL_ShowDetails;


    public OriginalViewHolder(View v) {
        super(v);
        TV_BranchName = (TextView) v.findViewById(R.id.AO_item_TV_BranchName);
        TV_Total = (TextView) v.findViewById(R.id.AO_item_TV_Total);
        TV_itemDetails = (TextView) v.findViewById(R.id.AO_item_TV_itemDetails);

        ET_pickedProblem = (EditText) v.findViewById(R.id.AO_item_ET_pickedProblem);

        RL_ShowDetails = (LinearLayout) v.findViewById(R.id.AO_item_RL_ShowDetails);


        BTN_saveproblem = (Button) v.findViewById(R.id.AO_item_BTN_saveproblem);
        BTN_PickedUP = (Button) v.findViewById(R.id.AO_item_BTN_PickedUP);
        BTN_Navigate = (Button) v.findViewById(R.id.AO_item_BTN_Navigate);
        BTN_call = (Button) v.findViewById(R.id.AO_item_BTN_call);

        IB_expand_colisap = (ImageButton) v.findViewById(R.id.AO_item_IB_expand_colisap);

    }
}


    //interface
    public interface OnItemPickedUP {
        void onItemClick(View view, SONumber_model selectedSONumber, int position);
    }
    //object from interface
    private OnItemPickedUP ObjPickedUPListener;

    //call from mainactivity
    public void setOnItemPickedUPListener(OnItemPickedUP onItemClickListener) {
        this.ObjPickedUPListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_order_item, parent, false);
        vh = new OriginalViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            final OriginalViewHolder Item = (OriginalViewHolder) holder;
            final SONumber_model SONumber = SONumber_list.get(position);

            Item.TV_BranchName.setText(SONumber.getBranchNameEn()+" | "+SONumber.getBranchNameAr());
            Item.TV_Total.setText(" إجمالي "+SONumber.getItemPrice() + currency);

            Item.TV_itemDetails.setText(getDetailsString(position));

//        TextView textView = new TextView(context);
//        textView.setText(getDetailsString(position)+" - I am added dynamically to the view");
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        textView.setLayoutParams(params);
//        Item.RL_ShowDetails.addView(textView);

            /**
             * Des here the rows
             * */
            Item.RL_ShowDetails.removeAllViews();
            for (final SONumber_Details_model prod :SONumber.getSONumber_Details_model() ) {
                LayoutInflater inflater = LayoutInflater.from(context);
                //todo des
                RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_my_order_details, null, false);

                //image
                utility.displayImageOriginal(context , ((ImageView) layout.findViewById(R.id.item_my_order_details_IV_propic)), prod.getProductPic());
                //name + Qnty
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_ProName)).setText( " ▒  ("+prod.getSalesUnitPrice()+"x"+prod.getQty()+") "+ prod.getRKPrdNameEN()+","+prod.getRKPrdNameAR());
                //price + currency
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_ProPrice)).setText(prod.getTotalPrice() + " " + constant.currency);

                String desc = "";
//                prod.getNotes()
                if(!prod.getProductColor().isEmpty()){
                    desc = prod.getProductColor();
                }
                if(!prod.getProductAdditionals().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getProductAdditionals();
                    }else{
                        desc += " , "+prod.getProductAdditionals();
                    }
                }
                if(!prod.getProductSize().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getProductSize();
                    }else{
                        desc += " , "+prod.getProductSize();
                    }
                }
                if(!prod.getNotes().isEmpty()){
                    if(desc.isEmpty()) {
                        desc = prod.getNotes();
                    }else{
                        desc += "\n"+prod.getNotes();
                    }
                }
                ((TextView) layout.findViewById(R.id.item_my_order_details_TV_Note)).setText(desc);

//                layout.findViewById(R.id.item_prod_details_IV_Add).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //Toast.makeText(context, prod.getProduct_ID() , Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(context, AddProduct.class);
//
//                        i.putExtra("Product_ID",prod.getProduct_ID());
//                        i.putExtra("NameEN",prod.getNameEN());
//                        i.putExtra("NameAR",prod.getNameAR());
////                        i.putExtra("Name",EN_OR_AR(context,prod.getNameEN(),prod.getNameAR()));
//                        i.putExtra("Desc",EN_OR_AR(context,prod.getDescEn(),prod.getDescAr()));
//                        i.putExtra("Price",prod.getPrice());
//                        i.putExtra("Imageurl",prod.getImageurl());
//                        i.putExtra("Branch_ID",prod.getBranch_ID());
//                        context.startActivity(i);
//                    }
//                });
                Item.RL_ShowDetails.addView(layout);

            }



            if(SONumber.isPickedByDelivery()){
                Item.BTN_PickedUP.setText("تم الالتقاط");
                Item.BTN_PickedUP.setEnabled(false);
                Item.BTN_saveproblem.setEnabled(false);
                Item.ET_pickedProblem.setEnabled(false);
            }else{
                Item.BTN_PickedUP.setText("التقط");
                Item.BTN_PickedUP.setEnabled(true);
                Item.BTN_saveproblem.setEnabled(true);
                Item.ET_pickedProblem.setEnabled(true);
            }
            Item.ET_pickedProblem.setText(SONumber_list.get(position).getPickedProblem());

            Item.BTN_saveproblem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            SONumber_list.get(position).setPickedProblem(Item.ET_pickedProblem.getText()+"");
                }
            });



            if(SONumber.isExpanded()){
                Item.RL_ShowDetails.setVisibility(View.VISIBLE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_black_24));
            }else {
                Item.RL_ShowDetails.setVisibility(View.GONE);
                Item.IB_expand_colisap.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_black_24));
            }
            Item.IB_expand_colisap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(SONumber.isExpanded()){
                        SONumber_list.get(position).setExpanded(false);
                    }else {
                        SONumber_list.get(position).setExpanded(true);
                    }
                    notifyItemChanged(position);
                }
            });

            Item.BTN_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!SONumber.getBranchPhone().isEmpty()){
                        makePhoneCall(SONumber.getBranchPhone());
                    }else{
                        makePhoneCall(SONumber.getBranchCell());
                    }
                }
            });
            Item.BTN_Navigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    make_map_directory(SONumber.getBranchLatitude(),SONumber.getBranchLongitude());
                }
            });


            Item.BTN_PickedUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Item.ET_pickedProblem.getText().toString().isEmpty()){
                        if(ObjPickedUPListener!=null){
                            ObjPickedUPListener.onItemClick(view , SONumber , position);
                        }
                    }else{
                        Toast.makeText(context, "الكتابه في خانه ( مشكله في الالتقاط ) يجب تركها فارغة", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void make_map_directory(String Latitude,String Longitude) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=&daddr="+Latitude+","+Longitude));
        context.startActivity(intent);
    }
    private void makePhoneCall(String PhoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+PhoneNumber));
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    private String getDetailsString(int position) {
        String STR_Details = "";

        for (SONumber_Details_model singel_detail: SONumber_list.get(position).getSONumber_Details_model()) {
            if(STR_Details.isEmpty()){
                STR_Details = singel_detail.getfullDetailsNAME();
            }else{
                STR_Details += " \n\n"+singel_detail.getfullDetailsNAME();
            }
        }

        return STR_Details;
    }


    @Override
    public int getItemCount() {
        return SONumber_list.size();
    }

    public ArrayList<SONumber_model> getBean() {
        return SONumber_list;
    }

    public String getDataProblem(int position) {
        return SONumber_list.get(position).getPickedProblem();
    }

}


