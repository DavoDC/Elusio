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
        System.out.println("hi");
        try
        {
            // Create AGC
            Globals.agc = new AppGameContainer(new Entry());

            // Retrieve and store local screen info
            Globals.screenW = Globals.agc.getScreenWidth();
            Globals.screenH = Globals.agc.getScreenHeight();

            // Adjust AGC
            Globals.agc.setDisplayMode(Globals.screenW, Globals.screenH, true);
            Globals.agc.setTargetFrameRate(60);
            Globals.agc.setAlwaysRender(true);
            Globals.agc.setSmoothDeltas(true);
            Globals.agc.setShowFPS(false);

            // Prevents OpenAL DLL loading error when running in IDE, 
            // thereby enabling sound/music for IDE (64 bit only)
            System.loadLibrary("OpenAL64");
            
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
