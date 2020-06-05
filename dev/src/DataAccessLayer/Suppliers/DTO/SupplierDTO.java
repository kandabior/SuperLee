package DataAccessLayer.Suppliers.DTO;

public class SupplierDTO {

    private int id;
    private String name;
    private String phoneNumber;
    private int bankAccount;
    private String payment;
    private String supplySchedule;
    private String supplyLocation;

    public SupplierDTO(int id, String name, String phoneNumber, int bankAccount, String payment, String supplySchedule, String supplyLocation) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
        this.supplyLocation = supplyLocation;
        this.supplySchedule = supplySchedule;
        this.bankAccount = bankAccount;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public String getPayment() {
        return payment;
    }

    public String getSupplySchedule() {
        return supplySchedule;
    }

    public String getSupplyLocation() {
        return supplyLocation;
    }

}
