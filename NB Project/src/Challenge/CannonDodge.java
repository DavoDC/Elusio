package Challenge;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A mini-game where you dodge cannon balls
 *
 * @author David
 */
public class CannonDodge extends BasicGameState
{

    // The cannon balls
    private ArrayList<Circle> balls;

    // The player
    private Circle person;

    // The time elapsed
    private int timeElapsed;

    // Represents
    private String progress;

    // The higher this value, the harder the game
    private int hardness;

    /**
     * Used to identify states and switch to them
     *
     * @return id
     */
    @Override
    public int getID()
    {
        return 200;
    }

    /**
     * This is only called when the game starts Used to load resources Used to
     * initialise the game state.
     *
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException
    {
        balls = new ArrayList<>();
        person = new Circle(400, 500, 15);
        timeElapsed = 0;
        hardness = 2;

    }

    /**
     * The method is called each game loop to cause your game to update it's
     * logic. This is where you should make objects move. This is also where you
     * should check input and change the state of the game.
     *
     * @param container
     * @param game
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {

        // Increase time passed
        timeElapsed += 20;

        // Increase hardness periodically
        if (timeElapsed % 26000 == 0)
        {
            hardness += 2;
        }

        // Update progress
        double progressVal = Math.round(((double) timeElapsed / 60000) * 100);
        progress = (int) progressVal + " %";

        // Check if passed
        if (progress.contains("100"))
        {
            game.enterState(2);
        }

        // Make "person" move with mouse
        Input input = container.getInput();
        person.setCenterX(input.getMouseX());
        person.setCenterY(input.getMouseY());

        // Spawn random balls
        int rand = (int) (Math.random() * 10);

        if (rand > (10 - hardness))
        {
            int ranX = (int) (800 * Math.random());
            balls.add(new Circle(ranX, -10, 20));
        }

        // Move balls
        for (Circle c : balls)
        {
            //Speed depends on time
            int speed = (int) (delta / 4f);
            c.setCenterY(c.getCenterY() + speed);
        }

        // Remove balls when they exit
        // Must recurse backwards!
        for (int i = balls.size() - 1; i >= 0; i--)
        {
            Circle c = balls.get(i);
            if (c.getCenterY() > 605)
            {
                balls.remove(i);
            }
        }

        // End game if you collide
        for (Circle c : balls)
        {
            if (person.intersects(c))
            {
                //MainGame.mgProgress = progress;
                game.enterState(5);

            }
        }

    }

    /**
     * This method should be used to draw to the screen. All of your game's
     * rendering should take place in this method (or via calls) It is called
     * constantly. Items are constantly redrawn
     *
     * @param container
     * @param game
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {

        // Draw background
        //Image bg = deck image
        //bg = bg.getScaledCopy(800, 600);
        //g.drawImage(bg, 0, 0);
        // Draw "person"
        g.setColor(Color.green);
        g.draw(person);
        g.setColor(Color.green);
        g.fill(person);

        // Draw balls
        g.setColor(Color.black);
        for (Circle c : balls)
        {
            g.draw(c);
            g.setColor(Color.black);
            g.fill(c);
        }

        // Draw memory information
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        g.drawString("Memory Usage: " + memoryUsed + " MB", 10, 25);

        // Draw progress
        g.drawString("Progess:" + progress, 10, 40);

    }

}
