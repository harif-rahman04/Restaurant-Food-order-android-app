package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.opus.restaurant.Adapters.DishShowListViewAdapter;
import com.opus.restaurant.Models.BasicDetails;
import com.opus.restaurant.Models.Items;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;

public class EditAddItemsActivity extends AppCompatActivity {

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
String kot_no_via_intent;
String order_type;
String room_type;
    int table_id;
    private SharedPreferences preference;
    private Button proceedBtn;
    private ArrayList<KotOrdersView> order_list ;
    String sum_amount,ip_no,loc_ref_no,server_kot_no;
    EditText suggestion_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_items);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610"))); }
        getSupportActionBar().setTitle("Recipes");
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        dishlistview = (ListView) findViewById(R.id.listview_dish);
        proceedBtn = (Button)findViewById(R.id.proceed_checkout_btn);
        order_list = new ArrayList<KotOrdersView>();
        suggestion_et = (EditText)findViewById(R.id.suggestion_et);
        ip_no = preference.getString("ip_port_no", "");
        kot_no_via_intent=getIntent().getStringExtra("kot_no..");

        try {
            Intent intent = getIntent();
            selected_category = intent.getStringExtra("selected_category");
            table_name = preference.getString("selected_table","");
            table_type = preference.getString("selected_table_type","");
            no_of_persons = preference.getString("no_of_persons","");
            waiter_name = preference.getString("waiter_code","");
            server_kot_no = preference.getString("server_kot_no","");
            room_type=preference.getString("room_type","");
            order_type=preference.getString("order_type","");
            // room_type = preference.getString("selected_table_type","");

            //  table_id = intent.getIntExtra("selected_table_id",0);
            // waiter_name = intent.getStringExtra("selected_waiter");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        dish_list = new ArrayList<Items>();
        basic_details = new ArrayList<BasicDetails>();
        BasicDetails basics = new BasicDetails();
        basics.setSelected_table_name(table_name);
        basics.setKot_no(kot_no_via_intent);
        basics.setSelected_category(selected_category);
        basics.setSelected_waiter_code(waiter_name);
        basics.setNo_of_persons(no_of_persons);
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

                String ac_rate = all_items.get(i).getAc_rate();
                String nonac_rate = all_items.get(i).getNormal_rate();

                Items items = new Items();
                items.setId(auto_id);
                items.setItemId(item_id);
                items.setItemCode(item_code);
                if (table_type.equals("AC"))
                {
                    items.setCost(ac_rate);
                }
                else if (table_type.equals("NONAC"))
                {
                    items.setCost(nonac_rate);
                }
                else
                {
                    items.setCost(item_cost);
                }
                items.setItemName(item_name);
                items.setType(item_type);
                dish_list.add(items);
            }
            db.close();
            dAdapter = new DishShowListViewAdapter(getApplicationContext(), R.layout.dishes_list, dish_list,basic_details);
            dishlistview.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();

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
                Intent intent = new Intent(EditAddItemsActivity.this,EditAllKotActivity.class);
                intent.putExtra("kot_no",kot_no_via_intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
