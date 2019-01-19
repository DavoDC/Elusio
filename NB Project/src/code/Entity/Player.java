package code.Entity;


import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 * @author David
 */
public class Player 
{

    private SpriteSheet ss;
    private Animation anim;
    private int animSpeed;    
    
    private float movementSpeed; 
    
    private int startXpos;
    private int startYpos;
    
    private int playerW;
    private int playerH;
    
    public Player()
    {
      playerW = 33;
      playerH = 48;
        
      try 
      { 
          ss = new SpriteSheet("res/sprites/player/walking.png", playerW , playerH);
      } 
      catch (SlickException ex) { }
        
      animSpeed = 500;
      anim = new Animation(ss, animSpeed);

      startXpos = 60;
      startYpos = 60;
       
      movementSpeed = 0.25f;
    }

    
    public int getStartX()
    {
        return startXpos;
    }
    
    
    public int getStartY()
    {
        return startYpos;
    }
    
    public float getMovSpeed()
    {
        return movementSpeed;
    }
    
    
    public void updateAnimation(int delta) 
    {
        anim.update(delta);
    }

    
    public void startAnim(String dir) 
    {
         anim.start(); 
         
         String frameConfig = "n0";
         
         switch(dir)
         {
             case "up": frameConfig = "n9n10n11"; // back
             break;
             case "down": frameConfig = "n0n1n2"; //front 
             break;
             case "left": frameConfig = "n3n4n5"; //left
             break;
             case "right": frameConfig = "n6n7n8"; //right
             break;         
         }
         
         configureFrames(frameConfig);
    }


    
    
    /**
    * Adjusts an animation to only use the referenced frames
    * Frame numbers must start with "n"
    * @param anim
    * @param configS 
    */
   public void configureFrames(String configS)
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
            anim.setDuration(i, animSpeed);
        }
        else  //If not, deactivate
        {
            anim.setDuration(i, 0);
        } 
    }  
   }

   
   public void stopAnim() { anim.stop(); }
    
   
   public void drawPlayer(int pX, int pY)
   {
       int largerW = (int) (playerW*1.5);
       int largerH = (int) (playerH*1.5);
       
       anim.draw((int) pX, (int) pY, largerW, largerH);
   }
    
    
}
