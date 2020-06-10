package src.DataAccessLayer.Suppliers.DTO;

import src.DataAccessLayer.Suppliers.OrderMapper;

import java.time.LocalDate;
import java.util.List;

enum Status{Pending,Complete,Cancel,Waiting}

public class OrderDTO {
    private int id;
    private int branchId;
    private String suppName;
    private int suppId;
    private String address ;
    private LocalDate orderDate;
    private List<Integer> orderDays;
    private String phoneNumber;
    private List<DataAccessLayer.Suppliers.DTO.OrderLineDTO> orderLines;
    double totalCost;
    Status status;
    private OrderMapper orderMapper;


    public OrderDTO(int branchId , int id, int supplierId, String suppName, String phoneNumber, String address, LocalDate date,String status) {
        this.branchId = branchId;
        this.id = id;
        this.suppName = suppName;
        this.suppId = supplierId;
        this.address = address;
        this.orderDate = date;
        this.phoneNumber = phoneNumber;
        totalCost = 0;
        switch (status){
            case "Pending":
            {
                this.status=Status.Pending;
                break;
            }
            case "Waiting":
            {
                this.status=Status.Waiting;
                break;
            }

        }
    }

    public OrderDTO(int branchId , int id, int supplierId , String suppName, String phoneNumber , String address , List<Integer> days,String status) {
        this.branchId = branchId;
        this.id = id;
        this.suppName = suppName;
        this.suppId = supplierId;
        this.address = address;
        this.orderDays = days;
        this.phoneNumber = phoneNumber;
        totalCost = 0;
        switch (status){
            case "Pending":
            {
                this.status=Status.Pending;
                break;
            }
            case "Waiting":
            {
                this.status=Status.Waiting;
                break;
            }

        }
    }

    public void setItems(List<DataAccessLayer.Suppliers.DTO.OrderLineDTO> lines) {
        this.orderLines = lines;
    }

    public int getId() {
        return id;
    }

    public int getBranchId() {
        return branchId;
    }

    public List<Integer> getOrderDays() { return orderDays; }

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

    public List<DataAccessLayer.Suppliers.DTO.OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
