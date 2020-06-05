package src.BusinessLayer.Suppliers;

public class OrderLine {

    static int orderLineCounter = 1;

    private int orderLineId;
    private int orderId;
    private int itemId;
    private String itemName;
    private int itemQuantity;
    private Double itemCost;
    private Double itemDiscount ;
    private Double finalCost;

    public OrderLine(int orderId, int itemId, String itemName, int itemQuantity, Double itemCost, Double itemDiscount, Double finalCost) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemDiscount = itemDiscount;
        this.finalCost = finalCost;
        orderLineId = orderLineCounter;
        orderLineCounter++;
    }

    public int getItemId() {
        return itemId;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public String  geItemName() {
        return this.itemName;
    }

    public int getItemQuantity() {
        return this.itemQuantity;
    }

    public Double getItemCost() {
        return this.itemCost;
    }

    public Double getItemDiscount() {
        return this.itemDiscount;
    }
}
