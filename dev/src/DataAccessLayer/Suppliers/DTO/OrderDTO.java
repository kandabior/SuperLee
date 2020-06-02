package DataAccessLayer.Suppliers.DTO;

import DataAccessLayer.Suppliers.OrderMapper;

import java.time.LocalDate;
import java.util.List;

enum Status{Pending,Complete}

public class OrderDTO {
    private int id;
    private int branchId;
    private String suppName;
    private int suppId;
    private String address ;
    private LocalDate orderDate;
    private String phoneNumber;
    private List<OrderLineDTO> orderLines;
    double totalCost;
    Status status;
    private OrderMapper orderMapper;




    public OrderDTO(int branchId , int id, int supplierId , String suppName, String phoneNumber , String address , LocalDate date ) {
        this.branchId = branchId;
        this.id = id;
        // this.items = makeOrderLines(items);
        this.suppName = suppName;
        this.suppId = supplierId;
        this.address = address;
        this.orderDate = date;
        this.phoneNumber = phoneNumber;
        totalCost = 0;
        status = Status.Pending;
    }

    public void setItems(List<OrderLineDTO> lines) {
        this.orderLines = lines;
    }

    public int getId() {
        return id;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getSuppName() {
        return suppName;
    }

    public int getSuppId() {
        return suppId;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderDate() {
        return orderDate.toString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status.toString();
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
