public class DequeAtDefaultPackageAccessor {

    public static <Item> Deque<Item> newDeque() {
        return new Deque<Item>();
    }

    public static Deque<Integer> newIntegerDeque() {
        return new Deque<Integer>();
    }
}
