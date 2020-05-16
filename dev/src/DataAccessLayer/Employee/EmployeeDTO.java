package DataAccessLayer.Employee;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDTO {
    private boolean isAvailable;
    private String name;
    private String ID;
    private boolean isSupervisor;
    private List<String> roles;
    private String hiringConditions;
    private String bankId;
    private int salary;
    private Date startOfEmployment;
    private String employeeId;
    private List<String> constrains;
    private int branch;

    public EmployeeDTO(boolean isAvailable, String name, String ID, boolean isSupervisor, List<String> roles, String hiringConditions, String bankId,
                       int salary, Date startOfEmployment, String employeeId, List<String> constrains, int branch) {
        this.isAvailable = isAvailable;
        this.name = name;
        this.ID = ID;
        this.isSupervisor = isSupervisor;
        this.roles = roles;
        this.hiringConditions = hiringConditions;
        this.bankId = bankId;
        this.salary = salary;
        this.startOfEmployment = startOfEmployment;
        this.employeeId = employeeId;
        this.constrains = constrains;
        this.branch = branch;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getHiringConditions() {
        return hiringConditions;
    }

    public String getBankId() {
        return bankId;
    }

    public int getSalary() {
        return salary;
    }

    public Date getStartOfEmployment() {
        return startOfEmployment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public List<String> getConstrains() {
        return constrains;
    }

    public int getBranch() {
        return branch;
    }
}
