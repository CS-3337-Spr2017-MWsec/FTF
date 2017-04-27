package cat.dev.cutit;

/**
 * Created by karin on 4/26/2017.
 */

public class Item {

    public String name;
    public String description;
    public double price;
    public int quantity;

    public Item(String name, String description, double price, int quantity){
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
