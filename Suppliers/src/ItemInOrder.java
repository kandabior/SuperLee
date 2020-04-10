public class ItemInOrder {

    private int itemId;
    private int quantity;

    public ItemInOrder(int itemId,int quantity)
    {
        this.itemId=itemId;
        this.quantity=quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
