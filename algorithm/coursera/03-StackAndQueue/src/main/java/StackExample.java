public interface StackExample<T> {
    void push(T item);
    T pop();
    default boolean isEmpty() {
        return size() == 0;
    }
    int size();
}
