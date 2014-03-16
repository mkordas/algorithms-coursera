import spock.lang.Specification

import static java.lang.Double.NEGATIVE_INFINITY
import static java.lang.Double.POSITIVE_INFINITY

@Newify(Point)
class PointSpec extends Specification {
    def "Should compare points lexicographically"() {
        def point0 = Point x0, y0
        def point1 = Point x1, y1

        expect:
        point0.compareTo(point1) == result

        where:
        x0 | y0 | x1 | y1 | result
        0 | 0 | 0 | 0 | 0
        9 | 0 | 8 | 1 | -1
        8 | 0 | 9 | 1 | -1
        8 | 1 | 9 | 0 | 1
        3 | 0 | 4 | 0 | -1
        4 | 0 | 3 | 0 | 1
    }

    def "Should return slope between two points"() {
        def point0 = Point x0, y0
        def point1 = Point x1, y1

        expect:
        point0.slopeTo(point1) == result

        where:
        x0 | y0 | x1 | y1 | result
        0  | 0  | 1  | 1  | 1
        -9 | -9 | 9  | 9  | 1
        0  | 0  | 1  | 2  | 2
        0  | 0  | 2  | -3 | -1.5
        0  | 0  | 0  | 0  | NEGATIVE_INFINITY
        0  | 0  | 0  | 1  | POSITIVE_INFINITY
        0  | 0  | 1  | 0  | 0
        0  | 0  | -1 | 0  | 0
    }

    def "Should define slope comparator"() {
        def point0 = Point 0, 0
        def comparator = point0.SLOPE_ORDER

        expect:
        comparator.compare(new Point(x1, y1), new Point(x2, y2)) == result

        where:
        x1 | y1 | x2 | y2 | result
        1 | 1 | 1 | 2  | -1
        1 | 2 | 2 | -3 | 1
        1 | 1 | 1 | 1  | 0
    }
}
