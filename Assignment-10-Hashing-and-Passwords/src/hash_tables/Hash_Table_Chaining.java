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
	long insert_time = 0;
	long hash_time = 0;
	int find_num = 0;
	int hash_num = 0;
	long find_time = 0;
	private int bucketChecks = 0;
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
		if(resizeable && (double)num_of_entries/capacity >=5){
			resize(Primes.next_prime(capacity *2));
		}
		long hash_start_time = System.nanoTime();
		int index = key.hashCode();
		hash_num++;
		long hash_end_time = System.nanoTime();
		hash_time += (hash_end_time-hash_start_time);
		Pair<KeyType,ValueType> pair = new Pair<KeyType,ValueType>(key, value);
		num_of_entries++;
		if(array.get(wrap(index))!=null && this.find(key)!=null){
		for(Pair<KeyType,ValueType> newpair: array.get(wrap(index))){
			if(newpair.equals(pair)){
				newpair=pair;
				num_of_entries--;
				return;
			}
		}
		}
		array.get(wrap(index)).add(pair);
		long insert_end_time = System.nanoTime();
		insert_time += (insert_end_time-insert_start_time);
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
		find_num++;
		long hash_start_time = System.nanoTime();
		int index = key.hashCode();
		hash_num++;
		long hash_end_time = System.nanoTime();
		hash_time += (hash_end_time-hash_start_time);
		if(array.get(wrap(index))!=null){
			for(Pair<KeyType,ValueType> newpair: array.get(wrap(index))){
				collisions++;
				if(newpair.key.equals(key)){
					collisions--;
					long find_end_time = System.nanoTime();
					find_time += (find_end_time-find_start_time);
					return newpair.value;
				}
			}
		}
		long find_end_time = System.nanoTime();
		find_time += (find_end_time-find_start_time);
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
	 * @return - The capacity of the array.
	 */
	@Override
	public int capacity() {
		return this.capacity;
	}
	
	/**
	 * Returns the number of entries in the array.
	 * @return - Number of entries in the array.
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
	 * Returns an array of doubles containing the average collisions,
	 * number of entries, and the capacity of the array.
	 * 
	 * @return stats - array containing the previously stated data.
	 */
	@Override
	public ArrayList<Double> print_stats() {
		ArrayList<Double> stats = new ArrayList<Double>();
		// Calculates collisions per bucket check.
		stats.add(( (double) this.collisions / (double) (this.bucketChecks)));
		//
		stats.add((double) this.hash_time / (double) this.hash_num);
		//
		stats.add((double) this.insert_time / (double) this.num_of_entries);
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
	 * Resets the hash table stats.
	 *
	 */
	public void reset_stats() {
		this.collisions = 0;
		this.num_of_entries = 0;
		this.hash_time = 0;
		this.insert_time = 0;
		this.find_num = 0;
		this.find_time = 0;
		this.hash_num = 0;
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
		num_of_entries = 0;
		insert_time = 0;
		hash_time = 0;
		find_num = 0;
		hash_num = 0;
		find_time = 0;
		for(int i = 0; i<temparray.size();i++){
			for(int j = 0; j<temparray.get(i).size(); j++){
				Pair<KeyType,ValueType> temp_pair = temparray.get(i).get(j);
				this.insert(temp_pair.key, temp_pair.value);
			}
		}
	}
}
