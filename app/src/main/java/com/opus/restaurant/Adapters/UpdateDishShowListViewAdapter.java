package com.opus.restaurant.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.DishListModel;
import com.opus.restaurant.R;
import com.opus.restaurant.Services.APICallback;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateDishShowListViewAdapter extends ArrayAdapter<DishListModel> implements APICallback,CallBack {

    List<DishListModel> menus;
    Context context;
    String baseUrl,bearer;
    DatabaseHandler db;

    public static final int MODE_PRIVATE = 0x0000;
    String loc_ref_id,loc_kot_no;
    Calendar c = Calendar.getInstance();

    public UpdateDishShowListViewAdapter(Context context, int resource, List<DishListModel> menuItems) {
        super(context, resource, menuItems);
        menus = menuItems;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.update_dishes_list, parent, false);
        DishListModel dish = menus.get(position);
    /*    Animation animation = null;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_right);
        animation.setDuration(200);
        convertView.startAnimation(animation);*/

        final TextView dish_name=(TextView)row.findViewById(R.id.update_dishname);
        final TextView dish_name_title=(TextView)row.findViewById(R.id.update_dishname_title);
        final TextView dish_code_title=(TextView)row.findViewById(R.id.update_dishcode_title);
        final TextView dish_price_title=(TextView)row.findViewById(R.id.update_dishprice_title);

        final TextView dish_price  = (TextView) row.findViewById(R.id.update_dishprice);
        final TextView dish_id =(TextView)row.findViewById(R.id.update_dishid);
        final TextView dish_code=(TextView)row.findViewById(R.id.update_dishcode);
        final Button add_btn = (Button)row.findViewById(R.id.update_add_dishbtn);
        final LinearLayout invisible_layout = (LinearLayout)row.findViewById(R.id.update_invisible_dish_layout);
        final Button plus_btn = (Button)row.findViewById(R.id.update_btn_add);
        final Button minus_btn = (Button)row.findViewById(R.id.update_btn_minus);
        final EditText qty_range = (EditText)row.findViewById(R.id.update_qty_range);
        qty_range.setText("1");

        Typeface face= Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.ttf");
        dish_name.setTypeface(face);
        dish_price.setTypeface(face);
        dish_code.setTypeface(face);
        dish_name_title.setTypeface(face);
        dish_code_title.setTypeface(face);
        dish_price_title.setTypeface(face);
        add_btn.setTypeface(face);


        int d_id=dish.getDishid();
        String did = String.valueOf(d_id);
        String d_name=dish.getDishname();
        String d_price=dish.getDishprice();
        String d_code=dish.getDishcode();

        dish_name.setText(d_name);
        dish_price.setText(d_price);
        dish_code.setText(d_code);
        dish_id.setText(did);

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    DishListModel dish = menus.get(position);
                    // I saved itemcode in items id field. So get Item code instead of id
                    String d_code = dish.getDishcode();
                    String price = dish.getDishprice();
                    String local_id = dish.getLocalid();

                    // Get Default qty and process
                    String default_qty = qty_range.getText().toString();
                    int avail_qty = Integer.parseInt(default_qty);
                    int post_qty = avail_qty + 1;
                    String final_add_qty = String.valueOf(post_qty);
                    qty_range.setText(final_add_qty);

                    // Calculate Total Price for items
                    Float n_price = Float.parseFloat(price);
                    Float n_qty = Float.parseFloat(final_add_qty);
                    Float new_tot = n_price * n_qty;
                    String fin_tot = String.valueOf(new_tot);
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());
                    // Update Qty
                  //  dbHandler.Update_Cart_Qty(new CartItemModel(final_add_qty, local_id, d_code, fin_tot));
                  //  dbHandler.close();
                }
                catch (Exception e){
                    String err_msg = e.getMessage();
                    Toast.makeText(getContext().getApplicationContext(), err_msg , Toast.LENGTH_LONG).show();
                }
            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // I saved itemcode in items id field. So get Item code instead of id //
                    DishListModel dish = menus.get(position);
                    String d_code = dish.getDishcode();
                    String price = dish.getDishprice();
                    String local_id = dish.getLocalid();
                    String final_add_qty = "";

                    // Get Default qty and process
                    String default_qty = qty_range.getText().toString();
                    int avail_qty = Integer.parseInt(default_qty);
                    if (avail_qty > 1) {
                        int post_qty = avail_qty - 1;
                        final_add_qty = String.valueOf(post_qty);
                        qty_range.setText(final_add_qty);
                    }

                    // Calculate Total Price for items
                    Float n_price = Float.parseFloat(price);
                    Float n_qty = Float.parseFloat(final_add_qty);
                    Float new_tot = n_price * n_qty;
                    String fin_tot = String.valueOf(new_tot);
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());

                    // Update Qty in cart items
                  //  dbHandler.Update_Cart_Qty(new CartItemModel(final_add_qty, local_id, d_code, fin_tot));
                 //   dbHandler.close();

                }
                catch (Exception e){}
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DishListModel dish = menus.get(position);
                String items_code = dish.getDishcode();
                String items_price = dish.getDishprice();
                String items_name = dish.getDishname();
                String local_id = dish.getLocalid();
                String kot_no = dish.getKotno();
                int i_qty = 1;
                String qty = String.valueOf(i_qty);

                Float i_price = Float.parseFloat(items_price);
                Float i_tot = i_price * i_qty;
                String final_total = String.valueOf(i_tot);

                 // Add to Cart Items
              //  DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());
              //  dbHandler.Add_Kot_Items(new CartItemModel(items_code, qty, items_price, final_total, local_id, local_id,items_name));
                invisible_layout.setVisibility(View.VISIBLE);

            }
        });
        return row;
    }
    @Override
    public void onPostExecute(String result) {  }
    @Override
    public boolean isVisible() {
        return false;
    }
}
