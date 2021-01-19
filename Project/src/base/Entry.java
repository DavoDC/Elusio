package base;

import states.special.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.LogSystem;

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

            // Argument info strings
            String dev = "IDE";
            String user = "Exe";
            String argInfo = "ArgRequired: '" + dev + "' or '" + user + "'";

            // If there is not exactly one argument 
            if (args.length != 1) {

                // Throw error
                throw new IllegalArgumentException(argInfo);

            } else {

                // Determine whether the program is 
                // being run in the IDE or the Exe
                Globals.inIDE = args[0].contains(dev);
                Globals.inExe = args[0].contains(user);

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

                // Reduce error message verbosity
                reduceErrorMsgVerb();
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

            // If in IDE
            if (Globals.inIDE) {

                // Use windowed mode
                Globals.agc.setFullscreen(false);
            }

            // Start AGC
            Globals.agc.start();

            // Make space
            System.out.println("\n\n");

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

    /**
     * Create customized error logging system
     */
    private static void reduceErrorMsgVerb() {

        Log.setLogSystem(new LogSystem() {
            @Override
            public void error(String message, Throwable e) {
                System.err.println("Err1: " + message);
                System.err.println("Throwable: " + e.getClass());
                e.printStackTrace();
            }

            @Override
            public void error(Throwable e) {
                System.err.println("Err2: " + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void error(String message) {
                System.err.println("Err3: " + message);
            }

            @Override
            public void warn(String message) {
                System.err.println("Warn1: " + message);
            }

            @Override
            public void warn(String message, Throwable e) {

                // If not irrelevant error
                if (!message.
                        contains("PNGImageData failed to read the data")) {
                    System.err.println("Warn2: " + message);
                }
            }

            @Override
            public void info(String message) {
                System.err.println("Info: " + message);
            }

            @Override
            public void debug(String message) {
                System.err.println("Debug: " + message);
            }
        });
        Log.setVerbose(false);
    }

}
