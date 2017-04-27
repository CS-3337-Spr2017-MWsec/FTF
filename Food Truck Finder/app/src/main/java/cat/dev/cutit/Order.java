package cat.dev.cutit;

/**
 * Created by karin on 4/26/2017.
 */

public class Order {
    public String orderId;
    public String customerFirstName;
    public String customerId;
    public String customerLastName;
    public Item items[] = new Item[20];
    public double orderTotal;
    public int quantityTotal;
    public String vendorId;
    public String vendorName;

    public Order(String orderId,String customerFirstName, String customerId, String customerLastName,
                 Item items[], double orderTotal, int quantityTotal, String vendorId, String vendorName){
        this.orderId = orderId;
        this.customerFirstName = customerFirstName;
        this.customerId = customerId;
        this.customerLastName = customerLastName;
        this.items = items;
        this.orderTotal = orderTotal;
        this.quantityTotal = quantityTotal;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }


}
