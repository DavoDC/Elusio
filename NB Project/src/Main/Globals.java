package Main;



import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.transition.*;

/**
 * Global constants
 * @author David
 */
public class Globals
{
    // AGC
    public static AppGameContainer agc = null;
    
    // Screen dimensions
    public static int screenW = 0;
    public static int screenH = 0;
    
    
    // Cursor
    public static Image cursor;
    
    // States
    public static HashMap<String, Integer> states = new HashMap<>();
    
    // True if the game was paused at any point
    public static boolean isPaused = false;
    
    // Backgrounds
    public static ArrayList<BigImage> backgrounds = new ArrayList<>();
    public static final int BG_COUNT = 17;
    
    // Game font
    public static TrueTypeFont gameFont = null;
    
    // Transitions
    // Must be reinitialised each time, otherwise won't work
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
    
    // Settings
    public static boolean showFPS = true;
    public static boolean showMemUse = true;
    public static boolean showCoords = true;
    
    // Player lives
    public static int playerLives = 5;
    
}
