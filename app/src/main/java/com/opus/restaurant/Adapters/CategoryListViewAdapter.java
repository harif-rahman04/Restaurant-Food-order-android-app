package com.opus.restaurant.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.opus.restaurant.Models.Category;
import com.opus.restaurant.R;
import com.opus.restaurant.Services.APICallback;
import com.opus.restaurant.Services.CallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/6/2017.
 */

public class CategoryListViewAdapter extends ArrayAdapter<Category> implements APICallback,CallBack {


    List<Category> menus;
    Context context;
    ArrayList<Category> category_list = new ArrayList<Category>();
    public static final int MODE_PRIVATE = 0x0000;
    public SharedPreferences preference;
    public String types;

    public CategoryListViewAdapter(Context context, int resource, List<Category> menuItems) {
        super(context, resource, menuItems);
        menus = menuItems;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.categorylist, parent, false);
        Category category = menus.get(position);
   /*    Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
        convertView.setAnimation(animation);
        convertView.animate().setDuration(200);*/
        final TextView category_name    = (TextView)row.findViewById(R.id.category_list_name);
       // final TextView category_id      = (TextView)row.findViewById(R.id.category_list_id);
        final Button category_caption   = (Button)row.findViewById(R.id.category_caption);

        String c_name                   = category.getCategory();
        int c_id                        = category.getCid();
        String cid                      = String.valueOf(c_id);

        category_name.setText(c_name);
        //category_id.setText(cid);
        String captions=c_name.substring(0,1);
        category_caption.setText(captions);
        return row;
    }
    @Override
    public void onPostExecute(String result) {  }
    @Override
    public boolean isVisible() {
        return false;
    }
}
