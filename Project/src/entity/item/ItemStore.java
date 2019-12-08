package entity.item;

import java.util.ArrayList;

import main.Globals;
import components.structures.Player;
import entity.base.Entity;
import entity.base.EntityStore;

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
        itemList.add(new KeyItem(
                "Cryocapacitor Set",
                "Its storing microamounts of nearby energy as antimatter",
                "It could annihilate some weak, dry matter once activated",
                "The cryocapacitors annihilated the dead trees!",
                23, 25));
        itemList.add(new KeyItem(
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
        itemList.add(new KeyItem(
                "AcidGun",
                "A rifle that uses some sort of acerbic fluid",
                "The fluid could corrode certain materials",
                "The rifle's acid reacted with the carbonate!",
                21, 3));

        // Add crystals
        addCrystals(itemList);

        // Add ship gold
        itemList.add(new ProtectedItem(
                "Treasure",
                "We have all the electrovelox substitute we could ever need!",
                "Now we can finally repair me! Come back to me",
                "Ship",
                10, 74, 3, 6) {
            @Override
            public boolean doAction() {
                //Globals.hud.loadPopup(getEndPopup());
                Globals.player.adjustX(Globals.tileSize * 6);
                return true;
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
            public void applyEffect(Player player) {
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
            public void applyEffect(Player player) {
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
            public void applyEffect(Player player) {
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
            public void applyEffect(Player player) {
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
            public void applyEffect(Player player) {

                // Increase speed by 50%
                player.changeMovSpeed(player.getMovSpeed() / 2);
            }
        });

        // Add cigarette that slows down player greatly
        itemList.add(new InstantItem(
                "Cigarette",
                "Psionically embued with the name: Two Number 9s",
                "Not sure what this might do ... ",
                79, 37) {
            @Override
            public void applyEffect(Player player) {

                // Decrease speed by 50%
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
}
