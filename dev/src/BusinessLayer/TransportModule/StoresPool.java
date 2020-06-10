package src.BusinessLayer.TransportModule;


import src.DataAccessLayer.Transport.DTO.DTO_Store;
import src.DataAccessLayer.Transport.StoresMapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StoresPool {

    private LinkedList<Store> stores;
    private StoresMapper mapper;

    private static StoresPool ourInstance = new StoresPool();

    public static StoresPool getInstance() {
        return ourInstance;
    }

    private StoresPool() {

        stores = new LinkedList<Store>();
        mapper = new StoresMapper();
    }

    public void addStore(String address, String phoneNumber, String contactName, int area){
        Store s = new Store(address, phoneNumber,contactName,area);
        stores.add(s);
    }
    public void addStore(int id,String address, String phoneNumber, String contactName, int area){
        Store s = new Store(id,address, phoneNumber,contactName,area);
        stores.add(s);
    }

    public void deleteStore(int id){
        Store sr = null;
        for (Store s:stores){
            if(s.getId() == id)
                sr = s;
        }
        stores.remove(sr);
    }


    public List<String> getStores(int area, List<Integer> availableStores){
        /*List<String> output = new LinkedList<>();
        for (Store s:stores) {
            if (s.getArea() == area)
                output.add(s.toString());
        }
        return output;*/
        stores = new LinkedList<>();
        List<String> output = new LinkedList<>();
        List<DTO_Store> areaStores = mapper.getStoresInArea(area);
        Iterator itr = availableStores.iterator();
        while (itr.hasNext()){
            int storeId = (Integer) itr.next();
            for (DTO_Store s: areaStores) {
                if(storeId == s.getId()) {
                    output.add(s.toString());
                    stores.add(new Store(s.getId(),s.getAddress(),s.getPhoneNumber(),s.getContactName(),s.getArea()));
                }

            }
        }
        return output;
    }

    public String toString(){
        String s = "";
        Iterator itr = stores.iterator();
        while (itr.hasNext()){
            s = s + itr.next().toString() + "\n";
        }
        return s;
    }

    public List<String> StorestoString(){
        return mapper.getStoresString();
    }

    public boolean validStore(int id,int area) {
        Store st = null;
        for (Store s:stores) {
            if(s.getId() == id)
                st = s;
        }
        if(st != null){
            stores.remove(st);
            return true;
        }
        return false;
    }

    public List<String> getStoresStrings(List<Integer> docStores) {
        /*List<String> output = new LinkedList<>();
        Iterator itr = docStores.iterator();
        while (itr.hasNext()){
            output.add(getStore((Integer)itr.next()).toString());
        }
        return output;*/
        List<String> output = new LinkedList<>();
        List<DTO_Store> allStores = mapper.getAllStores();
        Iterator itr = docStores.iterator();
        while (itr.hasNext()){
            int storeId = (Integer) itr.next();
            for (DTO_Store s: allStores) {
                if(storeId == s.getId())
                    output.add(s.toString());
            }
        }
        return output;
    }

    private Store getStore(Integer id) {
        for (Store s:stores){
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    public void Update(int id,String address, String phoneNumber, String contactName){
        Store st = null;
        Iterator<Store> itr = stores.iterator();
        while (itr.hasNext()){
            st = itr.next();
            if(st.getId()==id){
                st.setAddress(address);
                st.setPhoneNumber(phoneNumber);
                st.setContactName(contactName);
            }
        }
    }
    public boolean isExistsId (int id){
        boolean isExists = false;
        for (Store s:stores){
            if (s.getId() == id)
                isExists = true;
        }
        return isExists;
    }


    public boolean validArea(int area) {
        /*for (Store s:stores){
            if (s.getArea() == area)
                return true;
        }
        return false;*/
        return mapper.validArea(area);
    }

    public int getArea(int storId) {
        return mapper.getArea(storId);
    }
}