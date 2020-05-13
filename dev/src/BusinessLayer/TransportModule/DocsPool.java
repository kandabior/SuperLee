package BusinessLayer.TransportModule;

import java.util.*;

public class DocsPool {

    private LinkedList<TransportDoc> transportDocs;

    private static DocsPool ourInstance = new DocsPool();

    public static DocsPool getInstance() {
        return ourInstance;
    }

    private DocsPool() {
        transportDocs = new LinkedList<TransportDoc>();
    }

    public void addDoc(int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers){
        transportDocs.add(new TransportDoc(area,date,truckId,driverId,driverName,stores,suppliers));
    }
    public void addDoc(int id,int area, Date date, String truckId, String driverId, String driverName, List<Integer> stores, List<Integer> suppliers){
        transportDocs.add(new TransportDoc(id,area,date,truckId,driverId,driverName,stores,suppliers));
    }

    public boolean validTransport(int docId) {
        for (TransportDoc t:transportDocs) {
            if(t.getId() == docId && t.getStatus() == TransportDoc.Status.PENDING)
                return true;
        }
        return false;
    }

    public double addWeight(int id, double total,double maxWeight) {
        TransportDoc doc = null;
        for (TransportDoc t : transportDocs) {
            if (t.getId() == id)
                doc = t;
        }
        if (total <= maxWeight) {
            if (doc != null) {
                doc.setFinalWeight(total);
                if(!doc.getItems().isEmpty())//if the doc contain all the detail,including the items list.
                  doc.setStatus(TransportDoc.Status.SUCCESS);
            }
            return 0;
        } else {
            if (doc != null) {
                doc.setStatus(TransportDoc.Status.FAIL);
                doc.setFinalWeight(total);
            }
            return total-maxWeight;
        }
    }

    public String getTruckId(int docId) {
        for (TransportDoc t:transportDocs){
            if(t.getId() == docId)
               return t.getTruckId();
        }
        return "";
    }

    public List<Integer> getStoresFromDoc(int docId) {
        TransportDoc doc = null;
        for (TransportDoc t : transportDocs) {
            if (t.getId() == docId)
                doc = t;
        }
        if(doc != null){
            return doc.getStores();
        }
        return new LinkedList<>();
    }

    public void addItems(int docId, List<Map<Integer, Integer>> allItems) {
        for (TransportDoc t : transportDocs) {
            if (t.getId() == docId) {
                t.setItems(allItems);
                if(t.getFinalWeight() != -1)
                    t.setStatus(TransportDoc.Status.SUCCESS);
            }

        }
    }

    public LinkedList<String> getFailDocs(){
        LinkedList<String> output = new LinkedList<>();
        Iterator<TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.FAIL))
                output.add(td.toString());
        }
        return output;
    }

    public LinkedList<String> getPendingDocs(){
        LinkedList<String> output = new LinkedList<>();
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING))
                output.add(td.toString());
        }
        return output;
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

    public String getDriverId(int docId) {
        for (TransportDoc t:transportDocs){
            if(t.getId() == docId)
                return t.getDriverId();
        }
        return "";
    }

    public void removeDoc(int id) {
        TransportDoc doc = null;
        for (TransportDoc t:transportDocs){
            if(t.getId() == id)
                doc = t;
        }
        transportDocs.remove(doc);
    }

    public List<String> getSuccessDoc() {
        LinkedList<String> output = new LinkedList<>();
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.SUCCESS))
                output.add(td.toString());
        }
        return output;
    }

    public boolean supplierIsBusy(int suppId) {
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING) && td.getSuppliers().contains(suppId))
                return true;
        }
        return false;
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
        Iterator <TransportDoc> itr = transportDocs.iterator();
        while (itr.hasNext()){
            TransportDoc td = itr.next();
            if(td.getStatus().equals(TransportDoc.Status.PENDING) && td.getTruckId().equals(truckId))
                return true;
        }
        return false;
    }
}
