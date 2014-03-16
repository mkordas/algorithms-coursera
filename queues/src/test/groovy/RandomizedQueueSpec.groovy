import spock.lang.Specification

class RandomizedQueueSpec extends Specification {
    def "Should return item that was previously added"() {
        given:
        def randomizedQueue = new RandomizedQueue()

        when:
        randomizedQueue.enqueue _

        then:
        !randomizedQueue.empty
        randomizedQueue.dequeue() == _
        randomizedQueue.empty
    }

    def "Should return in random order two previously added items"() {
        given:
        def randomizedQueue = new RandomizedQueue<Integer>()

        when:
        randomizedQueue.enqueue 1
        randomizedQueue.enqueue 2
        def firstResult = randomizedQueue.dequeue()
        def secondResult = randomizedQueue.dequeue()

        then:
        firstResult == 1 && secondResult == 2 || firstResult == 2 && secondResult == 1
        randomizedQueue.size() == 0
    }

    def "Should return in random order previously added items"() {
        def randomizedQueue = new RandomizedQueue<Integer>()
        def numbers = 1..10
        numbers.each {
            randomizedQueue.enqueue(it)
        }

        when:
        10.times {
            numbers -= randomizedQueue.dequeue()
        }

        then:
        numbers.size() == 0
        randomizedQueue.empty
    }

    def "Should return the same item when sampled queue with one item"() {
        def randomizedQueue = new RandomizedQueue<Integer>()
        randomizedQueue.enqueue 1

        expect:
        randomizedQueue.sample() == 1
        randomizedQueue.sample() == 1
    }

    def "Should return and not dequeue random item"() {
        given:
        def randomizedQueue = new RandomizedQueue<Integer>()
        (1..4).each { randomizedQueue.enqueue(it) }

        when:
        def sampleItem = randomizedQueue.sample()

        then:
        randomizedQueue.size() == 4
        (1..4).contains(sampleItem)
    }

    def "Should return valid iterator for queue with one item"() {
        given:
        def randomizedQueue = new RandomizedQueue()
        randomizedQueue.enqueue _

        when:
        def iterator = randomizedQueue.iterator()

        then:
        iterator.hasNext()
        iterator.next() == _
        !iterator.hasNext()
    }

    def "Should return valid iterator for queue with two items"() {
        given:
        def randomizedQueue = new RandomizedQueue()
        randomizedQueue.enqueue _

        when:
        def iterator = randomizedQueue.iterator()

        then:
        iterator.hasNext()
        iterator.next() == _
        !iterator.hasNext()
    }

    def "Should fail on attempt to add null item"() {
        given:
        def randomizedQueue = new RandomizedQueue()

        when:
        randomizedQueue.enqueue null

        then:
        thrown NullPointerException
    }

    def "Should fail when deque is performed on empty queue"() {
        given:
        def randomizedQueue = new RandomizedQueue()

        when:
        randomizedQueue.dequeue()

        then:
        thrown NoSuchElementException

        when:
        randomizedQueue.enqueue _
        randomizedQueue.dequeue()

        then:
        noExceptionThrown()

        when:
        randomizedQueue.dequeue()

        then:
        thrown NoSuchElementException
    }

    def "Should fail when sample is performed on empty queue"() {
        given:
        def randomizedQueue = new RandomizedQueue()

        when:
        randomizedQueue.sample()

        then:
        thrown NoSuchElementException

        when:
        randomizedQueue.enqueue _
        randomizedQueue.dequeue()
        randomizedQueue.sample()

        then:
        thrown NoSuchElementException
    }

    def "Should fail when remove operation called on iterator"() {
        given:
        def randomizedQueue = new RandomizedQueue()
        def iterator = randomizedQueue.iterator()

        when:
        iterator.remove()

        then:
        thrown UnsupportedOperationException
    }

    def "Should fail when next item requested from iterator and there are no more items to return"() {
        given:
        def randomizedQueue = new RandomizedQueue()
        def iterator = randomizedQueue.iterator()

        when:
        iterator.next()

        then:
        thrown NoSuchElementException
    }

}
