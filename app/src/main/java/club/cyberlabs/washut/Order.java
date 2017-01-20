package club.cyberlabs.washut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.*;

public class Order extends AppCompatActivity {

    TextView name,mobno,date_of_order,time_of_order,room_no,pants,shirts,bedsheets,amount,orderno;
    Button takeOrder;
    int position;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    public String fullname,mobileno,dateoforder,timeoforder,roomno,pant,shirt,bedsheet,amounts;
    private static final String DETAILS_URL = "http://washut.16mb.com/details.php";
    private static String KEY_SUCCESS = "success";
    private static String KEY_TAG= "tag";
    private static String KEY_FULLNAME = "full_name";
    private static String KEY_MOBILENO = "mobile_no";
    private static String KEY_DATEOFORDER = "date_of_order";
    private static String KEY_TIMEOFORDER = "time_of_order";
    private static String KEY_ROOMNO = "room_no";
    private static String KEY_PANTS= "pants";
    private static String KEY_SHIRTS= "shirt";
    private static String KEY_BEDSHEETS= "bedsheet";
    private static String KEY_AMOUNT= "amount";
    private static String TAG_MESSAGE= "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        register();
        Bundle b = this.getIntent().getExtras();
        position = b.getInt("TEXT");
        int order_no = position+1;
        orderno.setText("Order No "+order_no);

        takeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TakeOrder().execute("Take_order", String.valueOf(position));
            }
        });
        new OrderDetails().execute("details", String.valueOf(position));
    }

    private void register() {
        name = (TextView)findViewById(R.id.CustomerFullName);
        mobno = (TextView)findViewById(R.id.CustomerMobileNo);
        date_of_order = (TextView)findViewById(R.id.CustomerDateOfOrder);
        time_of_order = (TextView)findViewById(R.id.CustomerTimeOfOrder);
        room_no = (TextView)findViewById(R.id.CustomerRoomNo);
        pants = (TextView)findViewById(R.id.NoOfPants);
        shirts = (TextView)findViewById(R.id.NoOfShirts);
        bedsheets = (TextView)findViewById(R.id.NoOfBedsheet);
        amount = (TextView)findViewById(R.id.TotalAmount);
        takeOrder = (Button)findViewById(R.id.TakeOrder);
        orderno = (TextView)findViewById(R.id.OrderNo);
    }

    class TakeOrder extends AsyncTask<String, String, String>{
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Order.this);
            pDialog.setMessage("Getting Details Of Order");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String tag = args[0];
            String id = args[1];
            int success;
            try{
                java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag",tag));
                params.add(new BasicNameValuePair("position",id));
                JSONObject json = jsonParser.makeHttpRequest(DETAILS_URL,"POST",params);
                Log.d("Login attempt", json.toString());
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    Log.d("Successfully Login!", json.toString());
                    Intent ii = new Intent(Order.this,List.class);
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
                Toast.makeText(Order.this, "Order is Taken", Toast.LENGTH_LONG).show();
            }
        }
    }

    class OrderDetails extends AsyncTask<String, String, String>{
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Order.this);
            pDialog.setMessage("Getting Details Of Order");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String tag = args[0];
            String id = args[1];
            int success;
            try{
                java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag",tag));
                params.add(new BasicNameValuePair("position",id));
                JSONObject json = jsonParser.makeHttpRequest(DETAILS_URL,"POST",params);
                Log.d("Login attempt", json.toString());
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    fullname = json.getString(KEY_FULLNAME);
                    mobileno = json.getString(KEY_MOBILENO);
                    dateoforder = json.getString(KEY_DATEOFORDER);
                    timeoforder = json.getString(KEY_TIMEOFORDER);
                    roomno = json.getString(KEY_ROOMNO);
                    pant = json.getString(KEY_PANTS);
                    shirt = json.getString(KEY_SHIRTS);
                    bedsheet = json.getString(KEY_BEDSHEETS);
                    amounts = json.getString(KEY_AMOUNT);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(fullname);
                            mobno.setText(mobileno);
                            date_of_order.setText(dateoforder);
                            time_of_order.setText(timeoforder);
                            room_no.setText(roomno);
                            pants.setText(pant);
                            shirts.setText(shirt);
                            bedsheets.setText(bedsheet);
                            amount.setText(amounts);
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
                Toast.makeText(Order.this, "Order Details", Toast.LENGTH_LONG).show();
            }
        }
    }
}
