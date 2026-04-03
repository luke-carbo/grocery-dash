package group18;

import group18.mapCreation.Map_Builder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the Entity_Stationary class.
 */
public class Entity_Stationary_Test {

    /**
     * Create Testing Item {@link Item_Main}
     */
    private Item_Main Create_Main(int x, int y, int value) {
        Item_Main item = new Item_Main(null); // null Game is safe for collection tests
        item.setX(x);
        item.setY(y);
        item.value = value;
        return item;
    }

    /**
     * Create Testing Item {@link Item_Bonus}
     */
    private Item_Bonus Create_Bonus(int x, int y, int value) {
        Item_Bonus item = new Item_Bonus(null);
        item.setX(x);
        item.setY(y);
        item.value = value;
        return item;
    }

    /**
     * Create Testing Item {@link Item_Penalty}
     */
    private Item_Penalty Create_Penalty(int x, int y, int value) {
        Item_Penalty item = new Item_Penalty(null);
        item.setX(x);
        item.setY(y);
        item.value = value;
        return item;
    }

    // Tests Needed
    // General Tests (Getters / Setters) (Not really used as values are assigned on creation, however they exist and should be tested)

    // Getter Testing

    /**
     * Getter test for X.
     */
    @Test
    void test_get_X() {
        Item_Main item = Create_Main(100, 200, 50);
        assertEquals(100, item.getX());
    }

    /**
     * Getter test for Y.
     */
    @Test
    void test_get_Y() {
        Item_Main item = Create_Main(100, 200, 50);
        assertEquals(200, item.getY());
    }

    /**
     * Getter Testing for Value
     */
    @Test
    void test_get_value() {
        Item_Main item = Create_Main(0, 0, 75);
        assertEquals(75, item.getValue());
    }

    // Setter Testing

    /**
     * Setter Testing for X
     */
    @Test
    void test_set_X() {
        Item_Main item = Create_Main(0, 0, 50);
        item.setX(350);
        assertEquals(350, item.getX());
    }

    /**
     * Setter Testing for Y
     */
    @Test
    void test_set_Y() {
        Item_Main item = Create_Main(0, 0, 50);
        item.setY(250);
        assertEquals(250, item.getY());
    }

    /**
     * Setter Testing for Value
     */
    @Test
    void test_set_value() {
        Item_Main item = Create_Main(0, 0, 10);
        item.setValue(99);
        assertEquals(99, item.getValue());
    }

    // Spawn Entity Exceeding Limit

    /**
     * tests that main items can be spawned up to the main item limit
     */
    @Test
    void test_limit_main() {
        Game game = null;
        Map_Builder map_builder;
        List<Item_Main> Main_Items = new ArrayList<>();
        Spawn_Main main_spawner;
        game = game;
        map_builder = new Map_Builder();

        main_spawner = new Spawn_Main(game, map_builder);

        while (Main_Items.size() < Item.main_limit) {
            Main_Items.add(main_spawner.spawnMain());
        }

        assertEquals(Item.main_limit, Main_Items.size());
    }

    /**
     * tests that bonus items can be spawned up to the bonus item limit
     */
    @Test
    void test_limit_bonus() {
        Game game = null;
        Map_Builder map_builder;
        List<Item_Bonus> Bonus_Items = new ArrayList<>();
        Spawn_Bonus bonus_spawner;
        game = game;
        map_builder = new Map_Builder();

        bonus_spawner = new Spawn_Bonus(game, map_builder);

        while (Bonus_Items.size() < Item.bonus_limit) {
            Bonus_Items.add(bonus_spawner.spawnBonus());
        }

        assertEquals(Item.bonus_limit, Bonus_Items.size());
    }

    /**
     * tests that penalty items can be spawned up to the penalty item limit
     */
    @Test
    void test_limit_penalty() {
        Game game = null;
        Map_Builder map_builder;
        List<Item_Penalty> Penalty_Items = new ArrayList<>();
        Spawn_Penalty penalty_spawner;
        game = game;
        map_builder = new Map_Builder();

        penalty_spawner = new Spawn_Penalty(game, map_builder);

        while (Penalty_Items.size() < Item.penalty_limit) {
            Penalty_Items.add(penalty_spawner.spawnPenalty());
        }

        assertEquals(Item.penalty_limit, Penalty_Items.size());
    }

    // Entity Collection Logic

    /**
     * tests that the item is collected when the player overlaps it
     */
    @Test
    void test_collection_range_Y() {
        Item_Main item = Create_Main(100, 100, 50);
        // Player at same position, 30x30 — clearly overlaps 20x20 item
        int result = item.collectIfTouched(100, 100, 30, 30);
        assertEquals(50, result);
    }

    /**
     * tests that the item is not collected when the player does not overlap it
     */
    @Test
    void test_collection_range_N() {
        Item_Main item = Create_Main(100, 100, 50);
        // Player at same position, 30x30 — clearly overlaps 20x20 item
        int result = item.collectIfTouched(150, 150, 30, 30);
        assertEquals(0, result);
    }

    /**
     * tests the return value of the collectIfTouched method
     */
    @Test
    void test_return_value() {
        Item_Main item = Create_Main(100, 100, 50);
        assertEquals(50, item.collectIfTouched(100, 100, 30, 30));
    }

    /**
     * tests the collected flag
     */
    @Test
    void test_collected_flag() {
        Item_Main item = Create_Main(100, 100, 50);
        item.collectIfTouched(100, 100, 30, 30);
        assertTrue(item.collected);
    }

    /**
     * tests all keys
     */
    @Test
    void test_all_keys() {
        Item_Main a = Create_Main(0, 0, 50);
        Item_Main b = Create_Main(100, 100, 50);
        a.collectIfTouched(0, 0, 30, 30);
        b.collectIfTouched(100, 100, 30, 30);
        assertTrue(Item_Main.areAllCollected(List.of(a, b)));
    }

    /**
     * tests one key
     */
    @Test
    void test_one_key() {
        Item_Main a = Create_Main(0, 0, 50);
        Item_Main b = Create_Main(100, 100, 50);
        a.collectIfTouched(0, 0, 30, 30); // only a is collected
        assertFalse(Item_Main.areAllCollected(List.of(a, b)));
    }

    /**
     * tests no keys
     */
    @Test
    void test_no_keys() {
        Item_Main a = Create_Main(0, 0, 50);
        Item_Main b = Create_Main(100, 100, 50);
        assertFalse(Item_Main.areAllCollected(List.of(a, b)));
    }

    /**
     * tests mixed scoring
     */
    @Test
    void test_mixed_scoring() {
        Item_Main main   = Create_Main(100, 100, 50);
        Item_Bonus bonus  = Create_Bonus(200, 200, 7);
        Item_Penalty penalty  = Create_Penalty(300, 300, -4);

        int totalScore = 0;
        totalScore += main.collectIfTouched(100, 100, 30, 30);
        totalScore += bonus.collectIfTouched(200, 200, 30, 30);
        totalScore += penalty.collectIfTouched(300, 300, 30, 30);

        assertEquals(53, totalScore);
    }
}
