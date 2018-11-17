package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.List;
import java.util.Map;

// TODO: Remove references to Plip in javadoc.
public class Clorus extends Creature {

    /** red color. */
    private int r;

    @Override
    public String name() {
        return "clorus";
    }

    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /** Kill all plips! */
    public void attack(Creature c) {
        double victimsEnergy = c.energy();
        energy += victimsEnergy;
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.03;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy -= 0.01;
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Clorus replicate() {
        Clorus copy = new Clorus(energy() / 2);
        this.energy /= 2;
        return copy;
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        /* 1. No empty spaces. */
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        /* 2. Attack any Plips. */
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (plips.size() > 0) {
            int moveDir = StdRandom.uniform(plips.size());
            return new Action(Action.ActionType.ATTACK, plips.get(moveDir));
        }

        /* 3. Replicate. */
        if (energy >= 1) {
            int moveDir = StdRandom.uniform(empties.size());
            return new Action(Action.ActionType.REPLICATE, empties.get(moveDir));
        }

        /* 4. Move randomly. */
        int moveDir = StdRandom.uniform(empties.size());
        return new Action(Action.ActionType.MOVE, empties.get(moveDir));
    }
}
