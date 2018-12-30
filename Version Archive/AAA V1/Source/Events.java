import java.awt.Color;
import java.util.ArrayList;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Models in-game events
 *
 * @author David Charkey
 */
public class Events 
{
    private boolean suspenseOn = true;

    private boolean apexRevealed = false;
    private boolean apexEntered = false;

    private boolean caveEntered = false;
    private boolean burrowOpened = false;
    String pebbleOrder = "";

    private boolean Vtraded = false;
    private boolean Vworship = false;
    private boolean Vscared = false;
    private boolean Vstare = false;
    
    private boolean elemShown = false;
    private boolean boardedConqShip = false;

    public void sleep(int time)
    {
        if (suspenseOn)
        {
            try        
            {
                Thread.sleep(time);
            } 
            catch(InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Print out the opening message for the player.
     */
    public void gameStart()
    {
        System.out.println();
        System.out.println("Welcome to the Alien Aztec Adventure!");
        System.out.println("Your aim is to find a way to get home");
        System.out.println("Click on 'help' to find out how to play");
        System.out.println();

        Game.actions.look();
    }

    public void toggleSuspense()
    {
        if (suspenseOn)
        {
            System.out.println("\n" + "Suspense option went from on to off" + "\n");
        }
        else 
        {
            System.out.println("\n" + "Suspense option went from off to on" + "\n");
        }

        suspenseOn = !suspenseOn;
    }

    public void animalEvent()
    {
        System.out.println(Game.alien.getLocation().getEvent());

        if (Game.alien.returnNumberofType("W") != 0) //has weapon
        {
            System.out.println("...");
            sleep(2500);
            System.out.println("It was an intense battle ... but you were able to defend yourself");
            System.out.println("You used a weapon in your inventory");
            System.out.println("It was depleted in the process");
            System.out.println();

            Game.alien.removeType(1 , "W");
            Game.alien.getLocation().eventFinished();
        }
        else if (Game.alien.returnNumberofType("H") != 0) // has heal
        {
            System.out.println("...");
            sleep(3000);
            System.out.println("You were unable to defend yourself...");
            sleep(500);
            System.out.println("You fled ... but the creature had done its damage!");
            sleep(1000);
            System.out.println("Fortunately, you had a heal in your inventory");
            System.out.println("It was depleted in the process");
            System.out.println();

            Game.alien.removeType(1 , "H");
            Game.alien.getLocation().eventFinished();
        }
        else
        {
            sleep(500);
            System.out.println("You were unable to defend yourself...");
            sleep(500);
            System.out.println("You fled ... but the creature had done its damage!");
            sleep(500);
            System.out.println("With no way to heal, you begin to fade out ... while longingly gazing up at the sky ");
            System.out.println();
            sleep(1500);

            finishGame();
        }

    }

    /**
     * Check all the event conditions to see if one should be triggered
     */
    public void check()
    {
        Game.gui.update(Game.alien.getLocation());

        String Inventory = Game.alien.getInvString().toLowerCase().trim();
        String Location = Game.alien.getLocation().getSimpleDesc();

        boolean inVillage = Location.contains("village");
        boolean hasEarring = Inventory.contains("earring");
        boolean hasMask = Inventory.contains("mask");
        if (inVillage)
        {
            if (hasMask)
            {
                if (hasEarring)
                {
                    villageTrade();
                }
                else 
                {
                    villageWorship();
                }
            }
            else if (!hasMask)
            {
                villageScared();

                if (Vscared)
                {
                    villageStare();
                }
            }
        }
        else 
        {
            Vstare = false;
            Vworship = false;
        }

        boolean hasStone = Inventory.contains("heavy");
        boolean inHut = Location.contains("hut");
        if (hasStone && inHut)
        {
            burrowOpen();
        }
        else if (inHut && caveEntered)
        {
            System.out.println("I can't go back to the cave ... The hole collapsed as soon as I entered the cave system");
        }

        boolean inApex = Location.contains("aura");
        if (inApex)
        {
            apexEnter();
        }

        checkPebbleOrder();
        boolean inBackofCave = Location.contains("back");
        if (!inBackofCave)
        {
            pebbleOrder = "";
        }
        else if (inBackofCave && pebbleOrder.contains("ROYGBV"))
        {
            caveOpen();
        }

        boolean hasEnteredCave = Location.contains("system");
        if (hasEnteredCave)
        {
            caveEntered();
        }

        boolean inChamber = Location.toLowerCase().contains("chamber");
        boolean hasCrsKey = Game.alien.returnNumberofType("Key") > 0;
        if (inChamber && hasCrsKey)
        {
            apexReveal();
        }

        boolean inRuins = Location.toLowerCase().contains("bygone");
        if (inRuins && !elemShown)
        {
            Game.chl.openElementPuzzle();
            elemShown = true;
        }
        else 
        {
            elemShown = false;
        }
        
        boolean onBeach = Location.contains("shore");
        boolean hasAGbelt = Game.alien.getInvString().toLowerCase().contains("belt");
        if (onBeach && hasAGbelt)
        {
            boardConqShip();
        }

        boolean withShip = Location.contains("crashed");
        boolean hasGold = Game.alien.returnNumberofType("Gold") > 2;
        if (withShip && hasGold)
        {
            finalEvent();
        }
    }

    private void apexReveal()
    {
        if (apexRevealed == true)
        {
            return;
        }

        System.out.println(".... RUMBLE ....");
        sleep(2000);
        System.out.println(".... RUMBLE   RUMBLE   RUMBLE  ....");
        sleep(3000);
        System.out.println("The Ancient Pedestal and Crystal are resonating!");
        sleep(1500);
        System.out.println(".... RUMBLE ....");
        sleep(1500);
        System.out.println("The seal is opening!");
        sleep(1000);
        System.out.println(".... Rumbling  stops ...");
        sleep(400);
        System.out.println("You noticed there is now some light coming from above");
        System.out.println();

        // Add exit to chamber that goes to apex
        Game.locations.getRoom("chamber").addExit("toapex" , Game.locations.getRoom("pinnacle"));

        // Add exit to apex that goes to chamber
        Game.locations.getRoom("pinnacle").addExit("tochamber" ,  Game.locations.getRoom("chamber"));

        Game.gui.update(Game.alien.getLocation());

        apexRevealed = true;
    }

    private void apexEnter()
    {
        if (apexEntered == true)
        {
            return;
        }

        System.out.println("You climbed up the monolith to get into the apex of the temple");
        sleep(1000);
        System.out.println("... My damaged blaster is beginning to glow!...");
        System.out.println("... Could it be the healing aura?...");
        sleep(2000);
        System.out.println("... The temple is regenerating it! ....");
        sleep(2000);
        System.out.println("Your blaster was restored!");
        System.out.println();

        Game.alien.addItem(Game.locations.getItem("restored"));

        Game.gui.setBGCol(Color.orange);
        Game.gui.update(Game.alien.getLocation());

        apexEntered = true;
    }

    private void villageTrade()
    {
        if (Vtraded == true)
        {
            return;
        }

        System.out.println();
        System.out.println(" ... The villagers noticed you have their leader's daughter's earring! ...");
        sleep(1000);
        System.out.println(" ... You decide to give it to them ...");
        sleep(1000);
        System.out.println(" ... They were very thankful and gave you gifts in return...");

        Game.alien.addItem(Game.locations.getItem("native"));
        Game.alien.addItem(Game.locations.getItem("sword"));

        Game.alien.removeItem("Earring");

        Vtraded = true;
    }

    private void villageScared()
    {
        if (Vscared == true)
        {
            return;
        }

        System.out.println(" .... AHHHHH ... (villagers screaming)");
        sleep(1000);
        System.out.println(" ... They are very afraid of you! ... ");
        System.out.println(" ... They ran into their huts ... ");
        System.out.println(" ... I should leave ... ");
        System.out.println();

        Vscared = true;
    }

    private void villageWorship()
    {
        if (Vworship == true)
        {
            return;
        }

        System.out.println(" ... You have a great idea!  You put the mask on!");
        sleep(1000);
        System.out.println(" ... The villagers are mesmerised ... ");
        sleep(1000);
        System.out.println(" ... They form a circle around you  ... ");
        sleep(1500);
        System.out.println(" ... They bow down and begin to worship you  ... ");
        sleep(1000);
        System.out.println(" ... They begin to chant 'QUETZALCOATL! QUETZALCOATL!' ... ");
        System.out.println();

        Vworship = true;
    }

    private void villageStare()
    {
        if (Vstare == true)
        {
            return;
        }

        System.out.println();
        System.out.println(" ...  You sense the eyes of the many frightened villagers staring at you .. ");
        System.out.println(" ...  from shadows and huts ... ");
        System.out.println();

        Vstare = true;
    }

    private void burrowOpen()
    {
        if (burrowOpened == true)
        {
            return;
        }

        System.out.println("The ground cannot hold the weight of the stone!!");
        System.out.println("The ground is collapsing!!!");
        System.out.println(".... AHHHH ...");
        System.out.println();
        sleep(3500);
        System.out.println("You woke up dazed ... and looked around");
        System.out.println("The stone appears to have fallen out of your hands and buried into the dirt beneath you");
        System.out.println("You've fallen quite far ... You're trapped!");
        System.out.println("In the poorly lit cavity ... You see a cave system ahead!");
        System.out.println();

        Game.alien.removeItem("Stone"); //Remove stone

        Game.alien.setLocation(Game.locations.getRoom("deephole")); //Move character to burrow

        Game.gui.update(Game.alien.getLocation());
        Game.gui.setBGCol(Color.darkGray);

        burrowOpened = true;
    }

    private void caveEntered()
    {
        if (caveEntered == true)
        {
            return;
        }

        System.out.println(".... BRSSH ...");
        System.out.println("The hole behind you filled up with dirt!");
        System.out.println("You are really trapped now!");
        System.out.println();

        caveEntered = true;
    }

    private void checkPebbleOrder()
    {
        for (Item curitem :  Game.alien.getLocation().getItemAL())
        {
            if (curitem.getItemType() == "R" )
            {
                pebbleOrder += curitem.getItemName().charAt(0);
            }
        }
    }

    private void caveOpen()
    {
        Game.alien.removeType(7, "R");
        Game.alien.addItem(Game.locations.getRoom("events").getItemAL().get(3));

        sleep(500);
        System.out.println("\n" + "Wow! Each of the coloured pebbles are beginning to glow and vibrate");
        sleep(2000);
        System.out.println("Magnificient, coloured lights are dancing around the room");

        int counter = 30;
        while (counter != 0)
        {
            Game.gui.setBGCol(Game.gui.getRandomCol());
            Game.gui.update(Game.alien.getLocation());
            sleep(40);
            counter--;
        }

        sleep(2000);
        System.out.println("The colours merged in a bright, dazzling light ... that made you close your eyes" + "\n");
        Game.gui.setBGCol(Color.white);
        Game.gui.update(Game.alien.getLocation());
        sleep(2000);
        System.out.println("When you opened your eyes, the pebbles were gone!");
        Game.gui.setBGCol(Color.lightGray);
        Game.gui.update(Game.alien.getLocation());
        sleep(2000);
        System.out.println("and there was no wall or mural!");
        sleep(2000);
        System.out.println("Just before you was a gold bar!  You stowed it away" + "\n");
        sleep(2000);
        System.out.println("Several metres ahead of you was a grand waterfall");
        sleep(2000);
        System.out.println("... You moved closer to waterfall");
        sleep(2000);
        System.out.println("The sound of the immense amount of water moving was soothing to the ear"+ "\n");
        sleep(4000);
        System.out.println("Suddenly, a gust of wind blew past the mouth of the cave, pulling you in its wake!!!");
        sleep(2000);
        System.out.println("You went tumbling into the water of the river below!!! ....");
        sleep(2000);
        System.out.println("Some time later you wake up on a sunny, grassy bank" + "\n");

        Game.gui.setBGCol(Color.yellow);

        Game.alien.setLocation(Game.locations.getRoom("westpart"));

        Game.gui.update(Game.alien.getLocation());
    }

    public void elementSuccess()
    {
        System.out.println();
        System.out.println("I've done it! I've cracked the code!");
        sleep(1000);
        System.out.println("The structure is morphing!");
        sleep(1000);
        System.out.println("A compartment materialised");
        sleep(1000);
        System.out.println("There is an object inside!");
        sleep(1000);
        System.out.println("It is small antigravity device!?!");
        sleep(1000);
        System.out.println("I don't know how such familiar technology got here, but I'm sure it will come in handy .. ");
        sleep(1000);

        Game.alien.addItem(Game.locations.getItem("antigravity"));
    }

    private synchronized void boardConqShip()
    {
        if (boardedConqShip == true)
        {
            return;
        }

        System.out.println("A masterful idea has hit me!");
        sleep(1000);
        System.out.println("I could float over to the ship using my AGbelt!");
        sleep(1000);
        System.out.println("Perhaps they have what I need");
        sleep(1000);
        System.out.println("Ok ... here goes nothing!!!");
        sleep(1000);
        System.out.println("Wow its working!!");
        sleep(3000);
        
        Game.alien.setLocation(Game.locations.getRoom("conquistador")); //tele[prt to ship
        Game.gui.update(Game.alien.getLocation());
        
        System.out.println("I've arrived on the ship!");
        sleep(2000);
        
        System.out.println("They attack!   (minigame is opening) ");
         
        Game.mgProxy();
         
        boardedConqShip = true;
    }

    public void mgSuccess()
    {
        System.out.println("The Conquistadors are running away into the lower decks in fear!");
        sleep(1000);
        System.out.println("You take advantage of the commotion, and grab some gold from them!");
        sleep(1000);
        System.out.println("Soon after, you used the last charge of the antigravity belt to float to shore");
        
        Game.alien.removeItem("belt"); //Lose belt
        Game.alien.addItem(Game.locations.getItem("gold")); // Get gold
        
        Game.alien.setLocation(Game.locations.getRoom("shore")); //Go to beach
    }

    private void finalEvent()
    {
        System.out.println(".... wait ....");
        sleep(2000);
        System.out.println("The gold I've collected would be perfect for fixing the ship!");
        sleep(2000);
        System.out.println("... let me just get my toolkit ... solder this here ... fix this circuit board ...");
        sleep(3000);
        System.out.println(" .... BRRRR .. (Powering up noise) ....");
        sleep(1000);
        System.out.println("I've done it! I've fixed my ship" + "\n");
        sleep(2500);
        System.out.println("Narrator:" + "\n" + "... and so the alien was able to get through the perilous jungle,");
        System.out.println("using his wits and determination." + "\n" + "He was able to fix his ship , and fly home" + "\n");
        sleep(3000);
        System.out.println("Congratulations for completing this game!" + "\n");
        sleep(2000);
        System.out.println("Credits = David Charkey 2018");
        sleep(10000);

        finishGame();
    }

    public void finishGame()
    {
        System.out.println("Thankyou for playing AAA");
        System.out.println();
        System.out.println("The program will now exit shortly");
        System.out.println();

        Game.events.sleep(5000);

        System.exit(0);
    }
}

