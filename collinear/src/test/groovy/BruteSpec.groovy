import groovy.transform.CompileStatic
import org.junit.Rule
import org.junit.contrib.java.lang.system.StandardOutputStreamLog
import spock.lang.Specification

class BruteSpec extends Specification {
    def "Main"() {
        expect:
        Brute.main('rs1423.txt')
        StdDraw.show(20000)
    }

    @Rule
    StandardOutputStreamLog standardOutputInterceptor = new StandardOutputStreamLog()

    def "Should print to console results from experiment on 6 points"() {
        when:
        Brute.main('input6.txt')

        then:
        def outputContents = standardOutputInterceptor.log
        def expectedResult = '''\
                             |(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)
                             |(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (32000, 10000)
                             |(14000, 10000) -> (18000, 10000) -> (21000, 10000) -> (32000, 10000)
                             |(14000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)
                             |(18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)
                             |'''.stripMargin().denormalize()

        outputContents == expectedResult
    }

    @CompileStatic
    def '1 divided by 2'() {
        def i = 1
        def s = '2'
        println i / s
        cout << 1 / * (int *)
        "2" << endl;
    }

    def "Should print to console results from experiment on 8 points"() {
        when:
        Brute.main('input8.txt')

        then:
        def outputContents = standardOutputInterceptor.log
        def expectedResult = '''\
                             |(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)
                             |(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000)
                             |'''.stripMargin().denormalize()

        outputContents == expectedResult

    }
}
