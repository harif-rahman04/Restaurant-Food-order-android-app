package com.opus.restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import com.opus.restaurant.Adapters.KotListAdapter;
import com.opus.restaurant.Adapters.KotOrdersViewAdapter;
import com.opus.restaurant.Models.KotAddonList;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;
import com.opus.restaurant.SwipeListview.SwipeMenu;
import com.opus.restaurant.SwipeListview.SwipeMenuCreator;
import com.opus.restaurant.SwipeListview.SwipeMenuItem;
import com.opus.restaurant.SwipeListview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KotSummaryActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ArrayList<KotOrdersView> order_list;
    private KotOrdersViewAdapter dAdapter;
    private ListView listView;
    private View footer;
    String table_name;
    String waiter;
    String no_of_persons;
    String local_kot;
    String local_id;
    String kot_no_via_intent,kot_no,edit_date,table_type;

    ArrayList<KotAddonList> add;
    private Button proceedBtn;
    private SharedPreferences prefs;
    TextView txt_view_kot_no,txt_view_date,txt_view_table_type,txt_view_waiter_code,txt_view__table_list;
    private String ipNo;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kot_summary);
        ActionBar bar = getSupportActionBar();
        bar.hide();
       /* if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610")));
        }
        getSupportActionBar().setTitle("Kot summary");*/

        preferences  = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        table_name = preferences.getString("selected_table", "");
        waiter = preferences.getString("waiter_code", "");
        no_of_persons = preferences.getString("no_of_persons", "");
        ipNo = preferences.getString("ip_port_no","");
        kot_no = preferences.getString("server_kot_no", "");
        edit_date=preferences.getString("Edit_date","");
        table_type = preferences.getString("selected_table_type","");
        txt_view_kot_no=findViewById(R.id.edit_kot_no_kot_summary);
        txt_view_date=findViewById(R.id.date_txtvw_edit_all_kot_kot_summary);
        txt_view_table_type=findViewById(R.id.txt_room_type_kot_summary);
        txt_view_waiter_code=findViewById(R.id.waiter_code_txt_view_kot_summary);
        txt_view__table_list=findViewById(R.id.tables_kot_summary);
        listView = findViewById(R.id.listView);
        proceedBtn = (Button)findViewById(R.id.checkout_btn_kot_summary);
        kot_no_via_intent=getIntent().getStringExtra("Kot_no");
        order_list = new ArrayList<KotOrdersView>();
        setdetails(kot_no_via_intent);
       /* txt_view_kot_no.setText(kot_no);
        txt_view_date.setText(edit_date);
        txt_view_table_type.setText(table_type);
        txt_view_waiter_code.setText(waiter);
        txt_view__table_list.setText(table_name);*/
      /*  Intent intent =getIntent();
        if (intent.hasExtra("code"))
        {
            local_kot = intent.getStringExtra("code");
            server_kot = intent.getStringExtra("server_code");
            setdetails(local_kot);                                           ///////////////////
        }
        else
        {
            local_kot = preferences.getString("local_kot", "");
        }*/

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final String requestURL = "http://" + ipNo + "/Service1.svc/InvoiceDetails";
                    RequestQueue queue = Volley.newRequestQueue(KotSummaryActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String status = response;
                                    if (status.equals("1"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Checkout success",Toast.LENGTH_SHORT).show();
                                    }
                                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                    SQLiteDatabase dbb = db.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("status", "1");
                                    dbb.update("kot", values, "locid" + " = ?", new String[]{local_id});
                                    preferences.edit().putString("order_type","").putString("selected_table","").putString("selected_table_type","").putString("no_of_persons","").putString("server_kot_no","").putString("local_kot","").commit();
                                    finish();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                                Toast.makeText(getApplicationContext(), "Sorry! Server problem", Toast.LENGTH_SHORT).show();
                            }
                            // _response.setText("That didn't work!");
                        }
                    }) {
                        //adding parameters to the request
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("KotNo", kot_no_via_intent);
                            params.put("LocalId", local_id);
                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                } catch (Exception e) {
                }
            }
        });
