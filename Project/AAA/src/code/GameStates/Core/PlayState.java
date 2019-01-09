package code.GameStates.Core;



import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleEmitter;


import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.*;


import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


/**
 *
 * @author David
 */
public class PlayState extends BasicGameState
{
    // Player fields
    private SpriteSheet playerSS;
    private Animation playerAnim;
    private int playerX;
    private int playerY;
    private int playerAnimSpeed;
    
    //test
    private ParticleSystem ps;
    
    //Map
    private TiledMap map;
    
            
    /**
     * Used to identify states and switch to them
     * @return id
     */
    @Override
    public int getID() { return 3; }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
      playerAnimSpeed = 500;
        
      playerSS = new SpriteSheet("res/Sprites/player/walking.png", 33, 48);
      playerAnim = new Animation(playerSS, playerAnimSpeed);
      
      playerX = 40;
      playerY = 100;
      
       Image particleImg = new Image("res/Special/particle.png");
          ps = new ParticleSystem(particleImg, 1000);
    
        
          
          map = new TiledMap("res/Map/map.tmx","res/Map");
         
  
      
    
       
    }
    
       /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
       
        //Make animation use game time
        playerAnim.update(delta);
        
        //Control player with arrows
        Input input = gc.getInput();
         
        // Player movement speed
         int movementSpeed = 3;

         if(input.isKeyDown(Input.KEY_DOWN))
             {
                 playerAnim.start();
                 
              playerY += movementSpeed;
              
              // Show front of head 
              configureFrames(playerAnim, "n0n1n2" );
             }
          else if(input.isKeyDown(Input.KEY_LEFT))
             {
                 playerAnim.start();
              playerX -= movementSpeed;
              
              // Show left side
              configureFrames(playerAnim, "n3n4n5" );
             }
         else if (input.isKeyDown(Input.KEY_RIGHT))
             {
                 playerAnim.start();
              playerX += movementSpeed;
              
              // Show right side
              configureFrames(playerAnim, "n6n7n8" );

             }
         else if(input.isKeyDown(Input.KEY_UP))
             {
                 playerAnim.start();
              playerY -= movementSpeed;
              
              // Show back of head 
              configureFrames(playerAnim, "n9n10n11" );
             } 
         else // No arrows down
             {
                 playerAnim.stop();
             }
         

          // particleTest(gc,delta);
    }

   
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
    {
        //draw map first
        map.render(0,0);
        
        
        //Top left info
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem-freeMem)/1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", 10, 10);
        
        g.drawString("PLAYSTATE", 10, 30);
        
        
        // Draw player
        int playerW = (int) ((playerSS.getWidth()/3)*1.5);
        int playerH = (int) ((playerSS.getHeight()/4)*1.5);
        playerAnim.draw(playerX, playerY, playerW, playerH);
        
         //particle render
       // ps.render(400,200);
        
       
       
        
     
     
        
    }

   /**
    * Adjusts an animation to only use the referenced frames
    * Frame numbers must start with "n"
    * @param anim
    * @param configS 
    */
   public void configureFrames(Animation anim, String configS)
   {
    // Get frame count 
    int frameCount = anim.getFrameCount();
    
    //Turn string into ArrayList
    ArrayList<Integer> config = new ArrayList<>();
    for(String curNo : configS.split("n"))
    {
        if (curNo.length() != 0) { config.add(Integer.parseInt(curNo)); }
    }
    
    
    // Process config
    for(int i = 0; i < frameCount; i++)
    {
        
        //If inputted, activate frame
        if (config.contains(i))
        {
            anim.setDuration(i, playerAnimSpeed);
            
            
        }
        else  //If not, deactivate
        {
            anim.setDuration(i, 0);
        } 
    }  
   }
   
   
   /**
 * load resources (the particle system) and create our duplicate emitters
 * and place them nicely on the screen
 * @param container The surrounding game container
 */
public void particleTest(GameContainer container, int delta) throws SlickException {
	
	
	try 
        {
	   
         ParticleEmitter smoke = ParticleIO.loadEmitter("res/Special/smoke.xml");
         
         smoke.setEnabled(true);
         FireEmitter fire = new FireEmitter(200,200,5);
         fire.setEnabled(true);

        
          ps.addEmitter(smoke);
          ps.addEmitter(fire);
          ps.setVisible(true);
          ps.setPosition(400, 200);
          
          ps.setBlendingMode(ParticleSystem.BLEND_COMBINE);

          ps.update(delta);

               
        }
	catch (Exception e) 
        {
		throw new SlickException("Failed to load particle systems", e);
	}
}
   
   
   
   
}