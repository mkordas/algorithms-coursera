import static PercolationGridOpener.State.O

class PercolationGridOpener {
    enum State {
        O, X
    }

    Percolation percolationGrid

    def with(Map states) {
        List<List<State>> pattern = states.pattern

        pattern.eachWithIndex { List<State> row, int i ->
            row.findIndexValues { it == O }.each {
                percolationGrid.open(i + 1, it + 1 as int)
            }
        }
    }

}