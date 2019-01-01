import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is part of the "Alien Aztec Adventure" application. 
 * 
 * Used to setup Mini-games/Challenges
 *
 * @author David Charkey
 */
public class Challenge
{
    private AV av = new AV();
    
    private JFrame popup = new JFrame();
    private ArrayList<JButton> elembuttons = new ArrayList<>();

    private ArrayList<JButton> mgbuttons = new ArrayList<>();
    private ImageIcon cbI =  av.generateImageIcon("icon", "mg", "cball");
    private JButton alien = new JButton();
    private JFrame mgframe = new JFrame();
    private JPanel mgpanel = new JPanel();
    private Random rng = new Random();

    public void openElementPuzzle()
    {
        // Setup JPanel
        JPanel jpanel = new JPanel(null);
        jpanel.setBackground( new Color(232, 188, 30)    );

        for (int i = 1; i != 5; i++)
        {
            JButton elem = new JButton();
            elem.setName("blank");
            elem.setIcon (  av.generateImageIcon("element" , "puzzle" , "blank" ) );
            elem.removeMouseListener(elem.getMouseListeners()[0]);
            elembuttons.add(elem);
        }

        JButton air = new JButton();
        air.setName("air");
        air.setIcon(  av.generateImageIcon("element", "puzzle" , "air") );
        air.addActionListener(e -> addElement(air) );
        elembuttons.add(air);

        JButton earth = new JButton();
        earth.setName("earth");
        earth.setIcon(  av.generateImageIcon("element", "puzzle" , "earth") );
        earth.addActionListener(e -> addElement(earth) );
        elembuttons.add(earth);

        JButton elec = new JButton();
        elec.setName("elec");
        elec.setIcon(  av.generateImageIcon("element", "puzzle" , "elec") );
        elec.addActionListener(e -> addElement(elec) );
        elembuttons.add(elec);

        JButton fire = new JButton();
        fire.setName("fire");
        fire.setIcon(  av.generateImageIcon("element", "puzzle" , "fire") );
        fire.addActionListener(e -> addElement(fire) );
        elembuttons.add(fire);

        JButton ice = new JButton();
        ice.setName("ice");
        ice.setIcon(  av.generateImageIcon("element", "puzzle" , "ice") );
        ice.addActionListener(e -> addElement(ice) );
        elembuttons.add(ice);

        JButton metal = new JButton();
        metal.setName("metal");
        metal.setIcon(  av.generateImageIcon("element", "puzzle" , "metal") );
        metal.addActionListener(e -> addElement(metal) );
        elembuttons.add(metal);

        JButton nature = new JButton();
        nature.setName("nature");
        nature.setIcon(  av.generateImageIcon("element", "puzzle" , "nature") );
        nature.addActionListener(e -> addElement(nature) );
        elembuttons.add(nature);

        JButton water = new JButton();
        water.setName("water");
        water.setIcon(  av.generateImageIcon("element", "puzzle" , "water") );
        water.addActionListener(e -> addElement(water) );
        elembuttons.add(water);

        JButton clear = new JButton();
        clear.setIcon(  av.generateImageIcon("custom-25-25", "puzzle" , "clear") );
        clear.addActionListener(e -> clearElements() );
        elembuttons.add(clear);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(true); //enabled default   
        objects.add(true); //visible default         
        objects.add(null); //text color
        objects.add(null); //button color
        objects.add("Calibri"); //font as a string
        objects.add(jpanel);  //destination

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(80); //xpos
        numbers.add(70); //ypos
        numbers.add(50); //button width
        numbers.add(50); //button height
        numbers.add(10); //horizontal spacing
        numbers.add(10);  //vertical spacing
        numbers.add(4); //columns
        numbers.add(20); //fontsize

        Game.gui.setupButtonGrid(elembuttons, objects, numbers);

        // Make popup and add content
        popup.setSize(400, 400);
        popup.setLocation(  Game.gui.SCREEN_W /2 - popup.getWidth()/2 , 10 );
        popup.add(jpanel);
        popup.setVisible(true);
        popup.setResizable(false);

        //Specific changes
        for (int i = 0; i < 4; i++)
        {
            JButton temp = elembuttons.get(i);
            int xpos = temp.getX();
            int ypos = temp.getY();
            temp.setLocation(xpos , ypos - 60);
        }

        clear.setSize(25,25);
    }

