package creatures;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;
import java.awt.Color;

import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class   
 *  @authr FIXME
 */

public class TestPlip {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip original = new Plip(2);
        Plip replica = original.replicate();
        assertNotSame(replica, original);
        assertEquals(2 * 0.5, replica.energy(), 0.00);
        assertEquals(2 * 0.5, original.energy(), 0.00);

    }

    @Test
    public void testChoose() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!  

        /* Case 1: Stay when surrounded. */
        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        /* Case 2: Test Plip replicates. */
        surrounded.put(Direction.RIGHT, new Empty());
        actual = p.chooseAction(surrounded);
        expected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(expected, actual);

        /* Case 3: Run away from cloruses. */
        p = new Plip(0.9);
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        surrounded.put(Direction.BOTTOM, new Clorus(1.0));
        surrounded.put(Direction.LEFT, new Clorus(1.0));

        List<Action> choices = new ArrayList<>();
        for (int i = 0; i < 50; i += 1) {
            actual = p.chooseAction(surrounded);
            if (!choices.contains(actual)) {
                choices.add(actual);
            }
        }

        Action expected1 = new Action(Action.ActionType.MOVE, Direction.TOP);
        Action expected2 = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        assertTrue(choices.contains(expected1));
        assertTrue(choices.contains(expected2));

        /* Case 4: Plip is tired. */
        Plip tiredPlip = new Plip(0.1);
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        actual = tiredPlip.chooseAction(surrounded);
        expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
