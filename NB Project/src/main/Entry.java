package main;

import states.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The entry class from which the game application starts
 *
 * @author David 
 */
public class Entry extends StateBasedGame {

    /**
     * Creates an entry point object
     */
    public Entry() {
        super(Globals.gameTitle);
    }

    /**
     * The entry point for the game program. Sets up the execution environment
     * @param args
     */
    public static void main(String[] args) {
        try {

            // Print out version info
            System.out.println("\n" + Globals.gameTitle + " V" + Globals.VERSION + " Testing \n");

            // If being run in IDE
            if (args.length != 0 && args[0].equals("IDE")) {

                // Load OpenAL DLL to prevent IDE giving error and being muted
                System.loadLibrary("OpenAL64");
            }

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

            // Start AGC
            Globals.agc.start();

        } catch (SlickException e) {
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
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new Loading());
    }

}
