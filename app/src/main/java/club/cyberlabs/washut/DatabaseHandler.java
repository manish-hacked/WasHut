package club.cyberlabs.washut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by HP on 23-05-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "washut";
    //Table Name
    private static final String TABLE_LOGIN = "user_dhobi";

    private static final String KEY_ID = "id";
    private static final String KEY_FULLNAME = "full_name";
    private static final String KEY_FATHERSNAME = "fathers_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_MOBILENO = "mobile_no";
    private static final String KEY_IDPROOFNO = "idproof_no";
    private static final String KEY_BLOODGROUP = "blood_group";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_UID = "unique_id";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FULLNAME + " TEXT,"
                + KEY_FATHERSNAME + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_MOBILENO + " NUMBER,"
                + KEY_IDPROOFNO + " TEXT,"
                + KEY_BLOODGROUP + " TEXT,"
                + KEY_DESIGNATION+ " TEXT,"
                + KEY_UID + " TEXT,"
                + KEY_USERNAME  + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // Create tables again
        onCreate(db);
    }

    public void updateUser(int id,String full_name,String fathers_name,String address,String mobile_no,String idproof_no,
                           String blood_group,String designation){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_FULLNAME,full_name);
        cvUpdate.put(KEY_FATHERSNAME,fathers_name);
        cvUpdate.put(KEY_ADDRESS,address);
        cvUpdate.put(KEY_MOBILENO,mobile_no);
        cvUpdate.put(KEY_IDPROOFNO,idproof_no);
        cvUpdate.put(KEY_BLOODGROUP,blood_group);
        cvUpdate.put(KEY_DESIGNATION,designation);
        db.update(TABLE_LOGIN, cvUpdate, KEY_ID + "=" + id, null);
        db.close();
    }

    public HashMap getUserDetails(int id){
        HashMap user = new HashMap();
        String selectQuery = "SELECT * FROM"+ TABLE_LOGIN + " WHERE id = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            user.put("full_name",cursor.getString(1));
            user.put("fathers_name",cursor.getString(2));
            user.put("address",cursor.getString(3));
            user.put("mobile_no",cursor.getString(4));
            user.put("idproof_no",cursor.getString(5));
            user.put("blood_group",cursor.getString(6));
            user.put("designation",cursor.getString(7));
            user.put("username",cursor.getString(8));
            user.put("unique_id",cursor.getString(10));
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
}
