package src.BusinessLayer.EmployeeModule;

public class Pair<Day,ShiftType> {
    private Day day;
    private ShiftType shiftType;

    public Pair(Day a, ShiftType b){
        day = a;
        shiftType = b;
    }
    public Day getDay() {
        return day;
    }
    public void setDay(Day newDay) {
        this.day = newDay;
    }
    public ShiftType getShiftType() {
        return shiftType;
    }
    public void setShiftType(ShiftType newShiftType) {
        this.shiftType = newShiftType;
    }

    public boolean newEquals(Pair<Day,ShiftType> other){
        return (this.day.equals(other.getDay()) && this.shiftType.equals(other.getShiftType()));
    }
}