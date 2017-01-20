package club.cyberlabs.washut;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by HP on 23-05-2016.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jsonObj;
    static String json = "";
    public JSONParser(){

    }

    public JSONObject getJSONFromUrl(final String url){
        // Making HTTP request
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            //// Executing POST request & storing the response from server locally.
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            // Create a BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
            // Declaring string builder
            StringBuilder str = new StringBuilder();
            String strLine = null;
            // Building while we have string !equal null.
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            // Close inputstream.
            is.close();
            json = str.toString();
        }catch (Exception e){
            Log.e("Error", " something wrong with converting result " + e.toString());
        }
        try{
            jsonObj = new JSONObject(json);

        }catch (JSONException e){
            Log.e("json Parsering", "" + e.toString());
        }
        return jsonObj;
    }
    public JSONObject makeHttpRequest(String url,String method,List<NameValuePair> params){
        // Make HTTP request
        try{
            if(method.equals("POST")){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method.equals("GET")){
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        }catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
            StringBuilder str = new StringBuilder();
            String strLine = null;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            is.close();
            json = str.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            jsonObj = new JSONObject(json);

        }catch (JSONException e){

        }
        return jsonObj;
    }

}
