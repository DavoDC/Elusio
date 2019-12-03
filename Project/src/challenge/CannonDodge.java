package challenge;

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
public class CannonDodge extends BasicGameState {

    // The cannon balls
    private ArrayList<Circle> balls;

    // The player
    private Circle person;

    // The time elapsed
    private int timeElapsed;

    // Represents how much the player has progressed
    private String progress;

    // The higher this value, the harder the game
    private int hardness;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return 200;
    }

    /**
     * Called when the game starts to initialize the state
     *
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        balls = new ArrayList<>();
        person = new Circle(400, 500, 15);
        timeElapsed = 0;
        hardness = 2;
    }

    /**
     * Updates logic and internal variables every game loop
     *
     * @param container
     * @param game
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        // Increase time passed
        timeElapsed += 20;

        // Increase hardness periodically
        if (timeElapsed % 26000 == 0) {
            hardness += 2;
        }

        // Update progress
        double progressVal = Math.round(((double) timeElapsed / 60000) * 100);
        progress = (int) progressVal + " %";

        // Check if passed
        if (progress.contains("100")) {
            game.enterState(2);
        }

        // Make "person" move with mouse
        Input input = container.getInput();
        person.setCenterX(input.getMouseX());
        person.setCenterY(input.getMouseY());

        // Spawn random balls
        int rand = (int) (Math.random() * 10);

        if (rand > (10 - hardness)) {
            int ranX = (int) (800 * Math.random());
            balls.add(new Circle(ranX, -10, 20));
        }

        // Move balls
        // For all balls
        for (Circle c : balls) {

            // Calculate speed using time
            int speed = (int) (delta / 4f);

            // Move the ball
            c.setCenterY(c.getCenterY() + speed);
        }

        // Remove balls when they exit
        // Must recurse backwards!
        for (int i = balls.size() - 1; i >= 0; i--) {
            Circle c = balls.get(i);
            if (c.getCenterY() > 605) {
                balls.remove(i);
            }
        }

        // End game if you collide
        // For all balls
        for (Circle ball : balls) {

            // If person is touchin ball
            if (person.intersects(ball)) {

                // Enter game over state
                game.enterState(5);
            }
        }

    }

    /**
     * Renders the graphics every game loop
     *
     * @param container
     * @param game
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        // Draw background
        //Image bg = deck image
        //bg = bg.getScaledCopy(800, 600);
        //g.drawImage(bg, 0, 0);
        //
        // 
        // Draw "person" outline and fill
        g.setColor(Color.green);
        g.draw(person);
        g.setColor(Color.green);
        g.fill(person);

        // Draw balls
        g.setColor(Color.black);
        for (Circle c : balls) {
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
