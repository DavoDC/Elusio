package components.buttons;

import main.Globals;
import components.helpers.FontServer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
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

    // Alert status
    private boolean alertOn;

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

        // Initialize with alert off
        alertOn = false;

        // Turn off alert after clicks
        super.addListener(
                (AbstractComponent source)
                -> {
            alertOn = false;
        }
        );
    }

    /**
     * Change the position of the button
     *
     * @param x coordinate
     * @param y coordinate
     */
    private void setPosition(float x, float y) {

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
        drawImage(g);
        drawText(g);
        drawAlert(g);
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

            // Load new image
            Image newImg = new Image(resLoc);

            // Adjust new image
            int oldW = img.getWidth();
            int oldH = img.getHeight();
            newImg = newImg.getScaledCopy(oldW, oldH);

            // Save over old image
            img = newImg;

            // Update image
            updateImage();

        } catch (SlickException e) {
            System.err.println("Image loading failed");
        }

    }

    /**
     * Update button image
     */
    private void updateImage() {
        setNormalImage(img);
        setMouseOverImage(img);
        setMouseDownImage(img);
    }

    /**
     * Draw a red exclamation mark near the button
     *
     * @param g
     */
    private void drawAlert(Graphics g) {

        // Only continue if alert is on
        if (!alertOn) {
            return;
        }

        // Load image if not already done so
        if (Globals.alertMark == null) {
            try {
                Globals.alertMark = new Image(Globals.alertRes);
                int newSide = (int) shape.getHeight() / 2;
                Globals.alertMark = Globals.alertMark.getScaledCopy(newSide, newSide);
            } catch (SlickException e) {
            }
        }

        // Calculate position of alert
        int imageW = Globals.alertMark.getWidth();
        float imgX = shape.getX() + imageW - imageW / 2 + 5;
        float imgY = shape.getY() + shape.getHeight() + 5;

        // Add jiggle factor
        if (Math.random() < 0.15) {
            //imgX += Math.random()*5;
            imgY += Math.random() * 9;
        }

        // Draw alert
        g.drawImage(Globals.alertMark, imgX, imgY);
    }

    /**
     * Switch on the alert
     */
    public void activateAlert() {
        alertOn = true;
    }

}
