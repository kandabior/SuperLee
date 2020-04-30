package LogicLayer;

public class OrderLine {

    private int itemId;
    private String itemName;
    private int itemQuantity;
    private int itemCost;
    private int itemDiscount ;
    private int finalCost;

    public OrderLine(int itemId, String itemName ,int itemQuantity , int  itemCost, int itemDiscount , int finalCost )
    {
        this.itemId=itemId;
        this.itemName= itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemDiscount = itemDiscount;
        this.finalCost=finalCost;
    }

    public int getItemId() {
        return itemId;
    }
}
