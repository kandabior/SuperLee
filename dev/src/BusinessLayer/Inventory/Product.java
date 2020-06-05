package src.BusinessLayer.Inventory;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double costPrice;
    private double salePrice;
    private LocalDate expDate;
    private List<String> category;
    private String manufacturer;
    private int minAmount;
    private String place;
    private List<Double> lastCostPrice;
    private List<Double> lastSalePrice;

    public Product(int id, String name, Double costPrice,Double salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.expDate = expDate;
        this.category = category;
        this.manufacturer = manufacturer;
        this.minAmount = minAmount;
        this.place = place;
        lastCostPrice = new LinkedList<>();
        lastSalePrice = new LinkedList<>();
        lastCostPrice.add(costPrice);
        lastSalePrice.add(salePrice);
    }

    public LocalDate getExpDate() {
        return expDate;
    }
    public Double getCostPrice() {
        return costPrice;
    }
    public int getId() {
        return id;
    }
    public int getMinAmount() {
        return minAmount;
    }
    public Double getSalePrice() {
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
    public boolean isExpired(){
        return this.expDate.isBefore(LocalDate.now());
    }
    public void setSalePrice(Double price){
        this.salePrice = price;
        this.lastSalePrice.add(price);
    }
    public void setCostPrice(Double price){
        this.costPrice = price;
        this.lastCostPrice.add(price);
    }
    public void setCategory(List<String> category) {
        this.category = category;
    }
    public List<Double> getLastCostPrice() {
        return lastCostPrice;
    }
    public List<Double> getLastSalePrice() {
        return lastSalePrice;
    }
}
