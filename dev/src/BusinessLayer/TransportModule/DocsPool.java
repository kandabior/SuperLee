package src.BusinessLayer.TransportModule;

import javafx.util.Pair;
import src.DataAccessLayer.Transport.DTO.DTO_Supplier;
import src.DataAccessLayer.Transport.DTO.DTO_TransportDoc;
import src.DataAccessLayer.Transport.DocsMapper;

import java.util.*;

public class DocsPool {

    private LinkedList<TransportDoc> transportDocs;
    private DocsMapper mapper;

    private static DocsPool ourInstance = new DocsPool();

    public static DocsPool getInstance() {
        return ourInstance;
    }

    private DocsPool() {
        transportDocs = new LinkedList<TransportDoc>();
        mapper = new DocsMapper();
    }

    public int addDoc(int area, Date date, String truckId, int driverId, String driverName, List<Integer> stores, List<Integer> suppliers, List<Integer> ordersId){
        //transportDocs.add(new DTO_TransportDoc( area,date,truckId,driverId,driverName,stores,suppliers));
        int maxId = mapper.getMaxdocsId();
        if(maxId >= 0) {
            DTO_TransportDoc s = new DTO_TransportDoc(maxId + 1, area, dateToString(date), truckId , driverId , driverName, stores, suppliers, new LinkedList<>(), "PENDING", -1 );
            mapper.addNewDoc(s);
        }
        mapper.addTransportOrders(maxId + 1, ordersId);
        return maxId + 1;
    }
    public void addDoc(int id,int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers){
        transportDocs.add(new TransportDoc(id,area,date,truckId,driverId,driverName,stores,suppliers));
    }

    public boolean validTransport(int docId) {
       /* for (TransportDoc t:transportDocs) {
            if(t.getId() == docId && t.getStatus() == TransportDoc.Status.PENDING)
                return true;
        }
        return false;*/
        return mapper.validTransport(docId) && mapper.notMissing(docId);
    }


    public String getTruckId(int docId) {
        for (TransportDoc t:transportDocs){
            if(t.getId() == docId)
                return t.getTruckId();
        }
        return "";
    }

    public List<Integer> getStoresFromDoc(int docId) {
        /*TransportDoc doc = null;
        for (TransportDoc t : transportDocs) {
            if (t.getId() == docId)
                doc = t;
        }
        if(doc != null){
            return doc.getStores();
        }
        return new LinkedList<>();*/
        return mapper.getTStores(docId);
    }

    public void addItems(int docId,List<Integer> stores, List<Map<Pair<Integer, Integer>, Pair<Integer, Double>>> allItems) {
       /* for (TransportDoc t : transportDocs) {
            if (t.getId() == docId) {
                t.setItems(allItems);
                if(t.getFinalWeight() != -1)
                    t.setStatus(TransportDoc.Status.SUCCESS);
            }

        }*/
        mapper.addItemsToTransport(docId, stores, allItems);
    }

    public List<String> getFailDocs(){
        return mapper.getDocs("FAIL");

    }

    public List<String> getPendingDocs(){
       /* Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING))
                output.add(td.toString());
        }*/
        return mapper.getDocs("PENDING");
    }

    public String toString(){
        String s = "";
        Iterator itr = transportDocs.iterator();
        while (itr.hasNext()){
            s = s + itr.next().toString() + "\n";
        }
        return s;
    }

    public Date getDate(int docId) {
        for (TransportDoc t:transportDocs){
            if(t.getId() == docId)
                return t.getDate();
        }
        return new Date();
    }

    /*public String getDriverId(int docId) {
        for (TransportDoc t:transportDocs){
            if(t.getId() == docId)
                return t.getDriverId();
        }
        return "";
    }*/

    public void removeDoc(int id) {
        /*TransportDoc doc = null;
        for (TransportDoc t:transportDocs){
            if(t.getId() == id)
                doc = t;
        }
        transportDocs.remove(doc);*/
        mapper.removeDoc(id);
    }

    public List<String> getSuccessDoc() {
        return mapper.getDocs("SUCCESS");
    }

