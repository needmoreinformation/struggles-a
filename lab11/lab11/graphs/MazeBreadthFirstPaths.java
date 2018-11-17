package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private boolean targetFound;
    private Maze maze;
    private int source;
    private int target;

    private void initSingleSource() {
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        initSingleSource();
        targetFound = false;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> fringe = new LinkedList<>();
        fringe.add(source);

        while (!fringe.isEmpty()) {
            int u = fringe.poll();

            marked[u] = true;
            announce();

            if (u == target) {
                targetFound = true;
                return;
            }

            for (int v : maze.adj(u)) {
                if (!marked[v]) {
                    edgeTo[v] = u;
                    distTo[v] = distTo[u] + 1;
                    fringe.offer(v);
                    announce();
                }
            }

        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

