package Utility;

import Main.Globals;
import Utility.Menu.FontServer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/**
 * Helps to draw HUD elements
 * 
 * @author David C
 */
public class HUD 
{
    
    // GUI panels
    private Image bag;
    private Image lives;
    private Image menu;
    private Image stats;
    
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
     * @param cx Cam X
     * @param cy Cam Y
     * @param px player X
     * @param py player Y
     */
    public HUD(int cx , int cy, int px, int py)
    {
        try 
        {
            // Initialise images
            bag = new Image("res/hud/bag.png");
            lives = new Image("res/hud/lives.png");
            menu = new Image("res/hud/menu.png");
            stats = new Image("res/hud/stats.png");
            
            // Adjust stats image
            int newW = (stats.getWidth()*2) + 10;
            int newH = stats.getHeight();
            stats = stats.getScaledCopy(newW, newH);
            
            // Initialise co-ordinates
            update(cx, cy, px, py);
            
            // Initialise font
            lifeFont = FontServer.getFont("Cambria-Bold-36");
            
        } 
        catch (SlickException ex) 
        {
            System.err.println("Error loading HUD images");
        }
        
    }
    
    
    /**
     * Updates internal values
     * Changes where the HUD is drawn
     * @param camX
     * @param camY
     * @param playerX
     * @param playerY
     */
    public void update(int camX , int camY, int playerX, int playerY)
    {
        this.camX = camX;
        this.camY = camY;
        this.playerX = playerX;
        this.playerY = playerY;
    }
    
    
    /**
     * Draw the HUD
     * @param g 
     */
    public void drawHUD(Graphics g)
    {
       drawMenu(g);
       drawStats(g);
       drawBag(g);
       drawLives(g);
    }
    
    
    /**
     * Draws the menu button in top left
     * @param g 
     */
    private void drawMenu(Graphics g)
    {
        int drawX = camX;
        int drawY = camY;
        g.drawImage(menu, drawX, drawY);
    }
    
    
    /**
     * Draws stats in the top right
     * @param g 
     */
    private void drawStats(Graphics g)
    {
        int drawX = camX + Globals.screenW - stats.getWidth();
        int drawY = camY;
        
        g.drawImage(stats, drawX, drawY);
        
        // Adjust for text
        drawX += 16;
        drawY += 12;
        
        // Set font color
        g.setColor(Color.black);
        
        // If on, draw FPS
        if (Globals.showFPS)
        {
            String fps = "FPS: " + Globals.agc.getFPS();
            g.drawString(fps, drawX, drawY);
        }
        
        // If on, draw memory use
        if (Globals.showMemUse)
        {
            long freeMem = Runtime.getRuntime().freeMemory();
            long totalMem = Runtime.getRuntime().totalMemory();
            long memoryUsed = (totalMem-freeMem)/1000000;
            String mem = "Memory Usage: " + memoryUsed + " MB";
            g.drawString(mem, drawX, drawY + 20);
        }
        
        // If on, draw co-ordinates
        if (Globals.showCoords)
        {
            String player = "pX: " + playerX + " , pY: " + playerY;
            g.drawString(player, drawX, drawY + 40);
            
            String cam = "cX: " + camX + " , cY: " + camY;
            g.drawString(cam, drawX, drawY + 60);
        }
    }
    
    
    /**
     * Draws the inventory button in the bottom left
     * @param g 
     */
    private void drawBag(Graphics g)
    {
        int drawX = camX;
        int drawY = camY + Globals.screenH - bag.getHeight();
        
        g.drawImage(bag, drawX, drawY);
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
        String lives =  "" + Globals.playerLives + "";
        lifeFont.drawString(drawX, drawY, lives, Color.black);
        
    }
    
}
