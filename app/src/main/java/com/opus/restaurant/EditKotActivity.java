package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.opus.restaurant.Adapters.EditKotAdapter;
import com.opus.restaurant.Adapters.EditKotListAdapter;
import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.EditKot;
import com.opus.restaurant.Models.KotAddonList;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditKotActivity extends AppCompatActivity {

    private String local_id;
    private String kot_no;
    private ListView kotListview;
    private Button addNewItemBtn;
    private Button proceedBtn;
    private TextView kotNoTxt;
    private TextView waiter_code,table_type_txt_view,date_txt_view,table_list,no_of_per;
    private DatabaseHandler db;
    private EditKotAdapter kotListAdapter;
    private ImageButton edit_kot_additem;
    private View footer;
    private ArrayList<KotOrdersView> order_list;
    private TextView kotTotalTxt;
    ArrayList<CartItemModel> cart_items_list = new ArrayList<CartItemModel>();

    ArrayList<EditKot> kot_lists;
    String sum_amount,ip_no;

    //new variables
    String selected_category;
    String table_name;
    String waiter_name;
    String table_type;
    String no_of_persons;
    String edit_date;
    private SharedPreferences preference;
    String loc_ref_no,server_kot_no,room_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kot);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        preference = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
      //  local_id = preferences.getString("edit_kot_localid", "");
      //  kot_no = preferences.getString("edit_kot_no", "");
        kot_no = preferences.getString("server_kot_no", "");
        ip_no = preferences.getString("ip_port_no", "");
        local_id = preferences.getString("local_id","");

        try {
           // Intent intent = getIntent();

          //  selected_category = intent.getStringExtra("selected_category");
            table_name = preference.getString("selected_table","");
            table_type = preference.getString("room_type","");
            no_of_persons = preference.getString("no_of_persons","");
            waiter_name = preference.getString("waiter_code","");
            server_kot_no = preference.getString("server_kot_no","");
            edit_date=preference.getString("Edit_date","");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        kot_lists = new ArrayList<EditKot>();
        kotListview = (ListView)findViewById(R.id.edit_kot_listview);
        kotNoTxt = (TextView)findViewById(R.id.edit_kot_no_edit_kot_act);
       // addNewItemBtn = (Button)findViewById(R.id.edit_kot_newitem);
        proceedBtn = (Button)findViewById(R.id.edit_kot_proceed);
        edit_kot_additem = findViewById(R.id.edit_kot_additem__edit_kot_act);
        kotTotalTxt = (TextView)findViewById(R.id.edit_kot_total);
        waiter_code=findViewById(R.id.waiter_code__edit_kot_act);
        table_type_txt_view=findViewById(R.id.txt_room_type__edit_kot_act);
        date_txt_view=findViewById(R.id.date_txtvw__edit_kot_act);
        table_list=findViewById(R.id.table_list_edit_kot);
        no_of_per=findViewById(R.id.no_of_person_edit_kot);
        if(table_type.equals("0")){
            table_type_txt_view.setVisibility(View.GONE);
        }
        if(table_name.equals("0")){
            table_list.setVisibility(View.GONE);
        }
        kotNoTxt.setText("K/NO :"+kot_no);
        date_txt_view.setText(edit_date);
        table_type_txt_view.setText("T-type :"+table_type);
        waiter_code.setText("W-code :"+waiter_name);
        no_of_per.setText("N.O.P---"+no_of_persons);
        table_list.setText("T/"+table_name);
        order_list = new ArrayList<KotOrdersView>();
        loadAllDetails();
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendKot();
            }
        });

      /*  addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(EditKotActivity.this,EditAddItemActivity.class);
              //  startActivity(intent);
              //  Intent intent = new Intent(EditKotActivity.this,EditAddCategoryActivity.class);
             //   startActivity(intent);
            }
        });*/

        edit_kot_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(EditKotActivity.this,MainCategoryActivity.class);
               startActivity(intent);
               finish();

            }
        });

    }

    public void loadAllDetails(){
        try
        {
            kot_lists.clear();
            db = new DatabaseHandler(getApplicationContext());
            ArrayList<CartItemModel> contact_array_from_db = db.Get_Bill_Details(server_kot_no);
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
                editKot.setKot_no(kot_no);
                kot_lists.add(editKot);
            }
            db.close();
            totalCalculation();
            kotListAdapter = new EditKotAdapter(getApplicationContext(), R.layout.edit_kot_layout, kot_lists,EditKotActivity.this);
            kotListview.setAdapter(kotListAdapter);
        }
        catch (Exception e){ }

    }
    private void totalCalculation() {
        order_list.clear();
        db = new DatabaseHandler(getApplicationContext());
        if(kot_no.equals(null)|| kot_no.isEmpty())
            Toast.makeText(getApplicationContext(),"kot no is null",Toast.LENGTH_SHORT).show();
        ArrayList<KotOrdersView> items = db.Get_KOT_Orders_Items(kot_no);
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
            order_list.add(cat);
        }

        double sub_total = 0.0;
        double grand_total = 0.0;
        footer = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bill_total_layout, null);

        TextView sub_total_textview = (TextView) footer.findViewById(R.id.sub_total);
        TextView sub_tax_textview = (TextView) footer.findViewById(R.id.sub_tax);
        final TextView grand_total_textview = (TextView) footer.findViewById(R.id.grand_total);
       // Button footer_save_btn = (Button) footer.findViewById(R.id.view_kot_submit);
        //Button footer_pay_btn = (Button) footer.findViewById(R.id.view_kot_pay);
        for (int i = 0; i < order_list.size(); i++) {
            sub_total = sub_total + Double.parseDouble(order_list.get(i).getAmount());
        }

        String s_total = String.valueOf(sub_total);
        grand_total = sub_total;
        String g_total = String.valueOf(grand_total);
        kotTotalTxt.setText(g_total);
      //  sub_total_textview.setText(s_total);
      //  grand_total_textview.setText(g_total);
      //  kotListview.removeFooterView(footer);
        //kotListview.addFooterView(footer);
    }

    public void sendKot(){
        try
        {
            ip_no = preference.getString("ip_port_no", "");

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String ipno=ip_no;
            String url = "http://"+ipno+"/Service1.svc/kotdetails";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                DatabaseHandler db_prog = new DatabaseHandler(getApplicationContext());
                                db_prog.Add_Server_Kot(new KotNoListModel(local_id, server_kot_no, "0"));
                                db_prog.Update_Cart_Status(new KotOrders("1",server_kot_no));
                                String res = response;
                                ////// clearing the preference ....hrf....
                                preference.edit().putString("server_kot_no","").putString("local_id","").putString("selected_table","").
                                        putString("roomtype","").putString("no_of_persons","").putString("server_kot_no","").
                                        putString("Edit_date","").commit();
                                Toast.makeText(getApplicationContext(),"Kot processed",Toast.LENGTH_SHORT).show();
                                preference.edit().putString("order_status", "0").commit();
                                Intent intent = new Intent(EditKotActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            catch (Exception e){}
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
                    order_list.clear();
                    db = new DatabaseHandler(getApplicationContext());
                    HashMap<String, String> params = new HashMap<String, String>();
                    String final_code = "";
                    String final_qty = "";
                    String suggestion="";
                    if(kot_no.equals(null)|| kot_no.isEmpty())
                        Toast.makeText(getApplicationContext(),"kot no is null",Toast.LENGTH_SHORT).show();
                    ArrayList<KotOrdersView> items = db.Get_KOT_Orders_Items(kot_no);
                    for (int i = 0; i < items.size(); i++) {
                        int id = items.get(i).getId();
                        String name = items.get(i).getItemName();
                        String code = items.get(i).getItemCode();
                        String qty = items.get(i).getItemQty();
                        String amount = items.get(i).getAmount();
                       suggestion +=items.get(i).getSuggestion();
                        final_code+=code;
                        final_qty+=qty;
                        if (i!=params.size()-1)
                        {
                            final_code+=",";
                            final_qty+=",";
                            if(!TextUtils.isEmpty(suggestion))
                            {
                            suggestion+=",";
                        } }}
                    db.close();
                    String f_code = final_code.substring(0, final_code.lastIndexOf(","));
                    String f_qty = final_qty.substring(0, final_qty.lastIndexOf(","));
                    if(!TextUtils.isEmpty(suggestion)){
                        String final_sugg=suggestion.substring(0, suggestion.lastIndexOf(","));
                        params.put("Suggestion",final_sugg); }
                    params.put("Items",f_code);
                    params.put("qty",f_qty);
                    params.put("TbvTable",table_name);
                    params.put("TbvPerson",no_of_persons);
                    // String newKotNo =  server_kot_no.substring(1, server_kot_no.length() - 1);
                    params.put("KotNo",server_kot_no);
                    params.put("tableName",table_name);
                    params.put("WaiterCode",waiter_name);
                    params.put("Pcnt",no_of_persons);
                    params.put("RoomType",table_type);
                    params.put("localId",local_id);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
        catch (Exception e){}
    }
}
