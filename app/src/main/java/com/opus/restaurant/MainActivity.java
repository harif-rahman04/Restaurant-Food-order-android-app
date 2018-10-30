package com.opus.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.opus.restaurant.Models.KotCount;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ImageButton orderBtn;
    private ImageButton tablesBtn;
    private ImageButton kotBtn;
    private ImageButton settingsBtn;
    Button logout_btn;
    ImageView main_bg;
    private TextView waiterCodeTxt;
    private String waiter_code;
    private SharedPreferences preference;
    private FrameLayout loginProgressBarHolder;
    private String ipNo;
    RelativeLayout center_layout;
    private Animation from_up_anim,from_bottom_anim,slide_from_up,slide_from_bottom;
    private  AlertDialog order_choice_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mTextMessage = (TextView) findViewById(R.id.message);
        center_layout=findViewById(R.id.center_layout);
      /*  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        orderBtn = (ImageButton)findViewById(R.id.home_orders_btn);
        kotBtn = (ImageButton)findViewById(R.id.home_kot_btn);
        tablesBtn = (ImageButton)findViewById(R.id.home_table_btn);
        main_bg=findViewById(R.id.main_bg);
        settingsBtn = (ImageButton)findViewById(R.id.home_settings_btn);
        //logout_btn=(Button)findViewById(R.id.logout_btn);
        main_bg.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure want to logout ?")
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                preference.edit().putString("waiter_code","").putString("order_type","").putString("selected_table","").putString("selected_table_type","").putString("no_of_persons","").putString("server_kot_no","").commit();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Logout alert");
                alert.show();
            }

        });

        waiterCodeTxt = (TextView)findViewById(R.id.main_waiter_code);
        loginProgressBarHolder = (FrameLayout)findViewById(R.id.loginProgressBarHolder);
        from_bottom_anim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_from_right);
        from_up_anim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_from_left);
        kotBtn.setAnimation(from_up_anim);
        settingsBtn.setAnimation(from_up_anim);
        orderBtn.setAnimation(from_bottom_anim);
        tablesBtn.setAnimation(from_bottom_anim);
        settingsBtn.animate().setDuration(800);
        kotBtn.animate().setDuration(800);
        orderBtn.animate().setDuration(800);
        tablesBtn.animate().setDuration(800);
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        waiter_code = preference.getString("waiter_code","");
        waiterCodeTxt.setText(waiter_code);
        ipNo = preference.getString("ip_port_no","");
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MainActivity.this,MainCategoryActivity.class);
               // startActivity(intent);
               // createOrder();
                checkOngoingOrder();
            }
        });

        tablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TablesOnlyActivity.class);
                startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
    });

        kotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,KotlistActivity.class);
                startActivity(intent);
            }
        }); }
    private void createOrder(){
        String current_datetime = new SimpleDateFormat("dd-MM-yy hh:mm aa").format(Calendar.getInstance().getTime());
        String kot_status = "0";
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        int cart_counts = dbHandler.count_cart();
        dbHandler.close();
        // Count Increament //
        int new_cart_count = cart_counts + 1;
        String local_kot_no = String.valueOf(new_cart_count);
        final String loc_kot_no = local_kot_no;
        // Insert into Cart //
        dbHandler.Add_Kot(new KotCount(current_datetime,local_kot_no,kot_status));
        preference.edit().putString("local_kot", loc_kot_no).
                putString("Edit_date",current_datetime).putString("order_status", "1").commit();
        Toast.makeText(getApplicationContext(), "New Order Created", Toast.LENGTH_SHORT).show();
        try {
            loginProgressBarHolder.setVisibility(View.VISIBLE);
          //  final String requestURL = "http://192.168.1.14:6060/Service1.svc/GenerateKotNo";
            final String requestURL = "http://"+ipNo+"/Service1.svc/GenerateKotNo";
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loginProgressBarHolder.setVisibility(View.GONE);
                            String status  =response;
                            String newKotNo =  status.substring(1, status.length() - 1);
                            preference.edit().putString("server_kot_no",newKotNo).putString("local_id",loc_kot_no).commit();
                            call_alert_take_way_or_dinein();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        loginProgressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Sorry! Server problem",Toast.LENGTH_SHORT).show();
                    }
                    // _response.setText("That didn't work!");
                }
            }) {
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("LocalId", loc_kot_no);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        } catch (Exception e) {
        }
    }

    private void checkOngoingOrder(){
        preference = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        String orderStatus = preference.getString("order_status","");
        if (orderStatus.equals(1)){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            createOrder();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Order is going on. Do you want to create a new order?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else
        {
            createOrder();
        }
    }
    public void call_alert_take_way_or_dinein(){
        Toast.makeText(getApplicationContext(),"pd called",Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.order_choice_alerdiaog, null);
        slide_from_up= AnimationUtils.loadAnimation(MainActivity.this,R.anim.from_up);
        slide_from_bottom=AnimationUtils.loadAnimation(MainActivity.this,R.anim.from_bottom);
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        ImageView take_away_img=alertLayout.findViewById(R.id.Image_Button_take_away);
        ImageView dine_in_img=alertLayout.findViewById(R.id.Image_Button_dine_in);
        order_choice_dialog = alert.create();
        take_away_img.setAnimation(slide_from_up);
        dine_in_img.setAnimation(slide_from_bottom);
        order_choice_dialog.show();
        take_away_img.animate().setDuration(200);
        dine_in_img.animate().setDuration(200);
        dine_in_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.edit().putString("order_type","Dine In").commit();
                Intent intent = new Intent(MainActivity.this,TablesViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        take_away_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   preference.edit().putString("selected_table","0").putString("selected_table_type","0").
                           putString("no_of_persons","0").putString("order_type","Takeout").commit();;
                Intent intent = new Intent(MainActivity.this,MainCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        }); }
}
