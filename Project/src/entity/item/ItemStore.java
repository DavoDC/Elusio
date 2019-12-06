package entity.item;

import java.util.ArrayList;

import main.Globals;
import components.popups.Popup;
import components.structures.Map;
import components.structures.Player;
import entity.base.Entity;
import entity.base.EntityStore;
import entity.enemy.Enemy;
import entity.obstacle.Obstacle;
import entity.obstacle.ObstacleZone;
import org.newdawn.slick.Color;

/**
 * Handles multiple items
 *
 * @author David
 */
public class ItemStore extends EntityStore {

    /**
     * Create list of all game items
     *
     * @return
     */
    @Override
    public ArrayList<Entity> getEntities() {

        // Create list
        ArrayList<Entity> itemList = new ArrayList<>();

        // Add magic items
        itemList.add(new UsableItem(
                "Cryocapacitor Set",
                "Its storing microamounts of nearby energy as antimatter",
                "It could annihilate some weak, dry matter once activated",
                "The cryocapacitors annihilated the dead trees!",
                23, 25));
        itemList.add(new UsableItem(
                "Magistructor Orb",
                "Inside, it beats rhythmically with creative vigor",
                "Upon activation, it could manifest more of an existing substance",
                "The orb created a magistruct replica of the nearby wood!",
                82, 5));

        // Add limestone items
        itemList.add(new Item(
                "LimestoneSample",
                "Hmmm .. Looks like high calcium limestone",
                2, 28));
        itemList.add(new UsableItem(
                "AcidGun",
                "A rifle that uses some sort of acerbic fluid",
                "The fluid could corrode certain materials",
                "The rifle's acid reacted with the carbonate!",
                21, 3));

        // Add crystals
        addCrystals(itemList);

        // Add ship gold
        itemList.add(new ProtectedItem(
                "ShipGold",
                "We have all the electrovelox substitute we could ever need!",
                "Now we can finally repair me! Come back to me",
                "Ship",
                10, 74, 3, 6) {
            @Override
            public void afterAction() {
                Globals.player.adjustX(Globals.tileSize * 6);
            }
        });

        // Add gold coin
        itemList.add(new Item(
                "GoldCoins",
                new String[]{
                    "Hmmm .. a pile of metal discs .. ",
                    "Hold the phone! This is a substitute for electrovelox!",
                    "If you find more of it, we could repair me and go home!"
                },
                50, 79));

        // Add speed modifiers
        addSpeedItems(itemList);

        // Add clock for teleporting back to starting location
        itemList.add(new InstantItem(
                "DigitalClock",
                new String[]{
                    "A digital time-keeper .. ",
                    "Thats strange ... It has no Higgs boson field",
                    "Wow! It teleported you !"
                },
                12, 54) {
            @Override
            public void activateEffect(Player player) {
                player.adjustY(-34 * 64);

            }
        });

        // Return list
        return itemList;

    }

    /**
     * Add crystals
     *
     * @param itemList
     */
    private void addCrystals(ArrayList<Entity> itemList) {

        itemList.add(new Item(
                "Crystal12",
                "This crystal has a high, specific EMR frequency .. 1122Hz",
                "Somehow it seems that .. it is longing for something... ",
                65, 6));

        itemList.add(new ProtectedItem(
                "Crystal3",
                "This crystal has an EMR signature of 333Hz",
                "It .. misses the presence of something... ",
                "Viridash",
                30, 33));

        itemList.add(new ProtectedItem(
                "Crystal6",
                "The crystal emanates a particular EMR frequency .. 666Hz",
                "It is longing for something... ",
                "Trevil",
                89, 41));

        itemList.add(new ProtectedItem(
                "Crystal9",
                "A particular EMR frequency is being emitted by this crystal.. 999Hz",
                "This crystal yearns to join something ... ",
                "Mycovolence",
                71, 81));

    }

