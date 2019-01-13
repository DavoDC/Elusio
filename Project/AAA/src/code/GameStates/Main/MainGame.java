package code.GameStates.Main;

import code.GameStates.Menu.Pause;
import code.GameStates.Menu.Play;
import code.GameStates.Menu.GameOver;
import code.GameStates.Menu.MainMenu;
import code.GameStates.Menu.Intro;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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
    
    // StateIDs = Core
    public static final int ABOUT = 1;
    public static final int CREDITS = 2;
    public static final int GAME_OVER = 3;
    public static final int HELP = 3;
    public static final int INTRO = 4;
    public static final int MAIN_MENU = 5;
    public static final int PAUSE = 6;
    public static final int PLAY = 7;
    public static final int SETTINGS = 8;
    
    // StateIDs = Minigames
    
    
    
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
        this.addState(new Intro());
        
        // State 2 = Start Menu
        this.addState(new MainMenu());
        
        //State 3 = Gameplay
        this.addState(new Play());
        
        //State 4 = Paused Game
        this.addState(new Pause());
        
        // State 5 = Game Over
        this.addState(new GameOver()); 
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
