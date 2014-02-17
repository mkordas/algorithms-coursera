import spock.lang.Specification
import spock.lang.Unroll

import static PercolationStatsWith.create
import static java.lang.Double.NaN
import static java.lang.Math.sqrt
import static spock.util.matcher.HamcrestMatchers.closeTo
import static spock.util.matcher.HamcrestSupport.expect

@Newify([PercolationStats])
@Unroll
class PercolationStatsSpec extends Specification {

    def epsilon = 1e-10

    def "Should perform simple computational experiment on percolation grid"() {
        given:
        def percolationGrid = Mock(Percolation)

        when:
        create PercolationStats using percolationGrid ofSize 1
        then:
        1 * percolationGrid.isOpen(1, 1) >> false
        1 * percolationGrid.open(1, 1)
        1 * percolationGrid.percolates() >> true
    }


    def "Should perform non-trivial computational experiment on percolation grid"() {
        given:
        def percolationGrid = Mock(Percolation)

        when:
        create PercolationStats using percolationGrid ofSize 2

        then:
        4 * percolationGrid.isOpen(_ as Integer, _ as Integer) >>> [false, true, true, false]
        2 * percolationGrid.open(_ as Integer, _ as Integer)
        2 * percolationGrid.percolates() >>> [false, true]
    }

    def "Should perform computational experiments on multiple percolation grids"() {
        given:
        Percolation[] percolationGrids = [Mock(Percolation), Mock(Percolation), Mock(Percolation)]

        when:
        create PercolationStats using percolationGrids ofSize 1

        then:
        percolationGrids.each {
            1 * it.percolates() >> true
        }
    }

    def "Should return mean for one computational experiment"() {
        given:
        def percolationGrid = Mock(Percolation)

        when:
        def percolationStats = create PercolationStats using percolationGrid ofSize 3

        then:
        percolationGrid.percolates() >>> [false] * 2 + [true]
        expect percolationStats.mean(), closeTo(3 / 3**2, epsilon)
    }

    def "Should return proper mean after performing computational experiments"() {
        given:
        Percolation[] percolationGrids = [Mock(Percolation), Mock(Percolation), Mock(Percolation)]
        percolationGrids[0].percolates() >>> [false, true]
        percolationGrids[1].percolates() >>> [false, true]
        percolationGrids[2].percolates() >>> [false, false, true]

        when:
        def percolationStats = create PercolationStats using percolationGrids ofSize 2

        then:
        2 * percolationGrids[0].open(_ as Integer, _ as Integer)
        2 * percolationGrids[1].open(_ as Integer, _ as Integer)
        3 * percolationGrids[2].open(_ as Integer, _ as Integer)
        expect percolationStats.mean(), closeTo((2 + 2 + 3) / 2**2 / 3, epsilon)
    }

    def "Should return not a number standard deviation for one computational experiment"() {
        given:
        def percolationGrid = Mock(Percolation)

        when:
        def percolationStats = create PercolationStats using percolationGrid ofSize 1

        then:
        percolationGrid.percolates() >> true
        percolationStats.stddev() == NaN
    }

    def "Should return proper standard deviation after performing computational experiments"() {
        given:
        Percolation[] percolationGrids = [Mock(Percolation), Mock(Percolation)]
        percolationGrids[0].percolates() >>> [false, true]
        percolationGrids[1].percolates() >>> [false, false, true]

        when:
        def percolationStats = create PercolationStats using percolationGrids ofSize 2

        then:
        def mean = percolationStats.mean()
        def variance = ((2 / 2**2 - mean)**2 + (3 / 2**2 - mean)**2) / (2 - 1)
        def standardDeviation = sqrt(variance)
        expect percolationStats.stddev(), closeTo(standardDeviation, epsilon)
    }

    def "Should return not a number confidence intervals for one computational experiment"() {
        given:
        def percolationGrid = Mock(Percolation)

        when:
        def percolationStats = create PercolationStats using percolationGrid ofSize 1

        then:
        percolationGrid.percolates() >> true
        percolationStats.confidenceLo() == NaN
        percolationStats.confidenceHi() == NaN
    }

    def "Should return proper confidence intervals after performing computational experiments"() {
        given:
        Percolation[] percolationGrids = [Mock(Percolation), Mock(Percolation)]
        percolationGrids[0].percolates() >>> [false, true]
        percolationGrids[1].percolates() >>> [false, false, true]

        when:
        def percolationStats = create PercolationStats using percolationGrids ofSize 2

        then:
        def mean = percolationStats.mean()
        def standardDeviation = percolationStats.stddev()
        expect percolationStats.confidenceLo(), closeTo(mean - 1.96 * standardDeviation / sqrt(2), epsilon)
        expect percolationStats.confidenceHi(), closeTo(mean + 1.96 * standardDeviation / sqrt(2), epsilon)
    }

    def "Should fail when created with #N and #T parameters"() {
        when:
        PercolationStats(N, T)

        then:
        thrown IllegalArgumentException

        where:
        N  | T
        0  | 1
        1  | 0
        -1 | -1
    }
}
