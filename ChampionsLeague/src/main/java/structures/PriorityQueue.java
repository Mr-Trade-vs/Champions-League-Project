package structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<T> {
    private final List<T> heap;
    private final Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void add(T data) {
        heap.add(data);
        heapifyUp(heap.size() - 1);
    }

    public T remove() {
        if (heap.isEmpty()) {
            return null;
        }

        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return max;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int getSize() {
        return heap.size();
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && comparator.compare(heap.get(index), heap.get(parent)) > 0) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int leftChild, rightChild, largest;
        while (true) {
            leftChild = 2 * index + 1;
            rightChild = 2 * index + 2;
            largest = index;

            if (leftChild < heap.size() && comparator.compare(heap.get(leftChild), heap.get(largest)) > 0) {
                largest = leftChild;
            }

            if (rightChild < heap.size() && comparator.compare(heap.get(rightChild), heap.get(largest)) > 0) {
                largest = rightChild;
            }

            if (largest == index) {
                break;
            }

            swap(index, largest);
            index = largest;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
