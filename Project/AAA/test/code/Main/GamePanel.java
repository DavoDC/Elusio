package code.Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import code.Manager.GameStateManager;
import code.Manager.Keys;

/**
 * The GamePanel is the drawing canvas.
 * It contains the game loop which keeps the game moving forward.
 * This class is also the one that grabs key events
 * @author David Charkey
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	// Dimensions
	// WIDTH and HEIGHT are for playing area size
	// HEIGHT2 includes the bottom window
        // SCALE determines the window size factor
	public static final int WIDTH = 8*16;
	public static final int HEIGHT = 8*16;
	public static final int HEIGHT2 = HEIGHT + 16;
	public static final int SCALE = 4;
	
	// Game loop stuff
	private Thread thread;
	private boolean running;
	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;
	
	// Drawing stuff
	private BufferedImage image;
	private Graphics2D g;
	
	// Game state manager
	private GameStateManager gsm;
	
	// Constructor
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	// Ready to display
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	// Run new thread
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// Game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = TARGET_TIME - elapsed / 1000000;
			if(wait < 0) wait = TARGET_TIME;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	// Initializes fields
	private void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT2, 1);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager();
	}
	
	// Updates game
	private void update() {
		gsm.update();
		Keys.update();
	}
	
	// Draws game
	private void draw() {
		gsm.draw(g);
	}
	
	// Copy buffer to screen
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null);
		g2.dispose();
	}
	
	// Key event
        @Override
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {Keys.keySet(key.getKeyCode(), true);}
	public void keyReleased(KeyEvent key) {Keys.keySet(key.getKeyCode(), false);}
	
}