    /**
     * Add speed altering items
     *
     * @param itemList
     */
    private void addSpeedItems(ArrayList<Entity> itemList) {

        // Add MP3 player that gives small speed increase
        itemList.add(new InstantItem(
                "MP3Player",
                "Using ESP, I can perceive sound waves emanating!",
                "The arrangement of them makes me feel energized! Try it!",
                34, 1) {
            @Override
            public void activateEffect(Player player) {
                player.changeMovSpeed(0.07f);
            }
        });

        // Add wrist watch that gives small speed increase
        itemList.add(new InstantItem(
                "Wristwatch",
                "This looks like it came from a dimension where time goes faster...",
                "Wearing it will bring some of that aspect nearby",
                37, 87) {
            @Override
            public void activateEffect(Player player) {
                player.changeMovSpeed(0.08f);
            }
        });

        // Add ring that gives small speed increase
        itemList.add(new InstantItem(
                "Alacrity Ring",
                "This ring is enchanted with alacrity essence",
                "I recommend wearing it!",
                59, 50) {
            @Override
            public void activateEffect(Player player) {
                player.changeMovSpeed(0.08f);
            }
        });

        // Add syringe that gives huge speed increase
        itemList.add(new InstantItem(
                "STH06Syringe",
                new String[]{
                    "A common stimulant named SanicTH06",
                    "Its highly potent. Be careful... I'm receiving something ",
                    "I just heard " + quote("BigDaz") + " say " + quote("You skitz druggo") + "!",
                    "That was really strange ...",},
                3, 44) {
            @Override
            public void activateEffect(Player player) {
                player.changeMovSpeed(player.getMovSpeed());
            }
        });

        // Add cigarette that slows down player greatly
        itemList.add(new InstantItem(
                "Cigarette",
                "Psionically embued with the name: Two Number 9s",
                "Not sure what this might do ... ",
                79, 37) {
            @Override
            public void activateEffect(Player player) {
                player.changeMovSpeed(-player.getMovSpeed() / 2);
            }
        });
    }

    /**
     * Return the given string in quotes
     *
     * @param in
     * @return
     */
    private String quote(String in) {
        return ('"' + in + '"');
    }

    /**
     * Get the layer which holds the entities/items
     *
     * @return
     */
    @Override
    public String getEntLS() {
        return "Items";
    }

    /**
     * Attempt to grab an item
     */
    public void processItemGrab() {

        // Attempt to retrieve item under player
        Item itemFound = (Item) getItemUnder(Globals.player);

        // If an item was found, and hasn't been found before
        if (itemFound != null && !itemFound.isFound()) {

            // Set the item as found
            itemFound.setFound(true);

            // Hide item
            addEncounter(itemFound);

            // Add item to inventory
            Globals.player.addItem(itemFound);

            // Do extra processing for protected items
            if (itemFound instanceof ProtectedItem) {

                // Get protector name
                String protectorName = ((ProtectedItem) itemFound).
                        getProtector();

                // Get enemy/protector 
                Enemy enemy = Globals.enemyStore.getEnemy(protectorName);

                // Make enemy act
                enemy.doAction();

                // Hide encountered enemy
                Globals.enemyStore.addEncounter(enemy);

                // Unblock enemy
                Globals.map.unblockEntity(enemy);

            }

            // Do extra processing of instant items
            if (itemFound instanceof InstantItem) {

                // Activate item effect
                ((InstantItem) itemFound).activateEffect(Globals.player);
            }

            // Show item popup
            showItemPopup(itemFound);
        }
    }

    /**
     * Get the item under the player, or null otherwise
     *
     * @param alien
     * @return The item or null
     */
    public Item getItemUnder(Player alien) {

        // Get entity
        Entity entityFound = super.getEntityUnder(alien);

        // If entity is an item
        if (entityFound instanceof Item) {

            // Return it
            return ((Item) entityFound);
        }

        // Return null is nothing or not an item
        return null;
    }

