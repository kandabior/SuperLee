package src.DataAccessLayer.Inventory.DTO;

import java.util.List;

public class ReportLineDTO {

    private int prodId;
    private String prodName;
    private int amount;
    private List<String> optional;

    public ReportLineDTO(int prodId, String prodName, int amount, List<String> optional){
        this.prodId=prodId;
        this.prodName=prodName;
        this.amount=amount;
        this.optional= optional;
    }

    public int getAmount() {
        return amount;
    }

    public int getProdId() {
        return prodId;
    }

    public List<String> getOptional() {
        return optional;
    }

    public String getProdName() {
        return prodName;
    }

}
