package states.special;

import main.Globals;
import org.newdawn.slick.Color;
import states.base.AutoState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Models scenes where rift travel is occurring
 *
 * @author David
 */
public class Rift extends AutoState {

    // Ship variables
    private Image ship;
    private float shipX;
    private float shipY;
    private final float movFactor = 5;
    private final int boundsFactor = 100;

    // Control list
    private String[] contList;

    /**
     * Initialize state
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

        // Initialize ship image and position
        ship = new Image(Globals.getFP("alienship.png"));
        ship = ship.getScaledCopy(0.7f);
        ship.setRotation(90);
        shipX = Globals.screenW / 2 - (ship.getWidth() / 4 + 47);
        shipY = (float) (Globals.screenH * 0.7);

        // Initialize control list
        contList = new String[]{"Up", "Down", "Left", "Right",
            "Back", "Screenshot"};
    }

    /**
     * Update state
     *
     * @param container
     * @param game
     * @param delta
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game,
            int delta) throws SlickException {

        // Handle input
        Globals.conServer.handleInput(contList);
    }

    /**
     * Render state
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game,
            Graphics g) throws SlickException {

        // Draw particles on bottom
        Globals.partServer.drawRiftParticles();

        // Draw spaceship 
        g.drawImage(ship, shipX, shipY);

        // Draw popup on top
        Globals.popStore.renderCurPopup(g);

        // Draw info
        Globals.drawRuntimeInfo(0, 0, Color.white);
    }

    /**
     * Move the ship in a given direction
     *
     * @param dir Direction of motion
     */
    public void moveShip(String dir) {

        // Adjust direction string
        dir = dir.toLowerCase();

        // Change position 
        switch (dir) {
            case "up":
                shipY -= movFactor;
                break;
            case "down":
                shipY += movFactor;
                break;
            case "left":
                shipX -= movFactor;
                break;
            case "right":
                shipX += movFactor;
                break;
        }

        // Wrap around so the screen boundaries cannot be breached
        shipX = shipX % (Globals.screenW - boundsFactor);
        shipY = shipY % (Globals.screenH - boundsFactor);
        if (shipX < 0) {
            shipX = Globals.screenW - boundsFactor;
        } else if (shipY < 0) {
            shipY = Globals.screenH - boundsFactor;
        }
    }
}
