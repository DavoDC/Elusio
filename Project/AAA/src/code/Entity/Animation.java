
package code.Entity;

import java.awt.image.BufferedImage;

/** 
 * This class models an animation consisting of frames
 * 
 * setFrames() initializes fields
 * update() updates fields
 * getImage() gives you the current frame
 *
 * @author David C
 */
public class Animation {
	
        //Animation frame fields
	private BufferedImage[] frames;
	private int currentFrame;
	private int numFrames;
	
        //Time count : Acts as an "internal clock"
	private int count;
        
        //Time delay : Controls interval between frame switches
	private int delay;
	
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
     * Mutator for current frame
     * @param cf
     */
   // public void setCurFrame(int cf) { currentFrame = cf; }


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
                
                count = 0;
                delay = 2;
                
		timesPlayed = 0;	
	}
    
    
    /**
     * Update animation
     * 
     * - Increase time count
     * 
     * - If count == delay, go to next frame and reset count
     * 
     * - If on last frame, set curFrame back to 0 and increase times played
     *  
     */
    public void update() 
        {
		
		if(delay == -1) return;
		
		count++;
		
		if(count == delay) 
                {
			currentFrame++;
			count = 0;
		}
		if(currentFrame == numFrames) 
                {
			currentFrame = 0;
			timesPlayed++;
		}
		
	}
	
}