package tests;

import BusinessLayer.Day;
import BusinessLayer.ShiftType;
import BusinessLayer.shiftManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ShiftManagerTest {
    private shiftManager shiftManager;

    @Before
    public void setShiftManager(){
        shiftManager=new shiftManager();
        HashMap<String, Integer> roles = new HashMap();
        roles.put("chef", 5);
        shiftManager.editRequirements(Day.Friday, ShiftType.Morning, roles);
        shiftManager.editRequirements(Day.Monday, ShiftType.Evening, roles);

    }

    @Test
    public void DoesExistRequirements() {
        shiftManager.existRequirements(Day.Friday, ShiftType.Morning);
        Assert.assertEquals(1, shiftManager.getRequirements().size());
    }

    @Test
    public void DoesNotExistRequirements() {
        shiftManager.existRequirements(Day.Monday, ShiftType.Morning);
        Assert.assertEquals(2, shiftManager.getRequirements().size());
    }
    @Test
    public void shiftNotExist() {
        Date d2 = null;
        try {
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/04/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(shiftManager.ShiftExists(d2, ShiftType.Morning));
    }



}
