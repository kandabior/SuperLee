package DataAccessLayer.Employee;

import BusinessLayer.EmployeeModule.Employee;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Date;
import java.util.List;

public class EmployeesMapper {
    public Employee getByEID(int employeeID) {
        throw new NotImplementedException();
    }

    public List<EmployeeDTO> getAllEmployeesFromBranch(int branch) {
        throw new NotImplementedException();

    }

    public EmployeeDTO getByID(String id) {
        throw new NotImplementedException();
    }

    public EmployeeDTO addEmployee(String name, String id, String hiringConditions, String bankId, int salary,
                                   Date startOfEmployment, int currentBranch, String license){


        throw new NotImplementedException();

    }

    public void updateEmployee(EmployeeDTO e) {
    }
}
