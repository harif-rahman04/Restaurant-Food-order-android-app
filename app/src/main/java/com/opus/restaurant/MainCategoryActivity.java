package com.opus.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.opus.restaurant.Adapters.CategoryListViewAdapter;
import com.opus.restaurant.Models.Category;
import com.opus.restaurant.Models.KotCount;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainCategoryActivity extends AppCompatActivity {

    private ListView listview_category;
    private ArrayList<Category> category_list ;
    private CategoryListViewAdapter dAdapter;
    private DatabaseHandler db;
    private String table_name;
    private String waiter_name;
    private String order_type;

    private int table_id;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610"))); }
        getSupportActionBar().setTitle("Categories");
        category_list = new ArrayList<Category>();
        listview_category = (ListView)findViewById(R.id.listview_category);
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        try {
           // SharedPreferences preference = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
            table_name = preference.getString("selected_table","");
            waiter_name = preference.getString("selected_waiter","");
            order_type = preference.getString("order_type","");
            category_list.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<Category> items = db.Get_All_Category();
            for (int i = 0; i < items.size(); i++) {
                int id = items.get(i).getCid();
                String name = items.get(i).getCategory();
                String type = items.get(i).getType();
                if (order_type.equals(type)) {
                    Category cat = new Category();
                    cat.setCid(id);
                    cat.setCategory(name);
                    cat.setType(type);
                    category_list.add(cat);
                }
            }
            db.close();
            dAdapter = new CategoryListViewAdapter(getApplicationContext(), R.layout.dishes_list, category_list);
            listview_category.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();

        } catch (Exception e) {  }

        listview_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                String order_status = preferences.getString("order_status", "");

                if (order_status.equals("1")) {
                    int category_id = category_list.get(position).getCid();
                    String selected_category = category_list.get(position).getCategory();
                    String c_id = String.valueOf(category_id);
                    Toast.makeText(getApplicationContext(), selected_category + "  Selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainCategoryActivity.this, MainItemActivity.class);
                    intent.putExtra("selected_category", c_id);
                    intent.putExtra("selected_table", table_name);
                    // intent.putExtra("selected_table_id", table_id);
                    intent.putExtra("selected_waiter", waiter_name);
                    startActivity(intent); }
                else
                {
                    Toast.makeText(getApplicationContext(),"Order not created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        MenuItem searchmenu = menu.findItem(R.id.action_category_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchmenu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                try {
                    selectCategory(query);
                } catch (Exception e) {  }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    selectCategory(newText);
                } catch (Exception e) {  }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //this.finish();
                exitConfirmation();
                return true;
            case R.id.action_category_cart:
                // Intent intent = new Intent(MainItemActivity.this,ViewRunningOrderActivity.class);
                Intent intent = new Intent(MainCategoryActivity.this,EditKotActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectCategory(String category){
        try {
            category_list.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<Category> items = db.Get_Selected_Category(category);
            for (int i = 0; i < items.size(); i++) {
                int id = items.get(i).getCid();
                String name = items.get(i).getCategory();
                Category cat = new Category();
                cat.setCid(id);
                cat.setCategory(name);
                category_list.add(cat);
            }
            db.close();
            dAdapter = new CategoryListViewAdapter(getApplicationContext(), R.layout.dishes_list, category_list);
            listview_category.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();
        } catch (Exception e) {  }
    }

    private void createOrder(){

        String current_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String kot_status = "0";
        // Count //
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        int cart_counts = dbHandler.count_cart();
        dbHandler.close();

        // Count Increament //
        int new_cart_count = cart_counts + 1;
        String local_kot_no = String.valueOf(new_cart_count);
        String loc_kot_no = local_kot_no;
        // Insert into Cart //
        dbHandler.Add_Kot(new KotCount(current_datetime,local_kot_no,kot_status));
        preference.edit().putString("local_kot", loc_kot_no).putString("order_status", "1").commit();
        Toast.makeText(getApplicationContext(), "New Order Created", Toast.LENGTH_SHORT).show(); }
    @Override
    public void onBackPressed(){

        //this.finish();
        exitConfirmation();
    }

    public void exitConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainCategoryActivity.this);
        builder.setMessage("Are you sure want to home ?")
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
                       // preference.edit().putString("order_type","").putString("selected_table","").putString("selected_table_type","").putString("no_of_persons","").putString("server_kot_no","").commit();
                        Intent intent = new Intent(MainCategoryActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit Alert");
        alert.show();
    }

}
