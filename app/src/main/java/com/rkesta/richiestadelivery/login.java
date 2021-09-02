package com.rkesta.richiestadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rkesta.richiestadelivery.api.WebService;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiestadelivery.app.constant.Driver_ID;
import static com.rkesta.richiestadelivery.app.constant.Driver_fbid;
import static com.rkesta.richiestadelivery.util.utility.getPref;
import static com.rkesta.richiestadelivery.util.utility.setPref;

public class login extends AppCompatActivity {

    EditText UserName , Password ;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = findViewById(R.id.ET_UserName);
        Password = findViewById(R.id.ET_Password);
        Login = findViewById(R.id.btn_Login);

        UserName.setText(getPref(login.this,"username"));
        Password.setText(getPref(login.this,"password"));

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!UserName.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()){
                setPref(login.this,"username",UserName.getText().toString());
                setPref(login.this,"password",Password.getText().toString());
                new Async_login(UserName.getText().toString() , Password.getText().toString() ).execute();
            }else{
                Toast.makeText(login.this, "برجاء عدم ترك الخانه فارغه", Toast.LENGTH_SHORT).show();
            }
            }
        });




    }




    private ProgressDialog pDialog;
    class Async_login extends AsyncTask<String, String, String> {
        String username ;
        String password ;

        public Async_login(String username, String password) {
            this.username = username ;
            this.password = password ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        ArrayList<HashMap<String, String>> DeliverLogin = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            DeliverLogin = WS.RK_DeliverLogin(username, password,Driver_fbid);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

                if(DeliverLogin.size() == 0){
                    Toast.makeText(login.this, "اسم المستخدم او كلمه المرور خطأ", Toast.LENGTH_SHORT).show();
                    //something went wrong stay
                }else{
                    Driver_ID = DeliverLogin.get(0).get("ID");
                    Intent i = new Intent(login.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            pDialog.dismiss();


        }

    }



}