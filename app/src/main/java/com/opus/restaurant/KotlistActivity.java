package com.opus.restaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.Adapters.KotListAdapter;
import com.opus.restaurant.Models.Category;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Services.HttpTaskGet;
import com.opus.restaurant.Services.KeyValue;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class KotlistActivity extends AppCompatActivity {

    private Context ctx;
    private static final int MODE_PRIVATE =  0x0000;
    ListView kotlistview;
    KotListAdapter dAdapter;
    DatabaseHandler db;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlist);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610")));
        }
        getSupportActionBar().setTitle("Kotlist");
        preferences  = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        db = new DatabaseHandler(KotlistActivity.this);
        kotlistview = (ListView)findViewById(R.id.kot_listview);
        // progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Category Sync in Progress", true, false);
       /* ArrayList<KeyValue> params = new ArrayList<KeyValue>();
        String ip_no = ipNo;
        String Url = "http://"+ip_no + "/Service1.svc/getCategoryDetails";
        // String Url = "http://192.168.1.14:6060/Service1.svc/getCategoryDetails";
        HttpTaskGet httpCall = new HttpTaskGet(SettingsActivity.this, params, null, Url, new CallBack() {
            @Override
            public void onPostExecute(String result) {
                try {
                    //progressBar.dismiss();
                    JSONArray jsonArray =new JSONArray(result);
                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String categoryName = jsonObject.getString("Category");
                        String type = jsonObject.getString("Ktype");
                        int cid = Integer.parseInt(jsonObject.getString("Id"));

                        DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
                        // dbHandler.Add_Categorymaster(new Category(cid, categoryName, "Dine In", "I"));
                        dbHandler.Add_Categorymaster(new Category(cid, categoryName, type, "I"));

                      *//*  Category category =new Category();
                        category.setCategoryId(cid);
                        category.setCategory(categoryName); *//*
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  progressBar.dismiss();
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        },true);
        httpCall.execute();*/
        try {
            db = new DatabaseHandler(KotlistActivity.this);
            RequestQueue queue = Volley.newRequestQueue(this);
            String Waiter_code=preferences.getString("waiter_code","");
            String Url = "http://"+preferences.getString("ip_port_no","")+ "/Service1.svc/getKotList?WaiterCode="+Waiter_code;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);
                                ArrayList<String> kot_list=new ArrayList<String>();
                                for (int i=0;i<jsonArray.length();i++){
                                   // JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    kot_list.add(jsonArray.get(i).toString());
                                   /* String type = jsonObject.getString("Ktype");
                                    int cid = Integer.parseInt(jsonObject.getString("Id"));
                                    DatabaseHandler dbHandler = new DatabaseHandler(KotlistActivity.this);
                                    // dbHandler.Add_Categorymaster(new Category(cid, categoryName, "Dine In", "I"));
                                    dbHandler.Add_Categorymaster(new Category(cid, categoryName, type, "I"));*/
                                }
                                 dAdapter = new KotListAdapter(getApplicationContext(), R.layout.kot_list,kot_list);
                                kotlistview.setAdapter(dAdapter);
                                dAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        /*    ArrayList<KotNoListModel> contact_array_from_db = db.Get_KOT_List();
            Log.d("Tag",".>>>>"+contact_array_from_db);
            for (int i = 0; i <contact_array_from_db.size() ; i++) {
                int d_id = contact_array_from_db.get(i).getId();
                String loc_id = contact_array_from_db.get(i).getLocal_id();
                String kot_no = contact_array_from_db.get(i).getKot_no();
                String order_type_str = contact_array_from_db.get(i).getOrder_type();
                KotNoListModel cnt = new KotNoListModel();
                cnt.setId(d_id);
                cnt.setLocal_id(loc_id);
                cnt.setKot_no(kot_no);
                cnt.setOrder_type(order_type_str);
                kot_list.add(cnt);
            }
            Log.d("Tag",".>>>>"+kot_list.toArray());
            db.close();*/
           // dAdapter = new KotListAdapter(getApplicationContext(), R.layout.dishes_list,kot_list);
            //kotlistview.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();
        }
        catch (Exception e){ }

        kotlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // String chktime = slotlists.get(position).getTimevalue();
              /*  String localId = kot_list.get(position).getLocal_id();
                String serverId = kot_list.get(position).getKot_no();
                Intent intent =new Intent(KotlistActivity.this,KotSummaryActivity.class);
                intent.putExtra("code",localId);
                intent.putExtra("server_code",serverId);
                startActivity(intent); */

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                //  this.finish();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

   /*     try {
            //kot_list.clear();
            db = new DatabaseHandler(KotlistActivity.this);
            ArrayList<KotNoListModel> contact_array_from_db = db.Get_KOT_List();

            for (int i = 0; i < contact_array_from_db.size(); i++) {
                int d_id = contact_array_from_db.get(i).getId();
                String loc_id = contact_array_from_db.get(i).getLocal_id();
                String kot_no = contact_array_from_db.get(i).getKot_no();
                KotNoListModel cnt = new KotNoListModel();
                cnt.setId(d_id);
                cnt.setLocal_id(loc_id);
                cnt.setKot_no(kot_no);
              //  kot_list.add(cnt);
            }
            db.close();

            dAdapter = new KotListAdapter(getApplicationContext(), R.layout.dishes_list, kot_list);
            kotlistview.setAdapter(dAdapter);
            dAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }*/
    }



}
