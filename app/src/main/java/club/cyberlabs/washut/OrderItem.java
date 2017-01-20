package club.cyberlabs.washut;

/**
 * Created by HP on 24-05-2016.
 */
public class OrderItem {

    private String name,mobile_number,date_of_order,time_of_order,room_no,pants,shirt,bedsheet,amount;
    public String getName() {
        return name;
    }
    public String getMobileNo() {
        return mobile_number;
    }
    public String getDate_of_order() {
        return date_of_order;
    }
    public String getTime_of_order() {
        return time_of_order;
    }
    public String getRoom_no() {
        return room_no;
    }
    public String getPants() {
        return pants;
    }
    public String getShirt(){
        return shirt;
    }
    public String getBedsheet(){
        return bedsheet;
    }
    public String getAmount() {
        return amount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
    public void setDate_of_order(String date_of_order) {
        this.date_of_order = date_of_order;
    }
    public void setTime_of_order(String time_of_order) {
        this.time_of_order = time_of_order;
    }
    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
    public void setPants(String pants) {
        this.pants = pants;
    }
    public void setShirt(String shirt) {
        this.shirt = shirt;
    }
    public void setBedsheet(String bedsheet) {
        this.bedsheet = bedsheet;
    }
    public void setAmount(String name) {
        this.amount = amount;
    }
}
