package Utility.UI;

import Entity.Player;
import Main.Globals;
import Utility.Camera;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Helps to draw HUD elements
 * 
 * @author David C
 */
public class HUD 
{
    
    // Buttons
    private MouseOverArea menu;
    private MouseOverArea inv;
    
    // Panels
    private Image stats;
    private Image lives;
    
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
     * @param cam
     * @param player
     * @param guiC
     * @param sbg
     */
    public HUD(Camera cam, Player player, GUIContext guiC, StateBasedGame sbg)
    {
        try 
        {
            // Initialise menu button
            Image menuImg = new Image("res/hud/menu.png");
            menu = new MouseOverArea(guiC, menuImg, 0, 0);
            menu.addListener((AbstractComponent source) -> {
                Globals.hasBeenPaused = true;
                sbg.enterState(
                        Globals.states.get("MAINMENU"),
                        Globals.getLeave(),
                        Globals.getEnter());
            });
            
            // Initialise inventory button
            Image invImg = new Image("res/hud/bag.png");
            inv = new MouseOverArea(guiC, invImg, 0, 0);
            inv.addListener((AbstractComponent source) -> 
                    { 
                        //sbg.enterState(
                        //    Globals.states.get("MAINMENU"), //temp
                        //    Globals.getLeave(),
                        //    Globals.getEnter());
                    });
            
            // Initialise panels
            stats = new Image("res/hud/stats.png");
            lives = new Image("res/hud/lives.png");
            
            // Adjust stats image
            int newW = (stats.getWidth()*2) + 10;
            int newH = stats.getHeight();
            stats = stats.getScaledCopy(newW, newH);
            
            // Initialise co-ordinates
            camX = cam.getX();
            camY = cam.getY();
            playerX = player.getStartX();
            playerY = player.getStartY();
            
            // Initialise font
            lifeFont = FontServer.getFont("Cambria-Bold-36");

        } 
        catch (SlickException ex) 
        {
            System.err.println("Error loading HUD");
        }
        
    }
    
    
    /**
     * Updates internal values
     * Changes where the HUD is drawn
     * @param guiC
     * @param cam
     * @param playerX
     * @param playerY
     */
    public void update(Camera cam, int playerX, int playerY)
    {
        this.camX = cam.getX();
        this.camY = cam.getY();
        this.playerX = playerX;
        this.playerY = playerY;
        
        // Offset mouse so that buttons work   
        Globals.agc.getInput().setOffset(camX, camY);
    }
    
    
    /**
     * Draw the HUD
     * @param guiC
     * @param g 
     */
    public void drawHUD(GUIContext guiC, Graphics g)
    {
        drawButtons(guiC, g);
       
        drawStats(g);
       
        drawLives(g);
    }
    
    
    /**
     * Draws buttons
     * @param g 
     */
    private void drawButtons(GUIContext guiC, Graphics g)
    {
        menu.setLocation(camX, camY);
        menu.render(guiC, g);
        
        int drawX = camX;
        int drawY = camY + Globals.screenH - inv.getHeight();
        
        inv.setLocation(drawX, drawY);
        inv.render(guiC, g);
    }
    
    
    /**
     * Draws stats in the top right
     * @param g 
     */
    private void drawStats(Graphics g)
    {
        // Don't continue if stats aren't on
        if (!(Globals.showStats)) { return; }
        
        // Calculate position
        int drawX = camX + Globals.screenW - stats.getWidth();
        int drawY = camY;
        
        // Draw back panel
        g.drawImage(stats, drawX, drawY);
        
        // Adjust for text
        drawX += 16;
        drawY += 12;
        
        // Set font color
        g.setColor(Color.black);
        
        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);
        
        // Draw memory use
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem-freeMem)/1000000;
        String mem = "Memory Usage: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 20);
        
        // Draw co-ordinates
        String player = "pX: " + playerX + " , pY: " + playerY;
        g.drawString(player, drawX, drawY + 40);

        String cam = "cX: " + camX + " , cY: " + camY;
        g.drawString(cam, drawX, drawY + 60);
    }
    
    /**
     * Draws the number of lives in the bottom right
     * @param g 
     */
    private void drawLives(Graphics g)
    {
        // Calculate position
        int drawX = camX + Globals.screenW - lives.getWidth();
        int drawY = camY + Globals.screenH - lives.getHeight();
        
        // Draw panel
        g.drawImage(lives, drawX, drawY);
        
        // Adjust position for text
        drawX += 42;
        drawY += 30;
        
        // Draw number of lives
        String livesS =  "" + Globals.playerLives + "";
        lifeFont.drawString(drawX, drawY, livesS, Color.black);
        
    }
    
}
