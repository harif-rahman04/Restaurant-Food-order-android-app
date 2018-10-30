package com.opus.restaurant.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.opus.restaurant.Models.KotAddonList;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.R;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 11/28/2017.
 */

public class KotOrdersViewAdapter extends ArrayAdapter<KotOrdersView> {

    List<KotOrdersView> kotorders;
    Context context;
    DatabaseHandler db;
    ArrayList<KotOrdersView> kot_list = new ArrayList<KotOrdersView>();
    ArrayList<KotAddonList> addon_list = new ArrayList<KotAddonList>();
    public static final int MODE_PRIVATE = 0x0000;
    LinearLayout holder;

    public KotOrdersViewAdapter(Context context, int resource, ArrayList<KotOrdersView> items) {
        super(context, resource, items);
        kotorders = items;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.view_kot_list, parent, false);
        View v = convertView;
        KotOrdersView orders = kotorders.get(position);
        TextView item_name = (TextView)row.findViewById(R.id.view_kot_itemname);
        TextView item_price = (TextView)row.findViewById(R.id.view_kot_itemoprice);
        Button view_kot_qty = (Button)row.findViewById(R.id.view_kot_qty);
        String itemname = orders.getItemName();
        String itemprice = orders.getAmount();
        String itemqty = orders.getItemQty();
     /*   Animation animation = null;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_right);
        convertView.setAnimation(animation);
        animation.setDuration(200);
        convertView.startAnimation(animation);*/
        item_name.setText(itemname);
        item_price.setText(itemprice);
        view_kot_qty.setText(itemqty);
        return row;
    }


}
