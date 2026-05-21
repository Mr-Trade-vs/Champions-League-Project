package structures;

public class HashTable<T> {
    private int size = 150;
    private LinkedList<T> [] hashTable;

    public HashTable() {
        this.hashTable = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    private int hash(T key) {
        int hash = Math.abs(key.hashCode());
        return hash % hashTable.length;
    }

    public void put(T key, T value) {
        int hash = hash(key);

        if (hashTable[hash] == null){
            hashTable[hash] = new LinkedList<>();
        }
        hashTable[hash].add(value);
    }

    public T search(T key) {
        int hash = hash(key);
        return hashTable[hash].search(key);
    }

    public T remove(T key) {
        int hash = hash(key);
        return hashTable[hash].remove(key);
    }

    private void rehash() {
        LinkedList<T> [] oldTable = hashTable;
        size *= 2;
        hashTable = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            hashTable[i] = new LinkedList<>();
        }

        for (LinkedList<T> list : oldTable) {
            Node<T> current = list.getFirst();
            while (current != null) {
                T data = current.getData();
                put(data, data);
                current = current.getNext();
            }
        }
    }
}
