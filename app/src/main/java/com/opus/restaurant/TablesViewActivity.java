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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opus.restaurant.Adapters.TablesAdapter;
import com.opus.restaurant.Models.Tables;
import com.opus.restaurant.Services.Constant;
import com.opus.restaurant.Sqlite.DatabaseHandler;

import java.util.ArrayList;

public class TablesViewActivity extends AppCompatActivity {
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
    RadioButton acBtn;
    RadioButton nonAcBtn;
    TextView t1;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_view);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Brown)));
        }
        getSupportActionBar().setTitle("Tables");
        t1=findViewById(R.id.select_table_ac_nonactxtview);
        preference = getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
        acBtn =findViewById(R.id.ac_radio_btn);
        nonAcBtn = findViewById(R.id.non_ac_radio_btn);
        grid_layout = (LinearLayout)findViewById(R.id.grid_layout);
        gridView = (GridView)findViewById(R.id.table_grid);
        acBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AC ITEM CLICK
                t1.setText("SELECT TABLE IN AC");
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
                    cb.setBackgroundColor(getResources().getColor(R.color.Brown));
                    cb.setTextColor(getResources().getColor(R.color.White));
                    cb.setId(ids);
                    registerForContextMenu(cb);
                    dynamicButtons.add(cb);
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            final Button selection = (Button) v;
                            final int tableid = cb.getId();
                            CharSequence tablename = selection.getText();
                            final String table_name = String.valueOf(tablename);
                            AlertDialog.Builder builder = new AlertDialog.Builder(TablesViewActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.number_of_persons_layout, null);
                            builder.setView(dialogView);
                            dialog_noofPersons = (EditText) dialogView.findViewById(R.id.no_of_persons);
                            dialog_ok_btn = (Button) dialogView.findViewById(R.id.btn_persons_ok);
                            dialog_erase_btn = (Button) dialogView.findViewById(R.id.btn_persons_erase);
                            dialog_clear_btn = (Button) dialogView.findViewById(R.id.btn_persons_delete);
                            dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
if(!dialog_noofPersons.getText().toString().equals(null)){
    int length = dialog_noofPersons.getText().length();
                                        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                                        preferences.edit().putString("selected_table", table_name).putString("room_type", "AC").putString("no_of_persons", dialog_noofPersons.getText().toString()).commit();
                                        Intent intent = new Intent(getApplicationContext(), MainCategoryActivity.class);
                                        startActivity(intent);
                                        finish(); }
                                        else
                                            dialog_noofPersons.setError("Please enter the value");
                                }
                            });

                            dialog_erase_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int length = dialog_noofPersons.getText().length();
                                    if (length > 0 || !dialog_noofPersons.getText().toString().equals("")) {
                                        dialog_noofPersons.getText().delete(length - 1, length);
                                    }

                                }
                            });

                            dialog_clear_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.INVISIBLE);
                                    dialog_noofPersons.setText("");
                                }
                            });


                            zero = (Button) dialogView.findViewById(R.id.btn_zero);
                            zero.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String old_value = dialog_noofPersons.getText().toString();
                                    String new_value = zero.getText().toString();
                                    dialog_noofPersons.setText(old_value + new_value);

                                }
                            });

                            one = (Button) dialogView.findViewById(R.id.btn_one);
                            one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
