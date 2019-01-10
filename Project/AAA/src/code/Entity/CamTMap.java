/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code.Entity;


import java.awt.geom.Point2D;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author David
 */
public class CamTMap 
{
    
    

/**
 * the map used for our scene
 */
protected TiledMap map;

/**
 * the number of tiles in x-direction (width)
 */
protected int numTilesX;

/**
 * the number of tiles in y-direction (height)
 */
protected int numTilesY;

/**
 * the height of the map in pixel
 */
protected int mapHeight;

/**
 * the width of the map in pixel
 */
protected int mapWidth;

/**
 * the width of one tile of the map in pixel
 */
protected int tileWidth;

/**
 * the height of one tile of the map in pixel
 */
protected int tileHeight;

/**
 * the GameContainer, used for getting the size of the GameCanvas
 */
protected GameContainer gc;

/**
 * the x-position of our "camera" in pixel
 */
protected float cameraX;

/**
 * the y-position of our "camera" in pixel
 */
protected float cameraY;


/**
 * the offset which is necessary to tell the mouse where the camera is at
 */
public static float MOUSE_X_OFFSET;
public static float MOUSE_Y_OFFSET;

protected Point2D.Float currentCenterPoint = new Point2D.Float(0, 0);

/**
 * Create a new camera
 *
 * @param gc the GameContainer, used for getting the size of the GameCanvas
 * @param map the TiledMap used for the current scene
 */
public CamTMap(GameContainer gc, TiledMap map) {
    this.map = map;

    this.numTilesX = map.getWidth();
    this.numTilesY = map.getHeight();

    this.tileWidth = map.getTileWidth();
    this.tileHeight = map.getTileHeight();

    this.mapWidth = this.numTilesX * this.tileWidth; 
    this.mapHeight = this.numTilesY * this.tileHeight;

    this.gc = gc;
}

public int getCamX()
{
    return (int) cameraX;
}

public int getCamY()
{
    return (int) cameraX;
}


/**
 * "locks" the camera on the given coordinates. The camera tries to keep the
 * location in it's center.
 *
 * @param x the real x-coordinate (in pixel) which should be centered on the
 * screen
 * @param y the real y-coordinate (in pixel) which should be centered on the
 * screen
 */
public Point2D.Float centerOn(float x, float y) {
    //try to set the given position as center of the camera by default
    cameraX = x - gc.getWidth() / 2;
    cameraY = y - gc.getHeight() / 2;

    //if the camera is at the right or left edge lock it to prevent a black bar
    if (cameraX < 0) {
        cameraX = 0;
    }
    if (cameraX + gc.getWidth() > mapWidth) {
        cameraX = mapWidth - gc.getWidth();
    }

    //if the camera is at the top or bottom edge lock it to prevent a black bar
    if (cameraY < 0) {
        cameraY = 0;
    }
    if (cameraY + gc.getHeight() > mapHeight) {
        cameraY = mapHeight - gc.getHeight();
    }

    MOUSE_X_OFFSET = cameraX;
    MOUSE_Y_OFFSET = cameraY;

    currentCenterPoint.setLocation(cameraX, cameraY);
    return currentCenterPoint;
}

/**
 * "locks" the camera on the center of the given Rectangle. The camera tries
 * to keep the location in it's center.
 *
 * @param x the x-coordinate (in pixel) of the top-left corner of the
 * rectangle
 * @param y the y-coordinate (in pixel) of the top-left corner of the
 * rectangle
 * @param height the height (in pixel) of the rectangle
 * @param width the width (in pixel) of the rectangle
 */
public void centerOn(float x, float y, float height, float width) {
    this.centerOn(x + width / 2, y + height / 2);
}

/**
 * "locks the camera on the center of the given Shape. The camera tries to
 * keep the location in it's center.
 *
 * @param shape the Shape which should be centered on the screen
 */
public void centerOn(Shape shape) {
    this.centerOn(shape.getCenterX(), shape.getCenterY());
}

/**
 * draws the part of the map which is currently focused by the camera on
 * the screen
 */
public void drawMap() //removedlayer
{
    this.drawMap(0, 0);   //removed layer
}

/**
 * draws the part of the map which is currently focused by the camera on
 * the screen.
 * You need to draw something over the offset, to prevent the edge of the
 * map to be displayed below it
 * Has to be called before Camera.translateGraphics() !
 *
 * @param offsetX the x-coordinate (in pixel) where the camera should start
 * drawing the map at
 * @param offsetY the y-coordinate (in pixel) where the camera should start
 * drawing the map at
 */
public void drawMap(int offsetX, int offsetY) //layer removed here
{
    //calculate the offset to the next tile (needed by TiledMap.render())
    int tileOffsetX = (int) -(cameraX % tileWidth);
    int tileOffsetY = (int) -(cameraY % tileHeight);

    //calculate the index of the leftmost tile that is being displayed
    int tileIndexX = (int) (cameraX / tileWidth);
    int tileIndexY = (int) (cameraY / tileHeight);


    //finally draw the section of the map on the screen
    
    map.render(
            tileOffsetX + offsetX,  //x loc to render
            tileOffsetY + offsetY, //y loc to render
            tileIndexX, //x tile to start rendering
            tileIndexY, //y tile to start rendering
            (gc.getWidth() - tileOffsetX) / tileWidth + 50, //width to render //1 to 50,
            (gc.getHeight() - tileOffsetY) / tileHeight + 50, //height to render /
            //layer, 
            false); 
    
  
    
}

/**
 * Translates the Graphics-context to the coordinates of the map - now
 * everything can be drawn with it's NATURAL coordinates.
 */
public void translateGraphics() {
    gc.getGraphics().translate(-cameraX, -cameraY);
}

/**
 * Reverses the Graphics-translation of Camera.translatesGraphics(). Call
 * this before drawing HUD-elements or the like
 */
public void untranslateGraphics() {
    gc.getGraphics().translate(cameraX, cameraY);
}


    
}
