//package Tests;
//import BusinessLayer.TransportModule.Pool;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//
//public class PoolTest {
//    String truckId = "12-234-45";
//
//    Pool pool = Pool.getInstance();
//
//    List<Integer> stores;
//    List<Integer> suppliers;
//
//    Date date;
//    Date date2;
//
//
//    @org.junit.Before
//    public void setUp() throws Exception {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2020);
//        cal.set(Calendar.MONTH, 12 - 1);//Calendar.DECEMBER);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        date = cal.getTime();
//
//    }
//
//    @org.junit.After
//    public void tearDown() throws Exception {
//
//    }
//
//    @org.junit.Test
//    public void addTruck() {
//        assertTrue(pool.isUniqueTruck(truckId));
//        pool.addTruck(truckId, "2000", 12.0, 20.0);
//        assertFalse(pool.isUniqueTruck(truckId));
//        pool.removeTruck(truckId);
//    }
//
//    @org.junit.Test
//    public void ValidTruck() {
//        assertTrue(pool.validTruck("100",date));
//    }
//
//    @org.junit.Test
//    public void IsFreeTruck() {
//        pool.addDateToTruck("200", date);
//        assertFalse(pool.validTruck("200",date));
//        pool.freeTruckDate("200",date);
//    }
//
//    @org.junit.Test
//    public void ValidDriver() {
//        List<Integer> driver = new LinkedList<>();
//        driver.add(11);
//        driver = pool.validDriver(driver,date);
//        assertFalse(driver.isEmpty());
//    }
//
//    @org.junit.Test
//    public void IsFreeDriver() {
//        pool.addDateToDriver(10, date);
//        List<Integer> driver = new LinkedList<>();
//        driver.add(10);
//        driver = pool.validDriver(driver,date);
//        assertTrue(driver.isEmpty());
//        pool.freeDriverDate(10,date);
//    }
//
//    @org.junit.Test
//    public void removeTruck() {
//        pool.addTruck(truckId, "2000", 12.0, 20.0);
//        assertFalse(pool.isUniqueTruck(truckId));
//        pool.removeTruck(truckId);
//        assertTrue(pool.isUniqueTruck(truckId));
//    }
//}
//
