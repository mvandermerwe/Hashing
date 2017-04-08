/**
 * 
 */
package hash_tables;

/**
 * Hash table implementation that uses MD5 hashing and probes collisions by
 * adding quadratic values.
 * 
 * @author Mark Van der Merwe and Andrew Haas
 *
 */
public class Hash_Table_Quadtratic_Probing<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType> {

	/**
	 * Construct a new Hash Table w/ quadratic probing.
	 * 
	 * @param initial_capacity
	 *            - initial capacity of the table.
	 */
	public Hash_Table_Quadtratic_Probing(int initial_capacity) {
		super(initial_capacity);
	}

	/**
	 * Quadratic probing method, moves our counter forward by increasing amounts
	 * each time.
	 * 
	 * @param index
	 *            - index to probe from.
	 */
	@Override
	public int probe(int index) {
		this.probeCount++;
		// Probe by squaring our constant and then adding.
		index = index + (1 + 2 * (this.probeCount));
		return index;
	}

}