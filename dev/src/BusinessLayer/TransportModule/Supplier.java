package src.BusinessLayer.TransportModule;

public class Supplier extends Site {

    public Supplier(String address, String phoneNumber, String contactName, int area) {
        super(address, phoneNumber, contactName, area);
    }
    public Supplier(int id,String address, String phoneNumber, String contactName, int area) {
        super(id,address, phoneNumber, contactName, area);
    }
}
