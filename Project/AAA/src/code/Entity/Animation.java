
package code.Entity;

import java.awt.image.BufferedImage;

/** 
 * Entity objects contain an animation object
 * 
 * This class models an animation consisting of frames
 * 
 * setFrames() initializes fields
 * update() updates fields
 * getImage() gives you the current frame
 *
 * @author David C
 */
public class Animation 
{
	
    
        //Animation frame fields
	private BufferedImage[] frames;
	private int currentFrame;
	private int numFrames;
	
        //Time elapsed
	private int ticks;
        
        //The time after which the animation will go to the next frame
	private int delay;
	
        // The number of times the animation has played
	private int timesPlayed;
	
        
    /**
     * Constructor for animation objects
     * Sets timesPlayed to 0
     */
    public Animation() 
        {
		timesPlayed = 0;
	}
	
    
    /**
     * Mutator for delay, which controls the interval between frame progressions
     * @param del
     */
    public void setDelay(int del) { delay = del; }

    
    /**
     * @return The frame the animation is currently on 
     */
    public BufferedImage getImage() { return frames[currentFrame]; }

    
    /**
     * @return True if times played is at least 1 or more
     */
    public boolean hasPlayedOnce() { return timesPlayed > 0; }
    
    
     /**
     * Initialize all fields
     * Animation frames are inputted
     * All other values are set to preset values
     * @param frames
     */
    public void setFrames(BufferedImage[] frames) 
        {
		this.frames = frames;
		currentFrame = 0;
		numFrames = frames.length;
                
                ticks = 0;
                delay = 2;
                
		timesPlayed = 0;	
	}
    
    
    /**
     * Update animation
     * - Increase ticks
     * - If delay has elapsed, go to next frame and reset ticks
     * - If on last frame, set curFrame back to 0 and increase times played
     *  
     */
    public void update() 
        {
		
		if(delay == -1) return;
		
		ticks++;
		
		if(ticks == delay) 
                {
			currentFrame++;
			ticks = 0;
		}
		if(currentFrame == numFrames) 
                {
			currentFrame = 0;
			timesPlayed++;
		}
		
	}
	
}