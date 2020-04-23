package tests;

import BusinessLayer.Employee;
import BusinessLayer.EmployeesManager;
import BusinessLayer.service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class EmployeesManagerTest {
    EmployeesManager employeesManager;
    @Before
    public void setEmployee(){
        employeesManager=new EmployeesManager();
        Date d2 = null;
        try {
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employeesManager.addWorker("Raviv", "315", "a lot ", "555", 30000, d2);
    }
    @Test
    public void getEmployeeDetails() {
    employeesManager.getEmployeeDetails("1");
        Assert.assertFalse("get Employee details doesn't work","no such emplyee".equals(employeesManager
                .getEmployeeDetails("1")));
    }

    @Test
    public void emplyeeExist() {
        Assert.assertTrue(employeesManager.emplyeeExist("1"));
        Assert.assertFalse(employeesManager.emplyeeExist("2"));

    }


}