import spock.lang.Specification

import static DequeAtDefaultPackageAccessor.newDeque
import static DequeAtDefaultPackageAccessor.newIntegerDeque

class DequeSpec extends Specification {

    def 'Should initially create an empty queue'() {
        when:
        def deque = newDeque()

        then:
        deque.isEmpty()
        deque.size() == 0
    }

    def 'Should not be empty when item is added at the beginning'() {
        given:
        def deque = newDeque()

        when:
        deque.addFirst _

        then:
        !deque.isEmpty()
        deque.size() == 1

    }

    def 'Should not be empty when item is added at the end'() {
        given:
        def deque = newDeque()

        when:
        deque.addLast _

        then:
        !deque.isEmpty()
        deque.size() == 1

    }

    def 'Should fail when null item is added to either side of deque'() {
        given:
        def deque = newDeque()

        when:
        deque.addFirst null

        then:
        thrown NullPointerException

        when:
        deque.addLast null

        then:
        thrown NullPointerException
    }

    def 'Should fail when attempting to remove item from empty deque'() {
        given:
        def deque = newDeque()

        when:
        deque.removeFirst()

        then:
        thrown NoSuchElementException

        when:
        deque.removeLast()

        then:
        thrown NoSuchElementException
    }

    def 'Should return item and update size when removed from the beginning'() {
        given:
        def deque = newDeque()

        when:
        deque.addFirst _

        then:
        deque.removeFirst() == _
        deque.size() == 0
    }

    def 'Should return item and update size when removed from the end'() {
        given:
        def deque = newDeque()

        when:
        deque.addLast _

        then:
        deque.removeLast() == _
        deque.size() == 0
    }

    def 'Should return items added at the beginning from the beginning in correct order'() {
        given:
        def deque = newIntegerDeque()

        when:
        deque.addFirst 2
        deque.addFirst 1

        then:
        deque.removeFirst() == 1
        deque.removeFirst() == 2
    }

    def 'Should return items added at the end from the end in correct order'() {
        given:
        def deque = newIntegerDeque()

        when:
        deque.addLast 2
        deque.addLast 1

        then:
        deque.removeLast() == 1
        deque.removeLast() == 2
    }

    def 'Should return items added at the end from the beginning in correct order'() {
        given:
        def deque = newIntegerDeque()

        when:
        deque.addLast 1
        deque.addLast 2

        then:
        deque.removeFirst() == 1
        deque.removeFirst() == 2
    }

    def 'Should return items added at the beginning from the end in correct order'() {
        given:
        def deque = newIntegerDeque()

        when:
        deque.addFirst 1
        deque.addFirst 2

        then:
        deque.removeLast() == 1
        deque.removeLast() == 2
    }

    def 'Should not allow to remove from the end more items than the deque size'() {
        given:
        def deque = newDeque()
        deque.addFirst _
        deque.removeFirst()

        when:
        deque.removeLast()

        then:
        thrown NoSuchElementException
    }

    def 'Should not allow to remove from the beginning more items than the deque size'() {
        given:
        def deque = newDeque()
        deque.addFirst _
        deque.removeLast()

        when:
        deque.removeFirst()

        then:
        thrown NoSuchElementException
    }

    def "Should handle adding items again after being empty"() {
        given:
        def deque = newIntegerDeque()
        deque.addLast 1
        deque.addFirst 2
        deque.removeLast()
        deque.removeFirst()

        when:
        deque.addLast 3

        then:
        deque.size() == 1
        deque.removeFirst() == 3
        deque.size() == 0

    }

    def "Should return empty iterator when acquired from empty deque"() {
        given:
        def deque = newDeque()

        when:
        def iterator = deque.iterator()

        then:
        !iterator.hasNext()
    }

    def "Should return valid iterator when acquired from deque with one element"() {
        given:
        def deque = newDeque()
        deque.addFirst _

        when:
        def iterator = deque.iterator()

        then:
        iterator.hasNext()
        iterator.next() == _
        !iterator.hasNext()
    }

    def "Should return valid iterator when acquired from deque with two elements"() {
        given:
        def deque = newIntegerDeque()
        deque.addLast 1
        deque.addLast 2

        when:
        def iterator = deque.iterator()

        then:
        iterator.hasNext()
        iterator.next() == 1
        iterator.hasNext()
        iterator.next() == 2
        !iterator.hasNext()
    }

    def "Should handle multiple concurrent iterations using iterators"() {
        def deque = newIntegerDeque()
        deque.addLast 0
        deque.addLast 1
        def iterator1 = deque.iterator()
        def iterator2 = deque.iterator()

        expect:
        iterator1.next() == 0
        iterator2.next() == 0
    }

    def "Should fail when acquiring next item from empty iterator"() {
        given:
        def deque = newDeque()

        when:
        def iterator = deque.iterator()
        iterator.next()

        then:
        thrown NoSuchElementException
    }

    def "Should fail when acquiring next item from iterator and there is no more items to return"() {
        given:
        def deque = newDeque()
        deque.addFirst _

        when:
        def iterator = deque.iterator()
        iterator.next()

        then:
        noExceptionThrown()

        when:
        iterator.next()

        then:
        thrown NoSuchElementException
    }

    def "Should fail when invoking remove operation on iterator"() {
        given:
        def iterator = newDeque().iterator()

        when:
        iterator.remove()

        then:
        thrown UnsupportedOperationException
    }
}