    /**
     * Show information about an item as a popup
     *
     * @param item
     */
    private void showItemPopup(Item item) {

        // Calculate adjustment from camera
        int camRadj = 3;
        int camCadj = 2;
        int camYadj = camRadj * Globals.tileSize;
        int camXadj = camCadj * Globals.tileSize;

        // Calculate actual position
        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

        // Account for special teleportation cases
        String name = item.getName().toLowerCase();
        if (name.contains("clock")) {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj - 34 * 64);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
        } else if (name.contains("shipgold")) {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj + 6 * 64);
            item.afterAction();
        }

        // Generate and show popup
        Popup itemInfo = getItemPopup(item, r, c);
        Globals.hud.loadPopup(itemInfo);
    }

    /**
     * Get a popup that describes a given item
     *
     * @param item
     * @param r
     * @param c
     * @return
     */
    public Popup getItemPopup(Item item, int r, int c) {

        // Set features of popup
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(r);  // Tile grid row
        feats.add(c);  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        feats.add(Color.black); // Text color

        // Create popup lines 
        ArrayList<String> itemLines = item.getInfoLines();
        ArrayList<String> newLines = new ArrayList<>();
        String start = "(Ehecatl, telepathically): ";
        for (String curItemLine : itemLines) {
            newLines.add(start + curItemLine);
        }
        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");

        // Return
        return (new Popup(feats, newLines));
    }

    /**
     * Attempt to use all items
     */
    private void processItemUse() {

        // Get the obstacle zone the player is currently inside, if any
        ObstacleZone obZone = Globals.obStore.getZoneUnder(Globals.player);

        // If (locked obstacle found) && (player has key item of zone)
        if (((obZone != null) && (obZone.isLocked()))
                && (Globals.player.hasItem(obZone.getKeyItem()))) {

            // Move player if they have picked up ShipGold item
            if (obZone.getKeyItem().equals("ShipGold")) {
                obZone.afterAction();
                return;
            }

            // Set obstacle zone as unlocked
            obZone.setLocked(false);

            // Get the zone's matching obstacle(s)
            ArrayList<Obstacle> obstacles = Globals.obStore.getLinkedObstacles(obZone);

            // For every matching obstacle
            for (Obstacle obst : obstacles) {

                // Hide the obstacle
                Globals.obStore.addEncounter(obst);

                // Get obstacle name
                String obN = obst.getName();

                // Increase crystal count for magic gate
                if (obN.contains("Gate")) {
                    Globals.obStore.crystalPlaced();
                }

                // Calculate adjustment from camera
                int camRadj = 3;
                int camCadj = 2;
                int camYadj = camRadj * Globals.tileSize;
                int camXadj = camCadj * Globals.tileSize;

                // Calculate actual position
                int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
                int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

                // Show popups after item use
                System.out.println("#### TEST:  " + obst.getName() + 
                        " , " + obst.getOZName());
                showUsagePopup(obN, "Tree", "Cryo", r, c);
                showUsagePopup(obN, "Lime", "Acid", r, c);
                showUsagePopup(obN, "Water", "Orb", r, c);

                // Unblock the obstacle
                if (obst.isUnblockOn()) {
                    Globals.map.unblockEntity(obst);
                }
            }

        }
    }

    /**
     * Show usable item popup after it is grabbed
     *
     * @param obN The obstacle's full name
     * @param obQ Part of the obstacle's name
     * @param itemName Item name subset
     * @param r Row position of popup
     * @param c Column position of popup
     */
    private void showUsagePopup(String obN, String obQ, String itemName, int r, int c) {

        // If obstacle name contains query
        if (obN.contains(obQ)) {

            // Get item
            UsableItem item = (UsableItem) Globals.player.getItemByName(itemName);

            // Load popup for item
            Globals.hud.loadPopup(getUsagePopup(item, r, c));
        }
    }

    /**
     * Get a popup describing what a usable item just did
     *
     * @param item
     * @param r
     * @param c
     * @return
     */
    public Popup getUsagePopup(Item item, int r, int c) {

        // Set features of popup
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(r);  // Tile grid row
        feats.add(c);  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        feats.add(Color.black); // Text color

        // Create popup lines 
        ArrayList<String> newLines = new ArrayList<>();
        String start = "(Ehecatl, telepathically): ";
        newLines.add(start + ((UsableItem) item).getUseLine());
        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");

        // Return
        return (new Popup(feats, newLines));
    }

}
