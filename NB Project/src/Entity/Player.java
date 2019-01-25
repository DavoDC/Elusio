package Entity;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 *
 * @author David
 */
public class Player {

     // Starting location of player
    private int startX;
    private int startY;

    // Dimensions of player
    private double scale;
    private int width;
    private int height;
    
    // Movement speed of player
    private float movSpeed;

    // Spritesheet holding all frames of player
    private SpriteSheet sprites;
    private final String spritesLocation = "res/sprites/player/walking.png";
    
    // Animation of player moving
    private int animSpeed;
    private Animation anim;
    
    // 
   
    
    
    public Player() 
    {
        startX = 60;
        startY = 100;
        
        scale = 1.5;
        width = 33;
        height = 48;
        
        movSpeed = 0.25f;

        try { sprites = new SpriteSheet(spritesLocation, width, height); } 
        catch (Exception e) { }

        animSpeed = 500;
        anim = new Animation(sprites, animSpeed);   
        
        // Prevents animation from starting on its own
        anim.stop();
        
        // Puts animation on "standing still" frame
        anim.setCurrentFrame(1);
        
    }
    
    /**
     * 
     * @return 
     */
    public int getStartX() { return startX; }

    public int getStartY() { return startY; }

    public float getMovSpeed() { return movSpeed; }

    public void updateAnimation(int delta) { anim.update(delta); }

    
    /**
     * Starts the walking animation in a given direction
     * @param dir Direction of motion
     */
    public void startAnim(String dir) {
        anim.start();

        String frameConfig = "n0";

        switch (dir) 
        {
            case "up":
                frameConfig = "n9n10n11"; // back
                break;
            case "down":
                frameConfig = "n0n1n2"; //front 
                break;
            case "left":
                frameConfig = "n3n4n5"; //left
                break;
            case "right":
                frameConfig = "n6n7n8"; //right
                break;
        }

        configureFrames(frameConfig);
    }

    /**
     * Adjusts an animation to only use the referenced frames 
     * Frame numbers are prefixed by "n"
     * Example: "n0n1n2" (for first three frames)
     *
     * @param anim
     * @param configS
     */
    public void configureFrames(String configS) 
    {
        // Get frame count 
        int frameCount = anim.getFrameCount();

        //Turn string into ArrayList
        ArrayList<Integer> config = new ArrayList<>();
        for (String curNo : configS.split("n")) 
        {
            if (curNo.length() != 0) {
                config.add(Integer.parseInt(curNo));
            }
        }

        // Process config
        for (int i = 0; i < frameCount; i++) {

            //If inputted, activate frame
            if (config.contains(i)) {
                anim.setDuration(i, animSpeed);
            } else //If not, deactivate
            {
                anim.setDuration(i, 0);
            }
        }
    }

    /**
     * Stop the player's animation
     */
    public void stopAnim()
    {
        anim.stop();
    }

    /**
     * Draw the player at the coordinates
     * Enlarges the player sprite beforehand
     * @param pX
     * @param pY 
     */
    public void drawPlayer(int pX, int pY) 
    {
        int largerW = (int) (width * scale);
        int largerH = (int) (height * scale);

        anim.draw((int) pX, (int) pY, largerW, largerH);
    }

}
