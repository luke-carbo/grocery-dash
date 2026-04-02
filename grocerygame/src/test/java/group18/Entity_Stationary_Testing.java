//package group18;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Entity_Stationary_Testing {
//
//    /**
//     * Reset static counters before every test so tests don't bleed into
//     * each other. Both counts start at 0 and limits stay at their defaults.
//     */
//    @BeforeEach
//    void Reset_Counters() {
//        Item.bonus_count = 0;
//        Item.penalty_count = 0;
//        Item.bonus_limit = 4;
//        Item.penalty_limit = 3;
//    }
//
//    /**
//     * Create Testing Item {@link Item_Main}
//     */
//    private Item_Main Create_Main(int x, int y, int value) {
//        Item_Main item = new Item_Main(null); // null Game is safe for collection tests
//        item.position_x = x;
//        item.position_y = y;
//        item.value = value;
//        return item;
//    }
//
//    /**
//     * Create Testing Item {@link Item_Bonus}
//     */
//    private Item_Bonus Create_Bonus(int x, int y, int value) {
//        Item_Bonus item = new Item_Bonus(null);
//        item.position_x = x;
//        item.position_y = y;
//        item.value = value;
//        return item;
//    }
//
//    /**
//     * Create Testing Item {@link Item_Penalty}
//     */
//    private Item_Penalty Create_Penalty(int x, int y, int value) {
//        Item_Penalty item = new Item_Penalty(null);
//        item.position_x = x;
//        item.position_y = y;
//        item.value = value;
//        return item;
//    }
//
//    // Tests Needed
//    // General Tests (Getters / Setters) (Not really used as values are assigned on creation, however they exist and should be tested)
//
//    // Getter Testing
//    @Test
//    void test_get_X() {
//        Item_Main item = Create_Main(100, 200, 50);
//        assertEquals(100, item.getX());
//    }
//
//    @Test
//    void test_get_Y() {
//        Item_Main item = Create_Main(100, 200, 50);
//        assertEquals(200, item.getY());
//    }
//
//    @Test
//    void test_get_value() {
//        Item_Main item = Create_Main(0, 0, 75);
//        assertEquals(75, item.getValue());
//    }
//
//    // Setter Testing
//    @Test
//    void test_set_X() {
//        Item_Main item = Create_Main(0, 0, 50);
//        item.setX(350);
//        assertEquals(350, item.getX());
//    }
//
//    @Test
//    void test_set_Y() {
//        Item_Main item = Create_Main(0, 0, 50);
//        item.setY(250);
//        assertEquals(250, item.getY());
//    }
//
//    @Test
//    void test_set_value() {
//        Item_Main item = Create_Main(0, 0, 10);
//        item.setValue(99);
//        assertEquals(99, item.getValue());
//    }
//
//    // Spawn Entity Exceeding Limit
//
//
//    // Spawn Entity Overlapping Other Entity (Will Fail) (No existing value or spawn check)
//
//
//    // Spawn Entity Overlapping Wall (Will Fail) (Spawn Check done in spawn structure not Item Data)
//
//
//    // Spawn Entity In Start / End Area (Is success / failure good or bad?)
//
//
//    // Spawn Entity On Player (Non Issue)
//
//
//    // Entity Collection Priority (Does Not Exist)
//
//
//}
