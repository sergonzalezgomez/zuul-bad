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

    public Player(Room room)
    {
        currentRoom = room;
        salasAnteriores = new Stack<>();
        items = new ArrayList<>();
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
                    items.add(cogerItem);
                    System.out.println("Has recogido el objeto: " + cogerItem.getId()
                        + " ,de peso: " + cogerItem.getItemWeight() + ".");
                }
                else {
                    System.out.println("Este objeto no se puede recoger.");
                    items.add(cogerItem);
                    drop(command);
                }
            }            
        }
    }
    
    private void drop (Command command) {
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
            }
        }
    }
}
