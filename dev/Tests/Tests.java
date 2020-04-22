package Tests;
import InterfaceLayer.FacadeController;
import javafx.util.Pair;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;


public class Tests {

    @BeforeEach
    public void setUp() {
        int id = 0;//in the program the id will be static
        String name = "Elad";
        String phoneNumber = "055555555";
        int bankAccount = 123;
        String payment = "Cash";
        String supplySchedule = "Monday";
        String supplyLocation = "Hadera";
        FacadeController.getFacadeController().addSupplier(0, name, phoneNumber, bankAccount, payment, supplySchedule, supplyLocation);
        FacadeController.getFacadeController().addItemToSupplier(0, 0, "milk", "3%", 10);
        FacadeController.getFacadeController().addItemToAgreement(0, 0, 5.0);
        //add Bill
        int itemId = 0;
        Integer itemQuantity = 10;
        Double itemDiscount = 0.5;
        Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
        Map<Integer, Pair<Integer, Double>> map = new HashMap();
        map.put(itemId, pair);
        FacadeController.getFacadeController().addBillOfQuantities(0, map);


        id = 1;//in the program the id will be static
        name = "Dorin";
        String phoneNumber2 = "055555555";
        bankAccount = 111;
        payment = "Credit";
        supplySchedule = "Sunday";
        supplyLocation = "Mashen";
        FacadeController.getFacadeController().addSupplier(1, name, phoneNumber, bankAccount, payment, supplySchedule, supplyLocation);
        //add Items
        FacadeController.getFacadeController().addItemToSupplier(1, 1, "cucumber", "big", 10);
        FacadeController.getFacadeController().addItemToSupplier(1, 2, "tomato", "small", 20);
        FacadeController.getFacadeController().addItemToSupplier(1, 3, "meat", "raw", 30);
        FacadeController.getFacadeController().addItemToAgreement(1, 1, 5.0);
        FacadeController.getFacadeController().addItemToAgreement(1, 2, 15.0);
        FacadeController.getFacadeController().addItemToAgreement(1, 3, 20.0);


    }

    @AfterEach
    public void clean() {
        FacadeController.getFacadeController().deleteSupplier(0);
        FacadeController.getFacadeController().deleteSupplier(1);
    }

    @Test
    public void TestAddSupplierItem() {
        assertEquals("milk", FacadeController.getFacadeController().getItemNameById(0, 0));
        assertEquals("3%", FacadeController.getFacadeController().getItemDescByIndex(0, 0));
        assertEquals("cucumber", FacadeController.getFacadeController().getItemNameById(1, 1));
        assertEquals("big", FacadeController.getFacadeController().getItemDescByIndex(1, 0));
    }

    @Test
    public void TestDeleteSupplier() {
        assertEquals(true, FacadeController.getFacadeController().findSupplier(0));
        FacadeController.getFacadeController().deleteSupplier(0);
        assertEquals(false, FacadeController.getFacadeController().findSupplier(0));
    }

    @Test
    public void TestEditAgreement() {
        assertEquals(5, (int) (FacadeController.getFacadeController().getPriceOfItem(0, 0)));
        FacadeController.getFacadeController().setItemPrice(0, 0, 20.0);
        assertEquals(20, (int) (FacadeController.getFacadeController().getPriceOfItem(0, 0)));
    }

    @Test
    public void addBillOfQuantities() {
        int itemId = 1;
        Integer itemQuantity = 100;
        Double itemDiscount = 0.2;
        Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
        Map<Integer, Pair<Integer, Double>> map = new HashMap();
        map.put(itemId, pair);
        FacadeController.getFacadeController().addBillOfQuantities(1, map);

        Map<Integer, Pair<Integer, Double>> map1 = FacadeController.getFacadeController().getBillOfQuantities(1);
        for (Integer item : map.keySet()) {
            Integer itemQuantity2 = map.get(itemId).getKey();
            Double itemDiscount2 = map.get(itemId).getValue();
            assertEquals(itemQuantity, itemQuantity2);
            assertEquals(itemDiscount, itemDiscount2);
        }
    }

