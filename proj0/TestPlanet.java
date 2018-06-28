/**
 * Prints out pairwise force between two arbitrary planets
 * to practice test writing. TODO: Verify against an expected
 * result.
 */
public class TestPlanet {

    public static void checkPairwiseForces() {
		Planet p1 = new Planet(0, 0, -1, 0, 1, "");
		Planet p2 = new Planet(p1);
		p2.xxPos = 5;
		StdOut.println("Pairwise force: (" + p1.calcForceExertedByX(p2) + ", " + p1.calcForceExertedByY(p2) + ")");
	}

    public static void main(String[] args) {
        checkPairwiseForces();
    }
}
