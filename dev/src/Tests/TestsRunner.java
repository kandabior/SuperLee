package Tests;
import BusinessLayer.EmployeesManager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestsRunner {
    public static void main(String[] args) {
        Result result1= JUnitCore.runClasses(Tests.EmployeesManagerTest.class);
        for(Failure failure : result1.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println("Successful tests in EmployeesManager?: " +result1.wasSuccessful());

        Result result2= JUnitCore.runClasses(Tests.EmployeesManagerTest.class);
        for(Failure failure : result2.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println("Successful tests in EmployeesManager?: " +result2.wasSuccessful());
        Result result3= JUnitCore.runClasses(Tests.EmployeesManagerTest.class);
        for(Failure failure : result3.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println("Successful tests in EmployeesManager?: " +result3.wasSuccessful());
    }

}
