package structures;

public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
        size++;
    }

    public T pop() {
        if (isEmpty()) return null;

        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }

    public T peek() {
        return (top != null) ? top.getData() : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
