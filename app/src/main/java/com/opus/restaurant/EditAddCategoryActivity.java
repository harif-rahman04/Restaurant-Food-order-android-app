package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
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
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;

public class EditAddCategoryActivity extends AppCompatActivity {

    private ListView listview_category;
    private ArrayList<Category> category_list ;
    private CategoryListViewAdapter dAdapter;
    private DatabaseHandler db;
    private String table_name;
    private String waiter_name;
    private String order_type;
    private String server_kot_no;
    private int table_id;
    String kot_no_via_intent;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_category);
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
            waiter_name = preference.getString("waiter_code","");
            order_type = preference.getString("order_type","");
            server_kot_no = preference.getString("server_kot_no", "");
            kot_no_via_intent=getIntent().getStringExtra("kot_no__");
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
                } }
            db.close();
            dAdapter = new CategoryListViewAdapter(getApplicationContext(), R.layout.dishes_list, category_list);
            listview_category.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();
        } catch (Exception e) {  }

        listview_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
               // String order_status = preferences.getString("order_status", "");
               // String ids = preferences.getString("edit_kot_localid", "");
String kot_no_via_intent=getIntent().getStringExtra("kot_no__");
                if (!kot_no_via_intent.equals(null) && !kot_no_via_intent.isEmpty()) {

                    int category_id = category_list.get(position).getCid();
                    String selected_category = category_list.get(position).getCategory();
                    String c_id = String.valueOf(category_id);
                    Toast.makeText(getApplicationContext(), selected_category + "  Selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditAddCategoryActivity.this, EditAddItemsActivity.class);
                    intent.putExtra("selected_category", c_id);
                    intent.putExtra("selected_table", table_name);
                    intent.putExtra("kot_no..",kot_no_via_intent);
                    // intent.putExtra("selected_table_id", table_id);
                    intent.putExtra("selected_waiter", waiter_name);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kot no not selected", Toast.LENGTH_SHORT).show();
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
        int id = item.getItemId();
        if (id == R.id.action_category_cart){
            // Intent intent = new Intent(MainCategoryActivity.this,ViewRunningOrderActivity.class);
            Intent intent = new Intent(EditAddCategoryActivity.this,EditAllKotActivity.class);
            intent.putExtra("kot_no",kot_no_via_intent);
            startActivity(intent);
        }
        else if (id==R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

}
