package businessLayer.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SuppliersPool {

    private LinkedList<Supplier> suppliers;

    private static SuppliersPool ourInstance = new SuppliersPool();

    public static SuppliersPool getInstance() {
        return ourInstance;
    }

    private SuppliersPool() {

        suppliers = new LinkedList<Supplier>();
    }

    public void addSupplier(String address, String phoneNumber, String contactName, int area){
        Supplier s = new Supplier(address, phoneNumber,contactName,area);
        suppliers.add(s);
    }
    public void addSupplier(int id,String address, String phoneNumber, String contactName, int area){
        Supplier s = new Supplier(id,address, phoneNumber,contactName,area);
        suppliers.add(s);
    }

    public void deleteSupplier(int id){
        Supplier sp = null;
        for (Supplier s:suppliers){
            if(s.getId() == id)
                sp = s;
        }
        suppliers.remove(sp);
    }

    public List<String> getSuppliers(int area){
        List<String> output = new LinkedList<>();
        for (Supplier s:suppliers) {
            if (s.getArea() == area)
                output.add(s.toString());
        }
        return output;
    }

    public boolean validSupplier(int id,int area) {
        for (Supplier s:suppliers) {
            if(s.getId() == id && s.getArea() == area)
                return true;
        }
        return false;
    }

    public String toString(){
        String s = "";
        Iterator itr = suppliers.iterator();
        while (itr.hasNext()){
            s = s + itr.next().toString() + "\n";
        }
        return s;
    }
    public void Update(int id,String address, String phoneNumber, String contactName){
        Supplier sp = null;
        Iterator<Supplier> itr = suppliers.iterator();
        while (itr.hasNext()){
            sp =  itr.next();
            if(sp.getId()==id){
                sp.setAddress(address);
                sp.setPhoneNumber(phoneNumber);
                sp.setContactName(contactName);
            }
        }
    }

    public boolean isExistsId (int id){
        boolean isExists = false;
        for (Supplier s:suppliers){
            if (s.getId() == id)
                isExists = true;
        }
        return isExists;
    }


    public boolean validArea(int area) {
        for (Supplier s:suppliers){
            if (s.getArea() == area)
                return true;
        }
        return false;
    }

   /* public Supplier getSupplier(int suppId) {
        for (Supplier s:suppliers){
            if(s.getId() == suppId)
                return s;
        }
        return null;
    }*/
}
