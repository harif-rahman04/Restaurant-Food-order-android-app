package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.Adapters.DishShowListViewAdapter;
import com.opus.restaurant.Models.BasicDetails;
import com.opus.restaurant.Models.Items;
import com.opus.restaurant.Models.KotAddonList;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainItemActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ListView dishlistview;
    private ArrayList<Items> dish_list ;
    private ArrayList<BasicDetails> basic_details;
    private DishShowListViewAdapter dAdapter;
    String selected_category;
    String table_name;
    String waiter_name;
    String table_type;
    String no_of_persons;
    String order_type;
    int table_id;
    private SharedPreferences preference;
    private Button proceedBtn;
    private ArrayList<KotOrdersView> order_list ;
    String sum_amount,ip_no,loc_ref_no,server_kot_no,room_type;
    EditText suggestion_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_item);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610")));
        }

        getSupportActionBar().setTitle("Recipes");
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        dishlistview = (ListView) findViewById(R.id.listview_dish);
        proceedBtn = (Button)findViewById(R.id.proceed_checkout_btn);
        order_list = new ArrayList<KotOrdersView>();
        suggestion_et = (EditText)findViewById(R.id.suggestion_et);
        ip_no = preference.getString("ip_port_no", "");

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKot();
            }
        });

        try {
            Intent intent = getIntent();
            selected_category = intent.getStringExtra("selected_category");
            table_name = preference.getString("selected_table","");
            table_type = preference.getString("selected_table_type","");
            no_of_persons = preference.getString("no_of_persons","");
            waiter_name = preference.getString("waiter_code","");
            server_kot_no = preference.getString("server_kot_no","");
            room_type = preference.getString("selected_table_type","");
            order_type=preference.getString("order_type","");


            //  table_id = intent.getIntExtra("selected_table_id",0);
           // waiter_name = intent.getStringExtra("selected_waiter");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        dish_list = new ArrayList<Items>();
        basic_details = new ArrayList<BasicDetails>();
        BasicDetails basics = new BasicDetails();
        basics.setSelected_table_name("T1");
        basics.setSelected_category(selected_category);
        basics.setSelected_waiter_code(waiter_name);
        basics.setNo_of_persons("1");
        basics.setKot_no(server_kot_no);
        basics.setOrder_type(order_type);
        basics.setRoom_type(room_type);
        basic_details.add(basics);
        dishlistview.setItemsCanFocus(true);
        try {
            dish_list.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<Items> all_items = db.GetItems_By_Category(selected_category);
            for (int i = 0; i < all_items.size(); i++) {

                int auto_id = all_items.get(i).getId();
                int item_id = all_items.get(i).getItemId();
                String item_code = all_items.get(i).getItemCode();
                String item_name = all_items.get(i).getItemName();
                String item_cost = all_items.get(i).getCost();
                String item_type = all_items.get(i).getType();
                String item_category = all_items.get(i).getCategory();
                String ac_rate = all_items.get(i).getAc_rate();
                String nonac_rate = all_items.get(i).getNormal_rate();
                Items items = new Items();
                items.setId(auto_id);
                items.setItemId(item_id);
                items.setItemCode(item_code);
                items.setCategory(item_category);
                if (table_type.equals("AC"))
                {
                    items.setCost(ac_rate);
                }
                else if (table_type.equals("NONAC"))
                {
                    items.setCost(nonac_rate);
                }
                else {
                    items.setCost(item_cost);
                }
                items.setItemName(item_name);
                items.setType(item_type);
                dish_list.add(items);
            }
            db.close();
            dAdapter = new DishShowListViewAdapter(getApplicationContext(), R.layout.dishes_list, dish_list,basic_details);
            dishlistview.setAdapter(dAdapter);

        } catch (Exception e) { }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_category_cart:
               // Intent intent = new Intent(MainItemActivity.this,ViewRunningOrderActivity.class);
                Intent intent = new Intent(MainItemActivity.this,EditKotActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }

    private void loadSavedItems(){

        order_list.clear();
        db = new DatabaseHandler(getApplicationContext());
        db.close();
    }


    public void sendKot(){
        try {
           // final SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
            ip_no = preference.getString("ip_port_no", "");
            loc_ref_no = preference.getString("local_id", "");
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String ipno=ip_no;
            String url = "http://"+ipno+"/Service1.svc/kotdetails";
           // String url = "http://192.168.1.14:6060/Service1.svc/Kotdetails";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String newKotNo =  server_kot_no.substring(1, server_kot_no.length() - 1);
                                DatabaseHandler db_prog = new DatabaseHandler(getApplicationContext());
                                db_prog.Add_Server_Kot(new KotNoListModel(loc_ref_no, newKotNo, "0"));
                                String res = response;
                                Toast.makeText(getApplicationContext(),"Kot processed",Toast.LENGTH_SHORT).show();
                                preference.edit().putString("order_status", "0").commit();
                                Intent intent = new Intent(MainItemActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            catch (Exception e){}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String err = error.getMessage();
                }
            })
            {  //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //  Map<String, String> params = new HashMap<>();
                    order_list.clear();
                    db = new DatabaseHandler(getApplicationContext());

                    HashMap<String, String> params = new HashMap<String, String>();
                    String final_code = "";
                    String final_qty = "";
                    if(server_kot_no.equals(null)|| server_kot_no.isEmpty())
                        Toast.makeText(getApplicationContext(),"kot no is null",Toast.LENGTH_SHORT).show();
                    ArrayList<KotOrdersView> items = db.Get_KOT_Orders_Items(server_kot_no);
                    for (int i = 0; i < items.size(); i++) {
                        int id = items.get(i).getId();
                        String name = items.get(i).getItemName();
                        String code = items.get(i).getItemCode();
                        String qty = items.get(i).getItemQty();
                        String amount = items.get(i).getAmount();

                        final_code+=code;
                        final_qty+=qty;
                        if (i!=params.size()-1)
                        {
                            final_code+=",";
                            final_qty+=",";
                        }
                    }
                    db.close();

                    String f_code = final_code.substring(0, final_code.lastIndexOf(","));
                    String f_qty = final_qty.substring(0, final_qty.lastIndexOf(","));

                    params.put("Items",f_code);
                    params.put("qty",f_qty);

                    params.put("TbvTable",table_name);
                    params.put("TbvPerson",no_of_persons);
                   // String newKotNo =  server_kot_no.substring(1, server_kot_no.length() - 1);
                    params.put("KotNo",server_kot_no);
                    params.put("tableName",table_name);
                    params.put("WaiterCode",waiter_name);
                    params.put("Pcnt",no_of_persons);
                    params.put("Suggestion",suggestion_et.getText().toString());
                    params.put("RoomType",table_type);
                   // params.put("localId",loc_ref_no);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
        catch (Exception e){}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
