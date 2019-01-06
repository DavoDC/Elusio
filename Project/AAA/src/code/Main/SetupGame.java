package code.Main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * The entry point of the game.
 * This class loads up a JFrame window and puts a GamePanel into it.
 * 
 * @author David Charkey
 */
public class SetupGame extends BasicGame
{
   private Image img;
    
    //Main method
    public static void main(String[] args) throws Exception 
    {
        
         // Create AGC
         AppGameContainer agc = new AppGameContainer(new SetupGame("AAA"));
         
         // Set title
         agc.setTitle("Alien Aztec Adventure");
          
         // Set resolution and if in fullscreen mode
         agc.setDisplayMode(800, 600, false);
         
         // Render even without focus
         agc.setAlwaysRender(true);
         
         // Start AGC
         agc.start();
        
    }
    
     /**
     * Constructor from Slick Basic Game
     * @param title 
     */
    public SetupGame(String title) {
        super(title);
    }

    
    @Override 
    /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param gc Holds the game
     */
    public void init(GameContainer gc) throws SlickException 
    {
        img = new Image("res/menu.gif");
    }

    
    
    @Override
    /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param delta Amount of time since last update
     */
    public void update(GameContainer gc, int delta) throws SlickException {
         //Not coded yet
    }
    

    @Override 
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param g
     */
    public void render(GameContainer gc, Graphics g) throws SlickException 
    {
    
        //drawSpriteExample();
  
         
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
    
    
	
}
