package code.GameStates.Core;

import code.Entity.Camera;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
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
    private final int screenW = code.Main.MainGame.screenW;
    private final int screenH = code.Main.MainGame.screenH;
    
    //Map
    private TiledMap map;
    private Camera cam;
    
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
      map = new TiledMap("res/map/map.tmx");
      cam = new Camera(container, map);
      
      playerSS = new SpriteSheet("res/sprites/player/walking.png", 33, 48);
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

              // Check for blocked tiles
              //boolean cond1 = coll.canPass(playerX + 63, playerY - relSpeed);
             // boolean cond2 = coll.canPass(playerX + 1, playerY - relSpeed); //add camY factor
              
              // Check for edge of map
             // boolean cond3 = coll.canPass(playerX + 1, playerY - 50 - relSpeed); 
             
              // Move if conditions are satisfied
              //if (cond1 && cond2 && cond3)
             // {
                  playerY -= relSpeed;
             // }
              
            } 
        else if(input.isKeyDown(Input.KEY_DOWN)) //Down arrow
            {
              // Animate with front of head showing
              playerAnim.start();
              configureFrames(playerAnim, "n0n1n2" ); //Front face

              // Check for blocked tiles
             // boolean cond1 = coll.canPass(playerX + 63, playerY + 64 + relSpeed);
              //boolean cond2 = coll.canPass(playerX + 1, playerY + 64 + relSpeed);
              
              // Check for edge of map
             // boolean cond3 = playerY < (cam.getMapH()-70)-cam.getCamY() ;
              
              // Move if conditions are satisfied
            //  if (cond1 && cond2 && cond3)
            //  {
                  playerY += relSpeed;
            //  }
              

            }
        else if(input.isKeyDown(Input.KEY_LEFT)) //Left arrow
            {
              // Start and adjust animation
              playerAnim.start();
              configureFrames(playerAnim, "n3n4n5" ); //Left side
              
              // Check for blocked tiles
              //boolean cond1 = coll.canPass(playerX - relSpeed, playerY + 1);
             // boolean cond2 = coll.canPass(playerX - relSpeed, playerY + 63);
              
              // Check for edge of map
             // boolean cond3 = coll.canPass(playerX - 60 - relSpeed, playerY + 63);
              
              // Move if conditions are satisfied
              //if (cond1 && cond2 && cond3)
              //{
                  playerX -= relSpeed;
             // }

            }
        else if (input.isKeyDown(Input.KEY_RIGHT)) //Right arrow
            {
              // Start and adjust animation
              playerAnim.start();
              configureFrames(playerAnim, "n6n7n8" ); //Front
              
              // Check for blocked tiles
             // boolean cond1 = coll.canPass(playerX + 50 + relSpeed, playerY + 63);
              //boolean cond2 = coll.canPass(playerX + 50 + relSpeed, playerY + 1);
              
              // Check for edge of map
              //boolean cond3 = playerX < (cam.getMapW()-40)-cam.getCamX(); 
              
              // Move if conditions are satisfied
              //if (cond1 && cond2 && cond3)
              //{
                 playerX += relSpeed;
              //}
              
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
         
         // Update camera
         cam.centerOn(playerX, playerY);

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
        //Top left info
        int infoX = playerX - 100;
        int infoY = playerY - 100;
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem-freeMem)/1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", infoX, infoY);
        g.drawString("px: " + playerX + "  , py: " + playerY, infoX, infoY + 20);

        // Draw camera's view of map
        cam.drawMap();
        cam.translateGraphics();
          
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
	
     //Image particleImg = new Image(particle png)
     //ParticleSystem  ps = new ParticleSystem(particleImg, 1000);
	
//	try 
//        {
//	   
//        // ParticleEmitter smoke = ParticleIO.loadEmitter(smoke xml)
//         
//         smoke.setEnabled(true);
//         FireEmitter fire = new FireEmitter(200,200,5);
//         fire.setEnabled(true);
//
//        
//          ps.addEmitter(smoke);
//          ps.addEmitter(fire);
//          ps.setVisible(true);
//          ps.setPosition(400, 200);
//          
//          ps.setBlendingMode(ParticleSystem.BLEND_COMBINE);
//
//          ps.update(delta);
//
//         ps.render(400,200);
//               
//        }
//	catch (Exception e) 
//        {
//		throw new SlickException("Failed to load particle systems", e);
//	}
}
   
   
   
   
}