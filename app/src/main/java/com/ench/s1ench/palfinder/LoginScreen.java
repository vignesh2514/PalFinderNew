package com.ench.s1ench.palfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class LoginScreen extends AppCompatActivity {
Button Bsignup,Bloign;
    EditText Eusname,Epasswrd;
    String Susername,Spasswrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Eusname=(EditText) findViewById(R.id.editText);
        Epasswrd=(EditText) findViewById(R.id.editText2);
Bloign=(Button) findViewById(R.id.button);
Bsignup=(Button) findViewById(R.id.button2);
        Bloign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Susername=Eusname.getText().toString();
                Spasswrd=Epasswrd.getText().toString();
                logintodb(Susername,Spasswrd);
            }
        });
        Bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginScreen.this,SignupScreen.class);
                startActivity(intent);
            }
        });
    }

    public void logintodb(final String susername, final String spasswrd) {

        StringRequest stringreqs=new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exits");
if (abc)
{
    JSONObject users = jObj.getJSONObject("user_det");
    String mobile_number = users.getString("mobile_number");
    String username = users.getString("username");
    String uuidq = users.getString("uuid");

    Intent intent=new Intent(LoginScreen.this,HomeScreen.class);
    intent.putExtra("mobile_number",mobile_number);
    intent.putExtra("username",username);
    intent.putExtra("uuidq",uuidq);
    startActivity(intent);
    Toast.makeText(getApplicationContext(),mobile_number+"/"+username,Toast.LENGTH_SHORT).show();

}
else
{
    String messageofserver = jObj.getString("messeade");
    Toast.makeText(getApplicationContext(),messageofserver,Toast.LENGTH_SHORT).show();


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
                uandme.put("mobile_number",susername);
                uandme.put("password_user",spasswrd);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
