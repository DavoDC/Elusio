package Main;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;

/**
 * Global constants
 *
 * @author David
 */
public class Globals
{

    // Game framework
    public static AppGameContainer agc = null;
    public static StateBasedGame SBG = null;
    
    // A directory of game states
    public static HashMap<String, Integer> STATES = new HashMap<>();
    
    // Game font
    public static TrueTypeFont gameFont = null;

    // Transitions
    // Only work when re-created each time
    private static Transition leave;
    public static Transition getLeave()
    {
        leave = new FadeOutTransition(Color.black, 639);
        return leave;
    }
    private static Transition enter;
    public static Transition getEnter()
    {
        enter = new FadeInTransition(Color.black, 639);
        return enter;
    }

    // Global Strings
    public static final String headerFont = "Gamefont-Plain-60";
    public static final String gameTitle = "Escape from Somnium-9";
    public static final String SFXres = "res/audio/effects/"; // SFX location
    public static final String[] HINTS = new String[4]; // Hints as strings
    
    
    // Global Numbers
    public static int screenW = 0; // Screen width
    public static int screenH = 0; // Screen height
    public static int playerLives = 5; // Remaining lives of player
    public static final int BG_COUNT = 17; // Number of backgrounds to load (max 17)

    
    // Global Images
    public static Image cursor; // Image of cursor
    public static Image alertMark; // Image of exclamation mark
    public static ArrayList<BigImage> backgrounds = new ArrayList<>(); // Backgrounds
    
    
    // Global Booleans
    //  Settings
    public static boolean showDevData = false; // Show stats?
    public static boolean fastText = false; // Increase popup write speed?
    //  Internal      
    public static boolean itemGrabbed = false; // True = item was just grabbed
    public static boolean inputIgnored = false; // True = input disabled for PLAY state
    public static boolean hasBeenPaused = false; // True = Game was paused once
    public static boolean newHintAdded = false; // Status of hints



    
}
