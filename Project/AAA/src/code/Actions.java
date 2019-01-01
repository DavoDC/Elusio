import java.util.ArrayList;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Models in-game user commands
 * 
 *  @author David Charkey
 */
public class Actions 
{
    private boolean cheatsenabled = false;

    /** 
     * Try to go in one direction.
     * If there is an exit, enter the new Room.
     * Otherwise, print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) 
        {
            System.out.println("Go where?" + "\n");
            return;
        }

        String direction = command.getSecondWord().toLowerCase();

        Room nextRoom = Game.alien.getLocation().getExit(direction);

        if (nextRoom == null) 
        {
            System.out.println("You cannot go that way" + "\n");
        }
        else 
        {
            Game.alien.setLocation(nextRoom);

            if (Game.alien.getLocation().getEvent() == null) 
            {
                Game.actions.look();
            }
            else
            {
                Game.events.animalEvent();   
            }

        }

        Game.gui.update(Game.alien.getLocation());  
        Game.events.check();
    }

    public void look()
    {
        System.out.println(Game.alien.getLocation().getRoomDesc());

        System.out.println();

        Game.gui.update(Game.alien.getLocation());  

        Game.events.check();
    }

    /**
     * Used to check items in Room
     */
    public void inspect() 
    {
        String ISS = Game.alien.getLocation().getISS();

        if (ISS.contains("no")) // If there are no items, say so
        {
            System.out.println(ISS + "\n");
        }
        else // If there are items, say what they are
        {
            System.out.println("You inspect the items in the Room");
            System.out.println("You found the following = ");
            System.out.println(Game.alien.getLocation().getInvString());
        }
    }

    /**
     * The character will pickup an item in the Room
     */
    public void grab(Command command) 
    {
        if (Game.alien.getLocation().getInvString() == "")
        {
            System.out.println(Game.alien.getLocation().getISS());
        }
        else if(!command.hasSecondWord()) {
            System.out.println("Grab what?" + "\n");
        }
        else  // If there are no irregularities, do the following =
        {
            String second = command.getSecondWord();

            if (second.toLowerCase().equals("all")) // Get all
            {
                int comparison = Game.alien.getLocation().getItemAL().size();

                if (Game.alien.getLocation().getInvString() != "") //only add all if inv holds something
                {
                    Game.alien.getLocation().moveAllTo(Game.alien);
                }

                if (comparison == Game.alien.getLocation().getItemAL().size() ) //if Room items unchanged
                {
                    System.out.println("There are no items (that can be grabbed) here" + "\n");
                }
                else // grab was successful
                {
                    System.out.println("All items grabbed!" + "\n");
                }
            }
            else // Specific item
            {
                if (Game.locations.getItem(second).getItemType() == "Fixed") // Fixed item
                {
                    System.out.println("This item cannot be moved" + "\n");
                    return;
                }

                int comparison = Game.alien.getItemAL().size();

                Game.alien.getLocation().moveItemTo(Game.alien, second);

                if (comparison == Game.alien.getItemAL().size() )
                {
                    System.out.println("That item is not here" + "\n");
                }
                else //successful
                {
                    System.out.println("You grabbed an item!" + "\n");
                }
            }
        }

        Game.events.check();
    }

    /**
     * The character will drop an item into the Room
     */
    public void drop(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?" + "\n");
            return;
        }

        String second = command.getSecondWord();

        if (second.toLowerCase().equals("all"))
        {
            if (Game.alien.getInvString() != "") // if inventory holds something
            {
                Game.alien.moveAllTo(Game.alien.getLocation());
                System.out.println("All items dropped!" + "\n");
            }
            else // if inventory is empty
            {
                System.out.println("You don't have any items" + "\n");
            }
        }
        else
        {
            int comparison = Game.alien.getItemAL().size();

            Game.alien.moveItemTo(Game.alien.getLocation() , second);

            if (comparison == Game.alien.getItemAL().size() )
            {
                System.out.println("That item is not in your inventory" + "\n");
            }
            else
            {
                System.out.println("You dropped an item!" + "\n");
            }
        }

