package BusinessLayer;

public class Constrain {
    private ShiftType shiftType;
    private Day day;

    public Constrain(ShiftType shiftType, Day day) {
        this.shiftType = shiftType;
        this.day = day;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public Day getDay() {
        return day;
    }

}