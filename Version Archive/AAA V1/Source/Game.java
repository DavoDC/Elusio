
/**
 *  
 * This class is the main class of the "Alien Aztec Adventure" application. 
 * 
 * This application (AAA) is a text based adventure game. 
 * To play this game, make a Game object and call "Play" on it
 * 
 * 
 * @author David Charkey, Michael Kolling, David J. Barnes
 * 
 */

public class Game
{
    public static final Locations locations = new Locations();
    public static final Player alien = new Player(); 
    public static final AV av = new AV();
    public static final Actions actions = new Actions();
    public static final Events events = new Events();
    public static final GUI gui = new GUI();
    public static final Parser parser = new Parser();
    public static final Game game = new Game();
    public static final Challenge chl = new Challenge();

    /**
     * Create the game and initialise its components
     */
    public Game() 
    {
        av.showSplash();
    }

    public static void main(String[] args)
    {
        game.play();

    }  

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        events.gameStart();  //Print welcome message

        av.startMusic();

        // Enter the main command loop.  Here we repeatedly read commands and execute them until the game is over.

        while (true) 
        {
            Command command = parser.getCommand();
            parser.processCommand(command);

            events.check();
            gui.update(Game.alien.getLocation());
        }
    }

    public static void mgProxy()
    {
        Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        chl.runConqGame();         
                    }

                });

        thread.start();
    }
}

