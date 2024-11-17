// Name: Jerry Li
// Computing ID: mjs9qs
// Homework Name: HashTable.java
// Resources used: https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/ provided inspiration
package hash;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Hash Table implementation.
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> implements SimpleMap<K, V> {

    private static final int INITIAL_CAP = 5;  // a default initial capacity (set low for initial debugging)
    private int currentCapacity = INITIAL_CAP;

    /*
     * Here are some hints about how to declare your hash table.
     * If you're using an ArrayList, it might look like this:
     * 		private ArrayList<HashNode<K, V>> table;
     * Note that you cannot declare an array of generics (i.e., an array of HashNodes) like this:
     *          private LinkedList<HashNode<K,V>>[] table;
     * but see here https://programming.guide/java/generic-array-creation.html for workarounds.
     */

    /* YOU WILL LIKELY WANT MORE PRIVATE VARIABLES HERE */
    private ArrayList<HashNode<K, V>> table;
    private int size;


    public HashTable() {  // default constructor sets capacity to default value
        this(INITIAL_CAP);
    }

    public HashTable(int capacity) {  // constructor sets capacity to given value
        /* TODO: IMPLEMENT THIS METHOD */
        this.table = new ArrayList<>(capacity);
        size = 0;
        /*
         * Here are some hints about how to allocate memory for your hash table.
         * If you're using an array, it might look like this:
         * 		this.table = (HashNode<K,V>[]) new HashNode<?,?>[initialCapacity];
         * If you're using an ArrayList, it might look like this:
         * 		this.table = new ArrayList<>(capacity); // sets list's initial capacity
         */
    }

    public int getSize() {
        return size;
    }

    // insert() adds a new key/value pair if the key is not found, otherwise it replaces
    //    the existing key's value
    @Override
    public void insert(K key, V value) {
        /* TODO: IMPLEMENT THIS METHOD */
        int indexOfKey = indexInHashTable(key);
        int hashCode = hashResults(key);
        HashNode<K, V> container = table.get(indexOfKey);
        while (container != null) {
            if (container.getKey().equals(key) && container.hashCode() == hashCode) {
                container.setValue(value);
            }
            container = container.next;
        }
        size++;
        container = table.get(indexOfKey);
        HashNode<K, V> insertNode = new HashNode<>(key, value);
        insertNode.next = container;
        table.set(indexOfKey, insertNode);
        if ((1.0 * size) / currentCapacity >= 0.7) {
            ArrayList<HashNode<K, V>> transfer = table;
            table = new ArrayList<>();
            currentCapacity *= 2;
            size = 0;
            for (int i = 0; i < currentCapacity; i++)
                table.add(null);

            for (HashNode<K, V> headNode : transfer) {
                while (headNode != null) {
                    insert(headNode.getKey(), headNode.getValue());
                    headNode = headNode.next;
                }
            }
        }
    }

    @Override
    public V retrieve(K key) {
        /* TODO: IMPLEMENT THIS METHOD */
        int indexOfKey = indexInHashTable(key);
        int hashCode = hashResults(key);
        HashNode<K, V> firstNode = table.get(indexOfKey);
        while (firstNode != null) {
            if (firstNode.getKey().equals(key) && firstNode.hashCode() == hashCode) {
                return firstNode.getValue();
            }
            firstNode = firstNode.next;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        /* TODO: IMPLEMENT THIS METHOD */
        int indexOfKey = indexInHashTable(key);
        int hashCode = hashResults(key);
        HashNode<K, V> firstNode = table.get(indexOfKey);
        while (firstNode != null) {
            if (firstNode.getKey().equals(key) && firstNode.hashCode() == hashCode) {
                if (firstNode.getValue() != null) {
                    return true;
                }
            }
            firstNode = firstNode.next;
        }
        return false;
    }

    @Override
    public void remove(K key) {
        /* TODO: IMPLEMENT THIS METHOD */
        int indexOfKey = indexInHashTable(key);
        int hashCode = hashResults(key);
        HashNode<K, V> firstNode = table.get(indexOfKey);
        HashNode<K, V> prevNode = null;
        while (firstNode != null) {
            if (firstNode.getKey().equals(key) && hashCode == firstNode.hashCode()) {
                break;
            }
            prevNode = firstNode;
            firstNode = firstNode.next;
        }
        size--;
        if (prevNode != null) {
            prevNode.next = firstNode.next;
        } else {
            table.set(indexOfKey, firstNode.next);
        }
    }

    /* custom methods */

    /**
     * Returns the index of a given key in the table.
     *
     * @param key The key we intended to search for.
     * @return The corresponding index.
     */
    private int indexInHashTable(K key) {
        int hashResult = hashResults(key);
        int indexOfKey = hashResult % size;
        if (indexOfKey < 0) {
            indexOfKey *= -1;
        }
        return indexOfKey;
    }

    private final int hashResults(K key) {
        return Objects.hashCode(key);
    }

    /*
     * OPTIONAL HELPER METHODS: The next two methods will let you print out your
     * entire hash table, or let you make sure all keys that hash to a single
     * bucket's index get stored as they should in your table. You'll need to
     * implement the second method; it depends on how you store entries and
     * handle collisions. This is NOT required, but you may find it helpful when
     * debugging and testing your code.
     */

    public void printHashTable() {
        for (int idx = 0; idx < this.currentCapacity; ++idx) {
            System.out.print(idx + ": ");
            printEntriesByIndex(idx);
        }
    }

    private void printEntriesByIndex(int idx) {
        /*
         * To implement this method to help print out one bucket of your hash table, you need to determine:
         * a) If there are no key/value pairs in the bucket idx, print "no entries"
         * b) If there are key/value pairs at that bucket, use a loop to print each one.
         *    Best to use System.out.print() and not println() so they're all on one line.
         * c) At the end of that loop, do System.out.println() to print a new line.
         */
        System.out.println("Not yet implemented...");
        HashNode<K, V> firstNode = table.get(idx);
        if (firstNode != null) {
            while (firstNode != null) {
                System.out.print(firstNode.getKey() + " ");
                System.out.print(firstNode.getValue());
                System.out.println();
            }
        } else {
            System.out.println("No entries.");
        }
    }

}

