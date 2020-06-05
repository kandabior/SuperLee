package src.BusinessLayer.TransportModule;

public class Store extends Site {

    public Store(String address, String phoneNumber, String contactName, int area) {
        super(address, phoneNumber, contactName, area);
    }
    public Store(int id,String address, String phoneNumber, String contactName, int area) {
        super(id,address, phoneNumber, contactName, area);
    }

}
