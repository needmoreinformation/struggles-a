package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public int[] edgeTo2;

    private int s;
    private int t;
    boolean cycleFound = false;
    private boolean targetFound = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        edgeTo2 = new int[edgeTo.length];
        edgeTo2[s] = s;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void dfs(int v, int parent) {
        if (cycleFound) {
            return;
        }
        marked[v] = true;

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo2[w] = v;
                distTo[w] = distTo[v] + 1;
                dfs(w, v);
                if (targetFound) {
                    return;
                }
            } else {
                /* Check marked neighbour is not a parent. */
                if (w != parent) {
                    edgeTo[w] = v;
                    cycleFound = true;

                    /* Traverse parents until the original vertex is reached. */
                    int x = v;
                    while (x != w) {
                        edgeTo[x] = edgeTo2[x];
                        x = edgeTo2[x];
                    }

                    announce();
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        dfs(s, s);
    }

    // Helper methods go here
}

