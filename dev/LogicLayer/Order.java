package LogicLayer;

import InterfaceLayer.SupplierController;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;

enum Status{Pending,Complete}

public class Order {
    private int id;
    private String suppName;
    private int suppId;
    private String address ;
    private Date orderDate;
    private String phoneNumber;
    List<OrderLine> items;
    double totalCost;
    Status status;

    public Order(int id, List<Pair<Integer, Integer>> items, int supplierId , String suppName, String address ,Date date , String phoneNumber) {
        this.id = id;
       // this.items = makeOrderLines(items);
        this.suppName=suppName;
        this.suppId=supplierId;
        this.address= address;
        this.orderDate=date;
        this.phoneNumber = phoneNumber;
        totalCost=0;
        status=Status.Pending;
    }

    public String getStatus() {
        if(this.status==Status.Complete)
            return "COMPLETE";
        return "PENDING";
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

    public double getTotalOrderMoney() {
        double totalAmount=0;
        for (int i =0;i<items.size();i++)
        {
            totalAmount+= supplier.getOrderCost(items.get(i).getItemId(),items.get(i).getQuantity());
        }
        return totalAmount;
    }*/
}
