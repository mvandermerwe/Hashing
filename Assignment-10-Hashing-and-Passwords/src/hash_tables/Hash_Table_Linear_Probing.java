package hash_tables;

import java.util.ArrayList;

import static hash_tables.Primes.next_prime;

/**
 * @author H. James de St. Germain, April 2007 # Adapted by Erin Parker to
 *         accept generic items for keys and values
 *
 *         Represents a hash table of key-value pairs. Linear probing is used to
 *         handle conflicts.
 * 
 * 
 *         FIXME: You are to comment all functions and variables
 */
public class Hash_Table_Linear_Probing<KeyType, ValueType> implements Hash_Map<KeyType, ValueType> {

	// Backing array list of pairs that holds the key and value at its hashed
	// key.
	private ArrayList<Pair<KeyType, ValueType>> table;

	// Table info.
	// Capacity - size of backing array.
	protected int capacity;
	// Num of entries - number of entries in actual table.
	protected int num_of_entries;
	// Counter to increase probing when necessary.
	protected int probeCount = 0;

	// Stats on collisions and total ops.
	private int collisions = 0;
	private int operations = 0;

	// Whether our table can grow.
	private boolean resizeable;

	/**
	 * Hash Table Constructor
	 * 
	 * @param initial_capacity
	 *            - try to make this equal to twice the expected number of
	 *            values
	 */
	public Hash_Table_Linear_Probing(int initial_capacity) {
		this.capacity = next_prime(initial_capacity);
		init_table();
		this.num_of_entries = 0;
	}

	/**
	 * Puts the given "value" into the hash table under the given "key". On a
	 * duplicate entry, replace the old data with the new "value".
	 * 
	 * For Probing Tables: This method will double* the size of the table if the
	 * number of elements is > 1/2 the capacity For Chaining Tables: double* the
	 * size of the table if the average number of collisions is greater than 5.
	 * *double --> double then choose next greatest prime
	 * 
	 * FIXME: Make sure you collect statistics in this method. See
	 * print_stats().
	 */
	public void insert(KeyType key, ValueType value) {
		this.operations++;

		// Check if we need to resize and resize if necessary.
		if (this.resizeable && this.num_of_entries > (.5 * this.capacity)) {
			resize(Primes.next_prime(2 * this.capacity));
		}

		Pair<KeyType, ValueType> pair = new Pair<>(key, value);

		int index = key.hashCode();

		// Continue to probe until we find an empty bucket or the same key.
		while (table.get(wrapIndex(index)) != null || !table.get(wrapIndex(index)).key.equals(key)) {
			this.collisions++;
			index = probe(index);
		}

		// Resets probing scaling num.
		this.probeCount = 0;

		// Set pair will either replace or instantiate at this position.
		table.set(wrapIndex(index), pair);
		this.num_of_entries++;
	}

	/**
	 * Helper method that wraps provided index to the capacity of the table.
	 * 
	 * @param index
	 *            - index to wrap.
	 * @return - wrapped index.
	 */
	public int wrapIndex(int index) {
		return index % this.capacity;
	}

	/**
	 * Function that probes linearly forward to find the next open bucket after
	 * a collision.
	 * 
	 * @param index
	 *            - index of collision.
	 * @return - next 'bucket' after collision index to place pair.
	 */
	public int probe(int index) {
		this.probeCount++;
		return index + this.probeCount;
	}

	/**
	 * Search for an item in the hash table, using the given "key". Return the
	 * item if it exists in the hash table. Otherwise, returns null.
	 * 
	 * FIXME: Make sure you collect statistics in this method. See
	 * print_stats().
	 */
	public ValueType find(KeyType key) {
		this.operations++;

		int index = key.hashCode();
		while (table.get(wrapIndex(index)) == null || !table.get(wrapIndex(index)).key.equals(key)) {
			this.collisions++;
			index = probe(index);
		}

		Pair<KeyType, ValueType> pair = table.get(wrapIndex(index));
		return pair.value;
	}

	/**
	 * Remove all items from the hash table (and resets stats).
	 */
	public void clear() {
		init_table();
		this.num_of_entries = 0;
		reset_stats();
	}

	/**
	 * Returns the capacity of the hash table.
	 */
	public int capacity() {
		return this.capacity;
	}

	/**
	 * Returns the number of entries in the hash table (i.e., the number of
	 * stored key-value pairs).
	 */
	public int size() {
		return this.num_of_entries;
	}

	/**
	 * 
	 */
	public ArrayList<Double> print_stats() {
		// FIXME: insert code
		return null;
	}

	/**
	 * Fill in calculations to show some of the stats about the hash table
	 */
	public String toString() {
		String result = new String();
		result = "------------ Hash Table Info ------------\n" + "  Average collisions: "
				+ "  Average Hash Function Time: " + "  Average Insertion Time: " + "  Average Find Time: "
				+ "  Percent filled : " + "  Size of Table  : " + "  Elements       : "
				+ "-----------------------------------------\n";

		return result;

	}

	/**
	 * Resets the hash table stats.
	 *
	 */
	public void reset_stats() {
		this.collisions = 0;
		this.num_of_entries = 0;
		this.operations = 0;
	}

	/**
	 * Clear the hash table array and initializes all of the entries in the
	 * table to null.
	 */
	private void init_table() {
		// 1) build an array list of CAPACITY null values
		// note 1: create an initial array list (which will have a size 0) but
		// set the capacity to CAPACITY
		// note 2: then, you must explicitly insert nulls into the array list
		// CAPACITY times
		// 2) set the number of elements in the hash table to 0

		table = new ArrayList<Pair<KeyType, ValueType>>(capacity);
		for (int index = 0; index < table.size(); index++) {
			table.set(index, null);
		}
	}

	/**
	 * Set whether or not the hash map will resize when it reaches half full.
	 */
	public void set_resize_allowable(boolean status) {
		this.resizeable = status;
	}

	/**
	 * Expand the hash table to the new size, IF the new_size is GREATER than
	 * the current size (if not, doesn't do anything)
	 * 
	 * NOTE: The new hash table should have buckets equal in number the next
	 * prime number greater than or equal to the given "new_size". All the data
	 * in the original hash table must be maintained in the recreated hash
	 * table.
	 * 
	 * Note: make sure if you change the size, you rebuild your statistics...
	 */
	public void resize(int new_size) {
		ArrayList<Pair<KeyType, ValueType>> old = table;

		// Set new size and reset stats.
		// New capacity determined by Primes class.
		this.capacity = new_size;
		this.reset_stats();

		init_table();

		// For every pair from the old ArrayList that is non null, rehash into
		// new one.
		for (Pair<KeyType, ValueType> pair : old) {
			if (pair != null) {
				this.insert(pair.key, pair.value);
			}
		}

	}

}