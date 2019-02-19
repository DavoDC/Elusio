package Components.Structures;

import Entity.Base.Entity;
import Entity.Enemy.Enemy;
import Entity.Item.Item;
import Main.Globals;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 *
 * @author David
 */
public class Player
{

    // Starting location of player
    private int xPos;
    private int yPos;

    // Dimensions of player
    private double scale; // Size factor
    private int width;
    private int height;

    // Movement speed of player
    private float movSpeed;

    // Spritesheet holding all frames of player
    private SpriteSheet sprites;

    // Animation of player moving
    private int animSpeed;
    private Animation anim;

    // Entities encountered
    private ArrayList<Item> inv;
    private ArrayList<Enemy> enemies;

    
    /**
     * Create a player with preset values
     */
    public Player()
    {
        // Initialise fields
        xPos = 300;
        yPos = 300;

        scale = 1.5;
        width = 33;
        height = 48;

        movSpeed = 0.369f; //test increased speed

        try
        {
            sprites = new SpriteSheet(Globals.playerSprRes, width, height);
        }
        catch (SlickException e)
        {
        }

        animSpeed = 500;
        anim = new Animation(sprites, animSpeed);

        inv = new ArrayList<>();
        enemies = new ArrayList<>();

        // Adjust animation
        anim.stop();  // Prevents animation from starting on its own
        anim.setCurrentFrame(1); // Puts animation on "standing still" frame

    }

    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }

    public void adjustX(int change)
    {
        // Adjust x
        xPos += change;

        // Prevent negative values
        if (xPos < 0)
        {
            xPos -= change;
        }
    }

    public void adjustY(int change)
    {
        // Adjust x
        yPos += change;

        // Prevent negative values
        if (yPos < 0)
        {
            yPos -= change;
        }
    }

    public float getMovSpeed()
    {
        return movSpeed;
    }

    public void updateAnimation(int delta)
    {
        anim.update(delta);
    }

    /**
     * Starts the walking animation in a given direction
     *
     * @param dir Direction of motion
     */
    public void startAnim(String dir)
    {
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
     * Adjusts an animation to only use the referenced frames Frame numbers are
     * prefixed by "n" Example: "n0n1n2" (for first three frames)
     *
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
            if (curNo.length() != 0)
            {
                config.add(Integer.parseInt(curNo));
            }
        }

        // Process config
        for (int i = 0; i < frameCount; i++)
        {

            //If inputted, activate frame
            if (config.contains(i))
            {
                anim.setDuration(i, animSpeed);
            }
            else //If not, deactivate
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
     * Draw the player at the coordinates Enlarges the player sprite beforehand
     *
     * @param pX
     * @param pY
     */
    public void drawPlayer(int pX, int pY)
    {
        int largerW = (int) (width * scale);
        int largerH = (int) (height * scale);

        anim.draw((int) pX, (int) pY, largerW, largerH);
    }

    /**
     * Add an entity
     * @param ent
     */
    public void addToEntities(Entity ent)
    {
        if (ent instanceof Item)
        {
            inv.add((Item) ent);
        }
        else if (ent instanceof Enemy)
        {
            enemies.add((Enemy) ent);
        }
    }

    /**
     * Get the item that was grabbed most recently
     * @return
     */
    public Item getLastAddedItem()
    {
        int pos = inv.size() - 1;
        return inv.get(pos);
    }
    
    /**
     * Get the item that was grabbed most recently
     * @return
     */
    public Enemy getLastEnemy()
    {
        int pos = enemies.size() - 1;
        return enemies.get(pos);
    }



}
