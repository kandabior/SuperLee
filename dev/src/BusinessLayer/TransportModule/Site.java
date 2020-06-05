package src.BusinessLayer.TransportModule;

public class Site {


    public static int counter = 1 ;
    private int id;
    private String address;
    private String phoneNumber;
    private String contactName;
    private int area;

    public Site(String address, String phoneNumber, String contactName, int area){
        id = counter;
        counter++;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.area = area;
    }
    public Site(int id,String address, String phoneNumber, String contactName, int area){
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.area = area;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }


    public String toString(){
        return "id : "+ id + ", address : " + address + ", phone number : " + phoneNumber +", contact name : " + contactName + ", area : " + area;
    }
}
