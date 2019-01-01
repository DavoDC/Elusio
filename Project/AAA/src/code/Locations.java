import java.util.ArrayList;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Creates room locations
 *
 * @author David Charkey
 */
public class Locations
{
    private Room ship = new Room();
    private Room clearing = new Room();
    private Room hut = new Room();
    private Room village = new Room();

    // Jungle rooms
    private Room jnorth = new Room();
    private Room jmid = new Room();
    private Room jsouth = new Room();
    private Room jwest = new Room();

    // Temple rooms
    private Room tempent = new Room();
    private Room tN = new Room();
    private Room tW = new Room();
    private Room tE = new Room();
    private Room tNW = new Room();
    private Room tSW = new Room();
    private Room tNE = new Room();
    private Room tSE = new Room();
    private Room tchamber = new Room();
    private Room apex = new Room();

    // Cave rooms
    private Room burrow = new Room();
    private Room cmid = new Room();
    private Room cleft = new Room();
    private Room cright = new Room();
    private Room cback = new Room();
    private Room cintf = new Room();

    // South rooms
    private Room jsand = new Room();
    private Room rpath1 = new Room();
    private Room rpath2 = new Room();
    private Room ruins = new Room();
    private Room beach = new Room();
    private Room conqship = new Room();

    // Special dev rooms
    private Room events = new Room();
    private Room constr = new Room();

    // Lists
    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();

    /**
     * Constructor for objects of class Locations
     */
    public Locations()
    {
        addRoomDescs();
        addRoomExits();
        addRoomItems();
        addRoomImages();

        toggleAnimalEvents(true);

        fillRoomList();
        fillItemList();
    }

    private void addRoomDescs()
    {
        ship.addDesc("You find yourself at the crashed ship from whence you came.");

        jnorth.addDesc("The north part of the jungle.");
        jmid.addDesc("The middle of a dense jungle.");
        jsouth.addDesc("The south part of the jungle.");
        jwest.addDesc("The west part of the jungle. There is a fast-flowing river here.");

        clearing.addDesc("An open space, a clearing in the vast jungle.");

        hut .addDesc("Inside a hut with straw roof and clay bricks.");

        village.addDesc("A native Aztec village!.");

        tempent.addDesc("Just inside the entry of a grand temple.");
        tN.addDesc("Temple - North.");
        tW.addDesc("Temple - West.");
        tE.addDesc("Temple - East.");
        tNW.addDesc("Temple - NorthWest." );
        tSW.addDesc("Temple - SouthWest.");
        tNE.addDesc("Temple - NorthEast.");
        tSE.addDesc("Temple - SouthEast.");
        tchamber.addDesc("Temple - Central Chamber.");
        apex.addDesc("Within the pinnacle of the temple, above the central chamber.There is a healing aura.");

        burrow.addDesc("A deep hole beneath the hut.");
        cmid.addDesc ("The middle of the cave system.");
        cleft.addDesc("The left side of the cave system.");
        cright.addDesc ("The right side of the cave system.");
        cback.addDesc ( "\n" + 
            "The back of the cave system. There is a mural here." + "\n" +
            "There is a phrase in my language!  Translation = " + "\n" +
            "* Raindrops falling, but dont be down. A magnificent sight will turn your frown upside down *" + "\n" +
            "* Simulate that sight to incite delight *"  + "\n"
        );
        cintf.addDesc("A small cavern with a waterfall ahead.");

        jsand.addDesc("The far south part of the jungle.The ground is sandier here.");
        rpath1.addDesc("An ancient, paved path in a sandy area.");
        rpath2.addDesc("The forgotten path continues.");
        ruins.addDesc("Ancient ruins from a bygone era. A puzzle is here.");
        beach.addDesc("A picturesque sandy beach. There is a sailing ship anchored fairly close to shore.");
        conqship.addDesc("A Spanish conquistador ship.");

        events.addDesc("events.");
        constr.addDesc("constructables.");
    }

