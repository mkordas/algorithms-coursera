class PercolationStatsWith {
    static create(Class aClass) {
        assert aClass == PercolationStats
        new UsingHelper()
    }
}

class UsingHelper {
    static using(Percolation... percolations) {
        new OfSizeHelper(percolationGrids: percolations)
    }
}

class OfSizeHelper {
    Percolation[] percolationGrids

    def ofSize(int n) {
        def field = PercolationStats.getDeclaredField('percolationMocks')
        field.setAccessible(true)
        field.set(null, percolationGrids)
        def percolationStats = new PercolationStats(n, percolationGrids.length)
        field.set(null, null)
        percolationStats
    }
}
