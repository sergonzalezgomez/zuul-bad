
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
        return "Encontrado el objeto: " + id + " en esta sala!\nDescripción: " + itemDescription + ", de peso: " + itemWeight + ".\n";
    }
    
    public String getAllItems() {
        return "Objeto: " + getItemDescription() + ", de peso: " + getItemWeight() + ".";
    }
}
