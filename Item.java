
public class Item
{
    private String itemDescription;
    private int itemWeight;

    public Item(String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }
    
    public int getItemWeight() {
        return itemWeight;
    }
    
    public String getItem(){
        String cadena = "";
        if(itemDescription != "" && itemWeight != 0){
            cadena = "Encontrado el objeto: " + itemDescription + ", de peso: " + itemWeight + ", en esta sala!";
        }
        return cadena;
    }
}
