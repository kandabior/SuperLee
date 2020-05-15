package Tests;

import BusinessLayer.EmployeeModule.Employee;
import BusinessLayer.EmployeeModule.service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeTest {
    private Employee employee;
    private service service;
    @Before
    public void singleEmployee() {
        Date d2 = null;
        try {
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employee = new Employee("Raviv", "315", "a lot ", "555", 30000, d2, "1");
    }
    @Before
    public void setService() {
        service =new service();
        Date d2 = null;
        try {
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        service.addWorker("Raviv", "315", "a lot ", "555", 30000, d2);
        service.addWorker("Hodaya", "320", "a lot ", "555", 30000, d2);
        service.setSupervisor("2",true);
        service.addRole("1","Chef");
        service.addRole("2","Chef");

        service.addConstrain("2","Sunday","Morning");
    }
    @Test
    public void roleEsist() {
        Assert.assertFalse(employee.checkRoleExist("Chef"));

        employee.addRole("Chef");
        Assert.assertTrue("Get role doesn't work after add",employee.checkRoleExist("Chef"));

        employee.deleteRole("Chef");
        Assert.assertFalse("Get role doesn't work after delete",employee.checkRoleExist("Chef"));

    }

    @Test
    public void checkHasConstrain() {


        List<String> employees= service.relevantEmployees("Sunday","Morning","Chef");
        Assert.assertTrue("Not good constrains",employees.indexOf("Hodaya;2")==-1);
        Assert.assertFalse("Not good constrains",employees.indexOf("Raviv;1")==-1);

    }

    @Test
    public void getManagers() {
        List<String> managers=service.relevantPersonnelManager("Sunday","Evening");
        Assert.assertTrue("Not good managers fetcher",managers.indexOf("Hodaya;2")!=-1);
    }
    @Test
    public void getManagers2() {
        List<String> managers=service.relevantPersonnelManager("Sunday","Evening");
        Assert.assertTrue("Not good managers fetcher",managers.indexOf("Raviv;1")==-1);
    }
    @Test
    public void aviliable() {
        service.deleteWorker("2");
        List<String> managers=service.relevantPersonnelManager("Sunday","Evening");
        Assert.assertFalse("Not good managers fetcher",managers.indexOf("Hodaya;2")!=-1);
    }

}