package code.GameStates.Core;




import code.Entity.CamTMap;
import code.Entity.CollUtil;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
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
    //Screen dimensions
    private int screenW = code.Main.MainGame.screenW;
    private int screenH = code.Main.MainGame.screenH;
    
    //Map
    private TiledMap map;
    private CollUtil coll;
    private CamTMap ctm;
    
    // Player fields
    private SpriteSheet playerSS;
    private Animation playerAnim;
    private int playerAnimSpeed;     
    private int playerX;
    private int playerY;
    private float movementSpeed;
    
  
            
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
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
      map = new TiledMap("res/Map/map.tmx");
      coll = new CollUtil(map);
      ctm = new CamTMap(container, map);
      
      playerSS = new SpriteSheet("res/Sprites/player/walking.png", 33, 48);
      playerAnimSpeed = 500;
      playerAnim = new Animation(playerSS, playerAnimSpeed);

      playerX = 60;
      playerY = 60;
       
      movementSpeed = 0.25f;

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
       
         //camera
         ctm.centerOn(playerX, playerY);

        //Make animation use game time
        playerAnim.update(delta);
        
        //Relative speed
        float relSpeed = delta * movementSpeed;
        
        //Get input
        Input input = gc.getInput();

        // Handle input
        if(input.isKeyDown(Input.KEY_UP)) //Up arrow
            {
              // Start and adjust animation
              playerAnim.start();
              configureFrames(playerAnim, "n9n10n11" ); //Backside

              // Check for collision then map exit
              boolean cond1 = coll.canPass(playerX + 63, playerY - relSpeed);
              boolean cond2 = coll.canPass(playerX + 1, playerY - relSpeed);
              boolean cond3 = true;//playerY > 5;
             
              // Move if conditions are satisfied
              if (cond1 && cond2 && cond3)
              {
                  playerY -= relSpeed;
              }
              
            } 
        else if(input.isKeyDown(Input.KEY_DOWN)) //Down arrow
            {
              // Animate with front of head showing
              playerAnim.start();
              configureFrames(playerAnim, "n0n1n2" ); //Front face

              // Adjust coordinates of player and map
              boolean cond1 = coll.canPass(playerX + 63, playerY + 64 + relSpeed);
              boolean cond2 = coll.canPass(playerX + 1, playerY + 64 + relSpeed);
              boolean cond3 = true;//playerY < screenH-75;
              
              // Move if conditions are satisfied
              if (cond1 && cond2 && cond3)
              {
                  playerY += relSpeed;
              }
              

            }
        else if(input.isKeyDown(Input.KEY_LEFT)) //Left arrow
            {
              // Start and adjust animation
              playerAnim.start();
              configureFrames(playerAnim, "n3n4n5" ); //Left side
              
              // Adjust coordinates of player and map
              boolean cond1 = coll.canPass(playerX - relSpeed, playerY + 1);
              boolean cond2 = coll.canPass(playerX - relSpeed, playerY + 63);
              boolean cond3 =  true;//playerX > 5;
              
              // Move if conditions are satisfied
              if (cond1 && cond2 && cond3)
              {
                  playerX -= relSpeed;
              }

            }
        else if (input.isKeyDown(Input.KEY_RIGHT)) //Right arrow
            {
              // Start and adjust animation
              playerAnim.start();
              configureFrames(playerAnim, "n6n7n8" ); //Front
              
              // Adjust coordinates of player 
              boolean cond1 = coll.canPass(playerX + 50 + relSpeed, playerY + 63);
              boolean cond2 = coll.canPass(playerX + 50 + relSpeed, playerY + 1);
              boolean cond3 = true; //playerX < screenW-50;
              
              // Move if conditions are satisfied
              if (cond1 && cond2 && cond3)
              {
                 playerX += relSpeed;
              }
              
            }
        else // No arrows down
            {
                 playerAnim.stop();
            }
         
    
        
         // Handle setting keys
         if (input.isKeyDown(Input.KEY_F) && input.isKeyDown(Input.KEY_LCONTROL))
         {
             boolean newStatus = !code.Main.MainGame.agc.isFullscreen();
             code.Main.MainGame.agc.setFullscreen(newStatus);
         }
         
        

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
        //Draw map 
        ctm.translateGraphics();
        ctm.drawMap(0,0);
   
        
              
        //Top left info
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem-freeMem)/1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", 10, 10);
        g.drawString("PLAYSTATE", 10, 30);
          
        // Draw player
        int playerW = (int) ((playerSS.getWidth()/3)*1.5);
        int playerH = (int) ((playerSS.getHeight()/4)*1.5);
        playerAnim.draw((int) playerX, (int) playerY, playerW, playerH);
 
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
 * how to do particle effects
 * @param container The surrounding game container
 */
public void particleTest(GameContainer container, int delta) throws SlickException {
	
     Image particleImg = new Image("res/Special/particle.png");
     ParticleSystem  ps = new ParticleSystem(particleImg, 1000);
	
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

         ps.render(400,200);
               
        }
	catch (Exception e) 
        {
		throw new SlickException("Failed to load particle systems", e);
	}
}
   
   
   
   
}