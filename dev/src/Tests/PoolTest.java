package Tests;
import BusinessLayer.TransportModule.Pool;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class PoolTest {
    String truckId = "12-234-45";

    Pool pool = Pool.getInstance();

    List<Integer> stores;
    List<Integer> suppliers;

    Date date;
    Date date2;


    @org.junit.Before
    public void setUp() throws Exception {
      /*  pool.addTruck("13-113-13", "2015", 13, 20);
        pool.addTruck("14-114-14", "2018", 10, 20);
        pool.addSupplier(1, "ashdod4", "0955555", "s1", 1);
        pool.addSupplier(2, "ashdod5", "0944444", "s2", 1);
        stores = new LinkedList<>();
        stores.add(1);
        stores.add(2);
        suppliers = new LinkedList<>();
        suppliers.add(1);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 12 - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 11 - 1);//Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date2 = cal.getTime();
        pool.addDoc(1, 1, date2, "13-113-13", "101", "Yossi", stores, suppliers);
        pool.addDateToTruck("13-113-13", date2);
        pool.addDateToDriver(101, date2);
        pool.addDoc(2, 1, date2, "14-114-14", "100", "Moshe", stores, suppliers);
        pool.addDateToTruck("14-114-14", date2);
        pool.addDateToDriver(100, date2);
        pool.addDoc(3, 1, date, "14-114-14", "101", "Yossi", stores, suppliers);
        pool.addDateToTruck("14-114-14", date);
        pool.addDateToDriver(101, date);*/
    }

    @org.junit.After
    public void tearDown() throws Exception {
       /* //pool.removeDriver("100");
        //pool.removeDriver("101");
        pool.removeTruck("13-113-13");
        pool.removeTruck("14-114-14");
        //pool.removeStore(100);
        //pool.removeStore(101);
        //pool.removeStore(102);
        pool.removeSupplier(1);
        pool.removeSupplier(2);
        pool.freeDriverDate(1);
        pool.freeTruckDate(1);
        pool.removeDoc(1);
        pool.freeDriverDate(2);
        pool.freeTruckDate(2);
        pool.removeDoc(2);
        pool.freeDriverDate(3);
        pool.freeTruckDate(3);
        pool.removeDoc(3);*/
    }

    @org.junit.Test
    public void addTruck() {
        assertTrue(pool.isUniqueTruck(truckId));
        pool.addTruck(truckId, "2000", 12.0, 20.0);
        assertFalse(pool.isUniqueTruck(truckId));
        pool.removeTruck(truckId);
    }

    /*@org.junit.Test
   public void addDoc() {
        assertFalse(pool.validTransport(1));
        pool.addDoc(1, 1, date, "13-113-13", "100", "Moshe", stores, suppliers);
        pool.addDateToDriver(100, date);
        pool.addDateToTruck("13-113-13", date);
        assertTrue(pool.validTransport(1));
        pool.freeDriverDate(1);
        pool.freeTruckDate(1);
    }

    @org.junit.Test
    public void validTruck() {
        assertFalse(pool.validTruck("13-113-13", date2));//the truck was added to transport at date2 in the set up
    }*/

    @org.junit.Test
    public void removeTruck() {
        pool.addTruck(truckId, "2000", 12.0, 20.0);
        assertFalse(pool.isUniqueTruck(truckId));
        pool.removeTruck(truckId);
        assertTrue(pool.isUniqueTruck(truckId));
    }

/*
    @org.junit.Test
    public void validStore() {
        assertTrue(pool.);
        assertFalse(pool.validStore(15, 1)); //doesnt exsist
        assertFalse(pool.validStore(100, 2)); //wrong area
        assertTrue(pool.validStore(5, 3)); //right area
    }


    @org.junit.Test
    public void validTansport() {
        assertFalse(pool.validTransport(4));//doesnt exist
        assertTrue(pool.validTransport(3));
        pool.addWeight(3, 100);
        assertFalse(pool.validTransport(3));//failed transport

    }

    @org.junit.Test
    public void addWeight() {
        double ans = pool.addWeight(2, 25);
        assertFalse(ans == 0);
    }


    @org.junit.Test
    public void freeTruckDate() {
        assertFalse(pool.validTruck("14-114-14", date2));
        pool.freeTruckDate(302);
        assertTrue(pool.validTruck("14-114-14", date2));

    }*/
}

