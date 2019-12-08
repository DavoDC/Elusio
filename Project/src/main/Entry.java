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
     *
     * @param args
     */
    public static void main(String[] args) {
        try {

            // Argument info string
            String argInfo = "Argument Required: 'IDE' or 'Desktop'";

            // If there is not exactly one argument 
            if (args.length != 1) {

                // Throw error
                throw new IllegalArgumentException(argInfo);

            } else {

                // Determine whether the program is 
                // being run in the IDE or the Exe
                Globals.inIDE = args[0].contains("IDE");
                Globals.inExe = args[0].contains("Exe");

                // If the program is not being run in either
                if (!(Globals.inIDE || Globals.inExe)) {

                    // Throw error
                    throw new IllegalArgumentException(argInfo);
                }
            }

            // If being run in IDE
            if (Globals.inIDE) {

                // Load OpenAL DLL to prevent IDE giving 
                // related error and prevent it being muted
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
            Globals.agc.setVSync(true);
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
