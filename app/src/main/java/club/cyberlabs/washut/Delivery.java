package club.cyberlabs.washut;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Delivery extends AppCompatActivity {
    EditText name,mobilenumber,amountdue,clothsdue,amountpaid,pin;
    Button delivery;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String DELIVERY_URL = "http://washut.16mb.com/delivery.php";
    private static String KEY_SUCCESS = "success";
    private static String KEY_FULLNAME = "full_name";
    private static final String TAG_MESSAGE = "message";
    public String customer_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        register();
        mobilenumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String mobile_no = mobilenumber.getText().toString();
                    new DeliveryLogin().execute(mobile_no);
                    handled = true;
                }
                return handled;
            }
        });


    }

    private void register() {
        name = (EditText)findViewById(R.id.Delname);
        mobilenumber = (EditText)findViewById(R.id.Delmobno);
        amountdue = (EditText)findViewById(R.id.DelamountsDue);
        clothsdue = (EditText)findViewById(R.id.DelClothesDue);
        amountpaid = (EditText)findViewById(R.id.DelAmountPaid);
        pin = (EditText)findViewById(R.id.Delpin);
    }
    class DeliveryLogin extends AsyncTask<String, String, String>{
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Delivery.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String tag = "delivery";
            String mobno = args[0];
            try{
                java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag",tag));
                params.add(new BasicNameValuePair("mobile_no",mobno));
                JSONObject json = jsonParser.makeHttpRequest(DELIVERY_URL,"POST",params);
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    customer_name = json.getString(KEY_FULLNAME);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            name.setText(customer_name);
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
        }
    }
}
