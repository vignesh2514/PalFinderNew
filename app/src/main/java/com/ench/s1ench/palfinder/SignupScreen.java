package com.ench.s1ench.palfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupScreen extends AppCompatActivity {
EditText Ephonum,Eusername,Epassword;
    String Sphnum,Susername,Spassword;
    Button Blogin,Bsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
Ephonum=(EditText) findViewById(R.id.editText);
        Eusername=(EditText) findViewById(R.id.editText3);
        Epassword=(EditText) findViewById(R.id.editText2);
Blogin=(Button) findViewById(R.id.button);
        Bsignup=(Button) findViewById(R.id.button2);
        Bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sphnum=Ephonum.getText().toString();
                Susername=Eusername.getText().toString();
                Spassword=Epassword.getText().toString();
                allinone_insert(Sphnum,Susername,Spassword);
            }
        });

Blogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SignupScreen.this,LoginScreen.class);
        startActivity(intent);
    }
});

    }

    public void allinone_insert(final String sphnum, final String susername, final String spassword) {

        StringRequest stringreqs=new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exits");
                    if (abc)
                    {

                        Intent intent=new Intent(SignupScreen.this,LoginScreen.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"User Exists already with  mobile number!",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        JSONObject users = jObj.getJSONObject("user_det");
                        String mobile_number = users.getString("mobile_number");
                        String username = users.getString("username");

                        String uuidq = users.getString("uuid");

                        Intent intent=new Intent(SignupScreen.this,HomeScreen.class);
                        intent.putExtra("mobile_number",mobile_number);
                        intent.putExtra("username",username);
                        intent.putExtra("uuidq",uuidq);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),mobile_number+"/"+username,Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"INTERNET CONNECTION NOT AVAILABLE",Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> uandme=new HashMap<String, String>();
                uandme.put("number_mobile",sphnum);
                uandme.put("user_name",susername);
                uandme.put("password_user",spassword);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }

}
