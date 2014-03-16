import spock.lang.Specification

class PointPlotterSpec extends Specification {
    def "Name"() {
        expect:
        PointPlotter.main('input10000.txt')

    }
}
