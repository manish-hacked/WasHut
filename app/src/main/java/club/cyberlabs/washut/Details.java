package club.cyberlabs.washut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private static String KEY_SUCCESS = "success";
    public String name1,fname1,add1,mob1,id1,blood1,desig1;
    private static String KEY_ID = "id";
    private static final String TAG_MESSAGE = "message";
    private static String KEY_FULLNAME = "full_name";
    private static String KEY_FATHERSNAME = "fathers_name";
    private static String KEY_ADDRESS = "address";
    private static String KEY_MOBILENO = "mobile_no";
    private static String KEY_IDPROOFNO = "idproof_no";
    private static String KEY_BLOODGROUP = "blood_group";
    private static String KEY_DESIGNATION = "designation";
    private static String KEY_USERNAME = "username";
    private static String KEY_UID = "unique_id";
    private static String KEY_ERROR = "error";
    public String user;


    EditText name,fname,add,mob,id,blood,desig;
    Button submit;
    SharedPreferences someData;
    private static final String LOGIN_URL = "http://washut.16mb.com/update.php";
    private static final String VIEW_URL = "http://washut.16mb.com/view.php";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name= (EditText)findViewById(R.id.full_name);
        fname= (EditText)findViewById(R.id.fathers_name);
        add= (EditText)findViewById(R.id.address);
        mob= (EditText)findViewById(R.id.mobile_no);
        id= (EditText)findViewById(R.id.idproof_no);
        blood = (EditText)findViewById(R.id.blood_grp);
        desig = (EditText)findViewById(R.id.designation);
        submit = (Button)findViewById(R.id.submit);
        someData = getSharedPreferences("MySharedData", 0);
        user = someData.getString("username", "ramesh");
        new AttemptView().execute(user);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = name.getText().toString();
                String fathers_name = fname.getText().toString();
                String address = add.getText().toString();
                String mobile = mob.getText().toString();
                String idno = id.getText().toString();
                String bloodno = blood.getText().toString();
                String designation = desig.getText().toString();
                new UpdateView().execute(user,fullname,fathers_name,address,mobile,idno,bloodno,designation);
            }
        });
    }
    class UpdateView extends AsyncTask<String, String, String>{
        boolean failure = false;

        @Override
        protected String doInBackground(String...args) {
            int success;
            String user = args[0];
            String fullname= args[1];
            String fathers_name= args[2];
            String address= args[3];
            String mobile= args[4];
            String idno= args[5];
            String bloodno= args[6];
            String designation= args[7];
            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",user));
                params.add(new BasicNameValuePair(KEY_FULLNAME,fullname));
                params.add(new BasicNameValuePair(KEY_FATHERSNAME,fathers_name));
                params.add(new BasicNameValuePair(KEY_ADDRESS,address));
                params.add(new BasicNameValuePair(KEY_MOBILENO,mobile));
                params.add(new BasicNameValuePair(KEY_IDPROOFNO,idno));
                params.add(new BasicNameValuePair(KEY_BLOODGROUP,bloodno));
                params.add(new BasicNameValuePair(KEY_DESIGNATION,designation));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL,"POST",params);
                Log.d("View attempt", json.toString());
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    Log.d("Successfully Updation!", json.toString());
                    Intent ii = new Intent(Details.this,MainActivity.class);
                    finish();
                    startActivity(ii);
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
                Toast.makeText(Details.this,s, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Details.this);
            pDialog.setMessage("Attempting for updation...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
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
                JSONObject json = jsonParser.makeHttpRequest(VIEW_URL,"POST",params);
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
                Toast.makeText(Details.this,"Edit Your Details", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Details.this);
            pDialog.setMessage("Loading Your Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
