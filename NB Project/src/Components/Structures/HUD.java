package Components.Structures;

import Main.Globals;
import Components.Buttons.Button;
import Components.Buttons.ButtonGrid;
import Components.Helpers.FontServer;
import Components.Popups.Popup;
import Components.Popups.PopupDisplayer;
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
    private final String folder = Globals.root + "ui/";

    // Buttons
    private ButtonGrid buttonG;
    private final int BUTTON_NO = 1;
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

    // Popup Displayer
    private PopupDisplayer popupDisp;

    /**
     * Initialise the HUD
     *
     * @param cam
     * @param player
     */
    public HUD(Camera cam, Player player)
    {
        // Create button features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(BUTTON_NO); // Number of buttons
        feats.add(Globals.emptyImgRes); // Image Location
        feats.add(SPACING); // startXpos
        feats.add(SPACING); // startYpos 
        feats.add(SIDE_SIZE); // width
        feats.add(SIDE_SIZE); // height
        feats.add(SPACING); // Xspacing
        feats.add(0); // Yspacing
        feats.add(BUTTON_NO); // NumberofColumns
        feats.add("calibri-plain-5"); // FontString

        // Create button labels
        ArrayList<String> labels = new ArrayList<>();
        int buttonNo = (int) feats.get(0);
        for (int i = 0; i <= buttonNo; i++)
        {
            labels.add(" ");
        }

        buttonG = new ButtonGrid(feats, labels);

        // Change images
        buttonG.getButtonByPos(0).setImageLoc(folder + "menu.png");
//        buttonG.getButtonByPos(1).setImageLoc(folder + "inv.png");
//        buttonG.getButtonByPos(1).setImageLoc(folder + "hint.png");
//        buttonG.getButtonByPos(X).setImageLoc(folder + "lives.png");

        // Add actions
        buttonG.getButtonByPos(0).addListener( // Menu button
                (AbstractComponent source) ->
        {
            Globals.hasBeenPaused = true;
            Globals.SBG.enterState(Globals.STATES.get("MAINMENU"));
            // No transitions because map goes weird
        });

//        buttonG.getButtonByPos(1).addListener( // Inv button
//                (AbstractComponent source) ->
//        {
//            //showInventory();
//        });
        // Initialise co-ordinates
        camX = cam.getX();
        camY = cam.getY();
        playerX = player.getX();
        playerY = player.getY();
        shiftX = 0;
        shiftY = 0;

        // Initialise font
        lifeFont = FontServer.getFont("Cambria-Bold-25");

        // Initialise PD
        popupDisp = new PopupDisplayer();

    }

    /**
     * Updates internal values Changes where the HUD is drawn
     *
     * @param cam
     * @param player
     * @param delta
     */
    public void update(Camera cam, Player player, int delta)
    {
        // Update button shift
        shiftX = cam.getX() - camX;
        shiftY = cam.getY() - camY;

        // Update co-ordinates
        this.camX = cam.getX();
        this.camY = cam.getY();
        this.playerX = player.getX();
        this.playerY = player.getY();

        // Update PD
        popupDisp.updatePD();

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
        // drawLivesText(g);

        // Draw popups
        popupDisp.renderPopups(g);
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
        g.setColor(Color.black);

        // YSpacing
        int yGap = 20;

        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);

        // Draw memory use = 1
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        String mem = "Mem. Use: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 1 * yGap);

        // Draw cam = 2 and 3
        String cam = "cX: " + camX + " , cY: " + camY;
        g.drawString(cam, drawX, drawY + 2 * yGap);
        int camCol = Map.convertXtoCol(camX);
        int camRow = Map.convertYtoRow(camY);
        String camTile = "cC: " + camCol + " , cR: " + camRow;
        g.drawString(camTile, drawX, drawY + 3 * yGap);

        // Draw player = 4 and 5
        int adjPX = playerX + Globals.playerXadj;
        int adjPY = playerY + Globals.playerYadj;
        String player = "pX: " + adjPX + " , pY: " + adjPY;
        g.drawString(player, drawX, drawY + 4 * yGap);
        int playerCol = Map.convertXtoCol(adjPX);
        int playerRow = Map.convertYtoRow(adjPY);
        String playerTile = "pC: " + playerCol + " , pR: " + playerRow;
        g.drawString(playerTile, drawX, drawY + 5 * yGap);

        // Get extra mouse info
        Input input = Globals.agc.getInput();
        int mX = input.getMouseX();
        int mY = input.getMouseY();

        // Draw mouse = 6 and 7 
        String mouse = "mX: " + mX + " , mY: " + mY;
        g.drawString(mouse, drawX, drawY + 6 * yGap);
        int mCol = Map.convertXtoCol(mX);
        int mRow = Map.convertYtoRow(mY);
        String mouseTile = "pC: " + mCol + " , pR: " + mRow;
        g.drawString(mouseTile, drawX, drawY + 7 * yGap);
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
        // String livesS = "" + Globals.playerLives + "";
        // lifeFont.drawString(drawX, drawY, livesS, Color.black);
    }

    /**
     * Load a popup into the displayer
     *
     * @param itemInfo
     */
    public void loadPopup(Popup itemInfo)
    {
        popupDisp.loadPopup(itemInfo);
    }

}