zero.setVisibility(View.VISIBLE);

                                    setEdittextValues(one);
                                }
                            });

                            two = (Button) dialogView.findViewById(R.id.btn_two);
                            two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(two);
                                }
                            });

                            three = (Button) dialogView.findViewById(R.id.btn_three);
                            three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(three);
                                }
                            });

                            four = (Button) dialogView.findViewById(R.id.btn_four);
                            four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(four);
                                    zero.setVisibility(View.VISIBLE);
                                }
                            });

                            five = (Button) dialogView.findViewById(R.id.btn_five);
                            five.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(five);
                                }
                            });

                            six = (Button) dialogView.findViewById(R.id.btn_six);
                            six.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(six);

                                }
                            });

                            seven = (Button) dialogView.findViewById(R.id.btn_seven);
                            seven.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(seven);
                                }
                            });

                            eight = (Button) dialogView.findViewById(R.id.btn_eight);
                            eight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(eight);
                                }
                            });

                            nine = (Button) dialogView.findViewById(R.id.btn_nine);
                            nine.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zero.setVisibility(View.VISIBLE);
                                    setEdittextValues(nine);
                                }
                            });

                            final AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }

                gridView.setAdapter(new TablesAdapter(dynamicButtons));
                db.close();
                tables.clear();
            }
        });
        nonAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("SELECT TABLE IN NON-AC");
                //NON -AC ITEM CLICK
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
                    cb.setBackgroundColor(getResources().getColor(R.color.White));
                    cb.setTextColor(getResources().getColor(R.color.Red));
                    cb.setId(ids);
                    registerForContextMenu(cb);
                    dynamicButtons.add(cb);
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            final Button selection = (Button) v;
                            final int tableid = cb.getId();
                            CharSequence tablename = selection.getText();
                            final String table_name = String.valueOf(tablename);
                            AlertDialog.Builder builder = new AlertDialog.Builder(TablesViewActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.number_of_persons_layout, null);
                            builder.setView(dialogView);
                            dialog_noofPersons = (EditText) dialogView.findViewById(R.id.no_of_persons);
                            dialog_ok_btn = (Button) dialogView.findViewById(R.id.btn_persons_ok);
                            dialog_erase_btn = (Button) dialogView.findViewById(R.id.btn_persons_erase);
                            dialog_clear_btn = (Button) dialogView.findViewById(R.id.btn_persons_delete);
                            dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int length = dialog_noofPersons.getText().length();
                                    if (length > 0 || !dialog_noofPersons.getText().toString().equals("")) {
                                        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Constant.PREFERENCE, MODE_PRIVATE);
                                        preferences.edit().putString("selected_table", table_name).putString("room_type","NON-AC").putString("selected_table_type", "NONAC").putString("no_of_persons", dialog_noofPersons.getText().toString()).commit();
                                        Intent intent = new Intent(getApplicationContext(), MainCategoryActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                            dialog_erase_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    int length = dialog_noofPersons.getText().length();
                                    if (length > 0 || !dialog_noofPersons.getText().toString().equals("")) {
                                        dialog_noofPersons.getText().delete(length - 1, length);
                                    }

                                }
                            });

                            dialog_clear_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_noofPersons.setText("");
                                }
                            });


                            zero = (Button) dialogView.findViewById(R.id.btn_zero);
                            zero.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String old_value = dialog_noofPersons.getText().toString();
                                    String new_value = zero.getText().toString();
                                    dialog_noofPersons.setText(old_value + new_value);

                                }
                            });

                            one = (Button) dialogView.findViewById(R.id.btn_one);
                            one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    setEdittextValues(one);
                                }
                            });

                            two = (Button) dialogView.findViewById(R.id.btn_two);
                            two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    setEdittextValues(two);
                                }
                            });

                            three = (Button) dialogView.findViewById(R.id.btn_three);
                            three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(three);
                                }
                            });

                            four = (Button) dialogView.findViewById(R.id.btn_four);
                            four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(four);
                                }
                            });

                            five = (Button) dialogView.findViewById(R.id.btn_five);
                            five.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    setEdittextValues(five);
                                }
                            });

                            six = (Button) dialogView.findViewById(R.id.btn_six);
                            six.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(six);

                                }
                            });

                            seven = (Button) dialogView.findViewById(R.id.btn_seven);
                            seven.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(seven);
                                }
                            });

                            eight = (Button) dialogView.findViewById(R.id.btn_eight);
                            eight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEdittextValues(eight);
                                }
                            });

                            nine = (Button) dialogView.findViewById(R.id.btn_nine);
                            nine.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    setEdittextValues(nine);
                                }
                            });

                            final AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }

                gridView.setAdapter(new TablesAdapter(dynamicButtons));
                db.close();
                tables.clear();
            }
        });
    }

    private void setEdittextValues(Button btn){
        String old_value = dialog_noofPersons.getText().toString();
        String new_value = btn.getText().toString();
        dialog_noofPersons.setText(old_value+new_value);
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