try{
        order_list.clear();
        String ip_no = preferences.getString("ip_port_no", "");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://"+ip_no+"/Service1.svc/getKotDetails";
        // String url = "http://192.168.1.14:6060/Service1.svc/Kotdetails";
        // HashMap<String, String> params = new HashMap<String, String>()
        //String kot_no=preferences.getString("server_kot_no","");
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
                                KotOrdersView cat = new KotOrdersView();
                                cat.setId(id);
                                cat.setItemName(Items_names);
                                cat.setItemQty(quantity);
                                cat.setAmount(Amount);
                                order_list.add(cat);
                            }
                            totalCalculation();
                            dAdapter = new KotOrdersViewAdapter(getApplicationContext(), R.layout.dishes_list, order_list);
                            listView.setAdapter(dAdapter);
                            dAdapter.notifyDataSetChanged();
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
        catch (Exception e){}
   /*     db = new DatabaseHandler(getApplicationContext());
        ArrayList<KotOrdersView> items = db.Get_KOT_Orders_Items(local_kot);
        for (int i = 0; i < items.size(); i++) {
            int id = items.get(i).getId();
            String name = items.get(i).getItemName();
            String code = items.get(i).getItemCode();
            String qty = items.get(i).getItemQty();
            String amount = items.get(i).getAmount();

            ArrayList<KotAddonList> addons = db.Get_KOT_Addons_Items(local_kot, code);
            add = new ArrayList<KotAddonList>();
            for (int j = 0; j < addons.size(); j++) {
                KotAddonList addonList = new KotAddonList();
                int addon_id = addons.get(j).getAddonId();
                String addon_name = addons.get(j).getAddonName();
                String addon_qty = addons.get(j).getAddonQty();
                String addon_amount = addons.get(j).getAddonAmount();

                addonList.setAddonId(addon_id);
                addonList.setAddonName(addon_name);
                addonList.setAddonQty(addon_qty);
                addonList.setAddonAmount(addon_amount);
                add.add(addonList);
            }
            KotOrdersView cat = new KotOrdersView();
            cat.setId(id);
            cat.setItemName(name);
            cat.setItemCode(code);
            cat.setItemQty(qty);
            cat.setAmount(amount);
            cat.setAddonlist(add);
            order_list.add(cat);
        }
        db.close();*/
        totalCalculation();
        dAdapter = new KotOrdersViewAdapter(getApplicationContext(), R.layout.dishes_list, order_list);
        listView.setAdapter(dAdapter);
        dAdapter.notifyDataSetChanged();
/*
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setWidth(dp2px(90));
                item1.setBackground(R.color.colorLightGrey);
                item1.setIcon(R.drawable.del);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(R.color.colorLightGrey);
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.edit);
                menu.addMenuItem(item2);
            }
        };*/

      /*  listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                KotOrdersView item = order_list.get(position);
                switch (index) {
                    case 0:
                        order_list.remove(position);
                        String item_name = item.getItemName();
                        listView.removeFooterView(footer);
                        listView.setAdapter(dAdapter);
                        dAdapter.notifyDataSetChanged();
                        totalCalculation();
                        break;
                    case 1:

                        order_list.remove(position);
                        break;
                }
                return false;
            }
        });*/
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void totalCalculation() {

        double sub_total = 0.0;
        double grand_total = 0.0;
        footer = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bill_total_layout, null);

        TextView sub_total_textview = (TextView) footer.findViewById(R.id.sub_total);
        TextView sub_tax_textview = (TextView) footer.findViewById(R.id.sub_tax);
        final TextView grand_total_textview = (TextView) footer.findViewById(R.id.grand_total);
        Button footer_save_btn = (Button) footer.findViewById(R.id.view_kot_submit);
        Button footer_pay_btn = (Button) footer.findViewById(R.id.view_kot_pay);
/*
        footer_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        footer_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double totalAmount = Double.parseDouble(String.valueOf(grand_total_textview.getText()));
                    //Intent intent = new Intent(ExampleActivity.this, BillActivity.class);
                    //intent.putExtra("totalAmount", totalAmount);
                    // startActivity(intent);
                } catch (Exception e) {
                }
            }
        });*/

        for (int i = 0; i < order_list.size(); i++) {
            sub_total = sub_total + Double.parseDouble(order_list.get(i).getAmount());
        }

        String s_total = String.valueOf(sub_total);
        grand_total = sub_total;
        String g_total = String.valueOf(grand_total);
        sub_total_textview.setText(s_total);
        grand_total_textview.setText(g_total);
        String tot="Total="+g_total;
        g_total="\n"+"Checkout";
        SpannableString ss1=  new SpannableString(tot+g_total);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,tot.length(), 0); // set size
        //ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        proceedBtn.setText(ss1);
        //listView.addFooterView(footer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void setdetails(String kott_no){
        try{
            ArrayList<KotOrders> kot_details=new DatabaseHandler(getApplicationContext()).Get_kot_details(kott_no);
            local_id=kot_details.get(0).getLocalId();
        txt_view_kot_no.setText("K-no /"+getIntent().getStringExtra("Kot_no")); //////////////////////getting values from the intent
        txt_view_table_type.setText("R-type /"+kot_details.get(0).getRoomType());
        txt_view_waiter_code.setText("W-code /"+kot_details.get(0).getWaiterCode());
        if(kot_details.get(0).getTableName().equals("0")){
            txt_view__table_list.setVisibility(View.GONE);
        }
        else
        txt_view__table_list.setText("Table/"+kot_details.get(0).getTableName());
        String[] date_and_time=kot_details.get(0).getModifiedDate().split("\\s");  ////////splitting the date and time
            txt_view_date.setText(date_and_time[0]+"\n"+date_and_time[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
