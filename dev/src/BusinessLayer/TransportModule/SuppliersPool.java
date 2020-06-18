package src.BusinessLayer.TransportModule;

import src.DataAccessLayer.Transport.DTO.DTO_Store;
import src.DataAccessLayer.Transport.DTO.DTO_Supplier;
import src.DataAccessLayer.Transport.SuppliersMapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SuppliersPool {

    private LinkedList<Supplier> suppliers;
    private SuppliersMapper mapper;

    private static SuppliersPool ourInstance = new SuppliersPool();

    public static SuppliersPool getInstance() {
        return ourInstance;
    }

    private SuppliersPool() {
        suppliers = new LinkedList<Supplier>();
        mapper = new SuppliersMapper();
    }

   /* public void addSupplier(String address, String phoneNumber, String contactName, int area){
        int maxId = mapper.getSupplierId();
        if(maxId >= 0) {
            DTO_Supplier s = new DTO_Supplier(maxId + 1, address, phoneNumber, contactName, area);
            mapper.addSupplier(s);
        }
    }
    public void addSupplier(int id,String address, String phoneNumber, String contactName, int area){
        Supplier s = new Supplier(id,address, phoneNumber,contactName,area);
        suppliers.add(s);
    }

    public void deleteSupplier(int id){
        /*Supplier sp = null;
        for (Supplier s:suppliers){
            if(s.getId() == id)
                sp = s;
        }
        suppliers.remove(sp);
        mapper.removeSupplier(id);
    }

    public List<String> getSuppliers(int area){
        suppliers = new LinkedList<>();
        List<String> output = new LinkedList<>();
        List<DTO_Supplier> areaSupplier = mapper.getSupplierInArea(area);
        Iterator itr = areaSupplier.iterator();
        while (itr.hasNext()){
            DTO_Supplier s = (DTO_Supplier) itr.next();
                    output.add(s.toString());
                    suppliers.add(new Supplier(s.getId(),s.getAddress(),s.getPhoneNumber(),s.getContactName(),s.getArea()));
            }
        return output;
        }


    public boolean validSupplier(int id,int area) {
        Supplier sp = null;
        for (Supplier s:suppliers) {
            if(s.getId() == id && s.getArea() == area)
                sp = s;
        }
        if(sp != null){
            suppliers.remove(sp);
            return true;
        }
        return false;
    }*/

    public List<String> SupplierstoString(){
       return mapper.getSuppliersString();
    }
    /*public void Update(int supplierId,String address, String phoneNumber, String contactName){
        mapper.updateSupplier(supplierId,address,phoneNumber,contactName);
    }*/

    public boolean isExistsId (int id){
      /*  boolean isExists = false;
        for (Supplier s:suppliers){
            if (s.getId() == id)
                isExists = true;
        }
        return isExists;*/
     return mapper.isExist(id);
    }


   /* public boolean validArea(int area) {
       /* for (Supplier s:suppliers){
            if (s.getArea() == area)
                return true;
        }
        return false;
        return mapper.validArea(area);
    }*/

   /* public Supplier getSupplier(int suppId) {
        for (Supplier s:suppliers){
            if(s.getId() == suppId)
                return s;
        }
        return null;
    }*/
}
