package structures;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public LinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    public T search(T key) {
        if (isEmpty()) {
            return null;
        } else  {
            Node<T> current = first;
            while (current != null && !current.getData().equals(key)) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    public T remove(T data) {
        T result = null;
        if (isEmpty()) {
            return null;
        } else  {
            if(data.equals(first.getData())) {
                result = first.getData();
                first = first.getNext();
            } else if(data.equals(last.getData())) {
                result = last.getData();
                last = last.getNext();
            }  else {
                Node<T> current = first;
                while (!current.getNext().getData().equals(data) && current.getNext() != null) {
                    current = current.getNext();
                }
                result = current.getNext().getData();
                current.setNext(current.getNext().getNext());
            }
            size--;
        }
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }
}
