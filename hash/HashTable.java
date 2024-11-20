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
        /* Initialize the table */
        this.table = new ArrayList<>(capacity);
        /* Updates size */
        size = 0;
        /* Create empty buckets */
        for (int i = 0; i < capacity; i++) {
            table.add(null);
        }
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
        /* Get the correct index for insertion */
        int indexOfKey = indexInHashTable(key);
        /* Get the first thing in the bucket */
        HashNode<K, V> container = table.get(indexOfKey);
        while (container != null) {
            if (container.getKey().equals(key)) {
                /* If the key is present, change its value */
                container.setValue(value);
                return;
            }
            container = container.next;
        }
        /* Updates size */
        size++;
        /* If the key is not present, once again, get the appropriate bucket for insertion */
        container = table.get(indexOfKey);
        /* Create the node */
        HashNode<K, V> insertNode = new HashNode<>(key, value);
        /* Inserting into the head of the list by first connecting the next pointer of the node for insertion to the original head */
        insertNode.next = container;
        /* Update the ArrayList with the new head */
        table.set(indexOfKey, insertNode);
        /* Rehash the table if the load factor is greater than or equal to 0.7 */
        if ((1.0 * size) / currentCapacity >= 0.75) {
            /* Store the current table temporarily */
            ArrayList<HashNode<K, V>> transfer = table;
            /* Clear the table */
            table = new ArrayList<>(currentCapacity * 2);
            /* Double its capacity */
            currentCapacity *= 2;
            /* Updates size */
            size = 0;
            /* Create empty buckets */
            for (int i = 0; i < currentCapacity; i++) {
                table.add(null);
            }
            /* Recursively insert the original nodes by traversing the ArrayList and going through every bucket in the list */
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
        /* Get the correct index for retrieval */
        int indexOfKey = indexInHashTable(key);
        /* Get the first thing in the bucket */
        HashNode<K, V> firstNode = table.get(indexOfKey);
        while (firstNode != null) {
            /* Find the key */
            if (firstNode.getKey().equals(key)) {
                /* Return the value */
                return firstNode.getValue();
            }
            /* Move on to the next node */
            firstNode = firstNode.next;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        /* TODO: IMPLEMENT THIS METHOD */
        /* Get the correct index for inquiry */
        int indexOfKey = indexInHashTable(key);
        /* Get the first thing in the bucket */
        HashNode<K, V> firstNode = table.get(indexOfKey);
        while (firstNode != null) {
            /* Find the key */
            if (firstNode.getKey().equals(key)) {
                /* From SimpleMap: only return true if this key is already mapped to a value */
                if (firstNode.getValue() != null) {
                    /* Return contains or not */
                    return true;
                }
            }
            /* Move on to the next node */
            firstNode = firstNode.next;
        }
        return false;
    }

    @Override
    public void remove(K key) {
        /* TODO: IMPLEMENT THIS METHOD */
        /* Get the correct index for removal */
        int indexOfKey = indexInHashTable(key);
        /* Get the first thing in the bucket */
        HashNode<K, V> firstNode = table.get(indexOfKey);
        /* Create a temporary node that stores the thing before the element we intend to delete */
        HashNode<K, V> prevNode = null;
        /* Update size */
        size--;
        while (firstNode != null) {
            if (firstNode.getKey().equals(key)) {
                firstNode.setValue(null);
            }
            /* Iterate throughout the linked hash nodes */
            prevNode = firstNode;
            firstNode = firstNode.next;
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
        int indexOfKey = hashResult % currentCapacity;
        if (indexOfKey < 0) {
            indexOfKey *= -1;
        }
        return indexOfKey;
    }

    /**
     * Calls the Java default HashCode method and preserves the hash code.
     *
     * @param key The key needed to be hashed.
     * @return The hash code of the given key.
     */
    private final int hashResults(K key) {
        return Objects.hashCode(key);
    }

    public void resize(){
        
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

