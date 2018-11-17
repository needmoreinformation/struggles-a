package lab11.graphs;

import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private int heuristic;
    private int sourceX, sourceY;
    private int targetX, targetY;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int x = maze.toX(v);
        int y = maze.toY(v);
        return Math.abs(x - targetX) + Math.abs(y - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class Node {
        int key;
        int priority;

        public Node(int key, int priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        PriorityQueue<Node> fringe = new PriorityQueue<>();
        fringe.offer(new Node(s, h(s)));

        while (!fringe.isEmpty()) {
            Node n = fringe.poll();

            

        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

