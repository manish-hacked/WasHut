package club.cyberlabs.washut;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 20-05-2016.
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private static final String LOGIN_URL = "http://washut.16mb.com/view.php";
    TextView name,fname,add,mob,id,blood,desig;
    SharedPreferences someData;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    public String name1,fname1,add1,mob1,id1,blood1,desig1;
    private static String KEY_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static String KEY_UID = "unique_id";
    private static String KEY_USERNAME = "username";
    private static String KEY_FULLNAME = "full_name";
    private static String KEY_ID = "id";
    private static String KEY_FATHERSNAME = "fathers_name";
    private static String KEY_ADDRESS = "address";
    private static String KEY_MOBILENO = "mobile_no";
    private static String KEY_IDPROOFNO = "idproof_no";
    private static String KEY_BLOODGROUP = "blood_group";
    private static String KEY_DESIGNATION = "designation";
    public String user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        someData = getSharedPreferences("MySharedData",0);
        user = someData.getString("username", "ramesh");
        register();
        new AttemptView().execute(user);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.takeorder:
                        Intent intent = new Intent("android.intent.action.ORDERS");
                        startActivity(intent);
                        return true;
                    case R.id.delivery:
                        Intent intent1 = new Intent("android.intent.action.DELIVERY");
                        startActivity(intent1);
                        return true;
                    case R.id.makecomplaint:
                        Intent intent2 = new Intent("android.intent.action.COMPLAINT");
                        startActivity(intent2);
                        return true;
                    case R.id.guest:
                        Intent intent3 = new Intent("android.intent.action.SUGGESTION");
                        startActivity(intent3);
                        return true;
                    case R.id.details:
                        Intent intent4 = new Intent("android.intent.action.DETAILS");
                        startActivity(intent4);
                        return true;

                }
                return true;
            }
        });

    }

    private void register() {
        name= (TextView)findViewById(R.id.fullname);
        fname= (TextView)findViewById(R.id.fathersname);
        add= (TextView)findViewById(R.id.addressview);
        mob= (TextView)findViewById(R.id.mobileno);
        id= (TextView)findViewById(R.id.idproofno);
        blood = (TextView)findViewById(R.id.bloodgrp);
        desig = (TextView)findViewById(R.id.designationview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
    class AttemptView extends AsyncTask<String, String, String>{
        boolean failure = false;

        @Override
        protected String doInBackground(String... args) {
            int success;
            String user = args[0];
            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",user));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL,"POST",params);
                Log.d("View attempt", json.toString());
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    name1 = json.getString(KEY_FULLNAME);
                    fname1 = json.getString(KEY_FATHERSNAME);
                    add1 = json.getString(KEY_ADDRESS);
                    mob1 = json.getString(KEY_MOBILENO);
                    id1 = json.getString(KEY_IDPROOFNO);
                    blood1 = json.getString(KEY_BLOODGROUP);
                    desig1 = json.getString(KEY_DESIGNATION);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(name1);
                            fname.setText(fname1);
                            add.setText(add1);
                            mob.setText(mob1);
                            id.setText(id1);
                            blood.setText(blood1);
                            desig.setText(desig1);
                        }
                    });

                    return json.getString(TAG_MESSAGE);
                }else{
                    return json.getString(TAG_MESSAGE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            if (s != null){
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Your Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


}
