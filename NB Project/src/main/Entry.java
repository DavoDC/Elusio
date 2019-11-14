package main;

import states.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The entry class from which the game application starts
 *
 * @author David Charkey
 */
public class Entry extends StateBasedGame
{

    /**
     * Creates an entry point object
     */
    public Entry()
    {
        super(Globals.gameTitle);
    }

    /**
     * Entry method - Sets up the game-containing application
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // Print out version info
        System.out.println("\nSlick2DGame V" + Globals.VERSION + " Testing \n");

        // Start up game
        try
        {
            // Prevents OpenAL DLL loading error when running in IDE, 
            // thereby enabling sound/music for IDE (64 bit only)
            // DISABLE WHEN NOT IN IDE
            // System.loadLibrary("OpenAL64");

            // Create AGC
            Globals.agc = new AppGameContainer(new Entry());

            // Retrieve and store local screen info
            Globals.screenW = Globals.agc.getScreenWidth();
            Globals.screenH = Globals.agc.getScreenHeight();

            // Adjust AGC
            // NOTE = THINGS ARE MISSING FROM SCREEN, NEED TO ADD DEPENDENCY ON AGC dimension
            int border = 100;
            int winW = Globals.screenW - border;
            int winH = Globals.screenH - border;
            Globals.agc.setDisplayMode(winW, winH, false);
            Globals.agc.setTargetFrameRate(60);
            Globals.agc.setAlwaysRender(true);
            Globals.agc.setSmoothDeltas(true);
            Globals.agc.setShowFPS(false);

            // Start AGC
            Globals.agc.start();

        }
        catch (SlickException e)
        {
            System.err.println("Main method in 'Entry' had an error");
        }
    }

    /**
     * Add the first state to the game
     *
     * @param gc
     * @throws SlickException
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        addState(new Loading());
    }

}
