package Main;



import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Global constants
 * @author David
 */
public class Globals
{
    // AGC
    public static AppGameContainer agc = null;
    
    // Screen dimensions
    public static int screenH = 0;
    public static int screenW = 0;
    
    // Cursor
    public static Image cursor;
    
    // States
    public static final int LOADING_ID = 5;
    public static HashMap<String, Integer> states = new HashMap<>();
    
    // True if the game was paused at any point
    public static boolean isPaused = false;
    
    // Backgrounds
    public static ArrayList<BigImage> backgrounds = new ArrayList<>();
    public static final int BG_COUNT = 17;
    
    // Game font
    public static TrueTypeFont gameFont = null;
    
    // Transitions
    public static final FadeInTransition enter = new FadeInTransition();
    public static final FadeOutTransition leave = new FadeOutTransition();
    
    // Settings
    public static boolean showFPS = false;
    public static boolean showMemUse = false;
    public static boolean showCoords = false;
    
}