    /**
     * Add room exits
     */
    private void addRoomExits()
    {
        ship.addExit("tojungle", jmid); 

        jmid.addExit("north" , jnorth);
        jmid.addExit("toship" , ship);
        jmid.addExit("south", jsouth);
        jmid.addExit("west", jwest);

        jnorth.addExit("toclearing", clearing);
        jnorth.addExit("tojungle" , jmid);

        jsouth.addExit("north", jmid);
        jsouth.addExit("south", jsand);

        jwest.addExit("tojungle", jmid);

        clearing.addExit("tohut" , hut);
        clearing.addExit("totemple" , tempent);
        clearing.addExit("tovillage" , village);
        clearing.addExit("intojungle" , jnorth);

        hut.addExit("outside", clearing);
        village.addExit("toclearing", clearing);

        tempent.addExit("tochamber" , tchamber);
        tempent.addExit("east" , tSE);
        tempent.addExit("outside" , clearing);
        tempent.addExit("west" ,tSW);

        tchamber.addExit("north" , tN);
        tchamber.addExit("east" , tE);
        tchamber.addExit("toentrance" , tempent);
        tchamber.addExit("west" ,tW);

        tN.addExit("east", tNE);
        tN.addExit("tochamber", tchamber);
        tN.addExit("west" , tNW);
        tW.addExit("north", tNW);
        tW.addExit("tochamber", tchamber);
        tW.addExit("south" , tSW);
        tE.addExit("north", tNE);
        tE.addExit("south", tSE);
        tE.addExit("tochamber" , tchamber);
        tNW.addExit("east" , tN);
        tNW.addExit("south" , tW);
        tSW.addExit("north" , tW);
        tSW.addExit("toentrance", tempent);
        tNE.addExit("south" , tE);
        tNE.addExit("west" , tN);
        tSE.addExit("north" , tE);
        tSE.addExit("toentrance" , tempent);

        burrow.addExit("tocave", cmid);
        cmid.addExit("right" , cright);
        cmid.addExit("left", cleft);
        cmid.addExit("forward", cback);

        cright.addExit("tocave", cmid);

        cleft.addExit("tocave", cmid);

        cback.addExit("tocave", cmid);

        jsand.addExit("back", jsouth);
        jsand.addExit("topath", rpath1);
        jsand.addExit("tobeach", beach);

        rpath1.addExit("forward", rpath2);
        rpath1.addExit("back", jsand);

        rpath2.addExit("forward", ruins);
        rpath2.addExit("back", rpath1);

        ruins.addExit("back", rpath2);

        beach.addExit("back", jsand);
    }

    private void addRoomItems()
    {
        ship.addItem
        (new Item 
            ("DamagedSpaceship","The 3 damaged repulsors each require some conductive metal for repair" , "Fixed"));

        ship.addItem(new Item ("Blaster", "Your weapon, capable of firing one plasma burst", "W"));
        ship.addItem(new Item ("BioRestorer", "A medical marvel able to heal remarkably", "H"));

        jnorth.addItem(new Item ("Branch", "A fallen branch, straight and sturdy", "Spr"));

        jwest.addItem(new Item ("Leaf", "A fallen leaf. It has a strong smell", "Sal")); 

        clearing.addItem(new Item ("Stone", "A heavy stone"));

        hut.addItem(new Item ("Mortar+Pestle", "Used to make medicines", "Sal"));

        tempent.addItem(new Item ("Spearhead" , "A spearhead from a native", "Spr")); 

        tchamber.addItem (new Item ("AncientPedestal", 
                "A monolith with a familiar resonator seal. Enscribed on the front is a narrow hexagon,", "Fixed"));

        tN.addItem(new Item("SmallShard1", "A piece of crystal, with the approximate shape of a half-cone", "Crs"));

        tW.addItem(new Item ("SmallShard2", "A piece of crystal, appearing to be the other half of a cone" , "Crs"));

        tE.addItem(new Item("CrystalCore", "A crystal that roughly cylindrical and jagged on each end", "Crs"));

        tNW.addItem(new Item("Earring" , "A gold earring ... probably dropped by a native"));

        tSW.addItem(new Item("Purple Pebble" , "A purple pebble of unknown origin"));

        tNE.addItem(new Item("Mask" , "An intricate dragon mask , with Aztec aesthetic "));

        tSE.addItem( new Item ("Totem" , "A turtle totem with a roughly pentagonal shape"));

        cleft.addItem(new Item("RedP", "A red pebble", "R"));
        cleft.addItem(new Item("GreenP", "A green pebble" , "R"));
        cleft.addItem(new Item("Rabbit", "A cute, harmless rabbit. It may have dug under the hut", "Fixed"));

        cmid.addItem(new Item("OrangeP" , "An orange pebble" , "R"));
        cmid.addItem(new Item("VioletP", "A violet pebble" , "R"));

        cright.addItem(new Item("YellowP" , "A yellow pebble" , "R"));
        cright.addItem(new Item("BlueP", "A blue pebble" , "R"));

        apex.addItem(new Item("Gold" , "Pure Aztec gold", "Gold"));

        jsand.addItem(new Item("TatteredBook", "No pages, but there is a title: 'Estudio Científico Ruina Antigua' ") ); //Ancient Ruin Scientific Study

        rpath1.addItem(new Item("BookPage1", "There are some scribbles: 'Primero esta abajo, segundo es asesinado por agua' ") );
        rpath2.addItem(new Item("BookPage2", "Frustrated scribbles read: 'Tercero es más ligero, cuarto ... desconocido' ") );
        ruins.addItem(new Item("PuzzleGate" , "A stone structure with a puzzle mechanism") );
        beach.addItem(new Item("Shell" , "A seashell that washed up"));
        conqship.addItem(new Item("CannonBall", "Ammunition for the Juanita"));

        events.addItem(new Item("RestoredBlaster", "Your weapon, capable of firing one plasma burst", "W"));
        events.addItem(new Item("Conquistador Sword" , "A well crafted sword stolen from invaders", "W"));
        events.addItem
        (new Item("NativeCrystal" , "A magical cone-shaped crystal protected by the natives for generations", "Crs"));
        events.addItem( apex.getItemAL().get(0) ); //gold
        events.addItem( new Item("AntigravityBelt" , "A device that can negate the gravity of a small mass") ); 
         events.addItem( apex.getItemAL().get(0) ); //gold
        

        constr.addItem(new Item("Spear", "A formidable spear you made" , "W"));
        constr.addItem(new Item("Salve", "A surprisingly potent medicine" , "H"));
        constr.addItem(new Item("CrystalKey", "An amazing crystal you reconstructed" , "Key"));

    }

