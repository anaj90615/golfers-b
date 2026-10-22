// Social golfers with DFS
public class Golf {
    Plan plan;
    boolean[][] taken;
    int[][] pairs;
    int g, p, w, n;

    public Golf(int g, int p, int w) {
        this.g = g;
        this.p = p;
        this.w = w;
        this.n = g * p;
        plan = new Plan(g, p, w);
        taken = new boolean[w][n];
        pairs = new int[n][n];
    }

    public boolean run() {
        // first week 0..n-1
        int x = 0;
        for (int gr = 0; gr < g; gr++) {
            for (int k = 0; k < p; k++) {
                put(0, gr, k, x++);
            }
        }
        return search(g * p);
    }

    boolean search(int index) {
        int perWeek = g * p;
        if (index == w * perWeek) {
            return true;
        }
        int wk = index / perWeek;
        int gr = (index % perWeek) / p;
        int k = index % p;

        for (int x = 0; x < n; x++) {
            if (!ok(wk, gr, k, x)) {
                continue;
            }
            put(wk, gr, k, x);
            if (search(index + 1)) {
                return true;
            }
            remove(wk, gr, k, x);
        }
        return false;
    }

    boolean ok(int wk, int gr, int k, int x) {
        if (taken[wk][x]) {
            return false;
        }
        // check if x already played with the group
        if (k > 0 && pairs[plan.cell[wk][gr][0]][x] > 0) {
            return false;
        }
        return true;
    }

    void put(int wk, int gr, int k, int x) {
        plan.cell[wk][gr][k] = x;
        taken[wk][x] = true;
        for (int i = 0; i < k; i++) {
            int y = plan.cell[wk][gr][i];
            pairs[x][y]++;
            pairs[y][x]++;
        }
    }

    void remove(int wk, int gr, int k, int x) {
        for (int i = 0; i < k; i++) {
            int y = plan.cell[wk][gr][i];
            pairs[x][y]--;
            pairs[y][x]--;
        }
        taken[wk][x] = false;
        plan.cell[wk][gr][k] = -1;
    }

    public static void main(String[] args) {
        int g = 8, p = 4, w = 5;
        if (args.length == 3) {
            g = Integer.parseInt(args[0]);
            p = Integer.parseInt(args[1]);
            w = Integer.parseInt(args[2]);
        }
        Golf s = new Golf(g, p, w);
        if (s.run()) {
            s.plan.print();
            System.out.println("score = " + s.plan.score());
        } else {
            System.out.println("no solution");
        }
    }
}
