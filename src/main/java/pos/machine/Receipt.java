package pos.machine;
import java.util.*;

public class Receipt {
    private List<ReceiptItem> itemDetail;
    private int totalPrice;

    public Receipt(List<ReceiptItem> itemDetail, int totalPrice) {
        this.itemDetail = itemDetail;
        this.totalPrice = totalPrice;
    }

    public List<ReceiptItem> getItemDetail() {
        return itemDetail;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
