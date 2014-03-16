import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Element<Item> first;
    private Element<Item> last;

    private class Element<Item> {
        private Item item;
        private Element<Item> next;
        private Element<Item> previous;

        private Element(Item item) {
            this.item = item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Element<Item> element = new Element<Item>(item);
        if (first == null) {
            first = element;
            last = element;
        } else {
            first.previous = element;
            element.next = first;
            first = element;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Element<Item> element = new Element<Item>(item);
        if (last == null) {
            first = element;
            last = element;
        } else {
            last.next = element;
            element.previous = last;
            last = element;
        }
        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        size--;
        Element<Item> element = first;
        if (first == last) {
            Element<Item> lastElement = first;
            first = null;
            last = null;
            return lastElement.item;
        }
        first = first.next;
        first.previous = null;
        return element.item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        size--;
        Element<Item> element = last;
        if (first == last) {
            Element<Item> lastElement = first;
            first = null;
            last = null;
            return lastElement.item;
        }
        last = last.previous;
        last.next = null;
        return element.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Element<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
