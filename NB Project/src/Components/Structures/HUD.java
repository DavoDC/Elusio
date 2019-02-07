package Components.Structures;

import Entity.Player;
import Main.Globals;
import Components.Structures.Camera;
import Components.Buttons.Button;
import Components.Buttons.ButtonGrid;
import Components.Helpers.FontServer;
import java.util.ArrayList;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Helps to draw HUD elements
 *
 * @author David C
 */
public class HUD
{

    // Resource folder
    private final String folder = "res/ui/";

    // Buttons
    private ButtonGrid buttonG;
    private final int BUTTON_NO = 4;
    private final int SIDE_SIZE = 64;
    private final int SPACING = 16;
    private int shiftX;
    private int shiftY;

    // Camera co-ordinates
    private int camX;
    private int camY;

    // Player co-ordinates 
    private int playerX;
    private int playerY;

    // Font for lives
    private TrueTypeFont lifeFont;

    /**
     * Initialise the HUD
     *
     * @param cam
     * @param player
     * @param guiC
     * @param sbg
     */
    public HUD(Camera cam, Player player, GUIContext guiC, StateBasedGame sbg)
    {
        // Create button features
        ArrayList<Object> feats = new ArrayList<>();

        feats.add(BUTTON_NO); // Number of buttons
        feats.add("res/misc/nothing.png"); // Image Location
        feats.add(SPACING); // startXpos
        feats.add(SPACING); // startYpos 
        feats.add(SIDE_SIZE); // width
        feats.add(SIDE_SIZE); // height
        feats.add(SPACING); // Xspacing
        feats.add(0); // Yspacing
        feats.add(10); // NumberofColumns
        feats.add("calibri-plain-5"); // FontString

        buttonG = new ButtonGrid(feats, new ArrayList<>());

        // Adjust labels
        buttonG.applyLabel("");

        // Change images
        buttonG.getButtonByPos(0).setImageLoc(folder + "menu.png");
        buttonG.getButtonByPos(1).setImageLoc(folder + "inv.png");
        buttonG.getButtonByPos(2).setImageLoc(folder + "hint.png");
        buttonG.getButtonByPos(3).setImageLoc(folder + "lives.png");

        // Add actions
        buttonG.getButtonByPos(0).addListener( // Menu button
                (AbstractComponent source) ->
        {
            Globals.hasBeenPaused = true;
            sbg.enterState(Globals.STATES.get("MAINMENU"));
            // No transitions because map goes weird
        });

        buttonG.getButtonByPos(1).addListener( // Inv button
                (AbstractComponent source) ->
        {
            //showInventory();
        });

        buttonG.getButtonByPos(2).addListener( // Hint button 
                (AbstractComponent source) ->
        {
            //showHint();
        });

        // Initialise co-ordinates
        camX = cam.getX();
        camY = cam.getY();
        playerX = player.getX();
        playerY = player.getY();
        shiftX = 0;
        shiftY = 0;

        // Initialise font
        lifeFont = FontServer.getFont("Cambria-Bold-25");

    }

    /**
     * Updates internal values Changes where the HUD is drawn
     *
     * @param cam
     * @param player
     */
    public void update(Camera cam, Player player)
    {
        // Update button shift
        shiftX = cam.getX() - camX;
        shiftY = cam.getY() - camY;

        // Update co-ordinates
        this.camX = cam.getX();
        this.camY = cam.getY();
        this.playerX = player.getX();
        this.playerY = player.getY();

        // Offset mouse so that buttons work   
        Globals.agc.getInput().setOffset(camX + SPACING, camY);

    }

    /**
     * Draw the HUD
     *
     * @param g
     */
    public void drawHUD(Graphics g)
    {
        // Draw buttons   
        buttonG.drawButtonsShifted(g, shiftX, shiftY);

        // Draw info overlays
        drawStatsText(g);
        drawLivesText(g);
    }

    /**
     * Draws stats text on top of button
     *
     * @param g
     */
    private void drawStatsText(Graphics g)
    {
        // Only draw if stats are on
        if (!(Globals.showDevData))
        {
            return;
        }

        // Calculate position
        int drawX = camX + Globals.screenW - 200;
        int drawY = camY + 12;

        // Set font color
        g.setColor(Color.red);

        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);

        // Draw memory use
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        String mem = "Mem. Use: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 20);

        // Draw co-ordinates
        String player = "pX: " + playerX + " , pY: " + playerY;
        g.drawString(player, drawX, drawY + 40);

        String cam = "cX: " + camX + " , cY: " + camY;
        g.drawString(cam, drawX, drawY + 60);

        Input input = Globals.agc.getInput();
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        String mouse = "mX: " + mX + " , mY: " + mY;
        g.drawString(mouse, drawX, drawY + 80);
    }

    /**
     * Draws the number of lives in the bottom right
     *
     * @param g
     */
    private void drawLivesText(Graphics g)
    {
        // Calculate position
        Button ref = buttonG.getButtonByPos(3);
        int drawX = ref.getX() + SIDE_SIZE / 3 + 2;
        int drawY = ref.getY() + SIDE_SIZE / 4 + 2;

        // Draw number of lives
        String livesS = "" + Globals.playerLives + "";
        lifeFont.drawString(drawX, drawY, livesS, Color.black);

    }

}
