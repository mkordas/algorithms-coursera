public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        Quick.sort(points);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        Point[] points1 = new Point[]{p, q, r, s};
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            points1[0].drawTo(points1[points1.length - 1]);
                            for (int m = 0; m < points1.length - 1; m++) {
                                StdOut.print(points1[m]);
                                StdOut.print(" -> ");
                            }
                            StdOut.println(points1[points1.length - 1]);
                        }
                    }
                }
            }
        }
    }

}
