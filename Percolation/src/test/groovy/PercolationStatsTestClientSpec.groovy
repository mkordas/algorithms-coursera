import org.junit.Rule
import org.junit.contrib.java.lang.system.StandardOutputStreamLog
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString as contains
import static spock.util.matcher.HamcrestSupport.expect

class PercolationStatsTestClientSpec extends Specification {

    @Rule
    StandardOutputStreamLog standardOutputInterceptor = new StandardOutputStreamLog()

    def "Should contain test client"() {
        when:
        PercolationStats.main(['PercolationStats', '200', '100'] as String[])

        then:
        def standardOutputContents = standardOutputInterceptor.log
        expect standardOutputContents, contains('mean')
        expect standardOutputContents, contains('stddev')
        expect standardOutputContents, contains('95% confidence interval')
    }

}