    private void addElement(JButton elem)
    {
        for (int i = 0; i < 4; i++)
        {
            if (elembuttons.get(i).getName().equals("blank"))
            {
                elembuttons.get(i).setIcon( elem.getIcon() );
                elembuttons.get(i).setName( elem.getName() );
                break;  //allows code underneath to run
            }
        }

        String order = "";
        for (int i = 0; i < 4; i++)
        {
            order += elembuttons.get(i).getName();
        }

        if (order.equals("earthfireairwater"))
        {
            popup.setVisible(false);
            popup.dispose();
            Game.events.elementSuccess();

        }

    }

    private void clearElements()
    {   
        for (int i = 0; i < 4; i++)
        {
            JButton temp = elembuttons.get(i);
            temp.setName("blank");
            temp.setIcon (  av.generateImageIcon("element" , "puzzle" , "blank" ) );
        }
    }

    public synchronized void runConqGame()
    {
        setupCGParts();
        
        mgpanel.repaint();
         try { Thread.sleep(5000); }
            catch (Exception e) { }

        int barrages = 0;

        for (int i = 0; i < 11; i++)
        {
            
            try { Thread.sleep(500); }
            catch (Exception e) { }
            
            int size = getRandinRange(30 ,60 );
            int sep = getRandinRange(70 ,160 );
            int time = getRandinRange(20, 35 );

            fireBarrage(size, sep, time);

            mgpanel.repaint();


            if ( barrages % 10 == 0   && barrages != 50)
            {
                try { Thread.sleep(3000); }
                catch (Exception e) { }
                i = 0;
            }

            barrages++;
        }

        survivedMG();
    }

    private int getRandinRange(int start, int end)
    {
        return rng.ints(start, end).findFirst().getAsInt();
    }

