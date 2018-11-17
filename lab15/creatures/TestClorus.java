package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/** Tests the Clorus class   
 *  @authr FIXME
 */

public class TestClorus {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Clorus clorus = new Clorus(2);
        assertEquals(2, clorus.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), clorus.color());
        clorus.move();
        assertEquals(1.97, clorus.energy(), 0.01);
        clorus.move();
        assertEquals(1.94, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.93, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.92, clorus.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus clorus = new Clorus(1.2);
        Plip plip = new Plip(1.0);
        clorus.attack(plip);
        assertEquals(2.2, clorus.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus original = new Clorus(2);
        Clorus replica = original.replicate();
        assertNotSame(replica, original);
        assertEquals(2 * 0.5, replica.energy(), 0.00);
        assertEquals(2 * 0.5, original.energy(), 0.00);
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!  

        /* Case 1: Stay when surrounded. */
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        /* Case 2: Attack Plips. */
        c = new Clorus(1.2);
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.RIGHT, new Plip(0.3));
        actual = c.chooseAction(surrounded);
        expected = new Action(Action.ActionType.ATTACK, Direction.RIGHT);

        assertEquals(expected, actual);

        /* Case 3: Replicate. */
        c = new Clorus(1.0);
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        surrounded.put(Direction.BOTTOM, new Clorus(1.0));
        surrounded.put(Direction.LEFT, new Clorus(1.0));

        List<Action> choices = new ArrayList<>();
        for (int i = 0; i < 50; i += 1) {
            actual = c.chooseAction(surrounded);
            if (!choices.contains(actual)) {
                choices.add(actual);
            }
        }

        Action expected1 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        Action expected2 = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);
        assertTrue(choices.contains(expected1));
        assertTrue(choices.contains(expected2));

        /* Case 4: Clorus moves randomly. */
        c = new Clorus(0.5);
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        surrounded.put(Direction.BOTTOM, new Clorus(1.0));
        surrounded.put(Direction.LEFT, new Clorus(1.0));

        choices = new ArrayList<>();
        for (int i = 0; i < 50; i += 1) {
            actual = c.chooseAction(surrounded);
            if (!choices.contains(actual)) {
                choices.add(actual);
            }
        }

        expected1 = new Action(Action.ActionType.MOVE, Direction.TOP);
        expected2 = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        assertTrue(choices.contains(expected1));
        assertTrue(choices.contains(expected2));
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
} 
