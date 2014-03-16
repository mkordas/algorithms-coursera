import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_SIZE = 1;
    private Item[] items;
    private int size;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_SIZE];
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the queue
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (items.length == size) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, copy, 0, size);
        items = copy;
    }

    /**
     * delete and return a random item
     */
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == items.length / 4) {
            resize(items.length / 2);
        }
        --size;
        swapWithRandomPreviousItem(items, size);
        Item itemToReturn = items[size];
        items[size] = null;
        return itemToReturn;
    }

    /**
     * return (but do not delete) a random item
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(items, size);
    }

    /**
     * unit testing
     */
    public static void main(String[] args) {
    }

    private static <Item> void swapWithRandomPreviousItem(Item[] items, int index) {
        int randomIndex = StdRandom.uniform(index + 1);
        Item temp = items[index];
        items[index] = items[randomIndex];
        items[randomIndex] = temp;
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final Item[] items;
        private int size;

        private RandomizedQueueIterator(Item[] items, int size) {
            this.items = items.clone();
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            swapWithRandomPreviousItem(items, size - 1);
            return items[--size];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
