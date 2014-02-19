import spock.lang.Specification

import static PercolationGridOpener.State.O
import static PercolationGridOpener.State.X

@Newify([Percolation])
class PercolationSpec extends Specification {

    Percolation percolationGrid

    def "Should fail when percolation grid is created with size less than 1"() {
        when:
        Percolation 0

        then:
        thrown IndexOutOfBoundsException
    }

    def "Should have four sites blocked when created with size 2"() {
        when:
        percolationGrid = Percolation 2

        then:
        !percolationGrid.isOpen(1, 1)
        !percolationGrid.isOpen(1, 2)
        !percolationGrid.isOpen(2, 1)
        !percolationGrid.isOpen(2, 2)
    }

    def "Should not percolate when no sites was opened"() {
        when:
        percolationGrid = Percolation 1

        then:
        !percolationGrid.percolates()
    }

    def "Should report open site when site was opened"() {
        given:
        percolationGrid = Percolation 1

        when:
        percolationGrid.open 1, 1

        then:
        percolationGrid.isOpen 1, 1
    }

    def "Should report open site when site was opened twice"() {
        given:
        percolationGrid = Percolation 1

        when:
        percolationGrid.open 1, 1
        percolationGrid.open 1, 1

        then:
        percolationGrid.isOpen 1, 1
    }

    def "Should percolate and report full site when the only site is opened"() {
        given:
        percolationGrid = Percolation 1

        when:
        percolationGrid.open 1, 1

        then:
        percolationGrid.percolates()
        percolationGrid.isFull 1, 1
    }

    def "Should not percolate when path is opened on diagonal"() {
        given:
        percolationGrid = Percolation 2

        when:
        percolationGrid.open 1, 1
        percolationGrid.open 2, 2

        then:
        !percolationGrid.percolates()
    }

    def "Should percolate when path is opened from top to bottom on right side"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 1, 3
        percolationGrid.open 2, 3
        percolationGrid.open 3, 3

        then:
        percolationGrid.percolates()
    }

    def "Should percolate when path is opened from bottom to top in the middle"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 3, 2
        percolationGrid.open 2, 2
        percolationGrid.open 1, 2

        then:
        percolationGrid.percolates()
    }

    def "Should mark as full unfinished paths at top"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 1, 1
        percolationGrid.open 1, 3

        then:
        percolationGrid.isFull 1, 1
        percolationGrid.isFull 1, 3
    }


    def "Should not mark as full unfinished paths at bottom"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 3, 2
        percolationGrid.open 3, 3

        then:
        !percolationGrid.isFull(3, 1)
        !percolationGrid.isFull(3, 2)
        !percolationGrid.isFull(3, 3)
    }

    def "Should mark site as full when path is turning right"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 1, 1
        percolationGrid.open 2, 1
        percolationGrid.open 2, 2

        then:
        percolationGrid.isFull 2, 2
    }

    def "Should mark site as full when path is turning left"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 1, 3
        percolationGrid.open 2, 3
        percolationGrid.open 2, 2

        then:
        percolationGrid.isFull 2, 2
    }

    def "Should not mark site as full when there is no path open from top"() {
        given:
        percolationGrid = Percolation 3

        when:
        percolationGrid.open 1, 1
        percolationGrid.open 2, 1
        percolationGrid.open 3, 1
        percolationGrid.open 3, 3

        then:
        percolationGrid.percolates()
        1..3.each { i ->
            2..3.each { j ->
                assert !percolationGrid.isFull(i, j)
            }
        }
    }

    def "Should fail when percolation grid is queried about invalid indices"() {
        given:
        percolationGrid = Percolation 1

        when:
        percolationGrid."$operation" i, j

        then:
        thrown IndexOutOfBoundsException

        where:
        i | j | operation
        0 | 1 | 'open'
        1 | 0 | 'open'
        0 | 2 | 'open'
        0 | 2 | 'open'
        0 | 1 | 'isOpen'
        1 | 0 | 'isOpen'
        2 | 1 | 'isOpen'
        1 | 2 | 'isOpen'
        0 | 1 | 'isFull'
        1 | 0 | 'isFull'
        2 | 1 | 'isFull'
        1 | 2 | 'isFull'
    }

    def "Should percolate when valid complex path is opened"() {
        given:
        percolationGrid = Percolation 5

        when:
        open percolationGrid with pattern: [
                [O, X, X, X, X],
                [O, X, O, O, O],
                [O, O, O, X, O],
                [X, X, X, O, O],
                [X, X, X, O, X],
        ]

        then:
        percolationGrid.percolates()
    }

    def "Should not percolate when invalid complex path is opened"() {
        given:
        percolationGrid = Percolation 5

        when:
        open percolationGrid with pattern: [
                [X, O, O, O, O],
                [O, X, X, O, O],
                [O, O, O, X, O],
                [O, O, O, X, O],
                [O, O, O, O, X],
        ]

        then:
        !percolationGrid.percolates()
    }

    private static open(def what) {
        new PercolationGridOpener(percolationGrid: what)
    }

}

