package com.opus.restaurant;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.opus.restaurant.Models.Category;
import com.opus.restaurant.Models.Items;
import com.opus.restaurant.Models.Tables;
import com.opus.restaurant.Services.CallBack;
import com.opus.restaurant.Services.HttpTaskGet;
import com.opus.restaurant.Services.KeyValue;
import com.opus.restaurant.Sqlite.DatabaseHandler;
import com.opus.restaurant.Utilities.QuickSandRegularEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity implements CallBack {

    private Button showIpBoxBtn;
    private Button ipSyncBtn;
    private Button masterSyncBtn; //Btn-Button
    private QuickSandRegularEditText ipNoEt; //Et -Edittext
    private RelativeLayout ipBoxLayout;
    private Pattern pattern;
    private Matcher matcher;
    ProgressDialog progressBar;
    private SharedPreferences prefs;
    private String ipNo;
    private FrameLayout loginProgressBarHolder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        showIpBoxBtn = (Button)findViewById(R.id.show_ip_box);
        ipSyncBtn = (Button)findViewById(R.id.sync_ip);
        masterSyncBtn = (Button)findViewById(R.id.sync_master);
        ipNoEt = (QuickSandRegularEditText)findViewById(R.id.ip_no);
        ipBoxLayout = (RelativeLayout)findViewById(R.id.iplayout);
        prefs = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
        loginProgressBarHolder = (FrameLayout)findViewById(R.id.loginProgressBarHolder);
        /*ipNo = prefs.getString("ip_port_no","");
       if (!ipNo.isEmpty() && ipNo!=null){
           ipNoEt.setText(ipNo);
       }*/
        showIpBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ipBoxLayout.setVisibility(View.VISIBLE);
            }
        });

        ipSyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip_no = ipNoEt.getText().toString();
                if (ip_no.isEmpty()){

                    Toast.makeText(SettingsActivity.this,"IP no required ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences prefs = getSharedPreferences("com.opus.restaurant", MODE_PRIVATE);
                    prefs.edit().putString("ip_port_no",ip_no).commit();
                    ipNo = ip_no;
                    ipBoxLayout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"IP sync finished",Toast.LENGTH_SHORT).show();
                }
            }
        });

        masterSyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginProgressBarHolder.setVisibility(View.VISIBLE);
                deleteAllMasters();
                //progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Sync in Progress", true, false);
                itemCategorySync();
                itemNamesSync();
                tablesSync();
                //progressBar.dismiss();
            }
        });
    }

    private void itemCategorySync(){

       // progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Category Sync in Progress", true, false);
        ArrayList<KeyValue> params = new ArrayList<KeyValue>();
        String ip_no = ipNo;
        String Url = "http://"+ip_no + "/Service1.svc/getCategoryDetails";
       // String Url = "http://192.168.1.14:6060/Service1.svc/getCategoryDetails";
        HttpTaskGet httpCall = new HttpTaskGet(SettingsActivity.this, params, null, Url, new CallBack() {
            @Override
            public void onPostExecute(String result) {
                try {
                    //progressBar.dismiss();
                    JSONArray jsonArray =new JSONArray(result);
                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String categoryName = jsonObject.getString("Category");
                            String type = jsonObject.getString("Ktype");
                            int cid = Integer.parseInt(jsonObject.getString("Id"));
                            DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
                           // dbHandler.Add_Categorymaster(new Category(cid, categoryName, "Dine In", "I"));
                            dbHandler.Add_Categorymaster(new Category(cid, categoryName, type, "I"));

                      /*  Category category =new Category();
                        category.setCategoryId(cid);
                        category.setCategory(categoryName); */
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


              //  progressBar.dismiss();
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        },true);
        httpCall.execute();

    }

    private void itemNamesSync(){

       // progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Category Sync in Progress", true, false);
        ArrayList<KeyValue> params = new ArrayList<KeyValue>();
        String ip_no = ipNo;
        String Url = "http://"+ip_no + "/Service1.svc/getitemdetails";
       // String Url = "http://192.168.1.14:6060/Service1.svc/getItemDetails";
        HttpTaskGet httpCall = new HttpTaskGet(SettingsActivity.this, params, null, Url, new CallBack() {
            @Override
            public void onPostExecute(String result) {
               // progressBar.dismiss();

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                    for (int i=0;i<jsonArray.length();i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String acRate = jsonObject.getString("Ac_Rate");
                            String categoryName = jsonObject.getString("Category");
                            String code= jsonObject.getString("Code");
                            int id = jsonObject.getInt("Id");
                            String normalRate = jsonObject.getString("Normal_Rate");
                            String name =jsonObject.getString("Tname");
                            String type = jsonObject.getString("Ktype");
                            Date currentTime = Calendar.getInstance().getTime();
                            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").format(currentTime);
                            DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
                          //  dbHandler.Add_Itemaster(new Items(id,"2018",code,name, normalRate, categoryName,name,"Non Alcohol","I","Dine In",normalRate,acRate));
                            dbHandler.Add_Itemaster(new Items(id,currentDateandTime,code,name, normalRate, categoryName,name,"Non Alcohol","I",type,normalRate,acRate));

                        } catch (JSONException e) {
                            Log.d("db_err",e.getMessage());
                        }
                    }
                } catch (JSONException e) { }
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        },true);
        httpCall.execute();
    }

    private void acOrNonAcSync(){
       // progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Category Sync in Progress", true, false);
        ArrayList<KeyValue> params = new ArrayList<KeyValue>();
        String ip_no = ipNo;
        String Url = "http://"+ip_no + "/Service1.svc/getitemdetails";
        HttpTaskGet httpCall = new HttpTaskGet(SettingsActivity.this, params, null, Url, new CallBack() {
            @Override
            public void onPostExecute(String result) {
                progressBar.dismiss();
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        },true);
        httpCall.execute();
    }

    private void tablesSync(){

      //  progressBar = ProgressDialog.show(SettingsActivity.this, "Please Wait", "Category Sync in Progress", true, false);
        ArrayList<KeyValue> params = new ArrayList<KeyValue>();
        String ip_no = ipNo;
        String Url = "http://"+ip_no + "/Service1.svc/getTableDetails";
      //  String Url = "http://192.168.1.14:6060/Service1.svc/getTableDetails";
        HttpTaskGet httpCall = new HttpTaskGet(SettingsActivity.this, params, null, Url, new CallBack() {
            @Override
            public void onPostExecute(String result) {

              //  progressBar.dismiss();

                try {
                    JSONObject jsonObject =new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("AC");
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject tableObject = jsonArray.getJSONObject(i);
                            String floor = tableObject.getString("Floor");
                            int id = tableObject.getInt("Id");
                            String seat = tableObject.getString("Seat");
                            String tableName =tableObject.getString("Tablename");
                            String type= "AC";
                            DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
                            // dbHandler.Add_Tablemaster(new Tables(1,"001", "Floor 1", "T1", "Left","5","I"));
                            dbHandler.Add_Tablemaster(new Tables(id,tableName, floor, tableName, "Left",seat,"I",type));
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Ac is null",Toast.LENGTH_SHORT).show();

                    JSONArray jsonArrayNonAc = jsonObject.getJSONArray("NONAC");
                    if(jsonArrayNonAc.length()>0){
                        for(int i=0;i<jsonArrayNonAc.length();i++){
                            JSONObject tableObject = jsonArrayNonAc.getJSONObject(i);
                            String floor = tableObject.getString("Floor");
                            int id = tableObject.getInt("Id");
                            String seat = tableObject.getString("Seat");
                            String tableName =tableObject.getString("Tablename");
                            String type= "NONAC";
                            DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
                            // dbHandler.Add_Tablemaster(new Tables(1,"001", "Floor 1", "T1", "Left","5","I"));
                            dbHandler.Add_Tablemaster(new Tables(id,tableName, floor, tableName, "Left",seat,"I",type));


                            // finishActivity();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Non ac is null",Toast.LENGTH_SHORT).show();
                    loginProgressBarHolder.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Sync Finished",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                   // e.printStackTrace();
                }
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        },true);
        httpCall.execute();
    }

    @Override
    public void onPostExecute(String result) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }

    public void finishActivity(){
        this.finish();
    }

    public void deleteAllMasters(){

        DatabaseHandler dbHandler = new DatabaseHandler(SettingsActivity.this);
        dbHandler.Delete_Waitermaster();
        dbHandler.Delete_Categorymaster();
        dbHandler.Delete_Itemmaster();
        dbHandler.Delete_Tablemaster();
    }
}
