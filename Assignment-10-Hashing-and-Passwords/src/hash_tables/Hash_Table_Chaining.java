/**
 * 
 */
package hash_tables;

import static hash_tables.Primes.next_prime;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Hash table implementation that uses MD5 hashing and probes collisions by
 * adding to the index in a simple, linear fashion.
 * 
 * @author Mark Van der Merwe and Andrew Haas
 */

public class Hash_Table_Chaining<KeyType, ValueType> implements Hash_Map<KeyType, ValueType> {

	ArrayList<LinkedList<Pair<KeyType,ValueType>>> array = new ArrayList<LinkedList<Pair<KeyType,ValueType>>>();
	protected int capacity;
	protected int num_of_entries;
	int collisions;
	boolean resizeable;
	long total_inserting_time = 0;
	long total_hashing_time = 0;
	int find_counter = 0;
	int hash_counter = 0;
	long total_finding_time = 0;
	/**
	 * Hash Table Chaining Constructor
	 * 
	 * @param initial_capacity
	 *            - try to make this equal to twice the expected number of
	 *            hashes
	 */
	public Hash_Table_Chaining(int initial_capacity) {
		this.capacity = next_prime(initial_capacity);
		init_table();
		this.num_of_entries = 0;
		collisions = 0;
		resizeable=true;
	}
	
	private void init_table() {
		array.clear();
		for(int i = 0; i<capacity; i++){
			array.add(new LinkedList<Pair<KeyType,ValueType>>());
		}
	}
	
	public int wrap(int position){
		return position%capacity;
	}
	/**
	 * Creates a Pair out of the given key and value. Checks to
	 * see if the key is already in the array of linked lists,
	 * if it is overrides the old pair with the given one. if
	 * it isn't in the array adds the pair to the corresponding
	 * LinkedList. Counts all collisions that happen.
	 * @param key
	 * 			-The given KeyType
	 * @param value
	 * 			-The given ValueType		
	 */
	@Override
	public void insert(KeyType key, ValueType value) {
		long insert_start_time = System.nanoTime();
		if(resizeable && (double) num_of_entries/capacity >=5){
			resize(Primes.next_prime(capacity *2));
		}
		long hash_start_time = System.nanoTime();
		int index = key.hashCode();
		long hash_end_time = System.nanoTime();
		total_hashing_time += (hash_end_time-hash_start_time);
		Pair<KeyType,ValueType> pair = new Pair<KeyType,ValueType>(key, value);
		num_of_entries++;
		if(array.get(wrap(index))!=null){
		for(Pair<KeyType,ValueType> newpair: array.get(wrap(index))){
			collisions++;
			if(newpair.equals(pair)){
				newpair=pair;
				num_of_entries--;
				return;
			}
		}
		}
		array.get(wrap(index)).add(pair);
		long insert_end_time = System.nanoTime();
		total_inserting_time += (insert_end_time-insert_start_time);
	}
	/**
	 * This method checks if the array already has the 
	 * given key in it and also count collisions.
	 * @param key
	 * 			-The given KeyType
	 * @return -returns the value of the pair with the same key or returns null.
	 */
	@Override
	public ValueType find(KeyType key) {
		long find_start_time = System.nanoTime();
		find_counter++;
		long hash_start_time = System.nanoTime();
		int index = key.hashCode();
		hash_counter++;
		long hash_end_time = System.nanoTime();
		total_hashing_time += (hash_end_time-hash_start_time);
		if(array.get(wrap(index))!=null){
			for(Pair<KeyType,ValueType> newpair: array.get(wrap(index))){
				collisions++;
				if(newpair.key.equals(key)){
					long find_end_time = System.nanoTime();
					total_finding_time += (find_end_time-find_start_time);
					return newpair.value;
				}
			}
		}
		long find_end_time = System.nanoTime();
		total_finding_time += (find_end_time-find_start_time);
		return null;
	}
	
	/**
	 * Resets all variables except capacity and clears the array
	 */
	@Override
	public void clear() {
		init_table();
		this.num_of_entries = 0;
		collisions = 0;
		for(int i = 0; i<capacity; i++){
			array.set(i, new LinkedList<Pair<KeyType,ValueType>>());
		}
	}
	
	/**
	 * Returns the capacity of the array.
	 * @return this.capacity
	 */
	@Override
	public int capacity() {
		return this.capacity;
	}
	
	/**
	 * Returns the number of elements in the array.
	 * @return this.num_of_entries
	 */
	@Override
	public int size() {
		return this.num_of_entries;
	}

	/**
	 * Sets the resizeable instance variable to the given boolean.
	 * @param status
	 * 			-true/false of whether or not the array can be resized
	 */
	@Override
	public void set_resize_allowable(boolean status) {
		resizeable=status;

	}
	
	/**
	 * Fill in calculations to show some of the stats about the hash table
	 */
	public String toString() {
		String result = new String();
		ArrayList<Double> stats = print_stats();
		result = "------------ Hash Table Info ------------\n" + "  Average collisions: " + stats.get(0)
				+ "  Average Hash Function Time: " + total_hashing_time/hash_counter + " Average Insertion Time: " +
				total_inserting_time/num_of_entries + "  Average Find Time: " + total_finding_time/find_counter 
				+ "  Percent filled : " + 100*num_of_entries/(5*capacity) + "  Size of Table  : "+ stats.get(2) + "  Elements       : "
				+ stats.get(1) + "-----------------------------------------\n";

		return result;

	}


	/**
	 * Returns an array of doubles containing the average collisions,
	 * number of entries, and the capacity of the array.
	 * @return stats	-array containing the previously stated data.
	 */
	@Override
	public ArrayList<Double> print_stats() {
		ArrayList<Double> stats = new ArrayList<Double>();
		stats.add((double)collisions/num_of_entries);
		stats.add((double)num_of_entries);
		stats.add((double)capacity);
		return stats;
	}
	
	/**
	 * Replaces array for a new one of the given size and copies the
	 * LinkedLists over.
	 * @param new_size
	 * 			-int representing the size of the new array
	 */
	@Override
	public void resize(int new_size) {
		ArrayList<LinkedList<Pair<KeyType,ValueType>>> temparray = array;
		array= new ArrayList<LinkedList<Pair<KeyType,ValueType>>>();
		capacity=new_size;
		for(int i = 0; i<capacity; i++){
			array.add(new LinkedList<Pair<KeyType,ValueType>>());
		}
		for(int i = 0; i<temparray.size();i++){
			array.set(i, temparray.get(i));
		}
		
	}

}
