import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Creates the menu
 *
 * @author David Charkey
 */
public class Menu extends JFrame
{
    private JMenuBar menuBar = new JMenuBar();

    // Fields needed for color creation
    private int R = 0;
    private int G = 0;
    private int B = 0;
    private JTextArea rgbVals = new JTextArea( 0 + " , " + 0 + " , " + 0 );

    /**
     * Constructor for objects of class Menu
     */
    public Menu()
    {
        // Font size
        int mainFS = 24; 
        int subFS = mainFS - 4;
        int subsubFS = subFS - 2;

        // Main 1 = Help
        JButton help = new JButton("Help");
        help.setIcon( Game.av.generateImageIcon("icon", "icons", "help") );
        help.addActionListener(e -> showHelpWindow());
        help.setFont(new Font("Calibri",0,mainFS));
        help.setOpaque(false);
        help.setContentAreaFilled(false);
        help.setBorderPainted(false);

        // Main 2 = Settings Menu
        JMenu settingsM = new JMenu("Settings");
        settingsM.setIcon( Game.av.generateImageIcon("icon", "icons", "settings") );
        settingsM.setFont(new Font("Calibri",0, mainFS));

        // Sub 1 in Settings : Gameplay
        JMenu gameplayM = new JMenu("Gameplay");
        gameplayM.setIcon( Game.av.generateImageIcon("icon", "icons", "gameplay") );
        gameplayM.setFont(new Font("Calibri",0, subFS));
        settingsM.add(gameplayM);

        JMenuItem suspense = new JMenuItem("Toggle Suspense");
        suspense.addActionListener(e -> Game.events.toggleSuspense() );
        suspense.setFont(new Font("Corbel",0,subsubFS));
        gameplayM.add(suspense);

        JMenuItem vegan = new JMenuItem("Toggle Vegan Mode");
        vegan.addActionListener(e -> Game.locations.toggleAnimalEvents(false));
        vegan.setFont(new Font("Corbel",0,subsubFS));
        gameplayM.add(vegan);
        
        //  Sub 2 in Settings : Colours
        JMenu colM = new JMenu("Customize Colours");
        colM.setIcon( Game.av.generateImageIcon("icon", "icons", "colours") );
        colM.setFont(new Font("Calibri",0,subFS));
        settingsM.add(colM);

        JMenuItem randCol = new JMenuItem("Randomize");
        randCol.addActionListener(e -> randomCol());
        randCol.setFont(new Font("Corbel",0,subsubFS));
        colM.add(randCol);

        JMenuItem pickCol = new JMenuItem("Choose");
        pickCol.addActionListener(e -> chooseCol());
        pickCol.setFont(new Font("Corbel",0,subsubFS));
        colM.add(pickCol);

        JMenuItem makeCol = new JMenuItem("Create");
        makeCol.addActionListener(e -> createCol());
        makeCol.setFont(new Font("Corbel",0,subsubFS));
        colM.add(makeCol);

        // Sub 3 in Settings : Console
        JMenuItem console = new JMenuItem("Console Options");
        console.setIcon( Game.av.generateImageIcon("icon", "icons", "console") );
        console.addActionListener(e -> showConsoleOpt() );
        console.setFont(new Font("Corbel",0,subFS));
        settingsM.add(console);
        
        // Sub 4 in settings : Music
        JMenuItem music = new JMenuItem("Toggle Music");
        music.setIcon( Game.av.generateImageIcon("icon", "icons", "music") );
        music.addActionListener(e -> Game.av.toggleMusic() );
        music.setFont(new Font("Corbel",0,subFS));
        settingsM.add(music);

        // Main 3 = About
        JButton about = new JButton("About");
        about.setIcon( Game.av.generateImageIcon("icon", "icons", "about") );
        about.addActionListener(e -> showAboutWindow());
        about.setFont(new Font("Calibri",0,mainFS));
        about.setOpaque(false);
        about.setContentAreaFilled(false);
        about.setBorderPainted(false);

        // Main 4 = Exit
        JMenu exitM = new JMenu("Exit");
        exitM.setIcon( Game.av.generateImageIcon("icon", "icons", "exit") );
        exitM.setFont(new Font("Calibri",0,mainFS));

        // Only sub in exit = Confirm
        JMenuItem exit = new JMenuItem("Confirm");
        exit.addActionListener(e -> System.exit(0));
        exit.setFont(new Font("Impact",0,subFS));
        exitM.add(exit);

        // Add main menus to menuBar
        menuBar.add(help);
        menuBar.add(settingsM);
        menuBar.add(about);
        menuBar.add(exitM);
    }

