import java.util.Stack;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{   
    private Room currentRoom;
    private Stack<Room> salasAnteriores;
    private ArrayList<Item> items;
    private int pesoActual;
    private int pesoMax;

    public Player(Room room)
    {
        currentRoom = room;
        salasAnteriores = new Stack<>();
        items = new ArrayList<>();
        pesoActual = 0;
        pesoMax = 600;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            Room salaAnterior = currentRoom;
            salasAnteriores.push(salaAnterior);
            currentRoom = nextRoom;
            look();
        }
    }

    public void back() {
        if (!salasAnteriores.empty()) {
            currentRoom = salasAnteriores.pop();
        }
        else {
            System.out.println("No se puede volver más atras.");
        }
        look();
    }

    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("You have eaten now and you are not hungry any more.");
    }

    public void take(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("¿Qué objeto deseas coger?");        
        }
        else {        
            String item = command.getSecondWord();
            Item cogerItem = currentRoom.cogerItem(item);
            if (cogerItem == null) {
                System.out.println("Ese objeto no existe en esta habitación.");
            }
            else {
                if (cogerItem.sePuedeCoger()){
                    if (pesoActual + cogerItem.getItemWeight() < pesoMax) {
                        items.add(cogerItem);
                        pesoActual += cogerItem.getItemWeight();
                        System.out.println("Has recogido el objeto: " + cogerItem.getId()
                            + " ,de peso: " + cogerItem.getItemWeight() + ".");
                    }
                    else {
                        System.out.println("Has alcanzado el máximo peso total de objetos!");
                        items.add(cogerItem);
                        drop(command);
                        pesoActual += cogerItem.getItemWeight();
                    }
                }
                else {
                    System.out.println("Este objeto no se puede recoger.");
                    items.add(cogerItem);
                    drop(command);
                }
            }            
        }
    }

    public void drop (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Indica el objeto que deseas soltar.");
        }
        else {
            String item = command.getSecondWord();
            Item soltarItem = null;
            for (Item itemASoltar : items) {
                if (itemASoltar.getId().equals(item)) {
                    soltarItem = itemASoltar;                    
                }
            }
            items.remove(soltarItem);
            if (soltarItem == null) {
                System.out.println("No existe ese objeto en tu inventario.");
            }
            else {
                currentRoom.soltarItem(soltarItem);
                pesoActual -= soltarItem.getItemWeight();
                System.out.println("Soltado el objeto: " + 
                    soltarItem.getItemDescription() + ", de peso: " + soltarItem.getItemWeight() + ".");
            }
        }
    }

    public void items()
    {
        if (!items.isEmpty()) {
            int pesoTotal = 0;
            for (Item item : items) {
                System.out.println(item.getItemDescription());
                pesoTotal += item.getItemWeight();
            }
            System.out.println("Peso total de los objetos: " + pesoTotal + ".");
        }
        else {
            System.out.println("Todavía no tienes objetos.");
        }
    }

    public void drink(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drink what?");
        }
        else {
            if (command.getSecondWord().equals("pocion")) {
                if (!items.isEmpty()) {
                    for(Item item : items) {
                        if(item.getId().equals(command.getSecondWord())) {
                            pesoMax = 1200;
                            System.out.println("Has consumido la poción mágica y ahora puedes llevar el doble de peso!");
                        }
                    }
                }
                else {
                    System.out.println("Todavía no tienes la poció en tu inventario.");
                }
            }
            else {
                System.out.println("No puedes beber este objeto!");
            }
        }
    }
}
