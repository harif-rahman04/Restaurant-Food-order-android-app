package com.opus.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.opus.restaurant.Adapters.TablesAdapter;
import com.opus.restaurant.Models.Tables;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;

public class TablesOnlyActivity extends AppCompatActivity {

    Button takeaway_btn;
    Button dinein_btn;
    GridView gridView;
    LinearLayout grid_layout;
    private ArrayList<Button> dynamicButtons;
    Button zero;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    EditText dialog_noofPersons;
    Button dialog_ok_btn;
    Button dialog_erase_btn;
    Button dialog_clear_btn;
    Button acBtn;
    Button nonAcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_only);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F1610")));
        }
        getSupportActionBar().setTitle("Tables");

        acBtn =(Button)findViewById(R.id.ac_btn);
        nonAcBtn = (Button)findViewById(R.id.non_ac_btn);
        grid_layout = (LinearLayout)findViewById(R.id.grid_layout);
        gridView = (GridView)findViewById(R.id.table_grid);

        acBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preference = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                String orderStatus = preference.getString("order_status","");


                dynamicButtons = new ArrayList<Button>();
                dynamicButtons.clear();
                grid_layout.setVisibility(View.VISIBLE);
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ArrayList<Tables> tables = db.Get_AC_Table();
                int count = tables.size();
                for (int i = 0; i < tables.size(); i++) {

                    String name = tables.get(i).getTableName();
                    int ids = tables.get(i).getTid();
                    final Button cb = new Button(getApplicationContext());
                    cb.setText(name.toString());
                    cb.setBackgroundColor(getResources().getColor(R.color.colorIpbtn));
                    cb.setTextColor(getResources().getColor(R.color.colorWhite));
                    cb.setId(ids);
                    registerForContextMenu(cb);
                    dynamicButtons.add(cb);


                }
                gridView.setAdapter(new TablesAdapter(dynamicButtons));
                db.close();
                tables.clear();
            }



        });

        nonAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dynamicButtons = new ArrayList<Button>();
                dynamicButtons.clear();
                grid_layout.setVisibility(View.VISIBLE);
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ArrayList<Tables> tables = db.Get_NONAC_Table();
                int count = tables.size();
                for (int i = 0; i < tables.size(); i++) {

                    String name = tables.get(i).getTableName();
                    int ids = tables.get(i).getTid();
                    final Button cb = new Button(getApplicationContext());
                    cb.setText(name.toString());
                    cb.setBackgroundColor(getResources().getColor(R.color.colorIpbtn));
                    cb.setTextColor(getResources().getColor(R.color.colorWhite));
                    cb.setId(ids);
                    registerForContextMenu(cb);
                    dynamicButtons.add(cb);
                }
                gridView.setAdapter(new TablesAdapter(dynamicButtons));
                db.close();
                tables.clear();
            }
        });
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
    public void onBackPressed(){
        this.finish();
    }
}
