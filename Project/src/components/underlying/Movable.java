package components.underlying;

import base.Globals;

/**
 * Models a movable object
 *
 * @author David
 */
public abstract class Movable {

    // Position
    private int xPos;
    private int yPos;

    // Size 
    private final int width;
    private final int height;
    private final double scale;

    // Movement speed
    private float movSpeed;

    // Visibility
    private boolean visible;

    // Last direction of movement
    private String lastDirection;

    // Movement sound
    private final String movSoundName;

    /**
     * Create a movable object
     *
     * @param startX
     * @param startY
     * @param width
     * @param height
     * @param movSpeed
     * @param scale
     * @param defVis Default visibility
     * @param movSoundName
     */
    public Movable(int startX, int startY, int width, int height,
            double scale, float movSpeed,
            boolean defVis, String movSoundName) {

        // Save position
        this.xPos = startX;
        this.yPos = startY;

        // Save size
        this.width = width;
        this.height = height;
        this.scale = scale;

        // Save movement speed
        this.movSpeed = (float) movSpeed;

        // Save visibility
        visible = defVis;

        // Set last direction
        lastDirection = "up";

        // Save movement sound name
        this.movSoundName = movSoundName;
    }

    /**
     * Move the object in a given direction
     *
     * @param dir
     */
    public void move(String dir) {

        // Alter string
        dir = dir.toLowerCase();

        // Process movement type
        String movType = procMovType(dir);

        // If direction is not valid
        if (movType == null) {

            // Throw error
            throw new IllegalArgumentException("InvDirString: " + dir);
        }

        // Save direction
        lastDirection = dir;

        // Play movement sound
        Globals.audioServer.playSound(movSoundName);

        // Amplify speed
        int trueSpeed = (int) (Math.round(10 * movSpeed));

        // Process position change
        procPosChange(movType.charAt(0), movType.charAt(1), trueSpeed);
    }

    /**
     * Get the type (axis and sign) of movement occurring. Returns null if
     * movement is invalid
     *
     * @param dir
     * @return sign char then axis char
     */
    public String procMovType(String dir) {

        // Create holder
        String result = "";

        // Get direction booleans
        boolean up = dir.equals("up");
        boolean down = dir.equals("down");
        boolean left = dir.equals("left");
        boolean right = dir.equals("right");

        // If all are false
        if (up == false && down == false
                && left == false && right == false) {
            return null;
        }

        // Add sign
        if (up || left) {
            result += "-";
        } else {
            result += "+";
        }

        // Add axis 
        if (up || down) {
            result += "y";
        } else {
            result += "x";
        }

        // Return result
        return result;
    }

    /**
     * Change X position by given value. Prevents negatives
     *
     * @param change
     */
    public void adjustX(int change) {

        // If change does not result in negative vaue
        if (!(xPos + change < 0)) {

            // Apply change
            xPos += change;
        }
    }

    /**
     * Change Y position by given value. Prevents negatives
     *
     * @param change
     */
    public void adjustY(int change) {

        // If change does not result in negative vaue
        if (!(yPos + change < 0)) {

            // Apply change
            yPos += change;
        }
    }

    /**
     * Process a position change of the object
     *
     * @param sign
     * @param axis
     * @param trueSpeed
     */
    public abstract void procPosChange(char sign, char axis, int trueSpeed);

    /**
     * Set movement speed
     *
     * @param newSpeed
     */
    public void setMovSpeed(float newSpeed) {
        movSpeed = newSpeed;
    }

    /**
     * Set whether the player is visible or not
     *
     * @param newStatus
     */
    public void setVisible(boolean newStatus) {
        visible = newStatus;
    }

    /**
     * Set X position
     *
     * @param newX
     */
    public void setX(int newX) {
        xPos = newX;
    }

    /**
     * Set Y position
     *
     * @param newY
     */
    public void setY(int newY) {
        yPos = newY;
    }

    /**
     * Get scaled width
     *
     * @return
     */
    public int getScaledWidth() {
        return (int) (scale * width);
    }

    /**
     * Get scaled height
     *
     * @return
     */
    public int getScaledHeight() {
        return (int) (scale * height);
    }

    /**
     * Get last movement direction
     *
     * @return
     */
    public String getLastDir() {
        return lastDirection;
    }

    /**
     * Get movement speed
     *
     * @return
     */
    public float getMovSpeed() {
        return movSpeed;
    }

    /**
     * Return true if visible
     *
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Get width
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get X position
     *
     * @return
     */
    public int getX() {
        return xPos;
    }

    /**
     * Get Y position
     *
     * @return
     */
    public int getY() {
        return yPos;
    }

}
