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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static ArrayList<String> fileList;
    
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

    /**
     * Return a full file path using a substring of it
     * 
     * @param subFP
     * @return 
     */
    public static String getFP(String subFP) {

        // If file map is not initialized
        if (fileList == null) {

            // Initialize file map
            fileList = new ArrayList<>();

            // Get file path
            Object[] rawFileList = null;
            try {
                rawFileList = Files.walk(Paths.get(".\\components\\res\\"))
                        .filter(Files::isRegularFile).toArray();
            } catch (IOException ex) {
                Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
            }

            // For each file path
            for (Object fp : rawFileList) {
                // Get current path string
                String path = ((Path) fp).toString();

                // Trim start to make path begin with "res/"
                path = path.replace(".\\components\\", "");

                // Add to list
                fileList.add(path);
            }
        }

        // For all file paths in list
        for (String fileP : fileList) {

            // If file path contains substring 
            if (fileP.contains(subFP)) {

                // Return file path
                return fileP;
            }
        }

        // If nothing found, throw exception
        throw new IllegalArgumentException("getFP: bad param = " + subFP);
    }

    // Transitions
    public static Transition getLeave() {
        return new FadeOutTransition(Color.black, 639);
    }

    public static Transition getEnter() {
        return new FadeInTransition(Color.black, 639);
    }

}
