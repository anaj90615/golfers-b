import java.util.Arrays;

// the plan: cell[w][g][k] = golfer at place k of group g in week w
public class Plan {
    int weeks, groups, size, golfers;
    int[][][] cell;

    public Plan(int groups, int size, int weeks) {
        this.groups = groups;
        this.size = size;
        this.weeks = weeks;
        this.golfers = groups * size;
        cell = new int[weeks][groups][size];
        for (int[][] wk : cell) {
            for (int[] gr : wk) {
                Arrays.fill(gr, -1);
            }
        }
    }

    // how many times pairs play again together
    public int score() {
        int[][] seen = new int[golfers][golfers];
        int bad = 0;
        for (int w = 0; w < weeks; w++) {
            for (int g = 0; g < groups; g++) {
                int[] gr = cell[w][g];
                for (int i = 0; i < gr.length; i++) {
                    for (int j = i + 1; j < gr.length; j++) {
                        int a = Math.min(gr[i], gr[j]);
                        int b = Math.max(gr[i], gr[j]);
                        if (seen[a][b]++ >= 1) {
                            bad++;
                        }
                    }
                }
            }
        }
        return bad;
    }

    public void print() {
        for (int w = 0; w < weeks; w++) {
            System.out.print("Week " + (w + 1) + ":");
            for (int g = 0; g < groups; g++) {
                System.out.print(g == 0 ? " " : " | ");
                for (int k = 0; k < size; k++) {
                    if (k > 0) System.out.print(",");
                    System.out.print(cell[w][g][k]);
                }
            }
            System.out.println();
        }
    }
}
