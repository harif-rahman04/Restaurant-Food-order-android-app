package com.opus.restaurant;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.Models.User;
import com.opus.restaurant.Services.RequestHandler;
import com.opus.restaurant.Services.VolleySingletons;
import com.opus.restaurant.Sqlite.DatabaseHandler;
import com.opus.restaurant.Utilities.QuickSandRegularEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private QuickSandRegularEditText loginEt;
    private QuickSandRegularEditText passwordEt;
    private Button loginBtn;
    private Button settingsBtn;
    private FrameLayout loginProgressBarHolder;
    private SharedPreferences prefs;
    private String ipNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        loginEt = (QuickSandRegularEditText)findViewById(R.id.login_username);
        passwordEt = (QuickSandRegularEditText)findViewById(R.id.login_password);
        loginBtn = (Button)findViewById(R.id.login_signin_btn);
        settingsBtn = (Button)findViewById(R.id.login_settings_btn);
       // loginProgressBarHolder = (FrameLayout)findViewById(R.id.loginProgressBarHolder);
       prefs = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
                final String username = loginEt.getText().toString();
                final String password = passwordEt.getText().toString();
                if (username.isEmpty()){

                    Toast.makeText(LoginActivity.this,"Username required",Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){

                    Toast.makeText(LoginActivity.this,"Password required",Toast.LENGTH_SHORT).show();
                }
                else {

                    try {
                        ipNo = prefs.getString("ip_port_no","");
                      //  loginProgressBarHolder.setVisibility(View.VISIBLE);
                        // final String requestURL = getResources().getString(R.string.url) + "/LoginCheck";
                        //final String requestURL = "http://192.168.1.14:6060/Service1.svc/LoginCheckCredential";
                        final String requestURL = "http://"+ipNo+"/Service1.svc/LoginCheckCredential";

                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                //        loginProgressBarHolder.setVisibility(View.GONE);

                                        String status  =response;
                                        String res = response;
                                        if (res.equalsIgnoreCase("0"))
                                        {
                                            Toast.makeText(getApplicationContext(),"Please check your login details",Toast.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
                                            prefs.edit().putString("waiter_code",username.toString()).commit();
                                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                                            finish();

                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                          //          loginProgressBarHolder.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"Sorry! Server problem",Toast.LENGTH_SHORT).show();
                                }
                                // _response.setText("That didn't work!");
                            }
                        }) {
                            //adding parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Username", username);
                                params.put("Password", password);
                                params.put("Role", "Waiter");
                                return params;
                            }
                        };
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    } catch (Exception e) {
                    }
                }
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        }); }}
