package entity.item;

import base.Globals;
import entity.item.types.InstantItem;
import entity.item.types.KeyItem;
import java.util.ArrayList;

import components.modules.Player;
import entity.base.Entity;
import entity.base.EntityStore;
import entity.item.types.CrystalItem;
import entity.item.types.SpeedItem;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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

        // STARTING AREA KEYS
        itemList.add(new KeyItem(
                "Cryocapacitor Set",
                new String[]{"Stores microamounts of nearby energy as antimatter.",
                    "Could annihilate some weak, dry matter once activated.",
                    "Usage: The cryocapacitor annihilated the dead trees!"},
                "cryoCap",
                null,
                23, 25));
        itemList.add(new KeyItem(
                "Magistructor Orb",
                new String[]{"Beats rhythmically with creative vigor,",
                    "able to manifest more of an existing substance.",
                    "Usage: The orb created a magistruct replica of the wood nearby!"},
                "magiCreation",
                null,
                82, 7));

        // LIMESTONE ITEMS
        itemList.add(new Item(
                "Limestone Rocks",
                new String[]{"Limestone (calcium carbonate) fragments"},
                2, 28));

        itemList.add(new KeyItem(
                "Gastric Gun",
                new String[]{"A rifle filled with some sort of unpleasant acerbic fluid,",
                    "that likely has corrosive properties.",
                    "Usage: Good thinking! Acid + Metal Carbonate = Salt + Water + CO2!"},
                "acidAction",
                null,
                21, 3));

        // CRYSTALS
        itemList.add(new CrystalItem(
                "Destiny", 1122,
                "Focus your attention positively, on solutions.",
                null, 65, 6));
        itemList.add(new CrystalItem(
                "Protection", 333,
                "Light protects you every step of the way.",
                "Dracoflame", 30, 33));
        itemList.add(new CrystalItem(
                "Alignment", 666,
                "Thoughts -> Words -> Actions -> Habits.",
                "Trevil", 89, 41));
        itemList.add(new CrystalItem(
                "Growth", 999,
                "Your truest self is a shining light.",
                "Mycovolence", 71, 81));

        // GOLD
        itemList.add(new KeyItem(
                "Treasure",
                new String[]{"Highly conductive metal.",
                    "Usage: This is just what I needed for my repairs!"},
                "coinChink",
                "SailingShip",
                10, 74, 3, 6) {
            @Override
            public boolean doAction() {

                // Enter ending state
                Globals.sbg.enterState(Globals.states.get("Exit"),
                        // Leave
                        new FadeOutTransition(Color.black, 5000),
                        // Enter
                        new FadeInTransition(Color.black, 5000)
                );

                // Return true, always succeeds
                return true;
            }
        });

        itemList.add(new Item(
                "Metal Discs",
                new String[]{"Small conductive discs"},
                50, 79));

        // SPEED ITEMS
        itemList.add(new SpeedItem(
                "MP3 Player",
                "Sound waves are emanating in an energizing manner!",
                "powerUpBee",
                34, 1, 0.08f));
        itemList.add(new SpeedItem(
                "Doc Brown's Wristwatch",
                "Embued with energy that brings one closer to the future",
                "powerUpBub",
                37, 87, 0.08f));
        itemList.add(new SpeedItem(
                "Alacrity Ring",
                "Enchanted with alacrity essence",
                "powerUpBee",
                59, 50, 0.08f));
        itemList.add(new SpeedItem(
                "Syringe of STH06",
                "A highly potent stimulant",
                "powerUpTung",
                3, 44, 0.20f));
        itemList.add(new SpeedItem(
                "Big Smoke",
                "Psionically embued with the phrase: Two Number 9s",
                "alienHurt",
                79, 37, -0.16f));

        //  TELEPORTING CLOCK
        itemList.add(new InstantItem(
                "Mobius Clock",
                new String[]{
                    "A digital time-keeper with no Higgs boson field",
                    "Usage: You were teleported!"
                },
                "teleport",
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
     * Get the layer which holds the entities/items
     *
     * @return
     */
    @Override
    public String getEntLS() {
        return "Items";
    }

}
