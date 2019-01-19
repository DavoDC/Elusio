package code.Utility;

import code.MainGame;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.SlickException;

/**
 * Stores background images for access
 * 
 * @author David
 */
public class BGBank 
{
    /**
     * Stock of images
     */
    private ArrayList<BigImage> backgrounds;
    
    /**
     * Number of images (determined manually)
     */
    private final int BG_COUNT = 17;
    
    
    /**
     * Constructor for BGBank objects
     * Images must be like so: bg1.png, bg2.png ...
     */
    public BGBank() throws SlickException
    {
        // Initialise array
        backgrounds = new ArrayList<>();
        
        // Add every background to the array        
        for (int i = 1; i <= BG_COUNT; i++)
        {
            String imgPath = "res/backgrounds/bg" + i + ".png";
            BigImage bg = new BigImage(imgPath);
            bg = (BigImage) MainGame.adjustImage(bg);
            backgrounds.add(bg);
        }

    }
    
    /**
     * Get a random background image
     * @return Background image
     */
    public BigImage getRandomBG() throws SlickException
    {
        // Get random int within range
        Random rng = new Random();
        int ranIndex = rng.nextInt(backgrounds.size()-1);
        
        // Return random background
        return ( backgrounds.get(ranIndex) );
    }
    
    
}


