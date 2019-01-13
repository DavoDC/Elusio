package code.Main;

import code.GameStates.Core.*;
import code.Utility.BGBank;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
    // Screen dimensions
    public static int screenW;
    public static int screenH;
    
    // Accessible AGC
    public static AppGameContainer agc;
    
    //Acessible BGBank
    public static BGBank bgb;
    
    
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
         agc.setClearEachFrame(false);
         agc.setShowFPS(true);

         // Start AGC
         agc.start();
         
         //Initialise BGBank
         bgb = new BGBank();
    }

    
    /**
     * Constructor
     * @param title 
     */
    public MainGame(String title) 
    {
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
    
    
    
    /**
     * Get the game font, in the right format
     * @param size Fontsize
     * @return font as a TrueTypeFont
     * @throws java.lang.Exception
     */
    public static TrueTypeFont getGameFont(float size) throws Exception
    {
        InputStream fontStream = ResourceLoader.getResourceAsStream("res/misc/3dventure.ttf"); 
        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        awtFont = awtFont.deriveFont(size);
        TrueTypeFont gamefont = new TrueTypeFont(awtFont, true);

        return gamefont;
    }
    
    
   
    
    
    
    
    
    
    
    
    

    public void drawSpriteExample()
    {
        try
        { 
          // SpriteSheet ss = new SpriteSheet("image", 16, 16); 
        
           for(int i = 0; i != 6; i++)
             {
              //   ss.getSprite(i, 0).draw(50, i*90, 5);
             //    ss.getSprite(i, 1).draw(200, i*90, 5);

               //  ss.getSprite(0, 1).draw(300, i*80, 5);
             }
        
        }
        catch(Exception e) 
        { 
        
        }
 
    }
    
    
      public void recordMemoryEx(Graphics g)
    {

       //memory ex
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = totalMem-freeMem/1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", 10, 25);
       
    }
    
    

	
}
