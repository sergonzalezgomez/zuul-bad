
public class Item
{
    private String id;
    private String itemDescription;
    private int itemWeight;

    public Item(String id, String itemDescription, int itemWeight)
    {
        this.id = id;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
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

    public String getItem(){
        return "Encontrado el objeto: " + itemDescription + ", de peso: " + itemWeight + ", en esta sala!";
    }
}
