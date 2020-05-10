import java.util.ArrayList;
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> salasAnteriores;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room entradaCueva, catacumbas, tesoro, puente, lago, exterior, laberinto, rio;

        // create the rooms
        entradaCueva = new Room("en la entrada principal a la cueva");
        catacumbas = new Room("en las catacumbas");
        tesoro = new Room("en la sala del tesoro");
        puente = new Room("en el puente viejo");
        lago = new Room("en la salida hacia al lago");
        exterior = new Room("en la salida hacia al exterior");
        laberinto = new Room("en el laberinto de pasillos");
        rio = new Room("en la salida al r�o");

        // initialise room exits
        entradaCueva.setExits("south", catacumbas);
        entradaCueva.setExits("east", puente);
        entradaCueva.setExits("west", exterior);
        entradaCueva.setExits("northWest", laberinto);

        catacumbas.setExits("north", entradaCueva);
        catacumbas.setExits("west", tesoro);
        catacumbas.setExits("northWest", exterior);

        tesoro.setExits("east", catacumbas);

        puente.setExits("north", lago);
        puente.setExits("west", entradaCueva);
        puente.setExits("abajo", rio);

        lago.setExits("south", puente);

        exterior.setExits("southEast", catacumbas);
        exterior.setExits("east", entradaCueva);

        laberinto.setExits("northEast", entradaCueva);

        rio.setExits("north", puente);

        // initialise room items
        laberinto.addItem("manzana", "Manzana dorada", 180, true);
        tesoro.addItem("cofre", "Cofre del tesoro lleno de monedas", 1500, false);
        catacumbas.addItem("espada", "Espada encantada",1300, true);
        catacumbas.addItem("pocion", "Poci�n m�gica", 60, true);
        puente.addItem("pu�al", "Pu�al encantado", 90, true);
        rio.addItem("linterna", "linterna con alta durabilidad", 200,false);

        return entradaCueva;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();

            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")) {
            player.eat();
        }
        else if (commandWord.equals("back")) { 
            player.back();
        }
         else if (commandWord.equals("take")) {
            player.take(command);
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander.");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are: ");
        System.out.println(parser.getCommandList());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }
}
