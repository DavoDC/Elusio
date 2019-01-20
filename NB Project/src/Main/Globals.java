package Main;



import java.util.HashMap;
import org.newdawn.slick.AppGameContainer;
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
    
    // States
    public static final int LOADING_ID = 5;
    public static HashMap<String, Integer> states = new HashMap<>();
    
    // Has the game been paused?
    public static boolean isPaused = false;
    
    // Number of backgrounds
    public static final int BG_COUNT = 17;
    
    // Transitions
    public static final FadeInTransition enter = new FadeInTransition();
    public static final FadeOutTransition leave = new FadeOutTransition();
    
    // Settings
    public static boolean showFPS = false;
    public static boolean showMemory = false;
    public static boolean showCoordinates = false;
    
}