        Game.events.check();
    }

    public void items()
    {
        String ISS = Game.alien.getISS();

        if (ISS.contains("no"))
        {
            System.out.println("You don't have any items" + "\n");
        }
        else
        {
            System.out.println("You found the following in your bag = ");
            System.out.println(Game.alien.getInvString());
        }
    }

    public void construct()
    {
        System.out.println("Hmmm ... lets check the inventory for anything that could fit together");
        Game.events.sleep(1500);
        System.out.println("....maybe this fits here...");
        Game.events.sleep(1500);

        int itemsmade = 0;

        if (Game.alien.returnNumberofType("Spr") == 2)
        {
            System.out.println();
            System.out.println("Well done! You made a new item!");
            System.out.println(">> A Spear was added to your inventory (made from Spearhead and Branch) ");
            System.out.println();

            Game.alien.addItem(Game.locations.getRoom("construct").getItemAL().get(0));
            Game.alien.removeType(2, "Spr");

            itemsmade++;
        }

        if (Game.alien.returnNumberofType("Sal") == 2)
        {
            System.out.println();
            System.out.println("Well done! You made a new item!");
            System.out.println(">> A Salve was added to your inventory (made from Leaf ground up by Pestle) ");
            System.out.println();

            Game.alien.addItem(Game.locations.getRoom("construct").getItemAL().get(1));
            Game.alien.removeType(2, "Sal");

            itemsmade++;
        }

        if (Game.alien.returnNumberofType("Crs") > 3)
        {
            System.out.println();
            System.out.println("Well done! You made a new item!");
            System.out.println(">> A Crystal was added to your inventory (made from Crystal fragments) ");
            System.out.println();

            Game.alien.addItem(Game.locations.getRoom("construct").getItemAL().get(2));
            Game.alien.removeType(4, "Crs");

            itemsmade++;
        }

        if (itemsmade == 0)
        {
            System.out.println();
            System.out.println("You weren't able to come up with any logical/complete combinations...");
            System.out.println();
        }

        Game.events.check();
    }

    public void cheat(Command command)
    {
        System.out.println();
        String keyword = command.getSecondWord();

        if (keyword == null)
        {
            System.out.println("A second word is required");
            System.out.println("Try 'cheat help' ");
            System.out.println();
        }
        else if (keyword.contains("help"))
        {
            System.out.println("Cheat codes      :     (Capitalized parts require special input) ");

            System.out.println(" + cheat activate-PW       : Enable cheating , by entering password");

            System.out.println(" > cheat teleport-DESC     : Teleport to Room via description");
            System.out.println(" > cheat teleport-#        : Teleport to Room via reference number");
            System.out.println(" > cheat teleport-show     : Shows teleport possibilities");

            System.out.println(" ~ cheat additem-NAME      : Add an item via name");
            System.out.println(" ~ cheat additem-show      : Shows item names ");
            System.out.println();

            printCheatExamples();
        }
        else if (!keyword.contains("-"))
        {
            System.out.println(" All cheat codes require '-' ");
            printCheatExamples();
        }
        else 
        {
            String suffix = "";

            try 
            {
                suffix = keyword.split("-")[1];
            }
            catch (IndexOutOfBoundsException e)
            {
                throw new IllegalArgumentException("Format required is  'prefix-suffix' ");
            }

            if (keyword.contains("activate"))
            {
                if (suffix.equals("Dav=Dev"))
                {
                    System.out.println("Cheats are now enabled, awaiting use");
                    System.out.println();
                    cheatsenabled = true;
                }
                else
                {
                    System.out.println("Password was incorrect");
                    System.out.println();
                }
            }
            else if (cheatsenabled)
            {
                if ( keyword.contains("tele") || keyword.contains("item") )
                {
                    if ( keyword.contains ("tele") )
                    {
                        cheatTeleport(suffix);
                        System.out.println();
                    }
                    else if ( keyword.contains ("item") )
                    {
                        cheatAddItem(suffix);
                        System.out.println();
                    }
                }
            }
            else
            {
                System.out.println("Either you typed in an invalid suffix or cheats are not enabled");
                System.out.println("Try =  'cheat help'  for more information ");
                System.out.println();
            }

        }
    }

    private void printCheatExamples()
    {
        System.out.println();
        System.out.println(" Examples: ");
        System.out.println("    cheat additem-Yell");
        System.out.println("    cheat teleport-clearing");
        System.out.println("    cheat teleport-12");
        System.out.println();
    }

    private void cheatTeleport(String suffix)
    {
        if (suffix.contains("show"))
        {
            System.out.println("*** Locations available = ***");

            int refno = 1;
            for (Room curRoom : Game.locations.getRoomList())
            { 
                String desc = curRoom.getSimpleDesc().replaceAll("\n","");
                if (desc.length() > 61) {desc = desc.substring(0,60) + "...";} 
                if (desc.contains("event")) {return;}

                System.out.println(" Reference Number = " + refno + ", Description = " + desc);

                refno++;
            }
        }

        else if( Integer.parseInt(suffix) > -1 )  // Does the suffix contain any number greater than -1?
        {
            int refno = Integer.parseInt(suffix) - 1;

            if (refno < 0 || refno > Game.locations.getRoomList().size())
            {
                System.out.println("Invalid reference number");
            }
            else 
            {
                Room roomFound = Game.locations.getRoomList().get(refno);
                executeTeleport(roomFound);
            }
        }
        else 
        {
            Room roomFound = Game.locations.getRoom(suffix);
            executeTeleport(roomFound);
        }

    }
    
    private void executeTeleport(Room dest)
    {
        Game.alien.setLocation(dest);
        System.out.println("Teleport successful!");
        Game.actions.look();
    }

    private void cheatAddItem(String suffix)
    {
        if (suffix.contains("show"))
        {
            System.out.println("*** Item Names Available = ***");

            for (Item curItem : Game.locations.getFullItemList())
            {
                System.out.println("  " + curItem.getItemName());
            }
        }
        else 
        {
            Item itemFound = Game.locations.getItem(suffix);

            if (itemFound != null)
            {
                Game.alien.addItem(itemFound);
                System.out.println("Item copied into inventory successfully!  (" + itemFound.getItemName() + ") ");
            }
            else 
            {
                System.out.println("Item not found");
            }       
        }
    }

}

