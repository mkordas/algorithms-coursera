public class Subset {

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < count; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }

}
