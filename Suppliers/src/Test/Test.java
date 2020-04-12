package Test;
import classs.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
class Tests {

    @BeforeEach
    public void setUp()
    {







    }


    @Test
    public void  TestAddSupplier()
    {
        int id =0;//in the program the id will be static
        String name="Elad";
        String phoneNumber = "055555555";
        int bankAccount = 123;
        String payment= "Cash";
        String supplySchedule = "Monday";
        String supplyLocation = "Hadera";
        FacadeController.getFacadeController().addSupplier(0,name,phoneNumber,bankAccount,payment,supplySchedule,supplyLocation);
        FacadeController.getFacadeController().addItemToSupplier(0,0,"milk","3%",10);
        assertEquals("milk",FacadeController.getFacadeController().getItemNameById(0,0));



    }



}
