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
	private int bucketChecks = 0;
	private int find_num = 0;
	private int insert_num = 0;
	private long insert_time = (long) 0.0;
	private long find_time = (long) 0.0;
	private long hash_time = (long) 0.0;
	private int hash_num = 0;

	// Whether our table can grow.
	private boolean resizeable = true;

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
	 * @param key
	 * 			-The given KeyType
	 * @param value
	 * 			-The given ValueType
	 *
	 */
	public void insert(KeyType key, ValueType value) {
		long startInsertTime = System.nanoTime();

		this.insert_num++;

		// Check if we need to resize and resize if necessary.
		if (this.resizeable && this.num_of_entries > (int) (.5 * this.capacity)) {
			resize(Primes.next_prime(2 * this.capacity));
		}

		Pair<KeyType, ValueType> pair = new Pair<>(key, value);

		this.hash_num++;
		long startHashTime = System.nanoTime();
		int index = Math.abs(key.hashCode());
		this.hash_time += (System.nanoTime() - startHashTime);

		// Continue to probe until we find an empty bucket or the same key.
		// Lambda expression: if at index is null, add there, if not, check if
		// our key matches the key, if not, continue probing, if yes, replace
		// there.
		while ((table.get(wrapIndex(index)) == null) ? false : !table.get(wrapIndex(index)).key.equals(key)) {
			this.collisions++;
			this.bucketChecks++;
			index = Math.abs(probe(index));

			// If probe count reaches capacity, no more room in the table.
			if (this.probeCount > this.capacity) {
				return;
			}
		}
		this.bucketChecks++;
		// Resets probing scaling num.
		this.probeCount = 0;

		// Set pair will either replace or instantiate at this position.
		table.set(wrapIndex(index), pair);
		this.num_of_entries++;

		this.insert_time += (System.nanoTime() - startInsertTime);
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
		index = index + this.probeCount;
		return index;
	}

	/**
	 * Search for an item in the hash table, using the given "key". Return the
	 * item if it exists in the hash table. Otherwise, returns null.
	 * 
	 * @param key
	 * 			-Key that we are looking for.
	 * @return - The value of the pair that has the given key.
	 */
	public ValueType find(KeyType key) {
		long startFindTime = System.nanoTime();

		this.find_num++;

		this.hash_num++;
		long startHashTime = System.nanoTime();
		int index = Math.abs(key.hashCode());
		this.hash_time += (System.nanoTime() - startHashTime);
		
		// Lambda expression: if null, keep probing, if not null, check if our
		// key is there, if yes grab, otherwise keep probing.
		while ((table.get(wrapIndex(index)) == null) ? true : !table.get(wrapIndex(index)).key.equals(key)) {
			if (table.get(wrapIndex(index)) != null) {
				this.collisions++;
			}
			this.bucketChecks++;
			index = Math.abs(probe(index));

			// If probe count reaches capacity, the provided element doesn't
			// exist in the table.
			if (this.probeCount > this.capacity) {
				return null;
			}
		}
		this.bucketChecks++;

		Pair<KeyType, ValueType> pair = table.get(wrapIndex(index));

		this.find_time += (System.nanoTime() - startFindTime);

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
	 * 
	 * @return - The capacity of the array.
	 */
	public int capacity() {
		return this.capacity;
	}

	/**
	 * Returns the number of entries in the hash table (i.e., the number of
	 * stored key-value pairs).
	 * 
	 *  @return - The number of entries in the array.
	 */
	public int size() {
		return this.num_of_entries;
	}

	/**
	 * Calculates and adds the required statistics 
	 * 
	 * @return stats - array containing the required data.
	 */
	public ArrayList<Double> print_stats() {
		ArrayList<Double> stats = new ArrayList<Double>();
		// Calculates collisions per bucket check.
		stats.add(( (double) this.collisions / (double) (this.bucketChecks)));
		//
		stats.add((double) this.hash_time / (double) this.hash_num);
		//
		stats.add((double) this.insert_time / (double) this.insert_num);
		//
		stats.add((double) this.find_time / (double) this.find_num);
		//
		stats.add((double) this.size());
		//
		stats.add((double) this.capacity());
		//
		stats.add((double) this.size() / (double) this.capacity());
		return stats;
	}

	/**
	 * Fill in calculations to show some of the stats about the hash table
	 * 
	 * @return result - The string containing all of the required data and stats.
	 */
	public String toString() {
		ArrayList<Double> stats = print_stats();
		String result = new String();
		result = "------------ Hash Table Info ------------\n" 
				+ "  Average collisions: " + stats.get(0) + "\n"
				+ "  Average Hash Function Time: " + stats.get(1) + "\n"
				+ "  Average Insertion Time: " + stats.get(2) + "\n"
				+ "  Average Find Time: " + stats.get(3) + "\n"
				+ "  Size of Table  : " + stats.get(4) + "\n"
				+ "  Capacity of Table  :  " + stats.get(5) + "\n"
				+ "  Percent filled : " + stats.get(6) + "\n"
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
		this.bucketChecks = 0;
		this.insert_num = 0;
		this.insert_time = 0;
		this.find_num = 0;
		this.find_time = 0;
		this.hash_num = 0;
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
		for (int index = 0; index < capacity; index++) {
			table.add(index, null);
		}
	}

	/**
	 * Set whether or not the hash map will resize when it reaches half full.
	 * 
	 * @param status
	 * 			-boolean of what to change resizeable to.
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
	 * 
	 * @param new_size
	 * 			-int representing the new size of the array.
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