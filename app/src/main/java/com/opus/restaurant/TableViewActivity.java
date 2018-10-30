package com.opus.restaurant;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TableViewActivity extends AppCompatActivity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);


        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293C69")));
        }
        getSupportActionBar().setTitle("Select option");

        takeaway_btn = (Button) findViewById(R.id.main_takeaway_btn);
        dinein_btn = (Button) findViewById(R.id.main_dinein_btn);
        gridView = (GridView) findViewById(R.id.table_grid);
        grid_layout = (LinearLayout) findViewById(R.id.main_grid_layout);

        takeaway_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
