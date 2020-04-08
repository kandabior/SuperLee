import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;

public class Register {

    private Printer printer;
    private Inventory inventory;

    public Register(){
        inventory = Inventory.getInventory();
        printer = Printer.getPrinter(inventory);
    }

    //Inventory
    public boolean addProduct(int id, int amount, String name, int costPrice, int salePrice, LocalDate expDate, List<String> category, String manufacturer, int minAmount, String place){
        return inventory.addProduct(id, amount,name,costPrice,salePrice,expDate,category, manufacturer, minAmount, place);
    }
    public boolean removeProduct(int id, int amount){
        return inventory.removeProduct(id,amount);
    }
    public boolean setSalePriceById(int id, int price){
        return inventory.setSalePrice(id,price);
    }
    public boolean setPriceByCategory(List<String> category,int price){
        return inventory.setPriceByCategory(category, price);
    }
    public boolean setCategory(int id, List<String> category){
        return inventory.setCategory(id,category);
    }

    //Reports
    // @TODO - OR you need to implement in the Printer and Reporter (:
    // @TODO : makeBuyingReport() , makeCategoryReport(), makeExpiredReport()

    public boolean NeedToBuyReport(){
        List<Pair<Integer,Integer>> toBuy = inventory.NeedToBuyProducts();
        return printer.makeBuyingReport(toBuy);
    }
    public boolean CategoryReport(List<String> category){
        return printer.makeCategoryReport(category);
    }
    public boolean ExpiredReport(){
        List<Pair<Integer,Integer>> expiredProducts = inventory.ExpiredProducts();
        return printer.makeExpiredReport(expiredProducts);
    }


}
