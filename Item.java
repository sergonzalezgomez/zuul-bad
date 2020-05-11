
public class Item
{
    private String id;
    private String itemDescription;
    private int itemWeight;
    boolean cogerItem;

    public Item(String id, String itemDescription, int itemWeight, boolean cogerItem)
    {
        this.id = id;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.cogerItem = cogerItem;
    }
    
    public String getId() {
        return id;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }

    public int getItemWeight() {
        return itemWeight;
    }
    
    public boolean sePuedeCoger() {
        return cogerItem;
    }

    public String getItem(){
        return "Encontrado el objeto: " + itemDescription + ", de peso: " + itemWeight + ", en esta sala!";
    }
}
