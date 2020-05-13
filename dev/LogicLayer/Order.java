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
        // this.items = makeOrderLines(items);
        this.suppName = suppName;
        this.suppId = supplierId;
        this.address = address;
        this.orderDate = date;
        this.phoneNumber = phoneNumber;
        totalCost = 0;
        status = Status.Pending;
    }

    public String getStatus(int orderId) {
        return this.orderMapper.getStatud(orderId);
        /*if (this.status == Status.Complete)
            return "COMPLETE";
        return "PENDING";*/
    }

    public void setItems(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void setStatus() {
        this.status = Status.Complete;
    }

    public int getId() {
        return id;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Supplier getSuppById(int id) {
        return SupplierController.getSupplierController().getSuppById(id);
    }

    public List<List<Object>> getOrdersLineByOrderIndex() {
        List<List<Object>> list =new LinkedList<>();
        for(int i =0 ; i<orderLines.size();i++)
        {
            List<Object> l = new LinkedList<>();
            l.add(orderLines.get(i).getItemId());
            l.add(orderLines.get(i).geItemName());
            l.add(orderLines.get(i).getItemQuantity());
            l.add(orderLines.get(i).getItemCost());
            l.add(orderLines.get(i).getItemDiscount());
            l.add(orderLines.get(i).getFinalCost());
            list.add(l);
        }
        return list;
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

/*    public List<OrderLine> makeOrderLines(List<Pair<Integer, Integer>> items) {
        List<OrderLine> list = new LinkedList<>();
        for (int i = 0; i < items.size(); i++) {
            OrderLine a = new OrderLine(items.get(i).getKey(), items.get(i).getValue());
            list.add(a);
        }
        return list;
    }*/

/*    public boolean makeOrder() {
        return supplier.makeOrder(items);
    }

    public List<Pair<Integer, Integer>> getItemsInOrderById() {
        List<Pair<Integer, Integer>> list =new LinkedList<>();
        for(int i =0 ; i<items.size();i++)
        {
            list.add(new Pair(items.get(i).getItemId(),items.get(i).getQuantity()));
        }
        return list;
    }

    public int getSupplierIdOfOrder() {
        return this.supplier.getId();
    }
*/
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

    public List<Object> getSupplierDeatails(int id) {
        return this.orderMapper.getSupplierDeatails(id);


    }

    public boolean insertOrder(OrderDTO o) {
       //this.orderMapper.insert(o.getId(),o.getBranchId(),o.getSuppName(),o.getSuppId(),o.getOrderDate(),o.getAddress(),totalCost,o.getPhoneNumber(),o.getStatus());
        if(this.orderMapper.insertOrder(o))
        {
            for (OrderLineDTO ot : o.getOrderLines())
            {
                this.orderMapper.insertOrderLines(ot);
            }
            return true;

        }
        return false;
    }
}
