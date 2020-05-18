package Tests;
import BusinessLayer.TransportModule.Pool;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/*
public class PoolTest {
    String truckId = "12-234-45";

    Pool pool = Pool.getInstance();

    List<Integer> stores;
    List<Integer> suppliers;

    Date date;
    Date date2;


    @org.junit.Before
    public void setUp() throws Exception {
<<<<<<< HEAD
         /*   pool.addDriver("100", "Moshe", "C1");
            pool.addDriver("101", "Yossi", "C");
            pool.addTruck("13-113-13", "2015", 13, 20);
            pool.addTruck("14-114-14", "2018", 10, 20);
            pool.addStore(100,"ashdod1", "0988888", "a1", 1);
            pool.addStore(101,"ashdod2", "0977777", "a2", 1);
            pool.addStore(102,"ashdod3", "0966666", "a3", 2);
            pool.addSupplier(200,"ashdod4", "0955555", "s1", 1);
            pool.addSupplier(201,"ashdod5", "0944444", "s2", 1);
            stores = new LinkedList<>();
            stores.add(1);
            stores.add(2);
            suppliers = new LinkedList<>();
            suppliers.add(4);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2020);
            cal.set(Calendar.MONTH, 12 - 1);//Calendar.DECEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
            cal.set(Calendar.YEAR, 2020);
            cal.set(Calendar.MONTH, 11 - 1);//Calendar.NOVEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            date2 = cal.getTime();
            pool.addDoc(300,1, date2, "13-113-13", "101", "Yossi", stores, suppliers);
            pool.addDateToTruck("13-113-13", date2);
            pool.addDateToDriver("101", date2);
            pool.addDoc(302,1, date2, "14-114-14", "100", "Moshe", stores, suppliers);
            pool.addDateToTruck("14-114-14", date2);
            pool.addDateToDriver("100", date2);
            pool.addDoc(303,1, date, "14-114-14", "101", "Yossi", stores, suppliers);
            pool.addDateToTruck("14-114-14", date);
            pool.addDateToDriver("101", date); */
=======
        pool.addDriver("100", "Moshe", "C1");
        pool.addDriver("101", "Yossi", "C");
        pool.addTruck("13-113-13", "2015", 13, 20);
        pool.addTruck("14-114-14", "2018", 10, 20);
        pool.addStore(100,"ashdod1", "0988888", "a1", 1);
        pool.addStore(101,"ashdod2", "0977777", "a2", 1);
        pool.addStore(102,"ashdod3", "0966666", "a3", 2);
        pool.addSupplier(200,"ashdod4", "0955555", "s1", 1);
        pool.addSupplier(201,"ashdod5", "0944444", "s2", 1);
        stores = new LinkedList<>();
        stores.add(1);
        stores.add(2);
        suppliers = new LinkedList<>();
        suppliers.add(4);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 12 - 1);//Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 11 - 1);//Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date2 = cal.getTime();
        pool.addDoc(300,1, date2, "13-113-13", "101", "Yossi", stores, suppliers);
        pool.addDateToTruck("13-113-13", date2);
        pool.addDateToDriver("101", date2);
        pool.addDoc(302,1, date2, "14-114-14", "100", "Moshe", stores, suppliers);
        pool.addDateToTruck("14-114-14", date2);
        pool.addDateToDriver("100", date2);
        pool.addDoc(303,1, date, "14-114-14", "101", "Yossi", stores, suppliers);
        pool.addDateToTruck("14-114-14", date);
        pool.addDateToDriver("101", date);
>>>>>>> e69ef687f634e26f1f2ada2a39897e97e24d7167
    }
/*
    @org.junit.After
    public void tearDown() throws Exception {
        pool.removeDriver("100");
        pool.removeDriver("101");
        pool.removeTruck("13-113-13");
        pool.removeTruck("14-114-14");
        pool.removeStore(100);
        pool.removeStore(101);
        pool.removeStore(102);
        pool.removeSupplier(200);
        pool.removeSupplier(201);
        pool.freeDriverDate(300);
        pool.freeTruckDate(300);
        pool.removeDoc(300);
        pool.freeDriverDate(302);
        pool.freeTruckDate(302);
        pool.removeDoc(302);
        pool.freeDriverDate(303);
        pool.freeTruckDate(303);
        pool.removeDoc(303);
    }

    @org.junit.Test
    public void addTruck() {
        assertTrue(pool.isUniqueTruck(truckId));
        pool.addTruck(truckId,"2000",12.0,20.0);
        assertFalse(pool.isUniqueTruck(truckId));
        pool.removeTruck(truckId);
    }
    @org.junit.Test
    public void addDoc() {
        assertFalse(pool.validTansport(301));
        pool.addDoc(301,1,date,"13-113-13","100","Moshe",stores,suppliers);
        pool.addDateToDriver("100",date);
        pool.addDateToTruck("13-113-13",date);
        assertTrue(pool.validTansport(301));
    }

    @org.junit.Test
    public void validTruck() {
        assertFalse(pool.validTruck("13-113-13",date2));//the truck wad addedto transport at date2 in the set up
    }

    @org.junit.Test
    public void removeTruck() {
        pool.addTruck(truckId,"2000",12.0,20.0);
        assertFalse(pool.isUniqueTruck(truckId));
        pool.removeTruck(truckId);
        assertTrue(pool.isUniqueTruck(truckId));
    }

    @org.junit.Test
    public void validStore() {
        assertFalse(pool.validStore(110,1)); //doesnt exsist
        assertFalse(pool.validStore(100,2)); //wrong area
        assertTrue(pool.validStore(102,2)); //right area
    }

    @org.junit.Test
    public void validDriver() {
        assertFalse(pool.validDriver("101","13-113-13",date2));
        pool.addWeight(300,21); // over weight
        pool.freeDriverDate(300);
        assertTrue(pool.validDriver("101","13-113-13",date2));
    }

    @org.junit.Test
    public void validTansport() {
        assertFalse(pool.validTansport(310));//doesnt exist
        assertTrue(pool.validTansport(303));
        pool.addWeight(303,100);
        assertFalse(pool.validTansport(303));//failed transport

    }

    @org.junit.Test
    public void addWeight() {
        double ans = pool.addWeight(302,25);
        assertFalse(ans == 0);
    }

    @org.junit.Test
    public void validLicense() {
        assertFalse(pool.validLicense("C8"));
        assertTrue(pool.validLicense("C"));
        assertTrue(pool.validLicense("C1"));

    }

    @org.junit.Test
    public void freeTruckDate() {
        assertFalse(pool.validTruck("14-114-14",date2));
        pool.freeTruckDate(302);
        assertTrue(pool.validTruck("14-114-14",date2));

<<<<<<< HEAD
    }*/
}
=======
    }
}*/
>>>>>>> e69ef687f634e26f1f2ada2a39897e97e24d7167