    public boolean supplierIsBusy(int suppId) {
        /*Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING) && td.getSuppliers().contains(suppId))
                return true;
        }*/
        return mapper.supplierIsBusy(suppId);
    }

    public boolean storeIsBusy(int sId) {
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING) && td.getStores().contains(sId))
                return true;
        }
        return false;
    }

    public boolean driverIsBusy(String driverId) {
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING) && td.getDriverId().equals(driverId))
                return true;
        }
        return false;
    }

    public boolean truckIsBusy(String truckId) {
        return mapper.truckIsBusy(truckId);
    }

    public double addWeight(DTO_TransportDoc dto_doc, double total,double maxWeight) {

        double output;
        // TransportDoc doc = null;
      /*  for (TransportDoc t : transportDocs) {
            if (t.getId() == id)
                doc = t;
        }*/
        if (total <= maxWeight) {
            if (dto_doc != null) {
                dto_doc.setFinalWeight(total);
                if(!dto_doc.getItems().get(0).isEmpty())//if the doc contain all the detail,including the items list.
                    dto_doc.setStatus(TransportDoc.Status.SUCCESS);
            }
            output = 0;
        } else {
            if (dto_doc != null) {
                dto_doc.setStatus(TransportDoc.Status.FAIL);
                dto_doc.setFinalWeight(total);
            }
            output = total-maxWeight;
        }
        String status;
        switch (dto_doc.getStatus()) {
            case SUCCESS:
                status = "SUCCESS";
                break;
            case PENDING:
                status = "PENDING";
                break;
            default: //FAIL
                status = "FAIL";
        }
        mapper.updateTransportDoc(dto_doc.getFinalWeight(),status,dto_doc.getId());
        return output;
    }

    public DTO_TransportDoc getDoc(int docId) {
        return mapper.getTransportDoc(docId);
    }
    public DTO_TransportDoc getFailDoc(int docId) {
        return mapper.getFailDoc(docId);
    }
    private static Date stringToDate(String s){
        String[] parts = s.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(parts[2].substring(0,4)));
        cal.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
        return cal.getTime();
    }

    private static String dateToString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/" + calendar.get(Calendar.YEAR);
    }

    public void addMissingEmployees(int docId, int hasStoreKeeper, int hasDriver) {
        mapper.addMissingEmployees(docId, hasStoreKeeper, hasDriver);
    }

    public void addMissingMsg(int storeId, int docId, int hasStoreKeeper, int hasDriver, Date date) {
        String storeKeeper = "";
        String driver = "";
        if(hasStoreKeeper == 0)
            storeKeeper = "storeKeeper";
        if(hasDriver == 0)
            driver = "driver";
        String msg = "Dear Employees Manager, In store " + storeId +" On " + dateToString(date) + " missing " + storeKeeper +  " " + driver;
        mapper.addMissingMsg(docId, msg);
    }

    public List<Integer> getOrdersId(int docId) {
        return mapper.getOrdersId(docId);
    }

    public List<Integer> getWaitingTransportIds() {
        return mapper.getWaitingTransportIds();
    }

    public void removeFromWaitingTransport(int id) {
        mapper.removeWaitingTransport(id);
    }
    public void updateFailTransport(int id) {
        mapper.updateTransportDoc(-1 ,"FAIL", id);
    }

    public void addDriverToTransport(int id, int driverId, String driverName) {
        mapper.addDriverToTransport(id, driverId, driverName);
    }

    public Map<Pair<Integer, Integer>, Pair<Integer, Double>> getFullItems(int docId) {
        return mapper.getFullItems(docId);
    }

    public List<Integer> hasTransport(String date) {
        return mapper.hasTransport(date);
    }

    public List<Integer> hasTransport(int brunchId, String date) {
        return mapper.hasTransport(brunchId, date);
    }

    public List<Integer> getRequest() {
        return mapper.getRequest();
    }

    public String getReqString(Integer orderId) {
        return mapper.getReqString(orderId);
    }

    public int getTransportId(int orderId) {
        return mapper.getTransportId(orderId);
    }

    public void removeRequest(int orderId) {
        mapper.removeRequest(orderId);
    }

    public String getTransportDate(int docId) {
        return mapper.getTransportDate(docId);
    }
}