    private void setupCGParts()
    {
        // Foundation
        mgpanel = new JPanel(null);
        mgpanel.setVisible(true);
        mgpanel.setBackground(Color.white);
        
        mgframe.setSize(600, 600);
        mgframe.add(mgpanel);
        mgframe.setVisible(true);
        mgframe.setResizable(false);
        mgframe.setFocusable(false);


        // Add bg
        JButton bg = new JButton();
        String specs = "custom-600-600";
        bg.setIcon( av.generateImageIcon( specs, "mg", "deck") );
        bg.setSize( bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight() );
        bg.setLocation( 0, 0 );
        bg.removeMouseListener( bg.getMouseListeners()[0] );
        bg.setVisible(true);
        bg.setEnabled(true);

        //needed so up displays
        JButton j = new JButton();
        j. setVisible(false);
        mgpanel.add( j  , 0);

        // Starting location
        int Sx = 100;
        int Sy = 250;

        // Add controls
        JButton up = new JButton();
        up.setName("UP");
        up.setIcon( av.generateImageIcon("icon", "mg", "up") );
        up.setSize(50,50);
        up.setLocation( Sx, Sy - 50 );
        up.addActionListener( e -> moveButtons(0,-10, mgbuttons) );
        mgbuttons.add(up);

        JButton down = new JButton();
        down.setName("DOWN");
        down.setIcon(  av.generateImageIcon("icon", "mg", "down") );
        down.setSize(50,50);
        down.setLocation( Sx,Sy + 50 );
        down.addActionListener( e -> moveButtons(0,10, mgbuttons) );
        mgbuttons.add(down);

        JButton left = new JButton();
        left.setName("LEFT");
        left.setIcon(  av.generateImageIcon("icon", "mg", "L") );
        left.setSize(50,50);
        left.setLocation( Sx - 50, Sy );
        left.addActionListener( e -> moveButtons(-10,0, mgbuttons) );
        mgbuttons.add(left);

        JButton right = new JButton();
        right.setName("RIGHT");
        right.setIcon(  av.generateImageIcon("icon", "mg", "R") );
        right.setSize(50,50);
        right.setLocation( Sx + 50 ,Sy);
        right.addActionListener( e -> moveButtons(10,0, mgbuttons) );
        mgbuttons.add(right);

        alien.setName("alien");
        alien.setIcon(  av.generateImageIcon("icon", "mg", "alien") );
        alien.setSize(50,50);
        alien.setLocation( Sx, Sy );
        addKey(alien, "left",  
            new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) {
                    left.doClick();
                }
            } 
        );
        addKey(alien, "right",  
            new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) {
                    right.doClick();
                }
            } 
        );
        addKey(alien ,"up",  
            new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) {
                    up.doClick();
                }
            } 
        );
        addKey(alien,"down" , 
            new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) {
                    down.doClick();
                }
            } 
        );
        mgbuttons.add(alien);

        for (JButton curBut : mgbuttons)
        {
            curBut.setVisible(true);
            curBut.setEnabled(true);
            curBut.setOpaque(false);
            curBut.setContentAreaFilled(false);
            curBut.setBorderPainted(false);
            mgpanel.add(curBut, 0);
        }
        
        // Put background at back
        mgpanel.add(bg, mgpanel.getComponents().length-1);

        // alien is visible
        alien.setVisible(true);
    }

    private void addKey(JButton button, String kname, AbstractAction a)
    {
        String keyname = kname.toUpperCase();
        String keychar = "" + keyname.charAt(0) + "";

        InputMap im = button.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put( javax.swing.KeyStroke.getKeyStroke(keyname) , keychar);
        button.getActionMap().put( keychar ,a );
    }

    private void moveButtons(int xchange, int ychange, ArrayList<JButton> buttons)
    {
        for (JButton curBut : buttons)
        {
            int xpos = curBut.getX() + xchange;
            int ypos = curBut.getY() + ychange;

            if (  (xpos < 5 || ypos < 10)   || (xpos > 510 || ypos > 510) )
            {
                if (curBut.getName() == "cball")
                {
                    curBut.setVisible(false);
                }

                xpos = curBut.getX();
                ypos = curBut.getY();

            }

            curBut.setLocation(xpos,ypos);
        }
    }

    private void fireBarrage(int size, int sep, int time)
    {

        ArrayList<JButton> butA = new ArrayList<JButton>();

        int curXpos = 6;
        for (int i = 0; i != 11; i++)
        {
            JButton cBall = new JButton();
            cBall.setName("cball");
            cBall.setIcon( cbI );
            cBall.setVisible(true);
            cBall.setContentAreaFilled(false);
            cBall.setBorderPainted(false);

            cBall.setSize( size, size);
            cBall.setLocation( curXpos , 5 );
            curXpos += sep;

            butA.add(cBall);
            mgpanel.add(cBall, 0);
        }

        Timer t = new Timer( time ,  e -> moveButtons(0, 5, butA) );
        t.addActionListener( e -> mgpanel.repaint() );
        t.addActionListener( e -> collisionCheck(t, butA) );
        t.start();
    }

    private void collisionCheck(Timer t, ArrayList<JButton> balls)
    {
        for (JButton but : balls)
        {
            int ax = alien.getX();
            int bx = but.getX();
            int ay = alien.getY();
            int by = but.getY();
            boolean xcheck = bx == ax;
            boolean ycheck = by == ay;

            if ( xcheck && ycheck)
            {
                try { Thread.sleep(500); }
                catch (Exception e) { }

                t.stop();
                mgframe.dispose();
                mgframe.setVisible(false);

                Game.events.finishGame();

            }
        }
    }
    
    private void survivedMG()
    {
        mgframe.setVisible(false);
        mgframe.dispose();
        Game.events.mgSuccess();
    }
}
