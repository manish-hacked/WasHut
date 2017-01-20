package club.cyberlabs.washut;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 23-05-2016.
 */
/**public class UserFunctions {
    private JSONParser jsonParser;
    private static String loginURL = "http://192.168.42.123:8080/washut/";
    private static String UpdateURL = "http://192.168.42.123:8080/washut/";

    private static String login_tag = "login";
    private static String update_tag = "update";

    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
       // JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        //return json;
    }
    public JSONObject updateUser(String full_name,String fathers_name,String address,String mobile_no,String idproof_no,
                                 String blood_group,String designation){
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", update_tag));
        params.add(new BasicNameValuePair("full_name", full_name));
        params.add(new BasicNameValuePair("fathers_name", fathers_name));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("mobile_no", mobile_no));
        params.add(new BasicNameValuePair("idproof_no", idproof_no));
        params.add(new BasicNameValuePair("blood_group", blood_group));
        params.add(new BasicNameValuePair("designation", designation));
       // JSONObject json = jsonParser.getJSONFromUrl(UpdateURL,params);
        //return json;
    }
    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
   /** public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
}**/