    public JMenuBar getMenu()
    {
        return menuBar;
    }

    /**
     * Generate CSS lines from a list of strings
     * 
     * Formatting: 
     * ~ = <br> = Put at the end of a line to start a new line
     * 
     * <i> = Italic section start
     * </i> = Italic section end
     * 
     * <b> = Bold section start
     * </b> = Bold section end
     * 
     */
    public String getCSSLines(ArrayList<String> lines)
    {
        String output = "<html>";

        for (String s : lines)
        { 
            output += s;
        }

        // Replace line break symbol with line break code
        output = output.replaceAll("~", "<br>");

        output += "</html>";

        return output;
    }

    private void showHelpWindow()
    {
        JFrame popup = new JFrame();

        JLabel text = new JLabel();

        ArrayList<String> linesA = new ArrayList<>();
        linesA.add("<b>Aim:</b> ~");
        linesA.add("You have crashed in South America, near the Aztecs, in a time period not long ago cosmically ~");
        linesA.add("You are trying to get back home. Explore and investigate the surroundings~");
        linesA.add("It's possible, trust me :D ~~");

        linesA.add("<b>Movement:</b> ~");
        linesA.add("Use the buttons under the blue 'Movement' label to move around ~");
        linesA.add("The text on each button describes where they will take you ~");
        linesA.add("To assist in orientation, try drawing a map ~~");
        
        linesA.add("<b>Actions:</b> ~");
        linesA.add("Use the buttons under the red 'Actions' label to interact with matter ~");
        linesA.add("What the buttons do is pretty intuitive, so try them out! :) ~~");
        
        linesA.add("<b>Console:</b> ~");
        linesA.add("Textual information is delivered to you here ~");
        linesA.add("Additionally, it allows for the grabbing and dropping of <i>specific</i> items, ~");
        linesA.add("Use like so = ~");
        linesA.add("drop X ~");
        linesA.add("grab X ~");
        linesA.add("(where X is the name of an item) ~~");
        
        text.setText(getCSSLines(linesA));
        text.setForeground(Color.darkGray);
        text.setFont(new Font("Calibri",0,26));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        popup.setExtendedState(JFrame.MAXIMIZED_BOTH);
        popup.add(text);
        popup.setVisible(true);
    }

    private void showAboutWindow()
    {
        JLabel title = new JLabel();

        ArrayList<String> lines = new ArrayList<>();
        lines.add("This was an example project part of the 'Objects First with Java' textbook.~");
        lines.add("It was a simple text-based video game called <i>'World of Zuul'</i> .~");
        lines.add("The only functionality was the ability to go between a few rooms.~~");

        lines.add("This version is one heavily extended by David Charkey in 2018.~");
        lines.add("The project was heavily improved, to become the <i>'Alien Aztec Adventure'</i>.~~");

        lines.add("Additions include (in order of implementation) = ~~");

        lines.add("<i> - many more locations~");
        lines.add(" - items, and their placement in locations~");
        lines.add(" - commands to handle such items (inspect,grab,drop,items)~");
        lines.add(" - item creation/combination and the construct command~");
        lines.add(" - locations having dynamic events~");
        lines.add(" - a visual interface to ease gameplay </i> ~~");

        lines.add("This program is written in Java.~~");

        lines.add("To contact the developer (David Charkey) email him at = <i>skywalker_dc@live.com.au</i>.~");

        title.setText(getCSSLines(lines));
        title.setForeground(Color.black);
        title.setOpaque(false);
        title.setFont(new Font("Calibri",0,26));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);

