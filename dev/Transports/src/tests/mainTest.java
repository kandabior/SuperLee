package Transports.src.tests;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class mainTest {

    public static void main(String[] args) {
        Result r = JUnitCore.runClasses(tests.PoolTest.class);
        for (Failure f : r.getFailures()){
            System.out.println(f.toString());
        }
        System.out.println("Number of test: "+r.getRunCount());
        if(r.wasSuccessful()){
            System.out.println("All tests passed!!:)");
        }


    }
}
