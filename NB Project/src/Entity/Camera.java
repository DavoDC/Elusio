package Entity;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {

   /** The map used for our scene */
   protected TiledMap map;
   
   /** The number of tiles that fit on the x axis */
   protected int numTilesX;
   
   /** The number of tiles that fit on the y axis */
   protected int numTilesY;
   
   /** The height of the map in pixels */
   protected int mapHeight;
   
   /** The width of the map in pixels */
   protected int mapWidth;
   
   /** The width of one tile of the map in pixels */
   protected int tileWidth;
   
   /** The height of one tile of the map in pixels */
   protected int tileHeight;
   
   /** The GameContainer, used for getting the size of the GameCanvas */
   protected GameContainer gc;

   /** The x-position of our "camera" in pixel */
   protected float cameraX;
   
   /** The y-position of our "camera" in pixel */
   protected float cameraY;
   
   /**
    * Create a new camera
    * 
    * @param gc the GameContainer, used for getting the size of the GameCanvas
    * @param map the TiledMap used for the current scene
    */
   public Camera(GameContainer gc, TiledMap map) {
      this.map = map;
      
      this.numTilesX = map.getWidth();
      this.numTilesY = map.getHeight();
      
      this.tileWidth = map.getTileWidth();
      this.tileHeight = map.getTileHeight();
      
      this.mapHeight = this.numTilesX * this.tileWidth;
      this.mapWidth = this.numTilesY * this.tileHeight;
      
      this.gc = gc;
   }
   
   /**
    * "Locks" the camera on the given coordinates. 
    * The camera tries to keep the location in it's center.
    * 
    * @param x the real x-coordinate (in pixel) which should be centered on the screen
    * @param y the real y-coordinate (in pixel) which should be centered on the screen
    */
   public void centerOn(float x, float y) {
      //Try to set the given position as center of the camera
      cameraX = x - gc.getWidth()  / 2;
      cameraY = y - gc.getHeight() / 2;
      
      //If the camera is at the left or right edge, lock it to prevent a black bar
      if(isTooLeft()) cameraX = 0;
      if(isTooRight()) cameraX = mapWidth - gc.getWidth();
      
      //If the camera is at the top or bottom edge, lock it to prevent a black bar
      if(isTooUp()) cameraY = 0;
      if(isTooDown()) cameraY = mapHeight - gc.getHeight();
   }
   
   public boolean isTooLeft() { return (cameraX < 0); }
   public boolean isTooRight() { return (cameraX + gc.getWidth() > mapWidth); }
   public boolean isTooUp(){ return (cameraY < 0); }
   public boolean isTooDown() { return (cameraY + gc.getHeight() > mapHeight); }
   
   /**
    * "locks" the camera on the center of the given Rectangle. 
    * The camera tries to keep the location in it's center.
    * 
    * @param x the x-coordinate (in pixel) of the top-left corner of the rectangle
    * @param y the y-coordinate (in pixel) of the top-left corner of the rectangle
    * @param height the height (in pixel) of the rectangle
    * @param width the width (in pixel) of the rectangle
    */
   public void centerOn(float x, float y, float height, float width) {
      this.centerOn(x + width / 2, y + height / 2);
   }

   /**
    * "locks the camera on the center of the given Shape. 
    * The camera tries to keep the location in it's center.
    * @param shape the Shape which should be centered on the screen
    */
   public void centerOn(Shape shape) {
      this.centerOn(shape.getCenterX(), shape.getCenterY());
   }
   
   /**
    * draws the part of the map which is currently focused by the camera on the screen
    */
   public void drawMap() {
      this.drawMap(0, 0);
   }
   
   /**
    * Draws the part of the map which is currently focused by the camera on the screen.
    * Has to be called before Camera.translateGraphics() !
    * @param offsetX the x-coordinate (in pixel) where the camera should start drawing the map at
    * @param offsetY the y-coordinate (in pixel) where the camera should start drawing the map at
    */
   
   public void drawMap(int offsetX, int offsetY) {
       //calculate the offset to the next tile (needed by TiledMap.render())
       int tileOffsetX = (int) - (cameraX % tileWidth);
       int tileOffsetY = (int) - (cameraY % tileHeight);
       
       //calculate the index of the leftmost tile that is being displayed
       int tileIndexX = (int) (cameraX / tileWidth);
       int tileIndexY = (int) (cameraY / tileHeight);
       
       //finally draw the section of the map on the screen
       map.render(   
             tileOffsetX + offsetX, 
             tileOffsetY + offsetY, 
             tileIndexX,  
             tileIndexY,
                (gc.getWidth()  - tileOffsetX) / tileWidth  + 1,
                (gc.getHeight() - tileOffsetY) / tileHeight + 1);
   }
   
   /**
    * Translates the Graphics-context to the coordinates of the map
    * Changes things back to their NATURAL coordinates.
    */
   public void translateGraphics() {
      gc.getGraphics().translate(-cameraX, -cameraY);
   }
   /**
    * Reverses the Graphics-translation of Camera.translatesGraphics().
    * Call this before drawing HUD-elements or the like
    */
   public void untranslateGraphics() {
      gc.getGraphics().translate(cameraX, cameraY);
   }
   
}