        JFrame popup = new JFrame();
        popup.add(title);
        popup.setExtendedState(JFrame.MAXIMIZED_BOTH);
        popup.setVisible(true);
    }

    private void showConsoleOpt()
    {
        JFrame popup = new JFrame();

        JLabel title = new JLabel();

        ArrayList<String> lines = new ArrayList<>();
        lines.add("To change how the console is displayed:~~");
        lines.add("1. Right click on the top of the console window~");
        lines.add("2. Select properties~");
        lines.add("3. Check each tab for options you want to change~~");

        lines.add("You may need to restart or select 'Default' in Step 2~~");

        lines.add("I recommend the following for all systems:~~");
        lines.add("<b> Options Tab </b>  = No changes ~~");
        lines.add("<b> Font Tab </b>  = Size 20, Consolas ~~");

        lines.add("<b> Layout Tab </b>  =  <i>(These values have been calculated using system info)</i> ~~");
        lines.add("  -   Window Size ~");
        lines.add("<i>   >   Width = " + Math.round(( Game.gui.SCREEN_H/5) - 6) + "</i> ~");
        lines.add("<i>   >   Height = " + Math.round(( Game.gui.SCREEN_W/91)) + "</i> ~");
        lines.add("  -   Window Position ~");
        lines.add("<i>    >   Left = " + 1 + "</i> ~");
        lines.add("<i>    >   Height = " + Math.round(( Game.gui.SCREEN_W/4) + 40) + "</i> ~~");

        lines.add("<b> Colors Tab </b>  = Change screen text to white ~~");    
        title.setText (getCSSLines(lines));

        title.setFont(new Font("Calibri",0,22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);

        popup.setExtendedState(JFrame.MAXIMIZED_BOTH);
        popup.add(title);
        popup.setVisible(true);
    }

    public void randomCol()
    {
        // Get premade JPanel
        JPanel jp1 = generateColJP("Random Colour", Color.darkGray);

        // Buttons
        ArrayList<JButton> buttons = new ArrayList<>();

        JButton rand = new JButton();
        rand.setText("Randomize");
        rand.setBackground(Color.white);
        rand.addActionListener(e -> jp1.setBackground(Game.gui.getRandomCol()));
        buttons.add(rand);

        JButton setBG = generateColSetters(jp1).get(0);
        buttons.add(setBG);

        JButton setButtons = generateColSetters(jp1).get(1);
        buttons.add(setButtons);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(true); //enabled default   
        objects.add(true); //visible default         
        objects.add(null); //text color
        objects.add(null); //button color
        objects.add("Calibri"); //font as a string
        objects.add(jp1);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(40); //xpos
        numbers.add(120); //ypos
        numbers.add(200); //button width
        numbers.add(40); //button height
        numbers.add(0); //horizontal spacing
        numbers.add(30);  //vertical spacing
        numbers.add(1); //columns
        numbers.add(20); //fontsize

        Game.gui.setupButtonGrid(buttons, objects, numbers);

        // Make specific changes
        rand.setFont(new Font("Jokerman",0,22));

        setBG.setSize(300, 40);
        setBG.setLocation(0, setBG.getY() + 40);

        setButtons.setSize(300, 40);
        setButtons.setLocation(0, setBG.getY() + 50);

        // Make popup and add content
        JFrame popup = new JFrame();
        popup.setSize(300, setButtons.getY() + 100);
        popup.add(jp1);
        popup.setVisible(true);
        popup.setResizable(false);
    }

    public void chooseCol()
    {
        // Get premade JPanel
        JPanel jpanel = generateColJP("Choose Colour", Color.darkGray);

        // Buttons
        ArrayList<JButton> buttons = new ArrayList<>();

        JButton white = new JButton();
        white.addActionListener(e -> jpanel.setBackground(Color.white));
        white.setBackground(Color.white);
        buttons.add(white);

        JButton blue = new JButton();
        blue.addActionListener(e -> jpanel.setBackground(Color.BLUE));
        blue.setBackground(Color.blue);
        buttons.add(blue);

        JButton red = new JButton();
        red.addActionListener(e -> jpanel.setBackground(Color.red));
        red.setBackground(Color.red);
        buttons.add(red);

        JButton gray = new JButton();
        gray.addActionListener(e -> jpanel.setBackground(Color.gray));
        gray.setBackground(Color.gray);
        buttons.add(gray);

        JButton green = new JButton();
        green.addActionListener(e -> jpanel.setBackground(Color.green));
        green.setBackground(Color.green);
        buttons.add(green);

        JButton lpink = new JButton();
        lpink.addActionListener(e -> jpanel.setBackground(Color.pink));
        lpink.setBackground(Color.pink);
        buttons.add(lpink);

        JButton dgray = new JButton();
        dgray.addActionListener(e -> jpanel.setBackground(Color.darkGray));
        dgray.setBackground(Color.darkGray);
        buttons.add(dgray);

        JButton dpink = new JButton();
        dpink.addActionListener(e -> jpanel.setBackground(Color.magenta));
        dpink.setBackground(Color.magenta);
        buttons.add(dpink);

        JButton orange = new JButton();
        orange.addActionListener(e -> jpanel.setBackground(Color.orange));
        orange.setBackground(Color.orange);
        buttons.add(orange);

        JButton black = new JButton();
        black.addActionListener(e -> jpanel.setBackground(Color.black));
        black.setBackground(Color.black);
        buttons.add(black);

        JButton cyan = new JButton();
        cyan.addActionListener(e -> jpanel.setBackground(Color.cyan));
        cyan.setBackground(Color.cyan);
        buttons.add(cyan);

        JButton yellow = new JButton();
        yellow.addActionListener(e -> jpanel.setBackground(Color.yellow));
        yellow.setBackground(Color.yellow);
        buttons.add(yellow);

        JButton setBG = generateColSetters(jpanel).get(0);
        buttons.add(setBG);

        JButton setButtons = generateColSetters(jpanel).get(1);
        buttons.add(setButtons);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(true); //enabled default   
        objects.add(true); //visible default         
        objects.add(null); //text color
        objects.add(null); //button color
        objects.add("Calibri"); //font as a string
        objects.add(jpanel);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(60); //xpos
        numbers.add(70); //ypos
        numbers.add(50); //button width
        numbers.add(50); //button height
        numbers.add(10); //horizontal spacing
        numbers.add(10);  //vertical spacing
        numbers.add(3); //columns
        numbers.add(20); //fontsize

        Game.gui.setupButtonGrid(buttons, objects, numbers);

        // Make specific changes
        setBG.setLocation(0, setBG.getY() + 40);
        setBG.setSize(300, 40);

        setButtons.setLocation(0, setBG.getY() + 50);
        setButtons.setSize(300, 40);

        // Make popup and add content
        JFrame popup = new JFrame();
        popup.setSize(300, setButtons.getY() + 100);
        popup.add(jpanel);
        popup.setVisible(true);
        popup.setResizable(false);
    }

    public void createCol()
    {
        // Initialise popupwindow and contentpanel
        JPanel jpanel = generateColJP("Color Creation", Color.black);

        // Buttons
        ArrayList<JButton> buttons = new ArrayList<>();

        JButton redUp = new JButton();
        redUp.setText("+");
        redUp.setBackground(Color.white);
        redUp.addActionListener( e -> processChange( "r" , 10, jpanel));
        buttons.add(redUp);

        JButton greenUp = new JButton();
        greenUp.setText("+");
        greenUp.setBackground(Color.white);
        greenUp.addActionListener( e -> processChange( "g" , 10, jpanel));
        buttons.add(greenUp);

        JButton blueUp = new JButton();
        blueUp.setText("+");
        blueUp.setBackground(Color.white);
        blueUp.addActionListener( e -> processChange( "b" , 10, jpanel));
        buttons.add(blueUp);

        JButton redDown = new JButton();
        redDown.setText("-");
        redDown.setBackground(Color.white);
        redDown.addActionListener( e -> processChange( "r" , -10, jpanel));
        buttons.add(redDown);

        JButton greenDown = new JButton();
        greenDown.setText("-");
        greenDown.setBackground(Color.white);
        greenDown.addActionListener( e -> processChange( "g" , -10, jpanel));
        buttons.add(greenDown);

        JButton blueDown = new JButton();
        blueDown.setText("-");
        blueDown.setBackground(Color.white);
        blueDown.addActionListener( e -> processChange( "b" , -10, jpanel));
        buttons.add(blueDown);

        JButton setBG = generateColSetters(jpanel).get(0);
        buttons.add(setBG);

        JButton setButtons = generateColSetters(jpanel).get(1);
        buttons.add(setButtons);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(true); //enabled default   
        objects.add(true); //visible default         
        objects.add(Color.black); //text color
        objects.add(Color.white); //button color
        objects.add("Corbel"); //font as a string
        objects.add(jpanel);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(75); //xpos
        numbers.add(75); //ypos
        numbers.add(45); //button width
        numbers.add(45); //button height
        numbers.add(10); //horizontal spacing
        numbers.add(150);  //vertical spacing
        numbers.add(3); //columns
        numbers.add(20); //fontsize

        Game.gui.setupButtonGrid(buttons, objects, numbers);

        // Perfect text area
        rgbVals.setOpaque(true);
        rgbVals.setForeground(Color.black);
        rgbVals.setBackground(Color.white);
        rgbVals.setFont( new Font ("Cambria", 0, 26));
        rgbVals.setSize( 170, 40);
        rgbVals.setLocation( redUp.getX() - 10 , redUp.getY() + 100);

        // Add label
        jpanel.add(rgbVals);

        // Modify "setter" buttons
        setBG.setLocation(0, redDown.getY() + 100);
        setBG.setSize(300, 40);

        setButtons.setLocation(0, setBG.getY() + 50);
        setButtons.setSize(300, 40);

        // Make popup and add content
        JFrame popup = new JFrame();
        popup.setSize(300, 600);
        popup.add(jpanel);
        popup.setVisible(true);
        popup.setResizable(false);
    }

    private void processChange(String colS, int change, JPanel dest)
    {
        if (colS.equals("r"))
        {
            R += change;
            if ( 0 > R  || R > 255) { R = 0;}
        }
        else if (colS.equals("g"))
        {
            G += change;
            if ( 0 > G  || G > 255) { G = 0;}
        }
        else if (colS.equals("b"))
        {
            B += change;
            if ( 0 > B  || B > 255) { B = 0;}
        }

        Color col = new Color(R,G,B);
        dest.setBackground(col);

        rgbVals.setText( " " + R + " , " + G + " , " + B);
    }

    private JPanel generateColJP(String titleS, Color col)
    {
        // Initialize JPanel and its colour
        JPanel jpanel = new JPanel(null);
        jpanel.setBackground(col);

        // Make title label
        JLabel title = new JLabel();
        title.setSize(300, 40);
        title.setLocation(0,10);
        title.setForeground(Color.white);
        title.setBackground(Color.darkGray);
        title.setFont(new Font("Cambria",0,24));
        title.setText(titleS);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setHorizontalTextPosition(SwingConstants.CENTER);
        title.setOpaque(true);
        title.setEnabled(true);
        title.setVisible(true);

        // Add title label
        jpanel.add(title);

        return jpanel;
    }

    private ArrayList<JButton> generateColSetters(JPanel jp)
    {
        ArrayList<JButton> list = new ArrayList<>();

        JButton setBG = new JButton();
        setBG.addActionListener(e -> Game.gui.setBGCol(jp.getBackground()));
        setBG.setBackground(Color.lightGray);
        setBG.setText("Set as Background Colour");
        setBG.setFont(new Font("Calibri",0,20));
        setBG.setBackground(Color.white);
        list.add(setBG);

        JButton setButtons = new JButton();
        setButtons.addActionListener(e -> Game.gui.setCommandCol(jp.getBackground()));
        setButtons.setBackground(Color.lightGray);
        setButtons.setText("Set as Button Colour");
        setButtons.setFont(new Font("Calibri",0,20));
        setButtons.setBackground(Color.white);
        list.add(setButtons);

        return list;
    }
}
