package DataAccessLayer;

import java.sql.*;

public class SupplierMapper {

    private static Connection conn;

    /*
    public static void initializeSupplierMapper() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
        } catch (Exception e) {
            System.out.println("DB connection problem.");
        }
        System.out.println("DB opened successfully.");
    }*/

    public static boolean addSupplier(int id, String name, String phoneNum, int bankAccount, String payment, String suppSchedule, String suppLocation) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?,?,?);");
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, phoneNum);
            st.setInt(4, bankAccount);
            st.setString(5, payment);
            st.setString(6, suppSchedule);
            st.setString(7, suppLocation);
            int rowNum = st.executeUpdate();
            st.close();
            if (rowNum != 0) {
                conn.commit();
                conn.close();
                return true;
            } else {
                conn.rollback();
                conn.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return false;
    }

/*
 public void addBillOfQuantities(int supplierId,Map<Integer, Pair<Integer, Double>> bill) {
        if (getSuppById(supplierId) != null) {
            getSuppById(supplierId).getAgreement().addBillOfQuantities(bill);
        }
    }

    public void addItemToAgreement(Integer supp_id ,Integer item_id,Double cost){
        getSuppById(supp_id).addItemToAgreement(item_id,cost);
    }

    public void updateBillOfQuantities(int supplierId, Integer itemId, Pair<Integer, Double> quantity_disc) {
       if(getSuppById(supplierId)!=null)
       {
           getSuppById(supplierId).updateBillOfQuantities(itemId,quantity_disc);
       }
    }

    public void deleteFromBillOfQuantities(int suppId, Integer itemId) {
        if (getSuppById(suppId) != null) {
            getSuppById(suppId).deleteFromBillOfQuantities(itemId);
        }
    }

    public void deleteBillOfQuantities(int suppId) {
        if (getSuppById(suppId) != null) {
            getSuppById(suppId).deleteBillOfQuantities();
        }
    }

    public int getBillSize(int suppId) { return getSuppById(suppId).getBillSize(); }

    public LinkedHashMap<Integer, Double> showSuppItems(int suppId){
        Supplier supplier = getSuppById(suppId);
        return supplier.getAgreement().getTerms();
    }

    public double getPriceOfItem(int suppId, int index) {
        return getSuppById(suppId).getPriceOfItem(index);
    }

    public boolean checkBillOfQuantity(int suppId) {
        return getSuppById(suppId).checkBillOfQuantity();
    }

    public void addItemToBillOfQuantities(int suppId, int itemId, int itemQuantity, Double itemDiscount) {
        getSuppById(suppId).addItemToBillOfQuantities(itemId,itemQuantity,itemDiscount);
    }

    public Map<Integer, Pair<Integer, Double>> getBillOfQuantities(int suppId) {
        return getSuppById(suppId).getBillOfQuantities();
    }

    public String getItemName( Integer itemId) {
        return Items.getName(itemId);
    }

    public String getItemNameByIndex(int suppId, int i) {
        return Items.getName(getSuppById(suppId).getItemIdByIndex(i));
    }


    public int getItemIdByIndex(int suppId, int i) {
        return getSuppById(suppId).getItemIdByIndex(i);
    }

    public void addItemToSupplier(int suppId, int itemId) {
        this.getSuppById(suppId).addItemsToSupplier(itemId);
    }

    public int getItemsListSize(int suppId) {
        return this.getSuppById(suppId).getItemsListSize();
    }

    public void setItemPrice(int suppId, int itemId, double newPrice) {
        getSuppById(suppId).setItemPrice(itemId, newPrice);
    }

    public boolean validateItemId(int suppId, int itemId) {
        return getSuppById(suppId).validateItemId(itemId);
    }

    public int bestSuppForItem(Integer itemId, Integer quantity) {
        Double min=100000000.0;
        int bestSuppId=-1;
        for(int i=0 ; i<suppliers.size();i++)
        {
             Double temp =suppliers.get(i).getPriceOfAmountOfItem(itemId,quantity);
             if(temp<min) {
                 min = temp;
                 bestSuppId=i;
             }
        }

        return bestSuppId+1;
    }

    public Double getPriceOfAmountOfItem(int bestSuppForItem,Integer itemId, Integer amount) {
        return getSuppById(bestSuppForItem).getPriceOfAmountOfItem(itemId,amount);
    }

    public boolean checkIfItemExist(int itemId) {
        return (Items.getName(itemId)!=null);
    }

    public Double getPriceOfAmountOfItemBeforeDiscount(int suppId , int itemId , int amount) {
        return getSuppById(suppId).getPriceOfAmountOfItemBeforeDiscount(itemId, amount);
    }

    public Double getDiscountOfItem(int bestSuppForItem, int itemId , int amount) {
        return getSuppById(bestSuppForItem).getDiscountOfItem(itemId, amount);
    }

    public List<Object> getSuppDetails(int bestSuppForItem) {
        return getSuppById(bestSuppForItem).getSuppDetails();
    }
}


 */

}
