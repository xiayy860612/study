public class LinkedListStack<T> implements StackExample<T> {

    private Node<T> first;
    private int size;

    public LinkedListStack() {
        first = null;
        size = 0;
    }

    @Override
    public void push(T item) {
        Node<T> old = first;
        Node<T> newItem = new Node<>();
        newItem.item = item;
        newItem.next = old;
        first = newItem;
        ++size;
    }

    @Override
    public T pop() {
        T item = first.item;
        first = first.next;
        --size;
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
    }

}
