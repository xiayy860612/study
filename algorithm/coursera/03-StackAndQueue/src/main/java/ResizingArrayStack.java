public class ResizingArrayStack<T> implements StackExample<T> {

    private Object[] array;
    private int index;

    public ResizingArrayStack(int capacity) {
        array = new Object[capacity];
        index = 0;
    }

    @Override
    public void push(T item) {
        // when it's full
        if (index == array.length) {
            resize(2 * array.length);
        }
        array[index++] = item;
    }

    @Override
    public T pop() {
        T item = (T) array[--index];
        array[index] = null;
        // when it's 1/4
        if (index == array.length/4) {
            resize(array.length/2);
        }
        return item;
    }

    @Override
    public int size() {
        return index;
    }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < index; ++i) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
}
