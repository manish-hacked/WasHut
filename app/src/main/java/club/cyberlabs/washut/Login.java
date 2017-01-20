package club.cyberlabs.washut;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

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

/**
 * Created by HP on 19-05-2016.
 */
public class Login extends Activity{

    EditText username;
    ShowHidePasswordEditText password;
    Button login;
    SharedPreferences someData;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://washut.16mb.com/login.php";
    public static String filename = "MySharedData";
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText)findViewById(R.id.username);
        password = (ShowHidePasswordEditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        someData = getSharedPreferences(filename,0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!username.getText().toString().equals("")) && (!password.getText().toString().equals(""))) {
                    String stringData = username.getText().toString();
                    SharedPreferences.Editor editor = someData.edit();
                    editor.putString("username",stringData);
                    editor.commit();
                    new AttemptLogin().execute(username.getText().toString(),password.getText().toString());
                } else if ((!username.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!password.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Username and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class AttemptLogin extends AsyncTask<String, String, String>{

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String tag = "login";
            String user = args[0];
            String pass = args[1];
            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag",tag));
                params.add(new BasicNameValuePair("username",user));
                params.add(new BasicNameValuePair("password",pass));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL,"POST",params);
                //Checking log for json response
                Log.d("Login attempt", json.toString());
                // success tag for json
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    Log.d("Successfully Login!", json.toString());
                    Intent ii = new Intent(Login.this,MainActivity.class);
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
                Toast.makeText(Login.this,s, Toast.LENGTH_LONG).show();
            }
        }
    }


}
