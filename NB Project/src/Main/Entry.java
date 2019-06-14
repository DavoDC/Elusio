package main;

import states.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The entry point from which the game application starts
 *
 * @author David C
 */
public class Entry extends StateBasedGame
{

    /**
     * Creates an entry point
     */
    public Entry()
    {
        super(Globals.gameTitle);
    }

    /**
     * Sets up the game-containing application
     * @param args 
     */
    public static void main(String[] args)
    {
        try
        {
            // Create AGC
            Globals.agc = new AppGameContainer(new Entry());
            
            // Retrieve and store system information
            Globals.screenW = Globals.agc.getScreenWidth();
            Globals.screenH = Globals.agc.getScreenHeight();

            // Adjust AGC
            Globals.agc.setDisplayMode(Globals.screenW, Globals.screenH, true);
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
     * @param gc
     * @throws SlickException 
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        addState(new Loading());
    }

}
