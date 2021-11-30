package pos.machine;
import java.util.*;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return null;
    }
    private int calculateTotalPrice(List<ReceiptItem> receiptItems){
        int totalPrice = 0;
        for (ReceiptItem receiptItem : receiptItems){
            totalPrice = totalPrice + receiptItem.getSubTotal();
        }
        return totalPrice;
    }

    private List<ReceiptItem> calculateReceiptItems(List<ItemInfo> itemsWithDetail) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (ItemInfo iteminfo : itemsWithDetail){
            boolean done = false;
            for (int i = 0; i < receiptItems.size() && !done; i++){
                if (receiptItems.get(i).getName().equals(iteminfo.getName())){
                    receiptItems.get(i).setQuantity(receiptItems.get(i).getQuantity() + 1);
                    receiptItems.get(i).setSubTotal(receiptItems.get(i).getSubTotal() + receiptItems.get(i).getUnitPrice());
                    done = true;
                }
                if (i == receiptItems.size() - 1){
                    receiptItems.add(new ReceiptItem(iteminfo.getName(), 1, iteminfo.getPrice(), iteminfo.getPrice()));
                }
            }
        }
        return receiptItems;
    }

}
