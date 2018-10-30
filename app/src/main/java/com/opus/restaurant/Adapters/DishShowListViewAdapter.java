package com.opus.restaurant.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opus.restaurant.Models.BasicDetails;
import com.opus.restaurant.Models.Items;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.R;
import com.opus.restaurant.Services.APICallback;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
public class DishShowListViewAdapter extends ArrayAdapter<Items> implements APICallback,CallBack {
    List<Items> menus;
    List<BasicDetails> basic;
    Context context;
    Boolean flag=false;
    public static final int MODE_PRIVATE = 0x0000;
    private Calendar c = Calendar.getInstance();
    private DatabaseHandler db;
    private ArrayList<KotOrdersView> order_list ;
    String suggest_string="";
    EditText suugest_et;
    ImageButton tick_bt;
    LinearLayout suggest_ll;
    public DishShowListViewAdapter(Context context, int resource, List<Items> menuItems,List<BasicDetails> basicDetails) {
        super(context, resource, menuItems);
        menus = menuItems;
        basic = basicDetails;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.dishes_list, parent, false);
        final Items dish = menus.get(position);
        final BasicDetails basicdetails = basic.get(0);
        order_list = new ArrayList<KotOrdersView>();
        db = new DatabaseHandler(getContext().getApplicationContext());
      /*  int index = mList.getFirstVisiblePosition();
        View v = mList.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - mList.getPaddingTop());
        mList.setSelectionFromTop(index, top);*/
     /*   Animation animation = null;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_right);
        convertView.setAnimation(animation);
        animation.setDuration(200);
        convertView.startAnimation(animation);*/
      //  final SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
      //  final String local_id_ref = preferences.getString("local_refid", "");

        final String selected_table = basicdetails.getSelected_table_name();
        final String selected_waiter = basicdetails.getSelected_waiter_name();
        final String selected_category = basicdetails.getSelected_category();
        final String selected_no_of_persons = basicdetails.getNo_of_persons();

        final TextView dish_name            = (TextView) row.findViewById(R.id.dish_name_dishes_list);
        final TextView dish_price           = (TextView) row.findViewById(R.id.dish_price_dishes_list);
        //final TextView dish_id              = (TextView) row.findViewById(R.id.dish_id);
        final TextView dish_code            = (TextView) row.findViewById(R.id.dish_code__dishes_list);
        final Button add_btn                = row.findViewById(R.id.add_btn_dishes_list);
        final LinearLayout invisible_layout = (LinearLayout) row.findViewById(R.id.inv_dish_layout_dishes_list);
        final ImageButton plus_btn               = row.findViewById(R.id.btn_add_dishes_list);
        final ImageButton minus_btn              = row.findViewById(R.id.btn_minus__dishes_list);
        suugest_et=row.findViewById(R.id.suugest_et);
        tick_bt=row.findViewById(R.id.tick_bt);
       // final Button dish_addons_btn        = (Button) row.findViewById(R.id.dish_addons_btn);
        final TextView qty_range            = row.findViewById(R.id.qty_range_dishes_list);
     //  final TextView addon_id             = (TextView) row.findViewById(R.id.addon_id)
        //  final EditText suggest_et=row.findViewById(R.id.suggestion_et);
   //     final ImageButton suggest_tick=row.findViewById(R.id.sugeestion_tick_bt);

