package States;

import Entity.Camera;
import Entity.Player;
import Main.Globals;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


/**
 *
 * @author David
 */
public class Play extends BasicGameState
{
    
    // Map objects
    private TiledMap map;
    private Camera cam;
    
    // Player
    private Player alien;
    private int playerX;
    private int playerY;
            
    /**
     * Used to identify states and switch to them
     * @return id
     */
    @Override
    public int getID() { return Globals.states.get("PLAY"); }

    
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
      
      alien = new Player();
      playerX = alien.getStartX();
      playerY = alien.getStartY();
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
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        //Make animation use game time
        alien.updateAnimation(delta);
        
        //Relative speed
        float relSpeed = delta * alien.getMovSpeed();
        
        // FActor out to a method
        //Get input
        Input input = gc.getInput();

        // Handle input
        if(input.isKeyDown(Input.KEY_UP)) //Up arrow
            {
              // Change animation
              alien.startAnim("up");
              
              // Move if conditions are satisfied
              //if (map.upAllowed())
              // {
                  playerY -= relSpeed;
              // }
              
            } 
        else if(input.isKeyDown(Input.KEY_DOWN)) //Down arrow
            {
              // Change animation
              alien.startAnim("down");

              // Move if conditions are satisfied
              //if (map.downAllowed())
              // {
                  playerY += relSpeed;
              // }
             
            }
        else if(input.isKeyDown(Input.KEY_LEFT)) //Left arrow
            {
              // Change animation
              alien.startAnim("left");
              
              // Move if conditions are satisfied
              //if (map.leftAllowed())
              //{
                  playerX -= relSpeed;
             // }

            }
        else if (input.isKeyDown(Input.KEY_RIGHT)) //Right arrow
            {
              // Change animation
              alien.startAnim("right");
              
              // Move if conditions are satisfied
              //if (map.rightAllowed())
              //{
                 playerX += relSpeed;
              //}
              
            }
        else // No arrows down
            {
               alien.stopAnim();
            }
         
         
         // Pause key
         if (input.isKeyPressed(Input.KEY_ESCAPE))
         {
             sbg.enterState(
                 Globals.states.get("MAINMENU"),
                 Globals.getLeave(),
                 Globals.getEnter()
             );
             Globals.isPaused = true;
         }
        
        
         //testing
         if (input.isKeyDown(Input.KEY_T))
         {
             sbg.enterState(
                 Globals.states.get("GAMEOVER"),
                 Globals.getLeave(),
                 Globals.getEnter()
             );
         }
         
         
         // Handle setting keys
         if (input.isKeyDown(Input.KEY_F) && input.isKeyDown(Input.KEY_LCONTROL))
         {
             boolean newStatus = !Globals.agc.isFullscreen();
             Globals.agc.setFullscreen(newStatus);
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
        // Draw camera's view of map
        cam.drawMap();
        cam.translateGraphics();
          
        // Draw player
        alien.drawPlayer(playerX, playerY);
        
        //Top left info
        // facout out to draw settings
        int infoX = playerX - 50;
        int infoY = playerY - 50;
        
        if (Globals.showMemUse)
        {
            long freeMem = Runtime.getRuntime().freeMemory();
            long totalMem = Runtime.getRuntime().totalMemory();
            long memoryUsed = (totalMem-freeMem)/1000000;
            g.setColor(Color.white);
            g.drawString("Memory Usage: " + memoryUsed + " MB", infoX, infoY);
        }
        
        if (Globals.showCoords)
        {
            g.setColor(Color.white);
            g.drawString("pX: " + playerX + " , pY: " + playerY, infoX, infoY + 20);
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