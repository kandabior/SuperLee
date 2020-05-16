package DataAccessLayer.Employee;

import BusinessLayer.EmployeeModule.Shift;//todo remove and changed to dto

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShiftMapper {

    public List<Integer> getStoresWithStoreKeeperAtDate(Date date) {
        //TODO
        return null;
    }

    public List<Shift_DTO> loadBranch(int branch) {
        //TODO
        return null;
    }

    public void add(Shift_DTO shiftDTOFromShift) {
        //TODO
    }

    public void addRequirements(String dayToString, String sTtoString, Map<String, Integer> roles) {
        //TODO
    }

    public void deleteReq(String dayToString, String stToString) {
        //TODO
    }

    public void deleteShift(Shift_DTO shiftDTOFromShift) {
        //TODO
    }

}