        tick_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                suugest_et=row.findViewById(R.id.suugest_et);
                suggest_string=suugest_et.getText().toString();
                if(TextUtils.isEmpty(suggest_string))
                    Toast.makeText(getContext(),"Please type",Toast.LENGTH_SHORT).show();
                else{
                  suggest_ll.setVisibility(View.GONE);
                  db.Update_Suggestion(new KotOrders(suggest_string,dish.getItemCode(),basicdetails.getKot_no()));
                    inputMethodManager.hideSoftInputFromInputMethod(suugest_et.getWindowToken(),0);
                } }
        });
        qty_range.setText("1");
        int d_id=dish.getId();
        String did = String.valueOf(d_id);
        String d_name=dish.getItemName();
        String d_price=dish.getCost();
        String d_code=dish.getItemCode();
        dish_name.setText(d_name);
        dish_price.setText(d_price);
        dish_code.setText(d_code);
      //  dish_id.setText(did);
    /*    qty_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(view.getRootView().getContext());
                View promptsView = li.inflate(R.layout.qty_prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setMessage("Enter the QTY");
                alertDialogBuilder.setIcon(R.drawable.persons);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        qty_range.setText(userInput.getText()); } })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
              }
        });*/

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                final String local_id_ref = preferences.getString("local_kot", "");
                Items dish = menus.get(position);
                int id = dish.getId();
                String itemName = dish.getItemName();
                String itemCode = dish.getItemCode();
                String itemRate = dish.getCost();
                order_list.clear();
                ArrayList<KotOrdersView> items = db.Get_Item_Qty(basicdetails.getKot_no(),itemCode);
                //KotOrdersView qtyNumber = new KotOrdersView();
                String qty_get = items.get(0).getItemQty();
                int qty = Integer.parseInt((qty_get));
                int post_qty = qty + 1;
                String final_add_qty = String.valueOf(post_qty);
                qty_range.setText(final_add_qty);

                // Calculate Total Price for items
                Float n_price = Float.parseFloat(itemRate);
                Float n_qty = Float.parseFloat(final_add_qty);
                Float new_tot = n_price * n_qty;
                String fin_tot = String.valueOf(new_tot);
                // Update Qty
                String kot_no=basicdetails.getKot_no();
                suugest_et=row.findViewById(R.id.suugest_et);
                String suggest_str=suugest_et.getText().toString();
                    db.Update_Cart_Qty(new KotOrders(final_add_qty,kot_no , itemCode, fin_tot,suggest_str));
                db.close();
            }
        });
        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                final String local_id_ref = preferences.getString("local_kot", "");
                Items dish = menus.get(position);
                int id = dish.getId();
                String itemName = dish.getItemName();
                String itemCode = dish.getItemCode();
                String itemRate = dish.getCost();
                order_list.clear();
                ArrayList<KotOrdersView> items = db.Get_Item_Qty(basicdetails.getKot_no(),itemCode);
                String final_add_qty="0";
                String qty_get = items.get(0).getItemQty();
                int qty = Integer.parseInt((qty_get));
                if(qty==1){
                    db.Delete_Cart_Items(itemCode, basicdetails.getKot_no());
                    invisible_layout.setVisibility(View.GONE);
                    add_btn.setVisibility(View.VISIBLE);
                }
                  else{
                        int post_qty = qty - 1;
                        final_add_qty = String.valueOf(post_qty);
                        qty_range.setText(final_add_qty);
                        Float n_price = Float.parseFloat(itemRate);
                        Float n_qty = Float.parseFloat(final_add_qty);
                        Float new_tot = n_price * n_qty;
                        String fin_tot = String.valueOf(new_tot);
                        // Update Qty
                        String kot_no=basicdetails.getKot_no();
                    suugest_et=row.findViewById(R.id.suugest_et);
                        String suggest_str=suugest_et.getText().toString();
                        db.Update_Cart_Qty(new KotOrders(final_add_qty,kot_no, itemCode, fin_tot,suggest_str));

                    }
                db.close();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_datetime = new SimpleDateFormat("dd-MM-yy hh:mm aa").format(Calendar.getInstance().getTime());
             //   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               // String current_date_time = df.format(c.getTime());
                invisible_layout.setVisibility(View.VISIBLE);
                add_btn.setVisibility(View.GONE);
                Items dish = menus.get(position);
                int id = dish.getId();
                int item_id = dish.getItemId();
                String items_code = dish.getItemCode();
                String items_price = dish.getCost();
                String items_name = dish.getItemName();
                String items_category = dish.getCategory();
                String items_suggestion = "";
                SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                String local_id    = preferences.getString("local_id", "");
                String order_status     = preferences.getString("order_status", "");
                String table_name       = preferences.getString("selected_table","");
                String waiter_name      = preferences.getString("waiter_code","");
                String no_of_persons    = preferences.getString("no_of_persons","");
                String order_type    = preferences.getString("order_type","");
                String Kot_No=preferences.getString("server_kot_no","");
                String room_type=preferences.getString("room_type","");
               // if (order_status.equals("1"))
               // {
                    int i_qty = 1;
                    String qty = String.valueOf(i_qty);
                    Float i_price = Float.parseFloat(items_price);
                    Float i_tot = i_price * i_qty;
                    String final_total = String.valueOf(i_tot);
/*if(local_kot_ref.isEmpty()||local_kot_ref.equals(null))
    Toast.makeText(context,"local id is null",Toast.LENGTH_SHORT).show();*/
                    db.Add_Kot_Orders(new KotOrders(local_id,table_name,local_id,waiter_name,no_of_persons,
                            items_code, items_name,items_price,"0.00",qty,final_total,current_datetime,
                            "0",basicdetails.getKot_no(),current_datetime,"I",room_type,items_suggestion,order_type));
            db.close();
                row.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        suggest_ll=row.findViewById(R.id.suggest_layout);
                        suggest_ll.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
            }
        });

     /*   dish_addons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        return row;
    }

    @Override
    public void onPostExecute(String result) {  }
    @Override
    public boolean isVisible() {
        return false;
    }
}



