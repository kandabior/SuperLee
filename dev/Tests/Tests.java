package Tests;

import IntefaceLayer.Register;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class Tests {

    private Register register;



    @Test
    public void addProdTest1(){
        register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        assert register.totalStockReport().contains("Corn");
    }

    @Test
    public void addProdTest2(){
        register=new Register();

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        register.addProduct("Or", "1234",2, 30, "Milk",3,
                6, date2,cat2,"Tnuva", 20, "area2");
        assert !register.totalStockReport().contains("Milki");
    }

    @Test
    public void ChangeAmount1(){
        register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.addAmountToProduct(1,30);
        assert register.totalStockReport().contains("50");
    }

    @Test
    public void changeAmount2(){
        register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");

        register.removeAmountFromProduct(1,5);
        assert register.totalStockReport().contains("15");
    }

    @Test
    public void transferAmountToStorage(){

        register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.shelfToStorage(1,10);
        assert register.StorageReport().contains("10");
    }
}
