public class Percolation {

    private static final int TOPMOST_SITE_INDEX = 0;
    private final int N;
    private final int bottommostSiteIndex;
    private final WeightedQuickUnionUF unionFind;
    private final boolean[][] openedSites;

    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        this.N = N;
        if (N < 1) {
            throw new IndexOutOfBoundsException();
        }
        bottommostSiteIndex = N * N + 1;
        unionFind = new WeightedQuickUnionUF(bottommostSiteIndex + 1);
        openedSites = new boolean[N][N];
    }

    /**
     * open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            openedSites[i - 1][j - 1] = true;
            if (i > 1 && isOpen(i - 1, j)) {
                unionSiteAt(i, j).withSiteAt(i - 1, j);
            }
            if (i < N && isOpen(i + 1, j)) {
                unionSiteAt(i, j).withSiteAt(i + 1, j);
            }
            if (j > 1 && isOpen(i, j - 1)) {
                unionSiteAt(i, j).withSiteAt(i, j - 1);
            }
            if (j < N && isOpen(i, j + 1)) {
                unionSiteAt(i, j).withSiteAt(i, j + 1);
            }
            if (i == 1) {
                unionSiteAt(i, j).withTopmostSite();
            }
            if (i == N) {
                unionSiteAt(i, j).withBottommostSite();
            }
        }
    }

    private CreateUnionHelper unionSiteAt(int i, int j) {
        return new CreateUnionHelper(i, j);
    }

    private int flatIndex(int i, int j) {
        return (i - 1) * N + j;
    }


    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return openedSites[i - 1][j - 1];
    }

    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        return unionFind.connected(0, flatIndex(i, j));
    }

    private void validate(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {

        return unionFind.connected(0, N * N + 1);
    }

    private class CreateUnionHelper {
        private final int firstI;
        private final int firstJ;

        private CreateUnionHelper(int firstI, int firstJ) {
            this.firstI = firstI;
            this.firstJ = firstJ;
        }

        private void withSiteAt(int secondI, int secondJ) {
            unionFind.union(flatIndex(firstI, firstJ), flatIndex(secondI, secondJ));
        }

        private void withTopmostSite() {
            unionFind.union(flatIndex(firstI, firstJ), TOPMOST_SITE_INDEX);
        }

        private void withBottommostSite() {
            unionFind.union(flatIndex(firstI, firstJ), bottommostSiteIndex);
        }
    }
}