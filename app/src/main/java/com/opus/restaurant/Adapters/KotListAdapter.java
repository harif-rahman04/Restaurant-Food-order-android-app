package com.opus.restaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.opus.restaurant.EditAllKotActivity;
import com.opus.restaurant.EditBillViewActivity;
import com.opus.restaurant.EditKotActivity;
import com.opus.restaurant.KotSummaryActivity;
import com.opus.restaurant.KotlistActivity;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.R;
import com.opus.restaurant.Services.APICallback;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;
import com.opus.restaurant.ViewRunningOrderActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 8/10/2017.
 */

public class KotListAdapter extends ArrayAdapter<String> implements APICallback,CallBack {

/*
    List<KotNoListModel> menus;*/
    Context context;
    ArrayList<String> kot_list;
    String baseUrl,bearer;
    String kot_list_loop;
    String sum_amount,server_kotno;
    private static final int MODE_PRIVATE =  0x0000;
    String local_id;
    String kotNo;
    String ip_no;
    Typeface typeface;

    public KotListAdapter(Context context, int resource,ArrayList<String> kot_list_r) {
        super(context, resource, kot_list_r);
       kot_list=kot_list_r;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.kot_list, parent, false);
        /*KotNoListModel kot = .get(position);*/
        kot_list_loop=kot_list.get(position);
        final TextView kot_no=(TextView)row.findViewById(R.id.kot_no);
        final TextView kot_type=(TextView)row.findViewById(R.id.kot_type_kot_list);
        final ImageButton kot_view_btn = row.findViewById(R.id.kot_view_btn);
        final ImageButton kot_checkout_btn = row.findViewById(R.id.kot_checkout_btn);
        final ImageButton kot_new_update = row.findViewById(R.id.kot_update_btn);
     /*   Animation animation = null;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_right);
        convertView.setAnimation(animation);
        animation.setDuration(200);
        convertView.startAnimation(animation);*/
      //  final ImageButton kot_delete_btn = row.findViewById(R.id.kot_delete_btn);
        final SharedPreferences preferences = getContext().getApplicationContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        ip_no = preferences.getString("ip_port_no", "");
       /* int k_id=kot.getId();
        String l_id=kot.getLocal_id();
        String k_no=kot.getKot_no();
        //String kot_type_str=preferences.getString("order_type", "");
        String kid = String.valueOf(k_id);
        String lid = String.valueOf(l_id);
        kot_type.setText(kot.getOrder_type());
        local_id = lid;
        kotNo = k_no;*/
        kot_no.setText(kot_list_loop);
        //kot_type.setText(kot_type_str);
       /* kot_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KotNoListModel kot = menus.get(position);
                String localId = kot.getLocal_id();
                String kotNo= kot.getKot_no();
               DatabaseHandler db;
               db = new DatabaseHandler(getContext().getApplicationContext());
               boolean delStatus = db.Delete_Kot(localId,kotNo);
                menus.remove(position);
                notifyDataSetChanged();
            }
        });*/
        kot_new_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    KotNoListModel kot = menus.get(position);
                String local_id = kot.getLocal_id();
                String kot_no = kot.getKot_no();
                SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
              //  preferences.edit().putString("edit_kot_localid", local_id).putString("edit_kot_no",kot_no).putString("insert_type", "edit").commit();
                preferences.edit().putString("local_kot", local_id).putString("server_kot_no",kot_no).putString("insert_type", "edit").commit();
              //  kot_no = preferences.getString("server_kot_no", "");
                // local_id = preferences.getString("local_kot", "");
             //  Intent i= new Intent(context, EditAllKotActivity.class);*/
               Intent i= new Intent(context, EditBillViewActivity.class);
                i.putExtra("Kot_no",kot_list.get(position));
                preferences.edit().putString("server_kot_no",kot_list.get(position));
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
               v.getContext().startActivity(i);
            }
        });

        kot_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //KotNoListModel kot = menus.get(position);
                    //String lid = kot.getLocal_id();
                    /*SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);*/
                   // preferences.edit().putString("local_kot", lid).commit();
                    Intent i= new Intent(context, ViewRunningOrderActivity.class);
                    i.putExtra("Kot_no",kot_list.get(position));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    v.getContext().startActivity(i); }
                catch (Exception e){
                   // String err_mdg = e.getMessage();
                }
            }
        });

        kot_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*  KotNoListModel kot = menus.get(position);
                String lid = kot.getLocal_id();
                String sid = kot.getKot_no();*/
                Intent i= new Intent(context, KotSummaryActivity.class);
            /*    i.putExtra("code",lid);
                i.putExtra("server_code",sid);*/
                i.putExtra("Kot_no",kot_list.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                v.getContext().startActivity(i);
              /*     try {

                 KotNoListModel kot = menus.get(position);
                    String loc_id = kot.getLocal_id();
                    ArrayList<KotItemsModel> kot_no_list = new ArrayList<KotItemsModel>();
                    DatabaseHandler db;
                    kot_no_list.clear();
                    db = new DatabaseHandler(getContext().getApplicationContext());
                    ArrayList<KotNoListModel> kot_array = db.Get_Sum_Amount(loc_id);

                    for (int i = 0; i < kot_array.size(); i++) {
                        sum_amount = kot_array.get(i).getTotal_amount();
                    }
                    db.close();

                    ArrayList<KotNoListModel> server_kot_no = db.Get_Kot_No(loc_id);
                    for (int i = 0; i < server_kot_no.size(); i++) {
                        server_kotno = server_kot_no.get(i).getKot_no();
                    }
                    db.close();

                    SharedPreferences preferences = getContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                    preferences.edit().putString("server_kot", server_kotno).putString("sum_amount", sum_amount).putString("local_refid",loc_id).commit();

                    Intent i= new Intent(context, CheckoutCalculationActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    v.getContext().startActivity(i);

                   // v.getContext().startActivity(new Intent(v.getContext(), CheckoutCalculationActivity.class));
                }
                catch (Exception e)
                {
                    String err_msg = e.getMessage();
                } */
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

    private void deleteAsyncCall(final String local_id, final String kot_no){
        try {
            RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
            String ipno = ip_no;
            String url = "http://" + ipno + "/Service1.svc/kotdetailsdelete";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            try {

                                String res = response;

                            } catch (Exception e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String err = error.getMessage();
                }
            }) {  //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("localid", local_id);
                    params.put("kotNo", kot_no);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
        catch (Exception e){

            Toast.makeText(getContext().getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }




}
