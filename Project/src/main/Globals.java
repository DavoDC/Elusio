package main;

import java.util.ArrayList;
import java.util.HashMap;

import components.structures.Camera;
import components.structures.HUD;
import components.structures.Map;
import components.structures.Player;
import components.servers.AudioServer;
import components.servers.ControlServer;
import entity.enemy.EnemyStore;
import entity.item.ItemProcessor;
import entity.item.ItemStore;
import entity.obstacle.ObstacleStore;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Global scope objects and constants
 *
 * @author David
 */
public class Globals {

    // Game framework
    public static AppGameContainer agc = null;
    public static StateBasedGame SBG = null;

    // Game state directory
    public static HashMap<String, Integer> STATES = new HashMap<>();

    // Font variables
    public static TrueTypeFont gameFont = null;
    public static final String headerFont = "Gamefont-Plain-60";

    // Main structures
    public static Map map;
    public static Camera cam;
    public static Player player;
    public static HUD hud;
    public static ControlServer conServer;
    public static AudioServer audioServer;
    public static ItemProcessor itemProc;

    // Entity stores
    public static ItemStore itemStore;
    public static EnemyStore enemyStore;
    public static ObstacleStore obStore;

    // Music
    public static Music ambientMusic;

    // Game information
    public static final String gameTitle = "Elusio";
    public static final String VERSION = "2.0";

    // Resource filepath strings
    public static final String root = "res/";
    public static final String SFXres = root + "audio/effects/";
    public static final String ambMusRes = root + "audio/music/ambmus.ogg";
    public static final String mapRes = root + "map/map.tmx";
    public static final String cursorRes = root + "misc/cursor.png";
    public static final String gameFontRes = root + "misc/3dventure.ttf";
    public static final String emptyImgRes = root + "misc/nothing.png";
    public static final String playerSprRes = root + "player/frames.png";
    public static final String buttonPanelRes = root + "ui/general.png";
    public static final String popupPanelRes = root + "ui/popup.png";
    public static final String itemPanelRes = root + "ui/iteminfo.png";

    // Constants
    public static int screenW = 0; // Screen width
    public static int screenH = 0; // Screen height
    public static int tileSize = 64; // Side length of tiles
    public static final int BG_COUNT = 17; // Number of backgrounds to load (max 17) 
    public static final int playerXadj = 22; // Adjust player X pos
    public static final int playerYadj = 64; // Adjusts player Y pos

    // Images
    public static Image cursor; // Image of cursor
    public static Image alertMark; // Image of exclamation mark
    public static ArrayList<BigImage> backgrounds = new ArrayList<>(); // Backgrounds

    // Statuses
    // Game execution environment
    public static boolean inIDE = false;
    public static boolean inExe = false;
    // True means input was disabled for ActualGame state
    public static boolean inputIgnored = false;
    // True means game was paused at least once
    public static boolean hasBeenPaused = false;

    // Miscellaneous 
    public static int crystalsPlaced = 0;

    // Transitions
    public static Transition getLeave() {
        return new FadeOutTransition(Color.black, 639);
    }

    public static Transition getEnter() {
        return new FadeInTransition(Color.black, 639);
    }

}
