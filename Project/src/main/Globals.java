package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import components.modules.Camera;
import components.modules.HUD;
import components.modules.Map;
import components.modules.Player;
import components.popups.PopupStore;
import components.servers.AudioServer;
import components.servers.ControlServer;
import components.servers.particles.ParticleServer;
import entity.enemy.EnemyStore;
import entity.item.ItemProcessor;
import entity.item.ItemStore;
import entity.obstacle.ObstacleStore;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
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
    public static HashMap<String, Integer> states = new HashMap<>();

    // Font variables
    public static TrueTypeFont gameFont = null;
    public static final String headerFont = "Gamefont-Plain-60";

    // Main structures
    public static Map map;
    public static Camera cam;
    public static Player player;
    public static HUD hud;

    // Servers
    public static ControlServer conServer;
    public static AudioServer audioServer;
    public static ParticleServer partServer;

    // Misc structures
    public static PopupStore popStore;
    public static ItemProcessor itemProc;

    // Entity stores
    public static ItemStore itemStore;
    public static EnemyStore enemyStore;
    public static ObstacleStore obStore;

    // Game information
    public static final String gameTitle = "Elusio";
    public static final String VERSION = "2.0";

    // Resource filepath strings
    public static ArrayList<String> fileList;

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
    public static boolean isInputBlocked = false;
    // True means game was paused at least once
    public static boolean isGameStarted = false;
    // True means the rift state has been used
    public static boolean isIntroRiftDone = false;

    // Miscellaneous 
    public static int crystalsPlaced = 0;

    /**
     * Return true if the game is in the given state
     *
     * @param stateName
     * @return
     */
    public static boolean isGameInState(String stateName) {

        // Get inputted state ID
        int queryID = states.get(stateName);

        // Get current state ID
        int curStateID = SBG.getCurrentStateID();

        // Return true if they match, and false otherwise
        return (queryID == curStateID);
    }

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

    /**
     * Change the game's state with transitions if wanted
     *
     * @param stateName
     * @param doTrans
     */
    public static void changeState(String stateName, boolean doTrans) {

        // If transition wanted
        if (doTrans) {

            // Enter state with transition
            SBG.enterState(states.get(stateName),
                    new FadeOutTransition(Color.black, 639),
                    new FadeInTransition(Color.black, 639));
        } else {

            // Otherwise, just enter state
            SBG.enterState(states.get(stateName));
        }
    }

    /**
     * Draw internal game variable data in top right of screen
     *
     * @param camX
     * @param camY
     * @param textCol
     */
    public static void drawRuntimeInfo(int camX, int camY, Color textCol) {

        // If not in IDE
        if (!(Globals.inIDE)) {

            // Do not show
            return;
        }

        // Get graphics
        Graphics g = agc.getGraphics();

        // Calculate position
        int drawX = camX + Globals.screenW - 200;
        int drawY = camY + 12;

        // Set font color
        g.setColor(textCol);

        // YSpacing
        int yGap = 20;

        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);

        // Draw memory use 
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        String mem = "Mem. Use: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 1 * yGap);

        // Draw cam info
        String camInfo = "cX: " + camX + " , cY: " + camY;
        g.drawString(camInfo, drawX, drawY + 2 * yGap);
        int camCol = Map.convertXtoCol(camX);
        int camRow = Map.convYtoRow(camY);
        String camTile = "cC: " + camCol + " , cR: " + camRow;
        g.drawString(camTile, drawX, drawY + 3 * yGap);

        // Draw player info
        int adjPX = Globals.player.getX() + Globals.playerXadj;
        int adjPY = Globals.player.getY() + Globals.playerYadj;
        String playerInfo = "pX: " + adjPX + " , pY: " + adjPY;
        g.drawString(playerInfo, drawX, drawY + 4 * yGap);
        int playerCol = Map.convertXtoCol(adjPX);
        int playerRow = Map.convYtoRow(adjPY);
        String playerTile = "pC: " + playerCol + " , pR: " + playerRow;
        g.drawString(playerTile, drawX, drawY + 5 * yGap);

        // Draw mouse info
        Input input = Globals.agc.getInput();
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        String mouse = "mX: " + mX + " , mY: " + mY;
        g.drawString(mouse, drawX, drawY + 6 * yGap);
        int mCol = Map.convertXtoCol(mX);
        int mRow = Map.convYtoRow(mY);
        String mouseTile = "mC: " + mCol + " , mR: " + mRow;
        g.drawString(mouseTile, drawX, drawY + 7 * yGap);
    }

}
