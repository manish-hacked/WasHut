package club.cyberlabs.washut;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 24-05-2016.
 */
public class CustomListAdapter extends BaseAdapter {

    public  String KEY_FULLNAME = "full_name";
    public  String KEY_MOBILENO = "mobile_no";
    public  String KEY_DATEOFORDER = "date_of_order";
    public  String KEY_TIMEOFORDER = "time_of_order";
    public  String KEY_ROOMNO = "room_no";
    public  String KEY_PANTS= "pants";
    public  String KEY_SHIRTS= "shirt";
    public  String KEY_BEDSHEETS= "bedsheet";
    public  String KEY_AMOUNT= "amount";


    private ArrayList<HashMap<String, String>> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext,ArrayList<HashMap<String, String>> listData){
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> map = listData.get(position);
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.nameofcustomer);
            holder.mobno= (TextView) convertView.findViewById(R.id.Cmobno);
            holder.roomno = (TextView) convertView.findViewById(R.id.room_no);
            holder.date = (TextView) convertView.findViewById(R.id.Cdate);
            holder.time = (TextView) convertView.findViewById(R.id.Ctime);
            holder.pant = (TextView) convertView.findViewById(R.id.pants);
            holder.shirt = (TextView) convertView.findViewById(R.id.shirts);
            holder.bedsheet = (TextView) convertView.findViewById(R.id.bedsheets);
            holder.amount = (TextView) convertView.findViewById(R.id.amount);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(map.get(KEY_FULLNAME));
        holder.mobno.setText(map.get(KEY_MOBILENO));
        holder.roomno.setText(map.get(KEY_ROOMNO ));
        holder.date.setText(map.get(KEY_DATEOFORDER));
        holder.time.setText(map.get(KEY_TIMEOFORDER));
        holder.pant.setText(map.get(KEY_PANTS));
        holder.shirt.setText(map.get(KEY_SHIRTS));
        holder.bedsheet.setText(map.get(KEY_BEDSHEETS));
        holder.amount.setText(map.get(KEY_AMOUNT));
        return convertView;
    }
    static class ViewHolder {
        TextView name;
        TextView mobno;
        TextView roomno;
        TextView date;
        TextView time;
        TextView pant;
        TextView shirt;
        TextView bedsheet;
        TextView amount;
    }
}
