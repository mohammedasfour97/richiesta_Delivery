package com.rkesta.richiestadelivery.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.rkesta.richiestadelivery.R;

public class NotCompleteDialog extends Dialog {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public NotCompleteDialog(Activity a) {
        super(a);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.not_complete_dialog);
        no = (Button) findViewById(R.id.not_complete_btn_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}