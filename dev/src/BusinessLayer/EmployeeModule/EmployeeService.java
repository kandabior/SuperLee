package src.BusinessLayer.EmployeeModule;

import java.util.Date;
import java.util.List;

public class EmployeeService {
    Service service;
    public EmployeeService(){
        service=Service.getInstance();  
    }
    public List<Integer> getStores  (Date date){
        return service.getStoresWithStoreKeeper(date);
    }
    public List<Integer> getDrivers(Date date , String license)
    {
        return service.getDriversAvailableAtDate(date,license);
    }
    public String getDriverName(int id) throws Exception {
        return service.getDriveName(id);
    }
}
