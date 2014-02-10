public class Percolation {

    private static final int VIRTUAL_TOP_SITE_INDEX = 0;
    private final int N;
    private final int virtualBottomSiteIndex;
    private final WeightedQuickUnionUF unionFindWithVirtualTopAndBottom;
    private final WeightedQuickUnionUF unionFindWithVirtualTop;
    private final boolean[][] openedSites;

    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        this.N = N;
        if (N < 1) {
            throw new IndexOutOfBoundsException();
        }
        virtualBottomSiteIndex = N * N + 1;
        unionFindWithVirtualTopAndBottom =
                new WeightedQuickUnionUF(virtualBottomSiteIndex + 1);
        unionFindWithVirtualTop =
                new WeightedQuickUnionUF(virtualBottomSiteIndex);
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
                unionSiteAt(i, j).withVirtualTopSite();
            }
            if (i == N) {
                unionSiteAt(i, j).withVirtualBottomSite();
            }
        }
    }

    private CreateUnionHelper unionSiteAt(int i, int j) {
        return new CreateUnionHelper(i, j);
    }

    private class CreateUnionHelper {
        private final int linearIndex;

        private CreateUnionHelper(int firstI, int firstJ) {
            linearIndex = linearIndexOf(firstI, firstJ);
        }

        private void withSiteAt(int secondI, int secondJ) {
            unionFindWithVirtualTopAndBottom
                    .union(linearIndex, linearIndexOf(secondI, secondJ));
            unionFindWithVirtualTop
                    .union(linearIndex, linearIndexOf(secondI, secondJ));
        }

        private void withVirtualTopSite() {
            unionFindWithVirtualTopAndBottom
                    .union(linearIndex, VIRTUAL_TOP_SITE_INDEX);
            unionFindWithVirtualTop
                    .union(linearIndex, VIRTUAL_TOP_SITE_INDEX);
        }

        private void withVirtualBottomSite() {
            unionFindWithVirtualTopAndBottom
                    .union(linearIndex, virtualBottomSiteIndex);
        }
    }


    private int linearIndexOf(int i, int j) {
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
        return unionFindWithVirtualTop.connected(0, linearIndexOf(i, j));
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
        return unionFindWithVirtualTopAndBottom.connected(0, N * N + 1);
    }
}