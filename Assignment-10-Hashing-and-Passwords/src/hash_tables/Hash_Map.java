package hash_tables;

import java.util.ArrayList;

/**
 * @author H. James de St. Germain, April 2007
 *
 *  Required methods for all hash table implementations	
 *  
 *  WARNING!!!! KeyType must be an element that defines equals and hashCode
 */
public interface Hash_Map<KeyType, ValueType>
{
	/**
	 * Puts the given "value" into the hash table under the given "key".
	 * On a duplicate entry, replace the old data with the new "value".
	 * 
	 * FIXME: Make sure you collect statistics in this method. See print_stats().
	 */
	public void insert( KeyType key, ValueType value );

	/**
	 * Search for an item in the hash table, using the given "key".
	 * Return the item if it exists in the hash table.
	 * Otherwise, returns null.
	 * 
	 * statistics are collected based on collisions. See print_stats().
	 */
	public ValueType find( KeyType key );

	/**
	 * Remove all items from the hash table (and resets stats).
	 */
	public void clear();

	/**
	 * Returns the capacity (total number of "buckets" in the "backing store") of the hash table.
	 * starts at (a "largish" prime number)
	 */
	public int capacity();

	/**
	 * Returns the number of entries in the hash table (i.e., the number of stored key-value pairs).
	 * starts at 0.
	 */
	public int size();
	
	/** 
	 * if status is true, then automatically resize your tables when filling up.
	 * if false, do not automatically resize your table.
	 */
	public void set_resize_allowable( boolean status );

	/**
	 * Prints information about the hash table.
	 * 
	 * 
	 * WARNING: 1) do not "instrument" your hash table to compute these values until you have them working.
	 *             (simply return 0s).  
	 *          2) do not "instrument" the quadratic probing or chaining hash tables until you have the linear probing
	 *             table done.
	 *          3) The point value forthis part of the program is small compared to the total assignmnet points 
	 *
	 * Every time you look at a "bucket" in the hash table, you should
	 * track this fact. This includes during insert and find
	 * operations.
	 * 
	 * Every time you do an operation (such as find and insert)
	 * you should track this information
	 *
	 * This function should return the ratio of number of operations to
	 * number of "hits". If you have a perfect hash function you will
	 * have a ratio of 1. If you have a bad hash function (or a table
	 * with many entries) you will result in higher ratios.
	 * 
	 * A ratio of two means that you (on average) find the node (or and
	 * empty spot for the node, in the case of an insert) one bucket
	 * after the initial hash value. A ratio of 1 means you always
	 * insert into an empty spot or find the element on the first hash
	 * (e.g., one "look" to see the spot is null, and one "operation"
	 * the insertion) or (one "look" to see the spot contains the valid
	 * data, and one "operation" the find).
	 * 
	 * This function should print out a message such as:
	 * 
	 * ------- Hash Table Info -------
	 * Average collisions:         0.418   // number of incorrect buckets "looked at" vs (number of inserts + finds)
	 * Average Hash Function Time:  1421   // in nanoseconds
	 * Average Insertion Time:      6534   
	 * Average Find Time:           6534   
	 * Size of Table:                949
	 * Capacity of Table :          2019
	 * Percent filled :             47.0 % 
	 * -----------------------------------------
	 * 
	 * (Note: use toString!)
	 * 
	 * For testing purposes, this method should return the following information in
	 * an ArrayList<Double>:
	 * 
	 * 1) collisions (per find/insert)
	 * 2) number of elements
	 * 3) the capacity.
	 * 
	 */
	public ArrayList<Double> print_stats();

	/**
	 * Expand the hash table to the new size, IF the new_size is GREATER than the current size
	 * (if not, doesn't do anything)
	 * 
	 * NOTE: The new hash table should have buckets equal in number the next prime number
	 * greater than or equal to the given "new_size". All the data in the original hash
	 * table must be maintained in the recreated hash table.
	 * 
	 * Note: make sure if you change the size, you rebuild your statistics...
	 */
	public void resize( int new_size );

}