package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.Adapters.EditKotListAdapter;
import com.opus.restaurant.Adapters.KotOrdersViewAdapter;
import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.EditKot;
import com.opus.restaurant.Models.EditTablesDetails;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditBillViewActivity extends AppCompatActivity {

    private String local_id;
    private String kot_no;
    private String kot_no_via_intent;
    private ListView kotListview;
    private Button proceedBtn;
    private TextView kotNoTxt;
   private TextView txt_view_date,txt_view_table_type,txt_view_waiter_code,txt_view__table_list,txt_view_no_of_person;
    private DatabaseHandler db;
    private EditKotListAdapter kotListAdapter;
    ArrayList<CartItemModel> cart_items_list = new ArrayList<CartItemModel>();
    private ArrayList<EditKot> kot_lists;
    private ArrayList<EditTablesDetails> table_lists;
    public String sum_amount, ip_no;
    FloatingActionButton fab_add_button;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill_view);
        ActionBar bar = getSupportActionBar();
        bar.hide();
    /*    if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293C69")));
        }
        getSupportActionBar().setTitle("Summary");*/
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
       // edit_kot_localid = preference.getString("edit_kot_localid", "");
       // edit_kot_no = preference.getString("edit_kot_no", "");
        kot_no = preference.getString("server_kot_no", "");
        if(kot_no.equals(null)||kot_no.isEmpty())
            Toast.makeText(getApplicationContext(),"kotno is null",Toast.LENGTH_SHORT).show();
        local_id = preference.getString("local_kot", "");
        ip_no = preference.getString("ip_port_no", "");
        kot_no_via_intent=getIntent().getStringExtra("Kot_no");
        kot_lists = new ArrayList<EditKot>();
        fab_add_button=findViewById(R.id.fab_add_button);
        kotListview = (ListView) findViewById(R.id.edit_kot_listview);
        kotNoTxt = (TextView) findViewById(R.id.edit_kot_no_edit_bill_view);
        txt_view_date=findViewById(R.id.date_txtvw_edit_bill_view);
        txt_view_table_type=findViewById(R.id.txt_room_type_edit_bill_view);
        txt_view_waiter_code=findViewById(R.id.waiter_code_txt_view_edit_bill_view);
        txt_view__table_list=findViewById(R.id.txt_table_list_edit_bill_view);

        txt_view_no_of_person=findViewById(R.id.txt_no_of_person_edit_bill_view);
        //kotNoTxt.setText(kot_no);
        table_lists = new ArrayList<EditTablesDetails>();
        loadAllDetails();
        loadTableDetails();

        fab_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBillViewActivity.this, EditAddCategoryActivity.class);
                intent.putExtra("kot_no__",kot_no_via_intent);
                startActivity(intent);
                finish();
            }
        });

    }

    public void loadAllDetails() {
            try {
                kot_lists.clear();
                String ip_no = preference.getString("ip_port_no", "");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://"+ip_no+"/Service1.svc/getKotDetails";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = null;
                                    jsonArray = new JSONArray(response);
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String Amount=jsonObject.getString("Amount");
                                        String Items_names=jsonObject.getString("ItemNames");
                                        int each_item_rate=jsonObject.getInt("Rate");
                                        String quantity=jsonObject.getString("qty");
                                        int id = jsonObject.getInt("Id");
                                        EditKot cat = new EditKot();
                                        cat.setId(id);
                                        cat.setItem_name(Items_names);
                                        cat.setItem_qty(quantity);
                                        cat.setItem_total_rate(Amount);
                                        kot_lists.add(cat);
                                    }
                                    //totcal;
                                    kotListAdapter = new EditKotListAdapter(getApplicationContext(), R.layout.edit_kot_layout, kot_lists);
                                    kotListview.setAdapter(kotListAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            //TODO
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            //TODO
                        }
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                })
                {  //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //  Map<String, String> params = new HashMap<>();
                        HashMap<String, String> params = new HashMap<String, String>();

                        params.put("KotNo",kot_no_via_intent);
                        // params.put("localId",loc_ref_no);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
            catch (Exception e){

            }

/*
            kot_lists.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<CartItemModel> contact_array_from_db = db.Get_Bill_Details(kot_no_via_intent);
            for (int i = 0; i < contact_array_from_db.size(); i++) {
                String i_name = contact_array_from_db.get(i).getItem_name();
                String i_code = contact_array_from_db.get(i).getItem_code();
                String i_qty = contact_array_from_db.get(i).getQty();
                String i_price = contact_array_from_db.get(i).getPrice();
                String i_total = contact_array_from_db.get(i).getTotal_price();
                EditKot editKot = new EditKot();
                editKot.setItem_code(i_code);
                editKot.setItem_name(i_name);
                editKot.setItem_qty(i_qty);
                editKot.setItem_rate(i_price);
                editKot.setLocal_id(local_id);
                editKot.setItem_total_rate(i_total); //////////////////////hrf
                editKot.setKot_no(kot_no);
                kot_lists.add(editKot);
            }
            db.close();
            kotListAdapter = new EditKotListAdapter(getApplicationContext(), R.layout.edit_kot_layout, kot_lists);
            kotListview.setAdapter(kotListAdapter);
        } catch (Exception e) { }*/
    }
    private void loadTableDetails() { //////////////modified by hrf
        try {
            table_lists.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<EditTablesDetails> contact_array_from_db = db.Get_KOT_Tables(kot_no_via_intent);
                String table_name = contact_array_from_db.get(0).getTableName();
                String waiter_name = contact_array_from_db.get(0).getWaiterName();
                String order_type = contact_array_from_db.get(0).getOrderType();
                String server_kot = contact_array_from_db.get(0).getServerKot();
                String no_of_persons = contact_array_from_db.get(0).getNoofPersons();
                String room_type=contact_array_from_db.get(0).getRoomType();
               String[] edit_date=contact_array_from_db.get(0).getDate().split(" "); ////////////hrf
                txt_view_date.setText(edit_date[0]+edit_date[1]); ///////////////////////date_only
                txt_view_table_type.setText("T-type/"+order_type);
                txt_view_waiter_code.setText("W-code /"+waiter_name);
                txt_view__table_list.setText("Table/"+table_name);
                txt_view_no_of_person.setText("N.O.P  "+no_of_persons);
                kotNoTxt.setText(getIntent().getStringExtra("Kot_no"));
                preference.edit().putString("selected_table", table_name).putString("no_of_persons", no_of_persons).
                        putString("waiter_code", waiter_name).putString("order_type", order_type).putString("Edit_date",edit_date[0]+edit_date[1]).
                        putString("room_type",room_type).apply();
            db.close();
            }
        catch (Exception e){
            Log.d("KOT======>",""+e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}