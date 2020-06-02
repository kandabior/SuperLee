package DataAccessLayer.Suppliers.DTO;

public class OrderLineDTO {
    private int orderId;
    private int itemId;
    private String itemName;
    private int itemQuantity;
    private Double itemCost;
    private Double itemDiscount ;
    private Double finalCost;

    public OrderLineDTO(int orderId, int itemId, String itemName, int itemQuantity, Double itemCost, Double itemDiscount, Double finalCost) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemDiscount = itemDiscount;
        this.finalCost = finalCost;
        //orderLineId = orderLineCounter;
        //orderLineCounter++;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public Double getItemDiscount() {
        return itemDiscount;
    }

    public Double getFinalCost() {
        return finalCost;
    }
}
