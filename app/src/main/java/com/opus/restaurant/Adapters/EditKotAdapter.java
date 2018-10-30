package com.opus.restaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.opus.restaurant.EditKotActivity;
import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.EditKot;
import com.opus.restaurant.R;
import com.opus.restaurant.Services.APICallback;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.Calendar;
import java.util.List;

public class EditKotAdapter extends ArrayAdapter<EditKot> implements APICallback,CallBack {

    List<EditKot> menus;
    Context context;

    DatabaseHandler db;

    public static final int MODE_PRIVATE = 0x0000;

    Calendar c = Calendar.getInstance();
    TextView kot_item_name;
    TextView kot_item_code ;

    ImageButton add_qty_btn ;
    ImageButton minus_qty_btn ;
    ImageButton del_qty_btn;
    String local_id_ref;
    EditKotActivity editKotActivity;

    public EditKotAdapter(Context context, int resource, List<EditKot> menuItems, EditKotActivity editKotActivity) {
        super(context, resource, menuItems);
        menus = menuItems;
        this.context = context;
        this.editKotActivity = editKotActivity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.edit_kot_layout, parent, false);
       final EditKot editKot = menus.get(position);
     /*   Animation animation = null;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_right);
        convertView.setAnimation(animation);
        animation.setDuration(200);
        convertView.startAnimation(animation);*/
       kot_item_name = (TextView)row.findViewById(R.id.edit_kot_itemname);
       kot_item_code = (TextView)row.findViewById(R.id.edit_kot_itemcode);
      final TextView kot_item_qty = (TextView)row.findViewById(R.id.edit_kot_qty);
       add_qty_btn = (ImageButton)row.findViewById(R.id.edit_kot_add);
       minus_qty_btn = (ImageButton)row.findViewById(R.id.edit_kot_minus);
       del_qty_btn = (ImageButton)row.findViewById(R.id.edit_kot_del);
        String itemCode = editKot.getItem_code();
        String itemName = editKot.getItem_name();
        String itemQty = editKot.getItem_qty();
        kot_item_code.setText(itemCode);
        kot_item_name.setText(itemName);
        kot_item_qty.setText(itemQty);
        add_qty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   // final SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                   // final String local_id_ref = preferences.getString("local_refid", "");

                    EditKot kotEdit = menus.get(position);
                    String d_code = kotEdit.getItem_code();
                    String price = kotEdit.getItem_rate();
                    local_id_ref = kotEdit.getLocal_id();

                    // Get Default qty and process
                    String default_qty = kot_item_qty.getText().toString();
                    int avail_qty = Integer.parseInt(default_qty);
                    int post_qty = avail_qty + 1;
                    String final_add_qty = String.valueOf(post_qty);

                    // Calculate Total Price for items
                    Float n_price = Float.parseFloat(price);
                    Float n_qty = Float.parseFloat(final_add_qty);
                    Float new_tot = n_price * n_qty;
                    String fin_tot = String.valueOf(new_tot);
                    kot_item_qty.setText(final_add_qty);
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());
                    // Update Qty
                    String kott_no=editKot.getKot_no();
                   dbHandler.Update_Cart_Qtys(new CartItemModel(final_add_qty,kott_no , d_code, fin_tot));
                   dbHandler.close();
                    editKotActivity.loadAllDetails();
                }
                catch (Exception e){

                    String err_msg = e.getMessage();
                    Toast.makeText(getContext().getApplicationContext(), err_msg , Toast.LENGTH_LONG).show();
                }
            }
        });

        minus_qty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // I saved itemcode in items id field. So get Item code instead of id //
                    EditKot kotEdit = menus.get(position);
                    String d_code = kotEdit.getItem_code();
                    String price = kotEdit.getItem_rate();
                    local_id_ref = kotEdit.getLocal_id();
                    String final_add_qty = "";

                    // Get Default qty and process
                    String default_qty = kot_item_qty.getText().toString();
                    int avail_qty = Integer.parseInt(default_qty);
                    if (avail_qty > 1) {
                        int post_qty = avail_qty - 1;
                        final_add_qty = String.valueOf(post_qty);
                        kot_item_qty.setText(final_add_qty);
                    }
                    // Calculate Total Price for items
                    Float n_price = Float.parseFloat(price);
                    Float n_qty = Float.parseFloat(final_add_qty);
                    Float new_tot = n_price * n_qty;
                    String fin_tot = String.valueOf(new_tot);
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());
                    // Update Qty in cart items
                    String kott_no=editKot.getKot_no();
                   dbHandler.Update_Cart_Qtys(new CartItemModel(final_add_qty, kott_no, d_code, fin_tot));
                   dbHandler.close();
                    editKotActivity.loadAllDetails();

                }
                catch (Exception e){}
            }
        });

        del_qty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    EditKot kotEdit = menus.get(position);
                    String d_code = kotEdit.getItem_code();
                    local_id_ref = kotEdit.getLocal_id();
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext().getApplicationContext());
                    dbHandler.Delete_Cart_Items(d_code, editKot.getKot_no());
                    dbHandler.close();
                    menus.remove(position);
                    notifyDataSetChanged();
                    editKotActivity.loadAllDetails();
                }
                catch (Exception e){

                }
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
