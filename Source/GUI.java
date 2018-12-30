import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Creates the GUI
 *
 * @author David Charkey
 */
public class GUI
{
    // JFrame and Content pane
    private JFrame frame;
    private JPanel contentPane;

    // Loading screen
    private JLabel loading;

    // Location
    private JButton locationL;
    private JButton locImage;
    private JButton locInfo;

    // Movement
    private JButton movementL;
    private JButton dir1;
    private JButton dir2;
    private JButton dir3;
    private JButton dir4;
    private JButton dir5;
    private JButton dir6;

    // Actions
    private JButton actionL;

    private JButton look;
    private JButton inspect;
    private JButton construct;
    private JButton grabAll;
    private JButton dropAll;
    private JButton items;

    //Special fields
    private Menu menu = new Menu();

    private ArrayList<JButton> commands = new ArrayList<>();
    private ArrayList<JButton> directions = new ArrayList<>();

    private static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_W = (int) screen.getWidth();
    public static final int SCREEN_H = (int) screen.getHeight();

    public GUI()
    {     
        // Initialise foundation
        frame = new JFrame();
        contentPane = new JPanel();

        // Modify frame
        frame.setTitle("Alien Aztec Adventure");
        frame.setSize( SCREEN_W - 5, SCREEN_H/2);
        frame.setPreferredSize(frame.getSize());
        frame.setLocation( 2, 2 );
        frame.setIconImage( Game.av.generateImageIcon("icon", "special" , "icon").getImage() );
        frame.setResizable(false);
        frame.setVisible(true);

        // Create temporary loading screen
        loading = new JLabel();
        loading.setLocation(0,0);
        loading.setBackground(Color.lightGray);
        loading.setFont(new Font("algerian",0,40));
        loading.setText("Loading");
        loading.setSize(frame.getSize());
        loading.setHorizontalAlignment(SwingConstants.CENTER);
        loading.setVerticalAlignment(SwingConstants.CENTER);
        loading.setEnabled(true);
        loading.setVisible(true);
        loading.setOpaque(true);

        // Generate and add menu
        frame.setJMenuBar(menu.getMenu());

        // Modify content pane and add it
        contentPane.setSize(frame.getWidth(), frame.getHeight());
        contentPane.setVisible(true);
        contentPane.setLayout(null);  //very important! stops button location reset!
        contentPane.add(loading);
        setBGCol( new Color(140, 250, 100) );
        frame.add(contentPane);

        // Setup labels for each GUI part
        setupLabels();

        // Initialise three GUI parts
        setupLocation();
        setupMovement();
        setupActions();
    }

