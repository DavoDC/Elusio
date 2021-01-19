package base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import components.modules.Camera;
import components.modules.HUD;
import components.modules.GameMap;
import components.modules.Player;
import components.underlying.Movable;
import components.underlying.LooseMap;
import components.servers.AudioServer;
import components.servers.controls.ControlServer;
import components.servers.particles.ParticleServer;
import components.popups.PopupStore;
import components.servers.FontServer;
import entity.enemy.EnemyStore;
import entity.item.ItemProcessor;
import entity.item.ItemStore;
import entity.obstacle.ObstacleStore;
import states.special.SpRealm;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.GameState;
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
    public static StateBasedGame sbg = null;

    // Game state directory
    public static LooseMap<Integer> states = new LooseMap<>();

    // Current state delta value
    public static int curDelta;

    // Main structures
    public static GameMap gameMap;
    public static Camera cam;
    public static Player player;
    public static HUD hud;

    // Servers
    public static FontServer fontServer;
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
    public static final String gameTitle = "Resoulve";
    public static final String VERSION = "1.3";

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

    // Info Font
    private static TrueTypeFont infoFont;

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
        int curStateID = sbg.getCurrentStateID();

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

        // If file list is not initialized
        if (fileList == null) {

            // Initialize file list
            fileList = new ArrayList<>();

            // Get file paths
            Object[] rawFileList = null;
            try {

                // Get folder with files
                String fileRoot = ".";
                if (Globals.inExe) {
                    fileRoot += ".";
                }
                fileRoot += "\\res\\";

                // Recurse over folder
                rawFileList = Files.walk(Paths.get(fileRoot))
                        .filter(Files::isRegularFile).toArray();

            } catch (IOException ex) {

                // Give details if error occurs
                System.err.println("FileWalkError: " + ex.getMessage()
                        + "\nFilesFound: " + Arrays.toString(rawFileList));
            }

            // Protect against null
            if (rawFileList != null) {

                // For each file path
                for (Object fp : rawFileList) {

                    // Get current path string
                    String path = ((Path) fp).toString();

                    // Add to list
                    fileList.add(path);
                }
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
        String errS = "\ngetFP() bad param: " + subFP;
        String listS = "\nList Len: " + fileList.size();
        listS += "\nList[0]: " + fileList.get(0);
        throw new IllegalArgumentException(errS + listS);
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
            sbg.enterState(states.get(stateName),
                    new FadeOutTransition(Color.black, 639),
                    new FadeInTransition(Color.black, 639));
        } else {

            // Otherwise, just enter state
            sbg.enterState(states.get(stateName));
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
        if (!Globals.inIDE) {

            // Do not show
            return;
        }

        // Get graphics
        Graphics g = agc.getGraphics();

        // Initialize font if needed
        if (infoFont == null) {
            infoFont = fontServer.getFont("Calibri-Plain-36");
        }

        // Adjust font
        g.setFont(infoFont);
        g.setColor(textCol);

        // Calculate position
        int drawX = camX + Globals.screenW - 330;
        int drawY = camY + 30;

        // YSpacing
        int yGap = 40;

        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);

        // Draw memory use 
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        String mem = "Mem. Use: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 1 * yGap);

        // Draw mouse info
        Input input = Globals.agc.getInput();
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        String mouse = "mX: " + mX + " , mY: " + mY;
        g.drawString(mouse, drawX, drawY + 2 * yGap);
        int mCol = GameMap.convXtoCol(mX);
        int mRow = GameMap.convYtoRow(mY);
        String mouseTile = "mC: " + mCol + " , mR: " + mRow;
        g.drawString(mouseTile, drawX, drawY + 3 * yGap);

        // Draw cam info
        String camInfo = "cX: " + camX + " , cY: " + camY;
        g.drawString(camInfo, drawX, drawY + 4 * yGap);
        int camCol = GameMap.convXtoCol(camX);
        int camRow = GameMap.convYtoRow(camY);
        String camTile = "cC: " + camCol + " , cR: " + camRow;
        g.drawString(camTile, drawX, drawY + 5 * yGap);

        // Draw player info
        int adjPX = Globals.player.getX() + Globals.playerXadj;
        int adjPY = Globals.player.getY() + Globals.playerYadj;
        String playerInfo = "pX: " + adjPX + " , pY: " + adjPY;
        g.drawString(playerInfo, drawX, drawY + 6 * yGap);
        int playerCol = GameMap.convXtoCol(adjPX);
        int playerRow = GameMap.convYtoRow(adjPY);
        String playerTile = "pC: " + playerCol + " , pR: " + playerRow;
        g.drawString(playerTile, drawX, drawY + 7 * yGap);

        // Draw ship info
        if (isGameInState("SpRealm")) {
            GameState state = Globals.sbg.getCurrentState();
            SpRealm rift = (SpRealm) state;
            Movable ship = rift.getShip();
            String shipCoord = "sX: " + ship.getX();
            shipCoord += " , sY: " + ship.getY();
            g.drawString(shipCoord, drawX, drawY + 8 * yGap);
        }
    }

}
