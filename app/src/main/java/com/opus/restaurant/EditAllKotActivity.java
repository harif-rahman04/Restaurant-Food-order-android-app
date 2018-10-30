package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.Adapters.EditKotListAdapter;
import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.EditKot;
import com.opus.restaurant.Models.EditTablesDetails;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditAllKotActivity extends AppCompatActivity {

    private String local_id;
    private String kot_no;
    private ListView kotListview;
    private Button addNewItemBtn;
    private Button proceedBtn;
    private TextView kotNoTxt;
    private TextView waiter_code_txt_view;
    private TextView room_type_txt_view;
    private TextView date_txt_view;
    private TextView table_list_txt_view;
    private DatabaseHandler db;
    String kot_no_via_intent;
    private EditKotListAdapter kotListAdapter;
    private  ArrayList<CartItemModel> cart_items_list = new ArrayList<CartItemModel>();
    private ArrayList<EditKot> kot_lists;
    private ArrayList<EditTablesDetails> table_lists;
    private String sum_amount, ip_no;
    private SharedPreferences preference;
    private ArrayList<KotOrdersView> order_list;
    private String loc_ref_no;
    private String table_name;
    private String waiter_name;
    private String table_type;
    private String no_of_persons;
    private String server_kot_no;
    private String edit_date;
    private TextView kotTotalTxt;
    ArrayList<KotOrdersView> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_kot);
        ActionBar bar = getSupportActionBar();
       /* if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293C69")));
        }*/
        getSupportActionBar().hide();
        kot_no_via_intent=getIntent().getStringExtra("kot_no");
        waiter_code_txt_view=findViewById(R.id.waiter_code_txt_view_edit_all_kot);
        room_type_txt_view=findViewById(R.id.txt_room_type_edit_all_kot);
        kotListview = (ListView) findViewById(R.id.edit_kot_listview);
        kotNoTxt = (TextView) findViewById(R.id.edit_kot_no_edit_all_kot);
        // addNewItemBtn = (Button) findViewById(R.id.edit_kot_newitem);
        proceedBtn = (Button) findViewById(R.id.edit_kot_proceed);
        kotTotalTxt = (TextView)findViewById(R.id.edit_kot_total);
        date_txt_view=findViewById(R.id.date_txtvw_edit_all_kot);
        table_list_txt_view=findViewById(R.id.table_list_edit_all_kot);
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        db = new DatabaseHandler(getApplicationContext());
       // edit_kot_localid = preference.getString("edit_kot_localid", "");
       // edit_kot_no = preference.getString("edit_kot_no", "");
        kot_no = preference.getString("server_kot_no", "");
        edit_date=preference.getString("Edit_date","");
        table_type = preference.getString("selected_table_type","");
        local_id = preference.getString("local_kot", "");
        ip_no = preference.getString("ip_port_no", "");
        server_kot_no = preference.getString("server_kot_no","");
        table_name = preference.getString("selected_table","");
        no_of_persons = preference.getString("no_of_persons","");
        waiter_name = preference.getString("waiter_code","");
        kot_lists = new ArrayList<EditKot>();
        //waiter_code_txt_view.setText(waiter_name);
        table_lists = new ArrayList<EditTablesDetails>();
        order_list = new ArrayList<KotOrdersView>();
        loadAllDetails();
        loadTableDetails();
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKot();
            }
        });
    }
    public void loadAllDetails() {
        try {
            kot_lists.clear();
            items=db.Get_KOT_Orders_Unfinished(kot_no_via_intent);
            if(items.size()==0){
                proceedBtn.setVisibility(View.GONE);
            }
            ArrayList<CartItemModel> contact_array_from_db = db.Get_Bill_Details(kot_no_via_intent);
            for (int i = contact_array_from_db.size()-1; i >=0;  i--) {
                String i_name = contact_array_from_db.get(i).getItem_name();
                String i_code = contact_array_from_db.get(i).getItem_code();
                String i_qty = contact_array_from_db.get(i).getQty();
                String i_price = contact_array_from_db.get(i).getPrice();
                String i_total = contact_array_from_db.get(i).getTotal_price();
                String finised_at=contact_array_from_db.get(i).getFinished_at(); ////////
                EditKot editKot = new EditKot();
                editKot.setItem_code(i_code);
                editKot.setItem_name(i_name);
                editKot.setItem_qty(i_qty);
                editKot.setItem_rate(i_price);
                editKot.setFinishedAt(finised_at);  //////////
                editKot.setLocal_id(local_id);
                editKot.setKot_no(kot_no_via_intent);
                editKot.setItem_total_rate(i_total);
                kot_lists.add(editKot);
            }
            db.close();
            kotListAdapter = new EditKotListAdapter(getApplicationContext(), R.layout.edit_kot_layout, kot_lists,EditAllKotActivity.this);
            kotListview.setAdapter(kotListAdapter);
            totalCalculation();
        } catch (Exception e) {
        }}
    private void loadTableDetails(){
            table_lists.clear();
            ArrayList<EditTablesDetails> contact_array_from_db = db.Get_KOT_Tables(kot_no_via_intent);
                String table_name = contact_array_from_db.get(0).getTableName();
                String waiter_name = contact_array_from_db.get(0).getWaiterName(); //editDate,edit_date,_editDate
                String order_type = contact_array_from_db.get(0).getOrderType();
                String server_kot = contact_array_from_db.get(0).getServerKot();
                String no_of_persons = contact_array_from_db.get(0).getNoofPersons();
                String[] edit_date=contact_array_from_db.get(0).getDate().split("\\s"); ////////////hrf
                waiter_code_txt_view.setText("W-code /"+waiter_name);
                room_type_txt_view.setText("O-type /"+table_type);
                //kotNoTxt.setText();kot_no
                date_txt_view.setText(edit_date[0]+edit_date[1]);
                table_list_txt_view.setText(table_name);
                kotNoTxt.setText(""+kot_no_via_intent);
                preference.edit().putString("selected_table", table_name).putString("selected_table_type", order_type).putString("no_of_persons", no_of_persons).
                        putString("waiter_code", waiter_name).putString("order_type", order_type).putString("Edit_date",edit_date[0]).commit();
            db.close();
    }
    public void sendKot(){
        try {
            ip_no = preference.getString("ip_port_no", "");
            loc_ref_no = preference.getString("local_kot", "");
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String ipno=ip_no;
            String url = "http://"+ipno+"/Service1.svc/kotdetails";
            // String url = "http://192.168.1.14:6060/Service1.svc/Kotdetails";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                db.Update_Cart_Status(new KotOrders("1",kot_no_via_intent));

                                String res = response;
                                Toast.makeText(getApplicationContext(),"Kot processed",Toast.LENGTH_SHORT).show();
                                preference.edit().putString("order_status", "0").commit();
                                Intent intent = new Intent(EditAllKotActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            catch (Exception e){
                                String re=e.getMessage();
                            }
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
                    String suggestion="";
                    HashMap<String, String> params = new HashMap<String, String>();
                    items=db.Get_KOT_Orders_Unfinished(kot_no_via_intent);
                    String final_code = "";
                    String final_qty = "";
                        for (int i = 0; i < items.size(); i++) {
                            int id = items.get(i).getId();
                            String name = items.get(i).getItemName();
                            String code = items.get(i).getItemCode();
                            String qty = items.get(i).getItemQty();
                            String amount = items.get(i).getAmount();
                            suggestion=items.get(i).getSuggestion();
                            final_code+=code;
                            final_qty+=qty;
                            if (i!=params.size()-1)
                            {
                                final_code+=",";
                                final_qty+=",";
                            } }
                        db.close();
                        String f_code = final_code.substring(0, final_code.lastIndexOf(","));
                        String f_qty = final_qty.substring(0, final_qty.lastIndexOf(","));
                        params.put("Items",f_code);
                        params.put("qty",f_qty);
                        params.put("TbvTable",table_name);
                        params.put("TbvPerson",no_of_persons);
                        // String newKotNo =  server_kot_no.substring(1, server_kot_no.length() - 1);
                        params.put("KotNo",kot_no_via_intent);
                        params.put("tableName",table_name);
                        params.put("WaiterCode",waiter_name);
                        params.put("Pcnt",no_of_persons);
                        params.put("Suggestion",suggestion);
                        params.put("RoomType",table_type);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
        catch (Exception e){

        }
    }

    private void totalCalculation() {
        order_list.clear();
        db = new DatabaseHandler(getApplicationContext());
        ArrayList<KotOrdersView> items = db.Get_KOT_Orders_Items(kot_no_via_intent);
        for (int i = 0; i < items.size(); i++) {
            int id = items.get(i).getId();
            String name = items.get(i).getItemName();
            String code = items.get(i).getItemCode();
            String qty = items.get(i).getItemQty();
            String amount = items.get(i).getAmount();
            KotOrdersView cat = new KotOrdersView();
            cat.setId(id);
            cat.setItemName(name);
            cat.setItemCode(code);
            cat.setItemQty(qty);
            cat.setAmount(amount);
            order_list.add(cat); }
        double sub_total = 0.0;
        double grand_total = 0.0;
        for (int i = 0; i < order_list.size(); i++) {
            sub_total = sub_total + Double.parseDouble(order_list.get(i).getAmount()); }
        String s_total = String.valueOf(sub_total);
        grand_total = sub_total;
        String g_total = String.valueOf(grand_total);
        kotTotalTxt.setText(g_total);
    }
}