package Tests;

import IntefaceLayer.Register;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;


public class Tests {


    @org.junit.Test
    public void addProdTest1(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",1, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        assert register.getquantityById(1).getValue().getValue()==20;
    }

    @org.junit.Test
    public void addProdTest2(){
        Register register=new Register();

        List<String> cat2 = new LinkedList<>();
        cat2.add("Halavi");
        cat2.add("Cartons");
        LocalDate date2 = LocalDate.of(20, 8,31);
        register.addProduct("Or", "1234",2, 30, "Milk",3,
                6, date2,cat2,"Tnuva", 20, "area2");
        assert !register.totalStockReport().contains("Milki");
    }

    @org.junit.Test
    public void ChangeAmount1(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",3, 20, "Milki",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.addAmountToProduct(3,30);
        assert register.getquantityById(3).getValue().getValue()==50;
    }

    @org.junit.Test
    public void changeAmount2(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",4, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");

        register.removeAmountFromProduct(4,5);
        assert register.getquantityById(4).getValue().getValue()==15;
    }

    @org.junit.Test
    public void transferAmountToStorage(){

        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",5, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.shelfToStorage(5,5);
        assert register.getquantityById(5).getValue().getValue()==15 &&
                register.getquantityById(5).getValue().getKey()==5;
    }

    @org.junit.Test
    public void transferAmountToShelf(){

        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",6, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.shelfToStorage(6,5);
        register.storageToShelf(6,2);
        assert register.getquantityById(6).getValue().getValue()==17 &&
                register.getquantityById(6).getValue().getKey()==3;
    }

    @org.junit.Test
    public void MinTest(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",7, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.removeAmountFromProduct(7,15);
        assert register.NeedToBuyReport().contains("Corn");
    }

    @org.junit.Test
    public void EXPTest(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",8, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.setDefectiveProducts(8,5);
        assert register.getquantityEXPById(8).getValue()==5;
    }

    @org.junit.Test
    public void changeSalePriceById(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",9, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");

        register.setSalePriceById("Erez","1234",9,15);

        assert register.getProductSalePrice(9)==15;
    }

    @org.junit.Test
    public void changeSalePriceByCategory(){
        Register register=new Register();
        register.addGlobalManager("Erez", "1234");
        List<String> cat1 = new LinkedList<>();
        cat1.add("Shimurim");
        LocalDate date1 = LocalDate.of(20, 12,31);
        register.addProduct("Erez", "1234",10, 20, "Corn",5,
                8, date1,cat1,"Osem", 10, "area1");
        register.addProduct("Erez", "1234",11, 20, "Corn",5,
                13, date1,cat1,"Osem", 10, "area1");
        register.setPriceByCategory("Erez","1234",cat1,15);

        assert register.getProductSalePrice(10)==15 && register.getProductSalePrice(11)==15;
    }




}