    public void addRoomImages()
    {
        ship.setImage("ship");

        jmid.setImage("jungleM");
        jnorth.setImage("jungleN");
        jsouth.setImage("jungleS"); 
        jwest.setImage("jungleW");

        clearing.setImage("clearing");
        hut.setImage("hut");
        village.setImage("village");

        tN.setImage("tN");
        tW.setImage("tW");
        tE.setImage("tE");
        tempent.setImage("tempent");
        tNW.setImage("tNW");
        tSW.setImage("tSW");
        tNE.setImage("tNE");
        tSE.setImage("tSE");
        tchamber.setImage("tchamber"); 
        apex.setImage("apex");

        burrow.setImage("burrow");
        cintf.setImage("cintf");
        cleft.setImage("cleft");
        cmid.setImage("cmid");

        cright.setImage("cright");
        cback.setImage("cback");

        jsand.setImage("jsand");
        rpath1.setImage("rpath1");
        rpath2.setImage("rpath2");
        ruins.setImage("ruins");
        beach.setImage("beach");
        conqship.setImage("conqship");

        events.setImage("");
        constr.setImage("");

    }

    public void fillRoomList()
    {
        roomList.add(ship);

        roomList.add(jnorth);
        roomList.add(jmid);
        roomList.add(jsouth);
        roomList.add(jwest);

        roomList.add(clearing); 
        roomList.add(hut);
        roomList.add(village);

        roomList.add(tempent);
        roomList.add(tN);
        roomList.add(tW);
        roomList.add(tE);
        roomList.add(tNW);
        roomList.add(tSW);
        roomList.add(tNE);
        roomList.add(tSE);
        roomList.add(tchamber);

        roomList.add(apex);
        roomList.add(burrow);
        roomList.add(cmid);
        roomList.add(cleft);
        roomList.add(cright);
        roomList.add(cback);
        roomList.add(cintf);

        roomList.add(jsand);
        roomList.add(rpath1);
        roomList.add(rpath2);
        roomList.add(ruins);
        roomList.add(beach);
        roomList.add(conqship);

        roomList.add(events);
        roomList.add(constr);
    }

    public void fillItemList()
    {
        for (Room curRoom : roomList)
        {
            itemList.addAll(curRoom.getItemAL());
        }
    }

    public ArrayList<Room> getRoomList()
    {
        return roomList;
    }

    public ArrayList<Item> getFullItemList()
    {
        return itemList;
    }

    public Room getRoom(String roomdesc)
    {
        roomdesc = roomdesc.toLowerCase().replaceAll(" ", "");

        for (Room curRoom : roomList)
        { 
            String desc = curRoom.getSimpleDesc().toLowerCase().replaceAll(" ", "");
            if (desc.contains(roomdesc))
            {
                return curRoom;
            }
        }

        return null;
    }

    public Item getItem(String itemname)
    {
        itemname = itemname.toLowerCase().replaceAll(" ", "");

        for (Item curItem : itemList)
        { 
            String name = curItem.getItemName().toLowerCase().replaceAll(" ", "");
            if (name.contains(itemname))
            {
                return curItem;
            }
        }

        return null;
    }

    public void toggleAnimalEvents(boolean startingUp)
    {
        if (jnorth.getEvent() != null)
        {
            jnorth.eventFinished();
            jsouth.eventFinished();
            tN.eventFinished();
            tW.eventFinished();
            tE.eventFinished();

            System.out.println("\n" + "Animal encounters are now off" + "\n");
        }
        else 
        {
            jnorth.addEvent("You encounter a powerful jaguar!");
            jsouth.addEvent("You encounter a tiger!");
            tN.addEvent("You encounter a belligerent boar!");
            tW.addEvent("A sinister snake appears!");
            tE.addEvent("A massive mountain lion materialises!");

            if (!startingUp)
            {
                System.out.println("\n" + "Animal encounters are now on" + "\n");
            }
        }
    }

}
