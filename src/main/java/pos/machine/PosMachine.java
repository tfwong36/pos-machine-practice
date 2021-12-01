package pos.machine;
import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> itemsWithDetail = convertToItemInfos(barcodes);
        Receipt receipt = calculateReceipt(itemsWithDetail);
        return renderReceipt(receipt);
    }

    private String renderReceipt(Receipt receipt) {
        String printedReceipt = spliceItemsDetails(receipt);
        printedReceipt += spliceReceipt(receipt);
        return printedReceipt;
    }

    private String spliceItemsDetails(Receipt receipt) {
        String itemsDetail = "***<store earning no money>Receipt***\n";
        for (ReceiptItem receiptItem : receipt.getItemDetail()) {
            itemsDetail += "Name: " + receiptItem.getName() + ", " + "Quantity: " + receiptItem.getQuantity() + ", " + "Unit price: "
                    + receiptItem.getUnitPrice() + " (yuan), Subtotal: " + receiptItem.getSubTotal() + " (yuan)\n";
        }
        return itemsDetail;
    }

    private String spliceReceipt(Receipt receipt) {
        return "----------------------\n" +
                "Total: " + receipt.getTotalPrice() + " (yuan)" +
                "\n**********************";
    }

    private List<ItemInfo> convertToItemInfos(List<String> barcodes){
        List<ItemInfo> database = ItemDataLoader.loadAllItemInfos();
        List<ItemInfo> itemsWithDetail = new ArrayList<>();
        for (String barcode : barcodes){
            for (int i = 0; i < database.size(); i++){
                if (barcode.equals(database.get(i).getBarcode())) {
                    itemsWithDetail.add(database.get(i));
                }
            }
        }
        return itemsWithDetail;
    }

    private Receipt calculateReceipt(List<ItemInfo> itemsWithDetail){
        List<ReceiptItem> receiptItems = calculateReceiptItems(itemsWithDetail);
        int totalPrice = calculateTotalPrice(receiptItems);
        return new Receipt(receiptItems, totalPrice);
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
        List<ItemInfo> itemsWithDetail_distinct = itemsWithDetail.stream().distinct().collect(Collectors.toList());
        for (ItemInfo iteminfo : itemsWithDetail_distinct) {
            int quantity = Collections.frequency(itemsWithDetail, iteminfo);
            receiptItems.add(new ReceiptItem(iteminfo.getName(), quantity, iteminfo.getPrice(), iteminfo.getPrice() * quantity));
        }
        return receiptItems;
    }
}
