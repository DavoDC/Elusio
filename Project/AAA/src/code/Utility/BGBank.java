package code.Utility;

import code.Main.MainGame;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Image;
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
    private ArrayList<Image> backgrounds;
    
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
            Image bg = new Image(imgPath);
            bg = MainGame.adjustImage(bg);
            backgrounds.add(bg);
        }

    }
    
    /**
     * Get a random background image
     * @return Background image
     */
    public Image getRandomBG() throws SlickException
    {
        // Get random int within range
        Random rng = new Random();
        int ranIndex = rng.nextInt(backgrounds.size()-1);
        
        // Return random background
        return ( backgrounds.get(ranIndex) );
    }
    
    
    
    
}


