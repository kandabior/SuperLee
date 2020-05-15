package DataAccessLayer.Employee;

import BusinessLayer.EmployeeModule.Employee;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.List;

public class EmployeesMapper {
    public Employee getByEID(int employeeID) {
        throw new NotImplementedException();
    }

    public List<Employee> getAllEmployeesFromBranch(int branch) {
        throw new NotImplementedException();

    }

    public Employee getByID(String id) {
        throw new NotImplementedException();
    }

    public Employee addEmployee(String name, String id, String hiringConditions, String bankId, int salary,
                                Date startOfEmployment, int currentBranch)
    {
        throw new NotImplementedException();

    }

    public void updateEmployee(Employee e) {
    }
}
