package LogicLayer;

import DTO.OrderDTO;
import DTO.OrderLineDTO;
import DataAccessLayer.OrderMapper;
import InterfaceLayer.SupplierController;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

enum Status{Pending,Complete}

public class Order {
    private int id;
    private int branchId;
    private String suppName;
    private int suppId;
    private String address ;
    private LocalDate orderDate;
    private String phoneNumber;
    List<OrderLine> orderLines;
    double totalCost;
    Status status;
    private OrderMapper orderMapper;

    public Order()
    {
        this.orderMapper=new OrderMapper();
    }



    public Order(int branchId , int id, int supplierId , String suppName, String phoneNumber , String address , LocalDate date ) {
        this.branchId = branchId;
        this.id = id;
        this.suppName = suppName;
        this.suppId = supplierId;
        this.address = address;
        this.orderDate = date;
        this.phoneNumber = phoneNumber;
        totalCost = 0;
        status = Status.Pending;
    }

    public String getStatus(int orderId) {
        return this.orderMapper.getStatus(orderId);
        /*if (this.status == Status.Complete)
            return "COMPLETE";
        return "PENDING";*/
    }

    public void setItems(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    public int getId() {
        return id;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getSupplierId() {
        return this.suppId;
    }

    public String getSupplierName() {
        return this.suppName;
    }

    public String getSupplierAdd() {
        return this.address;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public String getSupplierPhone() {
        return this.phoneNumber;
    }

    public double getTotalOrderMoney(int orderId) {
        return this.orderMapper.getTotalOrderMoney(orderId);


      /*
        double totalAmount = 0;
        for (int i = 0; i < orderLines.size(); i++) {
            totalAmount += (orderLines.get(i).getFinalCost());
        }
        return totalAmount;*/
    }

    public int getSize() {
       return this.orderMapper.getSize();

    }

    public List<List<Object>> getOrdersLineByOrderID(int id) {
        return this.orderMapper.getOrdersLineByOrderID( id);
    }

    public List<Object> getSupplierDetails(int id) {
        return this.orderMapper.getSupplierDetails(id);
    }

    public boolean insertOrder(OrderDTO o) {
        if (this.orderMapper.insertOrder(o)) {
            for (OrderLineDTO ot : o.getOrderLines()) {
                this.orderMapper.insertOrderLines(ot);
            }
            return true;
        }
        return false;
    }

    public int getOrderIdCounter() {
        return this.orderMapper.getOrderIdCounter();
    }

    public boolean checkIfOrderExists(int orderId) {
        return this.orderMapper.checkIfOrderExists(orderId);
    }

    public boolean updateOrderStatus(int orderId) {
        return this.orderMapper.updateOrderStatus(orderId);
    }
}
