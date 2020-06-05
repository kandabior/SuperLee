//package Tests;
//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
//
//public class TestsRunner {
//    public static void main(String[] args) {
//        Result result1= JUnitCore.runClasses(Tests.EmployeesManagerTest.class);
//        for(Failure failure : result1.getFailures()){
//            System.out.println(failure.toString());
//        }
//        System.out.println("Successful tests in EmployeesManager?: " +result1.wasSuccessful());
//
//        Result result2= JUnitCore.runClasses(Tests.ShiftManagerTest.class);
//        for(Failure failure : result2.getFailures()){
//            System.out.println(failure.toString());
//        }
//        System.out.println("Successful tests in ShiftManagerTest?: " +result2.wasSuccessful());
//
//        Result result3= JUnitCore.runClasses(Tests.EmployeeTest.class);
//        for(Failure failure : result3.getFailures()){
//            System.out.println(failure.toString());
//        }
//        System.out.println("Successful tests in EmployeeTest?: " +result3.wasSuccessful());
//
//
//        Result r4 = JUnitCore.runClasses(Tests.PoolTest.class);
//        for (Failure f : r4.getFailures()){
//            System.out.println(f.toString());
//        }
//        System.out.println("Number of test: "+r4.getRunCount());
//        if(r4.wasSuccessful()){
//            System.out.println("All tests In Transport passed!!:)");
//        }
//
//    }
//
//}
//