    private void setupLabels()
    {
        ArrayList<JButton> headings = new ArrayList<>();

        movementL = new JButton();
        headings.add(movementL);

        locationL = new JButton();
        headings.add(locationL);

        actionL = new JButton();
        headings.add(actionL);

        for (JButton but : headings)
        {
            but.removeMouseListener(but.getMouseListeners()[0]);

            but.setHorizontalAlignment(SwingConstants.CENTER);
            but.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(true); //enabled default        
        objects.add(true); //visible default           
        objects.add(Color.black); //text color
        objects.add(Color.white); //button color
        objects.add("Segoe UI"); //font as a string
        objects.add(contentPane);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(0); //xpos
        numbers.add(0); //ypos
        numbers.add( frame.getWidth() / 2); //button width
        numbers.add(40); //button height
        numbers.add(0); //horizontal spacing
        numbers.add(100);  //vertical spacing
        numbers.add(2); //columns
        numbers.add(24); //fontsize

        // Setup grid
        setupButtonGrid(headings , objects, numbers);

        // Specific changes     
        movementL.setText("Movement");
        movementL.setForeground( Color.white);
        movementL.setBackground( new Color(28, 149, 255) );

        locationL.setText("Current Location");
        locationL.setBackground( new Color(255, 230, 5) );

        actionL.setText("Action");
        actionL.setForeground( Color.white);
        actionL.setBackground( new Color(240 , 10 , 10) );
    }

    private void setupLocation()
    {       
        locInfo = new JButton();
        locInfo.setSize( locationL.getWidth() , 30);
        locInfo.setLocation ( locationL.getX(), locationL.getY() + locationL.getHeight() + 5);
        locInfo.setForeground(Color.black);
        locInfo.setBackground( new Color(225, 160, 255) );
        locInfo.setFont( new Font("Corbel", Font.ITALIC, 18) );
        locInfo.removeMouseListener(locInfo.getMouseListeners()[0]);
        locInfo.setVisible(true);
        locInfo.setOpaque(true);
        contentPane.add(locInfo);

        locImage = new JButton();
        locImage.setSize(400, 250);
        locImage.setLocation( locInfo.getX() + locInfo.getWidth()/5 , locInfo.getY() + locInfo.getHeight() + 5);
        locImage.setBackground(Color.white);
        locImage.removeMouseListener(locImage.getMouseListeners()[0]);
        locImage.setVisible(true);
        contentPane.add(locImage);
    }

    private void setupMovement()
    {
        dir1 = new JButton();
        dir1.setName("direction");
        directions.add(dir1);

        dir2 = new JButton();
        dir2.setName("direction");
        directions.add(dir2);

        dir3 = new JButton();
        dir3.setName("direction");
        directions.add(dir3);

        dir4 = new JButton();
        dir4.setName("direction");
        directions.add(dir4);

        dir5 = new JButton();
        dir5.setName("direction");
        directions.add(dir5);

        dir6 = new JButton();
        dir6.setName("direction");
        directions.add(dir6);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(false); //enabled default        
        objects.add(false); //visible default           
        objects.add(Color.black); //text color
        objects.add( Color.cyan) ; //button color
        objects.add("Segoe UI"); //font as a string
        objects.add(contentPane);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(movementL.getX() + 50); //xpos
        numbers.add(movementL.getY() + 50); //ypos
        numbers.add(180); //button width
        numbers.add(30); //button height
        numbers.add(15); //horizontal spacing
        numbers.add(10);  //vertical spacing
        numbers.add(3); //columns
        numbers.add(18); //fontsize

        // Setup grid
        setupButtonGrid(directions , objects, numbers);
    }

    private void setupActions()
    {
        look = new JButton();
        look.setText("Look");
        look.setIcon( Game.av.generateImageIcon("icon", "icons", "look") );
        look.addActionListener(e -> Game.actions.look());
        look.addActionListener(e ->  System.out.println("\n" + "\n" + "\n"));
        look.setName("command");
        commands.add(look);

        inspect = new JButton();
        inspect.setText("Inspect");
        inspect.setIcon( Game.av.generateImageIcon("icon", "icons", "inspect") );
        inspect.addActionListener(e -> Game.actions.inspect());
        inspect.addActionListener(e ->  System.out.println("\n" + "\n" + "\n"));
        inspect.setName("command");
        commands.add(inspect);

        construct = new JButton();
        construct.setText("Construct");
        construct.setIcon( Game.av.generateImageIcon("icon", "icons", "construct") );
        construct.addActionListener(e -> Game.actions.construct());
        construct.addActionListener(e ->  System.out.println("\n" + "\n" + "\n"));
        construct.setName("command");
        commands.add(construct);

        grabAll = new JButton();
        grabAll.setText("Grab All");
        grabAll.addActionListener(e -> Game.actions.grab(new Command("grab", "all")));
        grabAll.setName("command");
        commands.add(grabAll);

        dropAll = new JButton();
        dropAll.setText("Drop All");
        dropAll.addActionListener(e -> Game.actions.drop(new Command("drop", "all")));
        dropAll.setName("command");
        commands.add(dropAll);

        items = new JButton();
        items.setText("Items");
        items.setIcon( Game.av.generateImageIcon("icon", "icons", "items") );
        items.addActionListener(e -> Game.actions.items());
        items.addActionListener(e ->  System.out.println("\n" + "\n" + "\n"));
        items.setName("command");
        commands.add(items);

        for (JButton command : commands)
        {
            contentPane.add(command);
        }

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(false); //enabled default        
        objects.add(false); //visible default           
        objects.add(Color.black); //text color
        objects.add( new Color(250 , 170 , 40) ); //button color
        objects.add("Segoe UI"); //font as a string
        objects.add(contentPane);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(50); //xpos
        numbers.add( actionL.getY() + 50 ); //ypos
        numbers.add(170); //button width
        numbers.add(40); //button height
        numbers.add(30); //horizontal spacing
        numbers.add(10);  //vertical spacing
        numbers.add(3); //columns
        numbers.add(20); //fontsize

        // Setup grid of commands
        setupButtonGrid(commands, objects, numbers);
    }

    public void setBGCol(Color color)
    {
        contentPane.setBackground(color);
    }

    public Color getRandomCol()
    {
        Random rng = new Random();
        float rn = rng.nextInt(777);
        float rm = (float) Math.random() * 0.5f;
        Color bg = Color.getHSBColor(rm, rm, rn);
        return bg;
    }

    /**
     * Setup a grid of buttons with a regular pattern and spacing
     * 
     * @param Buttons In an arraylist
     * 
     * @param Objects
     *  - Common name as a string
     *  - Enabled default 
     *  - Visibled default
     *  - Text color
     *  - Button color
     *  - Font as a string
     *  - Destination of buttons 
     * 
     * @param Numbers 
     *  - The starting position (x and y)
     *  - The button size (width and height)
     *  - The spacing (horizontal and vertical)
     *  - The number of columns
     *  - The fontsize
     */
    public void setupButtonGrid(ArrayList<JButton> buttons, ArrayList<Object> objects, ArrayList<Integer> numbers)
    {
        Boolean enable = (boolean) objects.get(0);
        Boolean visible = (boolean) objects.get(1);
        Color textC = (Color) objects.get(2);
        Color bgC = (Color) objects.get(3);
        String font = (String) objects.get(4);
        JPanel cp = (JPanel) objects.get(5);

        int xpos = numbers.get(0);
        int ypos = numbers.get(1);
        int bW = numbers.get(2);
        int bH = numbers.get(3);
        int xspacing = numbers.get(4);
        int yspacing = numbers.get(5);
        int columns = numbers.get(6);
        int fontsize = numbers.get(7);

        int curxpos = xpos;
        int curypos = ypos;

        int counter = 0;
        for (JButton curButton : buttons)
        {
            counter++;

            curButton.setEnabled(enable);
            curButton.setVisible(visible);
            curButton.setSize(bW, bH);
            curButton.setLocation(curxpos, curypos);

            if (textC != null && bgC != null)
            {
                curButton.setForeground(textC);
                curButton.setBackground(bgC);
            }

            curButton.setFont(new Font(font,0,fontsize));

            cp.add(curButton);

            curxpos += (bW + xspacing);
            if ( (counter%columns) == 0)
            {
                curxpos = xpos;
                curypos += (bH + yspacing);
            }
        }
    }

    /**
     * 
     * Update the GUI to reflect a new location
     * 
     * @param CurrentLocation
     */
    public void update(Room curLocation)
    {        
        // Remove loading screen
        loading.setVisible(false);

        //Make all commands visible and enable them
        for (JButton curButton : commands)
        {
            curButton.setVisible(true);
            curButton.setEnabled(true);
        }

        // Update location image
        locImage.setIcon( Game.av.generateImageIcon("location", "locations", curLocation.getImage() )  );

        // Get exits
        String[] exitA = curLocation.getExitArray();

        // Update direction buttons
        updateButton(exitA[0] , dir1);
        updateButton(exitA[1] , dir2);
        updateButton(exitA[2] , dir3);
        updateButton(exitA[3] , dir4);
        updateButton(exitA[4] , dir5);
        updateButton(exitA[5] , dir6);

        // Update info
        String[] fullDesc = curLocation.getSimpleDesc().split("\\.");  // "." is a special character
        String smallDesc = "";

        if (fullDesc.length == 1) { smallDesc += fullDesc[0]  + "."; }
        else if (fullDesc.length >= 2) { smallDesc += (fullDesc[0] + "." + fullDesc[1]  + "."); }

        locInfo.setText(smallDesc );
    }

    private static void updateButton(String dirS, JButton button)
    {
        // Remove pre-existing action listeners before to prevent multi-action
        for( ActionListener actionl : button.getActionListeners() ) 
        {
            button.removeActionListener( actionl );
        }

        if (dirS != null) // if a direction
        {
            button.setVisible(true);
            button.setEnabled(true);
            button.setText(dirS);

            // Prepare direction string
            String direction = dirS.trim().toLowerCase();

            // Add action
            button.addActionListener(e -> Game.actions.goRoom(new Command("go", direction) ));
            button.addActionListener(e ->  System.out.println("\n" + "\n" + "\n"));
        }
        else if (dirS == null) // if not a direction
        {
            button.setVisible(false);
            button.setEnabled(false);
            button.setText("");
        }
    }

    public void setCommandCol(Color col)
    {
        for (JButton curButton : commands)
        {
            curButton.setBackground(col);
        }
    }
}

