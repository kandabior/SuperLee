package DataAccessLayer.Employee;

import BusinessLayer.EmployeeModule.Shift;//todo remove and changed to dto

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShiftMapper {

    public List<Shift_DTO> loadBranch(int branch) {
        //TODO
        return null;
    }

    public List<Shift_DTO> loadShiftManager(int branch) {
        //TODO
        return null;
    }

    public void add(List<Shift_DTO> shiftDTOFromShift) {
        //TODO
    }

    public void addRequirements(String dayToString, String sTtoString,String role, int quantity) {
        //TODO
    }

    public void deleteReq(String dayToString, String stToString) {
        //TODO
    }

    public void deleteShift(String date, int branch , String shiftType) {
        //TODO
    }

    public List<Integer> getStoresWithStoreKeeperAtDate(Date date) {
        //TODO
        return null;
    }
}
