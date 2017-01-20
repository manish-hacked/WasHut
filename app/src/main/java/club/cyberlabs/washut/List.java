package club.cyberlabs.washut;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 24-05-2016.
 */
public class List extends Activity{
    ArrayList<String[]> outerArr = new ArrayList<String[]>();
    static public String p="hello jhh";
    private static final String TAG_MESSAGE = "message";
    private static final String ORDER_URL = "http://washut.16mb.com/orders.php";
    private ProgressDialog pDialog;
    ArrayList<String[]> outerArr1 = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    private static String KEY_SUCCESS = "success";
    private static String KEY_ID = "id";
    private static String KEY_FULLNAME = "full_name";
    private static String KEY_MOBILENO = "mobile_no";
    private static String KEY_DATEOFORDER = "date_of_order";
    private static String KEY_TIMEOFORDER = "time_of_order";
    private static String KEY_ROOMNO = "room_no";
    private static String KEY_PANTS= "pants";
    private static String KEY_SHIRTS= "shirt";
    private static String KEY_BEDSHEETS= "bedsheet";
    private static String KEY_AMOUNT= "amount";
    private static String KEY_PIN= "pin";
    ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lv1 = (ListView) findViewById(R.id.custom_list);
        new Orders().execute();
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {
                Intent intent = new Intent(List.this, Order.class);
                Bundle b = new Bundle();
                b.putInt("TEXT", position);
                intent.putExtras(b);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }
    private ArrayList getListData(){
        ArrayList<OrderItem> results = new ArrayList<>();
        for(int i=0;i<outerArr.size();i++){
            OrderItem orderData = new OrderItem();
            String[] myString;
            myString=outerArr.get(i);
            orderData.setName(p);
            orderData.setMobile_number("hello");
            orderData.setDate_of_order("hello");
            orderData.setTime_of_order("hello");
            orderData.setRoom_no("hello");
            orderData.setPants("hello");
            orderData.setShirt(myString[2]);
            orderData.setBedsheet(myString[2]);
            orderData.setAmount(myString[2]);
            results.add(orderData);
        }
        return results;

    }
    class Orders extends AsyncTask<String,String,ArrayList<HashMap<String, String>> >{
        boolean failure = false;
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        private Activity activity;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(List.this);
            pDialog.setMessage("Proccessing....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            int success;
            ArrayList<String[]> outerArr1 = new ArrayList<>();
            try{
                java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("orders","orders"));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(ORDER_URL,"POST",params);
                success = json.getInt(KEY_SUCCESS);
                if(success==1){
                    JSONArray jsonArray = json.optJSONArray("details");
                    int l = jsonArray.length()-1;
                    for(int i=l;i>=0;i--){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.optString(KEY_FULLNAME);
                        String mobile_no = jsonObject.optString(KEY_MOBILENO);
                        String date_of_order = jsonObject.optString(KEY_DATEOFORDER);
                        String time_of_order = jsonObject.optString(KEY_TIMEOFORDER);
                        String room_no = jsonObject.optString(KEY_ROOMNO);
                        String pants = jsonObject.optString(KEY_PANTS);
                        String shirt = jsonObject.optString(KEY_SHIRTS);
                        String bedsheet = jsonObject.optString(KEY_BEDSHEETS);
                        String amount = jsonObject.optString(KEY_AMOUNT);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FULLNAME,name);
                        map.put(KEY_MOBILENO,mobile_no);
                        map.put(KEY_DATEOFORDER,date_of_order);
                        map.put(KEY_TIMEOFORDER,time_of_order);
                        map.put(KEY_ROOMNO,room_no);
                        map.put(KEY_PANTS,pants);
                        map.put(KEY_SHIRTS,shirt);
                        map.put(KEY_BEDSHEETS,bedsheet);
                        map.put(KEY_AMOUNT, amount);
                        contactList.add(map);
                    }

                    return contactList;
                }else{
                    return null;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            lv1.setAdapter(new CustomListAdapter(List.this, result));
            Toast.makeText(List.this,"Orders", Toast.LENGTH_LONG).show();
            pDialog.dismiss();

        }

    }

}
