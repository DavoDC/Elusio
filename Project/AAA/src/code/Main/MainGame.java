package code.Main;

import code.GameStates.Core.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


/**
 * Sets up the game
 * Initializes states
 * 
 * @author David Charkey
 */
public class MainGame extends StateBasedGame
{
    //Fields
    public static AppGameContainer agc;
    public static int screenW;
    public static int screenH;
    
    public static String mgProgress;
    
    
    /**
     * Main method
     * Entry point
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception 
    {
        
         // Create AGC
         MainGame mg = new MainGame("Alien Aztec Adventure");
         agc = new AppGameContainer(mg);
          
         // Adjust AGC
         screenH = agc.getScreenHeight();
         screenW = agc.getScreenWidth();
         agc.setDisplayMode(screenW, screenH, true);
         agc.setAlwaysRender(true);
         agc.setTargetFrameRate(55);
         agc.setShowFPS(false);

         // Start AGC
         agc.start();
         
    }
    
    
    
     /**
     * Constructor
     * @param title 
     */
    public MainGame(String title) {
        super(title);
    }

  

 
    /**
     * Initialise states
     * 
     * @param gc
     * @throws SlickException 
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        
        // State 1 = Intro/Logo
        this.addState(new IntroState());
        
        // State 2 = Start Menu
        this.addState(new MenuState());
        
        //State 3 = Gameplay
        this.addState(new PlayState());
        
        //State 4 = Paused Game
        this.addState(new PauseState());
        
        // State 5 = Game Over
        this.addState(new GameOverState()); 
    }
    
    /**
     * Adjust an image for the system screen size
     * @param img
     * @return
     */
    public static Image adjustImage(Image img)
    {
        return img.getScaledCopy(screenW , screenH);
        
    }
    
    
    
    public static TrueTypeFont getGameFont(float size) 
    {
        TrueTypeFont gfont = null;
        try 
        {
            InputStream fis = ResourceLoader.getResourceAsStream("res/Special/3dventure.ttf");
            
            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, fis);
            awtFont = awtFont.deriveFont(size);
            gfont = new TrueTypeFont(awtFont, true);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return gfont;
    }
    
    
    
    
    
    
    
    

    public void drawSpriteExample()
    {
        try
        { 
           SpriteSheet ss = new SpriteSheet("res/tiles.gif", 16, 16); 
        
           for(int i = 0; i != 6; i++)
             {
                 ss.getSprite(i, 0).draw(50, i*90, 5);
                 ss.getSprite(i, 1).draw(200, i*90, 5);

                 ss.getSprite(0, 1).draw(300, i*80, 5);
             }
        
        }
        catch(Exception e) 
        { 
        
        }
 
    }
    
    
      public void drawingShapesExample(Graphics g)
    {
       //making shapes
        Shape shape = new Circle(400,100,10);
        Shape shape2 = new Rectangle(50, 200, 30, 20); 
        
        g.setBackground(Color.darkGray);
      
       
       // Drawing shapes
       // Each action requires a colour set before it
       g.setColor(Color.blue);
       g.fill(shape);
       g.setColor(Color.blue);
       g.draw(shape);
       
       g.setColor(Color.green);
       g.fill(shape2);
       g.setColor(Color.green);
       g.draw(shape2);
       
       
       //Collision example
       Boolean hasCollided = shape2.intersects(shape);
       
       if(hasCollided) { 
           g.setColor(Color.yellow);
    
               }
       
       //memory ex
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = totalMem-freeMem/1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", 10, 25);
  
       
       
    }
    
    
    public void arrowKeyinputEx(GameContainer gc)
    {
        int movementSpeed = 5;
        Shape shape2 = new Rectangle(50, 200, 30, 20); 
        
        
       

         
    }
    
  

   
    
	
}
