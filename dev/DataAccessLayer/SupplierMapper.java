package DataAccessLayer;

import DTO.SupplierDTO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Set;

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

    public static boolean addSupplier(SupplierDTO supp) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:dev\\EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?,?,?);");
            st.setInt(1, supp.getId());
            st.setString(2, supp.getName());
            st.setString(3, supp.getPhoneNumber());
            st.setInt(4, supp.getBankAccount());
            st.setString(5, supp.getPayment());
            st.setString(6, supp.getSupplySchedule());
            st.setString(7, supp.getSupplyLocation());
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

    public static boolean deleteSuppplier(int id) {

        //TODO OOOOOO
        return true;
    }


    public boolean addBillOfQuantities(int supplierId, Map<Integer, Pair<Integer, Double>> bill) {
     try {
         Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
         conn.setAutoCommit(false);
         PreparedStatement st = conn.prepareStatement("INSERT INTO BillsOfQuantities VALUES (?);");
         st.setInt(1, supplierId);
         int rowNum = st.executeUpdate();
         st.close();
         if (rowNum != 0) {
             conn.commit();
         } else {
             conn.rollback();
             conn.close();
             return false;
         }
         Set<Integer> ids = bill.keySet();
         for (int i = 0; i < bill.size(); i++) {
             st = conn.prepareStatement("INSERT INTO ItemsInBills VALUES (?,?,?,?);");
             st.setInt(1, supplierId);
             st.setInt(2, ids.iterator().next()); //no fucking idea if works
             st.setInt(3, bill.get(i).getKey());
             st.setDouble(4, bill.get(i).getValue());
             rowNum = st.executeUpdate();
             st.close();
             if (rowNum != 0) {
                 conn.commit();
             } else {
                 conn.rollback();
                 conn.close();
                 return false;
             }
         }
         return true;
     } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         return false;
     }
 }

    public boolean addItemToAgreement(Integer suppId, Integer itemId, Double cost){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("INSERT INTO ItemsInAgreements VALUES (?,?,?);");
            st.setInt(1, suppId);
            st.setInt(2, itemId);
            st.setDouble(3, cost);
            int rowNum = st.executeUpdate();
            st.close();
            if (rowNum != 0) {
                conn.commit();
                conn.close();
            } else {
                conn.rollback();
                conn.close();
                return false;
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
/*
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

    public void addItemToSupplier(int suppId, int itemId) { //TODO already have addItemToAgreement - must have item price!
        this.getSuppById(suppId).addItemsToSupplier(itemId);
    }

    public int getItemsListSize(int suppId) {
        return this.getSuppById(suppId).getItemsListSize();
    }
*/
    public boolean setItemPrice(int suppId, int itemId, double newPrice) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("UPDATE ItemsInAgreements SET price = ? WHERE agreementId = ?" +
                    "AND suppId = (?);");
            st.setDouble(1, newPrice);
            st.setInt(2, suppId);
            st.setInt(3, itemId);
            int rowNum = st.executeUpdate();
            st.close();
            if (rowNum != 0) {
                conn.commit();
                conn.close();
            } else {
                conn.rollback();
                conn.close();
                return false;
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;

        }
    }

    public boolean validateItemId(int suppId, int itemId) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:EOEDdatabase.db");
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement("SELECT * FROM ItemsInAgreements WHERE aggreementId = ? AND itemId = ?;");
            st.setInt(1, suppId);
            st.setInt(2, itemId);
            int rowNum = st.executeUpdate();
            st.close();
            if (rowNum != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
/*
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

    public List<Object> getSuppDetails(int bestSuppForItem) { //TODO create dto
        return getSuppById(bestSuppForItem).getSuppDetails();
    }
}


 */

}
