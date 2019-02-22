package Main;

import Components.Structures.Map;
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

    // The game mpa
    public static Map map = null;

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
    public static final String root = "res/";
    public static final String headerFont = "Gamefont-Plain-60";
    public static final String dimensionName = "Ellusio-9";
    public static final String gameTitle = "Escaping " + dimensionName;
    public static final String gameFontRes = root + "misc/3dventure.ttf";
    public static final String alertRes = root + "ui/alert.png";
    public static final String generalPanelRes = root + "ui/general.png";
    public static final String emptyImgRes = root + "misc/nothing.png";
    public static final String popupPanelRes = root + "ui/popup.png";
    public static final String playerSprRes = root + "player/frames.png";
    public static final String SFXres = root + "audio/effects/";
    public static final String mapRes = root + "map/map.tmx";
    public static final String cursorRes = root + "misc/cursor.png";

    // Global Numbers
    public static int screenW = 0; // Screen width
    public static int screenH = 0; // Screen height
    public static int tileSize = 64; // Side length of tiles
    public static final int BG_COUNT = 2; // Number of backgrounds to load (max 17) //test
    public static final int playerXadj = 22; // Adjust player X pos
    public static final int playerYadj = 64; // Adjusts player Y pos

    // Global Images
    public static Image cursor; // Image of cursor
    public static Image alertMark; // Image of exclamation mark
    public static ArrayList<BigImage> backgrounds = new ArrayList<>(); // Backgrounds

    // Global Booleans
    //  Settings
    public static boolean showDevData = true; // Show stats? //test
    public static boolean fastText = true; // Increase popup write speed? //test
    //  Internal      
    public static boolean inputIgnored = false; // True = input disabled for PLAY state
    public static boolean hasBeenPaused = false; // True = Game was paused once

}
