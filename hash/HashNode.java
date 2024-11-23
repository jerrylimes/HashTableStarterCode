// Name: Jerry Li
// Computing ID: mjs9qs
// Homework Name: HashTable.java
// Resources used: None
package hash;

@SuppressWarnings("unchecked")
public class HashNode<K, V> {

    private K key;
    private V value;


    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return this.key.equals(((HashNode<K, V>) other).key);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V newValue) {
        this.value = newValue;
    }

    @Override
    public String toString() {
        return "(" + key + "," + value + ")";
    }
}