    @Test
    public void EditBillOfQuantities() {
        //add bill for check//
        int itemId = 1;
        Integer itemQuantity = 100;
        Double itemDiscount = 0.2;
        Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
        Map<Integer, Pair<Integer, Double>> map = new HashMap();
        map.put(itemId, pair);
        FacadeController.getFacadeController().addBillOfQuantities(1, map);
        //**************************************//
        Integer newAmount = 200;
        Double newDiscount = 0.3;
        FacadeController.getFacadeController().updateBillOfQuantities(1, itemId, new Pair(newAmount, newDiscount));
        //check if its update//
        Map<Integer, Pair<Integer, Double>> map1 = FacadeController.getFacadeController().getBillOfQuantities(1);
        for (Integer item : map.keySet()) {
            Integer itemQuantity2 = map.get(itemId).getKey();
            Double itemDiscount2 = map.get(itemId).getValue();
            assertEquals(newAmount, itemQuantity2);
            assertEquals(newDiscount, itemDiscount2);
            assertNotEquals(itemQuantity, itemQuantity2);
            assertNotEquals(itemDiscount, itemDiscount2);
        }
    }

    @Test
    public void DeleteFromBillOfQuantities() {
        //add bill for check//
        int itemId = 1;
        Integer itemQuantity = 100;
        Double itemDiscount = 0.2;
        Pair<Integer, Double> pair = new Pair(itemQuantity, itemDiscount);
        Map<Integer, Pair<Integer, Double>> map = new HashMap();
        map.put(itemId, pair);
        FacadeController.getFacadeController().addBillOfQuantities(1, map);
        //***********check the size before delete************************//
        assertEquals(FacadeController.getFacadeController().getBillSize(1), 1);
        //*******now delete and check*************************************//
        FacadeController.getFacadeController().deleteFromBillOfQuantities(1, itemId);
        assertEquals(FacadeController.getFacadeController().getBillSize(1), 0);

    }

    @Test
    public void TestAddOrder() {
        int suppId = 1;
        List<Pair<Integer, Integer>> items = new LinkedList<>();
        int itemId = 1;
        int itemAmount = 5;
        items.add(new Pair(itemId, itemAmount));
        assertEquals(true, FacadeController.getFacadeController().addOrder(0, items, suppId));
        //check order 2
        List<Pair<Integer, Integer>> items2 = new LinkedList<>();
        int itemId2 = 2;
        int itemAmount2 = 5;
        items2.add(new Pair(itemId2, itemAmount2));
        assertEquals(true, FacadeController.getFacadeController().addOrder(1, items2, suppId));

    }

    @Test
    public void testTotalCosyWithoutBill() {
        int suppId = 1;
        List<Pair<Integer, Integer>> items = new LinkedList<>();
        int itemId = 1;
        int itemAmount = 5;
        items.add(new Pair(itemId, itemAmount));
        assertEquals(true, FacadeController.getFacadeController().addOrder(0, items, suppId));
        double totalMoney = FacadeController.getFacadeController().getTotalOrderMoney(0);
        FacadeController.getFacadeController().setOrderCost(0, totalMoney);
        assertEquals(25, (int) FacadeController.getFacadeController().getTotalOrderMoney(0));
        //check order2
        List<Pair<Integer, Integer>> items2 = new LinkedList<>();
        int itemId2 = 2;
        int itemAmount2 = 5;
        items2.add(new Pair(itemId2, itemAmount2));
        assertEquals(true, FacadeController.getFacadeController().addOrder(1, items2, suppId));
        double totalMoney2 = FacadeController.getFacadeController().getTotalOrderMoney(1);
        FacadeController.getFacadeController().setOrderCost(1, totalMoney2);
        assertEquals(75, (int) FacadeController.getFacadeController().getTotalOrderMoney(1));
    }


    @Test
    public void TestAddOrderNotEnoughItem() {
        //check if not enough items
        List<Pair<Integer, Integer>> items3 = new LinkedList<>();
        items3.add(new Pair(0, 20));
        assertEquals(false, FacadeController.getFacadeController().addOrder(0, items3, 0));
    }

    @Test
    public void TestTotalAmountOfOrderWithBill() {
        int suppId = 0;
        List<Pair<Integer, Integer>> items = new LinkedList<>();
        int itemId = 0;
        int itemAmount = 10;
        items.add(new Pair(itemId, itemAmount));
        assertEquals(true, FacadeController.getFacadeController().addOrder(0, items, suppId));
        double totalMoney = FacadeController.getFacadeController().getTotalOrderMoney(0);
        FacadeController.getFacadeController().setOrderCost(0, totalMoney);
        assertEquals(25, (int) FacadeController.getFacadeController().getTotalOrderMoney(0));
    }

}

