package components.buttons;

import main.Globals;
import components.servers.FontServer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * Models a button - a clickable area that does something
 *
 * @author David
 */
public class Button extends MouseOverArea {

    // Basic components
    private Image img;
    private final Rectangle shape;
    private String label;
    private TrueTypeFont font;

    // Text position
    private float textXoffset;
    private float textYoffset;

    // Enabled status
    private boolean enabled;

    /**
     * Create a button
     *
     * @param image
     * @param shape
     * @param font of button label
     */
    public Button(Image image, Shape shape, TrueTypeFont font) {

        // Call MouseOverArea constructor
        super(Globals.agc, image, shape);

        // Save image, shape and font
        this.img = image;
        this.shape = (Rectangle) shape;
        this.font = font;

        // Set label to default value
        label = "label";

        // Calculate and save offsets
        textXoffset = shape.getWidth() / 5;
        textYoffset = -(shape.getHeight() / 20);

        // Initialize as enabled
        enabled = true;
    }

    /**
     * Set enabled status of button
     *
     * @param newStatus
     */
    public void setEnabled(boolean newStatus) {

        // Update status
        enabled = newStatus;

        // If enabled, accept input
        // If disable, reject input
        super.setAcceptingInput(enabled);
    }

    /**
     * Get enabled status of button
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Change the position of the button
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void setPosition(float x, float y) {

        // Change MouseOverArea location
        super.setLocation(x, y);

        // Change shape location
        shape.setLocation(x, y);
    }

    /**
     * Change button dimensions
     *
     * @param w width
     * @param h height
     */
    public void setDimensions(int w, int h) {

        // Change image size
        img = img.getScaledCopy(w, h);

        // Change shape size
        shape.setSize(w, h);
    }

    /**
     * Change position and dimensions
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void setBounds(int x, int y, int w, int h) {

        // Change position
        setPosition(x, y);

        // Change size
        setDimensions(w, h);

        // Update image
        updateImage();
    }

    /**
     * Change the label of the button
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Retrieve the label of a button
     *
     * @return label string
     */
    public String getLabel() {
        return label;
    }

    /**
     * Draw the button, shifted
     *
     * @param g
     * @param sX
     * @param sY
     */
    public void drawShifted(Graphics g, int sX, int sY) {

        // Move position
        setX(getX() + sX);
        setY(getY() + sY);

        // Draw all of button
        drawFull(g);
    }

    /**
     * Draw the button fully
     *
     * @param g
     */
    public void drawFull(Graphics g) {

        // If enabled
        if (enabled) {

            // Draw image and text
            drawImage(g);
            drawText(g);
        }
    }

    /**
     * Draw the image only
     *
     * @param g
     */
    public void drawImage(Graphics g) {

        // Draw image and MouseOverArea
        super.render(Globals.agc, g);
    }

    /**
     * Draw the text only Action listener not included
     *
     * @param g
     */
    public void drawText(Graphics g) {

        // Calculate text position
        float textX = shape.getX() + textXoffset;
        float textY = shape.getY() + textYoffset;

        // Draw label using font
        font.drawString(textX, textY, label);
    }

    /**
     * Change where text is drawn (Note: Offset is relative to top left hand
     * corner of rectangle)
     *
     * @param xOff
     * @param yOff
     */
    public void changeTextOffset(float xOff, float yOff) {
        textXoffset = xOff;
        textYoffset = yOff;
    }

    /**
     * Set the font of the button
     *
     * @param fontS
     */
    public void setFont(String fontS) {
        TrueTypeFont newFont = FontServer.getFont(fontS);
        this.font = newFont;
    }

    /**
     * Set the button image to the one given
     *
     * @param resLoc
     */
    public void setImageLoc(String resLoc) {

        try {

            // Load and set new image
            setImage(new Image(resLoc), true);

        } catch (SlickException e) {
            System.err.println("Image loading failed");
        }

    }

    /**
     * Set the image of the button, resizing if wanted
     *
     * @param newImg The new image
     * @param resize True means resize
     */
    public void setImage(Image newImg, boolean resize) {

        // If resize wanted
        if (resize) {
            // Resize image
            int oldW = img.getWidth();
            int oldH = img.getHeight();
            newImg = newImg.getScaledCopy(oldW, oldH);
        }

        // Save over old image
        img = newImg;

        // Update image
        updateImage();
    }

    /**
     * Update button image
     */
    private void updateImage() {
        setNormalImage(img);
        setMouseOverImage(img);
        setMouseDownImage(img);
    }

}
