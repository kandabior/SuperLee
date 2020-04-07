import java.time.LocalDate;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private int costPrice;
    private int salePrice;
    private LocalDate expDate;
    private List<String> category;
    private String manufacturer;
    private int minAmount;
    private String place;

    public Product(int id, String name, int costPrice,int salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.expDate = expDate;
        this.category = category;
        this.manufacturer = manufacturer;
        this.minAmount = minAmount;
        this.place = place;
    }

    public LocalDate getExpDate() {
        return expDate;
    }
    public int getCostPrice() {
        return costPrice;
    }
    public int getId() {
        return id;
    }
    public int getMinAmount() {
        return minAmount;
    }
    public int getSalePrice() {
        return salePrice;
    }
    public String getName() {
        return name;
    }
    public List<String> getCategory() {
        return category;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getPlace() {
        return place;
    }
    public boolean isExpirated(){
        return this.expDate.isBefore(LocalDate.now());
    }
    public void setSalePrice(int price){
        this.salePrice = price;
    }
}
